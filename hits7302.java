
// Neel Patel cs610 7302 prp 

import java.util.*;
import java.io.*;
import java.lang.*;
import static java.lang.Math.*;

public class hits7302 {

        int iter;
        int initval;
        String filename;
        int v;      // number of vertices in the graph
        int e;      // number of edges in the graph
        int[][] m;  // adjacency matrix 
        double[] h0;
        double[] a0;
        double errorrate;

    hits7302() {} //default constructor

    hits7302(int iter, int initval, String filename)     
    {
        this.iter = iter;
        this.initval = initval;
        this.filename = filename;
        try {        
            Scanner scanner = new Scanner(new File(filename));
            v = scanner.nextInt();
            e = scanner.nextInt();
            
            //Adjacency matrix representation of graph
            m = new int[v][v];
            for(int i = 0; i < v; i++)
             for(int j = 0; j < v; j++)
               m[i][j] = 0;

            while(scanner.hasNextInt())
            {
                m[scanner.nextInt()][scanner.nextInt()] = 1;    
            }

            h0 = new double[v];
            a0 = new double[v];
			// Four cases for initial value: 0, 1, -1, -2
            switch(initval) {
            case 0: 
              for(int i = 0; i < v; i++) {
                h0[i] = 0;
                a0[i] = 0;
              }
              break;
            case 1:
              for(int i = 0; i < v; i++) {
                h0[i] = 1;
                a0[i] = 1;
              }
              break;
            case -1:
              for(int i =0; i < v; i++) {
                h0[i] = 1.0/v;
                a0[i] = 1.0/v;
              }
              break;
            case -2:
              for(int i =0; i < v; i++) {
                h0[i] = 1.0/Math.sqrt(v);
                a0[i] = 1.0/Math.sqrt(v);
              }
              break;
            }

        }
        catch(FileNotFoundException e)
        {
			System.out.println(" Entered File not found ");
        }
    }

    public static void main(String[] args)
    {
        if(args.length != 3) {
            System.out.println("Format: hits7302 iterations initialvalue filename");
            return;
        }
        //command line arguments
        int iterations = Integer.parseInt(args[0]);
        int initialvalue = Integer.parseInt(args[1]);
        String filename = args[2];

        if( !(initialvalue >= -2 && initialvalue <= 1) ) {
            System.out.println("Enter -2, -1, 0 or 1 for initialvalue");
            return;
        }

        hits7302 ht = new hits7302(iterations, initialvalue, filename);

        ht.hitsAlgo7302();
    }
 
    boolean isConverged(double[] p, double[] q)
    {
       for(int i = 0 ; i < v; i++) {
           if ( abs(p[i] - q[i]) > errorrate ) 
             return false;
       }
       return true;
    } 
    
