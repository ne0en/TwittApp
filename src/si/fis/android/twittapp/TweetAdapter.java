package si.fis.android.twittapp;

import java.util.List;

import twitter4j.Status;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Status> {
	Context context;
	int layoutResourceId;
	List<Status> tweets;

	public TweetAdapter(Context context, int textViewResourceId,
			List<Status> objects) {
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.layoutResourceId = textViewResourceId;
		this.tweets = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		TextView textTweetStatus = null;
		TextView textTweetUser = null;
		TextView textTweetDate = null;
		
		if (row == null)
		{
			LayoutInflater inflater = ((Activity)this.context).getLayoutInflater();
			row = inflater.inflate(this.layoutResourceId, parent, false);
		}
		
		textTweetStatus = (TextView)row.findViewById(R.id.textTweetStatus);
		textTweetUser = (TextView)row.findViewById(R.id.textTweetUser);
		textTweetDate = (TextView)row.findViewById(R.id.textTweetDate);
		
		Status tweetStatus = this.tweets.get(position);
		
		textTweetStatus.setText(tweetStatus.getText());
		textTweetUser.setText(tweetStatus.getUser().getName());
		
		CharSequence timeText = DateUtils.getRelativeTimeSpanString(
				this.context, tweetStatus.getCreatedAt().getTime());
		textTweetDate.setText(timeText);
		
		return row;
	}
	
	
}