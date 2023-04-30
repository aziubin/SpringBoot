package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@ConfigurationProperties(prefix = "demo")
public class ApplicationProperties {
	public String version;

//	public String getVersion() {
//		return version;
//	}

	public void setVersion(String version) {
		this.version = version;
	}

//	public ApplicationProperties(String version) {
//		super();
//		this.version = version;
//	}

}
