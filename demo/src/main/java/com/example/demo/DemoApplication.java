package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aziub
 * https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.0.6&packaging=jar&jvmVersion=17&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&dependencies=lombok,devtools,web,data-jpa
 */
@SpringBootApplication
//@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

//@ConfigurationProperties(prefix = "demo")
//record ApplicationProperties(String version) {}

@ConfigurationProperties(prefix = "demo")
class ApplicationPropertiesLocal {
	//@NotBlank
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

@RestController
class api {
	@Autowired
	ApplicationPropertiesLocal applicationProperties;
	
	@GetMapping("/temperature")
	Integer getTemperature() {
		return 1;
	}

	@GetMapping("/humidity")
	Integer getHumidity() {
		return 8;
	}

	@GetMapping("/pressure")
	Integer getPressure() {
		return 817;
	}

	@GetMapping("/version")
	String getVersion() {
		return applicationProperties.version;
	}

}
