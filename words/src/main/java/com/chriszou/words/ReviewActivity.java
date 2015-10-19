/**
 * ReviewActivity.java
 * 
 * Created by zouyong on Sep 10, 2014,2014
 */
package com.chriszou.words;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * @author zouyong
 *
 */
@EActivity(R.layout.review_activity)
public class ReviewActivity extends FragmentActivity  {
	public static final String EXTRA_STRING_WORD_ID = "extra_string_word_id";
	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	private List<Word>mWords;
	@ViewById(R.id.review_pager)
	ViewPager mPager;

	@AfterViews
	void loadWord() {
		mWords = getWords();
		int start = getStartIndex();

		mPagerAdapter = new WordPagerAdapter(getSupportFragmentManager(), mWords);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(start);
	}

	protected ViewPager getViewPager() {
		return mPager;
	}

	protected List<Word> getWords() {
		List<Word> words = new WordModel(this).getCache();
		return words;
	}

	protected int getStartIndex() {
		int start = 0;
		String id = getIntent().getStringExtra(EXTRA_STRING_WORD_ID);
		if(id==null) {
			return start;
		}

		for(Word word : mWords) {
			if(word.id.equals(id)){
				start = mWords.indexOf(word);
				break;
			}
		}

		return start;
	}
}