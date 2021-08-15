//FILE: droneCollector.java
//AUTHOR: Jason Tan Thong Shen
//USERNAME: jaxontn
//UNIT: Data Structures and Algorithms
//PURPOSE: to provide user the option to use interactive mode or report
//         mode
//REFERENCE: based on assignment paper 2021
//COMMENTS: none
//REQUIRES: DSAGraph, DSALinkedList
//Last Mod: 20th MAY 2021





import java.util.*;
import java.io.*;

public class droneCollector implements Serializable
{
    //private constants 
    private static final int LOCATION_FILE = 2694;
    private static final int INVENTORY_FILE = 8242;
    private static final int PRODUCT_FILE = 4624;
    private static final int ORDER_FILE = 8892;
    
    
    //private class field
    private static DSAGraph graph;



    //NAME: main
    //PURPOSE: to guide the user to select the correct mode (interactive
    //         or report) and then lead the user to their desired location
    //IMPORTS: args (String array)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE:
    //    POST:
    //REMARKS:
    public static void main( String[] args )
    {
        graph = new DSAGraph();
        final int MINIMUM = 1; //a minimum constant
        final int MAX_ARGUMENTS = 5; //maximum argument for report mode
        

        if( !(args.length < MINIMUM) ) //means have arguments
        {
            //check if report or interactive mode
            //show menu if interactive mode
            //show report if report mode

            //report mode must check for the exact number of arguments
            //which is 4 arguments

            switch( args[0] )
            {
                case "-i":
                    System.out.println("directing to interactive mode...");
                    interactMode();
                break;
                case "-r":
                    if( args.length != MAX_ARGUMENTS )
                    {
                        System.out.println("Only accepts 5 arguments");
                    }
                    else
                    {
                        System.out.println("directing to report mode...");
                        reportMode( args[1], args[2], args[3], args[4] );
                    }
                break;
            }
        }
        else if( args.length < MINIMUM ) //means no arguments
        {
            System.out.println("Only two options available:");
            System.out.println("---------------------------\n\n");
            System.out.print("[Interactive mode]: ");
            System.out.println(" droneCollector -i\n\n");
            System.out.print("[Report mode]: \n");
            System.out.print(" droneCollector -r <location_file>");
            System.out.print(" <prod_file> <inventory_file>");
            System.out.println(" <order_file>");
        }
    }






    //NAME: interectMode
    //PURPOSE: to provide the menu options for user to load data, or 
    //         view data.
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //   PRE: method is called, ask for user input
    //   POST: user is directed to another method calls for their 
    //         option
    //REMARKS:
    public static void interactMode()
    {
        Scanner sc  = new Scanner(System.in);
        int option;
        boolean endLoop;
        endLoop = false;

        try
        {
            while( !endLoop )
            {
                //show the menu here.
                System.out.println("MENU OPTIONS:");
                System.out.println("-----------------------");
                System.out.println("0. Load Data (from files or " +
                                   "serialized data)");
                System.out.println("1. Location Overview");
                System.out.println("2. Inventory Overview");
                System.out.println("3. Product Search");
                System.out.println("4. Find and display inventory for" +
                                 " a location (store)");
                System.out.println("5. Find and display distance" +
                                 " between two locations");
                System.out.println("6. Find and display route for" +
                                 " collecting order");
                System.out.println("7. Save Data (serialized)");
                System.out.println("8. Exit");
                System.out.print("\nENTER YOUR OPTION: ");
                option = sc.nextInt();
                System.out.println("\n");

                switch(option)
                {
                    case 0: 
                        loadData();
                    break;
                    case 1:
                        locationOverview();
                    break;
                    case 2:
                        inventoryOverview();
                    break;
                    case 3:
                        productSearch();
                    break;
                    case 4:
                        findNDisplayInventory();
                    break;
                    case 5:
                        findNDisplayDistance();
                    break;
                    case 6:
                        findNDisplayRoute();
                    break;
                    case 7:
                        saveData();
                    break;
                    case 8:
                        endLoop = true;
                    break;
                    default:
                        System.out.println("Please enter only 0 to 8");
                }
             
            }
        }
        catch( InputMismatchException e )
        {
            System.out.println("[Please enter between 1 and 8 only]");
            interactMode();
        }
        System.out.println("Program has Ended");
        System.exit(0);
    }






