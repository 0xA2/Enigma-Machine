import java.io.*;
import java.util.*;


class Rotor{

	// Attribures
	private char[] WiringForward;
	private char[] WiringBackward;
	private int Offset;
	private char Notch;
	private Rotor Prev;


	// Constructor

	Rotor(char[] wiringForward, char[] wiringBackwards, int offset, char notch, Rotor prev){

		WiringForward = wiringForward;
		WiringBackward = wiringBackwards;
		Offset = offset;
		Notch = Character.toUpperCase(notch);
		Prev = prev;

	}


	// Methods

	// Auxiliary function for modular arithmetic
	private int mod(int a, int b){ return (a>=0) ? a%b : ((a%b)+b)%b; }

	public int getOffset(){
		return Offset;
	}

	public void setOffset(int newOffset){
		Offset = newOffset;
	}

	public void setPrev(Rotor prev){
		Prev = prev;
	}

	public void step(){
		char curPosition = (char)(Offset+65);
		if( (Prev != null) && (curPosition == Notch) ){
			Prev.step();
		}
		Offset = (Offset + 1)%26;
	}

	public char encrypt(char c, boolean forward){
		if(forward){
			return (char)((mod(WiringForward[mod(c-65+Offset,26)]-65-Offset,26))+65);
		}
		return (char)((mod(WiringBackward[mod(c-65+Offset,26)]-65-Offset,26))+65);
	}

}


class RotorList{

	// Class to recreate the rotor settings used by the real Enigma Machine and mapping said settings to the corresponding rotor number

	private char[] rotor1Forward = {'E','K','M','F','L','G','D','Q','V','Z','N','T','O','W','Y','H','X','U','S','P','A','I','B','R','C','J'};
	private char[] rotor1Backward = {'U','W','Y','G','A','D','F','P','V','Z','B','E','C','K','M','T','H','X','S','L','R','I','N','Q','O','J'};
	private char rotor1Notch = 'Q';
	private char[] rotor2Forward = {'A','J','D','K','S','I','R','U','X','B','L','H','W','T','M','C','Q','G','Z','N','P','Y','F','V','O','E'};
	private char[] rotor2Backward = {'A','J','P','C','Z','W','R','L','F','B','D','K','O','T','Y','U','Q','G','E','N','H','X','M','I','V','S'};
	private char rotor2Notch = 'E';
	private char[] rotor3Forward = {'B','D','F','H','J','L','C','P','R','T','X','V','Z','N','Y','E','I','W','G','A','K','M','U','S','Q','O'};
	private char[] rotor3Backward = {'T','A','G','B','P','C','S','D','Q','E','U','F','V','N','Z','H','Y','I','X','J','W','L','R','K','O','M'};
	private char rotor3Notch = 'V';
	private char[] rotor4Forward = {'E','S','O','V','P','Z','J','A','Y','Q','U','I','R','H','X','L','N','F','T','G','K','D','C','M','W','B'};
	private char[] rotor4Backward = {'H','Z','W','V','A','R','T','N','L','G','U','P','X','Q','C','E','J','M','B','S','K','D','Y','O','I','F'};
	private char rotor4Notch = 'J';
	private char[] rotor5Forward = {'V','Z','B','R','G','I','T','Y','U','P','S','D','N','H','L','X','A','W','M','J','Q','O','F','E','C','K'};
	private char[] rotor5Backward = {'Q','C','Y','L','X','W','E','N','F','T','Z','O','S','M','V','J','U','D','K','G','I','A','R','P','H','B'};
	private char rotor5Notch = 'Z';

	private Rotor r1 = new Rotor(rotor1Forward,rotor1Backward,0,rotor1Notch,null);
	private Rotor r2 = new Rotor(rotor2Forward,rotor2Backward,0,rotor2Notch,null);
	private Rotor r3 = new Rotor(rotor3Forward,rotor3Backward,0,rotor3Notch,null);
	private Rotor r4 = new Rotor(rotor4Forward,rotor4Backward,0,rotor4Notch,null);
	private Rotor r5 = new Rotor(rotor5Forward,rotor5Backward,0,rotor5Notch,null);

	private Map<String, Rotor> rotorMap = new HashMap<>();

