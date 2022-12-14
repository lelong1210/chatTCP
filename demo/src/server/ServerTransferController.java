package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ServerTransferController implements ActionListener {
	private ServerTransferView view;
	private TCPServer tcpServer;
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(view.getBtnOpenServer().getText())) {
          tcpServer = new TCPServer(view.getTextAreaResult());
          tcpServer.open();
          tcpServer.start();
        }
        if(e.getActionCommand().equals(view.getBtnSendFile().getText())) {
//        	tcpServer.sendMess();
        }
	}
    public ServerTransferController(ServerTransferView view) {
        this.view = view;
        view.getBtnBrowse().addActionListener(this);
        view.getBtnSendFile().addActionListener(this);
        view.getBtnOpenServer().addActionListener(this);
        
    }
    
}
