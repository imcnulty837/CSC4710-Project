// Originally the people class, this is now the account class
// We can use this to manage account data within our code 

public class Account {
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String password;
    protected String birthday;
    protected String gender;
 
    public Account() {
    }
 
    public Account(String email) {
        this.email = email;
    }
 
    public Account(String email, String firstName, String lastName, String password) {
        this(firstName, lastName, password);
        this.email = email;
    }
     
    public Account(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
    
    public Account(String email, String firstName, String lastName, String password, String birthday) {
        this(firstName, lastName, password);
        this.email = email;
        this.birthday = birthday;
    }
    
    public Account(String email, String firstName, String lastName, String password, String birthday, String gender) {
        this(firstName, lastName, password);
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
    }
 
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getBirthday() {
    	return birthday;
    }
    public void setBirthday(String birthday) {
    	this.birthday = birthday;
    }
    public String getGender() {
    	return gender;
    }
    public void setGender(String gender) {
    	this.gender = gender;
    }
}