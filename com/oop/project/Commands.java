package com.oop.project;

import java.util.HashMap;

enum Command {
	BET("b"), CREDIT("$"), DEAL("d"), HOLD("h"), ADVICE("a"), STATISTICS("s");

	private final String command;
	private final static HashMap<String,Command> map;

	/**
	 * Gives command constructor
	 * 
	 * @param command
	 */
	Command(String command) {
		this.command = command;
	}
	
	/**
	 * returns the command attribute of command class 
	 * 
	 * @return
	 */
	public String getCommand() {
		return command;
	}
	
	static {
		map = new HashMap<String,Command>();

		for(Command c:Command.values())
			try {
				map.put(c.getCommand(),c);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * return the Command enum given a input String.
	 * returns null if it doesn't find a match.
	 * 
	 * @param constant String type it must match one of teh enums.
	 * @return if parameter doesn't match one of the enums it returns null
	 */
	public static Command getConstant(String constant) {
		return map.get(constant);
	}
}
