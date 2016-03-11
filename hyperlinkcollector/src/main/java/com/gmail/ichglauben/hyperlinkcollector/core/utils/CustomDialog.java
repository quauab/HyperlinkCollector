package com.gmail.ichglauben.hyperlinkcollector.core.utils;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CustomDialog {
	private final static javax.swing.ImageIcon icon_plain = createImageIcon("/q_window.gif");
	private final static javax.swing.ImageIcon dialog_icon = createImageIcon("/q1.gif");
	private final static CustomDialog dialog = new CustomDialog();

	private CustomDialog() {
		super();
	}

	public static void plain(Object msg) {
		final JFrame frame = new JFrame();
		frame.setLocation(new Point(120, 78));

		if (null != msg) {
			Object[] options = { "Alert!" };
			int n = javax.swing.JOptionPane.showOptionDialog(frame, String.valueOf(msg), "Alert",
					javax.swing.JOptionPane.OK_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE, icon_plain, options,
					options[0]);
		}
	}

	public static int information(Object msg) {
		final JFrame frame = new JFrame();
		frame.setLocation(new Point(110, 48));
		int n = 0;

		if (null != msg) {
			Object[] options = { "Acknowledge" };
			n = JOptionPane.showOptionDialog(frame, String.valueOf(msg), String.valueOf(options[0]),
					JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, dialog_icon, options, options[0]);
		}
		return n;
	}

	public static int question(Object msg) {
		final JFrame frame = new JFrame();
		frame.setLocation(new Point(110, 48));

		Object[] options = { "Yes", "No" };

		int n = JOptionPane.showOptionDialog(frame, String.valueOf(msg), "A Silly Question",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		return n;
	}

	public static javax.swing.ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = CustomDialog.class.getResource(path);
		if (imgURL != null) {
			return new javax.swing.ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file at: \n" + path);
			return null;
		}
	}

	public static CustomDialog getInstance() {
		return dialog;
	}
	
	@Override
	public String toString() {
		return "Custom Dialogue";
	}

}
