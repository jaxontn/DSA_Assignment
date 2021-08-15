//FILE: DSAQueue.java
//AUTHOR: Jason Tan Thong Shen
//UNIT: DATA STRUCTURES AND ALGORITHMS
//PURPOSE: to develop a queue algorihtm
//REFERENCE: lecture slides, GRAPH LAB 2020
//REQUIRES: DSALinkedList.java
//Last Mod: 20th MAY 2021
//REMARKS: THIS IS MY GRAPH LAB PRACTICAL FROM SEM 2, 2020, AND HAS BEEN
//         MODIFIED TO FIT THE ASSIGNMENT REQUIREMENTS

import java.util.*;

public class DSAQueue 
{
    //protected classfields
    protected Object[] array; //to accept any data types
    protected int counter; //count the enqueued
    protected int dequeuedCounter; //count the dequeued
    protected int count; //count the number of elements in the array
    protected int givenCapacity; //size of array
    protected Object head; //the head of the queue
    protected Object dequeuedValue; //store the current dequeued value
                                    //just in case if developer needs the
                                    //dequeued value after dequeued
    protected int headIndex;


    //Alternate Constructor
    public DSAQueue(int capacity)
    {
        array = new Object[capacity];
        givenCapacity = array.length;
        counter = 0;
        dequeuedCounter = 0;
        count = 0;
        headIndex = 0;
    }






    /*****************************************************
     *SUBMODULE: enqueue                                 *
     *IMPORT: none                                       *
     *EXPORT: none                                       *
     *ASSERTION: add a new item at the tail of the queue *
     *****************************************************/
    public void enqueue(Object data)
    {
        if(counter < array.length)
        {
            array[counter] = data;
            counter++;
        }
        else
        {
            System.out.println("[Full] enqueue not possible");
        }
       
        if(head == null) //if head is still empty
        {
            head = array[headIndex];
        }
    }





    
    /*******************************************************
     *SUBMODULE: dequeue                                   *
     *IMPORT: none                                         * 
     *EXPORT: none                                         *
     *ASSERTION: remove and shift the head index up by one * 
     *******************************************************/
    public void dequeue()
    {
        if(dequeuedCounter < counter - 1)
        {
            dequeuedValue = array[dequeuedCounter];
            array[dequeuedCounter] = null;
            head = array[dequeuedCounter + 1];
            dequeuedCounter++;//new
        }
        else if(dequeuedCounter == counter - 1)
        {
            dequeuedValue = array[dequeuedCounter];//new
            array[dequeuedCounter] = null;
            head = array[dequeuedCounter];
            dequeuedCounter++; //new
        }
        headIndex++;
    }






    /************************************************
     *SUBMODULE: peek                               *
     *IMPORT: none                                  *
     *EXPORT: head (Object)                         *
     *ASSERTION: just peek at the head/front item   *
     ************************************************/
    public Object peek()
    {
        return head;
    }






    /************************************************
     *SUBMODULE: isEmpty                            *
     *IMPORT: none                                  *
     *EXPORT: empty (boolean)                       *
     *ASSERTION: check if queue is empty            *
     ************************************************/
    public boolean isEmpty()
    {
        boolean empty;
        empty = false;

        for(int i = 0; i < array.length; i++)
        {
            if(array[i] == null)
            {
                empty = true;
            }
            else if(array[i] != null)
            {
                empty = false;
            }
        }
        return empty;
    }






    /************************************************
     *SUBMODULE: isFull                             *
     *IMPORT: none                                  *
     *EXPORT: full (boolean)                        *
     *ASSERTION: check if queue is full             *
     ************************************************/
    public boolean isFull()
    {
        boolean full;
        full = false;
        count = 0;
      
        for(int i = 0; i < array.length; i++)
        {
            if(array[i] != null)
            {
                full = true;
                count++; //number of elements in array
            }
            if(count != array.length) //means not full
            {
               full = false;
            }
        }
        return full;
    }






    /************************************************
     *SUBMODULE: getCount                           *
     *IMPORT: none                                  *
     *EXPORT: numOfElements (integer)               *
     *ASSERTION: return number of elements          *
     ************************************************/
    public int getCount()
    {
        int noOfElements; 
        noOfElements = 0;
        if(isEmpty())
        {
            noOfElements = 0;
        }
        for(int i = 0; i < array.length; i++)
        {
            if(array[i] != null)
            {
                 noOfElements++;
            }
        }
        return noOfElements;
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
     *SUBMODULE: getDequeuedValue                   *
     *IMPORT: none                                  *
     *EXPORT: dequeuedValue (Object)                *
     *ASSERTION: return most recent dequeued value  *
     ************************************************/
    public Object getDequeuedValue()
    {
        return dequeuedValue;
    }
}
