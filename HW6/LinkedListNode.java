/**
 * Walter Blair
 * Assignment: assignment1
 *
 * I worked alone and consulted the following references:
 * 		my work from CSCI 230 (July 2016)
 *
 * LinkedListNode used for LinkedList. Head is vertex and subsequent
 * nodes are Head's edges.
 * Has previous and visited attributes modified and reset by isPath and getPath.
 * Has a total number of edges to record how many connections total run
 * from this vertex and to this vertex to determine how many time an edge
 * should be checked in order to prevent infinite recursive calls in isPath().
 */
public class LinkedListNode {
    private LinkedListNode next;
    private int edgeWeight, pointer;
    private Object data;
    private int visited;
    private int numberOfEdges;
    private LinkedListNode previous;

    public LinkedListNode(Object data) {
        this.data = data;
        pointer = -1;
    }

    public LinkedListNode getNext() {
        return next;
    }

    public void setNext(LinkedListNode next) {
        this.next = next;
    }

    public int getEdgeWeight() {
        return edgeWeight;
    }

    public void setEdgeWeight(int dist) {
        this.edgeWeight = dist;
    }

    public int getPointer() {
        return pointer;
    }

    public void switchPointer() {
        pointer = -pointer;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

    public LinkedListNode getPrevious() {
        return previous;
    }

    public void setPrevious(LinkedListNode previous) {
        this.previous = previous;
    }

}