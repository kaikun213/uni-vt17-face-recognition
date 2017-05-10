package com.faceRecognition.utils.database.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

@Entity
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
	private String username;
	
	@NotNull
	@NonNull
	@Getter
	@Setter
	private String password;
}  