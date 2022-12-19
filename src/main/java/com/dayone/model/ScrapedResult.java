package com.dayone.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ScrapedResult {

	private Company company;

	private List<Dividend> dividendEntities;

	public ScrapedResult() {
		this.dividendEntities = new ArrayList<>();
	}
}
