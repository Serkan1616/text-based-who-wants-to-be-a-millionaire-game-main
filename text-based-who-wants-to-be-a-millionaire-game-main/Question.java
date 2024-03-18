

public class Question {
	private String category;
	private String question;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private String answer;
	private String difficulty;
	 
	public Question(String inputCat,String inputQue,String inputCho1,String inputCho2,String inputCho3,String inputCho4,String inputAns,String inputDiff) {
		category=inputCat;
		question=inputQue;
		choice1=inputCho1;
		choice2=inputCho2;
		choice3=inputCho3;
		choice4=inputCho4;
		answer=inputAns;
		difficulty=inputDiff;											
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}


	public String getChoice1() {
		return choice1;
	}

	public void setChoice1(String choice1) {
		this.choice1 = choice1;
	}

	public String getChoice2() {
		return choice2;
	}

	public void setChoice2(String choice2) {
		this.choice2 = choice2;
	}

	public String getChoice3() {
		return choice3;
	}

	public void setChoice3(String choice3) {
		this.choice3 = choice3;
	}

	public String getChoice4() {
		return choice4;
	}

	public void setChoice4(String choice4) {
		this.choice4 = choice4;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	
	public String wordCloud(String input) {
		
		return input;
	}
	
	

}