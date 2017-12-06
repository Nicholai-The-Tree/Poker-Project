/*
 * game class for texas hold'em
 * fields: suits, players, deck, pot, big, small, in(put)
 * methods: shuf(), deal(), table(), round(), river()
*/ 

import java.util.*;

public class game{
	
	private String[] suits = { "H", "S", "D", "C" }; 
	private LinkedList<player> players;
	private LinkedList<card> deck = new LinkedList<card>();
	private LinkedList<card> river = new LinkedList<card>();
	private int pot, big, small;
	private Scanner in = new Scanner(System.in);
	
	public game(LinkedList<player> players,boolean blind){
		this.players = players;
		for(String s:suits){
			for(int i = 2;i<=14;++i){
				deck.add(new card(i,s));
		}}
		if(blind){
			System.out.print("Small blind value?: ");
			small = in.nextInt();
			big = small*2;
			System.out.println();
		}
	}
	
	public void shuf(){
		Collections.shuffle(deck);
	}
	
	//deals 1 card to each player in 2 cycles
	public void deal(){
		player p;
		for(int i = 0;i<players.size()*2;++i){
			p = players.remove();
			p.insert(deck.pop());
			players.addLast(p);
		}
	}
	
	//table where all players sit
	//dealer is always the last person in ll players
	//for blind game, small is 1st and big is 2nd in the list
	public void table(){
		boolean gameover = false;
		Collections.shuffle(players);
		while(!gameover){
			deal();
			
			//blind game
			if(small>0){
				
				
			}
			//ante game
			else{
				
				
			}
			
			
			if(players.size()<2 )
				gameover = true;
		}
	}
	
	//a round of bets
	public void round(){
		
	}
	
	public void setPot(int bet){
		pot+=bet;
	}
	
	//a round of a hand 
	public void flop(){
		if(river.size()==0){
			deck.pop();
			river.add(deck.pop());
			river.add(deck.pop());
			river.add(deck.pop());
			for(card c : river)
				System.out.print(c + "\t");
		}
		else{
			deck.pop();
			river.add(deck.pop());
			for(card c : river)
				System.out.print(c + "\t");
		}
	}
}

