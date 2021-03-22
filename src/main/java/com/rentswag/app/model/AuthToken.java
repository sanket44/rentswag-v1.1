package com.rentswag.app.model;

public class AuthToken {

    private String token;
      private int roles;

	public AuthToken(String token, int roles) {
		this.token = token;
		this.roles = roles;
	}

	public int getRoles() {
		return roles;
	}

	public void setRoles(int roles) {
		this.roles = roles;
	}

    public AuthToken(){

    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
