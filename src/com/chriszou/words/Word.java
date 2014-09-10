/**
 * 
 */
package com.chriszou.words;

import java.io.Serializable;

import com.google.gson.Gson;


/**
 * @author Chris
 *
 */
public class Word implements Serializable{
	private static final long serialVersionUID = 1L;

	public String id;
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
