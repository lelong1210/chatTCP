package server;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerTransferView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton btnBrowse;
    private JTextField textFieldFilePath;
    private JButton btnSendFile;
    private JTextArea textAreaResult;
    private JButton btnOpenServer;
    
    public ServerTransferView() {
        setTitle("Server");
 
        textFieldFilePath = new JTextField();
        textFieldFilePath.setBounds(20, 262, 348, 25);
        btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(378, 262, 80, 25);
        btnSendFile = new JButton("Send File");
        btnSendFile.setBounds(471, 262, 80, 25);
        
        textAreaResult = new JTextArea();
        textAreaResult.setBounds(20, 76, 531, 168);
        getContentPane().add(textFieldFilePath);
        getContentPane().add(btnBrowse);
        getContentPane().add(btnSendFile);
        getContentPane().add(textAreaResult);
 
        getContentPane().setLayout(null);
        
        btnOpenServer = new JButton("Open Server");
        btnOpenServer.setBounds(20, 27, 120, 21);
        getContentPane().add(btnOpenServer);
        setSize(629, 399);
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
	public JButton getBtnOpenServer() {
		return btnOpenServer;
	}
	public void setBtnOpenServer(JButton btnOpenServer) {
		this.btnOpenServer = btnOpenServer;
	}
}