package it.lancio.pilot.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

	//TODO - MFJG questi bean potrebbero essere messi nel framework a fattor comune
	
	private final static Logger LOGGER = LogManager.getLogger();

	

	@Bean(name = "versionProperties")
	public Properties gitProperties() {
		LOGGER.info("CREATE VERSION Properties.. ");
		Properties properties = new Properties();
		try {
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/git.properties"));
		} catch (FileNotFoundException e) {
			LOGGER.info("Git properties file not found .. ");
		} catch (IOException e) {
			LOGGER.info("Git properties file problems .. ");
		}
		return properties;
	}
	
  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
      return new RestTemplate();
  }
}
