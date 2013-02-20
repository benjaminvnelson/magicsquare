
package magicsquare;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ben Nelson
 * This application generates magic squares of size n*n
 * where n is specified as an argument (or otherwise defaults to 10)
 * When it has finished n, it automatically moves onto n++
 * 
 * configuration parameters for genetic algorithm can be adjusted below
 */
public class MagicSquareApp {
    
    public static boolean solutionFound=false;    
      
    
    public static void main(String[] args) {  
        long startTime = System.currentTimeMillis();                      
        
        /***  - MS Process input parameters *****
         * MSProcess(double Pc, int Ctype, double Pm, int Mtype, int nsize, int max, int k, tID)
         * 
         * Pc: probability of crossover - 
         * Ctype - Type of crossover:
         *       - 1 - PMX
         *       - 2 - Uniform Order Based
         *       - 3 - Both of the above with 50% probability each pass
         * Pm: probability of mutation
         * Mtype- Type of mutation:
         *      - 1 - 25% chance of magicSwap, 50% chance miniswap, 25% scramble
         *      - 2 - 10% Magic swap, 90% miniswap
         * nsize - the value of n (is read from command line)
         * max - number of generations n^max before giving up and restarting.
         * k - Population Size     
         * tID - Thread ID - Not important, just for testing outputs
         *       
         * ******************************************************/
        
        //Thread t1=new Thread(new MSProcess(0,1,1,2,n,4,2,1));        
        //Thread t2=new Thread(new MSProcess(0,1,1,2,n,4,2,2));
        
        /******* Set parameters here *****/
        
        double Pc = 0;
        int Ctype=1;
        double Pm=1;
        int Mtype=2;        
        int n=nValue(args); //Will default to 10 if no args are detected
        int max=4; //max generations before population is destroyed
        int k=2; 
        // % of previous generation retained is set in MSProcess recombine method
        //at present it just keeps the one best individual from the previous generation
        
                
        /*********************************/
        
        
        int cores=Runtime.getRuntime().availableProcessors();
        
        System.out.println("Processor cores available: " + cores);
        
        
        
        
        
        while (n<=100) {
            ArrayList<Thread> threads=new ArrayList<Thread>();
            boolean threadsRunning=true;  
        
            for (int i=0; i<cores; i++) {

                Thread worker=new Thread(new MSProcess(Pc,Ctype,Pm,Mtype,n,max,k,1)); 
                worker.setDaemon(true);
                worker.start();
                threads.add(worker);
            }
        
             
            System.out.println("\n----Generating magic square of size " + n + "*" + n +"---");


            while (threadsRunning) {

                threadsRunning=false;        
                for (int i=0; i<threads.size(); i++) {

                    if (threads.get(i).isAlive()) {
                        threadsRunning=true;
                    }

                }

                while (!solutionFound) {                    

                    for (int i=0; i<threads.size(); i++) {

                        if (!threads.get(i).isAlive()) {
                            solutionFound=true;
                            break;
                        }

                    }
                }
            }
        
            n++;
            
            solutionFound=false;
            long currentTime = System.currentTimeMillis();
            long elapsedTime = (currentTime-startTime);

            System.out.println(String.format("\nElapsed Time: %d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(elapsedTime), TimeUnit.MILLISECONDS.toSeconds(elapsedTime)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime))));
        
        }
        
        
        long endTime = System.currentTimeMillis();
        long exeTime = (endTime-startTime);
        
        System.out.println(String.format("\nExecution time: %d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(exeTime), TimeUnit.MILLISECONDS.toSeconds(exeTime)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(exeTime))));
        
        
        System.exit(0);
        

        
    }
    private static int nValue(String[] args) {
        int n=10;
        
        if (args.length > 0) {
            try {
                n = Integer.parseInt(args[0]);
                System.out.println("Generating magic square of size " + n + " by " + n);
            } catch (NumberFormatException e) {
                System.err.println("No valid input arg found, using default 'n' of: 10");

            }
        }
        if (n<3) {
            System.out.println(n + "?? How would that work?");
            System.out.println("I'm going to assume you meant 10 instead.");
            n=10;
        }
        return n;
        
    }
    
}


    

    
  

       
