//package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // file names being stores in variables
	    String auditorium1 = "A1.txt";
        String auditorium2 = "A2.txt";
        String auditorium3 = "A3.txt";
        String userData = "userdb.dat";

        // creation of variables that will be used in the program
        String userName = "";
        String password = "";
        userInfo currentUser = null;

        boolean loggedIn = false;

        int counter = 0;
        int userChoice = 0;

        int userRowNumber = 0;
        int rowCounter = 0;
        char startingSeat = ' ';
        int columnCounter = 0;
        int numberOfAdultSeats = 0;
        int numberOfChildSeats = 0;
        int numberOfSeniorSeats = 0;
        int numberOfTotalTickets = 0;

        int orderToUpdate = 0;
        int updateOrderChoice = 0;

        // creation of file scanner object and input scanner object
        Scanner fileScanner = new Scanner(new File(userData));
        Scanner inputScanner = new Scanner(System.in);

        // creation of auditoriums and objects to hold the auditoriums temporarily
        Auditorium Auditorium1 = new Auditorium(auditorium1);
        Auditorium Auditorium2 = new Auditorium(auditorium2);
        Auditorium Auditorium3 = new Auditorium(auditorium3);
        Auditorium currentAuditorium = null;
        Auditorium thisAuditorium = null;

        // creation of hashmap object and hashmap
        HashMap database = new HashMap();

        // while loop loops as long as the database has data and adds it and the username to the hashmap
        while(fileScanner.hasNext())
        {
            // userInfo object is created that will hold the password along with the list of orders
            currentUser = new userInfo();
            // userName stores the username read from the file
            userName = fileScanner.next();
            // set the password from the file to a variable inside of the userInfo class
            currentUser.setPassword(fileScanner.next());
            // put the userInfo object and userName in the hashmap
            database.put(userName, currentUser);
        }

        // creation of loop control variable
        boolean outerLoopKeepLooping = true;

        // outerloop that loops as long as the user does note exit through the admin
        while(outerLoopKeepLooping)
        {
            // loop control variables being created that will be used
            boolean valid = false;
            loggedIn = true;

            // while loop to check the userName information
            while(!valid)
            {
                // prompting for the userName
                System.out.println("Enter your username: ");
                try
                {
                    // storing the user input inside of a userName variable
                    userName = inputScanner.next();
                    // checking to make sure that the userName is in the hashmap
                    if(!database.containsKey(userName))
                    {
                        // if not throw an exception
                        throw new Exception();
                    }
                    // set loop control variable to true
                    valid = true;
                }
                // catch the exception
                catch(Exception e)
                {
                    // print out this error
                    System.out.println("Username not found");
                }
            }

            // create a temporary userInfo object that will be used to access the data a userNames userInfo object
            userInfo tempHolder = (userInfo) database.get(userName);

            // loop control variables being created and initialized
            valid = false;
            counter = 0;

            // while loop for password validation
            while(counter < 3 && !valid)
            {
                // asking the user for a password
                System.out.println("Password: ");
                try
                {
                    // increment counter and store the password in a variable
                    counter++;
                    password = inputScanner.next();
                    //checking to see if the password matches the userName
                    if(tempHolder.getPassword().equals(password))
                    {
                        // if so then set loop control variable to true
                        valid = true;
                    }
                    else
                    {
                        // otherwise throw an exception
                        throw new Exception();
                    }
                }
                // catch exception
                catch(Exception e)
                {
                    //print out this error
                    System.out.println("Invalid password");
                    if(counter == 3)
                    {
                        // loops 3x then set loop control to false and ask for userName again
                        loggedIn = false;
                    }
                }
            }

            // while loop that loops while a user is logged in
            while(loggedIn)
            {
                // check to see if an admin is logged in
                if(userName.equals("admin"))
                {
                    // if so then present the following menu to them
                    valid = false;
                    while(!valid)
                    {
                        // prompt the menu to the admin
                        try {
                            System.out.println("Choose one of the following items from the menu");
                            System.out.println("1. Print Report");
                            System.out.println("2. Logout");
                            System.out.println("3. Exit");
                            // collect they're input
                            userChoice = Integer.parseInt(inputScanner.next());
                            // check to see if the admin input is valid
                            if (userChoice < 1 || userChoice > 3) {
                                // if not then throw exception
                                throw new Exception();
                            }
                            // setting loop control to true
                            valid = true;
                            // catching exception thrown
                        } catch (Throwable exception) {
                            // printing out the following error message
                            System.out.println("Invalid Choice");
                        }
                    }
                    // if the input is 1
                    if(userChoice == 1)
                    {
                        // find Auditorium 1's row and column number and print the auditorium with it's sales
                        int AuditoriumRowCounter1 = CountNumberOfRows(Auditorium1);
                        int AuditoriumColumnCounter1 = CountNumberOfColumns(Auditorium1);
                        adminPrint("Auditorium 1", Auditorium1, AuditoriumRowCounter1, AuditoriumColumnCounter1);

                        // find Auditorium 2's row and column number and print the auditorium with it's sales
                        int AuditoriumRowCounter2 = CountNumberOfRows(Auditorium2);
                        int AuditoriumColumnCounter2 = CountNumberOfColumns(Auditorium2);
                        adminPrint("Auditorium 2", Auditorium2, AuditoriumRowCounter2, AuditoriumColumnCounter2);

                        // find Auditorium 3's row and column number and print the auditorium with it's sales
                        int AuditoriumRowCounter3 = CountNumberOfRows(Auditorium3);
                        int AuditoriumColumnCounter3 = CountNumberOfColumns(Auditorium3);
                        adminPrint("Auditorium 3", Auditorium3, AuditoriumRowCounter3, AuditoriumColumnCounter3);

                        // find the total sales and figures from all three auditoriums and print it
                        adminTotalPrint(Auditorium1, Auditorium2, Auditorium3, AuditoriumRowCounter1, AuditoriumColumnCounter1,
                                AuditoriumRowCounter2, AuditoriumColumnCounter2, AuditoriumRowCounter3, AuditoriumColumnCounter3);
                    }
                    // if the input is 2
                    else if(userChoice == 2)
                    {
                        // log out the admin
                        loggedIn = false;
                    }
                    // if it's 3
                    else
                    {
                        // print all three auditoriums to they're respective files
                        int AuditoriumColumnCounter1 = CountNumberOfColumns(Auditorium1);
                        writeAuditorium(Auditorium1, AuditoriumColumnCounter1, "A1Final.txt");
                        int AuditoriumColumnCounter2 = CountNumberOfColumns(Auditorium2);
                        writeAuditorium(Auditorium2, AuditoriumColumnCounter2, "A2Final.txt");
                        int AuditoriumColumnCounter3 = CountNumberOfColumns(Auditorium3);
                        writeAuditorium(Auditorium3, AuditoriumColumnCounter3, "A3Final.txt");

                        // log the admin out
                        loggedIn = false;
                        // exit the program
                        outerLoopKeepLooping = false;
                    }
                }
                // if the user is not an admin
                else
                {
                    // present them with the following menu
                    valid = false;
                    while(!valid)
                    {
                        try
                        {
                            // prompt the user with a menu
                            System.out.println("Choose one of the following items from the menu");
                            System.out.println("1. Reserve Seats");
                            System.out.println("2. View Orders");
                            System.out.println("3. Update Order");
                            System.out.println("4. Display Receipt");
                            System.out.println("5. Log Out");
                            // collect the user input
                            userChoice = Integer.parseInt(inputScanner.next());

                            // make sure that the user input is valid
                            if(userChoice < 1 || userChoice > 5)
                            {
                                // if it's not between 1 and 5 throw an exception
                                throw new Exception();
                            }
                            // set loop control variable to true
                            valid = true;
                        }
                        // catch exception if it's thrown
                        catch(Throwable exception)
                        {
                            // print the following error
                            System.out.println("Invalid Choice");
                        }
                    }

                    // if the userChoice is 1
                    if(userChoice == 1)
                    {
                        // call the following method which will walk the user through a reservation process and bestAvailable process
                        // and present them with the auditorium list
                        optionOneReserveSeats(inputScanner, Auditorium1, Auditorium2, Auditorium3, currentAuditorium, userName,
                                database, tempHolder);
                    }
                    // if the userChoice is 2
                    else if(userChoice == 2)
                    {
                        // if the user has no orders
                        if(tempHolder.getListSize() == 0)
                        {
                            // print out no orders
                            System.out.println("No Orders");
                        }
                        // otherwise
                        else
                        {
                            // print out all the orders the user has with the printOrders function
                            tempHolder = (userInfo) database.get(userName);
                            tempHolder.printOrders();
                        }
                    }
                    // if the user input is 3
                    else if(userChoice == 3)
                    {
                        // creation of objects that will be used in this part of the if statement
                        tempHolder = (userInfo) database.get(userName);
                        orders updateOrders = null;

                        // check to see if the user has orders
                        if(tempHolder.getListSize() == 0)
                        {
                            // if the orderlist is 0 then print this message
                            System.out.println("No orders");
                        }
                        // otherwise
                        else
                        {
                            // present the user with they're orders
                            valid = false;
                            while(!valid)
                            {
                                try
                                {
                                    // prompting the user with their orders with the printToUpdateOrders function
                                    System.out.println("Select the order you would like to update");
                                    tempHolder.printToUpdateOrders();

                                    // collecting the user input
                                    orderToUpdate = Integer.parseInt(inputScanner.next());

                                    // making sure that the number the user inputted is an actual order number
                                    if(orderToUpdate > tempHolder.getListSize() || orderToUpdate < 1)
                                    {
                                        // if not between the bounds then throw this exception
                                        throw new Exception();
                                    }
                                    // set loop control to true
                                    valid = true;

                                    // store the order inside of a temporary variable to access and work with the order's values
                                    updateOrders = tempHolder.getUserOrder(orderToUpdate);
                                }
                                // catch exception
                                catch(Exception e)
                                {
                                    // print this error if exception caught
                                    System.out.println("No orders");
                                }
                            }

                            // once the user has chosen an order present them with this menu
                            valid = false;
                            while(!valid)
                            {
                                try
                                {
                                    // prompting the user with a menu
                                    System.out.println("What would you like to do with the order?");
                                    System.out.println("1. Add tickets to order");
                                    System.out.println("2. Delete tickets from order");
                                    System.out.println("3. Cancel Order");

                                    // collecting input from the user
                                    updateOrderChoice = Integer.parseInt(inputScanner.next());

                                    // making sure the input the user entered is within the bounds
                                    if(updateOrderChoice > 3 || updateOrderChoice < 1)
                                    {
                                        // if not then throw an exception
                                        throw new Exception();
                                    }
                                    // loop control equals true
                                    valid = true;
                                }
                                // exception caught
                                catch(Exception e)
                                {
                                    // print this error message
                                    System.out.println("No orders");
                                }
                            }

                            // setting loop control variables to false
                            boolean validOption = false;

                            // while loops loops until one of the options has successfully been met
                            while(!validOption)
                            {
                                // if the user chose 1 from the sub menu
                                if(updateOrderChoice == 1)
                                {
                                    // find which auditorium the order belongs to and store that auditorium in the thisAuditorium object
                                    // through the whichAuditorium function
                                    thisAuditorium = whichAuditorium(updateOrders.getAuditorium(), Auditorium1, Auditorium2, Auditorium3);
                                    // find how many rows the auditorium has and ask the user for which row, done through validateRow method
                                    rowCounter = CountNumberOfRows(thisAuditorium);
                                    userRowNumber = validateRow(inputScanner, userRowNumber, rowCounter);

                                    // asking the user which column they would like
                                    System.out.println("Which column would you like your first seat to be?");

                                    // call the countNumberOfColumns function which counts how many columns there are in columnCounter
                                    columnCounter = CountNumberOfColumns(thisAuditorium);

                                    // asking the user and checking and making sure they enter a valid seat value
                                    startingSeat = columnValidator(inputScanner, columnCounter);

                                    // asking the user for adult, child and senior ticket numbers and validating and storing those numbers
                                    // through the validateSeat function and the following prompts
                                    System.out.println("How many adult tickets would you like?");
                                    numberOfAdultSeats = validateSeat(inputScanner, numberOfAdultSeats);

                                    System.out.println("How many child tickets would you like?");
                                    numberOfChildSeats = validateSeat(inputScanner, numberOfChildSeats);

                                    System.out.println("How many senior tickets would you like?");
                                    numberOfSeniorSeats = validateSeat(inputScanner, numberOfSeniorSeats);

                                    // calculating the number of total tickets
                                    numberOfTotalTickets = numberOfAdultSeats + numberOfChildSeats + numberOfSeniorSeats;

                                    // checking to see if the seats the user wants are possible or not through the checkAvailability function
                                    boolean isAvailable = checkAvailability(thisAuditorium, numberOfTotalTickets, userRowNumber, startingSeat);

                                    // if they are available
                                    if(isAvailable)
                                    {
                                        // reserve the seats through the reserveSeats method
                                        reserveSeats(thisAuditorium, userRowNumber, startingSeat, numberOfAdultSeats, numberOfChildSeats,
                                                numberOfSeniorSeats, updateOrders, 1);
                                        // set the sub menu loop control to true
                                        validOption = true;
                                    }
                                    // if they are not available then
                                    else
                                    {
                                        // print the following message
                                        System.out.println("New seats are not available");
                                        // set the sub menu loop control to false
                                        validOption = false;
                                    }

                                }
                                // if the user chose option 2 from the sub menu
                                else if(updateOrderChoice == 2)
                                {
                                    // figure out which auditorium that order belongs to through the whichAuditorium function and store
                                    // that auditorium inside of thisAuditorium, a temporary auditorium object
                                    thisAuditorium = whichAuditorium(updateOrders.getAuditorium(), Auditorium1, Auditorium2, Auditorium3);
                                    // count number of rows in that auditorium
                                    rowCounter = CountNumberOfRows(thisAuditorium);

                                    // prompt the user for a row number and validate that row number and store it inside of userRowNumber
                                    // through the validateRow function
                                    userRowNumber = validateRow(inputScanner, userRowNumber, rowCounter);


                                    // prompt the user for a seat
                                    System.out.println("Which column would you like your first seat to be?");

                                    // count the number of seats there are in the auditorium
                                    columnCounter = CountNumberOfColumns(thisAuditorium);

                                    // prompt the user for a seat and validate that seat through the columnValidator function
                                    startingSeat = columnValidator(inputScanner, columnCounter);

                                    // breaking down the user inputed values into strings and combining them
                                    String row = Integer.toString(userRowNumber);
                                    String column = String.valueOf(startingSeat);
                                    String rowPlusColumn = row + column;

                                    // creation of temporary node to navigate through the auditorium
                                    Node tempHead = thisAuditorium.getHead();

                                    // navigate to the seat that the user wants to remove
                                    for(int i = 0; i < userRowNumber - 1; i++)
                                    {
                                        tempHead = tempHead.getDownNode();
                                    }
                                    for(char i = 'A'; i < startingSeat; i++)
                                    {
                                        tempHead = tempHead.getRightNode();
                                    }

                                    // get the size of the order list
                                    int listSize = updateOrders.getListSize();
                                    // creation a temporary holder variable and checker variable
                                    String seatHolder = " ";
                                    boolean isPartOfOrder = false;

                                    // loop the size of the list number of times
                                    for(int i = 0; i < listSize; i++)
                                    {
                                        // get the seat at index i and store it inside of the temporary variable
                                        seatHolder = updateOrders.getSeat(i);

                                        // check to see if the seat the user wants to delete is a part of the order
                                        if(rowPlusColumn.equals(seatHolder))
                                        {
                                            // if so then set the checker to true
                                            isPartOfOrder = true;
                                        }
                                    }

                                    // if the checker is true
                                    if(isPartOfOrder)
                                    {
                                        // delete the seat from the order through the deleteSeatFromOrder function
                                        deleteSeatFromOrder(tempHead, updateOrders, rowPlusColumn);
                                        // set the validOption variable to true
                                        validOption = true;
                                    }
                                    // if the checker is false
                                    else
                                    {
                                        // print out the following message and keep the loop control variable at false
                                        System.out.println("The seat not part of the chosen order");
                                        validOption = false;
                                    }

                                    // check to see if the adult, child and senior ticket values for the order are 0
                                    if(updateOrders.getAdultSeatCount() == 0 && updateOrders.getChildSeatCount() == 0 && updateOrders.getSeniorSeatCount() == 0)
                                    {
                                        // if they are then remove that order from the order list
                                        tempHolder.removeOrders(updateOrders);
                                    }
                                }
                                // if the user choice option 3 from the sub menu
                                else
                                {
                                    // find out which auditorium the order is in through the whichAuditorium function and store that
                                    // auditorium in a temporary auditorium object
                                    thisAuditorium = whichAuditorium(updateOrders.getAuditorium(), Auditorium1, Auditorium2, Auditorium3);

                                    // get the size of the order list and store it in a temporary variable
                                    int listSize = updateOrders.getListSize();
                                    // creation of variables and objects that will be used later on
                                    String seatHolder = "";
                                    int rowNumber = 0;
                                    char columnNumber = ' ';
                                    Node tempHead = null;

                                    // for loop, loops listSize number of times
                                    for(int i = 0; i < listSize; i++)
                                    {
                                        // store the start of the auditorium inside of tempHead which is a traversor
                                        tempHead = thisAuditorium.getHead();
                                        // get the seat from the order and store it inside of a variable to work with it
                                        seatHolder = updateOrders.getSeat(0);
                                        // get the row number the seat variable
                                        rowNumber = Integer.parseInt(seatHolder.substring(0,1));
                                        // get the column value from the seat variable
                                        columnNumber = seatHolder.charAt(1);

                                        // navigate to that seat
                                        for(int j = 0; j < rowNumber - 1; j++)
                                        {
                                            tempHead = tempHead.getDownNode();
                                        }
                                        for(char j = 'A'; j < columnNumber; j++)
                                        {
                                            tempHead = tempHead.getRightNode();
                                        }

                                        // delete it from the order with the deleteSeatFromOrder function
                                        deleteSeatFromOrder(tempHead, updateOrders, seatHolder);
                                    }

                                    // remove the order from the order list
                                    tempHolder.removeOrders(updateOrders);
                                    // set the loop control variable to true
                                    validOption = true;
                                }
                            }
                        }
                    }
                    // if the user chooses option 4 from the menu
                    else if(userChoice == 4)
                    {
                        // print the recepit, they're total, with the printReceipt function
                        tempHolder = (userInfo) database.get(userName);
                        tempHolder.printReceipt();
                    }
                    // if the user chooses option 5 from the menu
                    else
                    {
                        // log the user out
                        loggedIn = false;
                    }
                }

            }
        }
    }

    // function takes three parameters and asks the user for a row number and validates that row number
    public static int validateRow(Scanner inputScanner, int userInput, int checkAgainst)
    {
        boolean valid = false;
        while(!valid)
        {
            // try is the start of the error handling
            try
            {
                // asking the user which row they would like and collecting that number and storing it in rowNumber
                System.out.println("Which row would you like?");
                userInput = Integer.parseInt(inputScanner.next());
                // if that number is not a number or is a double or a string then the parseInt will throw and error
                // if it is a number greater than the number of rows in the auditorium then an error will be thrown
                if(userInput > checkAgainst)
                {
                    throw new Exception();
                }
                // if code gets to this point then it means the value inputted is correct and this will terminate the
                // while loop
                valid = true;
            }
            // catch clause that will catch the errors thrown in the try above
            catch(Throwable Exception)
            {
                System.out.println("Error: Enter a valid column");
            }
            // end of while
        }
        return userInput;
    }

    // function asks the user for a column value and validates that column value
    public static char columnValidator(Scanner inputScanner, int checkAgainst) {
        boolean valid = false;
        char startingSeat = 0;
        inputScanner.nextLine();
        while(!valid) {
            try {
                //Read in input
                System.out.println("Enter in a column value");
                String columnValue = inputScanner.nextLine();
                startingSeat = columnValue.charAt(0);
                System.out.println("This is the columnValue " + columnValue);
                //Check if the input is a valid uppercase letter
                if (startingSeat < 'A' || startingSeat > 'Z')
                    throw new Exception();

                //Check if the column exists in the auditorium
                char columnCounter = (char) (64);
                boolean inAuditorium = false;
                for (int i = 0; i < checkAgainst; i++) {
                    columnCounter++;
                    if (startingSeat == columnCounter) {
                        inAuditorium = true;
                    }
                }
                if (!inAuditorium) {
                    throw new Exception();
                }

                //If no exceptions have been thrown yet, then the input is valid
                valid = true;
            }
            catch(Exception e) {
                System.out.println("Error: enter in a valid column");
            }
        }
        return startingSeat;
    }

    // function collects the ticket amount for the age groups and validates those ticket numbers
    public static int validateSeat(Scanner inputScanner, int userInput)
    {
        boolean valid = false;
        while(!valid)
        {
            // start of error handling
            try
            {
                // parseInt checks to make sure that the value entered is an int if not then an error is thrown
                userInput = Integer.parseInt(inputScanner.next());
                // if statement makes sure the value entered is positive otherwise an error is thrown
                if(userInput < 0)
                {
                    throw new Exception();
                }
                // if code gets to this point, the value is valid and the while loop is ended
                valid = true;
            }
            // catch which handles the errors that will be thrown by the code
            catch(Throwable exception)
            {
                System.out.println("Error: Enter a valid ticket Number");
            }
        }
        return userInput;
    }

    // function counts the number of rows in an auditorium
    public static int CountNumberOfRows(Auditorium auditorium)
    {
        // creation of variable that will be used in the function
        int rowCounter = 0;
        Node tempTraversor = auditorium.getHead();

        // while loop loops as long the traversor variable is not out of bounds
        while(tempTraversor != null)
        {
            // everytime it moves to the right increment rowCounter by 1
            tempTraversor = tempTraversor.getDownNode();
            rowCounter++;
        }
        // return rowCounter at the end
        return rowCounter;
    }

    // function counts the number of columns in an auditorium
    public static int CountNumberOfColumns(Auditorium auditorium)
    {
        // creation of variable that will be used in the function
        int columnCounter = 0;
        Node tempTraversor = auditorium.getHead();
        // while loop loops as long the traversor variable is not out of bounds
        while(tempTraversor != null)
        {
            // everytime it moves to the right increment rowCounter by 1
            tempTraversor = tempTraversor.getRightNode();
            columnCounter++;
        }
        // return rowCounter at the end
        return columnCounter;
    }

    // function takes in multiple parameters, and walks the user through all of option 1 in the menu, including selection an auditorium
    // selecting seats, and giving them best available seats if those seats are not available
    public static void optionOneReserveSeats(Scanner inputScanner, Auditorium Auditorium1, Auditorium Auditorium2,
                                             Auditorium Auditorium3, Auditorium currentAuditorium, String userName,
                                             HashMap database, userInfo tempHolder)
    {
        // creation of variables that will be used in the function
        int counter = 0;
        int userChoice = 0;
        int userRowNumber = 0;
        char startingSeat = ' ';
        String columnValue = " ";
        int numberOfAdultSeats = 0;
        int numberOfChildSeats = 0;
        int numberOfSeniorSeats = 0;
        int numberOfTotalTickets = 0;
        int auditoriumChoice = 0;
        int columnCounter = 0;
        int rowCounter = 0;
        Node bestNode = null;
        orders customerOrder = new orders();

        // prompt the user with a 3 choice menu on which auditorium they should choose
        boolean valid = false;
        while(!valid)
        {
            try
            {
                // prompting the user with the menu
                System.out.println("Choose one of the auditoriums to reserve seats in");
                System.out.println("1. Auditorium 1");
                System.out.println("2. Auditorium 2");
                System.out.println("3. Auditorium 3");

                // collecting the user input
                auditoriumChoice = Integer.parseInt(inputScanner.next());

                // validating the user input, making sure it's between 1 and 3
                if(auditoriumChoice < 1 || auditoriumChoice > 3)
                {
                    // if not then throw an exception
                    throw new Exception();
                }
                // set loop control varaible to true
                valid = true;
            }
            // catch an exception
            catch(Throwable exception)
            {
                // print out this message if exception is caught
                System.out.println("Invalid Choice");
            }
        }

        // if the user chose auditorium 1
        if(auditoriumChoice == 1)
        {
            // set the variables to auditorium1, set the order auditorium to 1, find auditorium 1's column number and row number and print
            // auditorium 1
            currentAuditorium = Auditorium1;
            customerOrder.setAuditorium("Auditorium 1");
            columnCounter = CountNumberOfColumns(Auditorium1);
            rowCounter = CountNumberOfRows(Auditorium1);
            printAuditorium(Auditorium1, columnCounter);
        }
        // if the user chose auditorium 2
        else if(auditoriumChoice == 2)
        {
            // set the variables to auditorium2, set the order auditorium to 2, find auditorium 2's column number and row number and print
            // auditorium 2
            currentAuditorium = Auditorium2;
            customerOrder.setAuditorium("Auditorium 2");
            columnCounter = CountNumberOfColumns(Auditorium2);
            rowCounter = CountNumberOfRows(Auditorium2);
            printAuditorium(Auditorium2, columnCounter);
        }
        // if the user chose auditorium 3
        else
        {
            // set the variables to auditorium3, set the order auditorium to 3, find auditorium 3's column number and row number and print
            // auditorium 3
            currentAuditorium = Auditorium3;
            customerOrder.setAuditorium("Auditorium 3");
            columnCounter = CountNumberOfColumns(Auditorium3);
            rowCounter = CountNumberOfRows(Auditorium3);
            printAuditorium(Auditorium3, columnCounter);
        }

        // asking the user for a row and validating that row through the validateRow function and storing it inside of the userRowNumber variable
        userRowNumber = validateRow(inputScanner, userRowNumber, rowCounter);

        // asking the user which column they would like
        System.out.println("Which column would you like your first seat to be?");

        // collecting the user choice on the starting seat value and validating it with the columnValidator function
        startingSeat = columnValidator(inputScanner, columnCounter);

        // asking the user for adult, child and senior ticket numbers and validating and storing those numbers
        // through the validateSeat function and the following prompts
        System.out.println("How many adult tickets would you like?");
        numberOfAdultSeats = validateSeat(inputScanner, numberOfAdultSeats);

        System.out.println("How many child tickets would you like?");
        numberOfChildSeats = validateSeat(inputScanner, numberOfChildSeats);

        System.out.println("How many senior tickets would you like?");
        numberOfSeniorSeats = validateSeat(inputScanner, numberOfSeniorSeats);

        // calculating the total number of tickets with the user entered information about the adult, child and senior seat tickets
        numberOfTotalTickets = numberOfAdultSeats + numberOfChildSeats + numberOfSeniorSeats;

        // checking to see if the seats the user wants are possible or not through the checkAvailability function
        boolean isAvailable = checkAvailability(currentAuditorium, numberOfTotalTickets, userRowNumber, startingSeat);

        // if the seats are available
        if(isAvailable)
        {
            // reserve those seats with the reserve seats method
            reserveSeats(currentAuditorium, userRowNumber, startingSeat, numberOfAdultSeats, numberOfChildSeats, numberOfSeniorSeats, customerOrder, 0);
        }
        // if they are not available
        else
        {
            // call the bestAvailable method which will return the best available seat and store it inside of bestNode
           bestNode = bestAvailable(currentAuditorium, numberOfTotalTickets, rowCounter, columnCounter);

           // creation of variables to be used in this part of the code
           int downNumber = 0;
           int rightNumber = 0;
           int seatNumber = 0;
           Node tempNodeHolder = bestNode;

           // if there is a best seat
           if(bestNode != null)
           {
               // while loop loops until bestNode is null
               while(bestNode != null)
               {
                   // move bestNode downward until it is null
                   bestNode = bestNode.getDownNode();
                   // each time it moves downwards increment downNumber
                   downNumber++;
               }
               // decrement downNumber once to account for the null value
               downNumber--;

               // restore bestNode to the node it originially was pointing at
               bestNode = tempNodeHolder;

               // calculate the row that bestNode resides in and store it inside of userRowNumber
               userRowNumber = rowCounter - downNumber;

               // while loop loops until bestNode is null
               while(bestNode != null)
               {
                   // keeping moving bestNode to the right until it is null
                   bestNode = bestNode.getRightNode();
                   // everytime it moves to the right increment rightNumber
                   rightNumber++;
               }
               // decrement it once to account for the null value
               rightNumber--;

               // calculate which seat number the bestNode is in the row
               seatNumber = columnCounter - rightNumber;

               // variable to store the char of the bestSeat
               startingSeat = 'A';

               // variable to store the end of the bestSeat
               char endingSeat;

               // for loop increments seatNumber-1 times
               for(int i = 0; i < seatNumber-1; i++)
               {
                   // increment seatNumber each iteration
                   startingSeat++;
               }
               // startingSeat now holds the char value for the start of the bestSeat and store that value in endingSeat as well
               endingSeat = startingSeat;

               // increment endingSeat until it is numberOfTotalTickets away from the startingSeat
               for(int i = 1; i < numberOfTotalTickets; i++)
               {
                   endingSeat++;
               }

               // print the best series of seats to the user and ask if them they would like to reserve them
               System.out.println("The Best available seats are " + userRowNumber+startingSeat + "-" + userRowNumber+endingSeat);
               System.out.println("Would you like to reserve these seats?");

               // store they're input
               String userYesOrNo = inputScanner.next();

               // if it's a yes
               if(userYesOrNo.equals("Y"))
               {
                   // reserve the seats
                   reserveSeats(currentAuditorium, userRowNumber, startingSeat, numberOfAdultSeats, numberOfChildSeats, numberOfSeniorSeats, customerOrder, 0);
               }
           }
           // is bestNode is null
           else
           {
               // there are no bestSeats and print this message
               System.out.println("No seats available");
           }
        }

        // if adultSeatCount, childSeatCount and seniorSeatCount all equal 0 at the end of making an order
        if(customerOrder.getAdultSeatCount() == 0 && customerOrder.getChildSeatCount() == 0
        && customerOrder.getSeniorSeatCount() == 0)
        {
            // set that order to null
            customerOrder = null;
        }
        else
        {
            // if any one of them is not zero, then add the order to the order list
            tempHolder = (userInfo) database.get(userName);
            tempHolder.addToOrderList(customerOrder);
        }
    }

    // function takes the auditorium string from an order and returns and auditorium object appropriate to that auditorium string, takes
    // four parameters
    public static Auditorium whichAuditorium(String thisAuditorium, Auditorium Auditorium1, Auditorium Auditorium2,
                                             Auditorium Auditorium3)
    {
        // if the auditorium string is equal to 1
        if(thisAuditorium.equals("Auditorium 1"))
        {
            // return auditorium 1
            return Auditorium1;
        }
        // if the auditorium string is equal to 2
        else if(thisAuditorium.equals("Auditorium 2"))
        {
            // return auditorium 2
            return Auditorium2;
        }
        // if the auditorium string is equal to 3
        else
        {
            // return auditorium 3
            return Auditorium3;
        }
    }

    // function takes three parameters and deletes the seat entered from the order entered and seats the location of that seat in the
    // auditorium to a period
    public static void deleteSeatFromOrder(Node location, orders order, String seat)
    {
        // if the seats ticket type is an A
        if(((Seat)location.getPayload()).getTicketType() == 'A')
        {
            // remove 1 integer from the AdultSeatCount and remove that seat from the order
            order.setAdultSeatCount(order.getAdultSeatCount() - 1);
            order.removeSeat(seat);
        }
        // if the seats ticket type is an C
        else if(((Seat)location.getPayload()).getTicketType() == 'C')
        {
            // remove 1 integer from the ChildSeatCount and remove that seat from the order
            order.setChildSeatCount(order.getChildSeatCount() - 1);
            order.removeSeat(seat);
        }
        // if the seats ticket type is an S
        else
        {
            // remove 1 integer from the SeniorSeatCount and remove that seat from the order
            order.setSeniorSeatCount(order.getSeniorSeatCount() - 1);
            order.removeSeat(seat);
        }
        // set the location of that seat in the auditorium's ticket type to a period
        ((Seat)location.getPayload()).setTicketType('.');
    }

    // function takes multiple parameters and checks to see if the number of seats, in the row and column that the user wants is available
    // returns true if so and false if not
    public static boolean checkAvailability(Auditorium auditorium, int totalTicketCount, int rowNumber, char columnValue){
        // node variable to traverse through the auditorium
        Node<Seat> auditoriumTraverser = auditorium.getHead();
        // for loop used to get to the correct row
        for(int i = 1; i < rowNumber; i++)
        {
            auditoriumTraverser = auditoriumTraverser.getDownNode();
        }
        // for loop used to get to the correct starting position in that row
        for(char i = 'A'; i < columnValue; i++)
        {
            auditoriumTraverser = auditoriumTraverser.getRightNode();
        }
        // for loop used to count totalTicketCount seats from that starting position
        for(int i = 0; i < totalTicketCount; i++)
        {
            // if any of the seats from the starting one to the end one are not a period
            if(auditoriumTraverser.getPayload().getTicketType() != '.')
            {
                // return false
                return false;
            }
            // move to the next seat on the right
            auditoriumTraverser = auditoriumTraverser.getRightNode();
        }
        // if false is not returned then return true
        return true;
    }

    // reserveSeats method takes multiple parameters and reserves that number of seats that the user wants in the appropriate row
    // and the appropriate number of columns
    public static void reserveSeats(Auditorium auditorium, int rowNumber, char columnValue, int adultTickets, int childTickets,
                                    int seniorTickets, orders customerOrder, int indicator)
    {
        // node variable created to traverse through the auditorium
        Node<Seat> auditoriumTraverser = auditorium.getHead();
        String SeatRowName = Integer.toString(rowNumber);
        String fullSeatName;
        String tempSeatHolder;
        // for loop used to get to the correct row
        for(int i = 1; i < rowNumber; i++)
        {
            auditoriumTraverser = auditoriumTraverser.getDownNode();
        }
        // for loop used to get to the correct starting seat in that row
        for(char i = 'A'; i < columnValue; i++)
        {
            auditoriumTraverser = auditoriumTraverser.getRightNode();
        }
        // for loop reserves the first adultticket number of seats with an A
        for(int i = 0; i < adultTickets; i++)
        {
            fullSeatName = "";
            // reserve the seat with an A
            (auditoriumTraverser.getPayload()).setTicketType('A');
            tempSeatHolder = String.valueOf((auditoriumTraverser.getPayload()).getSeat());
            fullSeatName = SeatRowName + tempSeatHolder;
            if(indicator == 0)
            {
                customerOrder.addToSeatList(fullSeatName);
            }
            else
            {
                if(!customerOrder.doesContain(fullSeatName))
                {
                    customerOrder.addToSeatList(fullSeatName);
                }
            }
            // move to the next seat on the right
            auditoriumTraverser = auditoriumTraverser.getRightNode();
        }
        // for loop reserves the next childTicket number of seats with a C
        for(int i = 0; i < childTickets; i++)
        {
            fullSeatName = "";
            // reserve the seat with a C
            (auditoriumTraverser.getPayload()).setTicketType('C');
            tempSeatHolder = String.valueOf((auditoriumTraverser.getPayload()).getSeat());
            fullSeatName = SeatRowName + tempSeatHolder;
            if(indicator == 0)
            {
                customerOrder.addToSeatList(fullSeatName);
            }
            else
            {
                if(!customerOrder.doesContain(fullSeatName))
                {
                    customerOrder.addToSeatList(fullSeatName);
                }
            }
            // move to the next seat
            auditoriumTraverser = auditoriumTraverser.getRightNode();
        }
        // for loop reserves the next seniorTicket number of seats with a S
        for(int i = 0; i < seniorTickets; i++)
        {
            fullSeatName = "";
            // reserve the seat with an S
            (auditoriumTraverser.getPayload()).setTicketType('S');
            tempSeatHolder = String.valueOf((auditoriumTraverser.getPayload()).getSeat());
            fullSeatName = SeatRowName + tempSeatHolder;
            if(indicator == 0)
            {
                customerOrder.addToSeatList(fullSeatName);
            }
            else
            {
                if(!customerOrder.doesContain(fullSeatName))
                {
                    customerOrder.addToSeatList(fullSeatName);
                }
            }
            // move to the next seat on the right
            auditoriumTraverser = auditoriumTraverser.getRightNode();
        }

        if(indicator == 0)
        {
            customerOrder.setAdultSeatCount(adultTickets);
            customerOrder.setChildSeatCount(childTickets);
            customerOrder.setSeniorSeatCount(seniorTickets);
        }
        else
        {
            customerOrder.setAdultSeatCount(adultTickets + customerOrder.getAdultSeatCount());
            customerOrder.setChildSeatCount(childTickets + customerOrder.getChildSeatCount());
            customerOrder.setSeniorSeatCount(seniorTickets + customerOrder.getSeniorSeatCount());
        }
    }

    // bestAvailable function takes four parameters and returns a node that points to the best seat
   public static Node<Seat> bestAvailable(Auditorium auditorium, int quantity, int rowNumber, int columnNumber) {
       Node<Seat> auditoriumHead = (Node<Seat>)auditorium.getHead();
       Node<Seat> rowHead = auditoriumHead;
       Node<Seat> bestNode = null;
       double bestDistance = Double.MAX_VALUE;



       while (rowHead != null) {
           Node<Seat> rowTail = rowHead;

           process: for (; rowTail != null;) {
               Node<Seat> startingNode = rowTail;
               for (int i = 0; i < quantity; i++) {
                   if (startingNode == null) {
                       rowTail = rowTail.getRightNode();   //we increment rowTail here because if we continue,
                       //the incrementer at the bottom is skipped
                       continue process;
                   } else if ( (startingNode.getPayload()).getTicketType() != '.') {
                       rowTail = rowTail.getRightNode();   //we increment rowTail here because if we continue,
                       //the incrementer at the bottom is skipped
                       continue process;
                   }
                   startingNode = startingNode.getRightNode();
               }

               //Find the distance from the group of seats to the center
               int row = ( rowTail.getPayload() ).getRow();
               int seat = ( rowTail.getPayload() ).getSeat() - 65;
               double verDist = Math.abs(row - (rowNumber+1)/2.0);
               double horDist = Math.abs(seat + quantity/2.0 - columnNumber/2.0);
               double newDist = Math.pow(Math.pow(verDist, 2) + Math.pow(horDist, 2) , 0.5);


               if (newDist < bestDistance) {
                   bestDistance = newDist;
                   bestNode = rowTail;
               } else if (newDist == bestDistance) {
                   int bestRow = ( bestNode.getPayload() ).getRow();
                   if (Math.abs( (rowNumber+1)/2 - row) < Math.abs( (rowNumber+1)/2 - bestRow))
                       bestNode = rowTail;
               }

               rowTail = rowTail.getRightNode();
           }
           rowHead = rowHead.getDownNode();
       }

       return bestNode;
   }

   // adminPrint function takes four parameters and prints the sales and the values for open seats, reserved seats, adult, child and senior seats
    // inside of the auditorium passed to it
    public static void adminPrint(String auditoriumName, Auditorium auditorium, int rowCounter, int columnCounter)
    {
        // variables declared that will be used in the method
        Node auditoriumTraverser;
        Node headTracker = auditorium.getHead();
        int universalAdultAmount = 0;
        int universalChildAmount = 0;
        int universalSeniorAmount = 0;
        int reservedAmount = 0;
        int openAmount = 0;
        double total;
        // df is for formatting to two decimal places
        DecimalFormat df = new DecimalFormat("#.00");

        // outer for loop is used to loop through all of the rows
        for(int i = 0; i < rowCounter; i++)
        {
            // setting auditorium equal to headTracker, which keeps track of the start of every row
            auditoriumTraverser = headTracker;
            // inner loop used to loop through every seat in a row
            for(int j = 0; j < columnCounter; j++)
            {
                // of that seat is not null and a A
                if(((Seat)auditoriumTraverser.getPayload()).getTicketType() == 'A' || auditoriumTraverser == null)
                {
                    // increment the total ticket amount and the adult ticket amount
                    universalAdultAmount++;
                }
                // if it's not null and a C
                else if(((Seat)auditoriumTraverser.getPayload()).getTicketType() == 'C' || auditoriumTraverser == null)
                {
                    // increment the total ticket amount and the child ticket amount
                    universalChildAmount++;
                }
                // if it's not null and a S
                else if(((Seat)auditoriumTraverser.getPayload()).getTicketType() == 'S' || auditoriumTraverser == null)
                {
                    // increment the total ticket amount and the senior ticket amount
                    universalSeniorAmount++;
                }
                else
                {
                    openAmount++;
                }
                // move the traversor to the seat on the right
                auditoriumTraverser = auditoriumTraverser.getRightNode();
            }
            // set head tracker to the start of the next row
            headTracker = headTracker.getDownNode();
        }

        // print out the total seats, the total tickets, adult, child and senior tickets along with the total cost
        // and total sales
        total = (universalAdultAmount*10.00)+(universalChildAmount*5.00)+(universalSeniorAmount*7.50);
        reservedAmount = universalAdultAmount + universalChildAmount + universalSeniorAmount;
        System.out.println(auditoriumName + "\t" + openAmount + "\t" + reservedAmount + "\t" + universalAdultAmount + "\t" +
                universalChildAmount + "\t" + universalSeniorAmount + "\t" + "$" + df.format(total));
    }

    // adminTotalPrint takes all the auditoriums, they're rows and columns as parameters and prints the collective total of they're values
    // for open seats, reserved seats, adult, child and senior seats
    public static void adminTotalPrint(Auditorium auditorium1, Auditorium auditorium2, Auditorium auditorium3, int rowCounter1, int columnCounter1,
                                       int rowCounter2, int columnCounter2, int rowCounter3, int columnCounter3)
    {
        // objects and variables that are created to be used inside of the function
        Node auditoriumTraverser;
        Node headTracker;
        int universalAdultAmount = 0;
        int universalChildAmount = 0;
        int universalSeniorAmount = 0;
        int reservedAmount = 0;
        int openAmount = 0;
        DecimalFormat df = new DecimalFormat("#.00");

        int totalAdultAmount = 0;
        int totalChildAmount = 0;
        int totalSeniorAmount = 0;
        int totalReservedAmount = 0;
        int totalOpenAmount = 0;

        double total;
        int counter = 1;
        int rowCounter = 0;
        int columnCounter = 0;

        // counter loops three times for all three auditoriums
        while(counter <= 3)
        {
            // set all the counter values to 0 whenever a new auditorium is going to be counted
            universalAdultAmount = 0;
            universalChildAmount = 0;
            universalSeniorAmount = 0;
            openAmount = 0;
            reservedAmount = 0;

            // if counter equals 1
            if(counter == 1)
            {
                // set the appropriate row and columns counters and head of the auditorium
                rowCounter = rowCounter1;
                columnCounter = columnCounter1;
                headTracker = auditorium1.getHead();
            }
            // if counter equals 1
            else if(counter == 2)
            {
                // set the appropriate row and columns counters and head of the auditorium
                rowCounter = rowCounter2;
                columnCounter = columnCounter2;
                headTracker = auditorium2.getHead();
            }
            // if counter equals 1
            else
            {
                // set the appropriate row and columns counters and head of the auditorium
                rowCounter = rowCounter3;
                columnCounter = columnCounter3;
                headTracker = auditorium3.getHead();
            }

            // for loop loops for each row in the auditorium
            for(int i = 0; i < rowCounter; i++)
            {
                // setting auditorium equal to headTracker, which keeps track of the start of every row
                auditoriumTraverser = headTracker;
                // inner loop used to loop through every seat in a row
                for(int j = 0; j < columnCounter; j++)
                {
                    // of that seat is not null and a A
                    if(((Seat)auditoriumTraverser.getPayload()).getTicketType() == 'A' || auditoriumTraverser == null)
                    {
                        // increment the total ticket amount and the adult ticket amount
                        universalAdultAmount++;
                    }
                    // if it's not null and a C
                    else if(((Seat)auditoriumTraverser.getPayload()).getTicketType() == 'C' || auditoriumTraverser == null)
                    {
                        // increment the total ticket amount and the child ticket amount
                        universalChildAmount++;
                    }
                    // if it's not null and a S
                    else if(((Seat)auditoriumTraverser.getPayload()).getTicketType() == 'S' || auditoriumTraverser == null)
                    {
                        // increment the total ticket amount and the senior ticket amount
                        universalSeniorAmount++;
                    }
                    else
                    {
                        openAmount++;
                    }
                    // move the traversor to the seat on the right
                    auditoriumTraverser = auditoriumTraverser.getRightNode();
                }
                // set head tracker to the start of the next row
                headTracker = headTracker.getDownNode();
            }

            // Store the total from an auditorium inside appropriate variables
            totalAdultAmount = totalAdultAmount + universalAdultAmount;
            totalChildAmount = totalChildAmount + universalChildAmount;
            totalSeniorAmount = totalSeniorAmount + universalSeniorAmount;
            totalOpenAmount = totalOpenAmount + openAmount;
            reservedAmount = universalAdultAmount + universalChildAmount + universalSeniorAmount;
            totalReservedAmount = totalReservedAmount + reservedAmount;

            //increment counter
            counter++;
        }

        // print the totals out
        total = (totalAdultAmount*10.00)+(totalChildAmount*5.00)+(totalSeniorAmount*7.50);
        System.out.println("Total" + "\t" + "\t" + totalOpenAmount + "\t" + totalReservedAmount + "\t" + totalAdultAmount + "\t" +
                totalChildAmount + "\t" + totalSeniorAmount + "\t" + "$" + df.format(total));
    }

    // printAuditorium function takes two parameters and prints the auditorium passed to it as a parameter
    public static void printAuditorium(Auditorium auditorium, int columnCounter) {
        // creation of variables that will be used in the program
        boolean print = true;
        Node rowHead = auditorium.getHead();
        char columnLetter = 'A';
        int rowNumber = 1;
        // printing out the information of the auditorium to the console
        System.out.print("  ");
        for (int i = 0; i < columnCounter; i++) {
            System.out.print(columnLetter);
            columnLetter++;
        }
        System.out.println();
        while (print) {
            Node auditoriumTraverser = rowHead;
            System.out.print(rowNumber + " ");
            while (auditoriumTraverser != null) {
                System.out.print(((Seat) auditoriumTraverser.getPayload()).getTicketType());
                auditoriumTraverser = auditoriumTraverser.getRightNode();
            }
            System.out.print("\n");
            rowNumber++;
            rowHead = rowHead.getDownNode();

            // once you reach the last row
            if (rowHead == null) {
                // set the while loop control variable to false
                print = false;
            }
        }

    }

    // writeAuditorium function writes whatever auditorium is passed to it to the appropriate file which is passed in the form
    // of a pathName
    public static void writeAuditorium(Auditorium auditorium, int columnCounter, String pathName) throws IOException {
        // creation fo variables that will be used in the method
        boolean print = true;
        // creating the file
        File newAuditorium = new File(pathName);
        newAuditorium.createNewFile();
        // creating a writer to write to the file
        FileWriter writer = new FileWriter(newAuditorium);
        Node rowHead = auditorium.getHead();

        // writing the information of the auditorium to the file
        while(print)
        {
            Node auditoriumTraverser = rowHead;
            while(auditoriumTraverser != null)
            {
                writer.write(((Seat)auditoriumTraverser.getPayload()).getTicketType());
                auditoriumTraverser = auditoriumTraverser.getRightNode();
            }
            writer.write("\n");
            rowHead = rowHead.getDownNode();

            // once you have reached the last row
            if(rowHead == null)
            {
                // set the while loop control variable to false and close the writer
                print = false;
                writer.close();
            }
        }

    }
}

