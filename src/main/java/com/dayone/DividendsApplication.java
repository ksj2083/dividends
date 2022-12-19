package com.dayone;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DividendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DividendsApplication.class, args);

		try {
			Connection connection = Jsoup.connect(
				"https://finance.yahoo.com/quote/COKE/history?period1=1639869434&period2=1671405434&interval=1mo&filter=history&frequency=1mo&includeAdjustedClose=true"
			);
			Document document = connection.get();

			Elements elements = document.getElementsByAttributeValue("data-test", "historical-prices");
			Element element = elements.get(0);

			Element tbody = element.children().get(1);
			for (Element e : tbody.children()) {
				String txt = e.text();
				if (!txt.endsWith("Dividend")) {
					continue;
				}

				String[] splits = txt.split(" ");
				String month = splits[0];
				int day = Integer.parseInt(splits[1].replace(",", ""));
				int year = Integer.parseInt(splits[2]);
				String dividend = splits[3];

				System.out.println(year + "/" + month + "/" + day + " -> " + dividend);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}