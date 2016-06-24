package com.github.lerkasan.literature.parser;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.lerkasan.literature.entity.ItemToRead;
import com.github.lerkasan.literature.entity.Literature;

@Service("ParsingService")
public interface ParsingService {
	
	List<? extends ConvertableToItemToRead> parse(String input);
	
	public String save(int[] selectedItemsIds, List<ConvertableToItemToRead> items);

	// ItemToRead convertToItem(ConvertableToItemToRead item);

	

}
