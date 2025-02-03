package com.springcrud.CurdOparation.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_files")
@NamedQuery(name = "UserFilesTbl.findAll", query = "SELECT m FROM UserFilesTbl m")
public class UserFilesTbl {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private int fileId;

	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_file")
	private String userFile;
	
	@Column(name = "user_id")
	private int userId;
	
	@JsonIgnore
	@OneToMany(mappedBy="userFileIdTbl")
	private List<UserTbl> userTblList;

	public List<UserTbl> getUserTblList() {
		return userTblList;
	}

	public void setUserTblList(List<UserTbl> userTblList) {
		this.userTblList = userTblList;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFile() {
		return userFile;
	}

	public void setUserFile(String userFile) {
		this.userFile = userFile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
