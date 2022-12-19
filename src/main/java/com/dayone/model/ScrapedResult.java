package com.dayone.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ScrapedResult {

	private Company company;

	private List<Dividend> dividendEntities;

	public ScrapedResult() {
		this.dividendEntities = new ArrayList<>();
	}
}