	RotorList(){
		String[] keys = {"1","2","3","4","5"};
		rotorMap.put(keys[0],r1);
		rotorMap.put(keys[1],r2);
		rotorMap.put(keys[2],r3);
		rotorMap.put(keys[3],r4);
		rotorMap.put(keys[4],r5);
	}

	public Rotor getRotor(String r){
		return rotorMap.get(r);
	}

}


class Reflector{

	// Original refelctor (UKW-B reflector) used in the real Enigma Machine


	// Attributes
	private char[] Wiring;


	// Constructor

	Reflector(){
		char[] w = {'Y','R','U','H','Q','S','L','D','P','X','N','G','O','K','M','I','E','B','F','Z','C','W','V','J','A','T'};
		Wiring = w;
	}


	// Methods

	public char encrypt(char c){
		return (char)(Wiring[c-65]);
	}

}

class Plugboard{

	// Attributes
	private Map<Character, Character> plugboard;


	// Constructor

	Plugboard(){
		plugboard = new HashMap<Character,Character>();
	}


	// Methods

	public char swap(char c){
		return plugboard.get(c);
	}

	public boolean contains(char c){
		return plugboard.containsValue(c);
	}

	public void clearPlugboard(){
		plugboard.clear();
	}

	public void addPair(char a, char b){
		plugboard.put(a,b);
		plugboard.put(b,a);
	}

}


class EnigmaI{

	// Attributes
	private RotorList rotorList;
	private Rotor left;
	private Rotor mid;
	private Rotor right;
	private Reflector UKW_B;
	private Plugboard plugboard;


	// Constructor

	EnigmaI(){

		// Default settings use rotors 1,2 and 3 (from left to right) and 'AAA' as the starting position.
		// By default, the Plugboard is initialized with same letter pairs meaning no swaps happen.

		rotorList = new RotorList();

		left = rotorList.getRotor("1");
		mid = rotorList.getRotor("2");
		right = rotorList.getRotor("3");

		UKW_B = new Reflector();

		plugboard = new Plugboard();
	}

	EnigmaI(String r1, String r2, String r3){

		// Alternative constructor that recieves intended rotors to use as parameters
		// By default, the Plugboard is initialized with same letter pairs meaning no swaps happen.

		rotorList = new RotorList();

		left = rotorList.getRotor(r1);
		mid = rotorList.getRotor(r2);
		right = rotorList.getRotor(r3);

		UKW_B = new Reflector();

		plugboard = new Plugboard();

	}


	// Methods

	public char encrypt(char c, boolean showSteps){
		right.step();

		// Swap letter if it's connected to another letter through the plugboard
		if(plugboard.contains(c)){ c = plugboard.swap(c);}

		// Right Rotor Encryption
		c = right.encrypt(c,true);
		if(showSteps){System.out.println("Right Rotor Forward > " + c);}

		// Mid Rotor Encryption
		c = mid.encrypt(c,true);
		if(showSteps){System.out.println("Mid Rotor Forward > " + c);}

		// Left Rotor Encryption
		c = left.encrypt(c,true);
		if(showSteps){System.out.println("Left Rotor Forward > " + c);}

		// Refelctor Encryption
		c = UKW_B.encrypt(c);
		if(showSteps){System.out.println("Refelctor > " + c);}

		// Left Rotor Encryption
		c = left.encrypt(c,false);
		if(showSteps){System.out.println("Left Rotor Backward > " + c);}

		// Mid Rotor Encryption
		c = mid.encrypt(c,false);
		if(showSteps){System.out.println("Mid Rotor Backward > " + c);}

		// Right Rotor Encryption
		c = right.encrypt(c,false);
		if(showSteps){System.out.println("Right Rotor Backward > " + c);}

		// Swap letter if it's connected to another letter through the plugboard
		if(plugboard.contains(c)){ c = plugboard.swap(c); }

		if(showSteps){System.out.println("--------------------------------");}
		return c;
	}

	public String getRotorSettings(){
		return "" + (char)(left.getOffset() + 65) + (char)(mid.getOffset() + 65) + (char)(right.getOffset() + 65);
	}

