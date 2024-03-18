


import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import enigma.console.Console;
import enigma.console.TextAttributes;
import enigma.console.TextWindowNotAvailableException;

import java.awt.Color;

public class game {
   public static enigma.console.Console cn = Enigma.getConsole("Mouse and Keyboard");
   public static TextMouseListener tmlis; 
   public static KeyListener klis; 

   // ------ Standard variables for mouse and keyboard ------
   public static int mousepr;          // mouse pressed?
   public static int mousex;   // mouse text coords.
public static int mousey;
   public static int keypr;   // key pressed?
   public static int rkey;    // key   (for press/release)
   // ----------------------------------------------------
   
   Scanner scannerChoice = new Scanner(System.in);
   Scanner scannerFile = new Scanner(System.in);	
   int qColumnCount=8;
   String fileName;
   String stopWords=Files.readString(Path.of("stop_words.txt"));
   String[] stopWordsArr=stopWords.trim().replaceAll("\\s+"," ").replaceAll("\\r|\\n", " ").split(" ");	
   int qRowCount;
   Statistic statistic;
   String quesWords;
	public int less30=0;
	public int greater30=0;
	public int greater50=0;
	public int less30Count=0;
	public int greater30Count=0;
	public int greater50Count=0;
   
   Game() throws Exception {   // --- Contructor
                 
      // ------ Standard code for mouse and keyboard ------ Do not change
      tmlis=new TextMouseListener() {
         public void mouseClicked(TextMouseEvent arg0) {}
         public void mousePressed(TextMouseEvent arg0) {
            if(mousepr==0) {
               mousepr=1;
               mousex=arg0.getX();
               mousey=arg0.getY();
            }
         }
         public void mouseReleased(TextMouseEvent arg0) {}
      };
      cn.getTextWindow().addTextMouseListener(tmlis);
    
      klis=new KeyListener() {
         public void keyTyped(KeyEvent e) {}
         public void keyPressed(KeyEvent e) {
            if(keypr==0) {
               keypr=1;
               rkey=e.getKeyCode();
            }
         }
         public void keyReleased(KeyEvent e) {}
      };
      cn.getTextWindow().addKeyListener(klis);
      // ----------------------------------------------------
      

      
      while(true) {
    	    	
         if(keypr==1) {    // if keyboard button pressed
            if(rkey==KeyEvent.VK_1) 
            {
            	clearScreen();
				System.out.print("Enter file name to load: ");					
				fileName = scannerFile.nextLine();											
				//finding the file in the computer.
				Path questionsPath = Paths.get(fileName);											
				Scanner questions = new Scanner(questionsPath);		
				//counting the questions file lines for arrays rows.
			    qRowCount = (int)Files.lines(questionsPath).count();
				//creating 2d array for questions.															
				String[][] arrayQuestions = new String[qRowCount][qColumnCount];	
				
				//READ QUESTIONS AND ADD ARRAY
				while (questions.hasNextLine()) {						
					for(int i=0;i<arrayQuestions.length;i++) {
						String[] line = questions.nextLine().trim().split("#");
						for (int j=0; j<line.length; j++) {
				               arrayQuestions[i][j] =line[j];					               							
						}
					}
				}
				
			
									
			
				//************QUESTIONS WORDS IN STRING***********************
				String fileContent = Files.readString(questionsPath);		
			    quesWords=fileContent.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("[0-9]","").trim().replaceAll("\\s+"," ").toLowerCase();					
									
				//REMOVE STOP WORDS AND MAKE WORDCLOUD----------------------------
				
				String[] words=quesWords.trim().split(" ");																				
				 StringBuilder builder = new StringBuilder();
				    for(String word : words) {
				        if(!stopWords.contains(word)) {
				            builder.append(word);
				            builder.append(' ');
				        }
				    }					    
				String wordsCloud = builder.toString().trim();																																																						
				//FIND CATEGORIES AND COUNT THEM
				String[] categories = new String[qRowCount];					
				for(int i=0;i<qRowCount;i++) {						
					categories[i]=arrayQuestions[i][0];						
				}
				//FIND DIFFICULTY AND COUNT
				String[] difficulties = new String[qRowCount];					
				for(int i=0;i<qRowCount;i++) {						
					difficulties[i]=arrayQuestions[i][7];						
				}
				
				String space="";	
				TextAttributes attrs = new TextAttributes(Color.MAGENTA, Color.BLACK);
		        s_console.setTextAttributes(attrs);
				System.out.println("Categories         The number of questions");
				TextAttributes attrs1 = new TextAttributes(Color.pink, Color.BLACK);
		        s_console.setTextAttributes(attrs1);
				CountAndRemove(categories,space);
				System.out.println();
			    space="    ";
			    TextAttributes attrs11 = new TextAttributes(Color.MAGENTA, Color.BLACK);
		        s_console.setTextAttributes(attrs11);
				System.out.println("Difficulty level   The number of questions");
				TextAttributes attrs111 = new TextAttributes(Color.pink, Color.BLACK);
		        s_console.setTextAttributes(attrs111);
				CountAndRemove(difficulties,space);
				cn.getTextWindow().setCursorPosition(1, 20);
				cn.getTextWindow().output("(Esc:Menu) ");																																																																
				questions.close();		
            	
            }
           
            if(rkey==KeyEvent.VK_2) {
            	clearScreen();
				
				//numbers of participants info for array
				int partColumn=4;
				//taking the participants file name from the user.
				System.out.print("Enter file name to load: ");					
				String filePart = scannerFile.nextLine();	
				//finding the file in the computer.
				Path partPath = Paths.get(filePart);											
				Scanner partInfo = new Scanner(partPath);		
				//counting the participants file lines for arrays rows.
				int countPart = (int)Files.lines(partPath).count();
				//creating 2d array for contestants info.
				String[][] partArray = new String[countPart][partColumn];
				while(partInfo.hasNextLine()) {
					for(int k=0;k<partArray.length;k++) {
						String[] line = partInfo.nextLine().trim().split("#");
						for (int l=0; l<line.length; l++) {
				               partArray[k][l] =line[l];					               							
						}
					}						
				}
													
				//System.out.println(Arrays.deepToString(partArray));
				System.out.println("The file is loaded.");
				
				//*********PARTICIPANTS CLASS******************
				Participant partArr[][]=new Participant[countPart][partColumn];
				for(int k=0;k<partArray.length;k++) {						
					for (int l=0; l<partArray[0].length; l++) {
			               partArr[k][l]= new Participant(partArray[k][0],partArray[k][1],partArray[k][2],partArray[k][3]);				               							
					}
				}
				cn.getTextWindow().setCursorPosition(1, 20);
				cn.getTextWindow().output("(Esc:Menu) ");
            } 
               
            if(rkey==KeyEvent.VK_3) {
            	
            	clearScreen();
            	//numbers of participants info for array
				int partColumn=4;
				
				//finding the file in the computer.
				Path partPath = Paths.get("Participants.txt");											
				Scanner partInfo = new Scanner(partPath);		
				//counting the participants file lines for arrays rows.
				int countPart = (int)Files.lines(partPath).count();
				//creating 2d array for contestants info.
				String[][] partArray = new String[countPart][partColumn];
				while(partInfo.hasNextLine()) {
					for(int k=0;k<partArray.length;k++) {
						String[] line = partInfo.nextLine().trim().split("#");
						for (int l=0; l<line.length; l++) {
				               partArray[k][l] =line[l];					               							
						}
					}						
				}
													
				//System.out.println(Arrays.deepToString(partArray));
				
				
				//*********PARTICIPANTS CLASS******************
				Participant partArr[][]=new Participant[countPart][partColumn];
				for(int k=0;k<partArray.length;k++) {						
					for (int l=0; l<partArray[0].length; l++) {
			               partArr[k][l]= new Participant(partArray[k][0],partArray[k][1],partArray[k][2],partArray[k][3]);				               							
					}
				}
				int max = 0;
		        int count= 1;
		        String word1="";
		        String curr1="";
				//numbers of participants info for array
				partColumn=4;
				Path dicPath = Paths.get("dictionary.txt");
                Scanner dicWords = new Scanner(dicPath);
                int countDicWords = (int)Files.lines(dicPath).count();
                String[] dicArray = new String[countDicWords];
                while(dicWords.hasNextLine()) {
                    for(int i=0;i<dicArray.length;i++) {
                        dicArray[i]=dicWords.nextLine();
                    }
                }
                
                dicWords.close();
				//finding the file in the computer.
				partPath = Paths.get("Participants.txt");											
				partInfo = new Scanner(partPath);		
				//counting the participants file lines for arrays rows.
				countPart = (int)Files.lines(partPath).count();
				//creating 2d array for contestants info.
			    partArray = new String[countPart][partColumn];
				while(partInfo.hasNextLine()) {
					for(int k=0;k<partArray.length;k++) {
						String[] line = partInfo.nextLine().trim().split("#");
						for (int l=0; l<line.length; l++) {
				               partArray[k][l] =line[l];					               							
						}
					}						
				}
				
				//Choose a contestant as random number
				Random random = new Random();
				int numberPart = random.nextInt(3);
				String randomPart = partArray[numberPart][0];
				System.out.println("Contestant: " + randomPart);
				System.out.println("");
				money=0;
				numberofflag2=0;
				numberofflag2false=0;
				numberofflag2exit=0;
				ishalfjokerUsed=false;
				isDoubledipUsed=false;
				boolean letter=true;
				
				System.out.println("");
				int number_of_question=1;
				
				String [][] arrayQuestions_two=Read_questions(fileName) ;
									
				 stopWords=Files.readString(Path.of("stop_words.txt"));
				 stopWordsArr=stopWords.trim().replaceAll("\\s+"," ").replaceAll("\\r|\\n", " ").split(" ");
				 String[] words=quesWords.trim().split(" ");																				
				 StringBuilder builders = new StringBuilder();
				    for(String word : words) {
				        if(!stopWords.contains(word)) {
				            builders.append(word);
				            builders.append(' ');
				        }
				    }
					  //QUESTIONS CLASS
					Question quesArr2[][]=new Question[qRowCount][qColumnCount];
					for(int k=0;k<arrayQuestions_two.length;k++) {						
						for (int l=0; l<arrayQuestions_two[0].length; l++) {
				               quesArr2[k][l]= new Question(arrayQuestions_two[k][0],arrayQuestions_two[k][1],arrayQuestions_two[k][2],arrayQuestions_two[k][3],arrayQuestions_two[k][4],arrayQuestions_two[k][5],arrayQuestions_two[k][6],arrayQuestions_two[k][7]);				               							
						}
					}	
				
				String[] correctCat=new String[qRowCount];
				String[] wrongCat=new String[qRowCount];
				String[] bestPart=new String[qRowCount];
				int correctCount=0;
				int wrongCount=0;
				int bestPartCount=0;
				String Age=""; 	
		    	
				
				String difficulty="1";
				int five_correct=0;
				boolean answer_is_correct =true;
				
                while(answer_is_correct&&five_correct!=5) {
                	
				String wordsClouds = builders.toString().trim();
				TextAttributes attrs11 = new TextAttributes(Color.pink, Color.BLACK);
		        s_console.setTextAttributes(attrs11);
				if(letter) {
					System.out.println("Do you want to start the competition? (y/n)");
				}
				else {
					System.out.println("Do you want to see the question? (y/n)");
				}
				
				 boolean flagg=true;
				 String Yes_No = null;
					while(flagg) {
						
					if(keypr==1) {  
						  // if keyboard button pressed
				          if(rkey==KeyEvent.VK_Y)  Yes_No="y"; 
				          if(rkey==KeyEvent.VK_N) Yes_No="n"; 
				             
				          if(Yes_No!=null) {
				        	  keypr=0;  
				        	  flagg=false;
				          }
				          keypr=0;	         
					  }
					 Thread.sleep(20);
					}
					if(Yes_No.equals("y")) {
				clearScreen();
				letter=false;
				WordsCloud(difficulty,arrayQuestions_two,wordsClouds);
				System.out.println("");
				System.out.println("Please select word in wordCloud");
				
				keypr=0;
				String selected_word=scannerFile.nextLine();	
				keypr=0;
				String[] resultt=Spell_check_and_Question(number_of_question,difficulty,selected_word,stopWords,dicArray,arrayQuestions_two,qRowCount,qColumnCount );
				int diffs=Integer.parseInt(resultt[0]);
				answer_is_correct =questions(resultt,arrayQuestions_two,qRowCount,qColumnCount,number_of_question);
				if(answer_is_correct) {
					five_correct++;
					for(int i=0;i<countPart;i++) {
						if(partArr[i][0].getName().equals(randomPart)) {
							
							Age=partArr[i][0].getBirthday();
							Age=Age.substring(Age.length()-4,Age.length());
					    	int intAge = Integer.parseInt(Age);
					    	int partAge = 2022-intAge;
					    	if (partAge<=30) {
					    		less30 = partAge;
					    		less30Count++;
					    	}
					    	else if (partAge>30 && partAge<=50) {
					    		greater30 = partAge;
					    		greater30Count++;
					    	}
					    	else if (partAge>50) {
					    		greater50 = partAge;
					    		greater50Count++;
					    	}			    					    

						}
					}
					
					bestPart[bestPartCount]=randomPart;
					bestPartCount++;
					correctCat[correctCount]=quesArr2[diffs][0].getCategory();
					correctCount++;
					number_of_question++;
					int real_diff=Integer.parseInt(difficulty);
					real_diff++;
					difficulty=Integer.toString(real_diff);
				}
				else {
					
					wrongCat[wrongCount]=quesArr2[diffs][0].getCategory();
					wrongCount++;
					
				}
                }
                
               
	
		        String[][] addArray=new String[countPart][5];
		    	for(int i=0;i<countPart;i++) {
		    		String[] line = partArray[i][3].trim().split(";");
		    		for (int j=0; j<line.length; j++) {
			               addArray[i][j] =line[j];	
			               
					}
		    	}
		    	
		    	String[] City=new String[countPart];
		    	for(int i=0;i<countPart;i++) {
		    		City[i]=addArray[i][3];	
		    	}
		    			    			                      
               
			 statistic=new Statistic(mostFrequent(bestPart),mostFrequent(correctCat),mostFrequent(wrongCat),"",mostFrequent(City));
            }
                
                if(five_correct==5) {
                	clearScreen();
                	TextAttributes attrs = new TextAttributes(Color.ORANGE, Color.BLACK);
    		        s_console.setTextAttributes(attrs);
                	System.out.println("Congratulations, YOU ARE A MIILIONAIRE!");
                	System.out.println("You won $1,000,000!!");
                	System.out.println("");
                	System.out.println("");
                	System.out.println("");
                	System.out.println("");
                	System.out.println("");
                	System.out.println("");
                	
                	System.out.println("\r\n"
                			+ " _  ___  ________ _      _     _____ _____ _   _   ___  ___________ _____   _ \r\n"
                			+ "| | |  \\/  |_   _| |    | |   |_   _|  _  | \\ | | / _ \\|_   _| ___ \\  ___| | |\r\n"
                			+ "| | | .  . | | | | |    | |     | | | | | |  \\| |/ /_\\ \\ | | | |_/ / |__   | |\r\n"
                			+ "| | | |\\/| | | | | |    | |     | | | | | | . ` ||  _  | | | |    /|  __|  | |\r\n"
                			+ "|_| | |  | |_| |_| |____| |_____| |_\\ \\_/ / |\\  || | | |_| |_| |\\ \\| |___  |_|\r\n"
                			+ "(_) \\_|  |_/\\___/\\_____/\\_____/\\___/ \\___/\\_| \\_/\\_| |_/\\___/\\_| \\_\\____/  (_)\r\n"
                			+ "                                                                              \r\n"
                			+ "                                                                              \r\n"
                			+ "");
                	TextAttributes attrs1 = new TextAttributes(Color.pink, Color.BLACK);
    		        s_console.setTextAttributes(attrs1);
                }
                cn.getTextWindow().setCursorPosition(1, 20);
				cn.getTextWindow().output("(Esc:Menu) ");
            }
            if(rkey==KeyEvent.VK_4) {
            	clearScreen();
            	 int max = 0;
		         int  count= 1;
				//numbers of participants info for array
				int  partColumn=4;	
				//finding the file in the computer.
				Path partPath = Paths.get("Participants.txt");											
				Scanner partInfo = new Scanner(partPath);		
				//counting the participants file lines for arrays rows.
				int  countPart = (int)Files.lines(partPath).count();
				//creating 2d array for contestants info.
			    String[][] partArray = new String[countPart][partColumn];
				while(partInfo.hasNextLine()) {
					for(int k=0;k<partArray.length;k++) {
						String[] line = partInfo.nextLine().trim().split("#");
						for (int l=0; l<line.length; l++) {
				               partArray[k][l] =line[l];					               							
						}
					}						
				}
				/////////////////////////////////////////
				System.out.println("Statistic:");
				/////////////////////////////////////////
		    	System.out.print("�	The most successful contestant:");
		    	System.out.println(statistic.getConstestant());
		    	/////////////////////////////////////////
		    						    				    	
		    	System.out.print("�	The category with the most correctly answered:");
		    	System.out.println(statistic.getCorrectlyAns());
		    	/////////////////////////////////////////
		    	
		    	System.out.print("�	The category with the most badly answered:");
		    	System.out.println(statistic.getBadlyAns());
		    	/////////////////////////////////////////
		    	System.out.println("�	On average, how many questions did contestants in each age group answer correctly?");
		    	statistic.getAgeAve(less30Count,greater30Count,greater50Count);		    	
		    	
		  
		    	/////////////////////////////////////////	

		    	System.out.print("�	The city with the highest number of participants:");
		    	System.out.println(statistic.getCity());
		    	
		    	cn.getTextWindow().setCursorPosition(1, 20);
				cn.getTextWindow().output("(Esc:Menu) ");
            }
            
            
            
            if(rkey==KeyEvent.VK_ESCAPE) {
            	clearScreen();
            	TextAttributes attrs = new TextAttributes(Color.MAGENTA, Color.BLACK);
		        s_console.setTextAttributes(attrs);
            	System.out.println("\r\n"
						+ "  __  __  _____  _   _  _   _ \r\n"
						+ " |  \\/  || ____|| \\ | || | | |\r\n"
						+ " | |\\/| ||  _|  |  \\| || | | |\r\n"
						+ " | |  | || |___ | |\\  || |_| |\r\n"
						+ " |_|  |_||_____||_| \\_| \\___/ \r\n"
						+ "                              \r\n"
						+ "");
            	TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
		        s_console.setTextAttributes(attrss);
				System.out.println("   1) Load questions\n");
				System.out.println("   2) Load participants\n");
				System.out.println("   3) Start competition\n");
				System.out.println("   4) Show statistics\n");
				System.out.println("   5) Exist");
            }
            if(rkey==KeyEvent.VK_5) {
            	clearScreen();
            	TextAttributes attrs = new TextAttributes(Color.MAGENTA, Color.BLACK);
		        s_console.setTextAttributes(attrs);
            	System.out.println("\r\n"
            			+ "  ______     ________ _ _ \r\n"
            			+ " |  _ \\ \\   / /  ____| | |\r\n"
            			+ " | |_) \\ \\_/ /| |__  | | |\r\n"
            			+ " |  _ < \\   / |  __| | | |\r\n"
            			+ " | |_) | | |  | |____|_|_|\r\n"
            			+ " |____/  |_|  |______(_|_)\r\n"
            			+ "                          \r\n"
            			+ "                          \r\n"
            			+ "");
            	TextAttributes attrs1 = new TextAttributes(Color.pink, Color.BLACK);
		        s_console.setTextAttributes(attrs1);
				String choices = scannerChoice.next();
								
				System.exit(0);
            }
                                               
            
            keypr=0;    // last action  
         }
         Thread.sleep(20);
         	
      }
   }
   static void clearScreen() {
		
		enigma.console.Console cn = Enigma.getConsole("Who Wants to be A Millionaire", 90, 35, 12, 10);
		for (int i = 0; i < 90; i++) {
			for (int j = 0; j < 36; j++) {
				cn.getTextWindow().setCursorPosition(i, j);
				cn.getTextWindow().output(" ");
			}
		}
		cn.getTextWindow().setCursorPosition(0, 0);
	
	}
   
