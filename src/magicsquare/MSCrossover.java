
package magicsquare;

import java.util.ArrayList;

/**
 *
 * @author Ben Nelson
 * Implementations of Uniform Order Based and partially matched crossover
 * specifically for magic squares
 */
public class MSCrossover {
    
    private double xoverrate; // the xover rate. will be overwritten by msprocess
    private double xVyrate=0.5; //the rate at a 1d array build based on x axis is favoured over y axis
                                //there is no reason for this not to be 0.5...
    
    public MSCrossover() {}
    
    
    // implements uniform order based crossover
    public MagicSquare[] uobCrossover(MagicSquare[] preXover) {
        
        MagicSquare[] postXover = new MagicSquare[preXover.length];
        
        
        
        for (int i=0; i<preXover.length; i++) {
            if (Math.random()<xoverrate) {
                ArrayList<Integer> iToChange = new ArrayList<Integer>();
                MagicSquare A=preXover[i];
                int Bindex=(int)Math.random()*preXover.length;
                MagicSquare B=preXover[Bindex];
                if (A!=B) {
                
                //De-construct into 1D arrays.
                //choose indexes that will change
                //Cycle through these indexes and make a swap
                    int[] a1d;
                    int[] b1d;
                    int[] a1dCopy;
                    int[] b1dCopy;
                    boolean buildx;
                    if (Math.random()<xVyrate) {
                        a1d=A.get1DArrayX();
                        b1d=B.get1DArrayX();
                        a1dCopy=A.get1DArrayX();
                        b1dCopy=B.get1DArrayX();
                        buildx=true;
                    } else {
                        a1d=A.get1DArrayY();
                        b1d=B.get1DArrayY();
                        a1dCopy=A.get1DArrayY();
                        b1dCopy=B.get1DArrayY();
                        buildx=false;
                        
                    }
                    
                    //choose which elements will change and which stay the same
                    for (int j=0; j<a1d.length; j++) {
                        if (Math.random()>0.5) {
                            Integer index=j;
                            iToChange.add(index);
                        }
                    }
                    int s = iToChange.size();
                    int[] aOut = new int[s];
                    int[] bOut = new int[s];
                    int[] aIn = new int[s];
                    int[] bIn = new int[s];
                    
                    for (int j=0; j<aIn.length; j++) {
                        aIn[j]=0;
                        bIn[j]=0;
                    } 
                    
                    for (int j=0; j<iToChange.size(); j++) {
                        int index=iToChange.get(j);
                        int Avalue=a1d[index];
                        int Bvalue=b1d[index];
                        aOut[j]=Avalue;
                        bOut[j]=Bvalue;
                        a1d[index]=0;
                        b1d[index]=0;
                                             
                    }
                    
                    /*
                    System.out.println("Printing aOUT:");
                    for (int j=0; j<aOut.length; j++) {
                        System.out.print(aOut[j]);
                    }
                    
                    */
                    
                    
                    
                    //populate aIn based on positions of aOut in b1dCopy
                    
                    for (int j=0; j<b1dCopy.length; j++) {
                        int target=b1dCopy[j];
                        for (int k=0; k<aOut.length; k++) {
                            if (aOut[k]==target) {
                                
                                for (int l=0; l<aIn.length; l++) {
                                    
                                        if (aIn[l]==0) {
                                            aIn[l]=target;
                                            break;
                                        }
                                    
                                    
                                }
                            }
                        }
                    }
                    
                    /*
                    System.out.println("Printing aIN:");
                    for (int j=0; j<aIn.length; j++) {
                        System.out.print(aIn[j]);
                    }
                    
                    end test ***/
                    
                    
                    
                    for (int j=0; j<a1dCopy.length; j++) {
                        int target=a1dCopy[j];
                        for (int k=0; k<bOut.length; k++) {
                            if (bOut[k]==target) {
                                
                                for (int l=0; l<bIn.length; l++) {
                                    
                                        if (bIn[l]==0) {
                                            bIn[l]=target;
                                            break;
                                        }
                                    
                                    
                                }
                            }
                        }
                    }       
                    /***************
                    System.out.println();
                    System.out.println("a1d pre xover: ");
                    for(int j=0; j<a1dCopy.length; j++) {
                        System.out.print(a1dCopy[j]);
                        
                    }
                    System.out.println();
                    System.out.println("b1d pre xover: ");
                    for(int j=0; j<b1dCopy.length; j++) {
                        System.out.print(b1dCopy[j]);
                        
                    } *****************/
                    
                    for (int j=0; j<aIn.length; j++) {
                        for (int k=0; k<a1d.length; k++) {
                            if (a1d[k]==0) {
                                a1d[k]=aIn[j];
                                break;
                            }
                        }
                    }
                    for (int j=0; j<bIn.length; j++) {
                        for (int k=0; k<b1d.length; k++) {
                            if (b1d[k]==0) {
                                b1d[k]=bIn[j];
                                break;
                            }
                        }
                    }
                    
                /*************
                    System.out.println();
                    System.out.println("a1d post xover: ");
                    for(int j=0; j<a1d.length; j++) {
                        System.out.print(a1d[j]);
                        
                    }
                    System.out.println();
                    System.out.println("b1d post xover: ");
                    for(int j=0; j<b1d.length; j++) {
                        System.out.print(b1d[j]);
                        
                }     ******/
                if (buildx) {    
                    A.build2dX(a1d);                
                    B.build2dX(b1d);
                } else {
                    A.build2dY(a1d);                
                    B.build2dY(b1d);
                }
                A.calculateFitness();
                B.calculateFitness();
                postXover[i]=A;
                postXover[Bindex]=B;
                
                } else {
                postXover[i]=preXover[i].deepCopy();
            }
            
                
            } else {
                postXover[i]=preXover[i].deepCopy();
            }
            //System.out.println("fitness of i after xover: " + postXover[i].getFitness());
        }

            return postXover;
        }
        
    
    // same as UOB Crossover except there is no separation between old and new
    // solutions, so one individual could be crossed-over more than once
    public MagicSquare[] uobCrossoverMix(MagicSquare[] population) {
        
        //MagicSquare[] postXover = new MagicSquare[preXover.length];
        
        
        
        for (int i=0; i<population.length; i++) {
            if (Math.random()<xoverrate) {
                ArrayList<Integer> iToChange = new ArrayList<Integer>();
                MagicSquare A=population[i];
                int Bindex=(int)Math.random()*population.length;
                MagicSquare B=population[Bindex];
                if (A!=B) {
                
                //De-construct into 1D arrays.
                //choose indexes that will change
                //Cycle through these indexes and make a swap
                
                int[] a1d;
                int[] b1d;
                int[] a1dCopy;
                int[] b1dCopy;
                boolean buildx;
                    if (Math.random()<xVyrate) {
                        a1d=A.get1DArrayX();
                        b1d=B.get1DArrayX();
                        a1dCopy=A.get1DArrayX();
                        b1dCopy=B.get1DArrayX();
                        buildx=true;
                    } else {
                        a1d=A.get1DArrayY();
                        b1d=B.get1DArrayY();
                        a1dCopy=A.get1DArrayY();
                        b1dCopy=B.get1DArrayY();
                        buildx=false;
                        
                    }
                    
                    //choose which elements will change and which stay the same
                    for (int j=0; j<a1d.length; j++) {
                        if (Math.random()>0.5) {
                            Integer index=j;
                            iToChange.add(index);
                        }
                    }
                    int s = iToChange.size();
                    int[] aOut = new int[s];
                    int[] bOut = new int[s];
                    int[] aIn = new int[s];
                    int[] bIn = new int[s];
                    
                    for (int j=0; j<aIn.length; j++) {
                        aIn[j]=0;
                        bIn[j]=0;
                    } 
                    
                    for (int j=0; j<iToChange.size(); j++) {
                        int index=iToChange.get(j);
                        int Avalue=a1d[index];
                        int Bvalue=b1d[index];
                        aOut[j]=Avalue;
                        bOut[j]=Bvalue;
                        a1d[index]=0;
                        b1d[index]=0;
                                             
                    }
                    
                    /*
                    System.out.println("Printing aOUT:");
                    for (int j=0; j<aOut.length; j++) {
                        System.out.print(aOut[j]);
                    }
                    
                    */
                    
                    
                    
                    //populate aIn based on positions of aOut in b1dCopy
                    
                    for (int j=0; j<b1dCopy.length; j++) {
                        int target=b1dCopy[j];
                        for (int k=0; k<aOut.length; k++) {
                            if (aOut[k]==target) {
                                
                                for (int l=0; l<aIn.length; l++) {
                                    
                                        if (aIn[l]==0) {
                                            aIn[l]=target;
                                            break;
                                        }
                                    
                                    
                                }
                            }
                        }
                    }
                    
                    /*
                    System.out.println("Printing aIN:");
                    for (int j=0; j<aIn.length; j++) {
                        System.out.print(aIn[j]);
                    }
                    
                    end test ***/
                    
                    
                    
                    for (int j=0; j<a1dCopy.length; j++) {
                        int target=a1dCopy[j];
                        for (int k=0; k<bOut.length; k++) {
                            if (bOut[k]==target) {
                                
                                for (int l=0; l<bIn.length; l++) {
                                    
                                        if (bIn[l]==0) {
                                            bIn[l]=target;
                                            break;
                                        }
                                    
                                    
                                }
                            }
                        }
                    }       
                    /***************
                    System.out.println();
                    System.out.println("a1d pre xover: ");
                    for(int j=0; j<a1dCopy.length; j++) {
                        System.out.print(a1dCopy[j]);
                        
                    }
                    System.out.println();
                    System.out.println("b1d pre xover: ");
                    for(int j=0; j<b1dCopy.length; j++) {
                        System.out.print(b1dCopy[j]);
                        
                    } *****************/
                    
                    for (int j=0; j<aIn.length; j++) {
                        for (int k=0; k<a1d.length; k++) {
                            if (a1d[k]==0) {
                                a1d[k]=aIn[j];
                                break;
                            }
                        }
                    }
                    for (int j=0; j<bIn.length; j++) {
                        for (int k=0; k<b1d.length; k++) {
                            if (b1d[k]==0) {
                                b1d[k]=bIn[j];
                                break;
                            }
                        }
                    }
                    
                /*************
                    System.out.println();
                    System.out.println("a1d post xover: ");
                    for(int j=0; j<a1d.length; j++) {
                        System.out.print(a1d[j]);
                        
                    }
                    System.out.println();
                    System.out.println("b1d post xover: ");
                    for(int j=0; j<b1d.length; j++) {
                        System.out.print(b1d[j]);
                        
                }     ******/
                    
                    if (buildx) {    
                        A.build2dX(a1d);                
                        B.build2dX(b1d);
                    } else {
                        A.build2dY(a1d);                
                        B.build2dY(b1d);
                    }
                    A.calculateFitness();
                    B.calculateFitness();
                    population[i]=A;
                    population[Bindex]=B;
                
                }
                
            
            }
            //System.out.println("fitness of i after xover: " + postXover[i].getFitness());
        }

            return population;
        }
    
    
    public MagicSquare[] partiallyMatchedCrossover(MagicSquare[] preXover) {
        MagicSquare[] postXover = new MagicSquare[preXover.length];
        
        for (int i=0; i<preXover.length; i++) {
            if (Math.random()<xoverrate) {
                MagicSquare A=preXover[i];
                int Bindex=(int)Math.random()*preXover.length;
                MagicSquare B=preXover[Bindex];
                if (A!=B) {
                
                    int[] a1d;
                    int[] b1d;
                    int[] a1dCopy;
                    int[] b1dCopy;
                    boolean buildx;
                    if (Math.random()<xVyrate) {
                        a1d=A.get1DArrayX();
                        b1d=B.get1DArrayX();
                        a1dCopy=A.get1DArrayX();
                        b1dCopy=B.get1DArrayX();
                        buildx=true;
                    } else {
                        a1d=A.get1DArrayY();
                        b1d=B.get1DArrayY();
                        a1dCopy=A.get1DArrayY();
                        b1dCopy=B.get1DArrayY();
                        buildx=false;
                        
                    }
                
                int x1 = (int)Math.random()*a1d.length;
                int x2 = (int)Math.random()*a1d.length;
                while (x1==x2) {
                    x2 = (int)(Math.random()*a1d.length);
                }
                /***
                
                    System.out.println();
                    System.out.println("a1d pre xover: ");
                    for(int j=0; j<a1d.length; j++) {
                        System.out.print(a1d[j]);
                        
                    }
                    System.out.println();
                    System.out.println("b1d pre xover: ");
                    for(int j=0; j<b1d.length; j++) {
                        System.out.print(b1d[j]);
                        
                    } ***/
                
                if (x1<x2) {
                    int temp;
                    int newval;
                    for (int j=x1; j<=x2; j++) {
                        temp=a1d[j];
                        newval=b1dCopy[j];
                        for (int k=0; k<a1d.length;k++) {
                            if (a1d[k]==newval) {
                                a1d[j]=newval;
                                a1d[k]=temp;                               
                            }
                        }
                        
                    }
                    for (int j=x1; j<=x2; j++) {
                        temp=b1d[j];
                        newval=a1dCopy[j];
                        for (int k=0; k<b1d.length;k++) {
                            if (b1d[k]==newval) {
                                b1d[j]=newval;
                                b1d[k]=temp;                               
                            }
                        }
                        
                    }
                    
                } else {
                    int temp;
                    int newval;
                    for (int j=x2; j<=x1; j++) {
                        temp=a1d[j];
                        newval=b1dCopy[j];
                        for (int k=0; k<a1d.length;k++) {
                            if (a1d[k]==newval) {
                                a1d[j]=newval;
                                a1d[k]=temp;                               
                            }
                        }
                        
                    }
                    for (int j=x2; j<=x1; j++) {
                        temp=b1d[j];
                        newval=a1dCopy[j];
                        for (int k=0; k<b1d.length;k++) {
                            if (b1d[k]==newval) {
                                b1d[j]=newval;
                                b1d[k]=temp;                               
                            }
                        }
                        
                    }
                }
                /****
                
                    System.out.println();
                    System.out.println("a1d post xover: ");
                    for(int j=0; j<a1d.length; j++) {
                        System.out.print(a1d[j]);
                        
                    }
                    System.out.println();
                    System.out.println("b1d post xover: ");
                    for(int j=0; j<b1d.length; j++) {
                        System.out.print(b1d[j]);
                        
                } ***/

                    if (buildx) {    
                        A.build2dX(a1d);                
                        B.build2dX(b1d);
                    } else {
                        A.build2dY(a1d);                
                        B.build2dY(b1d);
                    }
                    A.calculateFitness();
                    B.calculateFitness();
                    postXover[i]=A;
                    postXover[Bindex]=B;
                } else {
                    postXover[i]=preXover[i].deepCopy();
                }
            
            } else {
                postXover[i]=preXover[i].deepCopy();
            }
            
        }
        
        
        return postXover;
    }
    
    public void setXoverRate(double Px) {
        xoverrate=Px;
    }
    
    
    
}