// userInfo class is used to store the list of orders and the password for each user/userName
class userInfo
{
    // creation of private variable to be used inside of the class
    private String password;
    private orders userOrder;
    private List<orders> OrderList = new ArrayList<orders>();

    // constructor for the class
    public userInfo()
    {
        password = "";
    }

    // setter method for userOrder
    public void setUserOrder(orders eOrder)
    {
        userOrder = eOrder;
    }

    // setter method for password
    public void setPassword(String ePassword)
    {
        password = ePassword;
    }

    // getter method for userOrder
    public orders getUserOrder(int index)
    {
        orders speceficOrder = OrderList.get(index-1);
        return speceficOrder;
    }

    // getter method for password
    public String getPassword()
    {
        return password;
    }

    // addToOrderList function adds whatever order passed into it to the orderList
    public void addToOrderList(orders order)
    {
        OrderList.add(order);
    }

    // function returns the orderList's size
    public int getListSize()
    {
        return OrderList.size();
    }

    // function removes an order from the orderList
    public void removeOrders(orders order)
    {
        OrderList.remove(order);
    }

    // function prints orders from the order list
    public void printOrders()
    {
        // temporary order object to access orders from the orderList
        orders tempOrderHolder;

        // for loop loops the orderList size number of times
        for(int i = 0; i < OrderList.size(); i++)
        {
            // get the order from the orderList and store it inside of the tempOrderHolder
            tempOrderHolder = OrderList.get(i);

            // print the order's auditorium
            System.out.print(tempOrderHolder.getAuditorium() + ", ");
            //print the seat list inside of the order
            tempOrderHolder.printSeatList();
            // print the audltSeatCount inside of the order
            System.out.print(tempOrderHolder.getAdultSeatCount() + " adult, ");
            // print the childSeatCount inside of the order
            System.out.print(tempOrderHolder.getChildSeatCount() + " child, ");
            // print the seniorSeatCount inside of the order
            System.out.println(tempOrderHolder.getSeniorSeatCount() + " senior");
        }
    }

