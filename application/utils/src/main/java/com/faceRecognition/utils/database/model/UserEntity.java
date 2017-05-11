package com.faceRecognition.utils.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@RequiredArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString
@Table(name = "userEntity")
public class UserEntity {

	@Id
	@NotNull
	@NonNull
	@Getter
	@Setter
	@Column(name = "id")
	@Length(max = 1024)
	private String id;

	@NotNull
	@NonNull
	@Getter
	@Setter
	@Column(name = "personalNumber")
	@Length(max = 12)
	private String personalNumber;
}