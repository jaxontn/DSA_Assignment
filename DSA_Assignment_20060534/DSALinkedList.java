//FILE: DSALinkedList.java
//AUTHOR: Jason Tan Thong Shen
//UNIT: DATA STRUCTURES AND ALGORITHMS
//PURPOSE: to develop an algorihtm for double-ended, doubly linked list
//REFERENCE: lecture slides & GRAPH LAB 2020
//COMMENTS: this algorithm is quite long
//REQUIRES: DSAQueue.java, DSAStack.java
//Last Mod: 20TH MAY 2021
//REMARKS: THIS IS MY GRAPH LAB PRACTICAL FROM SEM 2, 2020, AND HAS
//         BEEN MODIFIED TO FIT THE ASSIGNMENT REQUIREMENTS

import java.util.*;
import java.io.*;

public class DSALinkedList implements Iterable, Serializable 
{                               //^^^^^^^^^^^^
                                //so for-each loop can be used. Only 
                                //defies iterator() method
    //private constants
    private final int SHOW_EDGE = 1389;
    private final int NONE = -1;
    private final int HEAD = 3975;
    
    //private class field
    private DSAListNode head; 
    private DSAListNode currNd;
    private DSAListNode prevNd;
    private DSAListNode tail;
    private Object[] edgesFound;
    private int counter; //for edgesFound
    private int numOfEdgesIndex;
    private int vertexCount;
    private Object[][] collected;
    private Object[][] orderArray;

    //Default Constructor 
    public DSALinkedList()
    {
        head = null;
        tail = null;
        currNd = null;
        prevNd = null;
        vertexCount = 0;
    }






    //NAME: iterator 
    //PURPOSE: return a new Iterator of internal type DSALinkedList
    //IMPORTS: none
    //EXPORTS: DSALinkedListIterator (Iterator)
    public Iterator iterator() //return a new Iterator of internal type
    {                          //DSALinkedListIterator
        return new DSALinkedListIterator(this);
        //Hook the iterator to this DSALinkedList Object
    }






    //NAME: insertFirst
    //PURPOSE: to insert a new listnode to the front of the list after 
    //         the linkedlist
    //IMPORTS: newValue (Object)
    //EXPORTS: none
    //ASSERTIONS:
    //   PRE: creates an object of DSAListNode for the new listnode, with
    //        its own value
    //   POST: makes the head of the linked list points to the new 
    //         listnode
    //REMARKS:
    public void insertFirst( Object newValue )
    {
        DSAListNode newNd = new DSAListNode( newValue );
        
        if( isEmpty() ) //when there is no list node
        {        
            head = newNd; //head will pont to the new list node
            tail = newNd; //-----
        }
        else
        {
            newNd.setNext( head ); //ask the new list node to point to the
                                     //old list node using the next
                                     //so, the next != null
            head.setPrev( newNd ); //------------
  
            head = newNd; //head will point to the new list node
        }
    }






    //NAME: insertLast
    //PURPOSE: to insert a new listnode at the last of the list after the
    //         last node before.
    //IMPORTS: newValue (Object)
    //EXPORTS: none
    //ASSERTIONS:
    //   PRE: creates an object of DSAListNOde for the new listnode, with
    //        its own value
    //   POST: makes the previous last's list points to the new listnode
    //REMARKS:
    public void insertLast( Object newValue )
    {//AVOIDING DUPLICATE 
        int duplicate = 0;

        if( isEmpty() ) //when there is no list node
        {
            DSAListNode newNd = new DSAListNode( newValue );
            head = newNd; //head will pont to the new list node
            tail = newNd;//----
        }
        else
        {
            currNd = head;
            if(currNd.getLabelVal().equals(newValue) )
            { //for first  node/vertex
                duplicate++; //means have duplicate
            }

            while( currNd.next != null ) //loop through the linked list
            {                           
                currNd = currNd.next;   
                                                     
                if(currNd.getLabelVal().equals(newValue) )
                { //for the rest of the node/vertex
                    duplicate++; //means have duplicate
                }           
            }
            if( duplicate == 0 ) //means no duplicate
            {
                DSAListNode newNd = new DSAListNode( newValue );
                currNd = head; //meaning the current node will be 
                               //the first list node.
                //we are going to loop through each lsit node from 
                //first to last until we encounter a null
                while( currNd.next != null )
                {
                    currNd = currNd.next; //move the current node to the 
                                          //next node after each loop
                }
                currNd.setNext( newNd ); //sets the last listnode to point
                                         //to the new node
                newNd.setPrev( currNd ); //-------------

                tail = newNd;//------
            }
        }
    }






