
// Neel Patel cs610 7302 prp 

import java.util.*;
import java.io.*;
import java.lang.*;
import static java.lang.Math.*;

public class pgrk7302 {

        int iter;
        int initval;
        String filename;
        int n;      // number of vertices in the graph
        int m;      // number of edges in the graph
        int[][] A;  // adjacency matrix 
        final double d = 0.85;
        double errorrate;
        double[] Src;
        int[] P; // array to contain number of outgoing links for a page

    pgrk7302() {} //default constructor

    pgrk7302(int iter, int initval, String filename)     
    {
        this.iter = iter;
        this.initval = initval;
        this.filename = filename;
        int p,q = 0;
        try {        
            Scanner scanner = new Scanner(new File(filename));
            n = scanner.nextInt();
            m = scanner.nextInt();
            
            //Adjacency matrix representation of graph
            A = new int[n][n];
            for(int i = 0; i < n; i++)
             for(int j = 0; j < n; j++)
               A[i][j] = 0;
            
            while(scanner.hasNextInt())
            {
                p = scanner.nextInt();
                q = scanner.nextInt();
                A[p][q] = 1;
            }
            

            //P[i] represents number of outgoing links of a page 
            P = new int[n];
            for(int i = 0; i < n; i++) {
                P[i] = 0;
                for(int j = 0; j < n; j++) {
                    P[i] += A[i][j];
                }
            }

            Src = new double[n];
            switch(initval) {
            case 0:
              for(int i = 0; i < n; i++) {
                Src[i] = 0;
              }
              break;
            case 1:
              for(int i = 0; i < n; i++) {
                Src[i] = 1;
              }
              break;
            case -1:
              for(int i =0; i < n; i++) {
                Src[i] = 1.0/n;
              }
              break;
            case -2:
              for(int i =0; i < n; i++) {
                Src[i] = 1.0/Math.sqrt(n);
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
            System.out.println("Format: pgrk7302 iterations initialvalue filename");
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
		

        pgrk7302 pr = new pgrk7302(iterations, initialvalue, filename);

        pr.pgrkAlgo7302();
    }

    boolean isConverged(double[] src, double[] target)
    {
        for(int i = 0; i < n; i++)
        {
          if ( abs(src[i] - target[i]) > errorrate )
            return false;
        }
        return true;
    }

    void pgrkAlgo7302() {
        double[] D = new double[n];
        boolean flag = true;
        // If the graph has N greater than 10, then the values for iterations, initialvalue is set to 0 and -1 respectively.
        if(n > 10) {
            iter = 0;
            for(int i =0; i < n; i++) {
              Src[i] = 1.0/n;
            }
            int i = 0;
          do {
               if(flag == true)
               {
                  flag = false;
               }
               else
				   
               for(int j = 0; j < n; j++) {
				   Src[j] = D[j];
				   D[j] = 0.0;
                 for(int k = 0; k < n; k++)
                 {
                    if(A[k][j] == 1) {
                        D[j] += Src[k]/P[k];
                    }
                 }
               }

               //Evaluating pagerank of all pages
               for(int l = 0; l < n; l++) {
                 D[l] = d*D[l] + (1-d)/n;
               }
               i++;
             } while (isConverged(Src, D) != true);

             // print pageranks at the stopping iteration 
             System.out.println("Iter: " + i);
             for(int l = 0 ; l < n; l++) {
                 System.out.printf("P[" + l + "] = %.7f\n",Math.round(D[l]*10000000.0)/10000000.0);
             }
             return;
        }
        //Base Case
        System.out.print("Base    : 0");
        for(int j = 0; j < n; j++) {
            System.out.printf(" :P[" + j + "]=%.7f",Math.round(Src[j]*10000000.0)/10000000.0);
        }

        if (iter > 0) {
          for(int i = 0; i < iter; i++)
          {
              for(int l = 0; l < n; l++) {
                D[l] = 0.0;
              }
            
              for(int j = 0; j < n; j++) {
				  
                for(int k = 0; k < n; k++)
                {
                    if(A[k][j] == 1) {
                        D[j] += Src[k]/P[k];
                    } 
                }
              }

              //Evaluating pagerank of all pages
              System.out.println();
              System.out.print("Iter    : " + (i+1));
              for(int l = 0; l < n; l++) {
                D[l] = d*D[l] + (1-d)/n;
                System.out.printf(" :P[" + l + "]=%.7f",Math.round(D[l]*10000000.0)/10000000.0);
              }
            
              for(int l = 0; l < n; l++) {
                Src[l] = D[l]; 
              } 
          }
          System.out.println();
        }
        else 
        { //number of iterations is 0 or less than 0, try convergence
	
		  errorrate = ( iter < 0 ) ? Math.pow(10,iter) : Math.pow(10,-5);
			  
          int i = 0;
          do {
               if(flag == true)
               {
                  flag = false;
               }
               else{
               
				   for(int j = 0; j < n; j++) {
					   Src[j] = D[j];
				   }
			   }
			   
			   for(int j = 0; j < n; j++) 
			   {
				   D[j] = 0.0;
				   
			   }
			    for(int j = 0; j < n; j++) {
					 for(int k = 0; k < n; k++)
					 {
						if(A[k][j] == 1) {
							D[j] += Src[k]/P[k];
						}
					 }
				}
               

               //Evaluating pagerank of all pages
               System.out.println(); 
               System.out.print("Iter    : " + (i+1));
               for(int l = 0; l < n; l++) {
                 D[l] = d*D[l] + (1-d)/n;
                 System.out.printf(" :P[" + l + "]=%.7f",Math.round(D[l]*10000000.0)/10000000.0);
               }
               i++;  
             } while (isConverged(Src, D) != true);  
        System.out.println(); 
        }
    }
}