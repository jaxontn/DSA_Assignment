//FILE: DSAGraph.java
//AUTHOR: Jason Tan Thong Shen
//UNIT: DATA STRUCTURES AND ALGORITHMS
//PURPOSE: to develop a graph that uses adjacency list and stores all
//         data related to the assignment
//REFERENCE: lecture slides & GRAPH LAB 2020
//REQUIRES: DSALinkedList.java to function
//Last Mod: 20th MAY 2021
//REMARKS: THIS IS MY GRAPH LAB PRACTICAL FROM SEM 2, 2020. IT HAS
//         NOW BEEN MODIFIED TO FIT THE ASSIGNMENT REQUIREMENTS
import java.util.*;
import java.io.*;


public class DSAGraph implements Serializable
{
    //private constants 
    private final int LOCATION_FILE = 2694;
    private final int INVENTORY_FILE = 8242;
    private final int PRODUCT_FILE = 4624;
    private final int ORDER_FILE = 8892;
    private final int SHOW_EDGE = 1389;
    private final int HEADER = 1;
    private final int NONE = -1;
    private final int HEAD = 3975;
    private final int LINE_ONE = 1;
    private final int LINE_TWO = 2;
    private final int LINE_THREE = 3;
    private final int LINE_FOUR = 4;
    
    
    //private class field
    private DSALinkedList vertices; //this is the vetices linked list
    private int edgeCount;
    private int vertexCount;
    private Object[] product; //product's name or ID
    private Object[] description; //description of the product
    private Object[] oneAbove; //represents Qty1+
    private Object[] tenAbove; //represents Qty10+
    private Object[] twtyFiveAbove; //represents Qty25+
    private String date;
    private String contact;
    private String address;
    private Object[][] orders; //contains product name and number of items
                               //the order requested.
    private int indexCounter; //for the order file only





    //Default constructor
    public DSAGraph()
    {
         vertices = null; 
         edgeCount = 0;
         orders = null;   
    }





    //NAME: readFile
    //PURPOSE: to read a file that contains the data
    //IMPORTS: inFileName (String), option (Integer)
    //EXPORTS: none
    //    PRE: method is called, read file based on option
    //    POST: process file
    //REMARKS:
    public void readFile( String inFileName, int option )
    {
        FileInputStream fileStream = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        int lineNum;
        String line;
        indexCounter = -1; //for the order files only.
 
        if( option == LOCATION_FILE )
        {
            vertices = new DSALinkedList(); //means new graph vertices
                                            //for new location file
            edgeCount = 0; //ONLY FOR THE LOCATION FILE
        }
        else if( option == ORDER_FILE )
        {
            orders = new Object[999][999];
        }
        try
        {
            fileStream = new FileInputStream( inFileName );
            rdr = new InputStreamReader( fileStream );
            bufRdr = new BufferedReader( rdr );
            lineNum = 0;
            line = bufRdr.readLine();
            
            if( option == PRODUCT_FILE )
            { //always intialise new array here when reading new 
              //catalogue file
                product = new Object[999];
                description = new Object[999];
                oneAbove = new Object[999];
                tenAbove = new Object[999];
                twtyFiveAbove = new Object[999];
            }

            while( line != null )
            {
                lineNum++;
                //without header
                if( option == LOCATION_FILE || option == ORDER_FILE ) 
                {  //for location file
                    processLine( line, option, lineNum );
                }
                else if( option == INVENTORY_FILE || 
                         option == PRODUCT_FILE )//with header
                {//for inventory file OR product file
                    if( lineNum != HEADER ) //exclude header in file
                    {
                        processLine( line, option, lineNum );
                    }
                }
                line = bufRdr.readLine();
            }
            fileStream.close();
        }
        catch( IOException e )
        {
            if( fileStream != null )
            {
                try
                {
                    fileStream.close();
                }
                catch( IOException ex2 )
                {}
            }
            System.out.println("[Error in file processing: " +
                                e.getMessage() + "]");
        }
    }






