
package magicsquare;

import java.util.Random;

/**
 *
 * @author Ben Nelson
 * Mutation of solutions specifically for magic squares.
 */
public class MSMutation {
    public MSMutation() {}
    private double mrate;
    private double xVyrate=0.5; //the rate at a 1d array build based on x axis is favoured over y axis
                                //there is no reason for this not to be 0.5...
    
    // Cycle through each individual with an x percent chance of any two randomly
    // selected elements getting swapped. Never performs more than one swap per
    // individual. 
    
    public MagicSquare[] swapMutationPerIndividual(MagicSquare[] p) {
        
        for (int i=0; i<p.length; i++) {
            boolean buildx;
            if (Math.random()<mrate) {
                int[] TempArray;
                if (Math.random()<xVyrate) {
                    TempArray=p[i].get1DArrayX();
                    buildx=true;
                } else {
                    TempArray=p[i].get1DArrayY();
                    buildx=false;
                }
                
                int Aindex=(int)(Math.random()*TempArray.length);
                int Bindex=(int)(Math.random()*TempArray.length);
                while (Aindex==Bindex) {
                    Bindex=(int)(Math.random()*TempArray.length);
                    
                }
                //System.out.println("A: " + Aindex + " B: " + Bindex);
                
                int temp=TempArray[Aindex];
                TempArray[Aindex]=TempArray[Bindex];
                TempArray[Bindex]=temp;
                if (buildx) {
                    p[i].build2dX(TempArray);
                } else {
                    p[i].build2dY(TempArray);
                }    
                
                p[i].calculateFitness();
            }
            
        }
        return p;
    }
    
    // Cycle through every element of every individual with a x percent chance 
    // of of swapping with another randomly selected bit in the same individual
    public MagicSquare[] swapMutationPerElement(MagicSquare[] p) {
        for (int i=0; i<p.length; i++) {            
            boolean buildx;
            int[] TempArray;
            if (Math.random()<xVyrate) {
                TempArray=p[i].get1DArrayX();
                buildx=true;
            } else {
                TempArray=p[i].get1DArrayY();
                buildx=false;
            }
            for (int j=0; j<TempArray.length; j++) {
                if (Math.random()<mrate) {
                    int Aindex=j;
                    int Bindex=(int)(Math.random()*TempArray.length);
                    while (Aindex==Bindex) {
                        Bindex=(int)(Math.random()*TempArray.length);

                    }
                    //System.out.println("A: " + Aindex + " B: " + Bindex);

                    int temp=TempArray[Aindex];
                    TempArray[Aindex]=TempArray[Bindex];
                    TempArray[Bindex]=temp;
                    if (buildx) {
                        p[i].build2dX(TempArray);
                    } else {
                        p[i].build2dY(TempArray);
                    }    
                        p[i].calculateFitness();
                    }
            }
            
            
        }
        return p;
    }
    
    public MagicSquare[] inversionMutation(MagicSquare[] p) {
        
        for (int i=0; i<p.length; i++) {
            
            if (Math.random()<mrate) {
                boolean buildx;
                int[] TempArray;
                if (Math.random()<xVyrate) {
                    TempArray=p[i].get1DArrayX();
                    buildx=true;
                } else {
                    TempArray=p[i].get1DArrayY();
                    buildx=false;
                }
                int Aindex=(int)(Math.random()*TempArray.length);
                int Bindex=(int)(Math.random()*TempArray.length);
                                
                while (Aindex==Bindex) {
                    Bindex=(int)(Math.random()*TempArray.length);                    
                }
                
                int substringsize=Math.abs(Aindex-Bindex);
                int[] substring=new int[substringsize];
                int[] invertedSubstring=new int[substringsize];
                        
                if (Aindex<Bindex) {
                    int tempIndex=Aindex;
                    for (int j=0;j<substring.length;j++) {
                        substring[j]=TempArray[tempIndex];
                        tempIndex++;
                    }
                    tempIndex=(substring.length-1);
                    for (int j=0; j<invertedSubstring.length;j++) {
                        invertedSubstring[j]=substring[tempIndex];
                        tempIndex--;
                    }
                    
                    tempIndex=Aindex;
                    for (int j=0; j<invertedSubstring.length;j++) {
                        TempArray[tempIndex]=invertedSubstring[j];
                        tempIndex++;
                    }
                    
                } else {
                    int tempIndex=Bindex;
                    for (int j=0;j<substring.length;j++) {
                        substring[j]=TempArray[tempIndex];
                        tempIndex++;
                    }
                    tempIndex=(substring.length-1);
                    for (int j=0; j<invertedSubstring.length;j++) {
                        invertedSubstring[j]=substring[tempIndex];
                        tempIndex--;
                    }
                    
                    tempIndex=Bindex;
                    for (int j=0; j<invertedSubstring.length;j++) {
                        TempArray[tempIndex]=invertedSubstring[j];
                        tempIndex++;
                    }
                    
                }
                
                
                
                if (buildx) {
                    p[i].build2dX(TempArray);
                } else {
                    p[i].build2dY(TempArray);
                }    
                p[i].calculateFitness();
            }
            
        }
        return p;
        
    }
    
