package io.whisper.console.shiro.repository;

import io.whisper.console.repository.base.BaseRepository;
import io.whisper.console.shiro.entity.Resource;

public interface ResourceRepository extends BaseRepository<Resource, Long>{
	public Resource findByName(String name);
	public void deleteAssociateById(Long... resourceIds);
}
