

public class Participant {
	private String name;
	private String birthday;
	private String telNo;
	private String address;
	
	
	public Participant(String inputName,String inputBirth,String inputTel,String inputAddr) {		
		name=inputName;
		birthday=inputBirth;
		telNo=inputTel;
		address=inputAddr;											
	}
	
	
	public String getName() {
		return name;		
	}
	
	public void setName(String inputName) {
		name=inputName;		
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getTelNo() {
		return telNo;
	}


	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

}
