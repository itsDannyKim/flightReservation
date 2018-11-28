
public abstract class UserType {
	
	public String Username = "";
	public String Password;
	public String FirstName;
	public String LastName;
	public String StreetAddress;
	public String City;
	public String State;
	public int Zipcode;
	public String Email;
	public int SocialSecurity;
	
	public UserType(){
		
		this.Username = Username;
		this.Password = Password;	
		
	}
	
	/*(public static boolean Login(String Username, String Password) {
		
		if(Username = db.username && Password = db.password) {
			return true;
		}
		
	}*/
	
	public void Registration(String Username, String Password, String FirstName, 
			String LastName, String StreetAddress,
			String City, String State, int Zipcode, String Email, int SocialSecurity) {
		
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

	public int getZipcode() {
		return Zipcode;
	}

	public void setZipcode(int zipcode) {
		Zipcode = zipcode;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getSocialSecurity() {
		return SocialSecurity;
	}

	public void setSocialSecurity(int socialSecurity) {
		SocialSecurity = socialSecurity;
	}
	
	
	

}

