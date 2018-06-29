package com.home.processor.job;

public class SinglyLinkedList {
    private Node head;
    
    public static void main(String[] args) {
        SinglyLinkedList.Node head = new SinglyLinkedList.Node(1);
        SinglyLinkedList linkedlist = new SinglyLinkedList(head); 
       
        linkedlist.add(new SinglyLinkedList.Node(2)); 
        linkedlist.add(new SinglyLinkedList.Node(3)); 
        linkedlist.print(); 
    
        linkedlist.reverse(); 
       
        linkedlist.print();

    }
    
    
    public SinglyLinkedList(Node head) {
        super();
        this.head = head;
    }

    static class Node { 
        private int data;
        private Node next; 
        
        public Node(int data) {
            this.data = data; 
            
        }
        public int data() {
            
            return data; 
            
         } 
        
        public Node next() {
             return next; 
        } 
   } 
   
    public void add(Node node) { 
        Node current = head;
        while (current != null) {
            if (current.next == null)  { 
                current.next = node; 
             break;
                
        } 
        current = current.next; 
        } 
        
    }

    public void print() { 
        Node node = head;
        while (node != null) { 
            System.out.print(node.data() + " "); 
            node = node.next(); 
            } System.out.println(""); 
   }
    
    public void reverse() { 
        Node pointer = head;
        Node previous = null;
        Node current = null;
        
        while (pointer != null) {
            current = pointer; 
            pointer = pointer.next;
            // reverse the link 
            current.next = previous; 
            previous = current;
            head = current; 
            
         } 
        }
    
}
