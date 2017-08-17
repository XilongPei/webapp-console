package io.whisper.console.shiro.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import io.whisper.console.repository.base.BaseRepository;
import io.whisper.console.shiro.entity.User;

public interface UserRepository extends BaseRepository<User, Long> {
    public User findByUsername(String username);
    public Page<User> getUsersByCondition(User user, Pageable pageable);
}
