package TranslatingWordProcessor;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.*;

public class GUI {

	private JTextArea textArea;
	private JToolBar toolBar;
	private JToggleButton Italic;
	private JComboBox letterSize;
	private JComboBox font;
	private JButton transButton;
	private JScrollPane inputContainer;
	boolean clickStart = false;
	boolean B = false;
	boolean I = false;
	boolean UnderL = false;
	String text = null;
	String fontSize = null;
	String fontType = null;

	private JScrollPane scrollPane;
	private JFrame startframe;
	private JFrame frame = new JFrame();
	private JToggleButton Bold;
	private JToolBar toolBar_1;
	private JLabel lblNewLabel;
	private JComboBox from_1;
	private JLabel lblNewLabel_1;
	private JComboBox to_1;
	String from, to;
	private JLabel lblNewLabel_2;
	private JTextField WordCount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.startframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		loading();
	}

	private void loading() {
		startframe = new JFrame();
		startframe.setTitle("GPTranslator");
		startframe.setBounds(200, 200, 700, 625);
		startframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startframe.getContentPane().setLayout(null);
		ImageIcon icon = new ImageIcon("./image/loading.png");
		JPanel load = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		ImageIcon img = new ImageIcon("./image/start.png");
		JButton start = new JButton(img);
		start.addActionListener(new goInitialize());
		start.setBounds(283, 450, 128, 68);
		start.setBorderPainted(false);
		//start.setContentAreaFilled(false);
		scrollPane = new JScrollPane(load);
		startframe.setContentPane(scrollPane);
		startframe.getContentPane().add(start);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setTitle("GPTranslator");
		frame.setBounds(200, 200, 700, 625);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		toolBar = new JToolBar();
		toolBar.setBounds(12, 10, 660, 35);
		toolBar.getAccessibleContext();
		frame.getContentPane().add(toolBar);

		Bold = new JToggleButton("B");
		Bold.setFont(new Font("굴림", Font.BOLD, 12));
		Bold.setPreferredSize(new Dimension(30, 30));
		Bold.addActionListener(new FontChanger());
		toolBar.add(Bold);
		toolBar.addSeparator();

		Italic = new JToggleButton("I");
		Italic.setFont(new Font("굴림", Font.ITALIC, 17));
		Italic.setPreferredSize(new Dimension(30, 30));
		Italic.addActionListener(new FontChanger());
		toolBar.add(Italic);
		toolBar.addSeparator();

		letterSize = new JComboBox(new String[] { "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" });
		letterSize.setSelectedIndex(2);
		letterSize.addActionListener(new FontChanger());
		toolBar.add(letterSize);
		toolBar.addSeparator();

		font = new JComboBox(new String[] { "굴림", "궁서", "나눔고딕", "맑은 고딕", "HY견고딕" });
		font.setFont(new Font("Gulim", Font.PLAIN, 12));
		font.addActionListener(new FontChanger());
		toolBar.add(font);

		ImageIcon img = new ImageIcon("./image/button.png");
		transButton = new JButton(img);
		transButton.setBounds(295, 525, 50, 50);
		transButton.setBorderPainted(false);
		transButton.setContentAreaFilled(false);
		transButton.addActionListener(new ButtonClickListener());
		frame.getContentPane().add(transButton);

		inputContainer = new JScrollPane();
		inputContainer.setBounds(22, 54, 650, 461);
		frame.getContentPane().add(inputContainer);

		textArea = new JTextArea();
		textArea.setFont(new Font("Gulim", Font.PLAIN, 12));
		textArea.setLineWrap(true);
		inputContainer.setViewportView(textArea);

		toolBar_1 = new JToolBar();
		toolBar_1.setBounds(32, 525, 251, 26);
		frame.getContentPane().add(toolBar_1);

		lblNewLabel = new JLabel("From");
		toolBar_1.add(lblNewLabel);
		toolBar_1.addSeparator();

		from_1 = new JComboBox(new String[] { "en", "ko", "fr", "de", "ja", "zh", "es" });
		from_1.setSelectedIndex(1);
		toolBar_1.add(from_1);
		toolBar_1.addSeparator();

		lblNewLabel_1 = new JLabel("To");
		toolBar_1.add(lblNewLabel_1);
		toolBar_1.addSeparator();

		to_1 = new JComboBox(new String[] { "en", "ko", "fr", "de", "ja", "zh", "es" });
		toolBar_1.add(to_1);
		toolBar_1.addSeparator();

		lblNewLabel_2 = new JLabel("WordCount");
		lblNewLabel_2.setBounds(433, 525, 73, 26);
		frame.getContentPane().add(lblNewLabel_2);

		WordCount = new JTextField();
		WordCount.setEditable(false);
		WordCount.setBounds(502, 525, 125, 26);
		frame.getContentPane().add(WordCount);
		WordCount.setColumns(10);
	}

	class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) // 번역 버튼을 눌렀을때
		{
			text = textArea.getText(); // 여기서 입력한 값을 가져옴.
			from = from_1.getSelectedItem().toString();
			to = to_1.getSelectedItem().toString();

			String result = new Client(text, from, to).getTrans(); // 이 입력값을 통해, 새로운 client를 만듦.
			int wordCount = new CountWord().getWordCount(result);
			int end = textArea.getSelectionEnd();
			WordCount.setText(Integer.toString(wordCount));
			textArea.replaceRange("", 0, end);
			textArea.append(result);
		}
	}

	class FontChanger implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			B = Bold.isSelected();
			I = Italic.isSelected();
			fontSize = letterSize.getSelectedItem().toString();
			fontType = font.getSelectedItem().toString();
			if (B == true) {
				if (I == true)
					textArea.setFont(new Font(fontType, Font.BOLD | Font.ITALIC, Integer.parseInt(fontSize)));
				else
					textArea.setFont(new Font(fontType, Font.BOLD, Integer.parseInt(fontSize)));
			} else if (I == true)
				textArea.setFont(new Font(fontType, Font.ITALIC, Integer.parseInt(fontSize)));
			else
				textArea.setFont(new Font(fontType, Font.PLAIN, Integer.parseInt(fontSize)));
		}
	}
	
	class goInitialize implements ActionListener {
		public void actionPerformed(ActionEvent e) // 번역 버튼을 눌렀을때
		{
			startframe.setVisible(false);
			frame.setVisible(true);
			initialize();
		}
	}
}