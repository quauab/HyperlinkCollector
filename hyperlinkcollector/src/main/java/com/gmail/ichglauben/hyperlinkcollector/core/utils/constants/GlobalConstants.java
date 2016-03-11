package com.gmail.ichglauben.hyperlinkcollector.core.utils.constants;

import java.nio.file.FileSystems;

import com.gmail.ichglauben.hyperlinkcollector.core.utils.StringUtils;

public class GlobalConstants {
	// Platform Constants
	public final static String OS = System.getProperty("os.name").toLowerCase();
	public final static String OS_ARCH = System.getProperty("os.arch").toLowerCase();
	public final static String OS_VERSION = System.getProperty("os.version").toLowerCase();
	public final static String FILESEPARATOR = FileSystems.getDefault().getSeparator();
	public final static String LINESEPARATOR = System.getProperty("line.separator");
	public final static String USRDIR = System.getProperty("user.dir") + FILESEPARATOR;
	public final static String USRHOME = System.getProperty("user.home") + FILESEPARATOR;

	// Menu Captions
	public static final String MENU_FILE = "file";
	public static final String MENU_EXIT = "exit";
	public static final String MENU_OPEN = "open";

	// Radio Button Captions
	public static final String RADIO_FILE = "file";
	public static final String RADIO_DIR = "directory";
	public static final String MENU_CLOSE = "close";
	public static final String RADIO_TEXT = "text";
	public static final String RADIO_THUMBNAIL = "thumbnails";

	// Button Captions
	public static final String SEARCH = "search";
	public static final String URL = "url";
	public static final String REPORT = "report";

	// ComboBox Items
	public static final String[] REPORTS = { StringUtils.cfc("all"), StringUtils.cfc("relative"),
			StringUtils.cfc("absolute"), StringUtils.cfc("regular"), StringUtils.cfc("secure") };

	// Component Captions
	public static final String RESET = "reset";
	public static final String CLEAR = "clear";
	public static final String START = "start";
	public static final String STOP = "stop";
	public static final String RESULTS = "hyperlinks";

	// Action Commands
	public static final String BUTTON_SEARCH = "search";
	public static final String BUTTON_REPORT = "generate report";

	// Regular Expression Validation
	public static final String VALID_PORT = "^([0-9]){4,}([0-9])?$";
	public static final String ABSOLUTE = "^(http:\\/\\/|https:\\/\\/){1}.*$";
	public static final String REGULAR = "^((http:)\\/\\/).*";
	public static final String SECURE = "^(https:\\/\\/|https:\\/\\/www\\.){1}\\w+(\\.\\w{2,3}.*$)";
	public static final String RELATIVE = "(^(\\/){1}.*)";
	public static final String WWW = "^(http://|https://|http://www\\.|https://www\\.){1}\\w[^(www\\.)| ]+(\\.\\w{2,3}(\\/.*)?$)";

}
