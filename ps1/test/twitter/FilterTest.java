/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-18T11:00:00Z");
    private static final Instant d4 = Instant.parse("2017-02-17T11:00:00Z");
    private static final Instant d5 = Instant.parse("2016-06-18T15:00:00Z");

    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "alyssa", "11111 @abc", d3);
    private static final Tweet tweet4 = new Tweet(4, "abcdsde", "11111 @Abcde Talk", d4);
    private static final Tweet tweet5 = new Tweet(5, "Alyssa", "It is a message. TALK", d5);


    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");

        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));

        List<Tweet> writtenBy1 = Filter.writtenBy(Arrays.asList( tweet1,tweet2, tweet3,tweet4), "alyssa");
        assertEquals("expected singleton list", 2, writtenBy1.size());
        assertTrue("expected list to contain tweet",
                writtenBy1.contains(tweet1)&&writtenBy1.contains(tweet3));

        List<Tweet> writtenBy2 = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3, tweet5), "alyssa");
        assertEquals("expected singleton list", 3, writtenBy2.size());
        assertTrue("expected list to contain tweet",
                writtenBy2.contains(tweet1)&&writtenBy2.contains(tweet3)&&writtenBy2.contains(tweet5));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant test1 = Instant.parse("2016-02-16T09:00:00Z");
        Instant test2 = Instant.parse("2016-02-18T12:00:00Z");
        Instant test3 = Instant.parse("2016-10-18T12:00:00Z");

        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5);
        
        List<Tweet> inTimespan = Filter.inTimespan(tweets, new Timespan(test1, test2));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));

        List<Tweet> inTimespan1 = Filter.inTimespan(tweets, new Timespan(test1, test3));
        assertTrue("expected list to contain tweets",
                inTimespan1.containsAll(Arrays.asList(tweet1, tweet2,tweet3, tweet5)));
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));

        List<Tweet> containing1 = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5),
                Arrays.asList("talk"));
        assertTrue("expected list to contain tweets",
                containing1.containsAll(Arrays.asList(tweet1, tweet2, tweet4, tweet5)));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
