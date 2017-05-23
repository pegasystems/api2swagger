package com.pega.api2swagger.utils;

import org.junit.Assert;
import org.junit.Test;

import com.pega.api2swagger.utils.URLUtils;

public class TestURLUtils {

	@Test
	public void hostWithContext(){
		String endpoint= "http://vcentossde3:8080/prweb/PRRestService/reports/caches/mru/v73sde-1";
		Assert.assertEquals("http://vcentossde3:8080/prweb/", URLUtils.calculateHostIncludingContext(endpoint));
	}
	
	@Test
	public void basePath(){
		String endpoint= "http://vcentossde3:8080/prweb/PRRestService/reports/caches/mru/v73sde-1";
		//Assert.assertEquals("/reports", URLUtils.calculateBasePath(endpoint));
	}
	
	@Test
	public void apiPathTest(){
		String endpoint= "http://vcentossde3:8080/prweb/PRRestService/reports/caches/mru/v73sde-1";
		//Assert.assertEquals("/reports/caches/mru/v73sde-1", URLUtils.calculateAPIPath(endpoint));
	}
	
	@Test
	public void pathParameters(){
		String endpoint= "http://vcentossde3:8080/prweb/PRRestService/reports/caches/mru/{nodeId}/{type}";
		Assert.assertEquals("[nodeId, type]", URLUtils.parsePathParameters(endpoint).toString());
	}
}
