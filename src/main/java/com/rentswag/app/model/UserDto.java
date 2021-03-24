package com.rentswag.app.model;

public class UserDto {
    
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String cname;
    private String verificationCode;
    
    private boolean enabled;
    private int  id;
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    
    
    
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public User getUserFromDto(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCname(cname);
        user.setAddress(address);
        user.setVerificationCode(verificationCode);;
        user.setEnabled(enabled);
        
        return user;
    }
    
}