package com.egate.ecommerce.dto;

import lombok.Data;

@Data
public class SignupReqest {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
