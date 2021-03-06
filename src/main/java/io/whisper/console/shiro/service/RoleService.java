package io.whisper.console.shiro.service;


import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.whisper.console.service.base.BaseService;
import io.whisper.console.shiro.entity.Role;
import io.whisper.console.shiro.entity.User;
import io.whisper.console.shiro.exception.RoleDuplicateException;

public interface RoleService extends  BaseService<Role, Long> {


	/**
	 * 建立角色与资源之间的联系
	 * @param roleId
	 * @param resourceIds
	 */
	public void connectRoleAndResource(Long roleId,Long ...resourceIds );
	public void disconnnectRoleAndResource(Long roledId,Long... resourceIds);
	public void clearAllRoleAndResourceConnection(Long roleId);
	public void update(Role role);
	public void saveWithCheckDuplicate(Role role,User user) throws RoleDuplicateException;
	public Page<Role> findRoleByCondition(Map<String, Object> searchParams,
			Pageable pageable);
}