   public static String[] CountAndRemove(String[] inputArr,String space) {
		int count=0;
		int lth = inputArr.length;
		// Specify a broad length for categories
		String[] unique = new String[lth];
		int[] times = new int[lth];
		int i = 0;
		int j = 0;
		
		
		Arrays.sort(inputArr);
		while (i < lth) {
		    String w = inputArr[i];
		    count = 1;
		    while(++i < lth && inputArr[i].equals(w)) ++count;
		    unique[j] = w;
		    times[j++] = count;
		}
		// Reduce the length of the arrays    
		unique = Arrays.copyOf(unique, j);
		times  = Arrays.copyOf(times, j);     
		String leftAlignFormat = " %-15s              %-4d         %n";
		for (i = 0; i < unique.length;++i) {
			
			System.out.format(leftAlignFormat,space+unique[i], times[i]);
          		    		  
		}
		return unique;
	}
   
   public static String[] WordsCloud(String inputDiff,String[][] inputarrayQuestions,String inputwordsCloud) throws IOException {
		String[] currentCloud = new String[inputwordsCloud.length()];
		String stopWords=Files.readString(Path.of("stop_words.txt"));
		String[] stopWordsArr=stopWords.trim().replaceAll("\\s+"," ").replaceAll("\\r|\\n", " ").split(" ");
		int j=0;
		//create word cloud by difficulty levels.
		for(int i=0;i<inputarrayQuestions.length;i++) {
			if(inputDiff.equals("1")&&inputarrayQuestions[i][7].equals("1")){
				currentCloud[j]=inputarrayQuestions[i][1];
				j++;			
			}	
			if(inputDiff.equals("2")&&inputarrayQuestions[i][7].equals("2")){
				currentCloud[j]=inputarrayQuestions[i][1];
				j++;			
			}	
			if(inputDiff.equals("3")&&inputarrayQuestions[i][7].equals("3")){
				currentCloud[j]=inputarrayQuestions[i][1];
				j++;			
			}	
			if(inputDiff.equals("4")&&inputarrayQuestions[i][7].equals("4")){
				currentCloud[j]=inputarrayQuestions[i][1];
				j++;			
			}	
			if(inputDiff.equals("5")&&inputarrayQuestions[i][7].equals("5")){
				currentCloud[j]=inputarrayQuestions[i][1];
				j++;			
			}	
			
		}
		//converting array to string and rearrange this string. then delete unnecessary char and stop words.	    						
		String WORDS = String.join(",", currentCloud);
		WORDS=WORDS.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("[0-9]","").trim().replaceAll("\\s+"," ").toLowerCase();
		String[] words=WORDS.trim().split(" ");
		StringBuilder builder = new StringBuilder();
	    for(String word : words) {
	        if(!stopWords.contains(word)) {
	            builder.append(word);
	            builder.append(' ');
	        }
	    }					    	    
	    String wordsCloud = builder.toString().trim();
	    //array size is big at the beginning. so remove all null string.
		wordsCloud=wordsCloud.replaceAll(" null","");
								
		 removeDuplicate(wordsCloud);
								
		
		return currentCloud; 
	}
	
