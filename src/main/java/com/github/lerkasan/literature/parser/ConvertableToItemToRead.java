package com.github.lerkasan.literature.parser;

import com.github.lerkasan.literature.entity.ItemToRead;

public interface ConvertableToItemToRead {
	public ItemToRead convertToItem();
	//public List<ItemToRead> convertItems(List<BasicItemStructure> basicItems);


}
