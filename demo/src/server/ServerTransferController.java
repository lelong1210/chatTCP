package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerTransferController implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.getBtnOpenServer().getText())) {
          TCPServer tcpServer = new TCPServer();
          tcpServer.open();
          tcpServer.start();
        }
	}
    private ServerTransferView view;
    
    public ServerTransferController(ServerTransferView view) {
        this.view = view;
        view.getBtnBrowse().addActionListener(this);
        view.getBtnSendFile().addActionListener(this);
        view.getBtnOpenServer().addActionListener(this);
        
    }
}
