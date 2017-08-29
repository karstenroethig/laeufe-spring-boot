package karstenroethig.laeufe.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties( prefix = "tomcat.ajp", ignoreUnknownFields = false )
@Getter
@Setter
public class TomcatAjpProperties
{
	private boolean enabled = false;
	private int port = 8009;
}
