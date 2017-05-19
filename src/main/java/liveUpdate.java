/**
 * Created by Michael Walsh on 5/18/2017.
 */

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class liveUpdate {



    public void declareAndRetrieve(String handle, int numTweets)
    {


        Properties apiInfo = new Properties();
        InputStream input = null;

        try{
            //obtain authentication info from config.properties
            apiInfo.load(getClass().getClassLoader().getResourceAsStream("config.properties"));



            //initialize config
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(false)
                    .setOAuthConsumerKey(apiInfo.getProperty("consumerKey"))
                    .setOAuthConsumerSecret(apiInfo.getProperty("consumerSecret"))
                    .setOAuthAccessToken(apiInfo.getProperty("token"))
                    .setOAuthAccessTokenSecret(apiInfo.getProperty("secret"));


            TwitterFactory tf = new TwitterFactory(cb.build());

            Twitter twitter = tf.getInstance();


            List statuses = new ArrayList();

            System.out.println();
            try {
                //load first 200 pages since that is the upper limit twitter allows any one request
                Paging page = new Paging(1, 200);
                //add statues to a list
                statuses.addAll(twitter.getUserTimeline(handle, page));
                for(int i = 0 ; i < numTweets; ++i)
                {
                    //ensures the user does not ask for more than 200 pages can provide
                    if(i>statuses.size()-1)
                        break;

                    //parse the json file for the tweet only, the other garbage is unnecessary, print
                    JSONObject obj = new JSONObject(statuses.get(i));
                    try {
                        System.out.println("Tweet #" + (i+1));
                        System.out.println(obj.get("text").toString());
                        System.out.println();
                    }
                    catch(JSONException err)
                    {
                        err.printStackTrace();
                    }
                }

            }
            catch(TwitterException e) {
                e.printStackTrace();
            }

        }catch (IOException err)
        {
            err.printStackTrace();
        }



    }

}