    //NAME: displayVertex
    //PURPOSE: it will display all the vertex/node in the graph 
    //         (no duplicates of vertices)
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called, it gets the current node/vertex's label
    //         value
    //    POST: it prints out the data onto the terminal
    public void displayVertex()
    {
        currNd = head;
        System.out.print(currNd.getLabelVal() + " ");
        while( currNd.next != null )
        {
            currNd = currNd.next;
            System.out.print(currNd.getLabelVal() + " ");
        }
        System.out.println();
    }
    
    
    
    
    
    
    //NAME: setVertexCount
    //PURPOSE: to count the number of vertices in the linked list
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE:  method is called
    //    POST: counts the vertices by looping through the linked list
    //REMARKS:
    public void setVertexCount()
    {
        if( !isEmpty() )
        {
            currNd = head;
            vertexCount++;
            while( currNd.next != null )
            {
                 vertexCount++;
                currNd = currNd.next;
            }
        }
    }
    
    
    
    
    
    
    //NAME: getVertexCount
    //PURPOSE: get the number of vertex
    //IMPORTS: none
    //EXPORTS: vertexCount
    //ASSERTIONS:
    //     PRE: method is called
    //     POST: returns the vertexCount
    //REMARKS:
    public int getVertexCount()
    {
        return vertexCount;
    }






    //NAME: getVertexAddress
    //PURPOSE: to get the address of the vertex that im looking for
    //IMPORTS: label (Object)
    //EXPORTS: returnGraph (DSAGraphVertex)
    //ASSERTIONS:
    //    PRE: if the label matches the current label, the address of the
    //         label will be returned to caller.
    //    POST: 
    //REMARKS:
    public DSAGraphVertex getVertexAddress( Object label )
    {
        DSAGraphVertex returnGraph;
        currNd = head;
        
        returnGraph = null;
        if( currNd.getLabelVal().equals(label) ) //for first node
        {
            returnGraph = currNd.getGraphVertex();
        }
        else //for rest of the node
        {
            while( currNd.next != null )
            {
                currNd = currNd.next;

                if( currNd.getLabelVal().equals(label) )
                {
                    returnGraph = currNd.getGraphVertex();
                }
            }
        }
        return returnGraph;
    }






    //NAME: getEdgeOf
    //PURPOSE: to get the the edges of the sepcified vertex
    //IMPORTS: vertex (DSAGraphVertex), vertices (DSALinkedList)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: a linked list is received, an address of vertex's 
    //         DSAGraphVertex is also received.
    //    POST: returns the edgeList
    //REMARKS:
    public DSALinkedList getEdgeOf( DSAGraphVertex vertex, 
                                    DSALinkedList vertices )
    {
        DSALinkedList edgeList;
        edgeList = vertex.getEdgeList(); //getting edge list from
                                         //DSAGraphVertex class
        return edgeList;
    }






    //NAME: loopThroughNSetEdge
    //PURPOSE: it will display all vertices from the edge list provided
    //IMPORTS: vertices (DSALinkedList), option (Integer)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: it gets the call another method to match the addresses to
    //         get the label value
    //    POST: it prints out the label/vertex onto the terminal
    //REMARKS: only for edge list
    public void loopThroughNSetEdge( DSALinkedList vertices, int option )
    {
        Object label;
        edgesFound = new Object[9999]; 
        currNd = head; //head of the edgeList
        counter = 0;
        label = vertices.getLabel( (DSAGraphVertex)currNd.getLabelVal(), 
                                    counter );
        setEdgesFound( label, counter );
        
        if( option == SHOW_EDGE )
        {
            System.out.println("[" + counter + "] : " + 
                                edgesFound[counter]);
        }
        while( currNd.next != null )
        {
            currNd = currNd.next;
            counter++; //new
            label =                
            vertices.getLabel( (DSAGraphVertex)currNd.getLabelVal(),
                               counter );
            setEdgesFound( label, counter );
            
            if( option == SHOW_EDGE )
            {
                System.out.println("[" + counter + "] : " + 
                                   edgesFound[counter]);
            }
        }
    }






    //NAME: getLabel
    //PURPOSE: get the label in Object data type from the labelAdress
    //IMPORTS: labelAddress (DSAGraphVertex), counter (Integer)
    //EXPORTS: label (Object)
    //ASSERTIONS:
    //    PRE: gets the labelAddress, then look for the label with that
    //         address from the linkedlist associated to it.
    //    POST: returns the label back to the caller
    //REMARKS:
    public Object getLabel( DSAGraphVertex labelAddress, int counter )
    {                              
        Object label;
        currNd = head;     
                                                                 
        label = null;                                      
        if( currNd.value.equals(labelAddress) ) //for first node
        {                                         
            label = currNd.getLabelVal();
        }                               
        else //for rest of the node     
        {                                                 
            while( currNd.next != null )                  
            {                                             
                currNd = currNd.next;                     
                                                          
                if( currNd.value.equals(labelAddress) )  
                {                                         
                    label = currNd.getLabelVal();
                }
            }
        }
        return label;
     }
     
     
     
     
     
     
     
