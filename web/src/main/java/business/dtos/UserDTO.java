package business.dtos;

import business.enums.UserRole;

public class UserDTO {
	String email;
	String password;
	UserRole role;
	
	public UserDTO(String email, String password, UserRole role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public String toString() {
		return "Email: " + this.getEmail() + "; password: " + this.getPassword() + "; role: " + this.getRole().name();
	}
	
}