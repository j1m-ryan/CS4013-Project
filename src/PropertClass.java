


public class PropertyClass {
	private double taxOverdue;
	private String address;
	private double value;
	private String eirCode;
	private String locationCategory;
	private boolean PRP; //Principal Private Residence
	
	
	public PropertyClass() {
		
	}
	
	public PropertyClass(String address,String postCode,double marketValue,String location) {
		this.setAddress(address);
		this.setEirCode(eirCode);
		this.value = marketValue;
		this.locationCategory = location;
		this.setPRP(PRP);
	
	}
	
	public double getTaxOverdue() {
		return taxOverdue;
	}
	public void setTaxOverdue(double taxOverdue) {
		this.taxOverdue = taxOverdue;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getLocationCategory() {
		return locationCategory;
	}
	public void setLocationCategory(String locationCategory) {
		this.locationCategory = locationCategory;
	}
	public boolean isPRP() {
		return PRP;
	}
	public void setPRP(boolean pRP) {
		PRP = pRP;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEirCode() {
		return eirCode;
	}

	public void setEirCode(String eirCode) {
		this.eirCode = eirCode;
	}
	
	
}