    public void hitsAlgo7302()
    {
        double[] h = new double[v];
        double[] a = new double[v];
        double a_scale_factor,a_sum_square,h_scale_factor,h_sum_square = 0.0;
        double[] aprev = new double[v]; //last iterations values of a, used for convergence
        double[] hprev = new double[v]; //last iterations values of h, used for convergence

        //If the graph has N greater than 10, then the values for iterations, initialvalue revert to 0 and -1 respectively
        if(v > 10) {
            iter = 0;
            for(int i =0; i < v; i++) {
                h[i] = 1.0/v;
                a[i] = 1.0/v;
                hprev[i] = h[i];
                aprev[i] = a[i];
            }
            
          int i = 0;
          do {  
               for(int r = 0; r < v; r++) {
                   aprev[r] = a[r];
                   hprev[r] = h[r];
               }
            
                for(int j = 0; j < v; j++) {
					a[j]=0.0;
                    for(int k = 0; k < v; k++) {
                        if(m[k][j] == 1) {
                            a[j] += h[k]; 
                        }
                    }
                }//Terminating the steps of A

                //Initializing the steps of H
                /*for(int p = 0; p < v; p++) {
                    h[p] = 0.0;
                }*/

                for(int j = 0; j < v; j++) {
					h[j]=0.0;
                    for(int k = 0; k < v; k++) {
                        if(m[j][k] == 1) {
                            h[j] += a[k]; 
                        }
                    }
                }//Terminating the steps of H

                //Initializing scaling of A
                a_scale_factor = 0.0;
                a_sum_square = 0.0;
                for(int s = 0; s < v; s++) {
                    a_sum_square += a[s]*a[s];    
                }
                a_scale_factor = Math.sqrt(a_sum_square); 
                for(int s = 0; s < v; s++) {
                    a[s] = a[s]/a_scale_factor;
                }//Terminating scaling of A  
 
                //Initializing scaling of H
                h_scale_factor = 0.0;
                h_sum_square = 0.0;
                for(int s = 0; s < v; s++) {
                    h_sum_square += h[s]*h[s];    
                }
                h_scale_factor = Math.sqrt(h_sum_square); 
                for(int s = 0; s < v; s++) {
                    h[s] = h[s]/h_scale_factor;
                }// Terminating scaling of H
                i++; // incr the interation counter
          } while( false == isConverged(a, aprev) || false == isConverged(h, hprev));
          System.out.println("Iter:    " + i);
          for(int s = 0; s < v; s++) {
              System.out.printf(" A/H[%d]=%.7f/%.7f\n",s,Math.round(a[s]*10000000.0)/10000000.0,Math.round(h[s]*10000000.0)/10000000.0); 
          }
          return;
        }

        //Initialization
        for(int i = 0; i < v; i++)
        {
            h[i] = h0[i];
            a[i] = a0[i];
            hprev[i] = h[i];
            aprev[i] = a[i]; 
        }
        
        //Base Case
        System.out.print("Base:    0 :");
        for(int i = 0; i < v; i++) {
          System.out.printf(" A/H[%d]=%.7f/%.7f",i,Math.round(a0[i]*10000000.0)/10000000.0,Math.round(h0[i]*10000000.0)/10000000.0); 
          //System.out.println("a0[" + i + "]= " + a0[i]); 
        }
        
        if (iter > 0) { 
            for(int i = 0; i < iter; i++) { //iteration starts
            
                //Initializing the steps of A
                /*for(int p = 0; p < v; p++) {
                    a[p] = 0.0;
                }*/
            
                for(int j = 0; j < v; j++) {
					a[j] = 0.0;
                    for(int k = 0; k < v; k++) {
                        if(m[k][j] == 1) {
                            a[j] += h[k]; 
                        }
                    }
                }//Terminating the steps of A

               

                for(int j = 0; j < v; j++) {
					h[j] = 0.0;
                    for(int k = 0; k < v; k++) {
                        if(m[j][k] == 1) {
                            h[j] += a[k]; 
                        }
                    }
                }//Terminating the steps of H

                //Initializing scaling of A
                a_scale_factor = 0.0;
                a_sum_square = 0.0;
                for(int s = 0; s < v; s++) {
                    a_sum_square += a[s]*a[s];    
                }
                a_scale_factor = Math.sqrt(a_sum_square); 
                for(int s = 0; s < v; s++) {
                    a[s] = a[s]/a_scale_factor;
                }//Terminating scaling of A  
 
                //Initializing scaling of H
                h_scale_factor = 0.0;
                h_sum_square = 0.0;
                for(int s = 0; s < v; s++) {
                    h_sum_square += h[s]*h[s];    
                }
                h_scale_factor = Math.sqrt(h_sum_square); 
                for(int s = 0; s < v; s++) {
                    h[s] = h[s]/h_scale_factor;
                }// Terminating scaling of H
            
                System.out.println();
                System.out.print("Iter:    " + (i+1) + " :");
                for(int s = 0; s < v; s++) {
                    System.out.printf(" A/H[%d]=%.7f/%.7f",s,Math.round(a[s]*10000000.0)/10000000.0,Math.round(h[s]*10000000.0)/10000000.0); 
                }
   
            }//iteration ends
        } // if iter != 0 ends
        else
        {
		  errorrate = ( iter < 0 ) ? Math.pow(10,iter) : Math.pow(10,-5);
          int i = 0;
          do {  
                for(int r = 0; r < v; r++) {
                    aprev[r] = a[r];
                    hprev[r] = h[r];
                }

                for(int j = 0; j < v; j++) {
					a[j]=0.0;
                    for(int k = 0; k < v; k++) {
                        if(m[k][j] == 1) {
                            a[j] += h[k]; 
                        }
                    }
                }//Terminating the steps of A

                for(int j = 0; j < v; j++) {
					h[j]=0.0;
                    for(int k = 0; k < v; k++) {
                        if(m[j][k] == 1) {
                            h[j] += a[k]; 
                        }
                    }
                }//Terminating the steps of H

                //Initializing scaling of A
                a_scale_factor = 0.0;
                a_sum_square = 0.0;
                for(int s = 0; s < v; s++) {
                    a_sum_square += a[s]*a[s];    
                }
                a_scale_factor = Math.sqrt(a_sum_square); 
                for(int s = 0; s < v; s++) {
                    a[s] = a[s]/a_scale_factor;
                }//Terminating scaling of A  
 
                //Initializing scaling of H
                h_scale_factor = 0.0;
                h_sum_square = 0.0;
                for(int s = 0; s < v; s++) {
                    h_sum_square += h[s]*h[s];    
                }
                h_scale_factor = Math.sqrt(h_sum_square); 
                for(int s = 0; s < v; s++) {
                    h[s] = h[s]/h_scale_factor;
                }// Terminating scaling of H
                i++; 
                System.out.println();
                System.out.print("Iter:    " + i + " :");
                for(int s = 0; s < v; s++) {
                    System.out.printf(" A/H[%d]=%.7f/%.7f",s,Math.round(a[s]*10000000.0)/10000000.0,Math.round(h[s]*10000000.0)/10000000.0); 
                }
          } while( false == isConverged(a, aprev) || false == isConverged(h, hprev));
        }
        System.out.println();
    }
}