package com.pavely.app.rest;

import org.springframework.stereotype.Component;

@Component
public class ServerOrigin {
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
