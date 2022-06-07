/**
 * KIT107 Assignment 1
 *
 * Periodic Table Printer Class
 * This program implements the Periodic Table interface. It asks the user which periodic table elements
 * they would like displayed, and whether to display the special Lanthanum and Actinium groups. 
 * This is then printed in a formatted table to standard output. 
 * PeriodicTable.java
 * 
 * @author << Vinh Nguyen 470821, Evan Harvey 471061>>
 * Proportion of workload <<50:50>>
 * @version <<15/03/19>>
 * 
 */

import java.util.Scanner;
import java.text.DecimalFormat;
import java.lang.Integer;
import java.lang.String;


public class PeriodicTable implements PeriodicTableInterface
{
    // Final Variables
    
    // These are used for boundary checks and formatting 
    protected final int FIRST_ELEMENT = 1;  // Periodic table size - lower boundary
    protected final int LAST_ELEMENT = 118; // Periodic table size - upper boundary 
    protected final int MAX_COLUMNS = 18; // Maximum number of columns in the table
    
    // These are 'magic numbers' used to determine whitespace and what elements to (not) display
    protected final int LANTH_INDEX = 57;  // Index location for first lanthanum element 
    protected final int FIRST_LANTH = -11; // First group number for actinium elements
    protected final int LAST_LANTH = -25; // Last group number for actinium elements
    
    protected final int ACTIN_INDEX= 89;   // Index location for first actinium element
    protected final int FIRST_ACTIN = -31; // First group number for actinium elements
    protected final int LAST_ACTIN = -45; //  Last group number for actinium elements
    
    // These are used to make assignments using array subscripts explicit 
    protected final int SYMBOL_INDEX = 0; // Index location of elements' chemical symbol
    protected final int GROUP_INDEX = 1;  // Index location of element group 
    
    // Pattern match for a character in the set, if it appears at the line start - used when prompting the user for input
    protected final String YES = "^[Yy].*";  // Pattern match for yes
    protected final String NO =  "^[Nn].*";  // Pattern match for no
    
    protected final String TABLE[][]={{"H","1"},{"He","18"},{"Li","1"},{"Be","2"},{"B","13"},{"C","14"},{"N","15"},{"O","16"},{"F","17"},
        {"Ne","18"},{"Na","1"},{"Mg","2"},{"Al","13"},{"Si","14"},{"P","15"},{"S","16"},{"Cl","17"},{"Ar","18"},{"K","1"},{"Ca","2"},
        {"Sc","3"},{"Ti","4"},{"V","5"},{"Cr","6"},{"Mn","7"},{"Fe","8"},{"Co","9"},{"Ni","10"},{"Cu","11"},{"Zn","12"},{"Ga","13"},
        {"Ge","14"},{"As","15"},{"Se","16"},{"Br","17"},{"Kr","18"},{"Rb","1"},{"Sr","2"},{"Y","3"},{"Zr","4"},{"Nb","5"},{"Mo","6"},
        {"Tc","7"},{"Ru","8"},{"Rh","9"},{"Pd","10"},{"Ag","11"},{"Cd","12"},{"In","13"},{"Sn","14"},{"Sb","15"},{"Te","16"},{"I","17"},
        {"Xe","18"},{"Cs","1"},{"Ba","2"},{"La","-11"},{"Ce","-12"},{"Pr","-13"},{"Nd","-14"},{"Pm","-15"},{"Sm","-16"},{"Eu","-17"},
        {"Gd","-18"},{"Tb","-19"},{"Dy","-20"},{"Ho","-21"},{"Er","-22"},{"Tm","-23"},{"Yb","-24"},{"Lu","-25"},{"Hf","4"},{"Ta","5"},
        {"W","6"},{"Re","7"},{"Os","8"},{"Ir","9"},{"Pt","10"},{"Au","11"},{"Hg","12"},{"Tl","13"},{"Pb","14"},{"Bi","15"},{"Po","16"},
        {"At","17"},{"Rn","18"},{"Fr","1"},{"Ra","2"},{"Ac","-31"},{"Th","-32"},{"Pa","-33"},{"U","-34"},{"Np","-35"},{"Pu","-36"},
        {"Am","-37"},{"Cm","-38"},{"Bk","-39"},{"Cf","-40"},{"Es","-41"},{"Fm","-42"},{"Md","-43"},{"No","-44"},{"Lr","-45"},{"Rf","4"},
        {"Db","5"},{"Sg","6"},{"Bh","7"},{"Hs","8"},{"Mt","9"},{"Ds","10"},{"Rg","11"},{"Cn","12"},{"Uut","13"},{"Fl","14"},{"Uup","15"},
        {"Lv","16"},{"Uus","17"},{"Uuo","18"}};
    
