package com.gmail.ichglauben.hyperlinkcollector.core.abstracts;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import com.gmail.ichglauben.hyperlinkcollector.core.utils.StringUtils;
import com.gmail.ichglauben.hyperlinkcollector.core.utils.constants.GlobalConstants;
import com.gmail.ichglauben.hyperlinkcollector.core.utils.reportgenerator.ReportGenerator;
import com.gmail.ichglauben.hyperlinkcollector.core.utils.validators.PathValidator;

public abstract class AbstractAnchorScraper extends CustomClass {
	private List<String> anchors = new ArrayList<String>();
	private ReportGenerator reporter = ReportGenerator.getInstance();
	private boolean report_generated = false;
	private String web_address = "";
	
	public AbstractAnchorScraper() {
		super();
	}

	public void scrapePage(String url) throws Exception {
		Reader reader = null;
		if (null != url) {
			if (!url.isEmpty()) {
				clearList();
				web_address = url;
				try {
					URL address = new URL(url);
					InputStream input_stream = address.openStream();
					reader = new InputStreamReader(input_stream);

					ParserDelegator parser = new ParserDelegator();

					parser.parse(reader, new HTMLEditorKit.ParserCallback() {
						public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
							if (t == HTML.Tag.A) {
								Enumeration<?> attrNames = a.getAttributeNames();
								while (attrNames.hasMoreElements()) {
									Object key = attrNames.nextElement();
									if ("href".equalsIgnoreCase(key.toString())) {
										anchors.add(a.getAttribute(key).toString());
									}
								}
							}
						}
					}, true);
				} catch (IOException ioe) {
					return;
				} finally {
					if (reader != null) {
						reader.close();
					}
				}
			}
		}
	}

	public boolean hasResults() {
		return (anchors.size() > 0);
	}

	/**
	 * This method will write the list of links to file, per
	 * argument request: all, absolute, relative, regular or secure.
	 * @param which_results Name which links to report
	 * @return boolean Returns true if, and only if report was generated*/
	public boolean generateReport(String which_results) {
		switch (which_results.toLowerCase()) {
		case "all":
			report_generated = reporter.generateReport(new File(StringUtils.extractDomainName(web_address) + "_All_Links"), getAnchors(), true);
			break;
			
		case "absolute":
			report_generated = reporter.generateReport(new File(StringUtils.extractDomainName(web_address) + "_Absolute_Links"), getAbsoluteLinks(), true);
			break;
			
		case "relative":
			report_generated = reporter.generateReport(new File(StringUtils.extractDomainName(web_address) + "_Relative_Links"), getRelativeLinks(), true);
			break;
			
		case "regular":
			report_generated = reporter.generateReport(new File(StringUtils.extractDomainName(web_address) + "_Regular_Absolute_Links"), getRegularAbsoluteLinks(), true);
			break;
			
		case "secure":
			report_generated = reporter.generateReport(new File(StringUtils.extractDomainName(web_address) + "_Secure_Absolute_Links"), getSecureAbsoluteLinks(), true);
			break;
		}		
		generateSummaryReport();
		return report_generated;
	}

	private void generateSummaryReport() {
		if (!PathValidator.pathExists(StringUtils.extractDomainName(web_address))) {
			try {
				reporter.summary(new File(StringUtils.extractDomainName(web_address)), web_address);
			} catch (IOException e) {
			}
		}
	}
	
	public List<String> getAnchors() {
		return anchors;
	}

	public List<String> getAbsoluteLinks() {
		List<String> list = null;
		if (hasResults()) {
			list = new ArrayList<String>();
			for (String link : anchors) {
				if (isAbsoluteLink(link))
					list.add(link);
			}
			return list;
		}
		return null;
	}

	public List<String> getRegularAbsoluteLinks() {
		List<String> list = null;
		if (hasResults()) {
			list = new ArrayList<String>();
			for (String link : anchors) {
				if (isRegularAbsoluteLink(link))
					list.add(link);
			}
			return list;
		}
		return null;
	}

	public List<String> getSecureAbsoluteLinks() {
		List<String> list = null;
		if (hasResults()) {
			list = new ArrayList<String>();
			for (String link : anchors) {
				if (isSecureAbsoluteLink(link))
					list.add(link);
			}
			return list;
		}
		return null;
	}

	public List<String> getRelativeLinks() {
		List<String> list = null;
		if (hasResults()) {
			list = new ArrayList<String>();
			for (String link : anchors) {
				if (isRelativeLink(link))
					list.add(link);
			}
			return list;
		}
		return null;
	}

	public String getWebAddress() {
		return web_address;
	}
	
	private boolean isRegularAbsoluteLink(String anchor) {
		Pattern pattern = Pattern.compile(GlobalConstants.REGULAR);
		Matcher matcher = pattern.matcher(anchor);
		return matcher.find();
	}

	private boolean isSecureAbsoluteLink(String anchor) {
		Pattern pattern = Pattern.compile(GlobalConstants.SECURE);
		Matcher matcher = pattern.matcher(anchor);
		return matcher.find();
	}

	private boolean isAbsoluteLink(String anchor) {
		Pattern pattern = Pattern.compile(GlobalConstants.ABSOLUTE);
		Matcher matcher = pattern.matcher(anchor);
		return matcher.find();
	}

	private boolean isRelativeLink(String anchor) {
		Pattern pattern = Pattern.compile(GlobalConstants.RELATIVE);
		Matcher matcher = pattern.matcher(anchor);
		return matcher.find();
	}

	public void clearList() {
		anchors.clear();
	}

	public String toString() {
		return "Abstract Anchor Extractor";
	}

}
