package client;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientTransferView extends JFrame {
    private static final long serialVersionUID = 1L;
 
    private JLabel labelHost;
    private JTextField textFieldHost;
    private JLabel labelPort;
    private JTextField textFieldPort;
    private JLabel labelUsername;
    private JTextField textUsername;
    private JButton btnBrowse;
    private JTextField textFieldFilePath;
    private JButton btnSendFile;
    private JTextArea textAreaResult;
    private JTextField textNameUserReceive;
 


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
 
        textFieldFilePath = new JTextField();
        textFieldFilePath.setBounds(20, 368, 477, 25);
        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(507, 368, 80, 25);
        btnSendFile = new JButton("Send File");
        btnSendFile.setBounds(597, 368, 80, 25);
        textAreaResult = new JTextArea();
        textAreaResult.setBounds(20, 104, 657, 254);
        textNameUserReceive = new JTextField();
        textNameUserReceive.setBounds(20, 69, 106, 25);
        textNameUserReceive.setEnabled(false);
 
        getContentPane().add(labelHost);
        getContentPane().add(textFieldHost);
        getContentPane().add(labelPort);
        getContentPane().add(textFieldPort);
        getContentPane().add(textFieldFilePath);
        getContentPane().add(btnBrowse);
        getContentPane().add(btnSendFile);
        getContentPane().add(textAreaResult);
        getContentPane().add(labelUsername);
        getContentPane().add(textUsername);
        getContentPane().add(textNameUserReceive);
        getContentPane().setLayout(null);
        
        setSize(731, 470);
        setVisible(true);
        // thoát chương trình khi tắt window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	
    public void chooseFile() {
        final JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(this);
        try {
            if (fc.getSelectedFile() != null) {
                textFieldFilePath.setText(fc.getSelectedFile().getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getUsername() {
    	while(true) {
    		String input = JOptionPane.showInputDialog(getContentPane(), "nhập user của bạn");
    		if(input != null && !input.isEmpty()) {
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
 
    public JTextField getTextFieldFilePath() {
        return textFieldFilePath;
    }
 
    public void setTextFieldFilePath(JTextField textFieldFilePath) {
        this.textFieldFilePath = textFieldFilePath;
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
}