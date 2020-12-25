//package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Auditorium{
    // setting head to null
    Node head = null;

    // auditorium constructor
    public Auditorium(String fileName) throws FileNotFoundException {
        // use the fileName to created a scanner for that file
        Scanner fileScanner = new Scanner(new File(fileName));
        // set the rowNumber to 1
        int rowNumber = 1;
        // while the file has another line
        while(fileScanner.hasNext())
        {
            // take that line and put it in the rowHolder string variable
            String rowHolder = fileScanner.next();
            // create a variable called seatLetter and set it to A the start of every row
            char seatLetter = 'A';
            // set the tail to null along with the head of the next row
            Node tail = null;
            Node nextRHead = null;
            // for loop, loops through the string
            for(int i = 0; i < rowHolder.length(); i++)
            {
                // and takes the characters from it and puts it in seatType
                char seatType = rowHolder.charAt(i);
                // and created a node that points to a new seat that is created by passing the variables to the constructor
                Node nextNode = new Node(new Seat(rowNumber, seatLetter, seatType));
                // check to see if the start of the auditorium is null
                if(head == null)
                {
                    // if so then make the newly created seat the head and the tail
                    head = nextNode;
                    tail = nextNode;
                }
                // if only tail is null, or at the end of the row
                else if(tail == null)
                {
                    // set tail to the newly created seat and set the start of the next row to that seat
                    tail = nextNode;
                    nextRHead = nextNode;
                }
                // if at the middle of the row
                else {
                    // connect the newly created seat to the seat on the left
                    tail.setRightNode(nextNode);
                    nextNode.setleftNode(tail);
                    tail = nextNode;
                }
                // increment the seat letter
                seatLetter++;
            }
            // if you are at any row except for the first
            if(rowNumber != 1)
            {
                // set the auditorum traverser to the head and the next row traversor to the next row head
                Node auditoriumTraverser = head;
                Node nextRTraversor = nextRHead;
                // while the node beneath the traverser is not null
                while(auditoriumTraverser.getDownNode() != null)
                {
                    // move down a row
                    auditoriumTraverser = auditoriumTraverser.getDownNode();
                }
                // if the traversor itself is not null
                while(auditoriumTraverser != null)
                {
                    // then connect the traversor to the traversor of the next row below it and the one below it to itself
                    auditoriumTraverser.setDownNode(nextRTraversor);
                    nextRTraversor.setUpNode(auditoriumTraverser);
                    // move both the nodes to the right
                    auditoriumTraverser = auditoriumTraverser.getRightNode();
                    nextRTraversor = nextRTraversor.getRightNode();
                }
            }
            // increment the rowNumber
            rowNumber++;
        }
    }
    // setter method for head
    public void setHead(Node eFirstNode)
    {
        head = eFirstNode;
    }
    // getter method for head
    public Node getHead()
    {
        return head;
    }
}

