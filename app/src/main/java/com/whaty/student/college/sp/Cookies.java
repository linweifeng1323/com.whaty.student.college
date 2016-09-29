package com.whaty.student.college.sp;

/**
 * 登录信息
 */
public class Cookies {
	
	private String userid;
	
	private String username; 
	
	private String account;
	
	private String tempmd5;
	
	private String logo;
	
	private String level;
	
	private String point;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTempmd5() {
		return tempmd5;
	}

	public void setTempmd5(String tempmd5) {
		this.tempmd5 = tempmd5;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Cookies [userid=" + userid + ", username=" + username
				+ ", tempmd5=" + tempmd5 + ", logo=" + logo + ", level="
				+ level + ", point=" + point + "]";
	}
	
}
