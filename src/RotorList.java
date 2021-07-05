public class RotorList{

	// Class to recreate the rotor settings used by the real Enigma Machine and mapping said settings to the corresponding rotor number

	private char[] rotor1Forward = {'E','K','M','F','L','G','D','Q','V','Z','N','T','O','W','Y','H','X','U','S','P','A','I','B','R','C','J'};
	private char[] rotor1Backward = {'U','W','Y','G','A','D','F','P','V','Z','B','E','C','K','M','T','H','X','S','L','R','I','N','Q','O','J'};
	private char rotor1Turnover = 'Q';
	private char[] rotor2Forward = {'A','J','D','K','S','I','R','U','X','B','L','H','W','T','M','C','Q','G','Z','N','P','Y','F','V','O','E'};
	private char[] rotor2Backward = {'A','J','P','C','Z','W','R','L','F','B','D','K','O','T','Y','U','Q','G','E','N','H','X','M','I','V','S'};
	private char rotor2Turnover = 'E';
	private char[] rotor3Forward = {'B','D','F','H','J','L','C','P','R','T','X','V','Z','N','Y','E','I','W','G','A','K','M','U','S','Q','O'};
	private char[] rotor3Backward = {'T','A','G','B','P','C','S','D','Q','E','U','F','V','N','Z','H','Y','I','X','J','W','L','R','K','O','M'};
	private char rotor3Turnover = 'V';
	private char[] rotor4Forward = {'E','S','O','V','P','Z','J','A','Y','Q','U','I','R','H','X','L','N','F','T','G','K','D','C','M','W','B'};
	private char[] rotor4Backward = {'H','Z','W','V','A','R','T','N','L','G','U','P','X','Q','C','E','J','M','B','S','K','D','Y','O','I','F'};
	private char rotor4Turnover = 'J';
	private char[] rotor5Forward = {'V','Z','B','R','G','I','T','Y','U','P','S','D','N','H','L','X','A','W','M','J','Q','O','F','E','C','K'};
	private char[] rotor5Backward = {'Q','C','Y','L','X','W','E','N','F','T','Z','O','S','M','V','J','U','D','K','G','I','A','R','P','H','B'};
	private char rotor5Turnover = 'Z';

	private Rotor r1 = new Rotor(rotor1Forward,rotor1Backward,0,rotor1Turnover,null);
	private Rotor r2 = new Rotor(rotor2Forward,rotor2Backward,0,rotor2Turnover,null);
	private Rotor r3 = new Rotor(rotor3Forward,rotor3Backward,0,rotor3Turnover,null);
	private Rotor r4 = new Rotor(rotor4Forward,rotor4Backward,0,rotor4Turnover,null);
	private Rotor r5 = new Rotor(rotor5Forward,rotor5Backward,0,rotor5Turnover,null);

	private Rotor[] rotorMap = new Rotor[5];

	RotorList(){
		rotorMap[0] = r1;
		rotorMap[1] = r2;
		rotorMap[2] = r3;
		rotorMap[3] = r4;
		rotorMap[4] = r5;
	}

	public Rotor getRotor(int r){
		Rotor ret;
		switch(r){
			case 1:
				ret = rotorMap[0];
				break;
			case 2:
				ret = rotorMap[1];
				break;
			case 3:
				ret = rotorMap[2];
				break;
			case 4:
				ret = rotorMap[3];
				break;
			case 5:
				ret = rotorMap[4];
				break;
			default:
				ret = rotorMap[0];
				break;
		}
		return ret;
	}

}