
package magicsquare;

import java.util.Arrays;

/**
 *
 * @author Ben Nelson
 * Genetic Algorithm process control for Magic Square solver.
 */
public class MSProcess implements Runnable {
    private  int n;
    private  int POPULATIONSIZE;
    private  MagicSquare[] population;
    private  MSTournament t = new MSTournament();
    private  MSCrossover x= new MSCrossover();
    private  MagicSquare bestEver;
    private  MSMutation m = new MSMutation();
    private int maxgens;
    private int asteroids=0;
    
    private int Ctype;
    private int Mtype;
    
    private int ThreadNum = 0;
    
    
    
    
    public MSProcess(double Pc, int Cty, double Pm, int Mty, int nsize, int max, int k, int tID) {
        n=nsize;
        x.setXoverRate(Pc);
        m.setMutationRate(Pm);
        maxgens=max;
        Ctype=Cty;
        Mtype=Mty;
        POPULATIONSIZE=k;
        population=new MagicSquare[POPULATIONSIZE];
        ThreadNum=tID;
        
    }
    
    @Override
    public void run() {
        process();
        
    }
    
    public void process() {
        
        genInitialPopulation();
        Arrays.sort(population);
        bestEver=population[0].deepCopy();
        MagicSquare[] PopCopy;
        
        long Gen = 0;
        
        //for (int X=0; X<1000; X++) {
        while (bestEver.getFitness()!=0) {            
            if (MagicSquareApp.solutionFound) {
                return; // Abort this thread if another thread has found a solution
            }
            
            Gen++;            
            if (Gen==Math.pow(n,maxgens)) {                
                Gen=0;
                asteroids++;
                //System.out.println("");
                //System.out.println("Thread " + ThreadNum + " restarting, best was:");
                
                //bestEver.printMagicSquare();
                genInitialPopulation(); //old population meets the same fate as the dinosaurs.
                bestEver=population[0].deepCopy(); // random new one just to wipe the old one
            }
            
            population=reCalcFitness(population);
            population=t.Tournament(population);
            PopCopy=copyPopulation(population);
            
            //if (Gen<n*n*n) {
                PopCopy=xover(PopCopy);
            //}
            checkForBest(PopCopy, "xover", Gen);
             
            PopCopy=mutate(PopCopy);
            checkForBest(PopCopy, "mutation", Gen);
            population=recombine(population, PopCopy);
            checkForBest(population, "recombine??", Gen);
        }
        bestEver.printMagicSquare();
        
        //System.out.println("");
        //System.out.println("Found in generation: " + Gen);
        //System.out.println("Number of aborted attempts on the way: " + asteroids);
        
    }
    
    private void genInitialPopulation() {
        for (int i=0; i<POPULATIONSIZE; i++) {
            MagicSquare ms = new MagicSquare(n);
            ms.generateRandom();
            population[i]=ms;
            //System.out.println("initial fitness of i: " + population[i].getFitness());
            //ms.printMagicSquare();
        }
    }
    
    private  void checkForBest(MagicSquare[] p, String x, long g) {
        Arrays.sort(p);
        
            if (p[0].getFitness()<bestEver.getFitness()) {
                //System.out.println("best of gen after: " + x + " has fitness: " + p[0].getFitness() + " gen: " + g);
                bestEver=p[0].deepCopy();
                
            }
            
    }
    
    private  MagicSquare[] copyPopulation(MagicSquare[] p) {
        MagicSquare[] copy = new MagicSquare[p.length];
        for (int i=0; i<p.length; i++) {
            copy[i]=p[i].deepCopy();
        }
        return copy;
    }
    
    private  MagicSquare[] recombine(MagicSquare[] p1, MagicSquare[] p2) {
        Arrays.sort(p1);
        Arrays.sort(p2);
        int j=0;
        
        //Experimental
        /*
        for (int i=0; i<p1.length; i++) {
            if (p1[i].getFitness()<p2[i].getFitness()) {
                p1[i]=p2[i].deepCopy();
            } 
        }*/
        
        for (int i=(p1.length-1); i>(0); i--) { //best yet
        //for (int i=(p1.length-1); i>(p1.length/2); i--) {    //when pop isnt 2
            p1[i]=p2[j].deepCopy();
            j++;
        }
        
        
        return p1;
    }
    
    private  MagicSquare[] reCalcFitness(MagicSquare[] p) {
        for (int i=0; i<p.length; i++) {
            p[i].calculateFitness();
        }
        return p;        
    }
    
    private MagicSquare[] xover(MagicSquare[] PopCopy) {
        if (Ctype==1) {
            PopCopy=x.partiallyMatchedCrossover(PopCopy);
        } else if (Ctype==2) {
            PopCopy=x.uobCrossover(PopCopy);
        } else {
            if (Math.random()<0.5) {
                PopCopy=x.partiallyMatchedCrossover(PopCopy);
            } else {
                PopCopy=x.uobCrossover(PopCopy);
            }
        }
        
        return PopCopy;
    }
    
    private MagicSquare[] mutate(MagicSquare[] PopCopy) {
        if (Mtype==1) {
            double r=Math.random();
            if (r<0.25) {
                PopCopy=m.magicSwap(PopCopy);
            } else if (r<0.75) {
                
                PopCopy=m.miniSwap(PopCopy);
            } else {
                PopCopy=m.scrambleMutation(PopCopy);
            }
        } else if (Mtype==2) {
            
            double r=Math.random();
            if (r<0.1) {
                PopCopy=m.magicSwap(PopCopy);
            } else {
                
                PopCopy=m.miniSwap(PopCopy);
            }
            
                
        } else {
            double r=Math.random();
            if (r<0.25) {
                PopCopy=m.swapMutationPerElement(PopCopy);
                
            } else if (r<0.5) {
                PopCopy=m.inversionMutation(PopCopy);
                
            } else if (r<0.75) {
                PopCopy=m.magicSwap(PopCopy);
                
            } else {
                PopCopy=m.scrambleMutation(PopCopy);
                
            }
        } 
        return PopCopy;
    }
}

