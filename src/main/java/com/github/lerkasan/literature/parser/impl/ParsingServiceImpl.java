package com.github.lerkasan.literature.parser.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.controller.Messages;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.parser.ConvertableToItemToRead;
import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.service.ItemToReadService;

@Service("ParsingService")
public class ParsingServiceImpl implements ParsingService {

	@Inject
	private ItemToReadService itemToReadService;

	@Inject
	private AutowireCapableBeanFactory beanFactory;

	public String save(int[] selectedItemsIds, List<ConvertableToItemToRead> items) {
		boolean oldItemsFlag = false;
		if ((selectedItemsIds != null) && (items != null)) {
			for (int i : selectedItemsIds) {

				ConvertableToItemToRead convertableItem = items.get(i);
				//without the next line injections used in method convertableItem.convertToItem() are null
				beanFactory.autowireBean(convertableItem);
				
				String url = convertableItem.getUrl();
				ItemToRead foundItem = itemToReadService.getByUrl(url);
				if (foundItem == null) {
					ItemToRead item = convertableItem.convertToItem();
					item.setAddedAt(LocalDate.now());
					itemToReadService.save(item);
				} else {
					oldItemsFlag = true;
				}
			}
		}
		return oldItemsFlag ? Messages.SOME_ITEMS_ALREADY_IN_DB : Messages.SUCCESSFUL_SAVE;
	}

	@Override
	public List<? extends ConvertableToItemToRead> parse(String input) {
		// always should be always overrided
		return null;
	}

	public LocalDate parseDate(String dateString, String[] formatStrings) {
		// String[] formatStrings = { "M/y", "M/d/y", "M-d-y" };
		for (String formatString : formatStrings) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
				return LocalDate.parse(dateString, formatter);
			} catch (Exception e) {
			}
		}
		return null;
	}

}
