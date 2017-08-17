package io.whisper.console.shiro.service;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import io.whisper.console.service.base.BaseService;
import io.whisper.console.shiro.entity.Resource;
import io.whisper.console.shiro.entity.User;
import io.whisper.console.shiro.exception.ResourceDuplicateException;
import io.whisper.console.shiro.exception.RoleDuplicateException;

public interface ResourceService extends  BaseService<Resource, Long> {

	void update(Resource resource);

	void saveWithCheckDuplicate(Resource resource,User user) throws ResourceDuplicateException;

	Page<Resource> findResourceByCondition(Map<String, Object> searchParams,
			Pageable pageable);
	Resource findByName(String name);
}
