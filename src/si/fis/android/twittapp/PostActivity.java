package si.fis.android.twittapp;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PostActivity extends Activity implements OnClickListener {
	Button buttonTweet;
	Button buttonCancel;
	Button buttonClear;
	TextView myName;
	private EditText vnosnoPolje;
	Twitter twitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post);
		
		twitter = ((TwittApplication) getApplication()).getTwitter();
		
		buttonTweet = (Button) findViewById(R.id.buttonTweet);
		buttonTweet.setOnClickListener(this);
		
		buttonClear = (Button) findViewById(R.id.buttonClear);
		buttonClear.setOnClickListener(this);
		
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(this);
		
		myName = (TextView) findViewById(R.id.myName);
		try {
			myName.setText(twitter.getScreenName());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		vnosnoPolje = (EditText) findViewById(R.id.vnosP);
		switch (v.getId()) {
		case R.id.buttonTweet:
			try {
				String text = vnosnoPolje.getText().toString();
				twitter.updateStatus(text);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case R.id.buttonClear:
			String vp = vnosnoPolje.getText().toString();

			if (vp.length() > 0) {
				vnosnoPolje.setText(vp.substring(0, 0));
			}
			break;

		case R.id.buttonCancel:
			finish();
			break;

		default:
			break;
		}

	}

}