   public static String[] Spell_check_and_Question(int number_of_question,String difficulty,String selected_word,String stopWords,String[] dicArray,String[][]  arrayQuestions_two,int qRowCount,int qColumnCount ) throws InterruptedException {
		
		Scanner scannerChoice = new Scanner(System.in);
		
		String[] result=new String[3];
		int number=0;	
		int number2=0;
		int count_char=0;
		int length=0;
		int goldnumber=-1;
		boolean find=false;
		String[] words=new String[5];
		String[][] new_words=new String[5][5];
		String[] not_changed_words=new String [5];
		String[] stopWordsArr_two=stopWords.trim().replaceAll("\\s+"," ").replaceAll("\\r|\\n", " ").split(" ");																
		Question quesArr_two[][]=new Question[qRowCount][qColumnCount];
		for(int k=0;k<arrayQuestions_two.length;k++) {						
			for (int l=0; l<arrayQuestions_two[0].length; l++) {
				quesArr_two[k][l]= new Question(arrayQuestions_two[k][0],arrayQuestions_two[k][1],arrayQuestions_two[k][2],arrayQuestions_two[k][3],arrayQuestions_two[k][4],arrayQuestions_two[k][5],arrayQuestions_two[k][6],arrayQuestions_two[k][7]);				               							
			}						
		}	
		
		    boolean flag=false;
		    String original_questions="";
		    String original_questionss="";
		    String copy_questions="";
		   for(int i=0;i<arrayQuestions_two.length;i++) {
			   original_questionss = quesArr_two[i][0].getQuestion();
			   int diff = Integer.parseInt(quesArr_two[i][0].getDifficulty());
			   int real_diff=Integer.parseInt(difficulty);
			   original_questionss=original_questionss.toLowerCase();
			   if(original_questionss.contains(selected_word)&&diff==real_diff) {
				   original_questions= quesArr_two[i][0].getQuestion();
				   original_questionss=original_questionss.toLowerCase();
				   copy_questions= quesArr_two[i][0].getQuestion();
				   goldnumber=i;
				   flag=true;
			   }
		   }
		    String goldnumber_string=Integer.toString(goldnumber);
	        
		    if(flag) {
		    copy_questions=copy_questions.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("[0-9]","").trim().replaceAll("\\s+"," ").toLowerCase();
			String[] questionwords = copy_questions.split(" ");						
			for(int h=0;h <questionwords.length;h++) { 
				for(int j=0;j<stopWordsArr_two.length;j++) {
					if(questionwords[h].equals(stopWordsArr_two[j])) {
						questionwords[h]="";									
						break;
					}								
				}							
			}
			//for(int a=0;a<questionwords.length;a++) {
			//	System.out.print(questionwords[a]+" ");
			//}
			for(int a=0;a<questionwords.length;a++) {
				find=false; 
				if(questionwords[a]!="") {			
				for(int b=0;b<dicArray.length;b++) {
					count_char=0;
					if(questionwords[a].length()==dicArray[b].length()) {
						for(int c=0;c<dicArray[b].length();c++) {
							if(questionwords[a].charAt(c)!=dicArray[b].charAt(c)) {
								count_char++;
							}
						}
						if(count_char==1&&length<5) {
							words[number]=questionwords[a];
							new_words[number][length]=dicArray[b];
							length++;
							find=true;
							
						}
						
					}
					
				}
				if(find&&number<5) {
					number++;
				}
				}
				if(!find&&number2<5) {
				not_changed_words[number2]=questionwords[a];
				number2++;
				}
			}
			
			for(int i=0;i<words.length;i++) {
				
				for(int j=0;j<dicArray.length;j++) {
					
					if(words[i]!=null&&words[i].equals(dicArray[j])) {
						words[i]="";
						break;
					}
				}
			}
																							
			System.out.println("Q"+number_of_question+") "+original_questions);
			
			//for(int i=0;i<words.length;i++) {
			//	System.out.println(words[i]);
			//}
					
			for(int i=0;i<words.length;i++) {
				if(words[i]!=null&&words[i]!="") {
			    clearScreen();
				System.out.println("(Would you like to change " +words[i]  + " to these? )");
				
				for(int j=0;j<5;j++) {
					if(new_words[i][j]!=null) {
					System.out.print((j+1)+")"+new_words[i][j]+" ");
					}
				}
				System.out.print("6) don't change");
				int choice=0;
				boolean flaggg=true;
				while(flaggg) {
					
					if(keypr==1) {  
						  // if keyboard button pressed
				          if(rkey==KeyEvent.VK_1) choice=1; 
				          if(rkey==KeyEvent.VK_2) choice=2; 
				          if(rkey==KeyEvent.VK_3) choice=3; 
				          if(rkey==KeyEvent.VK_4) choice=4; 
				          if(rkey==KeyEvent.VK_5) choice=5; 
				          if(rkey==KeyEvent.VK_6) choice=6; 
				          if(choice!=0) {
				        	  keypr=0;  
				        	  flaggg=false;
				          }
				          keypr=0;	         
					  }
					 Thread.sleep(20);
					}
			
				
				if(choice!=6) {
			   clearScreen();	
			    original_questions=original_questions.toLowerCase();
				original_questions=original_questions.replaceAll(words[i],new_words[i][choice-1]);		
				System.out.println("Q"+number_of_question+") "+original_questions);
				}
				else {
					clearScreen();	
					System.out.println("Q"+number_of_question+") "+original_questions);
				}
				
				
			}
			}
		    }
		    else
		    	System.out.println("game over :(");
		    	
			
			
		result[0]=goldnumber_string;
		result[1]=original_questions;
		
		
		clearScreen();						
		return result;
	}
	
