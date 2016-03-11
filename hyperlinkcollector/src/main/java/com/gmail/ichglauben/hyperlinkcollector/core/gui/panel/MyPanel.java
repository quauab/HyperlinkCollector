package com.gmail.ichglauben.hyperlinkcollector.core.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.gmail.ichglauben.basicgui.core.abstracts.panel.CustomPanel;
import com.gmail.ichglauben.hyperlinkcollector.core.concrete.AnchorCollector;
import com.gmail.ichglauben.hyperlinkcollector.core.gui.frame.MyFrame;
import com.gmail.ichglauben.hyperlinkcollector.core.utils.CustomDialog;
import com.gmail.ichglauben.hyperlinkcollector.core.utils.StringUtils;
import com.gmail.ichglauben.hyperlinkcollector.core.utils.constants.GlobalConstants;

public class MyPanel extends CustomPanel {
	public final MyMenu menu = new MyMenu();
	public final String TITLE = "Hyperlink Collector";
	private final static AnchorCollector collector = AnchorCollector.getInstance();
	private final static CustomDialog dialog = CustomDialog.getInstance();
	private MyFrame frame = null;
	private JTextField textfield_url = new JTextField();
	private JButton button_url = new JButton(StringUtils.cfc(GlobalConstants.BUTTON_SEARCH));
	private JButton button_generate_report = new JButton(StringUtils.cfc(GlobalConstants.BUTTON_REPORT));
	private JComboBox combo_report = null;
	private JTextPane textpane_results = new JTextPane();
	private StyledDocument doc = textpane_results.getStyledDocument();
	private SimpleAttributeSet left = new SimpleAttributeSet();
	private JPanel panel_textfield_url = new JPanel();
	private JPanel panel_button_url = new JPanel();
	private JPanel panel_button_generate_report = new JPanel();
	private JPanel panel_combo_report = new JPanel();
	private JPanel panel_scroll_panel_textpane = new JPanel();;
	private String captured_textfield_url = "";
	private String captured_combobox_selected_item = "";

	public MyPanel() {
		super();
		setPreferredSize(new Dimension(625,525));
		setAlignmentX(JPanel.CENTER_ALIGNMENT);
		setupPanel();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		updateUI();
	}

