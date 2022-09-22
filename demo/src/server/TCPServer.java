package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
								textAreaLog.append("\n" + messInfo.getMessContent());
								System.out.println("Server send from " + userInfo.getUsername() + "to "
										+ messInfo.getUserDes() + " with content: " + messInfo.getMessContent());

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

	public void sendFile(String sourceFilePath, String destinationDir, Socket client) {
		DataOutputStream outToServer = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		try {
			// make greeting
			outToServer = new DataOutputStream(client.getOutputStream());
			outToServer.writeUTF("Hello from " + client.getLocalSocketAddress());

			// get file info
			FileInfo fileInfo = getFileInfo(sourceFilePath, destinationDir);

			// send file
			oos = new ObjectOutputStream(client.getOutputStream());
			oos.writeObject(fileInfo);

			// get confirmation
			ois = new ObjectInputStream(client.getInputStream());
			fileInfo = (FileInfo) ois.readObject();
			if (fileInfo != null) {
				textAreaLog.append("send file to client " + fileInfo.getStatus() + "\n");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// close all stream
			closeStream(oos);
			closeStream(ois);
			closeStream(outToServer);
		}
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

}