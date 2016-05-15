package karstenroethig.laeufe.dto.info;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountInfoDto {

	private String userName;
	private String fullName;
	private String email;
	private List<String> roles = new ArrayList<String>();
	
	public void addRole( String role ) {
		roles.add( role );
	}
	
	public boolean removeRole( String role ) {
		return roles.remove( role );
	}
}