     //NAME: setEdgesFound
     //PURPOSE: set the location (label) into the edgesFound array
     //IMPORTS: label (Object), counter (Integer)
     //EXPORTS: none
     //ASSERTIONS:
     //    PRE: set the index as counter and set the edgesFound array
     //    POST:
     //REMAKRS:
     public void setEdgesFound( Object label, int counter )
     {
         edgesFound[counter] = label;
         numOfEdgesIndex = counter;
     }
     
     
     
     
     
     
     //NAME: getEdgesFound
     //PURPOSE: to get the edgesFound array
     //IMPORT: none
     //EXPORTS: edgesFound (Object 1D Array)
     //ASSERTIONS:
     //    PRE: method is called
     //    POST: returns the edgesFound array
     public Object[] getEdgesFound()
     {
         return edgesFound;
     }






    //NAME: searchBreathFirst 
    //PURPOSE: to search level by level (each vertex has its own children)
    //IMPORTS: veritces (DSALinkedList), orders (2D Object Array)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: it receives the linked list of the vertex, &starts from
    //         its head as level 1 all the way until the last level
    //    POST: output the arrangment of the seen LL as the result of the
    //          BFS.
    //REMARKS:
    public void searchBreathFirst( DSALinkedList vertices, 
                                   Object[][] orders )
    {
        //the queue is for the vertices/nodes of the current level
        DSAQueue q = new DSAQueue(9999); //max capcity 9999
        DSALinkedList seen; //as list of seen vertices/nodes
        Object currVertex; //keep track of the current vertex
        DSALinkedList edgeList;
        DSAGraphVertex vertex; //to get address of vertex
        int collectedIndex;
        seen = new DSALinkedList();
        edgeList = null;
        vertex = null;

        orderArray = orders;
        if( orderArray == null )
        {
            System.out.print("\n[Please go to \"load data\" -> ");
            System.out.println("\"Order data\" to load the orders]");
        }
        else if( orderArray != null ) 
        {   
            vertices.currNd = vertices.head;//BFS start from the head 

            q.enqueue( vertices.currNd.getLabelVal() ); 
            //^only for the first node
            while( q.peek() != null )
            {
                q.dequeue(); //2. to dequeue 
                currVertex = q.getDequeuedValue(); 
                //2. set current vertex to the front-most value
                //make sure no duplicate found in seen before insert
                if( !foundDuplicate( seen, currVertex ) )
                {
                    seen.insertLast( currVertex );
                    vertex = getVertexAddress( currVertex );
                    edgeList = getEdgeOf( vertex, vertices );
                    edgeList.loopThroughNSetEdge( vertices, NONE );
                }
                //adding the non-duplicate currVertex's edges to the 
                //queue
                for( int i = 0; i < edgeList.edgesFound.length; i++ )
                {
                    if( edgeList.edgesFound[i] != null )
                    {
                        if( !foundDuplicate( seen,
                            edgeList.edgesFound[i] ) )
                        {
                            q.enqueue( edgeList.edgesFound[i] );
                        }
                    }
                }
            }
            seen.currNd = seen.head;
            System.out.print("TRAVELLED THROUGH NODE(S): ");
            System.out.print(seen.currNd.getLabelVal() + " ");
            collected = new Object[999][2];
            collectedIndex = -1;
            while( seen.currNd.next != null )
            {//find iventories on locations except Home/DroneHome
                seen.currNd = seen.currNd.next;
                System.out.print(seen.currNd.getLabelVal() + " ");
                collectedIndex = findProduct( seen.currNd.getLabelVal(), 
                                              collectedIndex );
            }
            System.out.println();
            displayCollected(); //display the collected items
            System.out.println("\n");
            displayUncollected(); //display uncollected items
        }
    }
    
    
    
    
    
    
    //FOR BFS
    //NAME: findProduct
    //PURPOSE: to find the product in the location's inventory
    //IMPORTS: location (Object), collectedIndex (Integer)
    //EXPORTS: collectedIndex (Integer)
    //ASSERTIONS:
    //    PRE: acquire the locaiton's inventory first
    //    POST: compare and find the order in the location's inventory
    //REMARKS:
    public int findProduct( Object location, int collectedIndex )
    {
        Object[][] inventory;
        inventory = getInventory( location );
        
        //1. COMPARE ORDERS WITH INVENTORY
       for( int i = 0; i < orderArray.length; i++ )
       {
           if( orderArray[i][0] != null )
           {
               for( int j = 0; j < inventory.length; j++ )
               {
                   //2. IF FOUND, add to "collected" 2D Array
                   //   a) set current "orderArray" index to NONE
                   if( inventory[j][0] != null )
                   {
                       if( orderArray[i][0].equals((String)
                                                    inventory[j][0]) )
                       {
                           collectedIndex++;
                           //the product name
                           collected[collectedIndex][0] = 
                                                     orderArray[i][0];
                           //the location to collect product
                           collected[collectedIndex][1] = location;
                           orderArray[i][0] = NONE;
                           orderArray[i][1] = NONE;
                       }
                   }
               }
           }
       }
       return collectedIndex;
    }
    
    
    
    
    
    
    
