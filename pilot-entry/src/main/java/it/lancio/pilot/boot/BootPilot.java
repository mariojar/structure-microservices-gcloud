package it.lancio.pilot.boot;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.AbstractEnvironment;

import it.lancio.pilot.configuration.BeanConfiguration;
import it.lancio.pilot.utils.UtilHelper;
import it.lancio.pilot.version.PilotVersion;



@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackageClasses = {
									 PilotVersion.class
									 })
@Import({BeanConfiguration.class})
@EnableAutoConfiguration
public class BootPilot implements ApplicationContextAware {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	@Qualifier("versionProperties") 
	Properties versionProperties;
	
	@Value("${spring.application.name}")
	private String appName;
	
	
	public static void main(String[] args) {
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, UtilHelper.getActiveProfile(args));
		SpringApplication.run(BootPilot.class, args);
	}

	
	@PostConstruct
	public void printVersion() {
		LOGGER.info("\n\t Deploy Version "+ appName + " \n"+UtilHelper.buildVersionInfo(versionProperties)+". \n\n");
	}

	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		
	}
	
}