	// Rotaring leftmost rotor
	public void setLeftPosition(String s){
		left.setOffset(Character.toUpperCase(s.charAt(0))-65);
	}

	// Rotating middle rotor
	public void setMidPosition(String s){
		mid.setOffset(Character.toUpperCase(s.charAt(0))-65);
	}

	// Rotating rightmost rotor
	public void setRightPosition(String s){
		right.setOffset(Character.toUpperCase(s.charAt(0))-65);
	}

	public Plugboard getPlugboard(){
		return plugboard;
	}

}


class EnigmaMachine{

	// Auxiliary functions to handle user input
	static boolean checkValidRotor(String c){
		return (c.compareTo("1") == 0) || (c.compareTo("2") == 0) || (c.compareTo("3") == 0) || (c.compareTo("4") == 0) || (c.compareTo("5") == 0);
	}

	static boolean checkValidPairNumber(String c){
		return (c.compareTo("1") == 0) || (c.compareTo("2") == 0) || (c.compareTo("3") == 0) || (c.compareTo("4") == 0) || (c.compareTo("5") == 0) || (c.compareTo("6") == 0) || (c.compareTo("7") == 0) || (c.compareTo("8") == 0) || (c.compareTo("9") == 0) || (c.compareTo("10") == 0);
	}


	static void printMenu(String title, String[] options, int identation){
		char[] border = new char[title.length()+(identation*2)];
		Arrays.fill(border,'-');
		System.out.println(String.valueOf(border));
		for(int i = 0; i<identation; i++){ System.out.print(" "); }
		System.out.println(title);
		System.out.println(String.valueOf(border));
		for(int i = 0; i<options.length; i++){
			System.out.println( (i+1) + ") " + options[i]);
		}
	}