    //NAME: displayCollected
    //PURPOSE: to display the items collected 
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called and displays the items collected
    //    POST:
    //REMARKS:
    public void displayCollected()
    {
        System.out.println("[COLLECTED]");
        System.out.println("ITEMS\tLOCATION");
        System.out.println("-----\t--------");
        for( int i = 0; i < collected.length; i++ )
        {
            if( collected[i][0] != null )
            {
                System.out.println(collected[i][0] + "\t" + 
                                   collected[i][1]);
            }
        }
    }
    
    
    
    
    
    
    
    //NAME: displayUncollected
    //PURPOSE: to display the uncollected items
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called and displays the uncollected items
    //    POST:
    //REMARKS:
    public void displayUncollected()
    {
        System.out.println("[UNCOLLECTED]");
        System.out.println("ITEMS");
        System.out.println("-----");
        for( int i = 0; i < collected.length; i++ )
        {
            if( orderArray[i][0] != null && 
                !orderArray[i][0].equals(NONE) )
            {
                System.out.println(orderArray[i][0]);
            }
        }
        System.out.println("\n");
    }






    //NAME: foundDuplicate
    //PURPOSE: to find if there are or is any dulplicate to the value or
    //         not
    //IMPORTS: list (DSALinkedList), value (Object)
    //EXPORTS: found (Boolean)
    //ASSERTIONS:
    //    PRE: method is called to check for duplication
    //    POST: and then returns the result in boolean
    //REMARKs:
    public boolean foundDuplicate( DSALinkedList list, Object value )
    {
        boolean found;
        found = false;

        if( list == null )
        {
            found = false; //false because list is empty 
        }
        else //when list is not empty
        {
            list.currNd = list.head;
            while( list.currNd != null ) //while LL is not empty
            {
                if( list.currNd.getLabelVal().equals( value ) )
                {
                    found = true;
                }
                list.currNd = list.currNd.next; //set to next node
            }
        }
        return found;
    }






    //NAME: searchDepthFirst
    //PURPOSE: to search depth of a vertex first until the end and if no
    //         more  children for that vertex, it will move to another
    //         vertex and then to its children, and so on until it reaches
    //         the end of the graph
    //IMPORTS: vertices (DSALinkedList)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: it receives the linked list of the vertex, & starts from
    //         its head as depth 1 all the way until the last depth, then
    //         makes it way back up to search for any other vertices that
    //         needs to be visited and then go into depth with its
    //         children
    //    POST: output the arrangement of the seen LL as the result of the
    //          DFS.
    //REMARKS:
    public void searchDepthFirst( DSALinkedList vertices )
    {
        //this stack is for the vertices/nodes of the current level
        DSAStack s = new DSAStack(9999);//max capacity 9999
        DSALinkedList seen; //as list of seen vertices/nodes
        Object currVertex;

        seen = new DSALinkedList(); //initialize the LL of seen
        vertices.currNd = vertices.head; //may not be the same for Crypto
        //^since the searchDepthFirst starts from head. (edit for crypto)
        s.push( vertices.currNd.getLabelVal() );
        //^only for the first node
        while( !s.isEmpty() )
        {
            s.pop(); // to pop
            currVertex = s.getPopValue(); //to set currVertex to the 
                                          //most recent item on top of
                                          //stack
            edgesFound = new Object[9999];
            //to make sure it is empty array first
            System.out.println("currVertex = " + currVertex);
            
            //make sure no duplicate found in seen before insert
            if( !foundDuplicate( seen, currVertex ) )
            {
                seen.insertLast( currVertex );
            }

            DSAGraphVertex vertex; //to get address of vertex
            vertex = getVertexAddress( currVertex );
            getEdgeOf( vertex, vertices ); //gets the edges of 
                                           //the current vertex
            for( int i = 0; i < edgesFound.length; i++ )
            {
                if( edgesFound[i] != null )
                {
                    if( !foundDuplicate( seen, edgesFound[i] ))
                    {
                        s.push( edgesFound[i] );
                    }
                }
            }
        }
        seen.currNd = seen.head;
        System.out.print("RESULT OF DFS: ");
        System.out.print(seen.currNd.getLabelVal() + " ");
        while( seen.currNd.next != null )
        {
            seen.currNd = seen.currNd.next;
            System.out.print(seen.currNd.getLabelVal() + " ");
        }
        System.out.println();
    }






