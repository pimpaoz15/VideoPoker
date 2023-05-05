package com.oop.project;

import java.util.Arrays;
import java.util.List;

/**
 * Contains a pokerhand of 5 cards
 * 
 * @author grupo 45
 *
 */
public class PokerHand {
	
	private final static int handSize = 5;
	private final List<Card> hand = Arrays.asList(new Card[handSize]);

	/**
	 * Constructor for Pokerhand class
	 * @param 5 card array of type object. need to be casted.
	 */
	PokerHand(Object[] cards) {
		int i = 0;
		for(i = 0; i < 5; i++)
			hand.set(i,Card.class.cast(cards[i]));
	}

	/**
	 * Get method to get hand (list of cards
	 * @return returns hand
	 */
	public List<Card> getHand() {
		return hand;
	}
	
	/**
	 * Replaces the cards from the hand in position pos with card from Object[] cards
	 * @param pos of card to replace
	 * @param cards list of cards to replace with
	 * @return returns new pokerhand
	 */
	// Holds one card
	public PokerHand hold(int pos, Object[] cards) {
		Card card = null;
		
		for(Object obj:cards)
			card = Card.class.cast(obj);
		
		try {
			hand.set(pos-1, card);
		} catch(IndexOutOfBoundsException e) {
			System.out.println("PokerHand:set:Invalid position");
		}
		return new PokerHand(hand.toArray());
	}

	@Override
	public String toString() {
		String result = "";
		for(Card card:hand)
			result = result + card + " "; 
		return result;
	}
}