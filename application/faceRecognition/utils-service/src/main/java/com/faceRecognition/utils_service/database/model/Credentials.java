package com.faceRecognition.utils_service.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = { "username" }, callSuper = false)
@ToString
@Table(name = "credentials")
public class Credentials {

	@Id
	@NotNull
	@NonNull
	@Getter
	@Setter
	@Column(name = "username")
	@Length(max = 20)
	private String username;

	@NotNull
	@NonNull
	@Getter
	@Setter
	@Column(name = "password")
	@Length(max = 20)
	private String password;
}