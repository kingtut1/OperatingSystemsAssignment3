import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class OperatingSystem {
    // Cache that holds address and the time to ping the destination
    // first index is the address; second index is the ping value
    static int[][] Cache = new int[10][2];

    static int lastIndex = 0;
    static int firstIndex = 0;
    static Semaphore mutex = new Semaphore(1, true);
    static Random random = new Random();

    public static class DecisionThread extends Thread {
        String id = "";

        DecisionThread(String id) {
            this.id = id;
        }

        public void run() {
            while (true) {

                try {
                    // Choose random query between 1 and 100
                    int randomAddress = random.nextInt(100 - 1 + 1) + 1;
                    
                    //Acquire the mutex in order to check if address in cache
                    mutex.acquire();
                    int ping = GetDestinationPing(randomAddress);
                    if(ping == -1){
                        //Case 1: Release the mutex and put into cache
                        mutex.release();
                        //Ping the sytem and thread sleeps what ever time is randomly chosen(milliseconds)
                        ping = random.nextInt(10 - 1 + 1) + 1;
                        Thread.sleep(ping);

                        mutex.acquire();
                        System.out.print("Thread " + id + " ");
                        putInCache(randomAddress, ping);
                        mutex.release();
                    }
                    else{
                        //Case 2: Address in cache thus display ping and release mutex
                        System.out.println("ID: " + id + " the cache's ping at " + randomAddress + " is " + ping);
                        mutex.release();
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    // Check if destination exists in current cache else return false
    private static int GetDestinationPing(int destinationAddress) {
        for (int i = 0; i < 10; i++) {
            // Checking the cache for the address
            if (Cache[i][0] == destinationAddress) {
                //Returns the destination's ping value
                return Cache[i][1];
            }
        }
        return -1;
    }

    private static void putInCache(int destinationAddress, int destinationPing){
            //Checking to see if address is in the cache
            int ping = GetDestinationPing(destinationAddress);
            if(ping == -1){
                //Hasn't been added in time since last call
                
                if (lastIndex <= 9) {
                    Cache[lastIndex][0] = destinationAddress;
                    Cache[lastIndex][1] = destinationPing;
                    lastIndex++;
                }
                else{
                    //Removing the front of the queue
                    if(firstIndex > 9){
                        firstIndex = 0;
                    }
                    Cache[firstIndex][0] = destinationAddress;
                    Cache[firstIndex][1] = destinationPing;
                    firstIndex++;
                }

                System.out.println("has had nothing added since last time so succesfully added");
            }
            else{
                //Has been added to the cahce in the time since last call
                for (int i = 0; i < 10; i++) {
                    // Checking the cache for the address
                    if (Cache[i][0] == destinationAddress) {
                        Cache[i][1] = destinationPing; 
                    }
                }
                System.out.println("has had something added thus updating");
            }
            System.out.println("Cache after operations");
            System.out.println(Arrays.deepToString(Cache));

    }



    public static void main(String[] args) {
        DecisionThread thread1 = new DecisionThread("1");
        DecisionThread thread2 = new DecisionThread("2");
        DecisionThread thread3 = new DecisionThread("3");
        DecisionThread thread4 = new DecisionThread("4");
        DecisionThread thread5 = new DecisionThread("5");
        DecisionThread thread6 = new DecisionThread("6");
        DecisionThread thread7 = new DecisionThread("7");
        DecisionThread thread8 = new DecisionThread("8");
        DecisionThread thread9 = new DecisionThread("9");
        DecisionThread thread10 = new DecisionThread("10");
        
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();
    }
}