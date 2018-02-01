package com.graby.store.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.graby.store.entity.User;
import com.graby.store.service.base.AuthService;
import com.graby.store.util.Digests;
import com.graby.store.util.Encodes;
import com.graby.store.util.EncryptUtil;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private AuthService userService;

	@Test
	@Rollback(false)
	public void testRegister() {
		User user = new User();
		user.setUsername("admin");
		user.setPlainPassword("admin");
		user.setPassword("admin");
		user.setRoles("admin");
		user.setDescription("超级管理员");
		userService.registerUser(user);
	}

	public static void main(String[] args) {
		System.out.println(entryptPassword("admin"));
	}

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static String entryptPassword(String passwd) {
		passwd = EncryptUtil.md5(passwd);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(passwd.getBytes(), salt,
				HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword);
	}
}
