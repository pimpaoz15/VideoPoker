package com.oop.project;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

//Source: https://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.9

class Card implements Comparable<Card> {
	
	private final Rank rank;
	private final Suit suit;
	private static final Deque<Card> deck = new ArrayDeque<Card>();

	/**
	 * Constructor for class card
	 * 
	 * @param rank type Rank that stores card rank
	 * @param suit type Suit that store card suit
	 */
	Card (Rank rank, Suit suit) {
        if (rank == null || suit == null)
            throw new NullPointerException(rank + ", " + suit);
        this.rank = rank;
        this.suit = suit;
	}
	
	/**
	 * Getter that returns the rank of the class
	 * 
	 * @return return rank of card
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Setter that returns the suit of the class
	 * 
	 * @return return suit of card
	 */
	public Suit getSuit() {
		return suit;
	}
	
	@Override
    public int compareTo(Card c) {
		return rank.compareTo(c.rank);
    }

	@Override
	public String toString() {
		return rank.getRank() + suit.getSuit();
	}

	static {
		for(Suit suit: Suit.values())
			for(Rank rank: Rank.values())
				deck.addLast(new Card(rank,suit));
	}
	
	/**
	 * Compares if two cards are neighbours by rank
	 * 
	 * @param card receives a card type class to compare with current card
	 * @return return 1 if neighour and 0 if not
	 */
	public boolean isNeighbour(Card card) {
		if(card.rank.ordinal() + 1 == rank.ordinal())
			return true;
		if(card.rank.ordinal() == rank.ordinal() + 1)
			return true;
		return false;
	}
	
	/**
	 * Checks if two cards make a pair (only by rank)
	 * 
	 * @param card receives card
	 * @return returns 1 if they make a pair 0 otherwise
	 */
	public boolean isPair(Card card) {
		if(card.compareTo(card) == 0)
			return true;
		return false;
	}
	
	/**
	 * Check if two cards have the sam suit.
	 * 
	 * @param card receives a type Class as an argument
	 * @return 1 if they are of the same suit 0 otherwise.
	 */
	public boolean isSuit(Card card) {
		if(suit.compareTo(card.suit) == 0)
			return true;
		return false;
	}
	
	/**
	 * Returns a new deck not shuffled
	 * 
	 * @return returns the deck of the class
	 */
	public static Deque<Card> newDeck() {
		return new ArrayDeque<Card>(deck);
	}

	/**
	 * Receives a string of cards and returns resepective deck
	 *  
	 * @param Receives a String of cards and returns respective 
	 * @return returns the deck of cards
	 */
	public static Deque<Card> newDeck(String[] cards) {
		Deque<Card> deck = new ArrayDeque<Card>();
		
		for(String card:cards) {
			try {
				String srank = String.valueOf(card.charAt(0));
				String ssuit = String.valueOf(card.charAt(1));
				Rank rank = Rank.getConstant(srank);
				Suit suit = Suit.getConstant(ssuit);
				deck.addLast(new Card(rank,suit));
			} catch(NullPointerException | IndexOutOfBoundsException e) {
				System.out.println("Invalid card: " + card + " (didn't add it).");
			}
		}
		
		return new ArrayDeque<Card>(deck);
	}

	@Override
	public int hashCode() {
		return Objects.hash(rank, suit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return rank == other.rank && suit == other.suit;
	}
}