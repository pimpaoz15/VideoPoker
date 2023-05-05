package com.oop.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enum class with all plays
 * 
 * @author group 45
 *
 */
enum Play {
	/**
	 * Other plays
	 */
	OTHER("OTHER"), 
	/**
	 * jacks or better plays
	 */
	JACKSORBETTER("JACKS OR BETTER"),
	/**
	 * two pair play
	 */
	TWOPAIR("TWO PAIR"),
	/**
	 * three of a kind play
	 */
	THREEOFAKIND("THREE OF A KIND"),
	/**
	 * straight play
	 */
	STRAIGHT("STRAIGHT"),
	/**
	 * flush play
	 */
	FLUSH("FLUSH"),
	/**
	 * full house
	 */
	FULLHOUSE("FULL HOUSE"),
	/**
	 * four of a kind play
	 */
	FOUROFAKIND("FOUR OF A KIND"),
	/**
	 * straight flush
	 */
	STRAIGHTFLUSH("STRAIGHT FLUSH"),
	/**
	 * royal flush
	 */
	ROYALFLUSH("ROYAL FLUSH");

	private final String play;
	private static Map<Rank,Integer> rankHits = new HashMap<Rank,Integer>();
	private static Map<Suit,Integer> suitHits = new HashMap<Suit,Integer>();
	
	/**
	 * Constructor for enum type
	 * 
	 * @param play String type. Stores the name of the Enum. 
	 */
	Play(String play) {
		this.play = play;
	}
	
	/**
	 * Gets the play value of the enum
	 * 
	 * @return String type
	 */
	public String getPlay() {
		return play;
	}
	
	/**
	 * Receives an array of cards and processes it.
	 * It stores the total number of ranks and suits in a rank map and a suit map.
	 * 
	 * @param cards
	 */
	private static void hits(Object[] cards) {
		Integer countRank = null;
		Integer countSuit = null;
		Rank rank = null;
		Suit suit = null;
		Card card = null;
		rankHits = new HashMap<Rank,Integer>();
		suitHits = new HashMap<Suit,Integer>();
		
		for(Object obj:cards) {
			card = Card.class.cast(obj);
			rank = card.getRank();
			suit = card.getSuit();
			countRank = rankHits.get(rank);
			countSuit = suitHits.get(suit);
			
			if(countRank == null)
				rankHits.put(rank,1);
			else
				rankHits.put(rank, ++countRank);
			
			if(countSuit == null)
				suitHits.put(suit,1);
			else
				suitHits.put(suit, ++countSuit);
		}
	}
	
