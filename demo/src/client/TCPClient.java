package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import core.FileInfo;
import core.ListMessChat;
import core.MessInfo;

public class TCPClient {
	// create Socket object
	private Socket client;
	private String host;
	private int port;
	private DataOutputStream outToServer;
	private JTextArea textAreaLog;
	private JPanel PanelMessChat;
	private ObjectOutputStream oos = null;
	private String username;
	private JTextField textNameUserReceive;
	private ArrayList<ListMessChat> listUserChat = new ArrayList<ListMessChat>();

	public TCPClient(String host, int port, JTextArea textAreaLog, JPanel PanelMessChat, String username,JTextField textNameUserReceive) {
		this.host = host;
		this.port = port;
		this.textAreaLog = textAreaLog;
		this.username = username;
		this.PanelMessChat = PanelMessChat;
		this.textNameUserReceive = textNameUserReceive;
	}

	public void connectServer() {
		try {
			this.client = new Socket(host, port);
			this.outToServer = new DataOutputStream(client.getOutputStream());
			textAreaLog.append("connected to server.\n");
			this.outToServer.writeUTF(this.username);
			this.oos = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendFile(String sourceFilePath, String destinationDir,MessInfo messInfo) {
		try {
			// get file info
			FileInfo fileInfo = getFileInfo(sourceFilePath, destinationDir);

			// send file
			messInfo.setFileInfo(fileInfo);
			System.out.println("messInfo Client: "+messInfo.getFileInfo().getFilename());
			this.oos.writeObject(messInfo);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void getMess() {
		try {
			boolean isUserTrue = false; 
			ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
			MessInfo messInfo = (MessInfo) ois.readObject();
			if(messInfo.getFileInfo() != null) {
				createFile(messInfo.getFileInfo());
			}
			
			
			String onMess = "( " + messInfo.getUserSource() + " )" + messInfo.getMessContent()+"\n";			
			
			JLabel labelusername = new JLabel("[ "+messInfo.getUserSource()+" ]");
			labelusername.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					textNameUserReceive.setText(messInfo.getUserSource());
					for (ListMessChat listMessChat : listUserChat) {
						if(listMessChat.getUsername().equals(messInfo.getUserSource())) {
							ListMessChat listMess = listUserChat.get(listUserChat.indexOf(listMessChat));
							textAreaLog.setText(listMess.getAllMess());
							break;
						}
					}
				}
			});	
		
			for (ListMessChat listMessChat : listUserChat) {
				if(listMessChat.getUsername().equals(messInfo.getUserSource())) {
					String allMess =listMessChat.getAllMess();
					
					listUserChat.remove(listUserChat.indexOf(listMessChat));
					listUserChat.add(0,new ListMessChat(messInfo.getUserSource(),messInfo.getMessContent(),allMess + onMess, labelusername));
					isUserTrue = true;
					break;
				}
			}
			if(!isUserTrue) {
				String allMess ="";
				allMess = allMess + onMess;
				listUserChat.add(0,new ListMessChat(messInfo.getUserSource(),messInfo.getMessContent(),allMess, labelusername));
			}
			this.PanelMessChat.removeAll();
			for (ListMessChat listMessChat : listUserChat) {
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				panel.add(listMessChat.getLabelOfUsername(), BorderLayout.WEST);
				panel.add(new JLabel(" : " + listMessChat.getMessLast()), BorderLayout.CENTER);
				panel.setPreferredSize(new Dimension(30, 30));
				this.PanelMessChat.add(panel);
				this.PanelMessChat.revalidate();
				this.PanelMessChat.repaint();
			}
			int height = (int) this.PanelMessChat.getPreferredSize().getHeight();
			Rectangle rect = new Rectangle(0, height, 20, 20);
			this.PanelMessChat.scrollRectToVisible(rect);
			
			if(this.textNameUserReceive.getText().equals(messInfo.getUserSource())) {
				this.textAreaLog.append("( " + messInfo.getUserSource() + " )" + messInfo.getMessContent()+"\n");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendMess(MessInfo messInfo) {
		try {
			this.oos.writeObject(messInfo);
			textAreaLog.append("(me)==>" + messInfo.getMessContent()+"\n");
//			
			boolean isUserTrue = false; 
			JLabel labelusername = new JLabel("[ "+messInfo.getUserDes()+" ]");
			labelusername.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					textNameUserReceive.setText(messInfo.getUserDes());
					for (ListMessChat listMessChat : listUserChat) {
						if(listMessChat.getUsername().equals(messInfo.getUserDes())) {
							ListMessChat listMess = listUserChat.get(listUserChat.indexOf(listMessChat));
							textAreaLog.setText(listMess.getAllMess());
						}
					}
				}
			});
			for (ListMessChat listMessChat : listUserChat) {
				if(listMessChat.getUsername().equals(messInfo.getUserDes())) {
					
					ListMessChat listUserTmp = (ListMessChat)listMessChat;
					listUserTmp.setMessLast("(me)==>" +messInfo.getMessContent());
					listUserTmp.setAllMess(listUserTmp.getAllMess()+listUserTmp.getMessLast()+"\n");
					
					listUserChat.remove(listUserChat.indexOf(listMessChat));
					listUserChat.add(0,listUserTmp);
					isUserTrue=true;
					break;
				}
			}
			
			if(!isUserTrue) {
				listUserChat.add(0,new ListMessChat(messInfo.getUserDes(),messInfo.getMessContent(),"(me)==>"+messInfo.getMessContent()+"\n", labelusername));
			}
			System.out.println("----------------------------send--------------------------");
			for (ListMessChat listMessChat : listUserChat) {
				System.out.println("["+this.username+"]array ==> "+listMessChat.getUsername()+" ===> "+listMessChat.getMessLast());
			}
			this.PanelMessChat.removeAll();
			for (ListMessChat listMessChat : listUserChat) {
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				panel.add(listMessChat.getLabelOfUsername(), BorderLayout.WEST);
				panel.add(new JLabel(" : " + listMessChat.getMessLast()), BorderLayout.CENTER);
				panel.setPreferredSize(new Dimension(30, 30));
				this.PanelMessChat.add(panel);
				this.PanelMessChat.revalidate();
				this.PanelMessChat.repaint();
			}
			int height = (int) this.PanelMessChat.getPreferredSize().getHeight();
			Rectangle rect = new Rectangle(0, height, 20, 20);
			this.PanelMessChat.scrollRectToVisible(rect);
			

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	private boolean createFile(FileInfo fileInfo) {
		BufferedOutputStream bos = null;

		try {
			if (fileInfo != null) {
				File fileReceive = new File(fileInfo.getDestinationDirectory() + fileInfo.getFilename());
				bos = new BufferedOutputStream(new FileOutputStream(fileReceive));
				// write file content
				bos.write(fileInfo.getDataBytes());
				bos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeStream(bos);
		}
		return true;
	}
	private FileInfo getFileInfo(String sourceFilePath, String destinationDir) {
		FileInfo fileInfo = null;
		BufferedInputStream bis = null;
		try {
			File sourceFile = new File(sourceFilePath);
			bis = new BufferedInputStream(new FileInputStream(sourceFile));
			fileInfo = new FileInfo();
			byte[] fileBytes = new byte[(int) sourceFile.length()];
			// get file info
			bis.read(fileBytes, 0, fileBytes.length);
			fileInfo.setFilename(sourceFile.getName());
			fileInfo.setDataBytes(fileBytes);
			fileInfo.setDestinationDirectory(destinationDir);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			closeStream(bis);
		}
		return fileInfo;
	}

	public void closeSocket() {
		try {
			if (client != null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeStream(InputStream inputStream) {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void closeStream(OutputStream outputStream) {
		try {
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}