	public static void main(String[] args){

		Scanner input = new Scanner(System.in);
		EnigmaI machine = new EnigmaI();

		// Flag for settings option 4: "Show/Hide Encryption Settings"
		boolean showSteps = false;

		// Options to print in main menu
		String[] mainOptions = {"Enter message", "Edit settings", "Exit"};

		// Main loop to keep the machine running
		while(true){
			printMenu("Enigma Machine", mainOptions, 9);
			System.out.print("> ");
			try {

				// Selecting option from the main menu
				String op1 = input.nextLine();
				switch (op1) {

					// Entering message to encrypt/decrypt
					case "1":
						try{
							System.out.print("Input Message > ");
							String plaintext = input.nextLine();
							String out = "";
							for(int i = 0; i<plaintext.length(); i++){
								char curLetter = plaintext.charAt(i);
								if(Character.isLetter(curLetter)){ out += machine.encrypt(Character.toUpperCase(curLetter),showSteps); }
							}
							System.out.println("Current rotor positions > " + machine.getRotorSettings());
							System.out.println("Output Message > " + out);
						}
						catch ( Exception e ){ System.out.println("Error during encryption/decryption. Exiting..."); System.exit(1); }
						break;

					// Change machine settings (opening settings menu)
					case "2":
						String[] settingsOptions = {"Change Rotors", "Change Rotor Positions", "Change Plugboard", "Show/Hide Encryption Steps", "Back"};
						boolean flag = true;

						// Settings menu loop
						while(flag){
							printMenu("Machine Settings",settingsOptions,8);
							System.out.print("> ");
							try{
								String op2 = input.nextLine();
								switch(op2){

									// Change Rotors
									case "1":
										System.out.println("[Choose from Rotors 1 to 5]");
										System.out.println("---------------------------");

										String[] newRotors = new String[3];

										// Choose rotor for leftmost position
										System.out.print("Left Rotor (slow rotor) > ");
										newRotors[0] = input.nextLine();
										if(!checkValidRotor(newRotors[0])){
											System.out.println("Invalid Rotor. No changes applied."); 
											break;
										}

										// Choose rotor for middle position
										System.out.print("Middle Rotor > ");
										newRotors[1] = input.nextLine();
										if(!checkValidRotor(newRotors[1])){
											System.out.println("Invalid Rotor. No changes applied."); 
											break;
										}

										// Choose rotor for rightmost position
										System.out.print("Right Rotor (fast rotor) > ");
										newRotors[2] = input.nextLine();
										if(!checkValidRotor(newRotors[2])){
											System.out.println("Invalid Rotor. No changes applied.");
											break;
										}

										machine = new EnigmaI(newRotors[0],newRotors[1],newRotors[2]);
										System.out.println("----------------------------------------");
										System.out.println("Rotors have been changed\nRotor Positions have been reset to 'AAA'");

										break;

									// Change Rotor Positions
									case "2":
										System.out.println("[Choose Rotor Positions A-Z]");
										System.out.println("----------------------------");

										String[] newPositions = new String[3];

										// Choose position for leftmost rotor
										System.out.print("Left Rotor Position  > ");
										newPositions[0] = input.nextLine();
										if( (newPositions[0].length() != 1) || !Character.isLetter(newPositions[0].charAt(0))){
											System.out.println("Invalid Rotor. No changes applied.");
											break;
										}

										// Choose position for middle rotor
										System.out.print("Middle Rotor Position  > ");
										newPositions[1] = input.nextLine();
										if( (newPositions[1].length() != 1) || !Character.isLetter(newPositions[1].charAt(0))){
											System.out.println("Invalid Rotor. No changes applied.");
											break;
										}

										// Choose position for rightmost rotor
										System.out.print("Right Rotor Position  > ");
										newPositions[2] = input.nextLine();
										if( (newPositions[2].length() != 1) || !Character.isLetter(newPositions[2].charAt(0))){
											System.out.println("Invalid Rotor. No changes applied.");
											break;
										}

										machine.setLeftPosition(newPositions[0]);
										machine.setMidPosition(newPositions[1]);
										machine.setRightPosition(newPositions[2]);

										System.out.println("-----------------------------");
										System.out.println("Current Rotor Positions > " + machine.getRotorSettings());
										break;

									// Change Plugboard
									case "3":
										System.out.println("[Choose letter pairs for the plugboard]");
										System.out.println("---------------------------------------");
										System.out.print("Number of pairs to insert (1-10) > ");
										String pairNumber = input.nextLine();
										if(!checkValidPairNumber(pairNumber)) {
											System.out.println("----------------------------------");
											System.out.println("Invalid number for plugboard pairs");
											break;
										}
										machine.getPlugboard().clearPlugboard();
										for(int i = 0; i < Integer.valueOf(pairNumber); i++){
											System.out.println("[Selecting pair number " + (i+1) + "]");
											System.out.print("First Letter > ");
											String first = input.nextLine();
											if( (first.length() != 1) || !Character.isLetter(first.charAt(0)) || machine.getPlugboard().contains(first.charAt(0))){
												System.out.println("Invalid letter, skipping..."); continue;
											}

											System.out.print("Second Letter > ");
											String second = input.nextLine();
											if( (first.length() != 1) || !Character.isLetter(first.charAt(0)) || machine.getPlugboard().contains(first.charAt(0))){
												System.out.println("Invalid letter, skipping..."); continue;
											}

											machine.getPlugboard().addPair(Character.toUpperCase(first.charAt(0)), Character.toUpperCase(second.charAt(0)));
											System.out.println("---------------------------------");
											System.out.println("Added pair (" + Character.toUpperCase(first.charAt(0)) + "," + Character.toUpperCase(second.charAt(0)) + ") to the plugboard");
											System.out.println("---------------------------------");
										}

										break;

									// Show/Hide Encryption Steps
									case "4":
										if(showSteps){
											showSteps = false;
											System.out.println("> Encryption steps are now hidden");
										}
										else{
											showSteps = true;
											System.out.println("> Encryption steps will now be shown");
										}
										break;

									// Back to main menu
									case "5":
										flag = false;
										break;

									default:
										System.out.println("> Invalid option");
										break;
								}
							}
							catch( Exception e ){ System.out.println("Invalid input. Exiting..."); System.exit(1);}
						}
						break;

					// Close Application
					case "3":
						System.out.println("Exiting...");
						System.exit(0);
						break;

					default:
						System.out.println("> Invalid option");
						break;
				}
			}
			catch( Exception e ){ System.out.println("Invalid input. Exiting..."); System.exit(1);}
		}
	}
}

