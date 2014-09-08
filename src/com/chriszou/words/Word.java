/**
 * 
 */
package com.chriszou.words;

import com.google.gson.Gson;


/**
 * @author Chris
 *
 */
public class Word {
	public String title;
	public String meaning;
	public String example;

	public Word(String word, String meaning, String example) {
		this.title = word;
		this.meaning = meaning;
		this.example = example;
	}

	/**
	 * 
	 */
	public Word() {
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

	@Override
	public String toString() {
		return toJson();
	}
}
