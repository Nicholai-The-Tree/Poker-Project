/*
 * card class for game
 * fields: value and suit of a card
 * methods: getVal(), getSuit()
 */

import java.util.*;

public class card{
	private int value;
	private String suit;
	
	public card(int val,String suit){
		this.value = val;
		this.suit = suit;
	}
	
	public String getSuit(){
		return suit;
	}
	
	public int getVal(){
		return value;
	}
}
