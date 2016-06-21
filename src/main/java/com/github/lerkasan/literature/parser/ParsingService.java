package com.github.lerkasan.literature.parser;

import java.util.List;

import javax.inject.Named;

@Named
public interface ParsingService {
	List parse(String input);

}
