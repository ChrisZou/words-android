/**
 * 
 */
package com.chriszou.words;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.preference.PreferenceManager;

import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.UrlContentLoader;

/**
 * @author Chris
 *
 */
public class WordModel {
	public Context mContext;
	public WordModel(Context context) {
		mContext = context;
	}
	private static final String SERVER_URL = "http://woaifuxi.com:3006/words.json";
	/**
	 * Get words from web server synchronized, should call this method in background thread
	 * @return
	 * @throws IOException
	 */
	public List<Word> getWords() throws IOException {
		UrlContentLoader loader = new UrlContentLoader(SERVER_URL);
		String jsonString = loader.executeSync();

		if(jsonString.length()>0) {
			saveCache(jsonString);
		}

		List<Word> words = jsonStringToList(jsonString);
		return words;
	}

	public void addWord(Word word) {
		addWord(word.title, word.meaning, word.example);
	}

	/**
	 * Add a word synchonized, return the status code of the response
	 * @param word
	 * @param meaning
	 * @param example
	 * @return
	 */
	public int addWord(String word, String meaning, String example) {
		try {
			Word w = new Word(word, meaning, example);
			HttpPost post = new HttpPost(SERVER_URL);
			post.setHeader("Content-Type", "application/json");
			String data = w.toJson();
			L.l("data: " + data);
			post.setEntity(new StringEntity(data, "UTF-8"));
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 5*1000);
			HttpConnectionParams.setSoTimeout(params, 5*1000);
			DefaultHttpClient client = new DefaultHttpClient(params);
			HttpResponse response = client.execute(post);
			StatusLine status = response.getStatusLine();
			L.l("status: "+status.getStatusCode());
			return status.getStatusCode();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static final String PREF_STRING_CACHE = "pref_string_cache";
	private void saveCache(String jsonString) {
		PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString(PREF_STRING_CACHE, jsonString).commit();
	}
	private static List<Word> jsonStringToList(String jsonString) {
		List<Word> words = new ArrayList<Word>();
		try {
			JSONArray array = new JSONArray(jsonString);
			for(int i=0; i<array.length(); i++) {
				JSONObject wordJson = array.optJSONObject(i);
				Word word = new Word();
				word.title = wordJson.optString("title");
				word.meaning = wordJson.optString("meaning");
				word.example = wordJson.optString("example");
				words.add(word);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return words;

	}

	/**
	 * Get the cached data
	 * @return
	 */
	public List<Word> getCache() {
		List<Word> words = new ArrayList<Word>();
		String jsonString = PreferenceManager.getDefaultSharedPreferences(mContext).getString(PREF_STRING_CACHE, null);
		if(jsonString!=null) {
			words = jsonStringToList(jsonString);
		}
		return words;
	}
}