    //NAME: processLine
    //PURPOSE: to split the data from file into separate chunks based on
    //         the option received
    //IMPORTS: row (String), option (Integer), lineNum (Integer)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: receive the row and split it according to the option
    //    POST: data is then stored into this program
    //REMARKS:
    private void processLine( String row, int option, int lineNum )
    {
        try
        {
            String[] splitLine;
   
            //IF LOCATION FILE------------------------------------
            if( option == LOCATION_FILE )
            {
                splitLine = row.split(",");
                addVertex( splitLine[0] ); //adding 
                addVertex( splitLine[1]);

                //below is adding edges for undirected graph
                addEdge( splitLine[0], splitLine[1], splitLine[2] );
            }
            else if( option == INVENTORY_FILE )//IF INVENTORY FILE---
            {   
                splitLine = row.split(",");   
                DSAGraphVertex graphVertex;
                //1. find the correct vertex
                graphVertex = vertices.getVertexAddress( splitLine[0] );
                //2. add inventory into currenct vertex
                graphVertex.addInventories( splitLine[1] );
                //3. add the stocks on hand for that currenct inventory
                graphVertex.addStocks( splitLine[2] );
            
            }
            else if( option == PRODUCT_FILE )//IF PRODUCT FILE---
            {
                splitLine = row.split(":");
                categorizeProduct(splitLine[0], splitLine[1], 
                                  splitLine[2], lineNum );
            }
            else if( option == ORDER_FILE )//IF ORDER FILE---
            {
                //line 1 is for "Date"
                if( lineNum == LINE_ONE )
                {
                    date = row; 
                    System.out.println(date);
                }
                else if( lineNum == LINE_TWO )//line 2 is for "Contact"
                {
                    contact = row;
                    System.out.println(contact);
                }
                else if( lineNum == LINE_THREE )//line 3 is for "Address"
                {
                    address = row;
                    System.out.println(address);
                }
                else if( lineNum >= LINE_FOUR )
                {//line 4 etc is for "Product" & "number of prod. to buy"
                    splitLine = row.split(",");
                    indexCounter++;
                    orders[indexCounter][0] = splitLine[0];//product
                    orders[indexCounter][1] = splitLine[1];//number of item
                }
            }
        }
        catch( NullPointerException e )
        {
            System.out.println("[ERROR: Your file data format might " +
                               "be missing a few things]: " + 
                                e.getMessage());
            System.out.println("[PROGRAM TERMINATED: I am sensitive]");
            System.out.println("[Insert Correct Data File with CARE]");
            System.out.println("\nCOMMON MISTAKES:");
            System.out.println("1. You inserted a wrong inventory file" +
                               " for your inserted location file\nOR");
            System.out.println("2. You updated to a different inventory"
                               + " file that doesn't match correctly\n" +
                               "with your location vertices\nOR");
            System.out.println("3. You haven't loaded your location " +
                               "file");
            System.exit(0);
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
            System.out.println("[ERROR: Your file data format might " +
                               "be missing a few things]: " + 
                                e.getMessage());
            System.out.println("[PROGRAM TERMINATED: I am sensitive]");
            System.out.println("[Insert Correct Data File with CARE]");
            System.out.println("[You most likely entered an incorrect" +
                               " product/catalogue OR order file]");
            System.exit(0);
        }
    }
    
    
    
    
    
    
    //for catalogue
    //NAME: catagorizeProduct
    //PURPOSE: to catagories the product name, decsription, QTY1+, QTY10+
    //         QTY25+ into their arrays
    //IMPORTS: theProduct (Object), theDesc (Object), theQTYs (String),
    //         lineNum (Integer)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called, receivces the parameters
    //    POST: insert the parameters into disfferent arrays that are
    //          correct
    //REMARKS:
    public void categorizeProduct( Object theProduct, Object theDesc, 
                                   String theQTYs, int lineNum )
    {
        String[] splitLine;
        
        int index = lineNum - 1;
        
        product[index] = theProduct; //product name
        description[index] = theDesc; //product description
         
        splitLine = theQTYs.split(" ");
        
        //taking into account missing prices
        if( splitLine.length == 1 )
        {
            oneAbove[index] = splitLine[0];
        }
        else if( splitLine.length == 2 )
        {
            oneAbove[index] = splitLine[0];
            tenAbove[index] = splitLine[1];
        }
        else if( splitLine.length == 3 )
        {
            oneAbove[index] = splitLine[0];
            tenAbove[index] = splitLine[1];
            twtyFiveAbove[index] = splitLine[2];
        }
    }







    //NAME: getProduct
    //PURPOSE: to get the product
    //IMPORTS: none
    //EXPORTS: product (Object 1D Array)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the product array
    //REMARKS:
    public Object[] getProduct()
    {
        return product;
    }
    
    
    
    
    
    
    
    //NAME: getDesc
    //PURPOSE: to get the Description
    //IMPORTS: none 
    //EXPORTS: description (Object 1D Array)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the description array
    //REMARKS:
    public Object[] getDesc()
    {
        return description;
    }
    
    
    
    
    
    
    
    //NAME: getOneAbove
    //PURPOSE: to get the oneAbove
    //IMPORTS: none
    //EXPORTS: oneAbove (Object 1D Array)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the oneAbove array
    //REMARKS:
    public Object[] getOneAbove()
    {
        return oneAbove;
    }
    
    
    
    
    
    
    
