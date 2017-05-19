/**
 * Created by Michael Walsh on 5/18/2017.
 */


import org.apache.log4j.BasicConfigurator;
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
            apiInfo.load(getClass().getClassLoader().getResourceAsStream("config.properties"));



            // Follow the given handle
            BasicConfigurator.configure();

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

                Paging page = new Paging(1, 200);
                statuses.addAll(twitter.getUserTimeline(handle, page));
                for(int i = 0 ; i < numTweets; ++i)
                {
                    if(i>statuses.size()-1)
                        break;
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
