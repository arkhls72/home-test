package com.home.digital.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.home.digital.core.service.ProxyServiceException;
import com.home.digital.core.service.SecurityService;
import com.home.digital.model.core.security.Group;
import com.home.digital.model.core.security.RoleType;
import com.home.digital.model.core.security.StatusType;
import com.home.digital.model.core.security.User;
import com.home.digital.model.core.security.UserGroup;
import com.home.digital.model.core.security.UserRole;

/**
 * Bootstrap the security data on system startup.
 * 
 * @author thomas
 *
 */
public class SecurityBootstrapBean extends JdbcDaoSupport {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String ADMIN_USER = "admin";
	
	private static final String ADMIN_PASSWORD = ""; 
	
	private static final String ADMIN_ROLE = RoleType.ADMIN.toString();

	private static final String ADMIN_GROUP = "Admin";
	
	private SecurityService securityService;
	
	private PasswordEncoder passwordEncoder;

	private String query = "select user_name, password from adm_user";
	
    private Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
	
    private int count = 0;
    
	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * @param securityService securityService the securityService to set
	 */
	@Autowired
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	/**
	 * Perform database security initialization and checking at start-up.
	 * 
	 * <p>
	 * Check the administrator account setup and populate as required. Primarily
	 * intended to load the account the first time the application is started
	 * after installation.
	 * </p>
	 * 
	 * <p>
	 * Look for passwords requiring encryption and encrypt them. The user
	 * account encryption pattern must conform to that used by
	 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder,
	 * </p>
	 */
	public void secureDatabase() {	
		if (logger.isInfoEnabled()) {
			logger.info("Performing security checks");
		}
		
		this.checkAdminAccount();
		
		this.secureDatabasePasswords();
	}
	
	/**
	 * Check the administrator account setup and populate as required. Primarily
	 * intended to load the account the first time the application is started
	 * after installation.
	 * 
	 * @throws ProxyServiceException on any database errors
	 */
	@Transactional(readOnly=true)
	private void checkAdminAccount() throws ProxyServiceException {
		/* check for admin account */
		if (logger.isInfoEnabled()) {
			logger.info("Checking admin user account");
		}
		
		User user = securityService.getUser(ADMIN_USER);
		if (user == null) {
			if (logger.isInfoEnabled()) {
				logger.info("Initializing admin user account");
			}
			String adminPassword = passwordEncoder.encode(ADMIN_PASSWORD);
			user = new User();
			user.setUserName(ADMIN_USER);
			user.setPassword(adminPassword);
			user.setStatus(StatusType.ACTIVE.toString());
			securityService.createUser(user);
			
			UserRole userRole = new UserRole();
			userRole.setUserName(ADMIN_USER);
			userRole.setRoleName(ADMIN_ROLE);
			securityService.createUserRole(userRole);

			Group group = securityService.getGroup(ADMIN_GROUP);
			UserGroup userGroup = new UserGroup();
			userGroup.setGroupId(group.getGroupId());
			userGroup.setUserName(ADMIN_USER);
			securityService.createUserGroup(userGroup);
			
			if (logger.isInfoEnabled()) {
				logger.info("+------------------------------------------------------------------------+");
				logger.info("| The admin user account has been initialized with the default password. |");
				logger.info("| The system administrator should immediately log in and change it.      |");
				logger.info("+------------------------------------------------------------------------+");
			}
		} 
	}	

	@Transactional(readOnly=true)
	private void secureDatabasePasswords() {
		if (logger.isInfoEnabled()) {
			logger.info("Checking password encryption");
		}
		count = 0;
		getJdbcTemplate().query(query, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String username = rs.getString(1);
				String password = rs.getString(2);				
				if (password == null) {
					password = "";
				}
				
				boolean isBcryptEncoded = BCRYPT_PATTERN.matcher(password).matches();
				
				if (isBcryptEncoded == false) {
					if (logger.isInfoEnabled()) {
						logger.info("Encrypting password for user [{}]", username);
					}
					String encodedPassword = passwordEncoder.encode(password);
					getJdbcTemplate().update("update adm_user set password = ? where user_name = ?", 
							encodedPassword, username);
					count++;
				}
			}
		});
		if (count > 0 && logger.isInfoEnabled()) {
			logger.info("Encrypted [{}] passwords", count);
		}
	}
}