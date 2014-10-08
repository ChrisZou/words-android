/**
 * ReviewActivity.java
 * 
 * Created by zouyong on Sep 10, 2014,2014
 */
package com.chriszou.words;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.chriszou.androidlibs.Prefs;

/**
 * @author zouyong
 *
 */
@EActivity(R.layout.review_activity)
public class ReviewActivity extends   FragmentActivity  {

	public static final String EXTRA_STRING_WORD_ID = "extra_string_word_id";
	public static final String EXTRA_BOOL_QUICK_REVIEW = "extra_bool_quick_review";
	public static final String PREF_INT_QUICK_REVIEW_START = "pref_int_quick_review_start";
	private static final int QUICK_REVIEW_COUNT = 3;

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

		mPagerAdapter = new WordsPagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(start);
	}

	private boolean quickReviewMode() {
		return getIntent().getBooleanExtra(EXTRA_BOOL_QUICK_REVIEW, false);
	}

	private List<Word> getWords() {
		List<Word> words = new WordModel(this).getCache();

		if(quickReviewMode()) {
			int quickReviewStart = Prefs.getInt(PREF_INT_QUICK_REVIEW_START, 0);
			List<Word>results = new ArrayList<Word>();
			int count = words.size();
			for(int i=0; i<QUICK_REVIEW_COUNT; i++) {
				int index = (quickReviewStart+i) % count;
				results.add(words.get(index));
			}
			Prefs.putInt(PREF_INT_QUICK_REVIEW_START, (quickReviewStart+QUICK_REVIEW_COUNT)%count);
			return results;
		}
		return words;
	}

	private int getStartIndex() {
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

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
	 * sequence.
	 */
	private class WordsPagerAdapter extends FragmentStatePagerAdapter {
		public WordsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if(quickReviewMode()&&position==QUICK_REVIEW_COUNT) {
				finish();
			}

			Word word = mWords.get(position);
			WordPageFragment_ fragment = new WordPageFragment_();
			Bundle args = new Bundle();
			args.putSerializable(WordPageFragment.EXTRA_SERIAL_WORD, word);
			args.putInt(WordPageFragment.EXTRA_INT_count, mWords.size());
			args.putInt(WordPageFragment.EXTRA_INT_INDEX, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return mWords.size();
		}
	}
}