    //NAME: getTenAbove
    //PURPOSE: to get the tenAbove
    //IMPORTS: none 
    //EXPORTS: tenAbove (Object 1D Array)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the tenAbove array
    //REMARKS:
    public Object[] getTenAbove()
    {
        return tenAbove;
    }
    
    
    
    
    
    
    
    //NAME: getTwtyFiveAbove
    //PURPOSE: to get the twtyFiveAbove
    //IMPORTS: none
    //EXPORTS: twtyFiveAbove (Object 1D Array)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: return the twtyFiveAbove array
    //REMARKS:
    public Object[] getTwtyFiveAbove()
    {
        return twtyFiveAbove;
    }







    
    //important for the order data 
    //NAME: getOrders
    //PURPOSE: to get the orders
    //IMPORTS: none
    //EXPORTS: orders (Object 2D Array)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the orders Array
    //REMARKS:
    public Object[][] getOrders()
    {
        return orders;
    }






    //NAME: addVertex
    //PURPOSE: to add a vertex/node one at a time into the linkedlist
    //IMPORTS: label (Object)
    //EXPORTS: none
    //ASSERTION:
    //    PRE: method is called, it receives the label.
    //    POST: the label is called to DSALinkedList
    //REMARKS:
    public void addVertex( Object inLabel )
    {
        vertices.insertLast( inLabel );
    }






    //NAME: displayVertices
    //PURPOSE: to display all the vertices in the graph
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: calls the method from DSALinkedList
    //REMARKS:
    public void displayVertices()
    {
        vertices.displayVertex();
    }






    //NAME: addEdge
    //PURPOSE: to add an edge between two vertices and also store the
    //         distances between the edges
    //IMPORTS: labelOne (Object), labelTwo (Object), distance (Object)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE:  get the address of the each vertex's DSAGraphVertex 
    //    POST: add edge with DSAGraphVertex of each one and add the
    //          distance
    //REMARKS:
    public void addEdge( Object labelOne, Object labelTwo, 
                         Object distance )
    {
        DSAGraphVertex vOne;
        DSAGraphVertex vTwo;

        vOne = vertices.getVertexAddress( labelOne ); 
        //returns DSAGraphVertex
        vTwo = vertices.getVertexAddress( labelTwo ); 
        //return DSAGraphVertex

        vOne.addEdge( vTwo );
        vOne.addEdgeDistance( distance ); //adding distance
        vTwo.addEdge( vOne );
        vTwo.addEdgeDistance( distance ); //adding distance

        edgeCount++;
    }






    //NAME: getEdgeCount
    //PURPOSE: get the edgeCount
    //IMPORTS: none
    //EXPORTS: edgeCount (Integer)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the edgeCount
    //REMARKS:
    public int getEdgeCount()
    {
        return edgeCount;
    }
    
    
    
    
    
    
    
    //NAME: setVertexCount
    //PURPOSE: to set the vertex count to know how many vertex
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: calls a method from DSALinkedList to set the number of
    //          vertex
    //REMARKS:
    public void setVertexCount()
    {
        vertices.setVertexCount();
    }
    
    
    
    
    
    
    
    //NAME: getVertexCount
    //PURPOSE: to get the vertex count
    //IMPORTS: none
    //EXPORTS: vertices.getVertexCount() (Integer)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the vertex count gotten from the DSALinkedList
    //REMARKS:
    public int getVertexCount()
    {
        return vertices.getVertexCount();
    }
    
    
    
    
    
    
    
    //NAME: getVertices
    //PURPOSE: to get the vertices 
    //IMPORTS: none
    //EXPORTS: vertices (DSALinkedList)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: returns the vertices
    //REMARKS:
    public DSALinkedList getVertices()
    {
        return vertices;
    }






    //NAME: displayEdgeOf
    //PURPOSE: to display the edges of the specified vertex
    //IMPORTS: label (Object)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: gets the DSAGraphVertex address of the label
    //    POST: use it to get the edges for that vertex and display
    //          the edges
    //REMARKS:
    public void displayEdgeOf( Object label )
    {
        DSAGraphVertex vertex;
        DSALinkedList edgeList; 

        vertex = vertices.getVertexAddress( label );
        edgeList = vertices.getEdgeOf( vertex, vertices );
        edgeList.loopThroughNSetEdge( vertices, SHOW_EDGE );
        //SHOW_EDGE means want to show the edges
    }







