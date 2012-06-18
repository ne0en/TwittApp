package si.fis.android.twittapp;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RetweetActivity extends Activity implements OnClickListener {
	private EditText vnosnoPolje;
	TextView ime;
	Twitter twitter;
	Button buttonRetweet;
	Button buttonClear;
	Button buttonCancel;
	Long stID, uID;
	Button buttonReply;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.retweet);
		
		Bundle bundle = this.getIntent().getExtras();
		String param1 = bundle.getString("param1");
		String param2 = bundle.getString("param2");
		stID = bundle.getLong("param3");
		
		vnosnoPolje = (EditText) findViewById(R.id.vnosP);
		vnosnoPolje.setText(param1);
		
		ime = (TextView) findViewById(R.id.ime);
		ime.setText(param2);
		
		twitter = ((TwittApplication) getApplication()).getTwitter();
		
		buttonRetweet = (Button) findViewById(R.id.buttonReTweet);
		buttonRetweet.setOnClickListener(this);
		
		buttonClear = (Button) findViewById(R.id.buttonClear);
		buttonClear.setOnClickListener(this);
		
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(this);
		
		buttonReply = (Button) findViewById(R.id.buttonReply);
		buttonReply.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		vnosnoPolje = (EditText) findViewById(R.id.vnosP);
		switch (v.getId()) {
		case R.id.buttonReTweet:
			try {
				 twitter.retweetStatus(stID);
				 vnosnoPolje.setText("");
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case R.id.buttonReply:
			try {
				
				twitter.updateStatus(new StatusUpdate("@"+ime.getText().toString()+" "+vnosnoPolje.getText()).inReplyToStatusId(stID));
				Toast.makeText(this, stID + " selected", Toast.LENGTH_LONG).show();
				 
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
