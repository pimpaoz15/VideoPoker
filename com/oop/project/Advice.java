package com.oop.project;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Advice class 
 * 
 * @author group 45
 *
 */
public class Advice {
	private final static int handSize = 5;
	private final List<Card> hand = new LinkedList<>();
	private final Map<Rank,Integer> rankHits = new HashMap<>();
	private final Map<Suit,Integer> suitHits = new HashMap<>();
	
	/**
	 * Constructor for class Advice
	 * 
	 * @param pokerhand
	 */
	Advice(PokerHand pokerhand) {
		List<Card> hand = new LinkedList<>();
		hand = pokerhand.getHand();
		Collections.sort(hand);
		Object[] sorted = hand.toArray();

		// initializes hand array
		for(Object obj:sorted) {
			Card card = Card.class.cast(obj);
			Rank rank = card.getRank();
			Suit suit = card.getSuit();
			
			this.hand.add(card);
			
			if(!rankHits.containsKey(rank))
				rankHits.put(rank,1);
			else
				rankHits.put(rank,rankHits.get(rank)+1);
			
			if(!suitHits.containsKey(suit))
				suitHits.put(suit,1);
			else
				suitHits.put(suit,suitHits.get(suit)+1);
		}
	}
	
	/**
	 * Checks if card from hand are straight flush
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 1.1
	private List<Card> straightFlush() {
		List<Card> list = new LinkedList<>();
		int i = 0;
		Card curr = null;
		Card next = null;
		
		if(suitHits.size() == 1) {
			for(i = 0; i < handSize - 1; i++) {
				curr = hand.get(i);
				next = hand.get(i+1);
				if(!curr.isNeighbour(next))
					return new LinkedList<>();
				else
					list.add(curr);
			}
		}
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are four of a kind
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 1.2
	private List<Card> fourOfAKind() {
		List<Card> list = new LinkedList<>();
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(entry.getValue() == 4) {
				for(Card card:hand) {
					if(card.getRank().equals(entry.getKey()))
						list.add(card);
				}
				return new LinkedList<>(list);
			}
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are royal flush
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 1.3
	private List<Card> royalFlush() {
		List<Card> list = new LinkedList<>();
		int i = 0;
		Card curr = null;
		Card next = null;
		if(suitHits.size() == 1) {
			for(i = 0; i < handSize - 1; i++) {
				curr = hand.get(i);
				next = hand.get(i+1);
				if(!curr.isNeighbour(next))
					return new LinkedList<>();
				else 
					list.add(curr);
			}
		}
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are 4 to a royal flush
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 2.
	private List<Card> fourToARoyalFlush() {
		List<Card> list = new LinkedList<>();
		int royal = 0;
		// C,D,H,S
		int[] suits = {0,0,0,0};
		
		Rank rank = null;
		Suit suit = null;

		for(Card card:hand) {
			rank = card.getRank();
			suit = card.getSuit();
			
			if(Rank.TEN.compareTo(rank) >= 0) {
				royal++;
				if(Suit.CLUBS.equals(suit))
					++suits[0];
				if(Suit.DIAMONDS.equals(suit))
					++suits[1];
				if(Suit.HEARTS.equals(suit))
					++suits[2];
				if(Suit.SPADES.equals(suit))
					++suits[3];
			}
		}
		
		for(int i = 0; i < 4; i++) {
			if(suits[i] == 4 && royal == 4) {
				for(Card card:hand) {
					if(card.getRank().ordinal() == i)
						list.add(card);
				}
				return new LinkedList<>(list);
			}
		}
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are 3 aces
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	//3.
	private List<Card> threeAces() {
		List<Card> list = new LinkedList<>();
		for(Card card: hand) {
			if(card.getRank().equals(Rank.ACE))
				list.add(card);
		}
		if(list.size() == 3)
			return new LinkedList<>(list);
		else
			return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are straight
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 4.1
	private List<Card> straight() {
		List<Card> list = new LinkedList<>();
		Card curr = null;
		Card next = null;
		for(int i = 0; i < handSize - 1; i++) {
			curr = hand.get(i);
			next = hand.get(i+1);
			if(!curr.isNeighbour(next))
				return new LinkedList<>();
		}
		
		for(Card card:hand)
			list.add(card);
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are flush
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 4.2
	private List<Card> flush() {
		List<Card> list = new LinkedList<>();
		if(suitHits.size() == 1) {
			for(Card card:hand)
				list.add(card);
			return new LinkedList<>(list);
		}
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are full house
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 4.3
	private List<Card> fullHouse() {
		int tot = 0;
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet()) {
			if(entry.getValue() == 2)
				tot++;
			if(entry.getValue() == 3)
				tot++;
		}
		
		if(tot == 2)
			return new LinkedList<>(hand);
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are three of a kind
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	//5. NO ACES
	private List<Card> threeOfAKind() {
		Rank rank = null;
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet()) {
			rank = entry.getKey();
			if(entry.getValue() == 3 && !Rank.ACE.equals(rank));
				//return true;
		}
		//return false;
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are 4 to a straight flush
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 6. 
	private List<Card> fourToAStraightFlush() {
		List<Card> list = new LinkedList<>();
		int i = 0, j = 0, tot = 0;
		int band = 5;
		// A,K,Q,J,T,9,8,7,6,5,4,3,2
		boolean[] ranks = new boolean[13];
		Suit suits = null;
		
		// incializa o ranks
		for(i = 0; i < 13; i++)
			ranks[i] = false;
		
		
		for(Map.Entry<Suit,Integer> entry: suitHits.entrySet()) {
			suits = entry.getKey();
		
			for(Card card:hand)
				if(card.getSuit().equals(suits))
					ranks[card.getRank().ordinal()] = true;
		
			for(i = 0; i < ranks.length; i++) {
				for(j = i, tot = 0; j < i + band; j++) {
					if(j >= ranks.length)
						if(ranks[j - ranks.length])
							tot++;
					if(j < ranks.length)
						if(ranks[j])
							tot++;
				}
				if(tot == 4);
					//return true;
			}
			
			for(i = 0; i < 13; i++)
				ranks[i] = false;
		}
		//return false;
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are two pair
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 7.
	private List<Card> twoPair() {
		List<Card> list = new LinkedList<>();
		int tot = 0;
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(entry.getValue() == 2)
				tot++;
		//return tot == 2;
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are high pair
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 8.
	private List<Card> highPair() {
		List<Card> list = new LinkedList<>();
		Rank rank = null;
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(Rank.JACK.compareTo(entry.getKey()) <= 0)
				if(entry.getValue() == 2) {
					list = new LinkedList<>();
					rank = entry.getKey();
					for(Card card:hand) {
						if(card.getRank().equals(rank))
							list.add(card);
					}
				}
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are 4 to a flush
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 9.
	private List<Card> fourToAFlush() {
		for(Map.Entry<Suit,Integer> entry: suitHits.entrySet())
			if(entry.getValue() == 4);
				//return true;
		//return false;
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are 3 to a royal flush
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 10.
	private List<Card> threeToARoyalFlush() {
		List<Card> list = new LinkedList<>();
		int royal = 0;
		// C,D,H,S
		int[] suits = {0,0,0,0};
		
		Rank rank = null;
		Suit suit = null;
		//Card curr = null;
		
		for(Card card:hand) {
			rank = card.getRank();
			suit = card.getSuit();
			
			if(Rank.TEN.compareTo(rank) >= 0) {
				royal++;
			
				if(Suit.CLUBS.equals(suit)) 
					++suits[0];
				if(Suit.DIAMONDS.equals(suit)) 
					++suits[1];
				if(Suit.HEARTS.equals(suit)) 
					++suits[2];
				if(Suit.SPADES.equals(suit)) 
					++suits[3];
			}
		}
		
		for(int s:suits)
			if(s == 4 && royal == 3);
				//return true;
		//return false;
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are 4 to an outside straight
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 11.
	private List<Card> fourToAnOutsideStraight() {
		List<Card> list = new LinkedList<>();
		//int tot = 0;
		Card curr = null;
		Card next = null;
		
		for(int i = 0; i < handSize - 1; i++) {
			curr = hand.get(i);
			next = hand.get(i+1);
			if(!curr.isNeighbour(next));
				//return false;
		}
		//return true;
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are low pair
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 12.
	private List<Card> lowPair() {
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(Rank.JACK.compareTo(entry.getKey()) <= 0)
				if(entry.getValue() == 2);
					//return true;
		//return false;
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are AKQJ unsuited
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 13. 
	private List<Card> akqjUnsuited() {
		int high = 0;
		for(Map.Entry<Rank,Integer> entry: rankHits.entrySet())
			if(Rank.JACK.compareTo(entry.getKey()) >= 0)
				high++;
		//return high == 4;
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are 3 to a straight flush (type 1)
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 14. TYPE 1
	private List<Card> threeToAStraightFlush1() {
		List<Card> list = new LinkedList<>();
		int i = 0, j = 0, tot = 0;
		int band = 5;
		// A,K,Q,J,T,9,8,7,6,5,4,3,2
		boolean[] ranks = new boolean[13];
		Suit suits = null;
		
		// incializa o ranks
		for(i = 0; i < 13; i++)
			ranks[i] = false;
		
		
		for(Map.Entry<Suit,Integer> entry: suitHits.entrySet()) {
			suits = entry.getKey();
		
			for(Card card:hand)
				if(card.getSuit().equals(suits))
					ranks[card.getRank().ordinal()] = true;
		
			for(i = 0; i < ranks.length; i++) {
				for(j = i, tot = 0; j < i + band; j++) {
					if(j >= ranks.length)
						if(ranks[j - ranks.length])
							tot++;
					if(j < ranks.length)
						if(ranks[j])
							tot++;
				}
				if(tot == 3);
					//return true;
			}
			
			for(i = 0; i < 13; i++)
				ranks[i] = false;
		}
		//return false;
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are 4 to an insisde striaght with 3 high cards
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 15.
	private List<Card> fourToAnInsideStraightWith3HighCards() {
		List<Card> list = new LinkedList<>();
		int i = 0, j = 0, tot = 0;
		int band = 5;
		// A,K,Q,J,T,9,8,7,6,5,4,3,2
		boolean[] ranks = new boolean[13];
		Suit suits = null;
		int high = 0;
		
		// incializa o ranks
		for(i = 0; i < 13; i++)
			ranks[i] = false;
		
		for(Map.Entry<Suit,Integer> entry: suitHits.entrySet()) {
			suits = entry.getKey();
		
			for(Card card:hand)
				if(card.getSuit().equals(suits))
					ranks[card.getRank().ordinal()] = true;
		
			for(i = 0; i < 3; i++) {
				for(j = i, tot = 0; j < i + band; j++) {
					if(j >= ranks.length)
						if(ranks[j - ranks.length])
							tot++;
					if(j < ranks.length)
						if(ranks[j])
							tot++;
				}
				if(tot == 3);
					//return true;
			}
			
			for(i = 0; i < 13; i++)
				ranks[i] = false;
		}
		//return false;
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are QJ suited
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 16.
	private List<Card> qjSuited() {
		List<Card> list = new LinkedList<>();
		int tot = 0;
		for(Map.Entry<Suit,Integer> entry: suitHits.entrySet()) {
			for(Card card:hand) {
				if(card.getRank().equals(Rank.QUEEN))
					tot++;
				if(card.getRank().equals(Rank.JACK))
					tot++;
			}
			if(tot == 2)
				//return true;
			tot = 0;
		}
		//return false;
		
		return new LinkedList<>(list);
	}
	
	/**
	 * Checks if card from hand are 3 to a flush with 2 high cards
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 17.
	private List<Card> threeToAFlushWith2HighCards() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are 2 suited high cards
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 18.
	private List<Card> twoSuitedHighCards() {
		List<Card> list = new LinkedList<>();
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are 4 to an insisde striaght with 2 high cards
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 19.
	private List<Card> fourToAnInsideStraightWith2HighCards() {
		List<Card> list = new LinkedList<>();
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are 3 to a straight flush (type 2)
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 20. TYPE 2
	private List<Card> threeToAStraightFlush2() {
		List<Card> list = new LinkedList<>();
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are four to an insisde straight with 1 high card
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 21.
	private List<Card> fourToAnInsideStraightWith1HighCard() {
		List<Card> list = new LinkedList<>();
		return new LinkedList<>();
	}
	
	/**
	 * Checks if card from hand are KQJ unsuited
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 22.
	private List<Card> kqjUnsuited() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are JT Suited
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 23.
	private List<Card> jtSuited() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are QJ Unsuited
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 24.
	private List<Card> qjUnsuited() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are 3 to a Flush with 1 high card
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 25.
	private List<Card> threeToAFlushWith1HighCard() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are QT suited
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 26.
	private List<Card> qtSuited() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are 3 to a straight flush (type3)
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 27. TYPE3
	private List<Card> threeToAStraightFlush3() {
		List<Card> list = new LinkedList<>();
		return list;
		
	}
	
	/**
	 * Checks if card from hand are KQ unsuited
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 28.1
	private List<Card> kqUnsuited() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are KJ Unsuited
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 28.2
	private List<Card> kjUnsuited() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are ace
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 29.
	private List<Card> ace() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are KT Suited
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 30.
	private List<Card> ktSuited() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are jack
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 31.1
	private List<Card> jack() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are queen
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 31.2
	private List<Card> queen() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are king
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 31.3
	private List<Card> king() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are 4 to an insisde striaght with no high cards
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 32.
	private List<Card> fourToAnInsideStraightWithNoHighCards() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * Checks if card from hand are 3 to a flush with no high cards 
	 * and returns the cards that make it so.
	 * 
	 * @return list of cards advised to keep
	 */
	// 33.
	private List<Card> threeToAFlushWithNoHighCards() {
		List<Card> list = new LinkedList<>();
		return list;
	}
	
