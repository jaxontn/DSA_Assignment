//FILE: DSAGraphVertex.java
//AUTHOR: Jason Tan Thong Shen
//UNIT: DATA STRUCTURES AND ALGORITHMS
//PURPOSE: to store the all thre reuqired data for each vertex
//REFERENCE: lecture slides & GRAPH LAB 2020
//REQUIRES: DSALinkedList.java
//Last Mod: 20TH MAY 2021
//REMARKS: THIS IS MY GRAPH LAB PRACTICAL FROM SEM 2, 2020, AND HAS BEEN
//         MODIFIED FOR THE ASSIGNMENT REQUIREMENTS

import java.util.*;
import java.io.*;

public class DSAGraphVertex implements Serializable
{
    //private classfields
    private Object label;
    private DSALinkedList edgeList; //store the edges
    private Object[] edgeDistances; //store the each edges's distances
    private Object[] inventories; //stores the inventories
    private Object[] stocks; //stores the stocks
    private int distCount;
    private int inventoryCount;
    private int stockCount;


    //default constructor
    public DSAGraphVertex( Object inLabel )
    {
        label = inLabel;
        edgeList = new DSALinkedList();
        edgeDistances = new Object[999]; //size of 999 is the maximum
        inventories = new Object[999];
        stocks = new Object[999];
        distCount = -1;
        inventoryCount = -1;
        stockCount = -1;
    }






    //NAME: getLabel
    //PURPOSE: to get the label from DSAGraphVertex
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called.
    //    POST: returns the data in Object.
    //REMARKS:
    public Object getLabel()
    {
        return label;
    }






    //NAME: addEdge
    //PURPOSE: to add the edge, the vertex is the edge of the current node
    //IMPORTS: vertex (DSAGraphVertex)
    //EXPORTS: none
    //ASSERITONS:
    //    PRE: method is called
    //    POST: insert the vertex using DSALinkedList
    //REMARKS:
    public void addEdge( DSAGraphVertex vertex )
    {
        edgeList.insertLast( vertex ); //storing address of DSAGraphVertex
                                       //in the linkedList
    }







    //NAME: addEdgeDistance
    //PURPOSE: adding the edge distance for the current index
    //IMPORTS: distance (Object)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: increment the disCount for the current index
    //    POST: set the current location edgeDistance with the distance
    //REMARKS:
    public void addEdgeDistance( Object distance )
    {
        distCount++;
        edgeDistances[distCount] = distance;
    }







    //NAME: addInventories
    //PURPOSE: to add the inventories for the current location
    //IMPORTS: product (Object)
    //EXPORTS: none
    //ASSERITONS:
    //    PRE: recevies the product 
    //    POST: add the product into the inventories array
    //REMARKS:
    public void addInventories( Object product )
    { 
        inventoryCount++;
        inventories[inventoryCount] = product;
    }
    
    
    
    
    
    
    
    //NAME: addStocks
    //PURPOSE: to add the stock number for the inventories index
    //IMPORTS: stock (Objecy)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: receives the stock data
    //    POST: add into the stocks array
    //REMARKS:
    public void addStocks( Object stock )
    {
        stockCount++;
        stocks[stockCount] = stock;
    }
    
    
    
    
    
    
    
    //NAME: inventoryEmpty
    //PURPOSE: to check if the inventory is empty
    //IMPORTS: none
    //EXPORTS: empty (Boolean)
    //ASSERTIONS:
    //    PRE: loop through the inventories array to find any data
    //    POST: if empty the variable remains false
    //REMARKS:
    public boolean inventoryEmpty()
    {
        boolean empty = true;
        for( int i = 0; i < inventories.length; i++ )
        {
            if( inventories[i] != null )
            {
                empty = false;
            }
        }
        return empty;
    }








    //NAME: getEdgeList
    //PURPOSE: to get the LL of edge list
    //IMPORTS: none
    //EXPORTS: edgeList(DSALinkedList)
    //ASSERTIONS:
    //    PRE: method is called, get the data
    //    POST: returns the data in DSALinkList format
    //REMARKS:
    public DSALinkedList getEdgeList()
    {
        return edgeList;
    }







    //NAME: getEdgeDistance
    //PURPOSE: to get the edge distance 
    //IMPORTS: none
    //EXPORTS: edgeDistance (Object 1D Array)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the edgeDistances array
    //REMARKS:
    public Object[] getEdgeDistance()
    {
        return edgeDistances;
    }







    //NAME: getInventories
    //PURPOSE:  to get the inventories
    //IMPORTS: none
    //EXPORTS: inventories (Object 1D Array)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the inventories Array
    //REMARKS:
    public Object[] getInventories()
    {
        return inventories;
    }
    
    
    
    
    
    
    
    //NAME: getStocks
    //PURPOSE: to get the stocks
    //IMPORTS: none
    //EXPORTS: stocks (Object 1D Array)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the stocks Array
    //REMARKS:
    public Object[] getStocks()
    {
        return stocks;
    }
}