	/**
	 * Checks if array of cards matches Jack or better play 
	 * @param Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	private static boolean jackOrBetter(Object[] cards) {
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(Rank.JACK.compareTo(entry.getKey()) <= 0)
				if(entry.getValue() == 2)
					return true;
		return false;
	}
	
	/**
	 * Checks if array of cards matches flush play
	 * @param Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	private static boolean flush(Object[] cards) {
		for(Map.Entry<Suit,Integer> entry: suitHits.entrySet())
			if(entry.getValue() == 5)
				return true;
		return false;
	}
	
	/**
	 * Checks if array of cards matches four aces
	 * @param cards  array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	public static boolean FOURACES(Object[] cards) {		
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(entry.getValue() == 4)
				if(Rank.ACE.equals(entry.getKey()));
		return false;
	}
	
	/**
	 * Checks if array of cards matches four 2 to a 4
	 * @param cards Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	public static boolean FOUR24(Object[] cards) {		
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(entry.getValue() == 4)
				if(Rank.TWO.compareTo(entry.getKey()) <= 0)
					if(Rank.FOUR.compareTo(entry.getKey()) >= 0)
						return true;
		return false;
	}
	
	/**
	 * Checks if array of cards matches four 5 to a K
	 * @param cards Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	public static boolean FOUR5K(Object[] cards) {		
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(entry.getValue() == 4)
				if(Rank.FIVE.compareTo(entry.getKey()) <= 0)
					if(Rank.KING.compareTo(entry.getKey()) >= 0)
						return true;
		return false;
	}
	
	/**
	 * Checks if array of cards matches four of a Kind
	 * @param cards Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	private static boolean fourOfAKind(Object[] cards) {		
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(entry.getValue() == 4)
				return true;
		return false;
	}
	
	/**
	 * Checks if array of cards matches Full House
	 * @param cards Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	private static boolean fullHouse(Object[] cards) {
		boolean pair = false;
		boolean threes = false;
		
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet()) {
			if(entry.getValue() == 2) pair = true;
			if(entry.getValue() == 3) threes = true;
		}
		return pair && threes;
	}
	
	/**
	 * Checks if array of cards matches Royal Flush
	 * @param cards Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	private static boolean royalFlush(Object[] cards) {
		List<Card> sortedList = new ArrayList<>();
		for(Object obj:cards)
			sortedList.add(Card.class.cast(obj));
		Collections.sort(sortedList);
		Card card = null;
		Card nextCard = null;
		int listSize = sortedList.size();
		int i = 0;
		
		for(i = 0; i < listSize - 1; i++) {
			card = sortedList.get(i);
			nextCard = sortedList.get(i+1);
			if(!card.isNeighbour(nextCard))
				return false;
		}

		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(Rank.TEN.compareTo(entry.getKey()) > 0)
				return false;
		
		for(Map.Entry<Suit,Integer> entry: suitHits.entrySet())
			if(entry.getValue() == 5) 
				return true;
		return false;
	}
	
	/**
	 * Checks if array of cards matches~straight
	 * @param cards Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	private static boolean straigth(Object[] cards) {
		List<Card> sortedList = new ArrayList<>();
		for(Object obj:cards)
			sortedList.add(Card.class.cast(obj));
		Collections.sort(sortedList);
		Card card = null;
		Card nextCard = null;
		int listSize = sortedList.size();
		int i = 0;
		
		for(i = 0; i < listSize - 1; i++) {
			card = sortedList.get(i);
			nextCard = sortedList.get(i+1);
			if(!card.isNeighbour(nextCard))
				return false;
		}
		return true;
	}
	
	/**
	 * Checks if array of cards matches striagth flush
	 * @param cards Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	private static boolean straightFlush(Object[] cards) {
		List<Card> sortedList = new ArrayList<>();
		for(Object obj:cards)
			sortedList.add(Card.class.cast(obj));
		Collections.sort(sortedList);
		Card card = null;
		Card nextCard = null;
		int listSize = sortedList.size();
		int i = 0;
		
		for(i = 0; i < listSize - 1; i++) {
			card = sortedList.get(i);
			nextCard = sortedList.get(i+1);
			if(!card.isNeighbour(nextCard))
				return false;
		}
		
		for(Map.Entry<Suit,Integer> entry: suitHits.entrySet())
			if(entry.getValue() == 5)
				return true;
		return false;
	}
	
	/**
	 * Checks if array of cards matches three of a kind
	 * @param cards Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	private static boolean threeOfAKind(Object[] cards) {
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(entry.getValue() == 3)
				return true;
		return false;
	}
	
	/**
	 * Checks if array of cards matches two pair
	 * @param cards Array of 5 cards (hand) to be tested
	 * @return returns true if it detects a valid play. 0 otherwise.
	 */
	private static boolean twoPair(Object[] cards) {
		int totPairs = 0;
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(entry.getValue() == 2)
			totPairs++;
		return totPairs>1;
	}
	
	/**
	 * Implementes the logic on the five cards and picks the and.
	 * If it detects none return OTHER command type.
	 * @param cards an array of 5 Object cards
	 * @return enum of type Play. 
	 */
	public static Play check(Object[] cards) {
		hits(cards);
		
		if(royalFlush(cards))
			return Play.ROYALFLUSH;
		if(straightFlush(cards))
			return Play.STRAIGHTFLUSH;
		if(fourOfAKind(cards))
			return Play.FOUROFAKIND;
		if(fullHouse(cards))
			return Play.FULLHOUSE;
		if(flush(cards))
			return Play.FLUSH;
		if(straigth(cards))
			return Play.STRAIGHT;
		if(threeOfAKind(cards))
			return Play.THREEOFAKIND;
		if(twoPair(cards))
			return Play.TWOPAIR;
		if(jackOrBetter(cards))
			return Play.JACKSORBETTER;
		return Play.OTHER;
	}
	
	@Override
	public String toString() {
		return play;
	}
}
