package com.techolution.user.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techolution.user.model.audit.UserDateAudit;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "geo")
public class Geo extends UserDateAudit {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "lat")
	private String lat;

	@Column(name = "lng")
	private String lng;

	@OneToOne(mappedBy = "geo")
	private Address address;
}
