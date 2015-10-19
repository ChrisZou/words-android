/**
 * 
 */
package com.chriszou.androidlibs;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * @author Chris
 * 
 */
public class UrlUtils {
	public static HttpResponse postJson(String url, String jsonString) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json");
		post.setEntity(new StringEntity(jsonString, "UTF-8"));
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5 * 1000);
		HttpConnectionParams.setSoTimeout(params, 5 * 1000);
		DefaultHttpClient client = new DefaultHttpClient(params);
		HttpResponse response = client.execute(post);
		return response;
	}
}
