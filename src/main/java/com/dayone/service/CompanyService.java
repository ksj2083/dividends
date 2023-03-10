package com.dayone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.dayone.model.Company;
import com.dayone.model.ScrapedResult;
import com.dayone.persist.CompanyRepository;
import com.dayone.persist.DividendRepository;
import com.dayone.persist.entity.CompanyEntity;
import com.dayone.persist.entity.DividendEntity;
import com.dayone.scraper.Scraper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {

	private final Trie trie;
	private final Scraper yahooFinanceScraper;
	private final CompanyRepository companyRepository;
	private final DividendRepository dividendRepository;

	public Company save(String ticker) {
		boolean exists = this.companyRepository.existsByTicker(ticker);
		if(exists) {
			throw new RuntimeException("already exists ticker ->" + ticker);
		}
		return this.storeCompanyAndDividend(ticker);
	}

	public Page<CompanyEntity> getAllCompany(Pageable pageable) {
		return this.companyRepository.findAll(pageable);
	}

	public Company storeCompanyAndDividend(String ticker) {
		//ticker 를 기준으로 회사를 스크래핑
		Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
		if(ObjectUtils.isEmpty(company)) {
			throw new RuntimeException("failed to scrap ticker -> " + ticker);
		}

		//해당 회사가 존재할 경우, 배당금 정보를 스크래핑
		ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

		//결과
		CompanyEntity companyEntity = this.companyRepository.save(new CompanyEntity(company));
		List<DividendEntity> dividendEntities = scrapedResult.getDividendEntities().stream()
			.map(e -> new DividendEntity(companyEntity.getId(), e))
			.collect(Collectors.toList());

		this.dividendRepository.saveAll(dividendEntities);
		return company;
	}

	public void addAutocompleteKeyword(String keyword) {
		this.trie.put(keyword, null);
	}

	public List<String> autocomplete(String keyword) {
		return (List<String>)this.trie.prefixMap(keyword).keySet()
			.stream()
			.collect(Collectors.toList());
	}

	public void deleteAutocompleteKeyword(String keyword) {
		this.trie.remove(keyword);
	}
}