    // print the orders from the order list along with the sales values
    public void printReceipt()
    {
        // temporary order object to access orders from the orderList
        orders tempOrderHolder;

        // creation of variables to be used in the function
        DecimalFormat df = new DecimalFormat("#0.00");
        double total;
        double completeTotal = 0.00;

        // for loop loops the orderList size number of times
        for(int i = 0; i < OrderList.size(); i++)
        {
            // get the order from the orderList and store it inside of the tempOrderHolder
            tempOrderHolder = OrderList.get(i);

            // print the order's auditorium
            System.out.print(tempOrderHolder.getAuditorium() + ", ");
            //print the seat list inside of the order
            tempOrderHolder.printSeatList();
            // print the audltSeatCount inside of the order
            System.out.print(tempOrderHolder.getAdultSeatCount() + " adult, ");
            // print the childSeatCount inside of the order
            System.out.print(tempOrderHolder.getChildSeatCount() + " child, ");
            // print the seniorSeatCount inside of the order
            System.out.println(tempOrderHolder.getSeniorSeatCount() + " senior");
            // calculate the total with the adultSeatCount, childSeatCount, seniorSeatCount and their sales values
            total = (tempOrderHolder.getAdultSeatCount() * 10.00) + (tempOrderHolder.getChildSeatCount() * 5.00) + (tempOrderHolder.getSeniorSeatCount() * 7.50);
            // print out the total, formatted
            System.out.println("Order Total: $" + df.format(total));

            // calculate the complete total
            completeTotal = completeTotal + total;
        }
        // print out the formatted completeTotal
        System.out.println("Customer Total: $" + df.format(completeTotal));
    }

