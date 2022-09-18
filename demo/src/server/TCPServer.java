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

public class TCPServer extends Thread {
	// create serverSocket object
	private ServerSocket serverSocket;
	private int port = 9900;
	private JTextArea textAreaLog;
	private ArrayList<Socket> arrayListSocket = new ArrayList<Socket>();
	private int x = 1;

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
				if(arrayListSocket.size() < 2) {
					arrayListSocket.add(client);
				}
				
				if(arrayListSocket.size() == 2) {
					Socket client1 = (Socket) arrayListSocket.get(0);
					Socket client2 = (Socket) arrayListSocket.get(1);
					createRoomChat(client1, client2);
				}
				
				System.out.println("connected to " + client.getRemoteSocketAddress());
				

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void createRoomChat(Socket client1,Socket client2) {
			
		Thread threadInFromClient = new Thread() {
			@Override
			public void run() {
				try {
					while(true) {
						// get input from client
						DataInputStream inFromClient = new DataInputStream(client1.getInputStream());
						String messIn = inFromClient.readUTF();
						textAreaLog.append("\n"+messIn);
						
						DataOutputStream outToClient = new DataOutputStream(client2.getOutputStream());
						outToClient.writeUTF(messIn);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};
		Thread threadOutToClient = new Thread() {
			@Override
			public void run() {
				try {
					while(true) {
						// get input from client
						DataInputStream inFromClient = new DataInputStream(client2.getInputStream());
						String messIn = inFromClient.readUTF();
						textAreaLog.append("\n"+messIn);
						
						DataOutputStream outToClient = new DataOutputStream(client1.getOutputStream());
						outToClient.writeUTF(messIn);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		};
		threadInFromClient.start();
		threadOutToClient.start();
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

	public void sendMess() {
		DataOutputStream outToClient = null;
		DataInputStream inToClient = null;

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

	public ArrayList<Socket> getArrayListSocket() {
		return arrayListSocket;
	}

	public void setArrayListSocket(ArrayList<Socket> arrayListSocket) {
		this.arrayListSocket = arrayListSocket;
	}
}
/*
 * try { while(true) { // make greeting outToClient = new
 * DataOutputStream(client.getOutputStream());
 * outToClient.writeUTF("Hello from " + client.getLocalSocketAddress());
 * 
 * // get mess inToClient = new DataInputStream(client.getInputStream());
 * System.out.println((String)inToClient.readUTF()); String messOld =
 * this.textAreaLog.getText(); String mess = (String)inToClient.readUTF();
 * 
 * this.textAreaLog.setText(messOld+"\n"+mess); }
 * 
 * } catch (IOException ex) { ex.printStackTrace(); } finally { // close all
 * stream closeStream(outToClient); closeStream(inToClient);
 * 
 * 
 * 
 * // // receive file info // ois = new
 * ObjectInputStream(server.getInputStream()); // FileInfo fileInfo = (FileInfo)
 * ois.readObject(); // if (fileInfo != null) { // createFile(fileInfo); // } //
 * // // confirm that file is received // oos = new
 * ObjectOutputStream(server.getOutputStream()); //
 * fileInfo.setStatus("success"); // fileInfo.setDataBytes(null); //
 * oos.writeObject(fileInfo);
 * 
 * // catch (ClassNotFoundException e) { // e.printStackTrace(); // } }
 */