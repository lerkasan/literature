package com.github.lerkasan.literature.parser;

import com.github.lerkasan.literature.entity.ItemToRead;

public interface ConvertableToItemToRead {
	public final String[] DATE_FORMAT_STRINGS = { "yyyy-MM-dd", "yyyy-MM", "yyyy" };
	
	public ItemToRead convertToItem();
	public String getUrl();
	//public List<ItemToRead> convertItems(List<BasicItemStructure> basicItems);
}