    // print the orders with they're order numbers
    public void printToUpdateOrders()
    {
        // temporary order object to access orders from the orderList
        orders tempOrderHolder;
        int orderNumber = 0;

        // for loop loops the orderList size number of times
        for(int i = 0; i < OrderList.size(); i++)
        {
            orderNumber = i + 1;
            // get the order from the orderList and store it inside of the tempOrderHolder
            tempOrderHolder = OrderList.get(i);
            // printing the order number
            System.out.println("Order Number " + orderNumber + ":");
            // print the order's auditorium
            System.out.print(tempOrderHolder.getAuditorium() + ", ");
            //print the seat list inside of the order
            tempOrderHolder.printSeatList();
            // print the audltSeatCount inside of the order
            System.out.print(tempOrderHolder.getAdultSeatCount() + " adult, ");
            // print the childSeatCount inside of the order
            System.out.print(tempOrderHolder.getChildSeatCount() + " child, ");
            // print the seniorSeatCount inside of the order
            System.out.println(tempOrderHolder.getSeniorSeatCount() + " senior");
        }
    }
}

// orders class is used to store the auditorium that each order belongs to along with the adultSeatCount, childSeatCount and seniorSeatCount
// that each order has
class orders
{
    // creation of private variables that will be used inside of this class, and that make up this class
    private String Auditorium;
    private List<String> seatList;
    private int adultSeatCount;
    private int childSeatCount;
    private int seniorSeatCount;

