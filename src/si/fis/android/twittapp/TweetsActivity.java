package si.fis.android.twittapp;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class TweetsActivity extends ListActivity implements OnClickListener {
	Button buttonPost;
	ListView listV;
	Twitter twitter;
	ResponseList<Status> statuses;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.tweets);
        
        twitter = ((TwittApplication) getApplication()).getTwitter();
        
        buttonPost = (Button) findViewById(R.id.buttonPost);
		buttonPost.setOnClickListener(this);
		
		
		listV = (ListView) findViewById(android.R.id.list);
		
		listV.setAdapter(new TweetAdapter(this, R.layout.tweet_row, getStatuses()));
    }
    
    public ResponseList<Status> getStatuses() {
		try {
			statuses = twitter.getHomeTimeline();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return statuses;
    }
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonPost) {
			startActivity(new Intent(this, PostActivity.class));
		}
		
	}
}
