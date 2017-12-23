/*
 * game class for texas hold'em
 *
*/ 

import java.util.*;
import javax.swing.*;
@SuppressWarnings("unchecked")
public class game{
	
	private String[] suits = { "H", "S", "D", "C" }; 
	private LinkedList<player> players;
	private LinkedList<card> deck = new LinkedList<card>();
	private LinkedList<card> river = new LinkedList<card>();
	private LinkedList<card> temp = new LinkedList<card>();
	private player highestBet = new player();
	private int pot, big, small, highBet, bet, choice;
	private boolean gameover = false, start = true;
	private Scanner in = new Scanner(System.in);
	
	public game(LinkedList<player> players,boolean blind){
		this.players = players;
		Collections.shuffle(this.players);
		for(String s:suits){
			for(int i = 2;i<=14;++i){
				deck.add(new card(i,s));
				temp.add(new card(i,s));
		}}
		if(blind){
			small = Integer.parseInt(JOptionPane.showInputDialog("Small blind value?"));
			big = small*2;
		}
		else
			small = Integer.parseInt(JOptionPane.showInputDialog("Ante value?"));
	}
	
	//shuffle cards
	public void shuf(){
		Collections.shuffle(deck);
	}
	
	//deals 1 card to each player in 2 cycles
	public void deal(){
		deck = temp;
		player p;
		shuf();
		for(int i = 0;i<players.size()*2;++i){
			p = setLast();
			p.insert(deck.pop());
		}
	}
	
	//table where all players sit
	//dealer is always the last person in ll players
	//for blind game, small is 1st and big is 2nd in the list
	public void table(){
		player p;
		//a loop for each hand
		while(!gameover){
			//blind game
			if(small>0){
				//set pot with blinds
				if(start==true)
					blindBets();
				//player up to play
				p=setLast();
				//remove if player has illegal balance
				if(p.getBal()<=0)
					players.removeLast();
				else{
					//if player hasn't folded
					if(!p.fold()){
						decisions(p);
					}
				}
			}
			
			//ante game
			else{
				//set pot with antes
				if(start==true)
					anteBets();
				//player up to play
				p=setLast();
				//remove if player has illegal balance
				if(p.getBal()<=0)
					players.removeLast();
				else{
					//if player hasn't folded
					if(!p.fold()){
						decisions(p);
					}
				}
			}
			
			//only quit when 1 player remains
			if(players.size()==1)
				gameover = true;
		}
	}
	
	//add to pot when bets are made and set player's balance
	public void setPot(player p,int bet){
		p.bet(bet);
		pot+=bet;
	}
	
	//a round of a hand 
	public void flip(){
		//first 3 cards
		if(river.size()==0){
			resetOrder();
			deck.pop();
			System.out.println("\t\tTable cards:\n");
			river.add(deck.pop());
			river.add(deck.pop());
			river.add(deck.pop());
			System.out.println("\t"+river+"\n");
		}
		//4th and 5th cards
		else if(river.size()<5){
			resetOrder();
			deck.pop();
			river.add(deck.pop());
			System.out.println("\t\tTable cards:\n");
			System.out.println("\t"+river+"\n");
		}
		//choose winner and reset
		else{
			//set players' ranks
			winner();
			//sort players by rank (highest should be first)
			Collections.sort(players);
			for(player p:players)
				System.out.println(p.getName() + " " + p.getRank());
			player winner = players.peek();
			JOptionPane.showMessageDialog(null,winner.getName() + " wins!!!!", "alert", JOptionPane.INFORMATION_MESSAGE);
			winner.win(pot);
			pot=0;
			
			//resest order and move blinds
			resetOrder();
			resetRanks();
			player p = setLast();
			(players.peek()).setBlind();
			river.clear();
			start = true;
		}
	}
	
	//remove and return the player at the front and add them to the end
	public player setLast(){
		player p = players.pop();
		players.addLast(p);
		return p;
	}
	
	//reset ranks of players to 0
	public void resetRanks(){
		for(player p : players)
			p.setRank(0);
	}
	
	//sets last bet for every player to 0
	public void resetBets(){
		for(player p : players)
			p.setBet();
	}
	
	//resets order
	public void resetOrder(){
		resetBets();
		player p;
		for(int i=0;i<players.size();++i){
			p=setLast();
			if(p.blind()){
				p=setLast();
				break;
			}
		}
		highestBet = players.peekLast();
		System.out.println();
	}
	
	//blind bets
	public void blindBets(){
		player p;
		deal();
		p = setLast();	p.setBlind();	setPot(p,small);
		p = setLast();	setPot(p,small*2);
		highBet = small*2;
		highestBet = p;
		start = false;
	}
	
	//ante bets
	public void anteBets(){
		deal();
		for(player p:players)
			setPot(p,small);
		start=false;
	}
	
