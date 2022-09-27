package client;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class ClientTransferView extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel labelHost;
	private JTextField textFieldHost;
	private JLabel labelPort;
	private JTextField textFieldPort;
	private JLabel labelUsername;
	private JTextField textUsername;
	private JButton btnBrowse;
	private JTextField textFieldContenMess;
	private JButton btnSendFile;
	private JTextArea textAreaResult;
	private JTextField textNameUserReceive;
	private JScrollPane scrollableTextArea;
	private boolean isSendFile = false;

	public JTextField getTextFieldContenMess() {
		return textFieldContenMess;
	}

	public void setTextFieldContenMess(JTextField textFieldContenMess) {
		this.textFieldContenMess = textFieldContenMess;
	}

	private JPanel panelMessChat;
	private JScrollPane scrollListChat;
	private JButton btnNewMess;

	public JButton getBtnNewMess() {
		return btnNewMess;
	}

	public void setBtnNewMess(JButton btnNewMess) {
		this.btnNewMess = btnNewMess;
	}

	public JPanel getPanelMessChat() {
		return panelMessChat;
	}

	public void setPanelMessChat(JPanel panelMessChat) {
		this.panelMessChat = panelMessChat;
	}

	public JTextField getTextNameUserReceive() {
		return textNameUserReceive;
	}

	public void setTextNameUserReceive(JTextField textNameUserReceive) {
		this.textNameUserReceive = textNameUserReceive;
	}

	public JLabel getLabelUsername() {
		return labelUsername;
	}

	public void setLabelUsername(JLabel labelUsername) {
		this.labelUsername = labelUsername;
	}

	public JTextField getTextUsername() {
		return textUsername;
	}

	public void setTextUsername(JTextField textUsername) {
		this.textUsername = textUsername;
	}

	public ClientTransferView() {
		setTitle("Client");
		labelHost = new JLabel("Host:");
		textFieldHost = new JTextField();
		textFieldHost.setText("localhost");

		labelPort = new JLabel("Port:");
		textFieldPort = new JTextField();
		textFieldPort.setText("9900");

		labelUsername = new JLabel("Username:");
		textUsername = new JTextField();

		labelHost.setBounds(20, 20, 50, 25);
		textFieldHost.setBounds(55, 20, 120, 25);

		labelPort.setBounds(190, 20, 50, 25);
		textFieldPort.setBounds(220, 20, 50, 25);

		labelUsername.setBounds(290, 20, 50, 25);
		textUsername.setBounds(342, 20, 106, 25);

		textFieldContenMess = new JTextField();
		textFieldContenMess.setBounds(367, 522, 478, 25);

		btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(848, 522, 87, 25);
		btnSendFile = new JButton("Send");
		btnSendFile.setBounds(937, 522, 87, 25);
		btnNewMess = new JButton("New Mess");
		btnNewMess.setBounds(10, 65, 349, 33);

		textAreaResult = new JTextArea();
		textNameUserReceive = new JTextField();
		textNameUserReceive.setBounds(368, 65, 106, 25);
		textNameUserReceive.setEnabled(false);

		scrollableTextArea = new JScrollPane(textAreaResult);
		scrollableTextArea.setBounds(369, 100, 655, 412);

		panelMessChat = new JPanel(new GridLayout(0, 1));
		BoxLayout boxlayout = new BoxLayout(panelMessChat, BoxLayout.Y_AXIS);
		panelMessChat.setLayout(boxlayout);

		scrollListChat = new JScrollPane(panelMessChat);
		scrollListChat.setBounds(10, 100, 347, 447);

		getContentPane().add(labelHost);
		getContentPane().add(textFieldHost);
		getContentPane().add(labelPort);
		getContentPane().add(textFieldPort);
		getContentPane().add(textFieldContenMess);
		getContentPane().add(btnBrowse);
		getContentPane().add(btnSendFile);
		getContentPane().add(scrollableTextArea);
		getContentPane().add(labelUsername);
		getContentPane().add(textUsername);
		getContentPane().add(textNameUserReceive);
		getContentPane().add(scrollListChat);
		getContentPane().add(btnNewMess);
		getContentPane().setLayout(null);

		setSize(1059, 619);
		setVisible(true);
		setResizable(false);
		// thoát chương trình khi tắt window
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void chooseFile() {
		final JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(this);
		try {
			if (fc.getSelectedFile() != null) {
				textFieldContenMess.setText(fc.getSelectedFile().getPath());
				isSendFile = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUsername() {
		while (true) {
			String input = JOptionPane.showInputDialog(getContentPane(), "nhập user của bạn");
			if (input != null && !input.isEmpty()) {
				return input;
			}
		}
	}

	public JLabel getLabelHost() {
		return labelHost;
	}

	public void setLabelHost(JLabel labelHost) {
		this.labelHost = labelHost;
	}

	public JTextField getTextFieldHost() {
		return textFieldHost;
	}

	public void setTextFieldHost(JTextField textFieldHost) {
		this.textFieldHost = textFieldHost;
	}

	public JLabel getLabelPort() {
		return labelPort;
	}

	public void setLabelPort(JLabel labelPort) {
		this.labelPort = labelPort;
	}

	public JTextField getTextFieldPort() {
		return textFieldPort;
	}

	public void setTextFieldPort(JTextField textFieldPort) {
		this.textFieldPort = textFieldPort;
	}

	public JButton getBtnBrowse() {
		return btnBrowse;
	}

	public void setBtnBrowse(JButton btnBrowse) {
		this.btnBrowse = btnBrowse;
	}

	public JButton getBtnSendFile() {
		return btnSendFile;
	}

	public void setBtnSendFile(JButton btnSendFile) {
		this.btnSendFile = btnSendFile;
	}

	public JTextArea getTextAreaResult() {
		return textAreaResult;
	}

	public void setTextAreaResult(JTextArea textAreaResult) {
		this.textAreaResult = textAreaResult;
	}

	public boolean isSendFile() {
		return isSendFile;
	}

	public void setSendFile(boolean isSendFile) {
		this.isSendFile = isSendFile;
	}
}