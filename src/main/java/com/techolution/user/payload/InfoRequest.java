package com.techolution.user.payload;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class InfoRequest {

	@NotBlank
	private String street;

	@NotBlank
	private String suite;

	@NotBlank
	private String city;

	@NotBlank
	private String zipcode;

	private String companyName;

	private String catchPhrase;

	private String bs;

	private String website;

	private String phone;

	private String lat;

	private String lng;
}
