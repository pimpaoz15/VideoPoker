package com.oop.project;

import java.util.HashMap;

enum Suit {
	CLUBS("C"), DIAMONDS("D"), HEARTS("H"), SPADES("S");
	
	private final String suit;
	private static final HashMap<String,Suit> map;

	/**
	 * Constructor to initialize enum class
	 * 
	 * @param rank One character String value that relates to the rank.
	 */
	Suit(String suit) {
		this.suit = suit;
	}

	/**
	 * Generated getter method to get suit value.
	 * 
	 * @return String return a one character String value for suit.
	 */
	public String getSuit() {
		return suit;
	}
		
	static {
		map = new HashMap<String,Suit>();
	
		for(Suit s:Suit.values())
			try {
				map.put(s.getSuit(),s);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Static method that returns the Rank that relates to the one
	 * rank character value.
	 * 
	 * @param constant
	 * @return Rank
	 */
	public static Suit getConstant(String constant) {
		return map.get(constant);
	}
}