    public MagicSquare[] scrambleMutation(MagicSquare[] p) {
        
        for (int i=0; i<p.length; i++) {
            if (Math.random()<mrate) {
                int[] TempArray;
                boolean buildx;
                if (Math.random()<xVyrate) {
                    TempArray=p[i].get1DArrayX();
                    buildx=true;
                } else {
                    TempArray=p[i].get1DArrayY();
                    buildx=false;
                }
                int Aindex=(int)(Math.random()*TempArray.length);
                int Bindex=(int)(Math.random()*TempArray.length);
                                
                while (Aindex==Bindex) {
                    Bindex=(int)(Math.random()*TempArray.length);                    
                }
                
                int substringsize=Math.abs(Aindex-Bindex);
                int[] substring=new int[substringsize];
                
                        
                if (Aindex<Bindex) {
                    int tempIndex=Aindex;
                    for (int j=0;j<substring.length;j++) {
                        substring[j]=TempArray[tempIndex];
                        tempIndex++;
                    }
                    
                    substring=this.shuffle(substring);
                    
                    tempIndex=Aindex;
                    for (int j=0; j<substring.length;j++) {
                        TempArray[tempIndex]=substring[j];
                        tempIndex++;
                    }
                    
                } else {
                    int tempIndex=Bindex;
                    for (int j=0;j<substring.length;j++) {
                        substring[j]=TempArray[tempIndex];
                        tempIndex++;
                    }
                    
                    substring=this.shuffle(substring);
                    
                    tempIndex=Bindex;
                    for (int j=0; j<substring.length;j++) {
                        TempArray[tempIndex]=substring[j];
                        tempIndex++;
                    }
                    
                }
                
                
                
                if (buildx) {
                    p[i].build2dX(TempArray);
                } else {
                    p[i].build2dY(TempArray);
                }   
                p[i].calculateFitness();
            }
            
        }
        return p;
        
    }
    public MagicSquare[] magicSwap(MagicSquare[] p) {
        for (int i=0; i<p.length; i++) {
            if (Math.random()<mrate) {
                if (Math.random()<0.5) {
                    p[i]=rowSwap(p[i]);
                } else {
                    p[i]=colSwap(p[i]);
                }
                p[i].calculateFitness();
            }
        }
        
        return p;
    }
    
    private MagicSquare rowSwap(MagicSquare p) {
        
            
                int Aindex=(int)(Math.random()*(p.n-1));
                int Bindex=(int)(Math.random()*(p.n-1));
                while (Aindex==Bindex) {
                    Bindex=(int)(Math.random()*(p.n-1));                    
                }
                int[] A = p.getRow(Aindex);
                int[] B = p.getRow(Bindex);
                int[] temp = new int[A.length];
                
                for (int l=0; l<A.length; l++) {
                   temp[l]=A[l]; 
                }
                    
                for (int l=0; l<A.length; l++) {
                   A[l]=B[l]; 
                }
                for (int l=0; l<A.length; l++) {
                   B[l]=temp[l]; 
                }
                
                p.buildRow(Aindex, A);
                p.buildRow(Bindex, B);
            
            
        
        
        return p;
    }
    private MagicSquare colSwap(MagicSquare p) {
        
            
                int Aindex=(int)(Math.random()*(p.n-1));
                int Bindex=(int)(Math.random()*(p.n-1));
                while (Aindex==Bindex) {
                    Bindex=(int)(Math.random()*(p.n-1));                    
                }
                               
                
                int[] A = p.getCol(Aindex);
                int[] B = p.getCol(Bindex);
                int[] temp = new int[A.length];
                
               
                
                for (int l=0; l<A.length; l++) {
                    temp[l]=A[l];                    
                }
                    
                for (int l=0; l<A.length; l++) {
                   A[l]=B[l]; 
                }
                for (int l=0; l<A.length; l++) {
                   B[l]=temp[l]; 
                }
                
                p.buildCol(Aindex, A);
                p.buildCol(Bindex, B);
            
            
        
        
        return p;
    }
    
    
    
    private int[] shuffle(int[] substring) {
       Random rgen = new Random();
       for (int i=0; i<substring.length; i++) {
           int randomPosition = rgen.nextInt(substring.length);
           int temp = substring[i];
           substring[i] = substring[randomPosition];
           substring[randomPosition] = temp;
                
        }
        return substring;
        
    }
    
    public MagicSquare[] miniSwap(MagicSquare[] p) {
        // Like swap mutation but just swaps similar values
        for (int i=0; i<p.length; i++) {
            boolean buildx;
            if (Math.random()<mrate) {
                
                
                
                int[] TempArray;
                if (Math.random()<xVyrate) {
                    TempArray=p[i].get1DArrayX();
                    buildx=true;
                } else {
                    TempArray=p[i].get1DArrayY();
                    buildx=false;
                }
                
                //int mutnum=0;
                //Random r = new Random();
                //while (mutnum!=0) {
                //    mutnum = Math.abs((int)Math.round(r.nextGaussian() * 1));
                //}
                
                //for(int m=0; m<mutnum; m++) {
                
                    int Aindex=(int)(Math.random()*TempArray.length);
                    int Bindex=0;



                    Random rn = new Random();
                    int B=0;
                    while (B<1 || B>(TempArray.length) || B==TempArray[Aindex]) {
                        B = TempArray[Aindex] + (int)Math.round(rn.nextGaussian() * 2);
                    }
                    

                    //System.out.println("A: " + TempArray[Aindex] + " B: " + B);

                    for (int k=0; k<TempArray.length; k++) {
                        if (TempArray[k]==B) {
                            Bindex=k;
                        }

                    }
                    //System.out.println("A: " + Aindex + " B: " + Bindex);

                    int temp=TempArray[Aindex];
                    TempArray[Aindex]=TempArray[Bindex];
                    TempArray[Bindex]=temp;
                //}
                if (buildx) {
                    p[i].build2dX(TempArray);
                } else {
                    p[i].build2dY(TempArray);
                }    
                
                p[i].calculateFitness();
            }
            
        }
        return p;
        
    }
    
    public void setMutationRate(double Pm) {
        mrate=Pm;
    }
    
}
