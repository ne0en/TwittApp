package si.fis.android.twittapp;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;
import android.app.Application;


public class TwittApplication extends Application {
	private Twitter twitter;
	private RequestToken requestToken;

	public Twitter getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	public RequestToken getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(RequestToken requestToken) {
		this.requestToken = requestToken;
	}
}
