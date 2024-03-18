





import java.awt.Color;
import enigma.console.*;
import enigma.core.Enigma;
import java.io.FileInputStream; 
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.*;

public class project_1 {
	
	static enigma.console.Console cn=Enigma.getConsole("Who Wants to be A Millionaire",90,35,20,10);	
	public static void main(String[] args) throws FileNotFoundException, IOException{
    
				
				Scanner scannerChoice = new Scanner(System.in);
				Scanner scannerFile = new Scanner(System.in);	
				
				//menu.
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
				try {
					game myGame = new game();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
														    
  

      }
	private static final Console s_console;
    static
    {
        s_console = Enigma.getConsole("Hellow World!");
    }
}
	
	
	
	
	
	
	
	
	
	
	



