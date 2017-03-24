/**
 * Walter Blair
 * Assignment: assignment1
 *
 * I worked alone and consulted the following references:
 * 		my work from CSCI 232 (July 2016)
 *
 * LinkedList used by the List adjacency list. Uses LinkedListNode.
 */
public class LinkedList {
    private LinkedListNode head;

    public LinkedList() {
        this.head = null;
    }
    /**
     * Parametric constructor of LinkedList.
     * @param item of type Object.
     */
    public LinkedList(Object item) {
        this.head = new LinkedListNode(item);
    }

    /**
     * An easy unordered insert for adding edges to an existing
     * vertex's LinkedList.
     * @param data of type Object
     */
    public void insert(Object data) {
        LinkedListNode newNode = new LinkedListNode(data);
        if(head == null) {
            head = newNode;
            head.setPrevious(null);
        }
        else {
            LinkedListNode current = head;
            while(current.getNext() != null)
                current = current.getNext();
            current.setNext(newNode);
            current.getNext().setPrevious(current);
        }
    }

    /**
     * Removes specified edge Object from LinkedList
     * @param data of type Object
     */
    public void remove(Object data) {
        LinkedListNode current = head;
        while(current.getNext() != null) {
            if(current.getNext().getData().equals(data)) {
                LinkedListNode temp = current.getNext();
                current.setNext(temp.getNext());
                temp.setNext(null);
                return;
            }
            else
                current = current.getNext();
        }
    }

    public LinkedListNode getHead() {
        return head;
    }

    public void setHead(LinkedListNode head) {
        this.head = head;
    }

}