/**
 * WordPageAdapter.java
 * 
 * Created by zouyong on Oct 9, 2014,2014
 */
package com.chriszou.words;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author zouyong
 *
 */
public class WordPagerAdapter extends FragmentStatePagerAdapter {
	List<Word>mWords;
	public WordPagerAdapter(FragmentManager fm, List<Word>words ) {
		super(fm);
		mWords = words;
	}

	@Override
	public Fragment getItem(int position) {
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