    //NAME: insertBefore
    //PURPORSE: to insert a new node before a specified node
    //IMPORTS: newValue (Object), valueToFind (Object)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: sets the currNd to tail. It will loop to the left 
    //         (backwards) to find the node with the equivilent value 
    //         to the specified value
    //    POST: insert the new node and change the ".next" & ".next" for
    //          the current node, previous node, and new node.
    //REMARKS:
    public void insertBefore( Object newValue, Object valueToFind )
    {
        DSAListNode newNd = new DSAListNode( newValue );

        currNd = tail;
        if( isEmpty() )
        {
            head = newNd;
            tail = newNd;
        }
        else if( currNd.value == valueToFind ) //for the tail
        {      
            newNd.setNext( currNd );       
            currNd.prev.setNext( newNd );
            newNd.setPrev( currNd.prev );
            currNd.setPrev( newNd );
        }
        else
        {
            while( currNd.prev != null ) //for the rest of the list node
            {
                currNd = currNd.prev;

                if( currNd.value == valueToFind )
                {
                    newNd.setNext( currNd );       
                    currNd.prev.setNext( newNd );
                    newNd.setPrev( currNd.prev );
                    currNd.setPrev( newNd );
                }
            }
        }
    }






    //NAME: isEmpty
    //PURPOSE: to check if the list is empty or not
    //IMPORTS: none
    //EXPORTS: empty (boolean)
    //ASSERTIONS:
    //   PRE: method is called
    //   POST: if head is null, empty will be true, otherwise false
    //REMARKS:
    public boolean isEmpty()
    {
        boolean empty;
        empty = false;

        if( head == null )
        {
            empty = true;
        }
        return empty;
    }






    //NAME: peekFirst
    //PURPOSE: to peek the first listnode 
    //IMPORTS: none
    //EXPORTS: nodeValue (Object)
    //ASSERTIONS:
    //   PRE: checks if its empty or not
    //   POST: if not empty, it will show the first list node value
    //REMARKS:
    public Object peekFirst()
    {
        Object nodeValue;
        nodeValue = null; /*initialiazing first, if not compiler doesnt 
                            accept*/

        if( isEmpty() )
        {
            System.out.println("[Nothing to peek] -> Empty");
        }
        else
        {
            nodeValue = head.value;
        }
        return nodeValue;
    }






    //NAME: peekLast
    //PURPOSE: to peek the last listnode
    //IMPORTS: none
    //EXPORTS: nodeValue (Object)
    //ASSERTIONS:
    //   PRE: checks if its empty ot not
    //   POST: if not empty, it will show the last list node value
    //REMARKS:
    public Object peekLast()  //equivilent to TAIL
    {
        Object nodeValue;
        nodeValue = null; /*initializing first, if not compiler doesnt 
                            accept*/

        if( isEmpty() )
        {
            System.out.println("[Nothing to peek] -> Empty");
        }
        else
        {
            currNd = head;
            while( currNd.next != null ) 
            {  //this the is second last list node
               //of the linked list
                
                currNd = currNd.next; //this will make current node to be
                                      //the last list node of the linked
                                      //linked list
            }
            nodeValue = currNd.value; //this will be the value of the
                                      //last list node that the 2nd last
                                      //list node's "next" is pointing to
            tail = currNd;//-------------
        }
        return nodeValue;
    }






    //NAME: peekAt
    //PURPOSE: to peek starting from the tail.
    //IMPORTS: nodeIndex (Integer)
    //EXPORTS: nodeLabelValue (Object)
    //ASSERTIONS:
    //    PRE: looks for the node's Index starting from 0
    //    POST: if found, returns the nodeLabelValue
    //REMARKS:
    public Object peekAt( int nodeIndex )
    {
        Object nodeLabelValue;
        int counter;
        nodeLabelValue = null;
        counter = -1;

        if( isEmpty() )
        {
            System.out.println("[Nothing to peek] -> Empty");
        }
        else
        {
            currNd = head;
            counter++;
            if( counter == nodeIndex ) //for the head
            {
                nodeLabelValue = currNd.getLabelVal();
            }

            while( currNd.next != null ) //for the rest of the nodes
            {
                currNd = currNd.next;
                counter++;

                if( counter == nodeIndex )
                {
                    nodeLabelValue = currNd.getLabelVal();
                }
            }
        }
        return nodeLabelValue;
    }
    
    
    
    
    
    
    

    //NAME: getListNode
    //PURPOSE: to get the listNode of the location (labelValue)
    //IMPORTS: labelValue (Object)
    //EXPORTS: listNode (DSAListNode)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: search through the LinkedList for the correct listNode
    //REMARKS:
    public DSAListNode getListNode( Object labelValue )
    {
        DSAListNode listNode;
        
        currNd = head;
        listNode = null;
        
        if( currNd.getLabelVal().equals(labelValue))
        {
            listNode = currNd;
        }
        
        while( currNd.next != null )
        {
            currNd = currNd.next;
            
            if( currNd.getLabelVal().equals(labelValue))
            {
                listNode = currNd;
            }
        }
        return listNode;
    }
    
    
    
    
    
    
    

