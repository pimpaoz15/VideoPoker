package com.oop.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Machine {
	
	// store the total number of bets
	private int bet = 5;
	
	// Stores the name of the file that contains the cards for debug mode
	private String cardFile;

	// Stores commands
	private List<String> feed = null;
	
	// Stores the name the file that contains the commands for debug mode
	private String commandFile;
	
	// Store the total amount of credits available to the player.
	private int credit = 10000;
	
	private final int balance = credit;
	
	// Stores list of cards
	private Deque<Card> deck = new LinkedList<>();
	
	// Store the current hand being played
	private PokerHand hand;
	
	private final Statistics statistics = new Statistics(balance);
	
	// Stores game mode type. (d)debug mode or (s)simulation mode.
	private char mode;
	
	// Total number of deals
	private int nbdeals = 0;
	
	// CONSTRUCTOR
	/**
	 * Constructor for class Machine
	 * 
	 * @param args that are given as input from main
	 */
	Machine(String[] args) {		
		//try {
			this.mode = args[0].charAt(1);
		
			String cmdEntry1 = args[1];
			String cmdEntry2 = args[2];
			String cmdEntry3 = args[3];
			
			switch(this.mode) {
				case 'd': Debug(cmdEntry1,cmdEntry2,cmdEntry3); break;
				case 's': Simulation(cmdEntry1,cmdEntry2,cmdEntry3); break;					
					default: 
						System.out.println(""); break;
			}
		/*
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Invalide mode. Pick 'd' or 's'.");
		}
		*/
	}
	
	@Override
	public String toString() {
		String mode = String.valueOf(this.mode);
		
		String header = "";
		String end = "";
		
		String bet = "\tBet = " + String.valueOf(this.bet) + "\n";
		String cardFile = "\tCard file = " + this.cardFile + "\n";
		String feed = "\tCommands = " + this.feed.toString() + "\n";
		String commandFile = "\tCommand file " + this.commandFile + "\n";
		String credit = "\tCredit = " + String.valueOf(this.credit) + "\n";
		String deck = "\tDeck = " + this.deck.toString() + "\n";	
		String nbdeals = "\tTotal deals = " + String.valueOf(this.nbdeals) + "\n";
		
		switch(mode) {
		case "d": 
			header = "(Debug mode)\n";
			end = "(Debug mode end)\n\n\n";
			break;
		case "s": 
			header = "(Simulation mode)\n";
			end = "(Simulation mode end)\n\n\n";
			break;
		}
		return header + 
				bet + credit + nbdeals + "\n" +
				commandFile + feed + "\n" +
				cardFile + deck + 
				end;
	}

	/**
	 * Starts machine in debug mode
	 * 
	 * @param cmdEntry1 value of credits passed as argument
	 * @param cmdEntry2 name of command file passed as argument
	 * @param cmdEntry3 name of card file passed as argument
	 */
	private void Debug(String cmdEntry1, String cmdEntry2, String cmdEntry3) {
		this.cardFile = cmdEntry3;
		String[] cmds = parse(cmdEntry2);
		feed = new ArrayList<>(cmds.length);
		
		// esta a mais.
		for(String cmd:cmds)
			feed.add(cmd);
		
		this.commandFile = cmdEntry2;
		this.deck = Card.newDeck(parse(cmdEntry3));
		play();
	}
	
	/**
	 * Starts machine in simulation mode
	 * 
	 * @param cmdEntry1 credits passed by argument
	 * @param cmdEntry2 value of bet passed by argument
	 * @param cmdEntry3 total number of bets
	 */
	private void Simulation(String cmdEntry1, String cmdEntry2, String cmdEntry3) {
		this.credit = Integer.valueOf(cmdEntry1);
		this.bet = Integer.valueOf(cmdEntry2);
		this.cardFile = "(no file needed for simulation mode)";
		this.commandFile = "(no file neede for simulation mode)";
		this.deck = Card.newDeck();
		this.nbdeals = Integer.valueOf(cmdEntry3);
		
		// bad implemented
		if(this.bet > 5 && this.bet < 0)
			this.credit = 5;
		
		while(nbdeals != 0 && (credit - bet) >= 0) {
			deck = Card.newDeck();
			deck = shuffleDeck(deck);
			hand = new PokerHand(drawCard(5).toArray());
			
			Advice adv = new Advice(hand);
			List<Card> aCards = adv.whatsAdvised();
			
			int i = 0;
			for(Card card:hand.getHand()) {
				if(!aCards.contains(card))
					// hold should be named replace. hold REPLACES a card!!!!!
					hand = hand.hold(i+1, drawCard(1).toArray());
				i++;
			}
			
			Play play = Play.check(hand.getHand().toArray());
			statistics.sum(play);
			
			if(play.equals(Play.OTHER))
				credit = credit - bet;
			else
				credit = credit - bet*Bonus.bonus(hand);
			nbdeals--;
		}
		
		System.out.println("(Simulation mode)\n");
		statistics.setBalance(this.balance);
		statistics.setCredit(this.credit);
		System.out.println(statistics);
		System.out.println("(End of simulation mode)");
	}
	
	/**
	 * Parses the files given their name. Only used for debug mode.
	 * 
	 * @param fileName fileName is the name of the file we want to parse.
	 * @return returns the String of commands or cards (depends on what file is being parsed)
	 */
	private static String[] parse(String fileName) {
		File fp = new File(fileName);
		Scanner sc = null;
		String line = "";
		
		try {
			sc = new Scanner(fp);
			while(sc.hasNextLine()) {
				line = sc.nextLine();
			}
			sc.close();
			return line.split(" ");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Machine can't open file: " + fileName);
			return new String[] {};
		}
	}
	
	/**
	 * Runs machine in debug mode
	 * @return void
	 */
	private void play() {
		int i = 0;
		int index = 0;
		boolean bet = false;
		boolean deal = false;
		boolean hold = false;
		
		Integer num = 0;
		Command cmdAux = null;
		
		Play play = null;
		
		boolean[] hld = new boolean[] {false,false,false,false,false};
		
		for(String command:feed) {
			index++;
			cmdAux = Command.getConstant(command);
			
			if(cmdAux != null) {
				if(cmdAux.equals(Command.BET)) {
					if(bet) {
						//this.credit = this.credit - this.bet;
						System.out.println(" " + this.bet);
						System.out.println("Player is betting " + this.bet);
						System.out.println("");
						bet = false;
					}
					
					if(deal && hold) {
						for(i = 0; i < 5; i++) {
							if(hld[i])
								System.out.print(" " + (i+1));
							if(!hld[i])
								hand = hand.hold(i+1, drawCard(1).toArray());
						}
						System.out.println();
						play = Play.check(hand.getHand().toArray());
						statistics.sum(play);
						
						System.out.println("Player's hand " + hand.toString());
						if(play.equals(Play.OTHER)) {
							this.credit = this.credit - this.bet;
							System.out.println("player loses and his credit is " + this.credit);
							System.out.println();
						}
						else {
							//TODO:
							this.credit = this.credit - this.bet;
							this.credit = this.credit + this.bet * Bonus.bonus(hand);
							System.out.println("player wins with a " + 
									play + " and his credit is " + credit);
							System.out.println();
						}
						
						hld = new boolean[] {false,false,false,false,false};
						deal = false;
						hold = false;
					}
					
					System.out.print("-cmd "+Command.BET.getCommand());
					bet = true;
				}
				if(cmdAux.equals(Command.CREDIT)) {
					if(bet) {
						//this.credit = this.credit - this.bet;
						System.out.println(" " + this.bet);
						System.out.println("Player is betting " + this.bet);
						System.out.println("");
						bet = false;
					}
					
					if(deal && hold) {
						for(i = 0; i < 5; i++) {
							if(hld[i])
								System.out.print(" " + (i+1));
							if(!hld[i])
								hand = hand.hold(i+1, drawCard(1).toArray());
						}
						play = Play.check(hand.getHand().toArray());
						statistics.sum(play);
						System.out.println();
						System.out.println("Player's hand " + hand.toString());
						
						if(play.equals(Play.OTHER)) {
							this.credit = this.credit - this.bet;
							System.out.println("player loses and his credit is " + this.credit);
							System.out.println();
						}
						else {
							//TODO:
							this.credit = this.credit - this.bet;
							this.credit = this.credit + this.bet * Bonus.bonus(hand);
							System.out.println("player wins with a " + 
									play + " and his credit is " + credit);
							System.out.println();
						}
						
						hld = new boolean[] {false,false,false,false,false};
						deal = false;
						hold = false;
					}
					
					System.out.println("-cmd "+Command.CREDIT.getCommand());
					System.out.println("Player's credit is " + (this.credit - this.bet));
					System.out.println();
				}
				if(cmdAux.equals(Command.DEAL)) {
					if(bet) {
						//this.credit = this.credit - this.bet;
						System.out.println(" " + this.bet);
						System.out.println("Player is betting " + this.bet);
						System.out.println("");
						bet = false;
					}
					
					if(deal && hold) {
						for(i = 0; i < 5; i++) {
							if(hld[i])
								System.out.print(" " + (i+1));
							if(!hld[i])
								hand = hand.hold(i+1, drawCard(1).toArray());
						}
						System.out.println();
						play = Play.check(hand.getHand().toArray());
						statistics.sum(play);
						
						System.out.println("Player's hand " + hand.toString());
						if(play.equals(Play.OTHER)) {
							this.credit = this.credit - this.bet;
							System.out.println("player loses and his credit is " + this.credit);
							System.out.println();
						}
						else {
							//TODO:
							this.credit = this.credit - this.bet;
							this.credit = this.credit + this.bet * Bonus.bonus(hand);
							System.out.println("player wins with a " + 
									play + " and his credit is " + credit);
							System.out.println();
						}

						hld = new boolean[] {false,false,false,false,false};
						deal = false;
						hold = false;
					}
					
					if(!deal) {
						// starts hand
						hand = new PokerHand(drawCard(5).toArray());
						
						System.out.println("-cmd " + Command.DEAL.getCommand());
						System.out.println("Player's hand is " + hand.toString());
						System.out.println();
						deal = true;
					} else {
						System.out.println(Command.DEAL.getCommand() + ": already dealt");
					}
				}
				
				if(cmdAux.equals(Command.HOLD)) {
					if(bet) {
						System.out.println(" " + this.bet);
						System.out.println("Player is betting " + this.bet);
						System.out.println("");
						bet = false;
					}
					
					if(deal && hold) {
						for(i = 0; i < 5; i++) {
							if(hld[i])
								System.out.print(" " + (i+1));
							if(!hld[i])
								hand = hand.hold(i+1, drawCard(1).toArray());
						}
						System.out.println();
						play = Play.check(hand.getHand().toArray());
						statistics.sum(play);
						
						System.out.println("Player's hand " + hand.toString());
						if(play.equals(Play.OTHER)) {
							this.credit = this.credit - this.bet;
							System.out.println("player loses and his credit is " + this.credit);
							System.out.println();
						}
						else {
							//TODO:
							this.credit = this.credit - this.bet;
							this.credit = this.credit + this.bet * Bonus.bonus(hand);
							System.out.println("player wins with a " + 
									play + " and his credit is " + credit);
							System.out.println();
						}

						hld = new boolean[] {false,false,false,false,false};
						deal = false;
						hold = false;
					}
					
					if(deal) {
						if(!hold) {
							hold = true;
							System.out.print("-cmd " + Command.HOLD.getCommand());
						}
						else {
							System.out.println("-cmd " + Command.HOLD.getCommand());
							System.out.println(Command.HOLD.getCommand()+": illegal hold");
							System.out.println();
						}
					} else {
						System.out.println("-cmd " + Command.HOLD.getCommand());
						System.out.println(Command.HOLD.getCommand() + ": illegal hold");
						System.out.println();
					}
					
				}
				
				if(cmdAux.equals(Command.ADVICE)) {
					if(bet) {
						//this.credit = this.credit - this.bet;
						System.out.println(" " + this.bet);
						System.out.println("Player is betting " + this.bet);
						System.out.println("");
						bet = false;
					}
					
					if(deal && hold) {
						for(i = 0; i < 5; i++) {
							if(hld[i])
								System.out.print(" " + (i+1));
							if(!hld[i])
								hand = hand.hold(i+1, drawCard(1).toArray());
						}
						System.out.println();
						play = Play.check(hand.getHand().toArray());
						statistics.sum(play);
						
						System.out.println("Player's hand " + hand.toString());
						if(play.equals(Play.OTHER)) {
							this.credit = this.credit - this.bet;
							System.out.println("player loses and his credit is " + this.credit);
							System.out.println();
						}
						else {
							//TODO:
							this.credit = this.credit - this.bet;
							this.credit = this.credit + this.bet * Bonus.bonus(hand);
							System.out.println("player wins with a " + 
									play + " and his credit is " + credit);
							System.out.println();
						}
						
						hld = new boolean[] {false,false,false,false,false};
						deal = false;
						hold = false;
					}
					
					if(deal && !hold) {
						i = 0;
						Advice adv = new Advice(hand);
						List<Card> aCards = adv.whatsAdvised();
						System.out.println("-cmd "+Command.ADVICE.getCommand());
						System.out.print("player should hold cards");
						for(Card card:hand.getHand()) {
							if(aCards.contains(card)) {
								System.out.print(" " + (i+1));
								i++;
							}
						}
						System.out.println();
						System.out.println();
					} else {
						System.out.println("-cmd "+Command.ADVICE.getCommand());
						System.out.println("No advices to give.");
					}
				}
				
				if(cmdAux.equals(Command.STATISTICS)) {
					if(bet) {
						this.credit = this.credit - this.bet;
						System.out.println(" " + this.bet);
						System.out.println("Player is betting " + this.bet);
						System.out.println("");
						bet = false;
					}
					
					if(deal && hold) {
						for(i = 0; i < 5; i++) {
							if(hld[i])
								System.out.print(" " + (i+1));
							if(!hld[i])
								hand = hand.hold(i+1, drawCard(1).toArray());
						}
						System.out.println();
						play = Play.check(hand.getHand().toArray());
						statistics.sum(play);
						
						System.out.println("Player's hand " + hand.toString());
						if(play.equals(Play.OTHER)) {
							this.credit = this.credit - this.bet;
							System.out.println("player loses and his credit is " + this.credit);
							System.out.println();
						}
						else {
							//TODO:
							this.credit = this.credit - this.bet;
							this.credit = this.credit + this.bet * Bonus.bonus(hand);
							System.out.println("player wins with a " + 
									play + " and his credit is " + credit);
							System.out.println();
						}
						
						hld = new boolean[] {false,false,false,false,false};
						deal = false;
						hold = false;
					}
					
					System.out.println("-cmd " + Command.STATISTICS.getCommand());
					System.out.println("Printing statistics...");
					statistics.setBalance(this.balance);
					statistics.setCredit(this.credit);
					System.out.println(statistics);
				}
			}
			else {
				try {
					num = Integer.valueOf(command);
					
					if(bet) {
						if(num > 0 && num < 6) {
							this.bet = num;
							System.out.println(" " + this.bet);
							System.out.println("Player is betting " + num);
							System.out.println("");
						}
						else {
							System.out.println(" " + num);
							System.out.println(Command.BET.getCommand() + ": illegal amount");
							System.out.println("");
						}
						bet = false;
					} 
					if(deal && hold) {
						if(num > 0 && num < 6) {
							hld[num - 1] = true;
						}
						else {
							System.out.println(num + " :Out of range");
							System.out.println("");
						}
						
						if(feed.size() == index) {
							for(i = 0; i < 5; i++) {
								if(hld[i])
									System.out.print(" " + (i+1));
								if(!hld[i])
									hand = hand.hold(i+1, drawCard(1).toArray());
							}
							System.out.println();
							play = Play.check(hand.getHand().toArray());
							statistics.sum(play);
							
							System.out.println("Player's hand " + hand.toString());
							if(play.equals(Play.OTHER)) {
								this.credit = this.credit - this.bet;
								System.out.println("player loses and his credit is " + this.credit);
								System.out.println();
							}
							else {
								//TODO:
								Integer bonus = Bonus.bonus(hand);
								this.credit = this.credit - this.bet;
								this.credit = this.credit + this.bet * bonus;
								System.out.println("player wins with a " + 
										play + " and his credit is " + credit);
								System.out.println();
							}
							
							hld = new boolean[] {false,false,false,false,false};
							deal = false;
							hold = false;
						}
					}
					
				} catch(NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}
		}
	}
	
	/**
	 * Draws n cards from the deck and removes them.
	 * 
	 * @param numberOfDraws number of cards to draw from the deck.
	 * @return Queue of cards we want to daw from the deck
	 */
	public Deque<Card> drawCard(int numberOfDraws) {
		Deque<Card> deck = new LinkedList<Card>();
		for(int i = 0; i < numberOfDraws; i++) {
			try {
				deck.add(this.deck.removeFirst());
			} catch(NoSuchElementException  e) {
				System.out.println("Deck is empty");
			}
		}
		return new LinkedList<Card>(deck);
	}
	
	/**
	 * Shuffles a deck
	 * 
	 * @param deck we want to shuffle
	 * @return returns the deck passed in parameter, shuffled.
	 */
	public Deque<Card> shuffleDeck(Deque<Card> deck) {
		// linked list
		List<Card> sorted = new ArrayList<Card>(deck);
		Collections.shuffle(sorted);
		return new LinkedList<>(sorted); 
	}
}
