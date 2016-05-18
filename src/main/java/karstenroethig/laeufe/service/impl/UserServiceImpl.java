package karstenroethig.laeufe.service.impl;

import karstenroethig.laeufe.dto.info.AccountInfoDto;
import karstenroethig.laeufe.service.UserService;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public AccountInfoDto getAccountInfo() {
		
		AccountInfoDto info = new AccountInfoDto();
		
		info.setUserName( "???" );
		info.setFullName( "???" );
		info.setEmail( "???" );
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if( context == null ) {
			return info;
		}
		
		Authentication authentication = context.getAuthentication();
		
		if( authentication == null ) {
			return info;
		}
		
		info.setUserName( authentication.getName() );
		
		Object principalObj = authentication.getPrincipal();
		
		if( principalObj != null && principalObj instanceof KeycloakPrincipal ) {
			
			@SuppressWarnings("unchecked")
			KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>)principalObj;
			IDToken idToken = principal.getKeycloakSecurityContext().getIdToken();
			
			info.setUserName( idToken.getPreferredUsername() );
			info.setFullName( idToken.getName() );
			info.setEmail( idToken.getEmail() );
		}
		
		for( GrantedAuthority authority : authentication.getAuthorities() ) {
			info.addRole( authority.getAuthority() );
		}
		
		return info;
	}
}