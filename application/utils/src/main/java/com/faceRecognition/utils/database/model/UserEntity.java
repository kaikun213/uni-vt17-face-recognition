package com.faceRecognition.utils.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString
@Table(name = "user_entity")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	@Setter
	@Column(name = "id")
	@Length(max = 1024)
	private String id;

	@NotNull
	@NonNull
	@Getter
	@Setter
	@Column(name = "personal_number")
	@Length(max = 12)
	private String personalNumber;

	@NotNull
	@NonNull
	@Getter
	@Setter
	@Column(name = "photo_link")
	@Length(max = 1024)
	private String photoLink;
	
	public UserEntity(String personalNumber, String photoLink) {
		super();
		this.personalNumber = personalNumber;
		this.photoLink = photoLink;
	}
}