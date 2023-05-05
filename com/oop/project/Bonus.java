package com.oop.project;


/**
 * Store the value of the pays multipliers.
 */
public enum Bonus {
	ROYALFLUSH(250),STRAIGHTFLUSH(50),FOURACES(160),FOUR24(80),FOUR5K(50),
	FULLHOUSE(10), FLUSH(7),STRAIGHT(5),THREEOFAKIND(3),TWOPAIR(1),JACKSORBETTER(1);
	
	/**
	 * Store the values of the plays multipliers 
	*/
	private final Integer bonus;
	
	/**
	 * Constructor
	 * @param pay multiplier value for the game
	 */
	Bonus(Integer bonus) {
		this.bonus = bonus;
	}
	
	/**
	 * Pay variable get function.
	 * @return pay value
	 */
	public Integer getBonus() {
		return bonus;
	}
	
	/**
	 * String value of pay constants.
	 * 
	 * @return String pay value
	 */
	@Override
	public String toString() {
		return String.valueOf(bonus);
	}
	
	/**
	 * Returns the according bonus given a hand
	 * 
	 * @param hand hand is a PokerHand
	 * @return value of the bonus
	 */
	public static Integer bonus(PokerHand hand) {
		Object[] cards = hand.getHand().toArray();
		Play play = Play.check(cards);
		if(play.equals(Play.ROYALFLUSH)) return ROYALFLUSH.getBonus();
		
		if(Play.FOURACES(cards)) return FOURACES.getBonus();
		if(Play.FOUR24(cards)) return FOUR24.getBonus();
		if(Play.FOUR5K(cards))return FOUR5K.getBonus();
		
		if(play.equals(Play.STRAIGHTFLUSH)) return STRAIGHTFLUSH.getBonus();
		if(play.equals(Play.FULLHOUSE)) return FULLHOUSE.getBonus();
		if(play.equals(Play.FLUSH)) return STRAIGHT.getBonus();
		if(play.equals(Play.STRAIGHT)) return STRAIGHT.getBonus();
		if(play.equals(Play.THREEOFAKIND)) return THREEOFAKIND.getBonus();
		if(play.equals(Play.TWOPAIR)) return TWOPAIR.getBonus();
		if(play.equals(Play.JACKSORBETTER)) return JACKSORBETTER.getBonus();
		return 0;
	}
}
