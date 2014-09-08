/**
 * 
 */
package com.chriszou.words;

import java.io.IOException;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.BaseViewBinderAdapter;
import com.chriszou.androidlibs.BaseViewBinderAdapter.ViewBinder;
import com.chriszou.androidlibs.Notifier;

/**
 * @author Chris
 *
 */
@EActivity(R.layout.main)
public class WordsActivity extends Activity {

	private List<Word> mWords;
    
    @ViewById(R.id.main_listview)
    ListView mListView;
    
    @ViewById(R.id.main_emptyView)
    TextView mEmptyView;
    
    ViewBinder<Word> mViewBinder = new BaseViewBinderAdapter.ViewBinder<Word>() {
		@Override
		public void bindView(int position, View view, Word item, ViewGroup parent) {
			TextView wordView = (TextView) view.findViewById(R.id.word_item_title);
            TextView meaningView = (TextView)view.findViewById(R.id.word_item_meaning);
            wordView.setText(item.title);
            meaningView.setText(item.meaning);
		}
	};
    
    @AfterViews
    void loadData() {
        addNotification("Add a word", "Add a word");
    	getWords();
    }
    
    private void addNotification(String title, String text) {
    	Notifier notifier = new Notifier(this);
        notifier.setOnGoing(true);
		notifier.fireActivity(R.drawable.words, title, text, AddWordActivity_.class);
    }
    
    @Background
    void getWords() {
    	try {
			mWords = new WordModel().getWords();
            updateList();
		} catch (IOException e) {
			e.printStackTrace();
            notifyError("Error connecting to server.");
		}
    }
    
    @UiThread
    void notifyError(String text) {
        mEmptyView.setVisibility(View.VISIBLE);
    	mEmptyView.setText(text);
    }
    
    void hideError() {
    	mEmptyView.setVisibility(View.GONE);
    }
    
    @UiThread
    void updateList() {
        if(mWords.size()>0) {
        	BaseViewBinderAdapter<Word> adapter = new BaseViewBinderAdapter<Word>(this, mWords, R.layout.word_item, mViewBinder);
            mListView.setAdapter(adapter);
        	addNotification(mWords.get(0).title, mWords.get(0).meaning);
            hideError();
        } else {
        	notifyError("Seems you don't have any word yet, click add to add one.");
        }
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
		case R.id.action_add:
			startActivity(new Intent(this, AddWordActivity_.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

    
}
