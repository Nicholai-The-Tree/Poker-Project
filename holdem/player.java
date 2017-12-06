/*
 * player class for game
 * fields: name, balance, hand, fold
 * methods: getName(), getBal(), setFold(), fold(), 
 * 			bet(), win(), insert(), getHand()
 */

import java.util.*;

public class player{
	private String name;
	private int balance;
	private card[] hand = new card[2];
	private boolean fold;
	private Scanner in = new Scanner(System.in);
	
	public player(String name, int bal){
		this.name = name;
		balance = bal;
		fold = false;
	}
	
	public player(String name){
		this.name = name;
		fold = false;
	}
	
	public void setFold(){
		fold = true;
	}
	
	public boolean fold(){
		return fold;
	}
	
	public String getName(){
		return name;
	}
	
	public int getBal(){
		return balance;
	}
	
	//set player's hand
	public void insert(card cards){
		if(hand[0]!=null)
			hand[0]=cards;
		else
			hand[1]=cards;
	}
	
	public card[] getHand(){
		return hand;
	}
	
	public void bet(int bet){
		balance -= bet;
	}
	
	public void win(int pot){
		balance += pot;
	}
	
	public String choice(int bet){
		if(bet>0){
			System.out.println("Choose wisely");
			System.out.println("1.	Call");
			System.out.println("2.	Raise");
			System.out.println("3.	Fold");
			return in.nextLine();
		}
		else{
			System.out.println("Choose wisely");
			System.out.println("1.	Check");
			System.out.println("2.	Bet");
			System.out.println("3.	Fold");
			return in.nextLine();
		}
	}
}
