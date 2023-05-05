package com.oop.project;

/**
 * Keeps tabs on statistics
 * 
 * @author group 45
 *
 */
public class Statistics {
	private final static int size = Play.values().length;
	private final static int[] statistics = new int[size];
	private int balance;
	private int credit;
	
	/**
	 * Constructor for statistics class
	 * @param balance machine balance
	 */
	Statistics(int balance) {
		int i = 0;
		for(i = 0; i < size; i++)
			statistics[i] = 0;
		
		this.balance = balance;
	}
	
	/**
	 * Sets credit paramenter
	 * @param credit type int. total ammount of credits
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}

	/**
	 * Sets balance parameter from class.
	 * 
	 * @param balance machine balance
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	/**
	 * It add one more play to the statistics given a Play class
	 * @param play is of type Class. Should contain the play to add.
	 * @return returns the play added
	 */
	public String sum(Play play) {
		int index = play.ordinal();
		int sum = ++statistics[index];
		statistics[index] = sum;
		return String.valueOf(sum);
	}
	
	/**
	 * returns the total amount of plays. (Play.OHTER included)
	 * @return returns the total number of plays
	 */
	private int total() {
		int tot = 0;
		for(int i = 0; i < size;i++) 
			tot = tot + statistics[i];
		return tot;
	}
	
	/**
	 * Returns a percentage given initial balance and current credits
	 * @return percentage returned 
	 */
	private double percentage() {
		Double resb = Double.valueOf(balance);
		Double resc = Double.valueOf(credit);
		return resc/resb * 100;
	}
	
	
	@Override
	public String toString() {
		String header = "Hand\t\t\t   Nb\n";
		String total = String.format("Total\t\t\t    %s\n",total());
		String balance = String.format("Credit\t\t\t%s (%.2f%s)\n",this.credit,percentage(),"%");
		String line = "-----------------------------------\n";
		String body = "";
		
		int index = 0;
		String playName = "";
		for(Play play:Play.values()) {
			playName = play.getPlay();
			index = play.ordinal();
			if(Play.FLUSH == play || Play.OTHER == play)
				body = body + String.format("%s\t\t\t    %s\n", playName, statistics[index]);
			else
				body = body + String.format("%s\t\t    %s\n", playName, statistics[index]);
		}
		return header + line + body + line + total + line + balance;
	}
}
