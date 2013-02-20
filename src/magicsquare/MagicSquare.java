
package magicsquare;

import java.util.Random;

/**
 *
 * @author Ben Nelson
 * Magic Square object and associated methods to calculate its fitness.
 */
public class MagicSquare implements Comparable {

    
    public int n;
    int ms[][];
    private double fitness;
    private int fitnesstarget;
            
    public MagicSquare(int size) {
        
        n=size;
        ms = new int[n][n];
        fitnesstarget=((1+(n*n))*n)/2;
        //System.out.println(rowfitnesstarget);
        
        
    }
    
    /* generates a new random square of size n*n */
    public void generateRandom() {
        Random rgen = new Random();
        
        //1D array of ints 1 to n*n
        int[] nvalues = new int[(n*n)];
        for (int i=0; i<nvalues.length; i++) {
            nvalues[i]=(i+1);
        }
        
        // Shuffle the 1d array
        for (int i=0; i<nvalues.length; i++) {
                int randomPosition = rgen.nextInt(nvalues.length);
                int temp = nvalues[i];
                nvalues[i] = nvalues[randomPosition];
                nvalues[randomPosition] = temp;
                
         }
        
        // build magic square
        build2dX(nvalues);
        /**
        for (int y=0; y<n; y++) {
            for (int x=0; x<n; x++) {
                ms[y][x]=nvalues[(y*n)+x]; 

            }
        } ***/
        
        // calc initial fitness
        calculateFitness();
        
    
        
    }
    public void printMagicSquare() {
        System.out.println();
        
        int[] sumVector = new int[n+n+2];
        for (int y=0; y<n; y++) {
            int ftotal=0;
            for (int x=0; x<n; x++) {
                ftotal=ftotal+ms[y][x];
            }
            sumVector[y]=ftotal;
            //System.out.println(fitnessVector[y]);
        }
        for (int x=0; x<n; x++) {
            int ftotal=0;
            for (int y=0; y<n; y++) {                
                ftotal=ftotal+ms[y][x];
            }
            sumVector[n+x]=ftotal;
        }
        
        int ftd1=0;
        for (int i=0; i<n; i++) {
            ftd1=ftd1+ms[i][i];            
        }
        sumVector[n+n]=ftd1;
        
        //diag 2
        int ftd2=0;
        int x2=n-1;
        for (int y=0; y<n; y++) {
            
            ftd2=ftd2+ms[y][x2];
            x2--;
                        
        }
        sumVector[n+n+1]=ftd2;
        
        
        for (int y=0; y<n; y++) {
            System.out.println();
            System.out.print("       ");
            for (int x=0; x<n; x++) {
                if (ms[y][x]<10) {
                    System.out.print(ms[y][x] + "    ");
                } else if (ms[y][x]<100) {
                    System.out.print(ms[y][x] + "   ");
                } else if (ms[y][x]<1000) {
                    System.out.print(ms[y][x] + "  ");
                } else if (ms[y][x]<10000) {
                    System.out.print(ms[y][x] + " ");
                }
            }
            System.out.print("      " + sumVector[y]);
        }
        System.out.println("\n");
        System.out.print(sumVector[n+n+1] + "   ");
        for (int k=0; k<n+1; k++) {
            if (sumVector[n+k]<100) { System.out.print(sumVector[n+k] + "   "); }
            else if (sumVector[n+k]<1000) { System.out.print(sumVector[n+k] + "  "); }
            else if (sumVector[n+k]<10000) { System.out.print(sumVector[n+k] + " "); }
            else if (sumVector[n+k]<100000) { System.out.print(sumVector[n+k] + " "); }
            else if (sumVector[n+k]<1000000) { System.out.print(sumVector[n+k] + " "); }
        }
    }
    
