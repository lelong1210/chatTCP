package server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

import core.FileInfo;
import core.MessInfo;
import core.UserInfo;

public class TCPServer extends Thread {
	// create serverSocket object
	private ServerSocket serverSocket;
	private int port = 9900;
	private JTextArea textAreaLog;
	private ArrayList<UserInfo> arrayListSocket = new ArrayList<UserInfo>();

	public TCPServer(JTextArea textAreaLog) {
		this.textAreaLog = textAreaLog;
	}

	public void open() {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("server is open on port " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (true) {
				// accept connect from client and create Socket object
				Socket client = serverSocket.accept();
				// get user
				DataInputStream inFromClient = new DataInputStream(client.getInputStream());
				String messIn = inFromClient.readUTF();
				// create userInfo (login)
				UserInfo userInfo = new UserInfo(client, messIn);
				arrayListSocket.add(userInfo);
				// get mess info
				Thread threadInFromClient = new Thread() {
					private ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
					private ObjectOutputStream oos = null;

					@Override
					public void run() {
						try {
							// get input from client
							while (true) {
								MessInfo messInfo = (MessInfo) ois.readObject();
								textAreaLog.append("\n Server send from " + userInfo.getUsername() + " to "
										+ messInfo.getUserDes() + " with content: " + messInfo.getMessContent());

								// check send file
								if (messInfo.getFileInfo() != null) {
									FileInfo fileInfo = (FileInfo) messInfo.getFileInfo();
									createFile(fileInfo);
									fileInfo.setDestinationDirectory("D:\\Code\\Code_Java\\ChatTCP\\Client\\");
									fileInfo.setSourceDirectory(fileInfo.getDestinationDirectory()+fileInfo.getFilename());
								} 
								
								// find users and send mess
								for (UserInfo userInfo : arrayListSocket) {
									if (userInfo.getUsername().equals(messInfo.getUserDes())) {
										Socket socketOfuserSend = (Socket) userInfo.getSocket();
										oos = new ObjectOutputStream(socketOfuserSend.getOutputStream());
										sendMess(oos, messInfo);
										oos = null;
										break;
									}
								}

							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				};
				threadInFromClient.start();

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void sendMess(ObjectOutputStream oos, MessInfo messInfo) {
		try {
			Thread threadSend = new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						oos.writeObject(messInfo);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			threadSend.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void closeSocket(Socket socket) {
		try {
			if (socket != null) {
				socket.close();
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