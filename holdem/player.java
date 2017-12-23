/*
 * player class for game
 */

import java.util.*;
import javax.swing.*;

public class player implements Comparable<player>{
	private String name;
	private int balance, lastBet, rank, highcard;
	private card[] hand = new card[2];
	private boolean fold, small;
	private Scanner in = new Scanner(System.in);
	
	public player(){}
	
	public player(String name, int bal){
		this.name = name;
		balance = bal;
		fold = false;
	}
	
	public player(String name){
		this.name = name;
		fold = false;
	}
	
	//player folds
	public void setFold(){
		fold = true;
	}
	
	//see if player has folded or not
	public boolean fold(){
		return fold;
	}
	
	//make player small blind
	public void setBlind(){
		small = true;
	}
	
	//see if player is small blind
	public boolean blind(){
		return small;
	}
	
	//get player's name
	public String getName(){
		return name;
	}
	
	//get player's balance
	public int getBal(){
		return balance;
	}
	
	//see the last bet a player made
	public int getBet(){
		return lastBet;
	}
	
	//set player's last bet to 0
	public void setBet(){
		lastBet=0;
	}
	
	//set player's hand
	public void insert(card cards){
		if(hand[0]==null)
			hand[0]=cards;
		else{
			hand[1]=cards;
			if(hand[0].getVal()<hand[1].getVal())
				highcard = hand[1].getVal();
			else
				highcard = hand[0].getVal();
		}
	}
	
	//get player's hand
	public card[] getHand(){
		return hand;
	}
	
	//get highest card
	public int high(){
		return highcard;
	}
	
	//side effects for a player bet
	public void bet(int bet){
		balance -= bet;
		lastBet = bet;
	}
	
	//side effects for a player's winning hand
	public void win(int pot){
		balance += pot;
	}
	
	//possible choices a player can make
	public int choice(int bet){
		if(bet>0){
			Object[] choices = { "Call","Raise","All-In","Fold" };
			int val = JOptionPane.showOptionDialog(null, this.name + " what shall you do?\nbalance: $" + this.balance + "\nhand: " + this.hand[0] + "    " + this.hand[1], "decisions",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				choices, choices[0]);
			return val;
		}
		else{
			Object[] choices = { "Check","Bet","All-In","Fold" };
			int val = JOptionPane.showOptionDialog(null, this.name + " what shall you do?\nbalance: $" + this.balance + "\nhand: " + this.hand[0] + "    " + this.hand[1], "decisions",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				choices, choices[0]);
			return val;
		}
	}
	
	
	//set a rank for a player
	public void setRank(int rank){
		this.rank = rank;
	}
	
	//get rank of player's hand
	public int getRank(){
		return rank;
	}
	
	//compare player ranks (descending order)
	public int compareTo(player p){
		if(p.getRank()==rank)
			return 0;
		else if(p.getRank()>rank)
			return 1;
		else
			return -1;
	}
	
	public String toString(){
		return "name: " + this.name + "\tbalance: $" + this.balance + "\tcards: (" + hand[0] + "," + hand[1] + ")" + "\trank: " + this.rank;
	}
}
