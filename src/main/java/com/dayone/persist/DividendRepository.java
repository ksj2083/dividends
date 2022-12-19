package com.dayone.persist;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dayone.persist.entity.DividendEntity;

public interface DividendRepository extends JpaRepository<DividendEntity, Long> {

	List<DividendEntity> findAllByCompanyId(Long companyId);

	boolean existsByCompanyIdAndDate(Long companyId, LocalDateTime date);
}