	private void setupPanel() {
		textfield_url.setPreferredSize(new Dimension(400,23));
		textfield_url.setFont(new Font("", Font.PLAIN, 12));
		textfield_url.setBorder(BorderFactory.createEmptyBorder());
		textfield_url.addKeyListener(new TextfieldHandler());
		panel_textfield_url.add(textfield_url);
		
		button_url.setFont(new Font(StringUtils.cfc(GlobalConstants.BUTTON_SEARCH), Font.TRUETYPE_FONT, 13));
		button_url.setEnabled(false);
		button_url.setActionCommand(GlobalConstants.SEARCH);
		button_url.addActionListener(new ButtonHandler());
		panel_button_url.add(button_url);
		
		combo_report = new JComboBox(GlobalConstants.REPORTS);
		combo_report.setPreferredSize(new Dimension(150, 25));
		((JLabel)combo_report.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		combo_report.addActionListener(new ComboboxHandler());
		combo_report.setEditable(false);
		combo_report.setEnabled(false);
		combo_report.setSelectedItem(0);
		panel_combo_report.add(combo_report);
		
		button_generate_report.setFont(new Font(StringUtils.cfc(GlobalConstants.BUTTON_REPORT), Font.TRUETYPE_FONT, 13));
		button_generate_report.setEnabled(false);
		button_generate_report.setActionCommand(GlobalConstants.REPORT);
		button_generate_report.addActionListener(new ButtonHandler());
		panel_button_generate_report.add(button_generate_report);
		
		textpane_results.setEditable(false);
		textpane_results.setPreferredSize(new Dimension(445, 420));
		textpane_results.setBackground(Color.white);
		textpane_results.setForeground(Color.DARK_GRAY);
		textpane_results.setFont(new Font("", Font.PLAIN, 12));
		
		// Northern Components
		JLabel label_url = new JLabel(StringUtils.cs(GlobalConstants.URL));
		label_url.setFont(new Font(StringUtils.cs(GlobalConstants.URL), Font.ITALIC, 15));
		
		JPanel panel_label_url = new JPanel();
		panel_label_url.add(label_url);
		
		JPanel panel_north = new JPanel();
		panel_north.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		panel_north.add(panel_label_url);
		panel_north.add(panel_textfield_url);
		panel_north.add(panel_button_url);
		add(panel_north, BorderLayout.NORTH);
		
		// Western Components
		JLabel label_report = new JLabel(StringUtils.cs(GlobalConstants.REPORT));
		label_report.setFont(new Font(StringUtils.cs(GlobalConstants.REPORT), Font.BOLD, 12));
		
		JPanel panel_label_report = new JPanel();
		panel_label_report.add(label_report);
		
		JPanel panel_west = new JPanel(new BorderLayout());
		panel_west.add(panel_label_report, BorderLayout.NORTH);
		panel_west.add(panel_combo_report, BorderLayout.CENTER);
		panel_west.add(panel_button_generate_report, BorderLayout.SOUTH);
		add(panel_west, BorderLayout.WEST);
		
		// Central Components
		JScrollPane scroll_panel_textpane = new JScrollPane(textpane_results);
		scroll_panel_textpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll_panel_textpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll_panel_textpane.setPreferredSize(new Dimension(445,425));
		scroll_panel_textpane.setBorder(BorderFactory.createEmptyBorder());
		panel_scroll_panel_textpane.add(scroll_panel_textpane);
		add(panel_scroll_panel_textpane, BorderLayout.CENTER);
		updateCentralPanel();
	}
	
	/**
	 * Utils
	 */
	private void appendText(final String string) {
		Runnable runner = new Runnable() {
			public void run() {
				int length = doc.getLength();
				try {
					doc.insertString(doc.getLength(), (GlobalConstants.LINESEPARATOR + string), null);
					doc.setParagraphAttributes((length += 1), 1, left, false);
				} catch (BadLocationException ble) {
					print("Inserted text at a bad location - " + String.valueOf(length));
				}
			}
		};
		EventQueue.invokeLater(runner);
	}

	private void resetTextPane() {
		if (!textpane_results.getText().isEmpty())
			textpane_results.setText("");
	}
	
	private void updateCentralPanel() {
		boolean bool_results = collector.hasResults();
		
		if (bool_results) {
			enableWesternComponents();
			resetTextPane();
			for (String link : collector.getAnchors()) {
				appendText(link);
			}
		} else if (!bool_results) {
			disableWesternComponents();
		}
		
		String str_results;
		switch (collector.getAnchors().size()) {
		default:
			str_results = collector.getAnchors().size() + " Hyperlinks";
			break;

		case 1:
			str_results = collector.getAnchors().size() + " HyperLink";
			break;
		}

		changePanelTextPaneTitle(str_results);
	}
	
	private void changePanelTextPaneTitle(String title) {
		panel_scroll_panel_textpane.setBorder(BorderFactory.createTitledBorder(title));
	}
	
	private void resetTextField() {
		if (!textfield_url.getText().isEmpty())
			textfield_url.setText("");
	}
	
	private void resetCollector() {
		collector.clearList();
	}
	
	private void disableWesternComponents() {
		combo_report.setEnabled(false);
		combo_report.setSelectedIndex(0);
		button_generate_report.setEnabled(false);
	}
	
	private void enableWesternComponents() {
		combo_report.setEnabled(true);
		button_generate_report.setEnabled(true);
	}
	
	private void fullReset() {
		resetCollector();
		updateCentralPanel();
		resetTextPane();
		resetTextField();
		button_url.setEnabled(false);
		disableWesternComponents();
	}
	
	private void scrapePage() {
		SwingWorker worker = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				collector.scrapePage(captured_textfield_url);
				return null;
			}

			@Override
			protected void done() {
				updateCentralPanel();
				button_url.setEnabled(true);
			}
		};
		worker.execute();
	}
	
	private void generateReport() {
		dialog.plain(captured_combobox_selected_item);
	}
	
	private void print(String string) {
		System.out.println(string);
	}
	
	private void print(Object obj) {
		String text = null;
		try {
			if (null != (text = String.valueOf(obj)))
				print(text);
		} catch (Exception exception) {
			try {
				print(obj.toString());
			} catch (Exception exc) {
				return;
			}
		}
	}
		
	private void exit() {
		frame.exitProg();
	}

	/**
	 * Setters
	 */
	public void setMF(MyFrame mf) {
		this.frame = mf;
	}

	public String toString() {
		return "Custom JPanel";
	}

	/**
	 * Inner Classes*/
	private static final class TextFieldValidator {
		public static boolean valid(JTextField jtf) {
			return (notEmpty(jtf) && validUrl(jtf));
		}
		
		private static boolean notEmpty(JTextField jtf) {
			return (!jtf.getText().isEmpty());
		}
		
		private static boolean validUrl(JTextField jtf) {
			String text = null;
			if (null != (text = jtf.getText())) {
				return Pattern.matches(GlobalConstants.WWW, text);
			}
			return false;
		}
	}
	
	private class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			switch (ae.getActionCommand()) {
			case GlobalConstants.SEARCH:
				callButtonSearchHandler();
				break;
				
			case GlobalConstants.REPORT:
				callButtonReportHandler();
				break;
			}
		}

		private final void callButtonSearchHandler() {
			resetTextPane();
			resetCollector();
			changePanelTextPaneTitle(collector.getAnchors().size() + " Hyperlinks");
			disableWesternComponents();
			scrapePage();
			button_url.setEnabled(false);
		}
		
		private final void callButtonReportHandler() {
			collector.generateReport(captured_combobox_selected_item.toLowerCase());
		}
	}
	
	private class ComboboxHandler implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			JComboBox jcb = ((JComboBox)ae.getSource());
			callItemSelected(jcb);
		}
		
		private final void callItemSelected(final JComboBox jcb) {
			captured_combobox_selected_item = jcb.getSelectedItem().toString();
			String str_results;

			switch (captured_combobox_selected_item.toLowerCase()) {
			case "all":
				if (collector.hasResults())
					updateCentralPanel();
				break;

			case "relative":
				if (collector.hasResults()) {
					if (collector.getRelativeLinks().size() > 0) {
						if (!button_generate_report.isEnabled())
							button_generate_report.setEnabled(true);
						resetTextPane();
						for (String str : collector.getRelativeLinks()) {
							appendText(str);
						}

						switch (collector.getRelativeLinks().size()) {
						default:
							str_results = collector.getRelativeLinks().size() + " Relative Hyperlinks";
							break;

						case 1:
							str_results = collector.getRelativeLinks().size() + " Relative HyperLink";
							break;
						}

						changePanelTextPaneTitle(str_results);
						updateUI();
					} else {
						button_generate_report.setEnabled(false);
					}
				}
				break;

			case "absolute":
				if (collector.hasResults()) {
					if (collector.getAbsoluteLinks().size() > 0) {
						if (!button_generate_report.isEnabled())
							button_generate_report.setEnabled(true);
						resetTextPane();
						for (String str : collector.getAbsoluteLinks()) {
							appendText(str);
						}

						switch (collector.getAbsoluteLinks().size()) {
						default:
							str_results = collector.getAbsoluteLinks().size() + " Absolute Hyperlinks";
							break;

						case 1:
							str_results = collector.getAbsoluteLinks().size() + " Absolute HyperLink";
							break;
						}

						changePanelTextPaneTitle(str_results);
						updateUI();
					} else {
						button_generate_report.setEnabled(false);
					}
				}
				break;

			case "regular":
				if (collector.hasResults()) {
					if (collector.getRegularAbsoluteLinks().size() > 0) {
						if (!button_generate_report.isEnabled())
							button_generate_report.setEnabled(true);
						resetTextPane();
						for (String str : collector.getRegularAbsoluteLinks()) {
							appendText(str);
						}

						switch (collector.getRegularAbsoluteLinks().size()) {
						default:
							str_results = collector.getRegularAbsoluteLinks().size() + " Normal Absolute Hyperlinks";
							break;

						case 1:
							str_results = collector.getRegularAbsoluteLinks().size() + " Normal Absolute HyperLink";
							break;
						}

						changePanelTextPaneTitle(str_results);
						updateUI();
					} else {
						button_generate_report.setEnabled(false);
					}
				}
				break;

			case "secure":
				if (collector.hasResults()) {
					if (collector.getSecureAbsoluteLinks().size() > 0) {
						if (!button_generate_report.isEnabled())
							button_generate_report.setEnabled(true);
						resetTextPane();
						for (String str : collector.getSecureAbsoluteLinks()) {
							appendText(str);
						}

						switch (collector.getSecureAbsoluteLinks().size()) {
						default:
							str_results = collector.getSecureAbsoluteLinks().size() + " Secure Absolute Hyperlinks";
							break;

						case 1:
							str_results = collector.getSecureAbsoluteLinks().size() + " Secure Absolute HyperLink";
							break;
						}

						changePanelTextPaneTitle(str_results);
						updateUI();
					} else {
						button_generate_report.setEnabled(false);
					}
				}
				break;
			}
		}
	}
		
	private class TextfieldHandler implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			JTextField jtf = ((JTextField)ke.getSource());
			keyReleasedHandler(jtf);			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		private void keyReleasedHandler(final JTextField jtf) {
			Runnable runner = new Runnable() {
				public void run() {
					captured_textfield_url = jtf.getText();
					button_url.setEnabled((TextFieldValidator.valid(jtf)));
				}
			};
			EventQueue.invokeLater(runner);
		}
	}
	
	private class MyMenu extends JMenuBar implements ActionListener {
		private final javax.swing.ImageIcon icon = createImageIcon("/q6.gif");

		public void actionPerformed(final ActionEvent ae) {
			Runnable runner = new Runnable() {
				public void run() {
					switch (ae.getActionCommand().toString()) {
					case GlobalConstants.MENU_OPEN:

						break;

					case GlobalConstants.MENU_CLOSE:
						fullReset();
						break;

					case GlobalConstants.MENU_EXIT:
						exit();
						break;
					}
				}
			};
			EventQueue.invokeLater(runner);
		}

		public MyMenu() {
			super();
			createMenuItems();
		}

		void createMenuItems() {
			JMenuItem item = null;
			JMenu file = null;

			// File menu
			file = new JMenu(StringUtils.cfc(GlobalConstants.MENU_FILE));

			// Open
			item = new JMenuItem(icon);
			item.setActionCommand(GlobalConstants.MENU_OPEN);
			item.setMnemonic(KeyEvent.VK_O);
			item.setText(StringUtils.cfc(GlobalConstants.MENU_OPEN));
			item.addActionListener(this);
			file.add(item);
			add(file);

			// Close
			item = new JMenuItem(icon);
			item.setActionCommand(GlobalConstants.MENU_CLOSE);
			item.setMnemonic(KeyEvent.VK_E);
			item.setText(StringUtils.cfc(GlobalConstants.MENU_CLOSE));
			item.addActionListener(this);
			file.add(item);
			add(file);

			// Exit
			file.addSeparator();
			item = new JMenuItem(icon);
			item.setActionCommand(GlobalConstants.MENU_EXIT);
			item.setMnemonic(KeyEvent.VK_X);
			item.setText(StringUtils.cfc(GlobalConstants.MENU_EXIT));
			item.addActionListener(this);
			file.add(item);
			add(file);
		}

		private javax.swing.ImageIcon createImageIcon(String path) {
			java.net.URL imgURL = MyMenu.class.getResource(path);
			if (imgURL != null) {
				return new javax.swing.ImageIcon(imgURL);
			} else {
				System.err.println("Couldn't find file at: \n" + path);
				return null;
			}
		}

		public String toString() {
			return "Custom Menu";
		}
	}
}
