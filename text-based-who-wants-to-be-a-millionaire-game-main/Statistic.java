

public class Statistic {
	private String constestant;
	private String correctlyAns;
	private String badlyAns;
	private String ageAve;
	private String city;
	
	public Statistic(String inputCons,String inputcorrectlyAns, String inputbadlyAns,String inputageAve,String inputcity) {
		constestant=inputCons;
		correctlyAns=inputcorrectlyAns;
		badlyAns=inputbadlyAns;
		ageAve=inputageAve;
		city=inputcity;
	}
	
	
	
	public String getConstestant() {
		return constestant;
	}
	public void setConstestant(String constestant) {
		this.constestant = constestant;
	}
	public String getCorrectlyAns() {
		return correctlyAns;
	}
	public void setCorrectlyAns(String correctlyAns) {
		this.correctlyAns = correctlyAns;
	}
	public String getBadlyAns() {
		return badlyAns;
	}
	public void setBadlyAns(String badlyAns) {
		this.badlyAns = badlyAns;
	}
	public String getAgeAve(int l30,int g30,int g50) {
		System.out.println("<=30 "+(l30/5.0)+"     30< "+(g30/5.0)+"     50< "+(g50/5.0));
		return ageAve;
	}
	public void setAgeAve(String ageAve) {
		this.ageAve = ageAve;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	

	

}
