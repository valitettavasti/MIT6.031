/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        Instant start =Instant.MAX;
        Instant end = Instant.MIN;
        for(int i = 0; i<tweets.size(); i++){
            if (Duration.between(tweets.get(i).getTimestamp(),start).toMinutes()>0){
                start = tweets.get(i).getTimestamp();
            }
            if (Duration.between(end,tweets.get(i).getTimestamp()).toMinutes()>0){
                end = tweets.get(i).getTimestamp();
            }
        }
        return new Timespan(start, end);
        //Instant start = tweets.stream().map(Tweet::getTimestamp).min(Instant::compareTo).get();   //AI教的方法
        //Instant end = tweets.stream().map(Tweet::getTimestamp).max(Instant::compareTo).get();
        //return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        for(Tweet tweet:tweets){
            String[] words = tweet.getText().split(" ");
            for(String word:words){
                if(word.charAt(0) == '@'){
                    word = word.substring(1);
                    mentionedUsers.add(Character.toLowerCase(word.charAt(0))+word.substring(1));
                }
            }
        }
        return mentionedUsers;
    }

}
