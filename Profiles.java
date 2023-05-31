package bean;

public class Profiles {
	private int id;
	private String username;

	private String userpwd;
	public Profiles() {
		
	}
	public Profiles(String username,String userpwd) {
		this.username=username;
		this.userpwd=userpwd;
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserpwd() {
		return userpwd;
	}
	
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	
	
}
