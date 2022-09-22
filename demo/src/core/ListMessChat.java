package core;

import javax.swing.JLabel;

public class ListMessChat {
	private String username;
	private String messLast;
	private String allMess;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessLast() {
		return messLast;
	}
	public void setMessLast(String messLast) {
		this.messLast = messLast;
	}
	public JLabel getLabelOfUsername() {
		return labelOfUsername;
	}
	public void setLabelOfUsername(JLabel labelOfUsername) {
		this.labelOfUsername = labelOfUsername;
	}
	private JLabel labelOfUsername;
	public ListMessChat(String username,String messLast,String allMess,JLabel labelOfUsername) {
		this.username = username;
		this.messLast = messLast;
		this.labelOfUsername = labelOfUsername;
		this.allMess = allMess;
	}
	public String getAllMess() {
		return allMess;
	}
	public void setAllMess(String allMess) {
		this.allMess = allMess;
	}
}
