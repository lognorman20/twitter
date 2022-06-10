package com.codepath.apps.restclienttemplate;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONException;
import org.parceler.Parcels;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    // Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweet) {
        this.context = context;
        this.tweets = tweet;
    }

    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get data at the given position
        Tweet tweet = tweets.get(position);
        // Bind the tweet with view holder
        try {
            holder.bind(tweet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    // Define a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        ImageView ivBodyImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimeAgo;
        ImageButton ibFavorite;
        TextView tvFavoriteCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivDetailedProfileImage);
            ivBodyImage = itemView.findViewById(R.id.ivDetailedBodyImage);
            tvBody = itemView.findViewById(R.id.tvDetailedTweetBody);
            tvScreenName = itemView.findViewById(R.id.tvDetailedScreenName);
            tvTimeAgo = itemView.findViewById(R.id.tvDetailedTimeAgo);
            tvFavoriteCount = itemView.findViewById(R.id.tvFavoriteCount);
            ibFavorite = itemView.findViewById(R.id.ibFavorite);
        }

        public void bind(Tweet tweet) throws JSONException {
            // setting the tweet's body text
            tvBody.setText(tweet.body);
            // setting the tweets username
            tvScreenName.setText(tweet.user.screenName);
            // setting user profile picture
            Glide.with(context).load(tweet.user.publicImageUrl).into(ivProfileImage);
            // setting the body image if any
            if (tweet.entity != "cheese"){
                ivBodyImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.entity).into(ivBodyImage);
            } else {
                ivBodyImage.setVisibility(GONE);
            }
            tvTimeAgo.setText(tweet.timeAgo);

            ibFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // if not already favorited, then
                        // tell twitter that the user wants to favorite that tweet
                        // change the star to a filled in star
                        // increment the text inside tvFavoriteCount
                    // else if already favorited
                        // tell twitter to unfavorite the tweet
                        // change the drawable back to off
                        // decrement tvFavoriteCount
                }
            });
        }
    }
}
