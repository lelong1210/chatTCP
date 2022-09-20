package core;

import java.io.Serializable;

public class MessInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String getUserDes() {
		return userDes;
	}
	public void setUserDes(String userDes) {
		this.userDes = userDes;
	}
	public String getMessContent() {
		return MessContent;
	}
	public void setMessContent(String messContent) {
		MessContent = messContent;
	}
	private String userDes;
	private String MessContent;
	public MessInfo(String userDes,String MessContent) {
		this.userDes = userDes;
		this.MessContent = MessContent;
	}
}
