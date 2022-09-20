package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

import core.FileInfo;
import core.MessInfo;

public class TCPClient {
	// create Socket object
	private Socket client;
	private String host;
	private int port;
	private DataOutputStream outToServer;
	private DataInputStream inToServer;
	private JTextArea textAreaLog;
	private ObjectOutputStream oos=null;
	private String username;

	public TCPClient(String host, int port, JTextArea textAreaLog,String username) {
		this.host = host;
		this.port = port;
		this.textAreaLog = textAreaLog;
		this.username = username;
		
	}

	public void connectServer() {
		try {
			this.client = new Socket(host, port);
			this.outToServer = new DataOutputStream(client.getOutputStream());
			this.inToServer = new DataInputStream(client.getInputStream());
			textAreaLog.append("connected to server.\n");
			this.outToServer.writeUTF(this.username);
			
			this.oos = new ObjectOutputStream(client.getOutputStream());

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendFile(String sourceFilePath, String destinationDir) {
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
				textAreaLog.append("send file to server " + fileInfo.getStatus() + "\n");
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

	public void getMess() {
		try {
			ObjectInputStream ois =  new ObjectInputStream(client.getInputStream());
			MessInfo messInfo = (MessInfo) ois.readObject();
			this.textAreaLog.append("\n" + "("+messInfo.getUserSource()+")"+messInfo.getMessContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendMess(MessInfo mess) {
		try {
			// make greeting
			this.oos.writeObject(mess);
			textAreaLog.append("\n" + this.client.getLocalPort()+"(me)==>"+mess.getMessContent());
		} catch (IOException ex) {
			ex.printStackTrace();
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