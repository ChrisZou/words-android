/**
 * 
 */
package com.chriszou.words;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.EditText;

/**
 * @author Chris
 *
 */
@EActivity(R.layout.add_layout)
public class AddWordActivity extends Activity {

	@ViewById(R.id.main_word)
	EditText mWordEdit;

	@ViewById(R.id.main_meaning)
	EditText mMeaningEdit;

	@ViewById(R.id.main_example)
	EditText mExampleEdit;
    
	@AfterViews
	void loadData() {
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
		String pasteData = item.getText().toString();
        mExampleEdit.setText(pasteData);
	}

	@Click(R.id.main_ok)
	void addWord() {
		String word = mWordEdit.getText().toString();
		String meaning = mMeaningEdit.getText().toString();
		String example = mExampleEdit.getText().toString();

		if (word.length() > 0 && meaning.length() > 0 && example.length() > 0) {
			executeAdd(word, meaning, example);
		}
	}

	@Background
	void executeAdd(String word, String meaning, String example) {
		WordModel model = new WordModel();
		int result = model.addWord(word, meaning, example);
        onAddingResult(result);
	}
    
	@UiThread
	void onAddingResult(int resultCode) {
		if(resultCode==201) {
			finish();
		}
	}

}