    //NAME: getListNodeIndex
    //PURPOSE: to get the listNode index of the location (labelValue) 
    //IMPORTS: labelValue (Object)
    //EXPORTS: index (Integer)
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: search through the LinkedList for the correct listNode
    //          index
    //REMARKS:
    public int getListNodeIndex( Object labelValue )
    {
        int index;
        DSAListNode listNode;
        
        currNd = head;
        index = -1;
        
        if( currNd.getLabelVal().equals(labelValue))
        {
            listNode = currNd;
            index = 0;
        }
        
        while( currNd.next != null )
        {
            currNd = currNd.next;
            
            if( currNd.getLabelVal().equals(labelValue))
            {
                listNode = currNd;
            }
            index++;
        }
        return index;
    }






    //NAME: removeFirst
    //PURPOSE: to remove the first node of the list 
    //IMPORTS: none
    //EXPORTS: nodeValue (Object)
    //ASSERTIONS:
    //   PRE: check if empty or not
    //   POST: sets the head to point to the next listnode instead of the
    //         first listnode that we want to remove, 7 returns the value
    //         of the removed node.
    //REMARKS:
    public Object removeFirst()
    {
        Object nodeValue;
        nodeValue = null;

        if( isEmpty() )
        {
            System.out.println("[Nothing to Remove] -> Empty");
            tail = null;//-------------------
        }
        else
        {
            nodeValue = head.value; //gets the value of the first node
                                    //before it is being unpointed by
                                    //the head
            head = head.next; //set the head to point to the next node
                              //and not the first node that we want to 
                              //remove
        }
        return nodeValue;
    }






    //NAME: removeLast
    //PURPOSE: to remove the last node of the list 
    //IMPORTS: none
    //EXPORTS: nodeValue (Object)
    //ASSERTIONS:
    //   PRE: check if empty or not, switch the last node to be the 
    //        previous node, and current(last) node to be null after that
    //   POST: returns the value of the removed node.
    //REMARKS:
    public Object removeLast()
    {
        Object nodeValue;
        nodeValue = null;

        if( isEmpty() )
        {
            System.out.println("[Nothing to Remove] -> Empty");
            tail = null;//-----------
        }
        else if( head.next == null ) //meaning it only has one list node
        {
            nodeValue = head.value;
            head = null;
            tail = null;//-------------
        }
        else
        {
            prevNd = null; //must equal to null first to reset the
                           //prevNd used by other methods
            currNd = head;

            while( currNd.next != null ) //loop through the list
            {
                prevNd = currNd; /*the previous node will become the last
                                   node of the list because currNd is
                                   going to be dropped out of the list*/
                currNd = currNd.next; /*the "next" will be a null,
                                        so, currNd will = null. means,
                                        no more currNd*/
            }
            prevNd.setNext( null ); /*remove currNd from the list by
                                      pointing to a null*/
            nodeValue = currNd.value; /*returning the value of the
                                        node that is being removed*/
            currNd.setPrev( null ); //--------------

            tail = prevNd; //----------
        }
        return nodeValue;
    }






    //NAME: removeAt
    //PURPOSE: to remove a node at the specified node's index
    //IMPORTS: nodeIndex (Integer)
    //EXPORTS: nodeValue (Object)
    //ASSERTIONS:
    //    PRE: finds the nodeIndex
    //    POST: remove the node and change the ".next" & ".prev"
    //REMARKS:
    public Object removeAt( int nodeIndex )
    {
        Object nodeValue;
        int counter;
        nodeValue = null;
        counter = -1;

        if( isEmpty() )
        {
            System.out.println("[Nothing to Remove] -> Empty");
            tail = null;//-----------
        }
        else
        {
            currNd = head;
            prevNd = null;
            counter++;
            if( counter == nodeIndex ) //for the head
            {
                nodeValue = currNd.value;
                head = head.next;
            }
            while( currNd.next != null ) //for the rest of the node
            {
                prevNd = currNd;
                currNd = currNd.next;
                counter++;

                if( counter == nodeIndex )
                {
                    nodeValue = currNd.value;
                    prevNd.setNext( currNd.next );

                    if( currNd.next != null )
                    {
                        currNd.next.setPrev( prevNd );
                    }
                    else if( currNd.next == null )
                    {
                        tail = prevNd; //-----
                    }
                }
            }
        }
        return nodeValue;
    }







