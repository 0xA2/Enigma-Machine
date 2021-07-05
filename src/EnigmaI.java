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
		// By default the Plugboard is initialized so no swaps happen.

		rotorList = new RotorList();

		left = rotorList.getRotor(1);
		mid = rotorList.getRotor(2);
		mid.setPrev(left);
		right = rotorList.getRotor(3);
		right.setPrev(mid);

		UKW_B = new Reflector();

		plugboard = new Plugboard();
	}

	EnigmaI(String r1, String r2, String r3){

		// Alternative constructor that recieves intended rotors to use as parameters
		// By default the Plugboard is initialized so no swaps happen.

		rotorList = new RotorList();

		left = rotorList.getRotor(Integer.parseInt(r1));
		mid = rotorList.getRotor(Integer.parseInt(r2));
		mid.setPrev(left);
		right = rotorList.getRotor(Integer.parseInt(r3));
		right.setPrev(mid);

		UKW_B = new Reflector();

		plugboard = new Plugboard();

	}


	// Methods

	public char getEncrypt(char c, boolean showSteps){
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