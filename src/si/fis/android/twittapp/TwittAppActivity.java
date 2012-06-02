package si.fis.android.twittapp;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TwittAppActivity extends Activity {
    Twitter twitter;
	
    private static String TAG = "TwittApp";
    
    private static String CONSUMER_KEY = "B1VVyQKZNU1B3djHT4WNA";
    private static String CONSUMER_SECRET = "P660Y2DvDxDeJ6r6U4hl9Wflig19b92kU4nGsrH1G0";
    private static String CALLBACK_URL = "x-feelit://callback";
	
	Button buttonLogin;
	/** Called when the activity is first created. */
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        if (!this.useSavedLogin()) {
//        	this.getConsumerProvider();
        	
        	this.buttonLogin = (Button)this.findViewById(R.id.loginButton);
        	this.buttonLogin.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					openOAuth();
				}
			});
        } // if
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		
		Log.i(TAG, "onResume");
		if (this.getIntent() != null && this.getIntent().getData() != null) {
			Uri uri = this.getIntent().getData();
			if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
				try {
					this.twitter = ((TwittApplication)this.getApplication()).getTwitter();
					AccessToken accessToken = twitter.getOAuthAccessToken(
							((TwittApplication)this.getApplication()).getRequestToken());
				
					Log.i(TAG, "Get access token: " + accessToken.getToken());

					if (accessToken != null)
					{
						this.storeAccessToken(accessToken);
						this.startFirstActivity();
					}					
				} catch (Exception e) {
					//Log.e(APP, e.getMessage());
					e.printStackTrace();
					Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	private boolean useSavedLogin() {
    	AccessToken token = this.getAccessToken();
    	if (token == null) {
    		return false;
    	}
    	
    	this.twitter = new TwitterFactory().getInstance();
    	this.twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    	this.twitter.setOAuthAccessToken(token);
    	
    	((TwittApplication)this.getApplication()).setTwitter(this.twitter);
    	
    	this.startFirstActivity();
    	this.finish();
    	
    	return true;
    }
    
    private void openOAuth() {
    	this.twitter = new TwitterFactory().getInstance();
    	this.twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    	
    	((TwittApplication)this.getApplication()).setTwitter(this.twitter);
    	
    	try {
			RequestToken requestToken = this.twitter.getOAuthRequestToken(CALLBACK_URL);
			
			((TwittApplication)this.getApplication()).setRequestToken(requestToken);
		
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	private AccessToken getAccessToken() {
		SharedPreferences settings = getSharedPreferences("TWITTER_TOKEN", MODE_PRIVATE);
		String token = settings.getString("accessTokenToken", "");
		String tokenSecret = settings.getString("accessTokenSecret", "");
		
		if (token!=null && tokenSecret!=null && !"".equals(tokenSecret) && !"".equals(token)){
			return new AccessToken(token, tokenSecret);
		}
		return null;
	}
	
	private void storeAccessToken(AccessToken a) {
		SharedPreferences settings = getSharedPreferences("TWITTER_TOKEN", MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("accessTokenToken", a.getToken());
		editor.putString("accessTokenSecret", a.getTokenSecret());
		editor.commit();
	}
	
	private void startFirstActivity() {
		Log.i(TAG, "Starting activity");
		this.startActivity(new Intent(this, TweetsActivity.class));
	}

}