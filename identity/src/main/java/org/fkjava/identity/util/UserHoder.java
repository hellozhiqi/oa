package org.fkjava.identity.util;
/**	
 *  此工具,是将User存储到当前线程里面,方便其他模块使用
 */

import org.fkjava.identity.domain.User;

public class UserHoder {

	private static final ThreadLocal<User> LOCAL = new ThreadLocal<>();

	public static User get() {
		return LOCAL.get();
	}

	public static void set(User user) {
		LOCAL.set(user);
	}

	public static void remove() {
		LOCAL.remove();
	}
}
