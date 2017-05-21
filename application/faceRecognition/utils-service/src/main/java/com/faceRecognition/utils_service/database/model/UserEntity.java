package com.faceRecognition.utils_service.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString
@Table(name = "userentities")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	@Setter
	@Column(name = "id")
	//@Length(max = 1024)
	private Long id;

	@NotNull
	@NonNull
	@Getter
	@Setter
	@Column(name = "personalnumber")
	@Length(max = 12)
	private String personalNumber;

	@NotNull
	@NonNull
	@Getter
	@Setter
	@Column(name = "photolink")
	@Length(max = 1024)
	private String photoLink;
}