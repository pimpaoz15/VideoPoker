package com.oop.project;

import java.util.HashMap;

/**
 * Enum Rank contains all ranks available to a card.
 */
enum Rank {
	TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"),
	NINE("9"), TEN("T"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A");
	
	private final String rank;
	private static final HashMap<String,Rank> map;

	/**
	 * Constructor to initialize enum class
	 * 
	 * @param rank One character String value that relates to the rank.
	 */
	Rank(String rank) {
		this.rank = rank;
	}

	/**
	 * Generated getter method to get rank value.
	 * 
	 * @return String return a one character String value for rank.
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * Initializes a hashmap That helps finding the corresponding rank
	 * through String rank paramenter.
	 */
	static {
		map = new HashMap<String,Rank>();
	
		for(Rank r:Rank.values())
			try {
				map.put(r.getRank(),r);
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
	public static Rank getConstant(String constant) {
		return map.get(constant);
	}
}