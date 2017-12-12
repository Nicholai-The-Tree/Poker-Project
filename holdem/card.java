/*
 * card class for game
 */

import java.util.*;

public class card implements Comparable<card>{
	private int value;
	private String suit;
	
	public card(int val,String suit){
		this.value = val;
		this.suit = suit;
	}
	
	//get suit value
	public String getSuit(){
		return suit;
	}
	
	//get card value
	public int getVal(){
		return value;
	}
	
	//compare card values (ascending order)
	public int compareTo(card c){
		if(c.getVal()==value)
			return 0;
		else if(c.getVal()>value)
			return -1;
		else
			return 1;
	}
	
	public String toString(){
		return this.suit + " " + this.value;
	}
}
