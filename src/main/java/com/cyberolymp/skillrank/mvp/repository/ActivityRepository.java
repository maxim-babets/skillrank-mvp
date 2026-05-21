package com.cyberolymp.skillrank.mvp.repository;


import com.cyberolymp.skillrank.mvp.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
