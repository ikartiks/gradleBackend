package com.kartiks.biz;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;

import com.kartiks.common.RESTErrorUtil;
import com.kartiks.common.SearchCriteria;
import com.kartiks.common.SearchUtil;
import com.kartiks.common.StringUtil;
import com.kartiks.db.DaoManager;
import com.kartiks.db.view.VUser;
import com.kartiks.db.view.VUserList;
import com.kartiks.security.CustomAuthenticationProvider;
import com.kartiks.service.UserService;
import com.kartiks.view.VString;

@Component
public class ManagerUser {

	static Logger logger = Logger.getLogger(ManagerUser.class);

	@Autowired
	RESTErrorUtil restErrorUtil;

	@Autowired
	DaoManager daoManager;

	@Autowired
	UserService userService;

	@Autowired
	StringUtil stringUtil;

	@Autowired
	SearchUtil searchUtil;
	
	
	
	@Autowired
	@Qualifier("CustomAuthenticationProvider")
	CustomAuthenticationProvider authenticationProvider;

	@Autowired
	PlatformTransactionManager platformTransactionManager;

	public VUser authenticateUser(VUser user,HttpServletRequest request) {
		
		VUser fromDb =getUserProfileByLoginId(user.getEmail());
		
		if(fromDb!=null 
				&& (fromDb.getEmail().equals(user.getEmail()))
				&&(	(fromDb.getPassword().equals(user.getPassword())||(fromDb.getPassword().equals(user.getEmail()+"temp"))	))){
			
			
			if(fromDb.getPassword().equals(user.getEmail()+"temp")){
				fromDb.setPassword(user.getPassword());
				userService.updateResource(fromDb);
			}
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

		    // Authenticate the user
			
		    Authentication authentication = authenticationProvider.authenticate(authRequest);
		    SecurityContext securityContext = SecurityContextHolder.getContext();
		    securityContext.setAuthentication(authentication);
		    
		    
		    // Create a new session and add the security context.
		    //code works fine with session false
		    //HttpSession session = request.getSession(false);
		    //session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//		    String sessionString=null;
//		    if(session!=null)
//		    		sessionString=session.getId();
		    
		    user.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
			return user;
		}
		
		throw restErrorUtil.createKartiksRESTException(
				new VString("User does not exist OR invalid username and password Or disabled"));
	}

	
	public VUser getUser(Long id) {
		return userService.readResource(id);
	}

	public VUser createUser(final VUser vUser) {

		TransactionTemplate t = new TransactionTemplate(platformTransactionManager);

		if ("ROLE_SALES_PERSON".equals(vUser.getRole()) && (null!=vUser.getCreatedBy())) {

			VUser currentForMaxFree = userService.readResource(vUser.getCreatedBy());
			long maxFreeUsers = currentForMaxFree.getMaxFree();
			if (maxFreeUsers > 0) {

				currentForMaxFree.setMaxFree(maxFreeUsers - 1);
				userService.updateResource(currentForMaxFree);
			} else {
				throw restErrorUtil.createKartiksRESTException(new VString("max free limit exhausted"));
			}
		}

		VUser obj = t.execute(new TransactionCallback<VUser>() {
			@Override
			public VUser doInTransaction(TransactionStatus arg0) {
				return userService.createResource(vUser);
			}
		});

		// ROLE_ADMIN,ROLE_MANAGER,ROLE_SALES_PERSON
		if (obj.getRole()==null)
			obj.setRole(vUser.getRole());
		
		daoManager.getRolesDAO().insertRoles(obj);
		
		// Following should be added /removed while sending to UI.
		obj.setRole(vUser.getRole());
		//obj.setPassword("");
		return obj;
	}
	
	
	public VUser createUserMobile(final VUser vUser,HttpServletRequest request) {
		
		VUser fromDb = getUserProfileByLoginId(vUser.getEmail());
		
		if(fromDb==null){
			
			logger.error("from DB = null");
			//create new user
			VUser newUser =createUser(vUser);
			//added so that users password wont be updated when someone else is creatign user
			if(vUser.getCreatedBy()==null)
				newUser = authenticateUser(newUser, request);
			
			newUser.setRole(daoManager.getRolesDAO().getRoles(newUser.getId()));
			
			return newUser;
			
		}else if(fromDb!=null 
			&& (fromDb.getEmail().equals(vUser.getEmail()))
			&&(	(fromDb.getPassword().equals(vUser.getPassword())||(fromDb.getPassword().equals(vUser.getEmail()+"temp"))	))){
			
			logger.error("from DB != null");
			//check user name and password
			VUser user=null;
			vUser.setId(fromDb.getId());
			//added so that users password wont be updated when someone else is creatign user
			if(vUser.getCreatedBy()==null)
				user=authenticateUser(vUser, request);
			else 
				user=vUser;
			
			
			
			user.setRole(daoManager.getRolesDAO().getRoles(fromDb.getId()));
			userService.updateResource(user);
			
			return user;
		}else{
			//will bever come here
			throw restErrorUtil.createKartiksRESTException(new VString("User already exists"));
		}
	}

	public VUser updateUser(VUser vUser) {
		return userService.updateResource(vUser);
	}

	public void deleteUser(Long id) {

		userService.deleteResource(id);
	}

//	public VUser getUserProfile() {
//		VUser vUser = getUserProfileByLoginId(ContextUtil.getCurrentUserLoginId());
//		return vUser;
//	}

	public VUser getUserProfileByLoginId(String email) {
		
		return userService.findUserByName(email);
	}
	
	public VUser getResource(Long id) {

		
		VUser user=userService.readResource(id);
		user.setRole(daoManager.getRolesDAO().getRoles(user.getId()));
		return user;

	}

	public VUserList searchUsers(SearchCriteria searchCriteria) {

		return userService.searchUsers(searchCriteria);
	}

}
