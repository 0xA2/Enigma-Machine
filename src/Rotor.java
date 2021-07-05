class Rotor{

	// Attribures
	private char[] WiringForward;
	private char[] WiringBackward;
	private int Offset;
	private char Turnover;
	private Rotor Prev;


	// Constructor
	Rotor(char[] wiringForward, char[] wiringBackwards, int offset, char turnover, Rotor prev){

		WiringForward = wiringForward;
		WiringBackward = wiringBackwards;
		Offset = offset;
		Turnover = Character.toUpperCase(turnover);
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
		if( (Prev != null) && (curPosition == Turnover) ){
			Prev.step();
		}
		Offset = mod((Offset + 1),26);
	}

	public char encrypt(char c, boolean forward){
		if(forward){
			return (char)((mod(WiringForward[mod(c-65+Offset,26)]-65-Offset,26))+65);
		}
		return (char)((mod(WiringBackward[mod(c-65+Offset,26)]-65-Offset,26))+65);
	}

}
