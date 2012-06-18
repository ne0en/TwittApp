package si.fis.android.twittapp;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class TweetsActivity extends ListActivity implements OnClickListener {
	Button buttonPost;
	ListView listV;
	Twitter twitter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweets);

		buttonPost = (Button) findViewById(R.id.buttonPost);
		buttonPost.setOnClickListener(this);
		new TwitterGetHomeline().execute();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonPost) {
			startActivity(new Intent(this, PostActivity.class));
		}

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Status status = (Status) listV.getAdapter().getItem(position);
		
		
		
		Bundle bundle = new Bundle();
		bundle.putString("param1", status.getText());
		bundle.putString("param2", status.getUser().getScreenName());
		bundle.putLong("param3", status.getId());

		Intent newIntent = new Intent(this.getApplicationContext(), RetweetActivity.class);
		newIntent.putExtras(bundle);
		startActivityForResult(newIntent, 0);

	}

	class TwitterGetHomeline extends
			AsyncTask<Void, Void, ResponseList<twitter4j.Status>> {
		ResponseList<twitter4j.Status> statuses;

		@Override
		protected ResponseList<twitter4j.Status> doInBackground(Void... params) {

			twitter = ((TwittApplication) getApplication()).getTwitter();
			try {
				statuses = twitter.getHomeTimeline();
				return statuses;

			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(ResponseList<twitter4j.Status> result) {
			// TODO Auto-generated method stub 
			super.onPostExecute(result);

			if (result != null) {
				listV = (ListView) findViewById(android.R.id.list);
				listV.setAdapter(new TweetAdapter(TweetsActivity.this,
						R.layout.tweet_row, statuses));

			}
		}
	}

}
