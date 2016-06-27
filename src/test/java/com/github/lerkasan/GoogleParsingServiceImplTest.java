package com.github.lerkasan;

import org.junit.Before;
import org.junit.Test;

import com.github.lerkasan.literature.entity.Resource;

public class GoogleParsingServiceImplTest {
	Resource googleApi = new Resource();

	@Before
	public void setUp() throws Exception {
		googleApi.setId(1);
		googleApi.setName("Google");
		googleApi.setDomain("www.googleapis.com");
		googleApi.setUrl("https://www.googleapis.com/customsearch/v1");
		googleApi.setParameterFormat("q=%s&cx=%s&key=%s");
		googleApi.setResponseFormat("json");
		googleApi.setApiKey("my_key");
		googleApi.setSearchEngineKey("my_key");
	}

	@Test
	public void test() {
		//GoogleParsingServiceImpl parser = new GoogleParsingServiceImpl();
		//ApiRequestServiceImpl requester = new ApiRequestServiceImpl();
		//String result = requester.passRequestToApi(googleApi, "Java+Spring");
		//System.out.println(result);
		//parser.parse(result);
		
		//assertEquals(fishSale1.getFishName(),"");
	}

}