    //NAME: findDistance
    //PURPOSE: to find the distances between two locaitons
    //IMPORTS: startPoint (Object), destination (Object)
    //EXPORTS: totalDistance (Double)
    //ASSERTIONS:
    //    PRE: method receives the parameters
    //    POST: find the distance
    //REMARKS:
    public double findDistance( Object startPoint , Object destination )
    {
        DSAGraphVertex vertex;
        DSALinkedList edgeList;
        Object[] edgesArray;
        Object[] edgeDistanceArray;
        boolean foundDestination;
        double totalDistance;
        
        vertex = vertices.getVertexAddress( startPoint );
        edgeList = vertices.getEdgeOf( vertex, vertices );
        edgeList.loopThroughNSetEdge( vertices, NONE );
        edgesArray = edgeList.getEdgesFound();
        
        totalDistance = 0;
        foundDestination = false;
        //1. search the starting starting point's edge list for the
        //   destination point.
        if( edgesArray != null )
        { 
            for( int i = 0; i < edgesArray.length; i++ )
            {
                if( destination.equals(edgesArray[i]) )//found edge
                {
                    //now, find the distance by the getting the 
                    //edgeDistance array from DSAGraphVertex under 
                    //startpoints DSAGraphVertex.
                    edgeDistanceArray = vertex.getEdgeDistance();
                    totalDistance = 
                          Double.parseDouble((String)edgeDistanceArray[i]);
                    foundDestination = true;
                }
            }
        
            if( !foundDestination ) //WANTED TO DO THIS, BUT TIME LIMITED
            {
                System.out.println("[Sorry, I can't hop between " +
                                   "multiple locations]");
                System.out.println("[I can do neighbouring distance " +
                                   "for you]");
                System.out.println("OR, your location input might not" +
                                   " be available\n");
            }
        }
        else
        {
            System.out.println("Your starting point doesn't have " +
                               "edges");
        }
        return totalDistance;
    }







    //NAME: collectOrder
    //PURPOSE: to find the route at the same time collect order at
    //         each location
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: call a BFS method to iterate through the route
    //REMARKS:
    public void collectOrder()
    {
        //1. iterate through the graph using BFS
        breathFirstSearch(); //return the array
        
        //2. In the BFS, for each vertex, find the product, can be
        //   more than one, and the substract the stock number.
        //   a) after that, collected[product][location] array
        //the collected array will show where the product is collected.
        
        //3. displays the collected array, and uncollected products.
        
    }







    //NAME: displayEdgeDistanceOf
    //PURPOSE: to edge distance of a location (label)
    //IMPORTS: label (Object)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: find the label DSAGraphVertex. use it to find the label's
    //         edge array
    //    POST: display the edges
    //REMARKS:
    public void displayEdgeDistanceOf( Object label )
    {
        DSAGraphVertex vertex;
        Object[] edgeDistanceArray;

        vertex = vertices.getVertexAddress( label );
        edgeDistanceArray = vertex.getEdgeDistance();     
        
        for( int i = 0; i < edgeDistanceArray.length; i++ )
        {
            if( edgeDistanceArray[i] != null )
            {
                System.out.print("[" + edgeDistanceArray[i] + "] ");
            }
        }
        System.out.println();
    }







    //NAME: displayInventoryNStockOf
    //PURPOSE: to display the inventories details gotten from inventory
    //         file for a specific location (label)
    //IMPORTS: label (Object)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: getitng the stocks and inventory product from the 
    //         DSAGraphVertex of the location
    //    POST: displays the inventory products and stocks
    //REMARKS:
    public void displayInventoryNStockOf( Object label )
    {
        DSAGraphVertex vertex;
        Object[] inventoriesArray;
        Object[] stocksArray;

        vertex = vertices.getVertexAddress( label );
        inventoriesArray = vertex.getInventories();
        stocksArray = vertex.getStocks();     
        
        System.out.println("INVENTORY\tSTOCK ON HAND");
        System.out.println("---------\t-------------");
        for( int i = 0; i < inventoriesArray.length; i++ )
        {
            if( inventoriesArray[i] != null )
            {
                System.out.println(inventoriesArray[i] + "\t" +
                                   stocksArray[i]);
            }
        }
    }






    //NAME: breathFirstSearch
    //PURPOSE: to search the graph in breath-first (level by level)
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called to do the breath-first search operation
    //    POST: calls the method resposible from DSALinkedList
    //REMARKS:
    public void breathFirstSearch()
    {//specially for find route and collecting the orders at 
     //correct locations
        vertices.searchBreathFirst( vertices, getOrders() );
    }





    //NAME: depthFirstSearch
    //PURPOSE: to search the graph's depth first (going in-depth)
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called to do the depth-first search operation
    //    POST: calls the method responsible from DSALinkedList
    //REMARKS:
    public void depthFirstSearch()
    {
        vertices.searchDepthFirst( vertices );
    }
}
