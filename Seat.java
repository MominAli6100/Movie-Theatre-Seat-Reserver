//package com.company;

public class Seat {
    // variables that belong to the seat class
    public int row;
    public char seat;
    public char ticketType;

    // default Seat constructor
    public Seat()
    {
        row = 0;
        seat = ' ';
        ticketType = ' ';
    }

    // overloaded seat contrcutor
    public Seat(int enteredRow, char enteredSeat, char enteredTicketType)
    {
        row = enteredRow;
        seat = enteredSeat;
        ticketType = enteredTicketType;
    }

    // setter methods for the variables
    public void setRow(int enteredRow)
    {
        row = enteredRow;
    }

    public void setSeat(char enteredSeat)
    {
        seat = enteredSeat;
    }

    public void setTicketType(char enteredTicketType)
    {
        ticketType = enteredTicketType;
    }

    // getter methods for the variables
    public int getRow()
    {
        return row;
    }

    public char getSeat()
    {
        return seat;
    }

    public char getTicketType()
    {
        return ticketType;
    }
}
