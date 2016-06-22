/**********************************************************************************************
 * Copyright 2009 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file 
 * except in compliance with the License. A copy of the License is located at
 *
 *       http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE.txt" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License. 
 *
 * ********************************************************************************************
 *
 *  Amazon Product Advertising API
 *  Signed Requests Sample Code
 *
 *  API Version: 2009-03-31
 *
 */

package com.github.lerkasan.literature.parser.impl.amazon;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.lerkasan.literature.dao.ResourceRepository;
import com.github.lerkasan.literature.entity.Resource;

@Service("AmazonBookSearchService")
public class AmazonBookSearchService {

	@Inject
	ResourceRepository resourceRepository;
	private static String awsAccessKeyId;
	private static String awsSecretKey;
	private static final String ENDPOINT = "ecs.amazonaws.com";

	public String prepareRequestUrl(String keywords) {
		/*
		 * Set up the signed requests helper
		 */
		
		System.out.println("PREPARED QUERY " + keywords);
		SignedRequestsHelper helper;
		Resource foundApi = resourceRepository.findByName("Amazon");
		awsAccessKeyId = foundApi.getApiKey();
		awsSecretKey = foundApi.getSearchEngineKey();

		try {
			helper = SignedRequestsHelper.getInstance(ENDPOINT, awsAccessKeyId, awsSecretKey);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		String requestUrl = null;
		String title = null;

		System.out.println("Map form example:");
		Map<String, String> params = new HashMap<String, String>();

		params.put("Service", "AWSECommerceService");
		params.put("AssociateTag", foundApi.getAssociateId());
		params.put("Version", "2009-03-31");
		params.put("Operation", "ItemSearch");
		params.put("Keywords", keywords);
		//params.put("Keywords", "java%20spring");
		params.put("SearchIndex", "Books");
		params.put("ResponseGroup", "Medium");

		requestUrl = helper.sign(params);
		System.out.println("Signed Request is \"" + requestUrl + "\"");
		return requestUrl;
		/*
		 * title = fetchTitle(requestUrl); System.out.println(
		 * "Signed Title is \"" + title + "\""); System.out.println();
		 */
	}

	/*
	 * Utility function to fetch the response from the service and extract the
	 * title from the XML.
	 */
	private static String fetchTitle(String requestUrl) {
		String title = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(requestUrl);
			NodeList nodes = doc.getElementsByTagName("Item");
			int nodesSize = nodes.getLength();
			/*for (int i = 0; i < nodesSize; i++) {
				title = nodes.item(i).getTextContent();
			}*/
			System.out.println(nodes.item(0).getTextContent());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return title;
	}

}
