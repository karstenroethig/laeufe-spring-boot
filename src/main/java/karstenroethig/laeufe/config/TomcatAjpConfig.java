package karstenroethig.laeufe.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import karstenroethig.laeufe.config.properties.TomcatAjpProperties;

@Configuration
@EnableConfigurationProperties( TomcatAjpProperties.class )
public class TomcatAjpConfig
{
	@Autowired
	private TomcatAjpProperties properties;

	@Bean
	public EmbeddedServletContainerFactory ajpConnector()
	{
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

		if ( properties.isEnabled() )
		{
			Connector ajpConnector = new Connector( "AJP/1.3" );
			ajpConnector.setPort( properties.getPort() );
			tomcat.addAdditionalTomcatConnectors( ajpConnector );

			/*
			 * Bei Verwendung eines Reverse Proxies könnten die folgenden Einstellungen noch relevant werden.
			 * Diese können aber auch über die "server.tomcat.*"-Properties in der application.yml gesetzt werden.
			 * Genauere Hinweise dazu gibt es auch unter https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html#howto-use-tomcat-behind-a-proxy-server
			 */
//			RemoteIpValve remoteIpValve = new RemoteIpValve();
//			remoteIpValve.setRemoteIpHeader( "x-forwarded-for" );
//			remoteIpValve.setProtocolHeader( "x-forwarded-proto" );
//			remoteIpValve.setInternalProxies( "" );
//			tomcat.addContextValves( remoteIpValve );
		}

		return tomcat;
	}
}
