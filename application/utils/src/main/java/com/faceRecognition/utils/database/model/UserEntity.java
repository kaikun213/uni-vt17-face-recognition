package com.faceRecognition.utils.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "userEntity")
public class UserEntity {

	@Id
	@NotNull
	@GeneratedValue
	private long id;

	@NotNull
	private long personalNumber;

	@NotNull
	@Length(max = 1024)	
	private String photoLink;

	public UserEntity() {

	}

	public UserEntity(Long personalNumber, String photoLink) {
		this.personalNumber = personalNumber;
		this.photoLink = photoLink;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(long personalNumber) {
		this.personalNumber = personalNumber;
	}

	public String getPhotoLink() {
		return photoLink;
	}

	public void setPhotoLink(String photoLink) {
		this.photoLink = photoLink;
	}

	@Override
	public String toString() {
		return "Id: " + id + ", PersonalNumber: " + personalNumber + ", PhotoLink: " + photoLink;
	}
}