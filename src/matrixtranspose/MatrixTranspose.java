/*
 * Team 4: Daniel Gomes (L), John Gomes, & Robert Larrivee
 * Problem Set 1: Problem 1-5
 * Submission Date: 02/11/2020
*/

package matrixtranspose;

// libraries
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MatrixTranspose implements Runnable 
{
    // initialize variables
    private static AtomicInteger finish = new AtomicInteger(0);
    private int taskID;
    private static int Matrix[][];

    // constructors
    public MatrixTranspose() {}
    public MatrixTranspose(int i) { taskID = i; }
    
    // method to make a random matrix or testing
    static int[][] MakeRandomMatrix()
    {
        // create the instance of the random class
        Random rand = new Random();
        
        // generate random integers for row length
        int n = rand.nextInt(10) + 2;
        
        // initialize matrix
        int[][] M = new int[n][n];
        
        // iterate through all strips
        for (int i = 0; i < n; i++)
        {
            // iterate through all items in strip
            for (int j = 0; j < n; j++)
            {
                // random number into matrix spot
                M[i][j] = rand.nextInt(10);
            }
        }
    
        // return random matrix
        return M;
        
    } // MakeRandomMatrix()
    
    // method to transpose a matrix
    static void transpose(int A[][]) 
    { 
        // get length of matrix rows
        int N = A[0].length;
        
        // initialize new matrix
        int[][] B = new int[N][N];
        
        // transpose
        for (int i = 0; i < N; i++)
        {
           for (int j = 0; j < N; j++)
           {
                B[i][j] = A[j][i]; 
                System.out.print(B[i][j] + " ");
           }
            System.out.println();
        }   
        
    } // transpose(int A[][]) 

    public void run() 
    {
        // init string
        String s = "";
        
        // begin timer
        long begTest = new java.util.Date().getTime();
        
        // print start time
        System.out.println("Start - Task " + taskID);
        
        // delay processes
        for (int i = 0; i < 35000; i++) s = s + 'x';
        
        // transpose the matrix
        transpose(Matrix);
                
        // end timer
        Double secs = new Double((new java.util.Date().getTime() - begTest)*0.001);
        
        // print end
        System.out.println("End - task " + taskID + " [Run time " + secs + " secs]");
        
        finish.getAndAdd(1);  // atomic action
        
    } // run()
    
    public static void main(String[] args) 
    {
        // process workers
        int p = 10;
        
        // get a random matrix 
        Matrix = MakeRandomMatrix();
        
        // print the created matrix
        printMatrix();
        
        Thread[] worker = new Thread[p+1]; // worker[0] is not used
        long begTest = new java.util.Date().getTime();

        for (int i=1; i<=p; i++) {
            worker[i] = new Thread(new MatrixTranspose(i));
            worker[i].start();
        }

        // Wait until all threads have finished
        while (finish.get() != p) { Thread.yield(); }

        Double secs = new Double((new java.util.Date().getTime() - begTest)*0.001);
        System.out.println("** Total run time " + secs + " secs");
        
    } // main(String[] args)
    
    static void printMatrix()
    {
        // print the matrix
        System.out.println("------------Matrix------------");

        for (int i = 0; i < Matrix.length; i++) 
        {
            for (int j = 0; j < Matrix[i].length; j++) 
            {
                System.out.print(Matrix[i][j] + " ");
            }
                System.out.println();
        }
        System.out.println("------------Matrix------------");
        
    } // printMatrix()
    
} // class MatrixTranspose implements Runnable

/* Example Output
        run:
        ------------Matrix------------
        3 2 0 9 4 1 2 7 4 6 2 
        4 1 4 7 6 2 8 8 2 3 3 
        8 6 8 8 3 5 9 9 1 0 2 
        4 1 3 1 5 6 1 8 2 9 7 
        1 7 9 6 1 6 0 0 1 7 1 
        6 0 0 9 3 3 9 0 1 5 1 
        6 7 0 6 4 3 7 4 3 3 4 
        1 7 8 7 6 1 7 3 4 6 3 
        6 1 9 1 3 7 8 3 8 2 5 
        0 6 0 2 6 1 5 1 8 4 3 
        2 5 9 3 3 6 5 0 9 9 2 
        ------------Matrix------------
        Start - Task 1
        Start - Task 2
        Start - Task 4
        Start - Task 3
        Start - Task 5
        Start - Task 6
        Start - Task 8
        Start - Task 7
        Start - Task 9
        Start - Task 10
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 2 [Run time 3.408 secs]
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 5 [Run time 3.563 secs]
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 3 [Run time 3.633 secs]
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 1 [Run time 4.0360000000000005 secs]
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 10 [Run time 4.112 secs]
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 4 [Run time 4.265 secs]
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 7 [Run time 4.337 secs]
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 8 [Run time 5.54 secs]
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 9 [Run time 5.6240000000000006 secs]
        3 4 8 4 1 6 6 1 6 0 2 
        2 1 6 1 7 0 7 7 1 6 5 
        0 4 8 3 9 0 0 8 9 0 9 
        9 7 8 1 6 9 6 7 1 2 3 
        4 6 3 5 1 3 4 6 3 6 3 
        1 2 5 6 6 3 3 1 7 1 6 
        2 8 9 1 0 9 7 7 8 5 5 
        7 8 9 8 0 0 4 3 3 1 0 
        4 2 1 2 1 1 3 4 8 8 9 
        6 3 0 9 7 5 3 6 2 4 9 
        2 3 2 7 1 1 4 3 5 3 2 
        End - task 6 [Run time 5.658 secs]
        ** Total run time 5.658 secs
        BUILD SUCCESSFUL (total time: 5 seconds)
*/