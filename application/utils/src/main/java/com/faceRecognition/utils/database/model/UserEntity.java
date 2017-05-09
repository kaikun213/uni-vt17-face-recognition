package com.faceRecognition.utils.database.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@ToString
@Table(name = "userEntity")
public class UserEntity {

	@Id
	@NotNull
	@GeneratedValue
	@Getter
	private Long id;

	@NotNull
	@NonNull
	@Getter
	@Setter
	private Long personalNumber;

	@NotNull
	@NonNull
	@Getter
	@Setter
	@Length(max = 1024)	
	private String photoLink;
}