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
