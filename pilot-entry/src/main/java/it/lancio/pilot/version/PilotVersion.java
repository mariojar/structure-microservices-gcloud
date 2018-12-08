package it.lancio.pilot.version;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.lancio.pilot.utils.UtilHelper;

@RefreshScope
@RestController
@RequestMapping(path = "/pilot/version", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PilotVersion {
	
	private final static Logger LOGGER = LogManager.getLogger();
	
	@Autowired
	@Qualifier("versionProperties")
	private Properties versionProperties;
		
	private @Value("${configuration.greeting:notFoundConfiguration}") 
	String defaultGreeting;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping(method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public  String get(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String,String> requestParameters){
		LOGGER.info("defaultGreeting : {}",defaultGreeting);
		LOGGER.info("START - show version with requestParameters {}",requestParameters);
		
		if (versionProperties == null) return "Git properties file not found not avaliabily";
		if(null != requestParameters && requestParameters.containsKey("printAllServices")) printAllService();
		
		Map<String,String> version = UtilHelper.buildVersionInfo(versionProperties);
		version.put("HOST", UtilHelper.getHostName());
		version.put("NOW", (new Date()).toString());
		version.put("DEFAULT-GREETING", defaultGreeting);
		
		ObjectMapper mapperObj = new ObjectMapper();
		String responseVersion = "Not version avaliability";
		try {
			responseVersion = mapperObj.writeValueAsString(version);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		LOGGER.info("\n  Deploy Version LOCAL Info \n"+version+". \n");
		LOGGER.info("END - show version");
		return responseVersion ;
	}

	private void printAllService() {
		List<String> servicesNames =  discoveryClient.getServices();
		for (String services : servicesNames) {
			List<ServiceInstance> servicesInstance = discoveryClient.getInstances(services);
			for (ServiceInstance instanceInfo : servicesInstance) {
				LOGGER.info("INSTANCE AVALIABILITY FOR DISCOVERY CLIENT - show version with [uri= {}] - [getIPAddr ={}] -[getPort ={}]",
						instanceInfo.getUri().toString(),instanceInfo.getHost(),instanceInfo.getPort());
			}
		}
	}
}