import java.util.Arrays;
import java.util.Random;

public class OperatingSystem{
    //Cache that holds the time to ping the destination
    //first index is the address; second index is the ping value
    static int[][] Cache = new int[10][2];

    public static class DecisionThread extends Thread {
        
    }
    private void BestRoutes(int destinationAddress){
        Random random = new Random();
        for( int i = 0; i < 10; i++){
            System.out.println("Cache[i][0]:"+ Cache[i][0]);
            //Checking the cache for the address
            if(Cache[i][0] == destinationAddress){
                //Generate a ping
                int randomPing = random.nextInt( 10 - 1 + 1 ) + 1;
                Cache[i][1] = randomPing;
            }
            else{
                //Didn't find the address in the cache creating that address
                int randomAddress = random.nextInt( 100 - 1 + 1 ) + 1;


            }
        }
    }
    public static void main(String[] args){
        //int[][] Cache = new int[10][2];
        
        int destinationId = 9;
        Cache[0][0] = 9; //Address
        Cache[0][1] = 3; //Ping value
        System.out.println(Arrays.deepToString(Cache));
        /*
        for( int i = 0; i < 10; i++){
            System.out.println("Cache["+i+"][0]:"+ Cache[i][0]);
            if(Cache[i][0] == destinationId){

            }
        }
        */
    }
}