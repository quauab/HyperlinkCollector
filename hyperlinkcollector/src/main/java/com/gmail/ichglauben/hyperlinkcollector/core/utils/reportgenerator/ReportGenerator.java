package com.gmail.ichglauben.hyperlinkcollector.core.utils.reportgenerator;

import com.gmail.ichglauben.hyperlinkcollector.core.abstracts.AbstractReportGenerator;

public final class ReportGenerator extends AbstractReportGenerator {
	private static ReportGenerator reporter = new ReportGenerator();
	
	private ReportGenerator() {
		super();
	}

	public static ReportGenerator getInstance() {
		return reporter;
	}
	
	public String toString() { return "Report Generator"; }
}
