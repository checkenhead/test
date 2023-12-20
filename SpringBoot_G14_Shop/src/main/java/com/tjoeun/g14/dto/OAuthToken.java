package com.tjoeun.g14.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuthToken {
	private String access_token;
	private String toten_type;
	private String refresh_token;
	private String expires_in;
	private String scope;
	private String refresh_token_expires_in;
}