    //NAME: displayLL
    //PURPOSE: to display the linked list
    //IMPORTS: none
    //EXPORTS: none
    //ASSERITONS:
    //    PRE: method is called
    //    POST: loop through and display the list nodes
    //REMARKS:
    public void displayLL()
    {
        if( isEmpty() )
        {
            System.out.println("[Nothing to view] -> empty");
        }
        else
        {
            currNd = head;
            
            System.out.print("[" + currNd.getLabelVal() + "]");

            while( currNd.next != null )
            {
                currNd = currNd.next;
                System.out.print("[" + currNd.getLabelVal() + "]");
            }       
            System.out.println();
        }
    }
    
    
    
    


    
    
    
    //for each location
    //NAME: displayInventoryOverview
    //PURPOSE: to display the inventory overview for a location
    //IMPORTS: location (Object)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: gets the locations, search for the location's inventory
    //    POST: display the inventories 
    //REMARKS:
    public void displayInventoryOverview( Object location )
    {
        try
        {
            int productNum;
            Object[] inventoryList;
            Object[] stockList;
            DSAGraphVertex graphVertex = getVertexAddress( location );
            System.out.println("\nLocation " + graphVertex.getLabel() + 
                               "'s Inventories are: ");

            inventoryList = graphVertex.getInventories(); //in 1D array
            stockList = graphVertex.getStocks(); //in 1D array

            System.out.println("\nPRODUCT\t\tSTOCKS ON HAND");
            System.out.println("-------\t\t--------------");
        
            productNum = 0;
            for( int i = 0; i < inventoryList.length; i++ )
            {
                if( inventoryList[i] != null )
                {
                    System.out.println(inventoryList[i] + "\t\t" + 
                                       stockList[i]);
                    productNum++;
                }            
            }
            System.out.println("Number of Products: " + 
                                productNum ); 
        }
        catch( NullPointerException e )
        {
            System.out.println("[ERROR: Location might not currently" +
                               " exist in the program]");
            System.out.println("[Try another location]");
        }       
    }






    
    
    
    //FOR BFS
    //NAME: getInventory
    //PURPOSE: creating an array that has inventories and stocks together
    //IMPORTS: location (Object)
    //EXPORTS: inventory (Object 2D Array)
    //ASSERTIONS:
    //    PRE: find the location's inventory and stock and combine them
    //    POST: return the new inventory 2D array back to caller
    //REMARKS:
    public Object[][] getInventory( Object location )
    {
        int length;
        Object[] inventoryList;
        Object[] stockList;
        Object[][] inventory; //[product][stock available]
        inventory = null;
        
        DSAGraphVertex graphVertex = getVertexAddress( location );
        inventoryList = graphVertex.getInventories(); //in 1D array
        stockList = graphVertex.getStocks(); //in 1D array
        
        if( !graphVertex.inventoryEmpty() )
        {
            length = inventoryList.length;
            inventory = new Object[length][2];
        
            for( int i = 0; i < length; i++ )
            {
                if( inventoryList[i] != null )
                {
                    inventory[i][0] = inventoryList[i];
                    inventory[i][1] = stockList[i];
                }
            }
        }
        else if( graphVertex.inventoryEmpty() )
        {
            System.out.print("\n[Please go to \"load" +
                             " data\" -> ");
            System.out.println("\"Inventory data\" to load the" +
                               " Inventories]");
        }
        return inventory;
    }








    //NAME: iterateOverList
    //PURPOSE: to iterate iver the list using iterator
    //IMPORTS: theList (DSALinkedList)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: gets theList
    //    POST: iterate over it while iter.hasNext() is true
    //REMARKS:
    public void iterateOverList( DSALinkedList theList )
    {
        Iterator iter = theList.iterator();
        while(iter.hasNext())
        {
            System.out.println( iter.next() );
        }
    }






    //NAME: save
    //PURPOSE: to save object into a file
    //IMPORTS: objToSave (DSALinkedList), fileName (String)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called, performs the algorithm
    //    POST: a file is created 
    //REMARKS:
    public void save( DSALinkedList objToSave, String fileName )
    {
        FileOutputStream fileStrm;
        ObjectOutputStream objStrm;

        try
        {
            fileStrm = new FileOutputStream( fileName );//underlying 
                                                        //stream
            objStrm = new ObjectOutputStream( fileStrm );
            //^^object serialization stream
            objStrm.writeObject( objToSave );
            //serialize and save to filename, this will also save the
            //DSALinkedList class contained location object 

            objStrm.close(); //clean up
        }
        catch( Exception e )
        {
            throw new IllegalArgumentException("Unable to save object" +
                                               " to file: " + 
                                                e.getMessage());
        }
    }






