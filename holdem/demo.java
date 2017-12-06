/*
 * Texas Hold'em poker
 * by: Stephen Aigbomian && Nicholai Tukanov
 * started: 12/4/17
 * finished: n/a
 * 
 */

import java.util.*;

public class demo{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		LinkedList<player> players = new LinkedList<player>();
		int ppop, buyin;
		boolean blind;
		
		System.out.print("How many players are at the table today?:	");
		ppop = in.nextInt();
		
		while(ppop<4 || ppop>8){
			System.out.println("\n\t\tERROR!!!\nThe table only allows a min of 4 and a max of 8");
			System.out.print("Please enter a legal value:\t");
			ppop = in.nextInt();
		}
		
		System.out.print("What's the buy-in for today's game?:\t");
		buyin = in.nextInt();
		in.nextLine();
		System.out.print("Will this game use a blind system?:\t");
		switch((in.nextLine()).charAt(0)){
			case 'y': case 'Y':
				blind = true;
			default:
				blind = false;
		}
		
		System.out.print("\nWhat are the players names?:\t");
		for(int i = 1;i<=ppop;++i){
			System.out.print("\nPlayer " + i + ":\t");
			players.add(new player(in.nextLine(),buyin));
		}
		
		game holdem = new game(players,blind);
		
	}
}
