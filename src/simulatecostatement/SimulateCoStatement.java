/*
 * Team 4: Daniel Gomes (L), John Gomes, & Robert Larrivee
 * Problem Set 1: Problem 1-2 2.10b
 * Submission Date: 02/11/2020
*/

package simulatecostatement;

import java.util.HashSet;

/**
 * CIS 481: Parallel and Distributed Software Systems
 * Calculate the final results of x and y from the following concurrent program:
 * 
 * int x = 0, y = 0;
 * co x = x + 1; x = x + 2;
 * // x = x + 2; y = y - x; 
 * oc
 * 
 * Source code: SimulateCoStatement.java
 *
 * @author Haiping Xu
 * Created on February 17, 2013 at the CIS Department, UMass Dartmouth
 **/

public class SimulateCoStatement 
{
    private static int countHistory = 0;
    private static String[] array;  // used to store a history
    private static String[] array1, array2; // atomic actions in P1 and P2 respectively.
    private static int pos1, pos2; // current position at array1 and array2, respectively.
    private static HashSet<String> hs; // used to store final results
    
    public static void computeDFS(String[] a, String[] b) 
    {
        array1 = a;
        array2 = b;
        array = new String[array1.length + array2.length];
        hs = new HashSet<String>();
        dfs(0);  // depth-first search
    }
    
    private static void dfs(int level) 
    {
        if (level == array.length) 
        {
            countHistory++;
            printResult(array);
            return;
        }
        
        if (pos1 < array1.length) 
        {
            array[level] = array1[pos1]; // select the current atomic action
            pos1++; // point to the next atomic action
            dfs(level+1); // depth-first search for the next atomic action
            pos1--; // return to the current level and reset to the previous atomic action
        }
        
        if (pos2 < array2.length) 
        {
            array[level] = array2[pos2]; // select the current atomic action
            pos2++; // point to the next atomic action
            dfs(level+1); // depth-first search for the next atomic action
            pos2--; // return to the current level and reset to the previous atomic action
        }
    }
    
    private static void printResult(String[] array) 
    {
        // two processes use different sets of registers, but share the same memory
        int rx = 0, mx = 0, ry = 0, my = 0; 
        
        for (int i=0; i<array.length; i++) 
        {
            if (array[i].equals("a1")) rx = mx;  // read x
            else if (array[i].equals("a2")) rx++;  // add 1 to x
            else if (array[i].equals("a3")) mx = rx; // write to x
            else if (array[i].equals("a4")) rx = mx; // read x
            else if (array[i].equals("a2")) rx += 2;  // add 2 to x
            else if (array[i].equals("a3")) mx = rx; // write to x
            else if (array[i].equals("b1")) rx = mx; // read x 
            else if (array[i].equals("b2")) rx += 2;  // add 2 to x
            else if (array[i].equals("b3")) mx = rx; // write to x
            else if (array[i].equals("b4")) rx = mx; // read x 
            else if (array[i].equals("b5")) ry = my; // read y 
            else if (array[i].equals("b6")) ry = ry - rx; // y - x
            else if (array[i].equals("b7")) my = ry; // write to y
        }
        
        String result = "Final value: x = " + mx + " y = " + my;
        if (!hs.contains(result)) 
        { 
            hs.add(result);
            String histrory = "";
            for (int i = 0; i<array.length; i++)
                histrory += array[i] +", ";
            System.out.println("History = " + histrory + result);
        }
    }
    
    
    public static void main(String[] args) 
    {
        String[] a = {"a1", "a2", "a3", "a4", "a5", "a6"};
        String[] b = {"b1", "b2", "b3", "b4", "b5", "b6", "b7"};
        
        computeDFS(a, b);
        
        System.out.println("\nTotal number of histories = " + countHistory);
    }
}
