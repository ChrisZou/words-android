/**
 * 
 */
package com.chriszou.words;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.chriszou.androidlibs.L;

/**
 * @author Chris
 *
 */
public class WordModel {
	public List<Word> getWords() {
		List<Word> words = new ArrayList<Word>();
		return words;
	}
	
	public void addWord(Word word) {
		
	}

	public void addWord(String word, String meaning, String example) {
		try {
			Word w = new Word(word, meaning, example);
			HttpPost post = new HttpPost("http://woaifuxi.com:3006/words.json");
			post.setHeader("Content-Type", "application/json");
			String data = w.toJson();
			L.l("data: " + data);
			post.setEntity(new StringEntity(data));
			HttpClient client = new DefaultHttpClient();
			client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
