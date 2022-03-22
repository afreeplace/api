package seyfa.afreeplace.configuration;

import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GoogleGeocodingConfiguration {

    final static Logger logger = LoggerFactory.getLogger(GoogleGeocodingConfiguration.class);

    @Value("${app.geocoding.google.key}")
    String key;

    @Bean
    public GeoApiContext getGeoApiContext() {
        GeoApiContext context = new GeoApiContext.Builder()//new GaeRequestHandler.Builder())
                .apiKey(key)
                .maxRetries(5)
                .retryTimeout(1, TimeUnit.SECONDS)
                .build();
        logger.info("GeoApiContext initialized {}", context);
        return context;
    }

}
