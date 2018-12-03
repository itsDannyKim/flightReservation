package UserTypes;

public abstract class UserType {

	protected String Username = "";
	protected String Password;
	private String FirstName;
	private String LastName;
	private String StreetAddress;
	private String City;
	private String State;
	private String Zipcode;
	private String Email;
	private String SocialSecurity;
	private String SecurityQuestion;
	private String ConfirmPassword;

	public UserType() {


	}

	public void Registration(String Username, String Password, String FirstName, String LastName, String StreetAddress,
			String City, String State, String Zipcode, String Email, String SocialSecurity) {

		this.Username = Username;
		this.Password = Password;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.StreetAddress = StreetAddress;
		this.City = City;
		this.State = State;
		this.Zipcode = Zipcode;
		this.Email = Email;
		this.SocialSecurity = SocialSecurity;

	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getStreetAddress() {
		return StreetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		StreetAddress = streetAddress;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getZipcode() {
		return Zipcode;
	}

	public void setZipcode(String zipcode) {
		Zipcode = zipcode;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getSocialSecurity() {
		return SocialSecurity;
	}

	public void setSocialSecurity(String socialSecurity) {
		SocialSecurity = socialSecurity;
	}

	public String getSecurityQuestion() {
		return SecurityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		SecurityQuestion = securityQuestion;
	}

	public String getConfirmPassword() {
		return ConfirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}
}