    //NAME: reportMode
    //PURPOSE: show the overview of the files data as a report.
    //IMPORTS: location (String), catalogue (String), inventory (String),
    //         ordrFile (String)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called when user wants to view report mode
    //    POST: the method displays the data found from those files
    //          received.
    //REMARKS:
    public static void reportMode( String location, String catalogue,
                                   String inventory, String orderFile )
    {
        try
        {
            Object[][] orders;
        
            //READ location file, inventory file, catalogue file
            graph.readFile(location,  LOCATION_FILE );
            graph.setVertexCount(); //setting the vertex count 
            graph.readFile(inventory, INVENTORY_FILE );
            graph.readFile(catalogue, PRODUCT_FILE );     
        
            //1. Show location data
            locationOverview();
            //2. Show inventory data
            inventoryOverview();
            //3. Show catalogue data
            System.out.println("\n\n----------------------------------");
            System.out.println("[RANDOM PRODUCTS]:");
            displayProductDetails( "WQ7206" );
            displayProductDetails( "MPSA13*" );
            displayProductDetails( "2N3906" );
            System.out.println("----------------------------------\n\n");
        
            //4. read and show order file data
            System.out.println("*************************************");
            System.out.println("[ORDER FILE]:");
            graph.readFile(orderFile, ORDER_FILE ); //Read order file
            orders = graph.getOrders();
            for( int i = 0; i < orders.length; i++ ) //display orders
            {
                if( orders[i][0] != null && orders[i][1] != null )
                {
                    System.out.println(orders[i][0] + "," + orders[i][1]);
                }
            }
            System.out.println("*************************************");
        }
        catch( NullPointerException e )
        {
            System.out.println("[ERROR: CHECK FILE NAME, MUST CORRECT]");
        }
    }







    //NAME: loadData
    //PURPOSE: to give user option which file they want to load
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: Ask for user's option, keep looping if user wants to load
    //         another file
    //    POST: directs user to the method call for their option
    //REMARKS:
    public static void loadData()
    {
       Scanner sc  = new Scanner(System.in);
       int option;
       boolean endLoop;
       endLoop = false;

       try
       {
           while( !endLoop )
           {
               //asks for file name 
               System.out.println("\t1. Location data");
               System.out.println("\t2. Inventory data");
               System.out.println("\t3. Product data");
               System.out.println("\t4. Order data");
               System.out.println("\t5. Serialised data");
               System.out.println("\t6. Back to Main Menu");
               System.out.print("\n\tENTER YOUR OPTION: ");
               option = sc.nextInt();
               System.out.println("\n");
               switch(option)
               {
                   case 1:
                       locationData();
                   break;
                   case 2:
                       inventoryData();
                   break;
                   case 3:
                       productData();
                   break;
                   case 4:
                       orderData();
                   break;
                   case 5:
                       serialisedData();
                   break;
                   case 6:
                       endLoop = true;
                   break;
                   default:
                       System.out.println("Please choose only 1 to 5");
               }
           }
           interactMode(); //go back to interact mode
       }
       catch( InputMismatchException e )
       {
           System.out.println("[Please enter between 1 and 6 only]");
           loadData();
       }
    }







    //NAME: locationOverview
    //PURPOSE: to display the location's details, edges, and distances
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: Display the data gotten from locaiton file
    //    POST: Display the locations, number of egdes, edges & edges 
    //          distance of each location
    //REMARKS:
    public static void locationOverview()
    {
        try
        {
            DSALinkedList vertices;         
            //aquire the vertices from here
            vertices = graph.getVertices();
            
            System.out.println("------------------------------------" + 
                               "------------------");
            System.out.print("Available Locations: ");
            graph.displayVertices();
            System.out.println("Number of vertices/location: " 
                                + graph.getVertexCount() );
            System.out.println("Edge Numbers: " + graph.getEdgeCount());

            for( int i = 0; i < graph.getVertexCount(); i++ )
            {
                System.out.println("\n");
                displayEachLocationOverview( vertices.peekAt(i) );
            }
            System.out.println("------------------------------------" + 
                               "------------------");
        }
        catch( NullPointerException e )
        {
            System.out.print("\n[Please go to \"load data\" -> ");
            System.out.println("\"Location data\" to load the locations]");
        }
        
    }








