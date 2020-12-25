//package com.company;

public class Node<T> {

    // variables that belong to the class Node<T>
    public Node up;
    public Node down;
    public Node left;
    public Node right;
    public T payload;

    // default construcor
    public Node()
    {
        up = null;
        down = null;
        left = null;
        right = null;
        payload = null;
    }

    // overloaded contructor
    public Node(T ePayload)
    {
        up = null;
        down = null;
        left = null;
        right = null;
        payload = ePayload;
    }
    // getter methods for the variables
    public Node getUpNode()
    {
        return up;
    }
    public Node getDownNode()
    {
        return down;
    }
    public Node getLeftNode()
    {
        return left;
    }
    public Node getRightNode()
    {
        return right;
    }
    public T getPayload()
    {
        return payload;
    }
    // setter methods for the variables
    public void setUpNode(Node eUp)
    {
        up = eUp;
    }
    public void setDownNode(Node eDown)
    {
        down = eDown;
    }
    public void setleftNode(Node eLeft)
    {
        left = eLeft;
    }
    public void setRightNode(Node eRight)
    {
        right = eRight;
    }
    public void setPayload(T ePayload)
    {
        this.payload = ePayload;
    }
}
