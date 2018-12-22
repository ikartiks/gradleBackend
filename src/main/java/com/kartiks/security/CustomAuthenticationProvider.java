package com.kartiks.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.kartiks.db.DAORoles;
import com.kartiks.db.DAOUser;
import com.kartiks.db.DaoManager;
import com.kartiks.entity.EWUser;

//@Component //removed this 
@Qualifier("CustomAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
	static Logger logger = Logger.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	DaoManager daoManager;

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());
		EntityManager em = daoManager.getEntityManager();

		DAOUser daoUser = daoManager.getEWUser();
		DAORoles daoroles = daoManager.getRolesDAO();
		EWUser userExists = daoUser.authenticate(username, password);

		if (userExists != null) {
			
			//in this app every user has onlu a single role
			String role = daoroles.getRoles(userExists.getId());
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new SimpleGrantedAuthority(role));
//			for (String role : roles) {
//				grantedAuths.add(new SimpleGrantedAuthority(role));
//			}
			
			Authentication a=new UsernamePasswordAuthenticationToken(username, password,
					grantedAuths);
			return a ;

		} else
			throw new RuntimeException("user not found");
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.equals(UsernamePasswordAuthenticationToken.class);
	}

}
