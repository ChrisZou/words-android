/**
 * ScreenSlidePageFragment.java
 * 
 * Created by zouyong on Sep 10, 2014,2014
 */
package com.chriszou.words;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.chriszou.androidlibs.L;

/**
 * @author zouyong
 *
 */
@EFragment(R.layout.word_slide_page)
public class WordPageFragment extends Fragment {
	public static final String EXTRA_SERIAL_WORD = "extra_serial_word";
	public static final String EXTRA_INT_INDEX = "extra_int_index";
	public static final String EXTRA_INT_count = "extra_int_count";

	public WordPageFragment() {
	}

	@ViewById(R.id.review_title)
	TextView mTitleView;

	@ViewById(R.id.review_meaning)
	TextView mMeaningView;

	@ViewById(R.id.review_example)
	TextView mExampleView;

	@ViewById(R.id.review_count)
	TextView mCountView;

	Word mWord;
	@AfterViews
	void loadWord() {
		L.l("loadword");
		mWord = (Word) getArguments().getSerializable(EXTRA_SERIAL_WORD);
		mTitleView.setText(mWord.title);

		String example = mWord.example;
		int start = example.indexOf(mWord.title);
		if (start>0) {
			SpannableString span = new SpannableString(example);
			span.setSpan(new ForegroundColorSpan(Color.parseColor("#6666cc")), start, start+mWord.title.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
			mExampleView.setText(span);
		} else {
			mExampleView.setText(example);
		}

		int index = getArguments().getInt(EXTRA_INT_INDEX);
		int count = getArguments().getInt(EXTRA_INT_count);
		mCountView.setText((index+1)+"/"+count);
	}

	@Click(R.id.content)
	void onContentClicked() {
		mMeaningView.setText(mWord.meaning);;
	}
}