	// 34. DISCARD 
	
	/**
	 * Implements logic to deliver the list of cards advised
	 * 
	 * @return return the list of cards advised
	 */
	public List<Card> whatsAdvised() {		
		List<List<Card>> bigList = new LinkedList<>();
		boolean[] empty = null;
		
		bigList.add(straightFlush()); //0
		bigList.add(fourOfAKind()); //1
		bigList.add(royalFlush()); //2
		bigList.add(fourToARoyalFlush()); //3
		bigList.add(threeAces()); //4
		bigList.add(straight()); //5
		bigList.add(flush()); //6
		bigList.add(fullHouse()); //7
		bigList.add(threeOfAKind()); //8
		bigList.add(fourToAStraightFlush()); //9
		bigList.add(twoPair()); //10
		bigList.add(highPair()); //11
		bigList.add(fourToAFlush()); //12
		bigList.add(threeToARoyalFlush()); //13
		bigList.add(fourToAnOutsideStraight()); //14
		bigList.add(lowPair()); //15
		bigList.add(akqjUnsuited()); //16
		bigList.add(threeToAStraightFlush1()); //17
		bigList.add(fourToAnInsideStraightWith3HighCards()); //18
		bigList.add(qjSuited()); //19
		bigList.add(threeToAFlushWith2HighCards()); //20
		bigList.add(twoSuitedHighCards()); //21
		bigList.add(fourToAnInsideStraightWith2HighCards()); //22
		bigList.add(threeToAStraightFlush2()); //23
		bigList.add(fourToAnInsideStraightWith1HighCard()); //24
		bigList.add(kqjUnsuited()); //25
		bigList.add(jtSuited()); //26
		bigList.add(qjUnsuited()); //27
		bigList.add(threeToAFlushWith1HighCard()); //28
		bigList.add(qtSuited()); //29
		bigList.add(threeToAStraightFlush3()); //30
		bigList.add(kqUnsuited()); //31
		bigList.add(kjUnsuited()); //32
		bigList.add(ace()); //33
		bigList.add(ktSuited()); //34
		bigList.add(jack()); //35
		bigList.add(queen()); //36
		bigList.add(king()); //37
		bigList.add(fourToAnInsideStraightWithNoHighCards()); //38
		bigList.add(threeToAFlushWithNoHighCards()); // 39
		
		// Initializes list
		empty = new boolean[bigList.size()];
		
		for(int i = 0; i < bigList.size(); i++) {
			if(bigList.get(i).isEmpty())
				empty[i] = false;
			else
				empty[i] = true;
		}
		
		// Difficult hands
		// 1
		if(empty[0] && empty[3])
			return bigList.get(0);
		// 2
		if(empty[3] && empty[5]) 
			return bigList.get(3);
		// 3
		if(empty[3] && empty[6]) 
			return bigList.get(3);
		// 4
		if(empty[4] && empty[7]) 
			return bigList.get(4);
		// 5
		if(empty[7] && empty[8]) 
			return bigList.get(7);
		// 6
		if(empty[6] && empty[9]) 
			return bigList.get(6);
		// 7
		if(empty[5] && empty[9]) 
			return bigList.get(5);
		// 8
		if(empty[5] && empty[13]) 
			return bigList.get(5);
		// 9
		if(empty[9] && empty[13]) 
			return bigList.get(9);
		// 10
		if(empty[10] && empty[13]) 
			return bigList.get(10);
		// 11
		if(empty[11] && empty[12]) 
			return bigList.get(11);
		// 12
		if(empty[11] && empty[13]) 
			return bigList.get(11);
		// 13
		if(empty[12] && empty[13]) 
			return bigList.get(12);
		// 14
		if(empty[12] && empty[15]) 
			return bigList.get(12);
		// 15
		if(empty[13] && empty[14]) 
			return bigList.get(13);
		// 16
		if(empty[13] && empty[15]) 
			return bigList.get(13);
		// 17
		if(empty[14] && empty[15]) 
			return bigList.get(14);
		// 18
		if(empty[14] && empty[17]) 
			return bigList.get(14);
		// 19
		if(empty[15] && empty[17]) 
			return bigList.get(15);
		// 20
		if(empty[15] && empty[18]) 
			return bigList.get(15);
		// 21
		if(empty[16] && empty[17]) 
			return bigList.get(16);
		// 22
		if(empty[16] && empty[18]) 
			return bigList.get(16);
		// 23
		if(empty[16] && empty[19]) 
			return bigList.get(16);
		// 24
		if(empty[17] && empty[22]) 
			return bigList.get(17);
		// 25
		if(empty[17] && empty[26]) 
			return bigList.get(17);
		// 26
		if(empty[18] && empty[19]) 
			return bigList.get(18);
		// 27
		if(empty[18] && empty[20]) 
			return bigList.get(18);
		// 28
		if(empty[18] && empty[21]) 
			return bigList.get(18);
		// 29
		if(empty[18] && empty[23]) 
			return bigList.get(18);
		// 30
		if(empty[19] && empty[20]) 
			return bigList.get(19);
		// 31
		if(empty[19] && empty[22]) 
			return bigList.get(19);
		// 32
		if(empty[19] && empty[23]) 
			return bigList.get(19);
		if(empty[19] && empty[23]) 
			return bigList.get(30);
		
		// 33
		if(empty[20] && empty[21]) 
			return bigList.get(20);
		// 34
		if(empty[20] && empty[22]) 
			return bigList.get(20);
		// 35
		if(empty[20] && empty[24]) 
			return bigList.get(20);
		// 36
		if(empty[21] && empty[22]) 
			return bigList.get(21);
		// 37
		if(empty[21] && empty[23]) 
			return bigList.get(21);
		if(empty[21] && empty[30]) 
			return bigList.get(30);
		
		// 38
		if(empty[22] && empty[23]) 
			return bigList.get(22);
		if(empty[22] && empty[30]) 
			return bigList.get(22);
		// 39
		if(empty[22] && empty[28]) 
			return bigList.get(22);
		// 40
		if(empty[23] && empty[24]) 
			return bigList.get(23);
		// 41
		if(empty[24] && empty[26]) 
			return bigList.get(24);
		// 42
		if(empty[24] && empty[28]) 
			return bigList.get(24);
		// 43
		if(empty[24] && empty[30]) 
			return bigList.get(24);
		// 44
		if(empty[26] && empty[27]) 
			return bigList.get(26);
		// 45
		if(empty[26] && empty[28]) 
			return bigList.get(26);
		// 46
		if(empty[26] && empty[30]) 
			return bigList.get(26);
		// 47
		if(empty[26] && empty[31]) 
			return bigList.get(26);
		if(empty[26] && empty[32]) 
			return bigList.get(26);
		
		// 48 
		if(empty[26] && empty[33]) 
			return bigList.get(26);
		// 49
		if(empty[26] && empty[38]) 
			return bigList.get(26);
		// 50
		if(empty[26] && empty[39]) 
			return bigList.get(26);
		// 51
		if(empty[27] && empty[28]) 
			return bigList.get(27);
		// 52
		if(empty[27] && empty[29]) 
			return bigList.get(27);
		// 53
		if(empty[27] && empty[30]) 
			return bigList.get(27);
		// 54
		if(empty[27] && empty[33]) 
			return bigList.get(27);
		// 55
		if(empty[27] && empty[39]) 
			return bigList.get(27);
		// 56
		if(empty[28] && empty[29]) 
			return bigList.get(28);
		// 57
		if(empty[28] && empty[31]) 
			return bigList.get(28);
		if(empty[28] && empty[32]) 
			return bigList.get(28);
		
		// 58
		if(empty[28] && empty[33]) 
			return bigList.get(28);
		// 59
		if(empty[28] && empty[34]) 
			return bigList.get(28);
		// 60
		if(empty[28] && empty[38]) 
			return bigList.get(28);
		// 61
		if(empty[29] && empty[30]) 
			return bigList.get(29);
		// 62
		if(empty[29] && empty[31]) 
			return bigList.get(29);
		// 63
		if(empty[29] && empty[35]) 
			return bigList.get(29);
		if(empty[29] && empty[37]) 
			return bigList.get(29);
		if(empty[29] && empty[33]) 
			return bigList.get(29);
		
		// 64
		if(empty[29] && empty[38]) 
			return bigList.get(29);
		// 65
		if(empty[29] && empty[39]) 
			return bigList.get(29);
		// 66
		if(empty[30] && empty[31]) 
			return bigList.get(30);
		if(empty[30] && empty[32]) 
			return bigList.get(30);
		
		// 67
		if(empty[30] && empty[35]) 
			return bigList.get(30);
		if(empty[30] && empty[36]) 
			return bigList.get(30);
		if(empty[30] && empty[37]) 
			return bigList.get(30);
		if(empty[30] && empty[33]) 
			return bigList.get(30);
		
		// 68
		if(empty[30] && empty[34]) 
			return bigList.get(30);
		// 69
		if(empty[30] && empty[38]) 
			return bigList.get(30);
		// 70
		if(empty[31] && empty[33]) 
			return bigList.get(31);
		if(empty[32] && empty[33]) 
			return bigList.get(32);
		
		// 71
		if(empty[31] && empty[34]) 
			return bigList.get(31);
		if(empty[32] && empty[34]) 
			return bigList.get(32);
		
		// 72
		if(empty[31] && empty[39]) 
			return bigList.get(31);
		if(empty[32] && empty[39]) 
			return bigList.get(32);
		
		// 73
		if(empty[33] && empty[34]) 
			return bigList.get(33);
		// 74
		if(empty[33] && empty[35]) 
			return bigList.get(33);
		if(empty[33] && empty[36]) 
			return bigList.get(33);
		if(empty[33] && empty[37]) 
			return bigList.get(33);
	
		// 75
		if(empty[33] && empty[38]) 
			return bigList.get(33);
		// 76
		if(empty[33] && empty[39]) 
			return bigList.get(33);
		// 77
		if(empty[34] && empty[38]) 
			return bigList.get(34);
		// 78
		if(empty[34] && empty[39]) 
			return bigList.get(34);
		// 79
		if(empty[35] && empty[38]) 
			return bigList.get(35);
		if(empty[36] && empty[38]) 
			return bigList.get(36);
		if(empty[37] && empty[38]) 
			return bigList.get(37);
		
		// 80
		if(empty[35] && empty[39]) 
			return bigList.get(35);
		if(empty[36] && empty[39]) 
			return bigList.get(36);
		if(empty[37] && empty[39]) 
			return bigList.get(37);
		
		// 81
		if(empty[38] && empty[39]) 
			return bigList.get(38);
		
		// 
		for(int i = 0; i < bigList.size(); i++)
			if(!bigList.get(i).isEmpty())
				return bigList.get(i);
		
		return new LinkedList<Card>();
	}
}
