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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.chriszou.androidlibs.L;

/**
 * @author zouyong
 *
 */
@EActivity(R.layout.review_activity)
public class ReviewActivity extends   FragmentActivity  {

	public static final String EXTRA_STRING_WORD_ID = "extra_string_word_id";

	private List<Word>mWords;
	@ViewById(R.id.review_pager)
	ViewPager mPager;

	@AfterViews
	void loadWord() {
		mWords = new WordModel(this).getCache();

		String id = getIntent().getStringExtra(EXTRA_STRING_WORD_ID);

		int start = 0;
		if(id!=null) {
			for (int i = 0; i < mWords.size(); i++) {
				if(mWords.get(i).id.equals(id)){
					start = i;
					break;
				}
			}
		}

		mPagerAdapter = new WordsPagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
	}

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the system to handle the
			// Back button. This calls finish() on this activity and pops the back stack.
			super.onBackPressed();
		} else {
			// Otherwise, select the previous step.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
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
			Word word = mWords.get(position);
			WordPageFragment_ fragment = new WordPageFragment_();
			Bundle args = new Bundle();
			args.putSerializable(WordPageFragment.EXTRA_SERIAL_WORD, word);
			fragment.setArguments(args);
			L.l("get item "+position);
			return fragment;
		}

		@Override
		public int getCount() {
			return mWords.size();
		}
	}
}