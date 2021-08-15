//FILE: UnitTestDSAGraph.java
//AUTHOR: Jason Tan Thong Shen
//USERNAME: jaxontn
//UNIT: Data Structures and Algorithms
//PURPOSE: to test all the reading and storing data into the graph,
//         once reading passes, the other parts of the program will
//         surely work.
//REFERENCE:
//COMMENTS: none
//REQUIRES: DSAGraph
//Last Mod:

import java.util.*;



public class UnitTestDSAGraph
{
    //private constants
    private static final int LOCATION_FILE = 2694;
    private static final int INVENTORY_FILE = 8242;
    private static final int PRODUCT_FILE = 4624;
    private static final int ORDER_FILE = 8892;

    //private class field
    private static DSAGraph graph = new DSAGraph();
    private static DSAGraph graphTwo = new DSAGraph();

    public static void main( String[] args )
    {
        System.out.println("[EACH TEST PASSES WHEN NO ERROR OUTPUT]\n");
        testReadLocation();
        testReadInventory();
        testReadProduct();
        testReadOrder();
        testReadSerialized();
    }







    public static void testReadLocation()
    {
        graph.readFile( "stores.csv", LOCATION_FILE );
        System.out.print("UnitTestDSAGraph -> ");
        System.out.println("testReadLocation [PASS]");
    }







    public static void testReadInventory()
    {
       graph.readFile( "inventories.csv", INVENTORY_FILE );
       System.out.print("UnitTestDSAGraph -> ");
       System.out.println("testReadInventory [PASS]");
    }
    
    
    
    
    
    
    
    public static void testReadProduct()
    {
       graph.readFile( "catalogue.csv", PRODUCT_FILE );
       System.out.print("UnitTestDSAGraph -> ");
       System.out.println("testReadProduct [PASS]");
    }
    
    
    
    
    
    
    
    public static void testReadOrder()
    {
       graph.readFile( "order90.csv", ORDER_FILE );
       System.out.print("UnitTestDSAGraph -> ");
       System.out.println("testReadOrder [PASS]");
    }
    
    
    
    
    
    
    
    public static void testReadSerialized()
    {
       
       graphTwo = droneCollector.load( "serialized.txt" );
       System.out.print("UnitTestDSAGraph -> ");
       System.out.println("testReadSerialised [PASS]");
    }
}
