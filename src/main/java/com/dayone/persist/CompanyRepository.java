package com.dayone.persist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayone.persist.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
	boolean existsByTicker(String ticker);
	Optional<CompanyEntity> findByName(String name);
}
