import static java.lang.System.exit;

/**
 * Created by Michael Walsh on 5/18/2017.
 */



public class Main {

    public static void main(String[] args) {
        //args will contain the account to display from and the number of tweets
        liveUpdate manager = new liveUpdate();
        if(args.length < 2)
        {
            System.out.print("ERROR: Not enough command line arguments found.\n");
            exit(-1);
        }

        manager.declareAndRetrieve(args[0], Integer.parseInt(args[1]));


    }
}
