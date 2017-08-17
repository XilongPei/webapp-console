package io.whisper.console.shiro.repository;

import io.whisper.console.repository.base.BaseRepository;
import io.whisper.console.shiro.entity.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {
	public Role findByName(String name);
	public void deleteAssociateById(Long... roleIds);
}
