public class Employee {

    private String name = "";
    private String workId = "";
    private String password = "";

    public Employee(String name, String workId, String password) {
        setName(name);
        setWorkId(workId);
        setPassword(password);
    }
	//method to set name 
    public void setName(String name) {
        this.name = name;
    }

	//method to set work id 
    public void setWorkId(String workId) {
        this.workId = workId;
    }

	//method to set password (no return type)
    public void setPassword(String password) {
        this.password = password;
    }

	//method to return name
    public String getName() {
        return name;
    }
    
    //method to get work id 
    public String getWorkId() {
        return workId;
    }
    
    //Show password
    public String getPassword() {
        return password;
    }
}