    public void calculateFitness() {
        // calculate a mean fitness.
        // Fitness is deviatation from fitnesstarget of mean of each line
        int[] fitnessVector = new int[n+n+2];
        
        //calc fitness of each row
        
        for (int y=0; y<n; y++) {
            int ftotal=0;
            for (int x=0; x<n; x++) {
                ftotal=ftotal+ms[y][x];
            }
            fitnessVector[y]=Math.abs(fitnesstarget-ftotal);
            //System.out.println(fitnessVector[y]);
        }
        
        //calc fitness of each column
        for (int x=0; x<n; x++) {
            int ftotal=0;
            for (int y=0; y<n; y++) {                
                ftotal=ftotal+ms[y][x];
            }
            fitnessVector[n+x]=Math.abs(fitnesstarget-ftotal);
        }
        
        //calc fitness of diagonal 1,1 to n,n
        int ftd1=0;
        for (int i=0; i<n; i++) {
            ftd1=ftd1+ms[i][i];            
        }
        fitnessVector[n+n]=Math.abs(fitnesstarget-ftd1);
        
        //diag 2
        int ftd2=0;
        int x2=n-1;
        for (int y=0; y<n; y++) {
            
            ftd2=ftd2+ms[y][x2];
            x2--;
                        
        }
        fitnessVector[n+n+1]=Math.abs(fitnesstarget-ftd2);
        //System.out.println(" ");
        int total=0;
        for (int i=0; i<fitnessVector.length; i++) {
            total=total+fitnessVector[i];
            //System.out.print(fitnessVector[i]);
        }
        fitness=(double)total/fitnessVector.length;
        //System.out.println(fitness);
        
        
    }
    
    public int[][] getMagicSquare() {
        return ms;
    }
    
    
    public double getFitness() {
        return fitness;
    }
    
    
    public MagicSquare deepCopy() {
        MagicSquare newMS = new MagicSquare(n);
        int[] temp1 = new int[n*n];
        temp1=this.get1DArrayX();
        newMS.build2dX(temp1);
        newMS.calculateFitness();
        return newMS;
    }
    
    // takes a 1d array (length n*n) as input and builds it into a 2d array
    public void build2dX(int[] mag1d) {
        for (int y=0; y<n; y++) {
            for (int x=0; x<n; x++) {
                ms[y][x]=mag1d[(y*n)+x]; 

            }
        }
    }
    
    public void build2dY(int[] mag1d) {
        for (int x=0; x<n; x++) {
            for (int y=0; y<n; y++) {
                ms[y][x]=mag1d[(x*n)+y]; 

            }
        }
    }
    
    public int[] get1DArrayX() {
        int[] mag1d=new int[n*n];
        //System.out.println();
        for (int y=0; y<n; y++) {
          //  System.out.println();
            for (int x=0; x<n; x++) {
                
                mag1d[(y*n)+x]=ms[y][x];
            //    System.out.print(mag1d[(y*n)+x] + " ");
                
            }
        }
        return mag1d;
    }
    
    public int[] get1DArrayY() {
        int[] mag1d=new int[n*n];
        //System.out.println();
        for (int x=0; x<n; x++) {
          //  System.out.println();
            for (int y=0; y<n; y++) {
                
                mag1d[(x*n)+y]=ms[y][x];
            //    System.out.print(mag1d[(y*n)+x] + " ");
                
            }
        }
        return mag1d;
    }
    
    @Override
    public int compareTo(Object o) {
        MagicSquare tmp = (MagicSquare)o;
        if (this.fitness>tmp.fitness) {
            return 1;
        } else if (this.fitness<tmp.fitness) {
            return -1;
        }
        return 0; // If neither of the above conditions are true then two fitness must be identical
    }
    
    public int[] getRow(int y) {
        int[] row=new int[n];
        for (int i=0; i<n; i++) {
            row[i]=ms[y][i];
        }
        
        return row;
    }
    public int[] getCol(int x) {
        int[] col=new int[n];
        for (int i=0; i<n; i++) {
            col[i]=ms[i][x];
        }
        
        return col;
    }
    
    public void buildRow(int y, int[] row) {
        
        for (int i=0; i<n; i++) {
            ms[y][i]=row[i];
        }       
        
    }
    public void buildCol(int x, int[] col) {
        
        for (int i=0; i<n; i++) {
            ms[i][x]=col[i];
        }       
        
    }
    

   
}
