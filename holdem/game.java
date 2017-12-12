/*
 * game class for texas hold'em
 *
*/ 

import java.util.*;

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
	private Sorter sort = new Sorter();
	
	public game(LinkedList<player> players,boolean blind){
		this.players = players;
		Collections.shuffle(this.players);
		for(String s:suits){
			for(int i = 2;i<=14;++i){
				deck.add(new card(i,s));
				temp.add(new card(i,s));
		}}
		if(blind){
			System.out.print("Small blind value?: ");
			small = in.nextInt();
			big = small*2;
			System.out.println();
		}
		else{
			System.out.print("Ante value?: ");
			small = in.nextInt();
		}
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
				
				//intial preflop bets
				blindBets();
				
				//player up to play
				p=setLast();
				
				//if player hasn't folded
				if(!p.fold()){
					System.out.println(p + "\n");
					decisions(p);
				}
				//need to figure out what to do with folded players
			}
			
			//ante game
			else{
				anteBets();
			}
			
			//only quit when 1 player remains
			if(players.size()<2)
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
			System.out.println("\t\tTable cards:");
			river.add(deck.pop());
			river.add(deck.pop());
			river.add(deck.pop());
			System.out.print(river);
		}
		//4th and 5th cards
		else if(river.size()<5){
			resetOrder();
			deck.pop();
			river.add(deck.pop());
			System.out.println("\t\tTable cards:");
			System.out.print(river);
		}
		//choose winner and reset
		else{
			resetOrder();
			player p = setLast();
			(players.peek()).setBlind();
			river.clear();
			start = true;
			//compare players' hands
			//highest hand wins
			//player.win(pot)
		}
	}
	
	//remove and return the player at the front and add them to the end
	public player setLast(){
		player p = players.pop();
		players.addLast(p);
		return p;
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
		if(start){
			deal();
			p = setLast();	p.setBlind();	setPot(p,small);
			p = setLast();	setPot(p,small*2);
			highBet = small*2;
			highestBet = p;
			start = false;
		}
	}
	
	//ante bets
	public void anteBets(){
		if(start){
			deal();
			for(player pl:players)
				setPot(pl,small);
			start=false;
		}
	}
	
	//player's decisions
	public void decisions(player p){
		
		//check if player has current highest bet
		if(p.equals(highestBet)){
			highBet=0;
			choice = p.choice(highBet);
			//show cards if player does not reraise *not finished, must find way to reraise properly without over coding*
			if(choice==1)
				flip();
			System.out.println();
		}
		
		//when player isn't highest better
		else{
			choice = p.choice(highBet);
			//error check
			while(choice>4 || choice<1){
				System.out.println("\t\tERROR!!!\n\t\tNo cheating the system!\n\t\tEnter a legal value according to the menu!\n\n");
				choice = p.choice(highBet);
			}
			
			switch(choice){
				//	call/check
				case 1:
					//if player has already bet
					if(highBet>0 && p.getBet()>0)
						setPot(p,highBet-p.getBet());
					//else just a regular match for the bet
					else
						setPot(p,highBet);
					break;
				//	raise/bet
				case 2:
					System.out.print("\nBet amount? (it must be x2 last bet for raises and not All In) :" );
					bet = in.nextInt();
					//error check
					while(bet<=2*highBet || bet==p.getBal()){
						System.out.println("\t\tERROR!!!\n\t\tNo cheating the system!\n\t\tEnter a legal value!\n\n");
						System.out.print("\nBet amount? (must be x2 last bet for raises and not an All In) :" );
						bet = in.nextInt();
					}
					//set highest better to current player and fix pot and player's balances
					highBet = bet;
					highestBet = p;
					setPot(p,bet);
					break;
				//	all in
				case 3:
					//make sure that player wants to go through this decision
					System.out.println("are you sure?");
					switch((in.nextLine()).charAt(0)){
						case 'y': case 'Y':
							System.out.println("goodluck.");  
							setPot(p,p.getBal());
							if(highBet<p.getBal()){
								highBet = bet;
								highestBet = p;
							}
							break;
						//back choice
						case 'n': case 'N':
							System.out.println("pussssssssssssssssssssssssy");
							decisions(p);
							break;
						//for those who don't follow instructions
						default:
							System.out.println("Dude wtf, just say yes or no, for that I fold you");
							p.setFold();
							break;
					}
				//	fold
				case 4:
					p.setFold();
			}
		}
	}
	
	//checks for flush
	public boolean flush(player p){
		card[] hand = p.getHand();
		int count1 = 0, count2 = 0;
		for(card c : river){
			if((c.getSuit()).equals(hand[0].getSuit()))
				++count1;
			if((c.getSuit()).equals(hand[1].getSuit()))
				++count2;
		}
		if(count1==5 || count2==5)
			return true;
		return false;
	}
	
	//checks for straights
	public boolean straight(player p){
		card[] hand = p.getHand();
		int count = 0;
		river.add(hand[0]);
		river.add(hand[1]);
		sort.sort(river);
		ListIterator<card> c = river.listIterator();
		while(c.hasNext()){
			if(((c.next()).getVal())==(c.next()).getVal()+1){
				++count;
				c.previous();
			}
			else if(count==5)
				return true;
			else{
				c.previous();
				count=0;
			}
		}
		return false;
	}
	
	//checks for pairs
	public int pairs(player p){
		card[] hand = p.getHand();
		int count1 = 0, count2 = 0;
		for(card c : river){
			if(c.getVal()==hand[0].getVal())
				++count1;
			if(c.getVal()==hand[1].getVal())
				++count2;
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
	
}

