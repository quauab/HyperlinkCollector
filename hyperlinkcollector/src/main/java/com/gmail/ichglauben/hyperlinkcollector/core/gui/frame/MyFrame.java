package com.gmail.ichglauben.hyperlinkcollector.core.gui.frame;

import com.gmail.ichglauben.basicgui.core.abstracts.frame.CustomFrame;
import com.gmail.ichglauben.hyperlinkcollector.core.gui.panel.MyPanel;

public class MyFrame extends CustomFrame {

	public MyFrame() {
		super();
	}
	
	public MyFrame(MyPanel panel) {
		super(panel);
		setJMenuBar(panel.menu);
		panel.setMF(this);
		setTitle(panel.TITLE);
	}

}
