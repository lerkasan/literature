package com.github.lerkasan.literature.parser.impl;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.controller.Messages;
import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.parser.ConvertableToItemToRead;
import com.github.lerkasan.literature.parser.ParsingService;
import com.github.lerkasan.literature.service.ItemToReadService;

@Service("ParsingService")
public abstract class ParsingServiceImpl implements ParsingService {
	
	@Inject
	private ItemToReadService itemToReadService;
	
	 public String save(int[] selectedItemsIds, List<ConvertableToItemToRead> items) {
			boolean oldItemsFlag = false;
			if ((selectedItemsIds != null) && (items != null)) {
				for (int i : selectedItemsIds) {
					ConvertableToItemToRead convertableItem = items.get(i);
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

/*	@Override
	public List<? extends ConvertableToItemToRead> parse(String input) {
		//should be always overrided
		return null;
	}
*/
	 
	 

}