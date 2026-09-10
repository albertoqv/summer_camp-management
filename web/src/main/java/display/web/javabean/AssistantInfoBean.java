package display.web.javabean;


public class AssistantInfoBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int dni;
	private String firstName;
	private String lastName;
	private String birthDate;
	private boolean requireSpecialAttention;
	
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public boolean isRequireSpecialAttention() {
		return requireSpecialAttention;
	}
	public void setRequireSpecialAttention(boolean requireSpecialAttention) {
		this.requireSpecialAttention = requireSpecialAttention;
	}
}