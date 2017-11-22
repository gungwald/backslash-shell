package com.alteredmechanism.backslashshell;

public class Tokenizer {

	private char[] text;
	private int i;

	public Tokenizer(String text) {
		this.text = text.toCharArray();
		this.i = 0;
	}

	public void reset() {
		i = 0;
	}
	
	public String getToken() {
		StringBuilder token = new StringBuilder();
		boolean tokenFinished = false;
		boolean tokenStarted = false;
		while (i < text.length && !tokenFinished) {
			char c = text[i];
			if (isTokenChar(c)) {
				token.append(c);
				tokenStarted = true;
				i++;
			}
			else if (tokenStarted) {
				tokenFinished = true;
			}
			else {
				i++;
			}
		}
		return token.toString();
	}

	private boolean isTokenChar(char c) {
		return ! Character.isWhitespace(c);
	}

}
