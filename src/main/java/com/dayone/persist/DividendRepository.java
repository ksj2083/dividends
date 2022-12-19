package com.dayone.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayone.persist.entity.DividendEntity;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
}
