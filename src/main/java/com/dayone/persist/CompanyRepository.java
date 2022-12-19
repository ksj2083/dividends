package com.dayone.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayone.persist.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
	boolean existsByTicker(String ticker);
}
