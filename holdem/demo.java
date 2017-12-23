/*
 * Texas Hold'em poker
 * by: Stephen Aigbomian && Nicholai Tukanov
 * started: 12/4/17
 * finished: n/a
 * 
 */

import java.util.*;
import javax.swing.*;

public class demo{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		LinkedList<player> players = new LinkedList<player>();
		int n=0, buyin;
		boolean blind = false;
		
		n = Integer.parseInt(JOptionPane.showInputDialog("How many players?"));
		while(n<4 || n>8){
			if(n<4 || n>8)
				JOptionPane.showMessageDialog(null, "WRONG INPUT! must be between 4-8", "alert", JOptionPane.ERROR_MESSAGE);
			n = Integer.parseInt(JOptionPane.showInputDialog("How many players?"));
		}
		
		buyin = Integer.parseInt(JOptionPane.showInputDialog("What's the buy-in for today's game?"));
		
		int yn = JOptionPane.showConfirmDialog(null, "Will this game use a blind system?",null,JOptionPane.YES_NO_OPTION);
		if(yn==0)
			blind = true;
		
		for(int i = 1;i<=n;++i){
			players.add(new player(JOptionPane.showInputDialog("Player " + i + "'s name?"),buyin));
		}
		
		game holdem = new game(players,blind);
		holdem.table();
	}
}
