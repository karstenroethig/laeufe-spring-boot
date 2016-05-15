package karstenroethig.laeufe.service.impl;

import karstenroethig.laeufe.dto.info.AccountInfoDto;
import karstenroethig.laeufe.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public AccountInfoDto getAccountInfo() {
		
		AccountInfoDto info = new AccountInfoDto();
		
		info.setUserName( "karsten" );
		info.setFullName( "Karsten Röthig" );
		info.setEmail( "me@home.de" );
		
		info.addRole( "Admin" );
		info.addRole( "User" );
		
		return info;
	}
}