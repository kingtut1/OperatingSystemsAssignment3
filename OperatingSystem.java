import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class OperatingSystem {
    // Cache that holds address and the time to ping the destination
    // first index is the address; second index is the ping value
    static int[][] Cache = new int[10][2];
    static int lastIndex = 0;
    static int firstIndex = 0;
    // Queue< Pair<Integer, Integer> > Cache = new LinkedList<Integer>();
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
                    //mutex.acquire();
                    //System.out.println(id + " has acquired the mutex");

                    // Choose random query between 1 and 100
                    int randomAddress = random.nextInt(100 - 1 + 1) + 1;
                    //System.out.println("Generated Address:" + randomAddress);
                    mutex.acquire();
                    int ping = GetDestinationPing(randomAddress);
                    if(ping == -1){
                        //Release the mutex and put into cache
                        mutex.release();
                        //Ping the sytem
                        ping = random.nextInt(10 - 1 + 1) + 1;
                        //Thread sleep
                        mutex.acquire();
                        System.out.print("Thread " + id + " ");
                        putInCache(randomAddress, ping);
                        mutex.release();
                    }
                    else{
                        System.out.println("ID: " + id + " the cache's ping at " + randomAddress + " is " + ping);
                        mutex.release();
                    }

                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    //System.out.println("Cache after operations");
                    //System.out.println(Arrays.deepToString(Cache));
                    //System.out.println(id + " is releasing the mutex");
                    //mutex.release();
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
        try {
            PrintStream o = new PrintStream(new File("Text.txt"));
            System.setOut(o);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Unable to set Output to file instead of console");
        }
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
    }
}