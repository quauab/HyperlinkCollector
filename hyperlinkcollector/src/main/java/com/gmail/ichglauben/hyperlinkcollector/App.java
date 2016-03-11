package com.gmail.ichglauben.hyperlinkcollector;

import javax.swing.SwingUtilities;

import com.gmail.ichglauben.hyperlinkcollector.core.gui.frame.MyFrame;
import com.gmail.ichglauben.hyperlinkcollector.core.gui.panel.MyPanel;

public class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MyFrame(new MyPanel());
			}
		});
	}
}
