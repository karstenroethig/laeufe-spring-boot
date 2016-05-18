package karstenroethig.laeufe;

import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@ComponentScan( basePackageClasses = KeycloakSecurityComponents.class )
@Configuration
@EnableWebSecurity
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
	
	/**
	 * Registers the KeycloakAuthenticationProvider with the authentication manager.
	 */
	@Autowired
	public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
		auth.authenticationProvider( keycloakAuthenticationProvider() );
	}

	@Override
	protected KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
		
		/*
		 * Spring Security, when using role-based authentication, requires that role names start with ROLE_.
		 * For example, an administrator role must be declared in Keycloak as ROLE_ADMIN or similar, not simply ADMIN.
		 * 
		 * The class org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider supports an optional
		 * org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper which can be used to map roles coming
		 * from Keycloak to roles recognized by Spring Security.
		 * Use, for example, org.springframework.security.core.authority.mapping.SimpleAuthorityMapper to insert the ROLE_
		 * prefix and convert the role name to upper case. The class is part of Spring Security Core module.
		 * 
		 * see https://keycloak.github.io/docs/userguide/keycloak-server/html/ch08.html#spring-security-adapter
		 */
		
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();
		SimpleAuthorityMapper simpleAuthorityMapper = new SimpleAuthorityMapper();
		
		simpleAuthorityMapper.setConvertToUpperCase( true );
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper( simpleAuthorityMapper );
		
		return keycloakAuthenticationProvider;
	}
	
	/**
	 * Defines the session authentication strategy.
	 */
	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy( new SessionRegistryImpl() );
	}
	
	@Override
	protected void configure( HttpSecurity http ) throws Exception {
		
		super.configure( http );
		
		http
			.authorizeRequests()
			.antMatchers( "/user/**" ).hasRole( "USER" )
			.antMatchers( "/admin/**" ).hasRole( "ADMIN" )
			.anyRequest().permitAll();
	}
}