    //NAME: displayEachLocationOverview
    //PURPOSE: to display the current location's egdes and edge's
    //         distances
    //IMPORTS: location (Object)
    //EXPORTS: none
    //ASSERIOTIONS:
    //    PRE: method is called, it recieves the location
    //    POST: it calls two other methods to display the edges and
    //          distance
    //REMARKS:
    public static void displayEachLocationOverview( Object location )
    {
        System.out.println("\nVertex " + location + "'s edges are: ");
        graph.displayEdgeOf( location );
        System.out.println("\nDistances: ");
        graph.displayEdgeDistanceOf( location );
    }







    //NAME: inventoryOverview
    //PURPOSE: to display all the inventories for each location
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called, it gets the data from the uploaded
    //         inventory file
    //    POST: it ask a method in DSALinkedList to display the inventory
    //          for each location
    //REMARKS:
    public static void inventoryOverview()
    {
        try
        {
            DSALinkedList vertices;
            
            //aquire the vertices from here
            vertices = graph.getVertices(); //getting the linked list
                                            //from DSAGraph

            System.out.println("------------------------------------" + 
                               "------------------");
            System.out.print("Available Locations: ");
            graph.displayVertices();
            System.out.println("Number of vertices/location: " 
                                + graph.getVertexCount() );
            for( int i = 0; i < graph.getVertexCount(); i++ )
            {
                System.out.println("\n");
                vertices.displayInventoryOverview( vertices.peekAt(i) );
                //display inventory for each location
            }
            System.out.println("------------------------------------" + 
                               "------------------");
        }
        catch( NullPointerException e )
        {
            System.out.print("\n[Please go to \"load data\" -> ");
            System.out.println("\"Inventory data\" to load the" +
                               " Inventory]");
        }
    }







    //NAME: productSearch
    //PURPOSE: to search for a product from the product file
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called, it ask user for the product name
    //    POST: search for the product's details by calling another
    //          method
    //REMARKS:
    public static void productSearch()
    {
        try
        {
            Scanner sc = new Scanner(System.in);
            String product;
            System.out.print("ENTER A PRODUCT TO SEARCH: ");
            product = sc.nextLine();
            System.out.println("\n");
            displayProductDetails( product );
        }
        catch( NullPointerException e )
        {
            System.out.print("\n[Please go to \"load data\" -> ");
            System.out.println("\"Product data\" to load the" +
                               " Product search]");
        }
    }








    //NAME: displayProductDetails 
    //PURPOSE: to find the prodcut and dsiplay product's details
    //IMPORTS: theProduct (String)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called to get the details of the specified
    //         prodcut
    //    POST: display it to the terminal for user to see.
    //REMARKS:
    public static void displayProductDetails( String theProduct )
    {
        int indexAfterHeader;
        boolean productFound;
        Object[] product = graph.getProduct(); //get product name
        Object[] desc = graph.getDesc(); //get description
        Object[] oneAbove = graph.getOneAbove(); //get QTY1+
        Object[] tenAbove = graph.getTenAbove(); //get QTY10+
        Object[] twtyFiveAbove = graph.getTwtyFiveAbove(); //get QTY25+
        
        indexAfterHeader = 1;
        productFound = false;
        
        System.out.println("------------------------------------" + 
                               "------------------");
                               
        for( int i = indexAfterHeader; i < product.length; i++ )
        {   
            if( product[i] != null )
            {
                if( product[i].equals(theProduct) )//found product
                {
                    System.out.println("Product: " + product[i]);
                    System.out.println("Description: " + desc[i]);
                    System.out.println("QTY1+: " + oneAbove[i]);
                    System.out.println("QTY10+: " + tenAbove[i]);
                    System.out.println("QTY25+: " + twtyFiveAbove[i]);
                    System.out.println("\n");
                    productFound = true;
                }
            }
        }
        
        if( !productFound )
        {
            System.out.println("[Product Unavailable in Product Data]");
        }
        System.out.println("------------------------------------" + 
                               "------------------");
    }







