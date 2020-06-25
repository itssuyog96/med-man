package com.thewire.med.security.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable
{
	private static final long serialVerisonUID = 4408418647685225829L;
	private String uid;
	private String name;
	private String email;
	private boolean isPhoneVerified;
	private String issuer;
	private String picture;
}