    //NAME: load
    //PURPOSE: to load the file
    //IMPORTS: fileName (String)
    //EXPORTS: inObj (DSALinkedList)
    //ASSERTIONS:
    //    PRE: method is called, performs the algorithm
    //    POST: return inObj back to caller
    //REMARKS:
    public DSALinkedList load( String fileName ) throws 
    IllegalArgumentException
    {
        FileInputStream fileStrm;
        ObjectInputStream objStrm;
        DSALinkedList inObj;
        inObj = null; //initialize first 

        try
        {
            //underlying stream
            fileStrm = new FileInputStream( fileName );
            //object serialization stream
            objStrm = new ObjectInputStream( fileStrm );
            //deserialize. the type casting is needed.
            inObj = (DSALinkedList)objStrm.readObject();
            objStrm.close(); //clean up
        }
        catch( ClassNotFoundException e )
        {
            System.out.println("Class DSALinkedList not found: " +
                                e.getMessage());
        }
        catch( Exception e )
        {
            throw new IllegalArgumentException("Unable to load object " +
                                               "from file");
        }
        return inObj;
    }






    //private inner class
    public class DSAListNode implements Serializable
    {
        /*public class fields since DSAListNode is only inside 
          DSALinkedList so that we no need setter and getters*/
        public DSAGraphVertex value;
        public DSAListNode next;
        public DSAListNode prev;

        //Alternate Constructor
        public DSAListNode( Object inValue )
        {
            value = new DSAGraphVertex( inValue );
            next = null;
            prev = null;
        }






        //NAME: getLabelVal
        //PURPOSE: to get the value/data of label from DSAGraphVertex
        //IMPORTS: none
        //EXPORTS: value.getLabel()  (Object)
        //ASSERTIONS:
        //    PRE: method is called, get the label from DSAGraphVertex
        //    POST: returns the data in Object
        //REMARKS:
        public Object getLabelVal()
        {
            return value.getLabel();
        }






        public DSAGraphVertex getGraphVertex()
        {
            return value;
        }
        





        //NAME: setValue
        //PURPOSE: to set the value of the node
        //IMPORTS: inValue (DSAGraphVertex)
        //EXPORTS: none
        //ASSERTIONS:
        //   PRE: recevie the inValue (DSAGraphVertex) for the node
        //   POST: stores the inValue into the value
        //REMARKS:
        public void setValue( DSAGraphVertex inValue )
        {
            value = inValue;
        }






        //NAME: setNext
        //PURPOSE: to set the "next" of the current node to point to the 
        //         next node 
        //IMPORTS: newNext (DSAListNode)
        //EXPORTS:none
        //ASSERTIONS:
        //   PRE: ask the current listnode to point to a specific listnode
        //   POST: set the "next" to point to the specified listnode
        //REMARKS:
        public void setNext( DSAListNode newNext)
        {
            next = newNext;
        }






        //NAME: setPrev
        //PURPOSE: to set the "prev" of the current node to point to the 
        //         previous node 
        //IMPORTS: newPrev (DSAListNode)
        //EXPORTS:none
        //ASSERTIONS:
        //   PRE: ask the current listnode to point to a previous listnode
        //   POST: set the "prev" to point to the previous listnode
        //REMARKS:
        public void setPrev( DSAListNode newPrev)
        {
            prev = newPrev;
        }
    }






    //private inner class for DSALinkedListIterator
    private class DSALinkedListIterator implements Iterator, Serializable
    {
        private DSAListNode iterNext; //cursor (assuming DSAListNode
                                    //is the node class of DSALinkedList)

        public DSALinkedListIterator( DSALinkedList theList )
        {
            iterNext = theList.head;
            //NOTE: Able to access private field of DSALinkedList
        }






        //Iterator interface implementation
        //NAME: hasNext
        //PURPOSE: returns true or false to find out has next or not.
        //IMPORTS: none
        //EXPORTS: ( iterNext != null) (boolean)
        //ASSERTIONS:
        //    PRE: returns true if iterNext is not null, and vice versa
        //    POST:
        //REMARKS:
        public boolean hasNext()
        {
            return ( iterNext != null );
        }






        //NAME: next
        //PURPOSE: to get the value of the "next" pointer of the current 
        //         list node            
        //IMPORTS: none             
        //EXPORTS: value (Object)                     
        //ASSERTIONS:                   
        //    PRE: if iterNext != null, it will set the value and set
        //         iterNext to the next "next" pointer  
        //    POST: returns the value back to caller.                    
        //REMARKS:
        public Object next()
        {
            Object value;

            if( iterNext == null )
            {
                value = null;
            }
            else
            {
                value = iterNext.value; //get the value in the node
                iterNext = iterNext.next; //ready for subsequent calls
                                               //to next()
            }
            return value;
        }






        //NAME: remove  
        //PURPOSE: not needed, already have methods to do these 
        //         in different
        //         ways in the DSALinkedList class outside of this private
        //         inner class.       
        //IMPORTS: none             
        //EXPORTS: none                 
        //ASSERTIONS:                   
        //    PRE: if gets called, throw exception                    
        //    POST: "Not supported" will be displayed                    
        //REMARKS:
        public void remove()
        {
            throw new UnsupportedOperationException("Not supported");
        }
    }
}
