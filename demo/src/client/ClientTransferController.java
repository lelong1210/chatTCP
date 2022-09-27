package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import core.MessInfo;

public class ClientTransferController implements ActionListener {
	private ClientTransferView view;
	private TCPClient tcpClient;

	public ClientTransferController(ClientTransferView view) {
		this.view = view;
		view.getBtnBrowse().addActionListener(this);
		view.getBtnSendFile().addActionListener(this);
		view.getBtnNewMess().addActionListener(this);

		String host = view.getTextFieldHost().getText().trim();
		int port = Integer.parseInt(view.getTextFieldPort().getText().trim());
		view.getTextUsername().setText(view.getUsername());
		tcpClient = new TCPClient(host, port, view.getTextAreaResult(), view.getPanelMessChat(),
				view.getTextUsername().getText(), view.getTextNameUserReceive());
		tcpClient.connectServer();

		Thread thread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						tcpClient.getMess();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		};
		thread.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(view.getBtnNewMess().getText())) {
			view.getTextAreaResult().setText("");
			view.getTextNameUserReceive().setText("");
			String input = JOptionPane.showInputDialog(view.getContentPane(), "nhập user bạn muốn chat");
			view.getTextNameUserReceive().setText(input);
		}

		if (e.getActionCommand().equals(view.getBtnBrowse().getText())) {
			view.chooseFile();
		}
		if (e.getActionCommand().equals(view.getBtnSendFile().getText())) {
			if (view.getTextNameUserReceive().getText().isEmpty()) {
				String input = JOptionPane.showInputDialog(view.getContentPane(), "nhập user bạn muốn chat");
				view.getTextNameUserReceive().setText(input);
				view.getTextAreaResult().setText("");
			} else {
				if (view.isSendFile()) {
					String sourceFilePath = view.getTextFieldContenMess().getText();
					String destinationDir = "D:\\Code\\Code_Java\\ChatTCP\\Server\\";
					MessInfo messInfo = new MessInfo(view.getTextUsername().getText(),
							view.getTextNameUserReceive().getText(), view.getTextFieldContenMess().getText(), null);
					tcpClient.sendFile(sourceFilePath, destinationDir, messInfo);
					view.setSendFile(false);
				} else {
					MessInfo messInfo = new MessInfo(view.getTextUsername().getText(),
							view.getTextNameUserReceive().getText(), view.getTextFieldContenMess().getText(), null);
					this.tcpClient.sendMess(messInfo);
				}
				view.getTextFieldContenMess().setText("");
			}

		}
	}
}