    // constructor that intializes all variables
    public orders()
    {
        Auditorium = "";
        adultSeatCount = 0;
        childSeatCount = 0;
        seniorSeatCount = 0;
        seatList = new ArrayList<String>();
    }
    // overloaded contructor that sets the variables to the values in the parameters
    public orders(String eAuditorium, int eAdultSeatCount, int eChildSeatCount, int eSeniorSeatCount)
    {
        Auditorium = eAuditorium;
        adultSeatCount = eAdultSeatCount;
        childSeatCount = eChildSeatCount;
        seniorSeatCount = eSeniorSeatCount;
        seatList = new ArrayList<String>();
    }

    // setter method for auditorium
    public void setAuditorium(String eAuditorium)
    {
        Auditorium = eAuditorium;
    }
    // setter method for adultSeatCount
    public void setAdultSeatCount(int eAdultSeatCount)
    {
        adultSeatCount = eAdultSeatCount;
    }
    // setter method for childSeatCount
    public void setChildSeatCount(int eChildSeatCount)
    {
        childSeatCount = eChildSeatCount;
    }
    // setter method for seniorSeatCount
    public void setSeniorSeatCount(int eSeniorSeatCount)
    {
        seniorSeatCount = eSeniorSeatCount;
    }
    // getter method for auditorium
    public String getAuditorium()
    {
        return Auditorium;
    }
    // getter method for adultSeatCount
    public int getAdultSeatCount()
    {
        return adultSeatCount;
    }
    // getter method for childSeatCount
    public int getChildSeatCount()
    {
        return childSeatCount;
    }
    // getter method for seniorSeatCount
    public int getSeniorSeatCount()
    {
        return seniorSeatCount;
    }
    // function adds a seat to the seat list in order
    public void addToSeatList(String seat)
    {
        // variables that will be used in the function
        int index;
        boolean inserted;

        // if the list is empty
        if(seatList.isEmpty())
        {
            // add the seat to the list
            seatList.add(seat);
        }
        // otherwise
        else
        {
            // set the index and inserted value to 0 and false
            index = 0;
            inserted = false;

            // while inserted is false
            while(!inserted)
            {
                // if index is the same as the size of the list
                if(index == seatList.size())
                {
                    // add the seat to the list and set inserted to true
                    seatList.add(seat);
                    inserted = true;
                }
                // if the seat is less than the seat in the list
                else if(seat.compareTo(seatList.get(index)) < 0)
                {
                    // add the seat to that index and set inserted to true
                    seatList.add(index, seat);
                    inserted = true;
                }
                // increment
                index++;
            }
        }
    }