	//player's decisions
	public void decisions(player p){
		choice = 9999;
		
		//check if player has current highest bet
		if(p.equals(highestBet)){
			highBet=0;
			choice = p.choice(highBet);
		}
		
		//when player isn't highest better
		if(choice==9999)
			choice = p.choice(highBet);
		switch(choice){
			//call/check
			case 0: call(p); break;
			//raise/bet
			case 1: bet(p); break;
			//all in
			case 2: allin(p); break;
			//fold
			case 3: p.setFold(); break;
			//show menu again for errors
			default: decisions(p);
		}
	}
	
	//checks for flush
	public boolean flush(player p){
		card[] hand = p.getHand();
		int count1 = 0;
		LinkedList<card> riv = new LinkedList<card>();
		riv.addAll(river); riv.add(hand[0]);
		for(card c : riv){
			if((c.getSuit()).equals(hand[1].getSuit()))
				++count1;
		}
		if(count1==5)
			return true;
		return false;
	}
	
	//checks for straights
	public boolean straight(player p){
		card[] hand = p.getHand();
		int count = 0, n1 = 0;
		LinkedList<card> riv = new LinkedList<card>();
		riv.addAll(river); riv.add(hand[0]); riv.add(hand[1]);
		Collections.sort(riv);
		for(card c : riv){
			if(count==0)
				n1=c.getVal();
			else if(count<5 && n1==c.getVal()-1){
				n1=c.getVal()+1;
				count++;
			}
			else if(count==5)
				return true;
			else{
				count=0;
			}
		}
		return false;
	}
	
	//checks for pairs
	public int pairs(player p){
		card[] hand = p.getHand();
		int count1 = 1, count2 = 1;
		if(hand[0].getVal()!=hand[1].getVal()){
			for(card c : river){
				if(c.getVal()==hand[0].getVal())
					++count1;
				if(c.getVal()==hand[1].getVal())
					++count2;
			}
		}
		else{
			++count1;
			for(card c : river){
				if(c.getVal()==hand[0].getVal())
					++count1;
			}
		}
		if((count1==2 && count2==3) || (count1==3 && count2==2))
			return 5;//full house
		else if((count1==4 || count2==4))
			return 4;//4 of a kind
		else if((count1==3 || count2==3))
			return 3;//3 of a kind
		else if((count1==2 && count2==2))
			return 2;//2 pairs
		else if((count1==2 || count2==2))
			return 1;//1 pairs
		else
			return 0;
	}
	
	//set ranks of players
	public void winner(){
		player highcard = null;
		for(player p : players){
			card[] hand = p.getHand();
			//	rf:10/sf:9
			if(flush(p) && straight(p)){
				Collections.sort(river);
				if((river.peekLast()).getVal()==14 || hand[0].getVal()==14 || hand[1].getVal()==14)
					p.setRank(10);
				else
					p.setRank(9);
			}
			//	flush:6
			else if(flush(p))
				p.setRank(6);
			//	strght:5
			else if(straight(p))
				p.setRank(5);
			else{
				int pairRank = pairs(p);
				//	fullhouse:7
				if(pairRank==5)
					p.setRank(7);
				//	4ofakind:8
				else if(pairRank==4)
					p.setRank(8);
				//	3ofakind:4
				else if(pairRank==3)
					p.setRank(4);
				//	2pair:3
				else if(pairRank==2)
					p.setRank(3);
				//	pair:2
				else if(pairRank==1)
					p.setRank(2);
				//	highcard:1
				else{
					if(highcard!=null){
						if(highcard.high()<p.high()){
							highcard.setRank(0);
							p.setRank(1);
							highcard=p;
						}
					}
					else{
						highcard=p;
						p.setRank(1);
					}
				}
			}
		}
	}
	
	public void call(player p){
		//if player is highest better
		if(p.equals(highestBet))
			flip();
		else{
			if(highBet>0 && p.getBet()>0)
			setPot(p,highBet-p.getBet());
			//else just a regular match for the bet
			else
				setPot(p,highBet);
		}
	}
	
	public void bet(player p){
		bet = Integer.parseInt(JOptionPane.showInputDialog("Your balance = " + p.getBal() + "\nEnter bet amount: "));
		//error check
		while(bet<=2*highBet || bet==p.getBal()){
			JOptionPane.showMessageDialog(null, "WRONG INPUT! raises must be x2 current bet and not an All-in", "alert", JOptionPane.ERROR_MESSAGE);
			bet = Integer.parseInt(JOptionPane.showInputDialog("Enter bet amount: "));
		}
		//set highest better to current player and fix pot and player's balances
		highBet = bet;
		highestBet = p;
		setPot(p,bet);
	}
	
	public void allin(player p){
		//make sure that player wants to go through this decision
		int n = JOptionPane.showConfirmDialog(null, "Are you sure?",null,JOptionPane.YES_NO_OPTION);
		if(n==0){
			System.out.println("goodluck.");  
			setPot(p,p.getBal());
			if(highBet<p.getBal()){
				highBet = bet;
				highestBet = p;
			}
		}
		else
			decisions(p);
	}
}

