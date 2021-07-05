import java.io.*;
import java.util.*;

public class Plugboard{

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