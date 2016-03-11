package com.gmail.ichglauben.hyperlinkcollector.core.abstracts;

import java.nio.file.FileSystems;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public abstract class CustomClass {

	protected static void print(String string) {
		if (null != string) {
			if (!new String(string).isEmpty()) {
				System.out.println(string);
			}
		}
	}

	protected static void print(Object string) {
		String temp = null;
		if (null != (temp = String.valueOf(string))) {
			print(temp);
		} else if (null == temp) {
			try {
				temp = string.toString();
			} catch (Exception exception) {
				return;
			}
		}
	}

	private final static javax.swing.ImageIcon icon = createImageIcon("/q5.gif");

	protected static void alert(Object msg) {
		Object[] options = { "Acknowledge" };
		javax.swing.JOptionPane.showOptionDialog(null, String.valueOf(msg), "Alert",
				javax.swing.JOptionPane.OK_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, icon, options, options[0]);
	}

	protected static String stamp() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int mon = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DATE);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		String y = String.valueOf(year);
		String m = String.valueOf(mon);
		String d = String.valueOf(day);
		String h = String.valueOf(hour);
		String mi = String.valueOf(min);
		String s = String.valueOf(sec);
		String date = new String(y + ":" + m + ":" + d + " " + h + ":" + mi + ":" + s);
		return date;
	}

	protected static void detectPlatform() {
		final String os = FsConstants.OS;
		String windows = ".*windows.*";
		String linux = ".*linux.*";
		String gtk = ".*gtk.*";


		if ((Pattern.matches(windows, os))) {
			setWindowsLookAndFeel();
		} else if ((Pattern.matches(linux, os))) {
			setLinuxLookAndFeel();
		} else if ((Pattern.matches(gtk, os))) {
			setGtkLookAndFeel();
		} else {
			setDefaultLookAndFeel();
		}
	}
	
	protected String extractName(String s) {
		int index = s.indexOf(".");
		if(index != -1) {
			return s.substring(0,index);
		}
		else {
			return s;
		}
	}
	
	protected static void sleep(int milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException ir) {
		}		
	}
	
	private static void setDefaultLookAndFeel() {
		try {
			UIManager.setLookAndFeel(PlatformConstants.METAL);
			return;
		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (UnsupportedLookAndFeelException ulf) {
			return;
		} catch (IllegalAccessException iae) {
			return;
		}
	}

	private static void setGtkLookAndFeel() {
		try {
			UIManager.setLookAndFeel(PlatformConstants.GTK);
			return;
		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (UnsupportedLookAndFeelException ulf) {
			return;
		} catch (IllegalAccessException iae) {
			return;
		}
	}

	private static void setLinuxLookAndFeel() {
		try {
			UIManager.setLookAndFeel(PlatformConstants.LINUX);
			return;
		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (UnsupportedLookAndFeelException ulf) {
			return;
		} catch (IllegalAccessException iae) {
			return;
		}
	}

	private static void setWindowsLookAndFeel() {
		try {
			UIManager.setLookAndFeel(PlatformConstants.WIN);
			return;
		} catch (ClassNotFoundException e) {
			return;
		} catch (InstantiationException e) {
			return;
		} catch (UnsupportedLookAndFeelException ulf) {
			try {
				UIManager.setLookAndFeel(PlatformConstants.WINCLASSIC);
				return;
			} catch (ClassNotFoundException e) {
				return;
			} catch (InstantiationException e) {
				return;
			} catch (UnsupportedLookAndFeelException ulaf) {
				return;
			} catch (IllegalAccessException iae) {
				return;
			}
		} catch (IllegalAccessException iae) {
			return;
		}
	}

	private static javax.swing.ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = CustomClass.class.getResource(path);
		if (imgURL != null) {
			return new javax.swing.ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file at: \n" + path);
			return null;
		}
	}

	public String toString() {
		return "Custom Utilities";
	}

	private final static class PlatformConstants {
		public final static String GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
		public final static String LINUX = "com.sun.java.swing.plat.linux.LinuxLookAndFeel";
		public final static String WIN = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		public final static String WINCLASSIC = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
		public final static String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
		public final static String METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
		public final static String NIMBUS = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	}

	private final static class FsConstants {
		public final static String OS = System.getProperty("os.name").toLowerCase();
		public final static String OS_ARCH = System.getProperty("os.arch").toLowerCase();
		public final static String OS_VERSION = System.getProperty("os.version").toLowerCase();
		public final static String FILESEPARATOR = FileSystems.getDefault().getSeparator();
		public final static String USRDIR = System.getProperty("user.dir") + FILESEPARATOR;
		public final static String USRHOME = System.getProperty("user.home") + FILESEPARATOR;

	}

}
