/**
 * QuickReviewActivity.java
 * 
 * Created by zouyong on Oct 9, 2014,2014
 */
package com.chriszou.words;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.chriszou.androidlibs.Prefs;

/**
 * @author zouyong
 *
 */
@EActivity(R.layout.review_activity)
public class QuickReviewActivity extends ReviewActivity implements OnPageChangeListener {
	private static final String PREF_INT_QUICK_REVIEW_START = "pref_int_quick_review_start";
	private static final int QUICK_REVIEW_COUNT = 3;

	@Override
	protected List<Word> getWords() {
		List<Word> words = new WordModel(this).getCache();
		int quickReviewStart = Prefs.getInt(PREF_INT_QUICK_REVIEW_START, 0);
		List<Word>results = new ArrayList<Word>();
		int count = words.size();
		for(int i=0; i<QUICK_REVIEW_COUNT; i++) {
			int index = (quickReviewStart+i) % count;
			results.add(words.get(index));
		}
		Prefs.putInt(PREF_INT_QUICK_REVIEW_START, (quickReviewStart+QUICK_REVIEW_COUNT)%count);
		results.add(new Word("", "", ""));
		return results;
	}

	@Override
	protected int getStartIndex() {
		return 0;
	}

	@AfterViews
	void initViews() {
		getViewPager().setOnPageChangeListener(this);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		if(arg0==QUICK_REVIEW_COUNT) {
			finish();
		}
	}

}
