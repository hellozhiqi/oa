package org.fkjava.menu.service;

import java.util.List;

import org.fkjava.menu.domain.Menu;
import org.fkjava.vo.Result;

public interface MenuService {

	void save(Menu menu);

	List<Menu> findTopMenu();

	Result move(String id, String targetId, String moveType);

	Result delect(String id);

	List<Menu> findMenus();
}
