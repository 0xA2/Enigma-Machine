import java.io.*;
import java.util.*;

class EnigmaMachine{

	// Auxiliary functions to handle user input
	static boolean checkValidRotor(String c, TreeSet<String> usedRotors){
		if(usedRotors.contains(c)){ return false; }
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
								if(Character.isLetter(curLetter)){ out += machine.getEncrypt(Character.toUpperCase(curLetter),showSteps); }
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
										TreeSet<String> usedRotors = new TreeSet<String>();

										// Choose rotor for leftmost position
										System.out.print("Left Rotor (slow rotor) > ");
										newRotors[0] = input.nextLine();
										if(!checkValidRotor(newRotors[0], usedRotors)){
											System.out.println("Invalid Rotor. No changes applied."); 
											break;
										}
										usedRotors.add(newRotors[0]);

										// Choose rotor for middle position
										System.out.print("Middle Rotor > ");
										newRotors[1] = input.nextLine();
										if(!checkValidRotor(newRotors[1], usedRotors)){
											System.out.println("Invalid Rotor. No changes applied."); 
											break;
										}
										usedRotors.add(newRotors[1]);

										// Choose rotor for rightmost position
										System.out.print("Right Rotor (fast rotor) > ");
										newRotors[2] = input.nextLine();
										if(!checkValidRotor(newRotors[2], usedRotors)){
											System.out.println("Invalid Rotor. No changes applied.");
											break;
										}
										usedRotors.add(newRotors[2]);

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
											if( (second.length() != 1) || !Character.isLetter(second.charAt(0)) || machine.getPlugboard().contains(second.charAt(0))){
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