    //NAME: findNDisplayInventory
    //PURPOSE: Ask user for a location that he wants to display the
    //         location's inventory
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called, ask user for a location
    //    POST: calls a method from DSALinkedList to handle the task
    //REMARKS:
    public static void findNDisplayInventory()
    {
        try
        {
            Scanner sc = new Scanner( System.in );
            DSALinkedList vertices;
            Object location;
            
            //aquire the vertices from here
            vertices = graph.getVertices(); //getting the linked list
                                            //from DSAGraph
            System.out.println("------------------------------------" + 
                               "------------------");
            System.out.print("Available Locations: ");
            graph.displayVertices();
            System.out.print("ENTER A LOCATION: ");
            location = sc.nextLine();

            System.out.println("\n");
            vertices.displayInventoryOverview( location );
            //displays the inventory for that specific location

            System.out.println("------------------------------------" + 
                               "------------------");
        }
        catch( NullPointerException e )
        {
            System.out.print("\n[Please go to \"load data\" -> ");
            System.out.println("\"Inventory data\" to load the" +
                               " Inventory]");
        } 
    }







    //NAME: findNDisplayDistance
    //PURPOSE: to find the distance between two locations
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: ask user for two locations
    //    POST: calls a method from DSAGraph to find the distance between
    //          the two locations.
    //REMARKS:
    public static void findNDisplayDistance()
    {
        try
        {
            double totalDistance;
            Scanner sc = new Scanner( System.in );
            String pair;
            String[] splitLine;
            System.out.println("------------------------------------" + 
                               "------------------");
            System.out.println("Example: \"Home gadgetsRUs\"");
            System.out.print("ENTER TWO LOCATIONS: ");
            pair = sc.nextLine();
            System.out.println("\n");
            splitLine = pair.split(" ");
            //1. Search the starting points edge list's first for the
            //   destination point
            //2. If still can't find, search edge list's children
            //   (edge list)
            System.out.println("START POINT: " + splitLine[0] );
            totalDistance = graph.findDistance( splitLine[0], 
                                                splitLine[1] ); 
            System.out.println("DESTINATION: " + splitLine[1] );
           System.out.println("TOTAL DISTANCE: " + totalDistance );
            System.out.println("------------------------------------" + 
                               "------------------");
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
            System.out.println("[ERROR: Please enter two locations]");
        }
        catch( NullPointerException e )
        {
            System.out.print("\n[Please go to \"load data\" -> ");
            System.out.println("\"Location data\" to load the locations]");
        }
    }







    //NAME: findNDisplayRoute
    //PURPOSE: go and find route after collecting the order file
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: calls a method from DSAGraph to collect the order and
    //          find the route and location to collect the order from
    //REMARKS:
    public static void findNDisplayRoute()
    { 
        try
        {
            graph.collectOrder();
        }
        catch( NullPointerException e )
        {
            System.out.print("\n[Please go to \"load data\" -> ");
            System.out.println("\"Location data\" to load the locations]");
        }
    }







    //NAME: saveData
    //PURPOSE: to save all data into a serialized file
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called
    //    POST: called the save method to save to serialized file
    //REMARKS:
    public static void saveData()
    {
        save( graph, "serialized.txt" );
    }







    //NAME: locationData
    //PURPOSE: ask for location file to store into the program
    //IMPORT: none
    //EXPORT: none
    //ASSERTIONS:
    //    PRE: receives the location file from user input
    //    POST: calls a method from DSAGraph to read the file and store
    //          the file data into the program
    //REMARKS:
    public static void locationData()
    {
        //ask for location file
        Scanner sc = new Scanner(System.in);
        String file;
        System.out.print("ENTER LOCATION FILE: ");
        file = sc.nextLine();
        System.out.println("\n");

        graph.readFile(file,  LOCATION_FILE );
        graph.setVertexCount(); //count the vertex
 
    }







