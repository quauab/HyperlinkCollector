package com.gmail.ichglauben.hyperlinkcollector.core.concrete;

import com.gmail.ichglauben.hyperlinkcollector.core.abstracts.AbstractAnchorScraper;

public final class AnchorCollector extends AbstractAnchorScraper {
	private static AnchorCollector scraper = new AnchorCollector();
	
	private AnchorCollector() {
		super();
	}
	
	public static AnchorCollector getInstance() {
		return scraper;
	}
	
	public String toString() { return "Anchor Scraper"; }

}
