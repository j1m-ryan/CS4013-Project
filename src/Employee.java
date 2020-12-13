public class Employee {

    private String name = "";
    private String workId = "";
    private String password = "";

    public Employee(String name, String workId, String password) {
        setName(name);
        setWorkId(workId);
        setPassword(password);
    }
	
    /** 
     * @param name
     */
    //method to set name 
    public void setName(String name) {
        this.name = name;
    }

	
    /** 
     * @param workId
     */
    //method to set work id 
    public void setWorkId(String workId) {
        this.workId = workId;
    }

	
    /** 
     * @param password
     */
    //method to set password (no return type)
    public void setPassword(String password) {
        this.password = password;
    }

	
    /** 
     * @return String
     */
    //method to return name
    public String getName() {
        return name;
    }
    
    
    /** 
     * @return String
     */
    //method to get work id 
    public String getWorkId() {
        return workId;
    }
    
    
    /** 
     * @return String
     */
    //Show password
    public String getPassword() {
        return password;
    }
}
