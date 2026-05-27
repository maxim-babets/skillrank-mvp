package com.cyberolymp.skillrank.mvp.repository;

import com.cyberolymp.skillrank.mvp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
