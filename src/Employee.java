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
     * method to set name
     * 
     * @param name
     */ 
    public void setName(String name) {
        this.name = name;
    }

	
    /**
     * method to set work id
     * @param workId
     */
    public void setWorkId(String workId) {
        this.workId = workId;
    }

	
    /**
     * method to set password (no return type)
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

	
    /**
     * method to return name
     * 
     * @return String
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * method to get work id 
     * @return String
     */
    public String getWorkId() {
        return workId;
    }
    
    
    /**
     * Show password
     * @return String
     */
    public String getPassword() {
        return password;
    }
}