    // function returns the seat at an index
    public String getSeat(int index)
    {
        return seatList.get(index);
    }

    // function returns the size of the list
    public int getListSize()
    {
        return seatList.size();
    }

    // function removes a seat from the seatList
    public void removeSeat(String seat)
    {
        seatList.remove(seat);
    }

    // function checks to see if a seat is inside of the seatList
    public boolean doesContain(String seat)
    {
        // for loop loops the seatList size number of times
        for(int i = 0; i < seatList.size(); i++)
        {
            // if seatList contains a seat
            if(seatList.contains(seat))
            {
                // return true
                return true;
            }
        }
        // otherwise return false
        return false;
    }

    // function prints the seatList
    public void printSeatList()
    {
        // variables that are used in the function
        String tempSeatHolder;
        int commaChecker;

        // for loop loops the seatList size number of times
        for(int i = 0; i < seatList.size(); i++)
        {
            // tempSeatHolder temporarily holds a seat from the seatList
            tempSeatHolder = seatList.get(i);
            commaChecker = i+1;
            // if there is a comma when the seatlist as no next values
            if(commaChecker == seatList.size())
            {
                // just print the value
                System.out.println(tempSeatHolder);
            }
            else
            {
                // otherwise print the seat then a comma
                System.out.print(tempSeatHolder + ",");
            }
        }
    }
}
