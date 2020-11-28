import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class OperatingSystem{
    //Cache that holds the time to ping the destination
    //first index is the address; second index is the ping value
    static int[][] Cache = new int[10][2];
    static Semaphore mutex = new Semaphore(1, true);

    public static class DecisionThread extends Thread {
        String id = "";
        DecisionThread(String id){
            this.id = id;
        }
        public void run(){
            try{
                mutex.acquire();
                System.out.println(id + " is trying to acquire the mutex");
            }
            catch(Exception e){
                System.out.println(e);
            }
            finally{
                mutex.release();
            }
        }
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
                //Generate a ping
                int randomPing = random.nextInt( 10 - 1 + 1 ) + 1;
                Cache[randomAddress][1] = randomPing; 

            }
        }
    }
    public static void main(String[] args){
        //int[][] Cache = new int[10][2];
        DecisionThread thread1 = new DecisionThread("1");
        DecisionThread thread2 = new DecisionThread("2");
        DecisionThread thread3 = new DecisionThread("3");
        DecisionThread thread4 = new DecisionThread("4");
        DecisionThread thread5 = new DecisionThread("5");
        DecisionThread thread6 = new DecisionThread("6");
        DecisionThread thread7 = new DecisionThread("7");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();

        /*
        int destinationId = 9;
        Cache[0][0] = 9; //Address
        Cache[0][1] = 3; //Ping value
        System.out.println(Arrays.deepToString(Cache));
        for( int i = 0; i < 10; i++){
            System.out.println("Cache["+i+"][0]:"+ Cache[i][0]);
            if(Cache[i][0] == destinationId){

            }
        }
        */
    }
}