   public static String[][] Read_questions(String fileName) throws IOException{


		int qColumnCount=8;
		Path questionsPath1 = Paths.get(fileName);											
		Scanner questions1 = new Scanner(questionsPath1);		
		//counting the questions file lines for arrays rows.
		 int qRowCount = (int)Files.lines(questionsPath1).count();
		//creating 2d array for questions.															
		String[][] arrayQuestions_two  = new String[qRowCount][qColumnCount];	
		
		//READ QUESTIONS AND ADD ARRAY
		while (questions1.hasNextLine()) {						
			for(int i=0;i<arrayQuestions_two .length;i++) {
				String[] line = questions1.nextLine().trim().split("#");
				for (int j=0; j<line.length; j++) {
		               arrayQuestions_two [i][j] =line[j];					               							
				}
			}
		}
		return arrayQuestions_two;
			
	}
   static boolean time_is_over=false;
   static boolean flag2;
   static boolean ishalfjokerUsed = false;
   static boolean isDoubledipUsed = false;
   static int money;
   static int numberofflag2 = 0;
   static int numberofflag2false = 0;
   static int numberofflag2exit = 0;
   static String user_answer;
   static boolean flagme=true;
   static boolean questions(String[] question_and_difficulty,String[][] arrayQuestions_two,int qRowCount,int qColumnCount,int number_of_question ) throws TextWindowNotAvailableException, InterruptedException {
   	flag2 =true;
   	boolean keep=true;
   	Scanner scannerChoice = new Scanner(System.in);
   	
   	int diffs=Integer.parseInt(question_and_difficulty[0]);
		System.out.println("Q"+number_of_question+") "+question_and_difficulty[1]);
		Question quesArr_two1[][]=new Question[qRowCount][qColumnCount];
		for(int k=0;k<arrayQuestions_two.length;k++) {						
			for (int l=0; l<arrayQuestions_two[0].length; l++) {
				quesArr_two1[k][l]= new Question(arrayQuestions_two[k][0],arrayQuestions_two[k][1],arrayQuestions_two[k][2],arrayQuestions_two[k][3],arrayQuestions_two[k][4],arrayQuestions_two[k][5],arrayQuestions_two[k][6],arrayQuestions_two[k][7]);				               							
			}						
		}
   	String answer1,answer2,answer3,answer4,correctanswer,correctanswerfullword;					
		answer1=quesArr_two1[diffs][0].getChoice1();
		answer2=quesArr_two1[diffs][0].getChoice2();
		answer3=quesArr_two1[diffs][0].getChoice3();
		answer4=quesArr_two1[diffs][0].getChoice4();
		correctanswer=quesArr_two1[diffs][0].getAnswer();
		System.out.print("a)"+answer1+"\nb)"+answer2+"\nc)"+answer3+"\nd)"+answer4);
			
		System.out.println("");
		
		
		
		String[] answers = new String[4];
		String[] answersWithoutCorrectanswer = new String[4];
		answers[0] = answer1;
		answers[1] = answer2;
		answers[2] = answer3;
		answers[3] = answer4;
		
		correctanswerfullword = correctanswer;
		
		if (correctanswerfullword.equalsIgnoreCase("A")) {
			correctanswerfullword = answer1;
		}
		else if (correctanswerfullword.equalsIgnoreCase("B")) {
			correctanswerfullword = answer2;
		}
		else if (correctanswerfullword.equalsIgnoreCase("C")) {
			correctanswerfullword = answer3;
		}
		else if (correctanswerfullword.equalsIgnoreCase("D")) {
			correctanswerfullword = answer4;
		}
		
		
		System.out.println("Enter your choice (E:Exit): ");
		time_is_over=false;
		flagme=true;
		
		//money=money(money);
		timer();
		user_answer="";
		boolean wait_for_user_answer=true;				  		  	  
		while(wait_for_user_answer) {
			
		if(keypr==1) {  
			  // if keyboard button pressed
	          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
	          if(rkey==KeyEvent.VK_B) user_answer="B"; 
	          if(rkey==KeyEvent.VK_C) user_answer="C"; 
	          if(rkey==KeyEvent.VK_D) user_answer="D";
	          if(rkey==KeyEvent.VK_E) user_answer="E";
	          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
	          if(rkey==KeyEvent.VK_G) user_answer="double dip";  
	          if(rkey==KeyEvent.VK_H&&numberofflag2>=3) user_answer="audiance joker";
	          if(user_answer!="") {
	        	  keypr=0;  
	        	  wait_for_user_answer=false;
	          }
	          keypr=0;	         
		  }
		 Thread.sleep(20);
		}
		  
		
		  
		if(user_answer.equals(correctanswer)&&!time_is_over) {
			TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
			s_console.setTextAttributes(attrs);
			cn.getTextWindow().setCursorPosition(0, 9);
			System.out.println("answer is correct!!            ");
			TextAttributes attrss = new TextAttributes(Color.cyan, Color.BLACK);
			s_console.setTextAttributes(attrss);
			flag2=true;
			numberofflag2++;
			flagme=false;
			cn.getTextWindow().setCursorPosition(55, 4);
			money=money(money);
			
		}
		
		else if  (user_answer.equalsIgnoreCase("E")){	
			System.out.println("BYE!");
			String choices = scannerChoice.next();
			flag2=false;
			numberofflag2exit++;
			cn.getTextWindow().setCursorPosition(55, 4);
			money=money(money);
			keep=false;
			System.exit(0);
		}
		///////////////////////////////////////////////////////////////////////////////////////////
		
		else if  (user_answer.equalsIgnoreCase("50%"))
		{
			if (ishalfjokerUsed == false&&!time_is_over) {
				clearScreen();
				System.out.println("Q"+number_of_question+") "+question_and_difficulty[1]);
				int correctanswerindex = 0;
				for (int i = 0; i < 4; i++)
				{
					if (answers[i].equalsIgnoreCase(correctanswerfullword)) {
						answers[i] = " ";
						correctanswerindex = i;
					}
					answersWithoutCorrectanswer[i] = answers[i];
				}
				
				
				Random rnd = new Random();
				int rand1, rand2;
				
				do {
					rand1 = rnd.nextInt(0, 4);
				} while (answersWithoutCorrectanswer[rand1].equalsIgnoreCase(" "));
				answersWithoutCorrectanswer[rand1] = " ";
				
				do {
					rand2 = rnd.nextInt(0, 4);
				} while (answersWithoutCorrectanswer[rand2].equalsIgnoreCase(" "));
				answersWithoutCorrectanswer[rand2] = " ";
	
				
				answers[correctanswerindex] = correctanswerfullword;
				answersWithoutCorrectanswer[correctanswerindex] = answers[correctanswerindex];
				
				System.out.println("a)"+answersWithoutCorrectanswer[0]+"\nb)"+answersWithoutCorrectanswer[1]+
						"\nc)"+answersWithoutCorrectanswer[2]+"\nd)"+answersWithoutCorrectanswer[3]);

				System.out.println("Enter your choice (E:Exit): ");
				ishalfjokerUsed = true;
			    wait_for_user_answer=true;				  		  	  
				while(wait_for_user_answer) {
					
				if(keypr==1) {  
					  // if keyboard button pressed
			          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
			          if(rkey==KeyEvent.VK_B) user_answer="B"; 
			          if(rkey==KeyEvent.VK_C) user_answer="C"; 
			          if(rkey==KeyEvent.VK_D) user_answer="D";
			          if(rkey==KeyEvent.VK_E) user_answer="E";
			          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
			          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
			          if(user_answer!="") {
			        	  keypr=0;  
			        	  wait_for_user_answer=false;
			          }
			          keypr=0;	         
				  }
				 Thread.sleep(20);
				}
				
				if(user_answer.equals(correctanswer)&&!time_is_over) {
					TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
					s_console.setTextAttributes(attrs);
					cn.getTextWindow().setCursorPosition(0, 9);
					System.out.println("answer is correct!!                   ");
					TextAttributes attrss = new TextAttributes(Color.cyan, Color.BLACK);
					s_console.setTextAttributes(attrss);
					flag2=true;
					flagme=false;
					numberofflag2++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
				}
				else if  (user_answer.equalsIgnoreCase("E")){	
					System.out.println("BYE!");
					String choices = scannerChoice.next();
					flag2=false;
					numberofflag2exit++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
					keep=false;
					System.exit(0);
				}
				
				else if  (user_answer.equalsIgnoreCase("Double Dip"))
				{
					if (isDoubledipUsed == false&&!time_is_over) 
					{
						System.out.println("Enter your first choice (E:Exit): ");
						 wait_for_user_answer=true;				  		  	  
							while(wait_for_user_answer) {
								
							if(keypr==1) {  
								  // if keyboard button pressed
						          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
						          if(rkey==KeyEvent.VK_B) user_answer="B"; 
						          if(rkey==KeyEvent.VK_C) user_answer="C"; 
						          if(rkey==KeyEvent.VK_D) user_answer="D";
						          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
						          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
						          if(user_answer!="") {
						        	  keypr=0;  
						        	  wait_for_user_answer=false;
						          }
						          keypr=0;	         
							  }
							 Thread.sleep(20);
							}
						
						if(user_answer.equals(correctanswer)&&!time_is_over) {
							TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
							s_console.setTextAttributes(attrs);
							cn.getTextWindow().setCursorPosition(0, 9);
							System.out.println("answer is correct!!                     ");
							TextAttributes attrss = new TextAttributes(Color.cyan, Color.BLACK);
							s_console.setTextAttributes(attrss);
							flag2=true;
							flagme=false;
							numberofflag2++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
						}
						else if  (user_answer.equalsIgnoreCase("E")){	
							System.out.println("BYE!");
							String choices = scannerChoice.next();
							flag2=false;
							numberofflag2exit++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
							keep=false;
							System.exit(0);
						}
						else {
							TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
							s_console.setTextAttributes(attrs);
							System.out.println("Your first answer is wronngg!!       ");
							TextAttributes attrss = new TextAttributes(Color.cyan, Color.BLACK);
							s_console.setTextAttributes(attrss);
							System.out.println("pleaser enter your second choice (E:Exit):   ");
							 wait_for_user_answer=true;				  		  	  
								while(wait_for_user_answer) {
									
								if(keypr==1) {  
									  // if keyboard button pressed
							          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
							          if(rkey==KeyEvent.VK_B) user_answer="B"; 
							          if(rkey==KeyEvent.VK_C) user_answer="C"; 
							          if(rkey==KeyEvent.VK_D) user_answer="D";
							          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
							          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
							          if(user_answer!="") {
							        	  keypr=0;  
							        	  wait_for_user_answer=false;
							          }
							          keypr=0;	         
								  }
								 Thread.sleep(20);
								}
							
							if(user_answer.equals(correctanswer)&&!time_is_over) {
								TextAttributes attrs1 = new TextAttributes(Color.GREEN, Color.BLACK);
								s_console.setTextAttributes(attrs1);
								cn.getTextWindow().setCursorPosition(0, 9);
								System.out.println("answer is correct!!                     ");
								TextAttributes attrss1 = new TextAttributes(Color.cyan, Color.BLACK);
								s_console.setTextAttributes(attrss1);
								flag2=true;
								flagme=false;
								numberofflag2++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
							}
							else if  (user_answer.equalsIgnoreCase("E")){	
								System.out.println("BYE!");
								String choices = scannerChoice.next();
								flag2=false;
								numberofflag2exit++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
								keep=false;
								System.exit(0);
							}
							else {
								flag2=false;
								numberofflag2false++;
								money=money(money);
								cn.getTextWindow().setCursorPosition(55, 4);
								TextAttributes attrs1 = new TextAttributes(Color.RED, Color.BLACK);
								s_console.setTextAttributes(attrs1);
								System.out.println("answer is wronngg!!                        ");
								System.out.println("Game Oveeer!!                        ");
								TextAttributes attrss1 = new TextAttributes(Color.pink, Color.BLACK);
								s_console.setTextAttributes(attrss1);
							}
						}
						isDoubledipUsed = true;
					}
					else {
						System.out.println("You have already used the Double Dip joker.    " );
						System.out.println("Enter your choice (E:Exit):   ");
						 wait_for_user_answer=true;				  		  	  
							while(wait_for_user_answer) {
								
							if(keypr==1) {  
								  // if keyboard button pressed
						          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
						          if(rkey==KeyEvent.VK_B) user_answer="B"; 
						          if(rkey==KeyEvent.VK_C) user_answer="C"; 
						          if(rkey==KeyEvent.VK_D) user_answer="D";
						          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
						          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
						          if(user_answer!="") {
						        	  keypr=0;  
						        	  wait_for_user_answer=false;
						          }
						          keypr=0;	         
							  }
							 Thread.sleep(20);
							}
						
						if(user_answer.equals(correctanswer)&&!time_is_over) {
							TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
							s_console.setTextAttributes(attrs);
							cn.getTextWindow().setCursorPosition(0, 9);
							System.out.println("answer is correct!!                        ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
							flag2=true;
							flagme=false;
							numberofflag2++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
						}
						else if  (user_answer.equalsIgnoreCase("E")){	
							System.out.println("BYE!");
							String choices = scannerChoice.next();
							flag2=false;
							numberofflag2exit++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
							keep=false;
							System.exit(0);
						}
						else {
							flag2=false;
							numberofflag2false++;
							money=money(money);
							TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
							s_console.setTextAttributes(attrs);
							System.out.println("answer is wronngg!!                        ");
							System.out.println("Game Oveeer!!                        ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
						}
					}
				}
				
				else {
					flag2=false;
					numberofflag2false++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);	
					TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
					s_console.setTextAttributes(attrs);
					System.out.println("answer is wronngg!!                        ");
					System.out.println("Game Oveeer!!                        ");
					TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
					s_console.setTextAttributes(attrss);
				}
				
			}
			else {
				System.out.println("You have already used the 50% joker.      ");
				System.out.println("Enter your choice (E:Exit):        ");
				 wait_for_user_answer=true;				  		  	  
					while(wait_for_user_answer) {
						
					if(keypr==1) {  
						  // if keyboard button pressed
				          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
				          if(rkey==KeyEvent.VK_B) user_answer="B"; 
				          if(rkey==KeyEvent.VK_C) user_answer="C"; 
				          if(rkey==KeyEvent.VK_D) user_answer="D";
				          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
				          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
				          if(user_answer!="") {
				        	  keypr=0;  
				        	  wait_for_user_answer=false;
				          }
				          keypr=0;	         
					  }
					 Thread.sleep(20);
					}
				
				if(user_answer.equals(correctanswer)&&!time_is_over) {
					TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
					s_console.setTextAttributes(attrs);
					cn.getTextWindow().setCursorPosition(0, 9);
					System.out.println("answer is correct!!                       ");
					TextAttributes attrss = new TextAttributes(Color.cyan, Color.BLACK);
					s_console.setTextAttributes(attrss);
					flag2=true;
					flagme=false;
					numberofflag2++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
				}
				else if  (user_answer.equalsIgnoreCase("E")){	
					System.out.println("BYE!");
					String choices = scannerChoice.next();
					flag2=false;
					numberofflag2exit++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
					keep=false;
					System.exit(0);
				}
				else if  (user_answer.equalsIgnoreCase("Double Dip"))
				{
					if (isDoubledipUsed == false&&!time_is_over) 
					{
						System.out.println("Enter your first choice (E:Exit):    ");
						 wait_for_user_answer=true;				  		  	  
							while(wait_for_user_answer) {
								
							if(keypr==1) {  
								  // if keyboard button pressed
						          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
						          if(rkey==KeyEvent.VK_B) user_answer="B"; 
						          if(rkey==KeyEvent.VK_C) user_answer="C"; 
						          if(rkey==KeyEvent.VK_D) user_answer="D";
						          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
						          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
						          if(user_answer!="") {
						        	  keypr=0;  
						        	  wait_for_user_answer=false;
						          }
						          keypr=0;	         
							  }
							 Thread.sleep(20);
							}
						
						if(user_answer.equals(correctanswer)&&!time_is_over) {
							TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
							s_console.setTextAttributes(attrs);
							cn.getTextWindow().setCursorPosition(0, 9);
							System.out.println("answer is correct!!                      ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
							flag2=true;
							flagme=false;
							numberofflag2++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
						}
						else if  (user_answer.equalsIgnoreCase("E")){	
							System.out.println("BYE!");
							String choices = scannerChoice.next();
							flag2=false;
							numberofflag2exit++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
							keep=false;
							System.exit(0);
						}
						else {
							TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
							s_console.setTextAttributes(attrs);
							System.out.println("Your first answer is wronngg!!        ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
							System.out.println("pleaser enter your second choice (E:Exit):    ");
							 wait_for_user_answer=true;				  		  	  
								while(wait_for_user_answer) {
									
								if(keypr==1) {  
									  // if keyboard button pressed
							          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
							          if(rkey==KeyEvent.VK_B) user_answer="B"; 
							          if(rkey==KeyEvent.VK_C) user_answer="C"; 
							          if(rkey==KeyEvent.VK_D) user_answer="D";
							          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
							          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
							          if(user_answer!="") {
							        	  keypr=0;  
							        	  wait_for_user_answer=false;
							          }
							          keypr=0;	         
								  }
								 Thread.sleep(20);
								}
							
							if(user_answer.equals(correctanswer)&&!time_is_over) {
								TextAttributes attrs1 = new TextAttributes(Color.RED, Color.BLACK);
								s_console.setTextAttributes(attrs1);
								cn.getTextWindow().setCursorPosition(0, 9);
								System.out.println("answer is correct!!                       ");
								TextAttributes attrss1 = new TextAttributes(Color.pink, Color.BLACK);
								s_console.setTextAttributes(attrss1);
								flag2=true;
								flagme=false;
								numberofflag2++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
							}
							else if  (user_answer.equalsIgnoreCase("E")){	
								System.out.println("BYE!");
								String choices = scannerChoice.next();
								flag2=false;
								numberofflag2exit++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
								keep=false;
								System.exit(0);
							}
							else {
								flag2=false;
								numberofflag2false++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
								TextAttributes attrs1 = new TextAttributes(Color.GREEN, Color.BLACK);
								s_console.setTextAttributes(attrs1);
								System.out.println("answer is wronngg!!                        ");
								System.out.println("Game Oveeer!!                        ");
								TextAttributes attrss1 = new TextAttributes(Color.pink, Color.BLACK);
								s_console.setTextAttributes(attrss1);
							}
						}
						isDoubledipUsed = true;
					}
					else {
						System.out.println("You have already used the Double Dip joker.     ");
						System.out.println("Enter your choice (E:Exit):    ");
						 wait_for_user_answer=true;				  		  	  
							while(wait_for_user_answer) {
								
							if(keypr==1) {  
								  // if keyboard button pressed
						          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
						          if(rkey==KeyEvent.VK_B) user_answer="B"; 
						          if(rkey==KeyEvent.VK_C) user_answer="C"; 
						          if(rkey==KeyEvent.VK_D) user_answer="D";
						          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
						          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
						          if(user_answer!="") {
						        	  keypr=0;  
						        	  wait_for_user_answer=false;
						          }
						          keypr=0;	         
							  }
							 Thread.sleep(20);
							}
						
						if(user_answer.equals(correctanswer)&&!time_is_over) {
							TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
							s_console.setTextAttributes(attrs);
							cn.getTextWindow().setCursorPosition(0, 9);
							System.out.println("answer is correct!!                              ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
							flag2=true;
							flagme=false;
							numberofflag2++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
						}
						else if  (user_answer.equalsIgnoreCase("E")){	
							System.out.println("BYE!");
							String choices = scannerChoice.next();
							flag2=false;
							numberofflag2exit++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
							keep=false;
							System.exit(0);
						}
						else {
							flag2=false;
							numberofflag2false++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
							TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
							s_console.setTextAttributes(attrs);
							System.out.println("answer is wronngg!!                        ");
							System.out.println("Game Oveeer!!                        ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
						}
					}
				}
				else {
					flag2=false;
					numberofflag2false++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
					TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
					s_console.setTextAttributes(attrs);
					System.out.println("answer is wronngg!!                        ");
					System.out.println("Game Oveeer!!                        ");
					TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
					s_console.setTextAttributes(attrss);
				}
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if  (user_answer.equalsIgnoreCase("Double Dip"))
		{
			if (isDoubledipUsed == false) 
			{
				isDoubledipUsed = true;
				System.out.println("Enter your first choice (E:Exit):       ");
				 wait_for_user_answer=true;				  		  	  
					while(wait_for_user_answer) {
						
					if(keypr==1) {  
						  // if keyboard button pressed
				          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
				          if(rkey==KeyEvent.VK_B) user_answer="B"; 
				          if(rkey==KeyEvent.VK_C) user_answer="C"; 
				          if(rkey==KeyEvent.VK_D) user_answer="D";
				          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
				          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
				          if(user_answer!="") {
				        	  keypr=0;  
				        	  wait_for_user_answer=false;
				          }
				          keypr=0;	         
					  }
					 Thread.sleep(20);
					}
				
				if(user_answer.equals(correctanswer)&&!time_is_over) {
					TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
					s_console.setTextAttributes(attrs);
					cn.getTextWindow().setCursorPosition(0, 9);
					System.out.println("answer is correct!!                      ");
					TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
					s_console.setTextAttributes(attrss);
					flag2=true;
					flagme=false;
					numberofflag2++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
				}
				else if  (user_answer.equalsIgnoreCase("E")){	
					System.out.println("BYE!");
					String choices = scannerChoice.next();
					flag2=false;
					numberofflag2exit++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
					keep=false;
					System.exit(0);
				}
				else if (user_answer.equalsIgnoreCase("50%")) 
				{
					if (ishalfjokerUsed == false&&!time_is_over) 
					{
						ishalfjokerUsed = true;
						clearScreen();
						System.out.println("Q"+number_of_question+") "+question_and_difficulty[1]);
						int correctanswerindex = 0;
						for (int i = 0; i < 4; i++)
						{
							if (answers[i].equalsIgnoreCase(correctanswerfullword)) {
								answers[i] = " ";
								correctanswerindex = i;
							}
							answersWithoutCorrectanswer[i] = answers[i];
						}
						
						Random rnd = new Random();
						int rand1, rand2;
						
						do {
							rand1 = rnd.nextInt(0, 4);
						} while (answersWithoutCorrectanswer[rand1].equalsIgnoreCase(" "));
						answersWithoutCorrectanswer[rand1] = " ";
						do {
							rand2 = rnd.nextInt(0, 4);
						} while (answersWithoutCorrectanswer[rand2].equalsIgnoreCase(" "));
						answersWithoutCorrectanswer[rand2] = " ";
			
						
						answers[correctanswerindex] = correctanswerfullword;
						answersWithoutCorrectanswer[correctanswerindex] = answers[correctanswerindex];
						
						System.out.println("a)"+answersWithoutCorrectanswer[0]+"\nb)"+answersWithoutCorrectanswer[1]+
								"\nc)"+answersWithoutCorrectanswer[2]+"\nd)"+answersWithoutCorrectanswer[3]);

						System.out.println("Enter your choice (E:Exit): ");
						 wait_for_user_answer=true;				  		  	  
							while(wait_for_user_answer) {
								
							if(keypr==1) {  
								  // if keyboard button pressed
						          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
						          if(rkey==KeyEvent.VK_B) user_answer="B"; 
						          if(rkey==KeyEvent.VK_C) user_answer="C"; 
						          if(rkey==KeyEvent.VK_D) user_answer="D";
						          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
						          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
						          if(user_answer!="") {
						        	  keypr=0;  
						        	  wait_for_user_answer=false;
						          }
						          keypr=0;	         
							  }
							 Thread.sleep(20);
							}
						flagme=true;
						if(user_answer.equals(correctanswer)&&!time_is_over) {
							TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
							s_console.setTextAttributes(attrs);
							cn.getTextWindow().setCursorPosition(0, 9);
							System.out.println("answer is correct!!                     ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
							flag2=true;
							flagme=false;
							numberofflag2++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
						}
						else if  (user_answer.equalsIgnoreCase("E")){	
							System.out.println("BYE!");
							String choices = scannerChoice.next();
							flag2=false;
							numberofflag2exit++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
							keep=false;
							System.exit(0);
						}
						else {
							TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
							s_console.setTextAttributes(attrs);
							System.out.println("Your first answer is wronngg!!        ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
							System.out.println("pleaser enter your second choice (E:Exit):    ");
							 wait_for_user_answer=true;				  		  	  
								while(wait_for_user_answer) {
									
								if(keypr==1) {  
									  // if keyboard button pressed
							          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
							          if(rkey==KeyEvent.VK_B) user_answer="B"; 
							          if(rkey==KeyEvent.VK_C) user_answer="C"; 
							          if(rkey==KeyEvent.VK_D) user_answer="D";
							          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
							          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
							          if(user_answer!="") {
							        	  keypr=0;  
							        	  wait_for_user_answer=false;
							          }
							          keypr=0;	         
								  }
								 Thread.sleep(20);
								}
							
							if(user_answer.equals(correctanswer)&&!time_is_over) {
								TextAttributes attrs1 = new TextAttributes(Color.GREEN, Color.BLACK);
								s_console.setTextAttributes(attrs1);
								cn.getTextWindow().setCursorPosition(0, 9);
								System.out.println("answer is correct!!                    ");
								TextAttributes attrss1 = new TextAttributes(Color.pink, Color.BLACK);
								s_console.setTextAttributes(attrss1);
								flag2=true;
								flagme=false;
								numberofflag2++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
							}
							else if  (user_answer.equalsIgnoreCase("E")){	
								System.out.println("BYE!");
								String choices = scannerChoice.next();
								flag2=false;
								numberofflag2exit++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
								keep=false;
								System.exit(0);
							}
							else {
								flag2=false;
								numberofflag2false++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
								TextAttributes attrs1 = new TextAttributes(Color.RED, Color.BLACK);
								s_console.setTextAttributes(attrs1);
								System.out.println("answer is wronngg!!                        ");
								System.out.println("Game Oveeer!!                        ");
								TextAttributes attrss1 = new TextAttributes(Color.pink, Color.BLACK);
								s_console.setTextAttributes(attrss1);
							}
						}
					}
					else {
						System.out.println("You have already used your 50% joker.      ");
						System.out.println("Enter your choice (E:Exit): ");
						 wait_for_user_answer=true;				  		  	  
							while(wait_for_user_answer) {
								
							if(keypr==1) {  
								  // if keyboard button pressed
						          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
						          if(rkey==KeyEvent.VK_B) user_answer="B"; 
						          if(rkey==KeyEvent.VK_C) user_answer="C"; 
						          if(rkey==KeyEvent.VK_D) user_answer="D";
						          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
						          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
						          if(user_answer!="") {
						        	  keypr=0;  
						        	  wait_for_user_answer=false;
						          }
						          keypr=0;	         
							  }
							 Thread.sleep(20);
							}
						
						if(user_answer.equals(correctanswer)&&!time_is_over) {
							TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
							s_console.setTextAttributes(attrs);
							cn.getTextWindow().setCursorPosition(0, 9);
							System.out.println("answer is correct!!                         ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
							flag2=true;
							flagme=false;
							numberofflag2++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
						}
						else if  (user_answer.equalsIgnoreCase("E")){	
							System.out.println("BYE!");
							String choices = scannerChoice.next();
							flag2=false;
							numberofflag2exit++;
							cn.getTextWindow().setCursorPosition(55, 4);
							money=money(money);
							keep=false;
							System.exit(0);
						}
						else {
							TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
							s_console.setTextAttributes(attrs);
							System.out.println("Your first answer is wronngg!!       ");
							TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
							s_console.setTextAttributes(attrss);
							System.out.println("please enter your second choice (E:Exit):   ");
							 wait_for_user_answer=true;				  		  	  
								while(wait_for_user_answer) {
									
								if(keypr==1) {  
									  // if keyboard button pressed
							          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
							          if(rkey==KeyEvent.VK_B) user_answer="B"; 
							          if(rkey==KeyEvent.VK_C) user_answer="C"; 
							          if(rkey==KeyEvent.VK_D) user_answer="D";
							          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
							          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
							          if(user_answer!="") {
							        	  keypr=0;  
							        	  wait_for_user_answer=false;
							          }
							          keypr=0;	         
								  }
								 Thread.sleep(20);
								}
							
							if(user_answer.equals(correctanswer)&&!time_is_over) {
								TextAttributes attrs1 = new TextAttributes(Color.GREEN, Color.BLACK);
								s_console.setTextAttributes(attrs1);
								cn.getTextWindow().setCursorPosition(0, 9);
								System.out.println("answer is correct!!                         ");
								TextAttributes attrss1 = new TextAttributes(Color.pink, Color.BLACK);
								s_console.setTextAttributes(attrss1);
								flag2=true;
								flagme=false;
								numberofflag2++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
							}
							else if  (user_answer.equalsIgnoreCase("E")){	
								System.out.println("BYE!");
								String choices = scannerChoice.next();
								flag2=false;
								numberofflag2exit++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
								keep=false;
								System.exit(0);
							}
							else {
								flag2=false;
								numberofflag2false++;
								cn.getTextWindow().setCursorPosition(55, 4);
								money=money(money);
								TextAttributes attrs1 = new TextAttributes(Color.RED, Color.BLACK);
								s_console.setTextAttributes(attrs1);
								System.out.println("answer is wronngg!!                        ");
								System.out.println("Game Oveeer!!                        ");
								TextAttributes attrss1 = new TextAttributes(Color.pink, Color.BLACK);
								s_console.setTextAttributes(attrss1);
							}
						}
					}
				}
				else 
				{
					TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
					s_console.setTextAttributes(attrs);
					System.out.println("Your first answer is wronngg!!          ");
					TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
					s_console.setTextAttributes(attrss);
					System.out.println("Please enter your second choice (E:Exit):   ");
					 wait_for_user_answer=true;				  		  	  
						while(wait_for_user_answer) {
							
						if(keypr==1) {  
							  // if keyboard button pressed
					          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
					          if(rkey==KeyEvent.VK_B) user_answer="B"; 
					          if(rkey==KeyEvent.VK_C) user_answer="C"; 
					          if(rkey==KeyEvent.VK_D) user_answer="D";
					          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
					          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
					          if(user_answer!="") {
					        	  keypr=0;  
					        	  wait_for_user_answer=false;
					          }
					          keypr=0;	         
						  }
						 Thread.sleep(20);
						}
					
					if(user_answer.equals(correctanswer)&&!time_is_over) {
						TextAttributes attrs1 = new TextAttributes(Color.GREEN, Color.BLACK);
						s_console.setTextAttributes(attrs1);
						cn.getTextWindow().setCursorPosition(0, 9);
						System.out.println("answer is correct!!                    ");
						TextAttributes attrss1 = new TextAttributes(Color.pink, Color.BLACK);
						s_console.setTextAttributes(attrss1);
						flag2=true;
						flagme=false;
						numberofflag2++;
						cn.getTextWindow().setCursorPosition(55, 4);
						money=money(money);
					}
					else if  (user_answer.equalsIgnoreCase("E")){	
						System.out.println("BYE!");
						String choices = scannerChoice.next();
						flag2=false;
						numberofflag2exit++;
						cn.getTextWindow().setCursorPosition(55, 4);
						money=money(money);
						keep=false;
						System.exit(0);
					}
					else {
						flag2=false;
						numberofflag2false++;
						cn.getTextWindow().setCursorPosition(55, 4);
						money=money(money);
						TextAttributes attrs1 = new TextAttributes(Color.RED, Color.BLACK);
						s_console.setTextAttributes(attrs1);
						System.out.println("answer is wronngg!!                        ");
						System.out.println("Game Oveeer!!                        ");
						TextAttributes attrss1 = new TextAttributes(Color.pink, Color.BLACK);
						s_console.setTextAttributes(attrss1);
					}
				}
			}
			else {
				System.out.println("You have already used your double dip joker.     ");
				System.out.println("Please enter your choice (E:Exit):     ");
				 wait_for_user_answer=true;				  		  	  
					while(wait_for_user_answer) {
						
					if(keypr==1) {  
						  // if keyboard button pressed
				          if(rkey==KeyEvent.VK_A)  user_answer="A"; 
				          if(rkey==KeyEvent.VK_B) user_answer="B"; 
				          if(rkey==KeyEvent.VK_C) user_answer="C"; 
				          if(rkey==KeyEvent.VK_D) user_answer="D";
				          if(rkey==KeyEvent.VK_F) user_answer="50%";                        	        
				          if(rkey==KeyEvent.VK_G) user_answer="double dip";    
				          if(user_answer!="") {
				        	  keypr=0;  
				        	  wait_for_user_answer=false;
				          }
				          keypr=0;	         
					  }
					 Thread.sleep(20);
					}
				
				if(user_answer.equals(correctanswer)&&!time_is_over) {
					TextAttributes attrs = new TextAttributes(Color.GREEN, Color.BLACK);
					s_console.setTextAttributes(attrs);
					cn.getTextWindow().setCursorPosition(0, 9);
					System.out.println("answer is correct!!                            ");
					TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
					s_console.setTextAttributes(attrss);
					flag2=true;
					flagme=false;
					numberofflag2++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
				}
				else if  (user_answer.equalsIgnoreCase("E")){	
					System.out.println("BYE!");
					String choices = scannerChoice.next();
					flag2=false;
					numberofflag2exit++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
					keep=false;
					System.exit(0);
				}
				else {
					flag2=false;
					numberofflag2false++;
					cn.getTextWindow().setCursorPosition(55, 4);
					money=money(money);
					TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
					s_console.setTextAttributes(attrs);
					System.out.println("answer is wronngg!!                        ");
					System.out.println("Game Oveeer!!                        ");
					TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
					s_console.setTextAttributes(attrss);
				}
			}
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		else {
			flag2=false;
			
			numberofflag2false++;
			cn.getTextWindow().setCursorPosition(55, 4);
			money=money(money);
			if(time_is_over) {
				System.out.println("");
			}
			else {
				TextAttributes attrs = new TextAttributes(Color.RED, Color.BLACK);
				s_console.setTextAttributes(attrs);
				System.out.println("answer is wronngg!!                        ");
				System.out.println("Game Oveeer!!                        ");
				TextAttributes attrss = new TextAttributes(Color.pink, Color.BLACK);
				s_console.setTextAttributes(attrss);
			}
		}	
    
   	return  flag2;
   }
	
   public static int money(int money) 
   {
       if (numberofflag2 == 0 && numberofflag2false == 1) {
           money = 0;
           System.out.println("|Money: " + money+"$          ");        }
       else if (numberofflag2 == 1 && numberofflag2false == 1) {
           money = 0;
           System.out.println("|Money: " + money+"$          ");        }
       else if (numberofflag2 == 2 && numberofflag2false == 1) {
           money = 100000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 3 && numberofflag2false == 1) {
           money = 100000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 4 && numberofflag2false == 1) {
           money = 500000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 5 && numberofflag2false == 1) {
           money = 500000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 0 && numberofflag2exit == 1) {
           money = 0;
           System.out.println("|Money: " + money+"$          ");        }
       else if (numberofflag2 == 1 && numberofflag2exit == 1) {
           money = 20000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 2 && numberofflag2exit == 1) {
           money = 100000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 3 && numberofflag2exit == 1) {
           money = 250000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 4 && numberofflag2exit == 1) {
           money = 500000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 5 && numberofflag2exit == 1) {
           money = 1000000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 0) {
           money = 0;
           System.out.println("|Money: " + money+"$          ");        }
       else if (numberofflag2 == 1) {
           money = 20000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 2) {
           money = 100000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 3) {
           money = 250000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 4) {
           money = 500000;
           System.out.println("|Money: " + money + "$");        }
       else if (numberofflag2 == 5) {
           money = 1000000;
           System.out.println("|Money: " + money + "$");        }
       return money;
   }
     
   public static void timer() 
   {
   	Timer timer = new Timer();
   	TimerTask task = new TimerTask() {
   	    double i = 20;
   	   
   	    boolean flag3=true;
   	    public void run(){
   	     if (i == 0&&flag3) {
   	    	 
   	    	    TextAttributes attrs = new TextAttributes(Color.YELLOW, Color.BLACK);
	            s_console.setTextAttributes(attrs);
	        	System.out.println("Time's up!");
	        	TextAttributes attrs1 = new TextAttributes(Color.MAGENTA, Color.BLACK);
		        s_console.setTextAttributes(attrs1);
	        	
	        	cn.getTextWindow().setCursorPosition(55, 5);
   	        	System.out.println("|Remaining Time: " + 0 );   	        	
				flag2 = false;
				time_is_over=true;
				i=-1;
				
				}
   	        if (i >= 0) {
   	           
   	    		cn.getTextWindow().setCursorPosition(55, 3);
   	    		TextAttributes attrs = new TextAttributes(Color.YELLOW, Color.BLACK);
		        s_console.setTextAttributes(attrs);
   	    		System.out.println("+-----------------------------+");
   	    		cn.getTextWindow().setCursorPosition(55, 4);
   	    	    System.out.println("|Money: " + money +"$          ");   
   	    		cn.getTextWindow().setCursorPosition(55, 5);
   	    		if(i<10) {
   	    			
   	    			System.out.println("|Remaining Time: " + i--);
   	   	        	cn.getTextWindow().setCursorPosition(55, 6);
   	    		}
   	    		else {
   	    			System.out.println("|Remaining Time: " + i-- );
   	   	        	cn.getTextWindow().setCursorPosition(55, 6);
   	    		}
   	        	
   	            if (ishalfjokerUsed == false) 
   	            {
						System.out.println("|%50 (press f)            ");
					}
   	            else
   	            {
						System.out.println("|x                        ");
					}
   	            cn.getTextWindow().setCursorPosition(55, 7);
   	            if (isDoubledipUsed == false) 
   	            {
						System.out.println("|Double Dip (press g)      ");
					}
   	            else 
   	            {
						System.out.println("|x                        ");
					}
   	             	            	  	            
   	            cn.getTextWindow().setCursorPosition(55, 8);
   	            System.out.println("+-----------------------------+");
   	            TextAttributes attrs111 = new TextAttributes(Color.pink, Color.BLACK);
		        s_console.setTextAttributes(attrs111);
   	            
   	            if(!flag2) {
   	            	i=0;
   	            	
   		   	        flag3=false;
   	            }
   	            if(!flagme) {
   	            	i=0;
   	             flag3=false;
   	            }
   	            
   	        }
   	       
   	        if(i == 0&&!flag3) {
   	        	flag2 = false;
   	     	   i=-1;
   	        }
   	        if(i==0&&!flagme) {
   	        	i=-1;
   	        }
   	    }
   	
   	};
   	
   	
		timer.scheduleAtFixedRate(task,0, 1000);
   }
        
   public static void removeDuplicate(String input)
   {
       //convert the string to array by splitting it in words where space comes
       String[] words=input.split(" ");
       //put a for loop for outer comparison where words will start from "i" string
       for(int i=0;i<words.length;i++)
       {
           //check if already duplicate word is replaced with null
           if(words[i]!=null)
           {
               //put a for loop to compare outer words at i with inner word at j
               for(int j =i+1;j<words.length;j++)
               {
                   //if there is any duplicate word then make is Null
                   if(words[i].equals(words[j]))
                   {
                       words[j]=null;
                   }
               }
           }
       }
       //Print the output string where duplicate has been replaced with null
       int x=0;
      
       String[] words_print=new String[words.length];
       
       for(int k=0;k<words.length;k++)
       {
           //check if word is a null then don't print it
           if(words[k]!=null)
           {
        	   words_print[x]=words[k];
        	   x++;       	         	   
           }
       }
       System.out.format("+--------------------------------------------------------+%n");
       System.out.format("|                       Word Cloud                       |%n");
       System.out.format("+--------------------------------------------------------+%n");
       String leftAlignFormat = "| %-16s | %-16s | %-16s |%n";
       for(int i=0;i<x-2;i=i+3) {
    	   
    	   System.out.format(leftAlignFormat, words_print[i] , words_print[i+1],words_print[i+2]);
    	   
       }
       System.out.format("+---------------------------------------------------------+%n");
   }
  
   static String mostFrequent(String[] inputArr) {
	   	 int maxFreq = 0;
	        String mostRepeated = null;

	        for (int i = 0; i < inputArr.length; i++) {
	        	if(inputArr[i]!=null) {
	            String temp = inputArr[i];
	            int count1 = 1;
	            for (int j = i + 1; j < inputArr.length; j++) {
	                if (temp.equalsIgnoreCase(inputArr[j]))
	                    count1++;
	            }
	            if (maxFreq < count1) {
	               maxFreq = count1;
	               mostRepeated = temp;
	            }
	        }}
	        //System.out.println(mostRepeated + ": " + maxFreq);
	   	
	   	
	   	
	   	return mostRepeated;
	   }
   private static final Console s_console;
   static
   {
       s_console = Enigma.getConsole("Hellow World!");
   }
}