    // Used to format chemical numbers with leading zeroes
    protected final DecimalFormat FMT=new DecimalFormat("000");
    
    // Instance Variables
    protected int start;     // # of first element to print
    protected int stop;      // # of last element to print
    protected boolean show;     // want to display lanthanum and actinium groups?
    protected boolean printLan;  // need to display lanthanum group?
    protected boolean printAct;  // need to display actinium group?
    
    /**
     * getData() -- obtain user input
     *
     * Pre-condition: none
     * Post-condition: the instance variable show is set to true if printing of the lanthanum and actinium groups is desired and false
     *                 otherwise; start is given the atomic number of the first element to display; stop is given the atomic number of
     *                 the last element to display.
     */
    public void getData()
    {
        // Prompt user whether to print the special lanthanum and actinium groups
        System.out.print("Periodic Table Printer\n\n" 
                             + "Print the Lanthanum/Actinium groups if necessary [Y/N]: ");
        Scanner sc = new Scanner(System.in); 
        
        String choice = sc.nextLine(); // Get user input, whether to print special groups
        
        // Check if user enter "y" or "Y" to display the lanthanum and actinium groups.
        if (choice.matches(YES)) // User wants to see optional elements
        {
            show = true;
            printLan = true;
            printAct = true;
        }
        else if (choice.matches(NO)) // User doesn't want to see optional elements
        {
            show = false;
            printLan = false;
            printAct = false;
        }
        else // Default to 'no' if they don't provide correct input
        {
            System.out.println("... N assumed...");
            show = false;
            printLan = false;
            printAct = false;
        }
        
        // Prompt user for first element to begin displaying from
        System.out.print("Enter atomic number of first element to print: ");
        
        start = sc.nextInt(); // Get user input, first element to show
        
        // Is starting point within the periodic table boundary?
        if(! isElementInRange(FIRST_ELEMENT, LAST_ELEMENT, start) )
        {
            // Input is not within bounds, so set it to lower boundary 
            start = FIRST_ELEMENT;
            System.out.println("..." + FIRST_ELEMENT + " assumed...");
        }
        
        // Prompt user for last element to display
        System.out.print("Enter atomic number of last element to print: ");
        
        stop = sc.nextInt(); // Get user input, last element to show
        
        // Is place to stop within bounds & different from starting point?
        if (! isElementInRange(FIRST_ELEMENT, LAST_ELEMENT, stop) )
        {
            // Input isn't within bounds or is the same as starting point
            stop = LAST_ELEMENT;
            System.out.println("..." + LAST_ELEMENT + " assumed...");
        }
    }
    
