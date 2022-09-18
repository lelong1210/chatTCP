package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientTransferController implements ActionListener {
	private ClientTransferView view;
	private TCPClient tcpClient;

	public ClientTransferController(ClientTransferView view) {
		this.view = view;
		view.getBtnBrowse().addActionListener(this);
		view.getBtnSendFile().addActionListener(this);
		view.getBtnConnectToServer().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(view.getBtnBrowse().getText())) {
			view.chooseFile();
		}
		if (e.getActionCommand().equals(view.getBtnConnectToServer().getText())) {
			String host = view.getTextFieldHost().getText().trim();
			int port = Integer.parseInt(view.getTextFieldPort().getText().trim());
			tcpClient = new TCPClient(host, port, view.getTextAreaResult());
			tcpClient.connectServer();
			
			Thread thread = new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							tcpClient.getMess();
						}catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			};
			thread.start();
		}
		if (e.getActionCommand().equals(view.getBtnSendFile().getText())) {
			this.tcpClient.sendMess(view.getTextFieldFilePath().getText());
		}
	}
}
/*
 * if (host != "" && sourceFilePath != "") { // định nghĩa thư mục đích trên
 * server //String destinationDir = "D:\\Code\\Code_Java\\ChatTCP\\Server\\";
 * 
 * String sourceFilePath = view.getTextFieldFilePath().getText();
 * 
 * //tcpClient.sendFile(sourceFilePath, destinationDir); tcpClient.sendMess();
 * tcpClient.closeSocket(); } else { JOptionPane.showMessageDialog(view,
 * "Host, Port " + "và FilePath phải khác rỗng."); } //
 * System.out.println("in sao"); // String host =
 * view.getTextFieldHost().getText().trim(); // int port =
 * Integer.parseInt(view.getTextFieldPort().getText().trim()); // this.tcpClient
 * = new TCPClient(host, port, view.getTextAreaResult()); //
 * this.tcpClient.connectServer(); // this.tcpClient.getMess();
 */