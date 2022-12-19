package com.dayone.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dayone.model.ScrapedResult;
import com.dayone.service.FinanceService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor
public class FinanceController {

	private final FinanceService financeService;
	@GetMapping("/dividend/{companyName}")
	public ResponseEntity<?> searchFinance(@PathVariable String companyName) {
		ScrapedResult result = this.financeService.getDividendByCompanyName(companyName);
		return ResponseEntity.ok(result);
	}
}
