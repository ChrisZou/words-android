/**
 * 
 */
package com.chriszou.words;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.BaseViewBinderAdapter;
import com.chriszou.androidlibs.Notifier;
import com.chriszou.androidlibs.BaseViewBinderAdapter.ViewBinder;

/**
 * @author Chris
 *
 */
@EActivity(R.layout.main)
public class WordsActivity extends Activity {

	private List<Word> mWords;
    
    @ViewById(R.id.main_listview)
    ListView mListView;
    
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
        notifier.fireActivity(R.drawable.words, title, text, getClass());
    }
    
    @Background
    void getWords() {
    	mWords = new WordModel().getWords();
        updateList();
        
        if(mWords.size()>0) {
        	addNotification(mWords.get(0).title, mWords.get(0).meaning);
        }
    }
    
    @UiThread
    void updateList() {
    	BaseViewBinderAdapter<Word> adapter = new BaseViewBinderAdapter<Word>(this, mWords, R.layout.word_item, mViewBinder);
        mListView.setAdapter(adapter);
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
