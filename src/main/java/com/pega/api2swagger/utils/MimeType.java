package com.pega.api2swagger.utils;

public enum MimeType {
	JSON("application/json"), ZIP("application/zip"), XML("application/xml"), OCTETSTREAM("application/octet-stream");

	private String mName;

	MimeType(String aName) {
		mName = aName;
	}

	public String getName() {
		return mName;
	}
}
