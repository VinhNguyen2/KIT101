/**
 * KIT107 Assignment 1 SOLUTION
 *
 * Periodic Table Printer
 *
 * @author J. Dermoudy
 * @version 2/3/2019
 */

import java.util.Scanner;

public class AssigOne119
{
    /**
     * main() -- entry point
     *
     * @param args -- command line arguments
     */
    public static void main(String args[])
    {
        PeriodicTable p;
    
        // create PeriodicTable object
        p=new PeriodicTable();
        // get desired range from user
        p.getData();
        
        System.out.println("\t\t\t\t\t\t\t\t Periodic Table");
        System.out.println("\t\t\t\t\t\t\t\t ==============");
        // show (portion of) periodic table
        p.printTables();
        p.printGroups();
    }
}