package com.likelion.likelionproject.repository;

import com.likelion.likelionproject.entity.Alarm;
import com.likelion.likelionproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Page<Alarm> findByUserId(@Param(value = "user_id") Long userId, Pageable pageable);
    void deleteAllByPost(@Param(value = "post") Post post);
}
