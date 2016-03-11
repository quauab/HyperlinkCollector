package com.gmail.ichglauben.hyperlinkcollector.core.utils.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gmail.ichglauben.hyperlinkcollector.core.utils.constants.GlobalConstants;

public final class PortValidator {

	private PortValidator() {
		super();
	}

	public static boolean validPort(String port) {
		Pattern pattern = null;
		Matcher matcher = null;
		
		if (null != port) {
			if (!port.isEmpty() || !port.equals(null)) {
				pattern = Pattern.compile(GlobalConstants.VALID_PORT);
				matcher = pattern.matcher(port);
				return matcher.find();
			}
		}
		return false;
	}
	
	public static boolean validPort(int port) {
		return validPort(String.valueOf(port));
	}
}
