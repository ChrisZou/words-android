/**
 * ScreenSlidePageFragment.java
 * 
 * Created by zouyong on Sep 10, 2014,2014
 */
package com.chriszou.words;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.chriszou.androidlibs.L;

/**
 * @author zouyong
 *
 */
@EFragment(R.layout.word_slide_page)
public class WordPageFragment extends Fragment {
	public static final String EXTRA_SERIAL_WORD = "extra_serial_word";

	public WordPageFragment() {
	}

	@ViewById(R.id.review_title)
	TextView mTitleView;

	@ViewById(R.id.review_meaning)
	TextView mMeaningView;

	@ViewById(R.id.review_example)
	TextView mExampleView;

	@AfterViews
	void loadWord() {
		L.l("loadword");
		Word word = (Word) getArguments().getSerializable(EXTRA_SERIAL_WORD);
		mTitleView.setText(word.title);
		mMeaningView.setText(word.meaning);
		mExampleView.setText(word.example);
	}
}
