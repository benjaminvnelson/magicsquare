
package magicsquare;

import java.util.Random;

/**
 *
 * @author Ben Nelson
 * Binary tournament selection for Magic Squares.
 */
public class MSTournament {
    public MSTournament() { }
    
    public MagicSquare[] Tournament(MagicSquare[] preTournament) {
        
        MagicSquare[] postTournament = new MagicSquare[preTournament.length];
        
        // Shuffle the preTournament population
        preTournament=shuffle(preTournament);
     
        for (int i=0; i<(preTournament.length); i++) {
            MagicSquare A = preTournament[i];
            MagicSquare B = preTournament[(int)(Math.random()*preTournament.length)];
             if (A.getFitness() < B.getFitness()) {
                postTournament[i]=A.deepCopy(); // fitness closest to 0 wins
            } else {
                postTournament[i]=B.deepCopy();
            }        
        }   

        /***
        for (int i=0; i<preTournament.length; i++) {
            System.out.println("pre: " + preTournament[i].getFitness() + " post: " + postTournament[i].getFitness());
            
        } ****/
        
        
        return postTournament;
    }
    
    private MagicSquare[] shuffle(MagicSquare[] population) {
       Random rgen = new Random();
       for (int i=0; i<population.length; i++) {
           int randomPosition = rgen.nextInt(population.length);
           MagicSquare temp = population[i];
           population[i] = population[randomPosition];
           population[randomPosition] = temp;
                
        }
        return population;
        
    }
    
    
}
