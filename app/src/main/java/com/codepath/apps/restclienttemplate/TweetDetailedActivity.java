package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

// same as ComposeActivity.java, modularize?
public class TweetDetailedActivity extends AppCompatActivity {

    EditText etDetailedReply;
    Button btnReply;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detailed);

        etDetailedReply = findViewById(R.id.etCompose);
        btnReply = findViewById(R.id.btnTweet);
        client = TwitterApp.getRestClient(this);

        // Set click listener on button
        btnReply.setOnClickListener(new View.OnClickListener() {
            private static final int MAX_TWEET_LENGTH = 280;

            @Override
            public void onClick(View view) {
                String tweetContent = etDetailedReply.getText().toString();
                if (tweetContent.isEmpty()) {
                    Toast.makeText(TweetDetailedActivity.this,
                            "Sorry your tweet cannot be empty.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tweetContent.length() > MAX_TWEET_LENGTH) {
                    Toast.makeText(TweetDetailedActivity.this, "Sorry your tweet is too long.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(TweetDetailedActivity.this, tweetContent, Toast.LENGTH_LONG).show();
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    private static final String TAG = "DetailedActivity";

                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess to publish the tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published tweet, it says: " + tweet.body);
                            Intent intent = new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            // set result code and bundle data for response
                            setResult(RESULT_OK, intent);
                            // closes the activity, pass data to parent
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet that says" + tweetContent, throwable);
                    }
                });
            }
        });
    }
}