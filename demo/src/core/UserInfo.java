package core;

import java.net.Socket;

public class UserInfo {
	private Socket socket;
	private String username;
	public UserInfo(Socket socket,String username) {
		this.socket = socket;
		this.username = username;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