    /**
     * printTables() -- display (excerpt of) periodic table
     *
     * Pre-condition: instance variables start and stop have been validly defined
     * Post-condition: the (excerpt of) the periodic table from elements between the range of start and stop (excluding the lanthanum
     *                 and actinium groups) has been printed and if this range includes those groups then the relevant instance
     *                 variable (printLan or printAct) is true, otherwise it/these are false
     */
    public void printTables()
    {
        int elementGroup;  // Element grouping determines line breaks and special grouping
        int gap;     // Determines breaks between element groups to format the table
        int lastGroup = 0; // Remembers last element group number, used for whitespace
        
        String elementSymbol; // The elements chemical symbol
        
        // Traverse the periodic table depending on user-determined beginning and end
        for(int element = start; element <= stop; element++)
        {
            elementSymbol = TABLE[element - 1][SYMBOL_INDEX]; // The elements chemical symbol to display
            
            elementGroup = Integer.valueOf(TABLE[element - 1][GROUP_INDEX]); // The elements group for formatting purposes 
            
            gap = (elementGroup - lastGroup - 1); // Difference between columns for determining amount of whitespace
            // The difference is the amount of whitespace
            if(gap > 1)
            {
                // Break up the elements
                for( int i = 0; i < gap; i++)
                {
                    printElementGap(); 
                }
                
            }
            // Don't print special groups 
            if(elementGroup > 0)
            {
                printElement(element, elementSymbol);
                
                // Is it the end of a row? If so, start a new one
                if(elementGroup % MAX_COLUMNS == 0)
                {
                    System.out.println();
                }
                // Remember what the last group was for determining gaps
                lastGroup = elementGroup;
            }
            // Format the lanthinium and actinium placeholders on main table
            if(element == LANTH_INDEX || element == ACTIN_INDEX )
            {
                printElementGap(); 
            }
        }
        System.out.println();
    }
    
    /**
     * printGroups() -- display (excerpt of) lanthanum and actinium groups
     *
     * Pre-condition: the instance variables start, stop, show, printLan, printAct have been validly defined
     * Post-condition: if display of the lanthanum groups is necessary (because of the range) and desired by the user, then (a
     * portion of) it is displayed; ditto the actinium group
     */
    public void printGroups()
    {
        String elementSymbol; // the symbol of element current printing
        int elementGroup; // the group number of element current printing
        
        // If we need to print special groups, do this 
        if(show)
        {   
            // Go through the periodic table again depending on user-determined beginning and end 
            for(int element = start; element <= stop; element++)
            {
                // retrieve the element symbol and group from the table
                elementSymbol = TABLE[element-1][SYMBOL_INDEX];
                elementGroup = Integer.valueOf(TABLE[element-1][GROUP_INDEX]);
                
                // Special groups are negative
                if(elementGroup < 0)
                {
                    // Print a lanthanium element if we need to
                    if (printLan == true && isElementInRange(LAST_LANTH, FIRST_LANTH, elementGroup))
                    {
                        printElement(element, elementSymbol);
                        
                        // Break up the lanthinium and actinium groups by a new row
                        if(elementGroup % LAST_LANTH == 0)
                        {
                            System.out.println();
                        }
                    }
                    
                    // Print a actinium element if we need to
                    else if (printAct == true && isElementInRange(LAST_ACTIN, FIRST_ACTIN, elementGroup))
                    {
                        printElement(element, elementSymbol);                       
                    }
                }
            }    
        }
        System.out.println();
    }
    
    /**
     * printElement() --- print an element's atomic number and symbol
     *
     * Pre-condition: none 
     *
     * Post-condition: a left-aligned group number and matching chemical symbol are printed to standard output, eight (8) characters wide
     *
     */
    private void printElement(int element, String elementSymbol)
    {
        // Left aligned formatting of strings - leading zeroes on element 
        System.out.printf("%-4s%-4s", FMT.format(element), elementSymbol);
    }
    
    /**
     * printElementGap() -- print size-appropriate blank space 
     *
     * Pre-condition: none
     *
     * Post-condition: eight (8) spaces are printed to standard output 
     *
     */
    private void printElementGap()
    {
        System.out.printf("%8s",""); // One element-sized space
    }
    /**
     * isElementInRange() -- determine if atomic number is within bounds
     *
     * Pre-condition: none 
     *
     * Post-condition: true is returned if the atomic number is between bounds (inclusive), false otherwise
     *
     */
    private boolean isElementInRange(int lowerBound, int upperBound, int element)
    {
        // Return true if within bounds, false otherwise
        return (element >= lowerBound && element <= upperBound); 
    }
}

 
