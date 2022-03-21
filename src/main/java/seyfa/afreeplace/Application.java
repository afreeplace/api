package seyfa.afreeplace;

import com.google.maps.GeoApiContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seyfa.afreeplace.utils.lifecycle.BootstrapActions;
import seyfa.afreeplace.utils.lifecycle.EndActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@EnableScheduling
@SpringBootApplication
public class Application {

	static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Value("${app.name}")
	private String appName;

	@Value("${app.profile}")
	private String profile;

	@Autowired
	GeoApiContext geoApiContext;

	@Autowired
	BootstrapActions bootstrapActions;

	@Autowired
	EndActions endActions;

	@PostConstruct
	public void post() {
		logger.info("API: '{}', Environment: '{}' ", appName, profile);
		bootstrapActions.executeActions();
	}

	@PreDestroy
	public void pre() {

		geoApiContext.shutdown();
		logger.info("GeoApiContext shut down success");

		endActions.executeActions();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
