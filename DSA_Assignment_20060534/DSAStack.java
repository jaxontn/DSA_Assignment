//FILE: DSAStack.java
//AUTHOR: Jason Tan Thong Shen
//UNIT: DATA STRUCTURES AND ALGORITHMS
//PURPOSE: to do the stack algorithm
//REFERENCE: lecture slides & GRAPH LAB 2020
//REQUIRES: DSALinkedList.java
//Last Mod: 20th MAY 2021
//REMARKS: THIS IS MY GRAPH LAB PRACTICAL FROM SEM 2, 2020 AND HAS BEEN
//         MODIFIED TO FIT THE ASSIGNMENT REQUIREMENT.

import java.util.*;

public class DSAStack 
{
    //Private Classfield
    private Object[] array; //accept any data type that we give to it.
    private int counter;
    private int givenCapacity;
    private Object poppedValue;

    //Alternate Constructor
    public DSAStack(int capacity)
    {
        array = new Object[capacity];
        givenCapacity = array.length;
        counter = 0;
    }



    /*************************************************
     *SUBMODULE: push                                *
     *IMPORT: none                                   *
     *EXPORT: none                                   *
     *ASSERTION: add a new item onto the top of stack*
     *************************************************/
    public void push(Object data)
    {
        if(counter < array.length)
        {
            array[counter] = data;
            System.out.println("[" + counter + "] = " + array[counter]);
            counter++;
        }
        else 
        {
            System.out.println("[Full] Push not possible");
        }
    }






    /*************************************************
     *SUBMODULE: pop                                 *
     *IMPORT: none                                   *
     *EXPORT: none                                   *
     *ASSERTION: pop off the top-most item from stack*
     *************************************************/
    public void pop()
    {
        if(counter > 0)
        {
            poppedValue = array[counter-1];
            array[counter-1] = null;
            System.out.println("[" + (counter-1) + "] = " + 
                               array[counter-1]);
            counter--;
        }
        else
        {
            System.out.println("[cant pop anymore]");
        }
    }






    /************************************************
     *SUBMODULE: peek                               *
     *IMPORT: none                                  *
     *EXPORT: data (Object)                         *
     *ASSERTION: just peek at the top-most item     *
     ************************************************/
    public Object peek()
    {
        Object data;
        data = "";
        try
        {
            data = array[counter-1];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            data = "no value";
        }
        return data;
    }






    /************************************************
     *SUBMODULE: isEmpty                            *
     *IMPORT: none                                  *
     *EXPORT: empty (boolean)                       *
     *ASSERTION: check if stack is empty            *
     ************************************************/
    public boolean isEmpty() //check if stack is empty
    {
        boolean empty;
        empty = false;

        if(counter == 0)
        {
            empty = true;
        }
        return empty;
    }






    /************************************************
     *SUBMODULE: isFull                             *
     *IMPORT: none                                  *
     *EXPORT: full (boolean)                        *
     *ASSERTION: check if stack is full             *
     ************************************************/
    public boolean isFull() //check if stack is full
    {
        boolean full;
        full = false;

        if(counter == array.length)
        {
            full = true;
        }
        return full;
    }






    /************************************************
     *SUBMODULE: getCount                           *
     *IMPORT: none                                  *
     *EXPORT: counter (integer)                     *
     *ASSERTION: return number of elements          *
     ************************************************/
    public int getCount()
    {
        return counter;
    }






    /************************************************
     *SUBMODULE: getCapacity                        *
     *IMPORT: none                                  *
     *EXPORT: givenCapacity (integer)               *
     *ASSERTION: return capacity number             *
     ************************************************/    
    public int getCapacity()
    {
        return givenCapacity;
    }






    /************************************************
     *SUBMODULE: getPopValue                        *
     *IMPORT: none                                  *
     *EXPORT: poppedValue (Object)                  *
     *ASSERTION: return most recent popped value    *
     ************************************************/
    public Object getPopValue()
    {
        return poppedValue;
    }






    /************************************************
     *SUBMODULE: getArray                           *
     *IMPORT: none                                  *
     *EXPORT: array (Object Array)                  *
     *ASSERTION: return array                       *
     ************************************************/
    public Object[] getArray()
    {
        return array;
    }
}