    //NAME: inventoryData
    //PURPOSE: ask for inventory file to store into the program
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: receives the inventory file from user input
    //    POST: calls a method from DSAGraph to read the file and store
    //          the file data into the program
    //REMARKS:
    public static void inventoryData()
    {
        //ask for inventory file
        Scanner sc = new Scanner(System.in);
        String file;
        System.out.print("ENTER INVENTORY FILE: ");
        file = sc.nextLine();
        System.out.println("\n");
    
        graph.readFile(file, INVENTORY_FILE ); 
    }







    //NAME: prodcutData
    //PURPOSE: ask for product file to store into the program
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: receivces the product file from user input
    //    POST: calls a method form DSAGraph to read the file and store
    //          the file data into the program
    //REMARKS:
    public static void productData()
    {
        //ask for productData, the catalogue file
        Scanner sc = new Scanner(System.in);
        String file;
        System.out.print("ENTER PRODUCT FILE: ");
        file = sc.nextLine();
        System.out.println("\n");
        
        graph.readFile(file, PRODUCT_FILE );
    }







    //NAME: orderData
    //PURPOSE: ask for order file to store into the program & display the
    //         content of order data here
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: receives the order file from user input
    //    POST: calls a method from DSAGraph to read the file and store
    //          the file data into the program
    //REMARKS: ONLY SUPPORTS SMALL SET, THE MEDIUM SET WAS NOT PROGRAMMED
    //         TO READ ITS ORDER FILES BECAUSE OF LATE ANNOUNCEMENT &
    //         CHANGES.
    public static void orderData()
    {
        //ask for order data, the order file
        Scanner sc = new Scanner(System.in);
        String file;
        Object[][] orders;
        System.out.print("ENTER ORDER FILE: ");
        file = sc.nextLine();
        System.out.println("\n");
        System.out.println("*************************************");
        graph.readFile(file, ORDER_FILE );
        orders = graph.getOrders();
        
        for( int i = 0; i < orders.length; i++ )
        {
            if( orders[i][0] != null && orders[i][1] != null )
            {
                System.out.println(orders[i][0] + "," + orders[i][1]);
            }
        }
        System.out.println("*************************************");
    }







    //NAME: serialisedData
    //PURPOSE: load the serialized file and store into graph variable
    //IMPORTS: none
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: load the serialized file
    //    POST: store into graph variable
    //REMARKS:
    public static void serialisedData()
    {
        //ask for serialised file
        graph = load( "serialized.txt" );
    }
    
    
    
    
    
    
    
    //NAME: save
    //PURPOSE: to save object into a file
    //IMPORTS: objToSave (DSAGraph), fileName (String)
    //EXPORTS: none
    //ASSERTIONS:
    //    PRE: method is called, performs the algorithm
    //    POST: a file is created 
    //REMARKS:
    public static void save( DSAGraph objToSave, String fileName )
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
            //DSAGraph class contained location object 

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
    //EXPORTS: inObj (DSAGraph)
    //ASSERTIONS:
    //    PRE: method is called, performs the algorithm
    //    POST: return inObj back to caller
    //REMARKS:
    public static DSAGraph load( String fileName ) throws 
    IllegalArgumentException
    {
        FileInputStream fileStrm;
        ObjectInputStream objStrm;
        DSAGraph inObj;
        inObj = null; //initialize first 

        try
        {
            //underlying stream
            fileStrm = new FileInputStream( fileName );
            //object serialization stream
            objStrm = new ObjectInputStream( fileStrm );
            //deserialize. the type casting is needed.
            inObj = (DSAGraph)objStrm.readObject();
            objStrm.close(); //clean up
        }
        catch( ClassNotFoundException e )
        {
            System.out.println("Class DSAGraph not found: " +
                                e.getMessage());
        }
        catch( Exception e )
        {
            throw new IllegalArgumentException("Unable to load object " +
                                               "from file");
        }
        return inObj;
    }
}
