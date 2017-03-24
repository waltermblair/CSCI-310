import java.io.*;

/**
 * Created by walter on 11/14/16.
 */
public class Circuits {
    private List graph;
    private LinkedList permutation, minHC;
    private int minDist;
    private int v, e;
    PrintWriter writer;

    public Circuits(int vertices, int edges, PrintWriter writer) {
        graph = new List(123);
        v = vertices;
        e = edges;
        permutation = new LinkedList();
        minHC = new LinkedList();
        minDist = 10000;
        for (int i = 0; i < v; i++) {
            graph.addVertex(i);
            permutation.insert(i);
        }
        for (int j = 0; j < e; j++) {
            graph.addEdge(j, (j + 1) % v, j);
        }
        // Set up initial permutation for JohnsonTrotter algorithm
        LinkedListNode current = permutation.getHead();
        int k = 0;
        while(current != null) {
            current.setEdgeWeight(k++);
            current = current.getNext();
        }
        this.writer = writer;
        this.printPermutation();
    }

    /**
     * Uses JohnsonTrotter, p145 of text
     * If changed, return true
     */
    public boolean permutation() {
        LinkedListNode current = permutation.getHead();
        LinkedListNode k = new LinkedListNode(-1);
        LinkedListNode checkChange = k;
        // Find largest mobile element
        while(current != null) {
            // If current element is larger than current max...
            if((int)(current.getData()) > (int)(k.getData())) {
                // If current element points left
                if(current.getPointer() < 0) {
                    // And current element points to a non-null and smaller element
                    if (current.getPrevious() != null && (int) (current.getData()) > (int) (current.getPrevious().getData())) {
                        // then we update max mobile element
                        k = current;
                    }
                }
                // If current element points right
                else {
                    // And current element points to a non-null and smaller element
                    if (current.getNext() != null && (int) (current.getData()) > (int) (current.getNext().getData())) {
                        // then we update max mobile element
                        k = current;
                    }
                }
            }
            current = current.getNext();
        }
        if(checkChange.getData().equals(k.getData())) {
            writer.println("The optimal circuit with a total distance of " + minDist + " is: ");
            return false;
        }
        else {
            // Swap k with adjacent element k points to
            LinkedListNode swap;
            // If k is moving to the left and left is non-null
            if(k.getPointer() < 0 && k.getPrevious() != null) {
                swap = k.getPrevious();
                k.setPrevious(swap.getPrevious());
                swap.setPrevious(k);
                if(k.getPrevious() != null)
                    k.getPrevious().setNext(k);
                else
                    permutation.setHead(k);
                swap.setNext(k.getNext());
                if(k.getNext() != null)
                    k.getNext().setPrevious(swap);
                k.setNext(swap);
            }

            // Else if k is moving to the right and right is non-null
            else if(k.getPointer() > 0 && k.getNext() != null) {
                swap = k.getNext();
                k.setNext(swap.getNext());
                swap.setNext(k);
                if(k.getNext() != null)
                    k.getNext().setPrevious(k);
                swap.setPrevious(k.getPrevious());
                if(k.getPrevious() != null)
                    k.getPrevious().setNext(swap);
                else
                    permutation.setHead(swap);
                k.setPrevious(swap);
            }

            // Reverse pointers for elements that are larger than k
            current = permutation.getHead();
            while (current != null) {
                if ((int)(current.getData()) > (int)(k.getData())) {
                    current.switchPointer();
                }
                current = current.getNext();
            }
            return true;
        }
    }

    public void solve() {
        while(permutation()) {
            this.printPermutation();
            if(isCircuit()) {
                int curDist = 0;
                LinkedListNode current = permutation.getHead();
                while(current != null) {
                    curDist += current.getEdgeWeight();
                    current = current.getNext();
                }
                if(curDist < minDist) {
                    minDist = curDist;
                    minHC = permutation;
                }
            }
        }
    }

    public boolean isCircuit() {
        LinkedListNode current = permutation.getHead();
        while(current.getNext() != null) {
            if (!graph.isAdjacent(current.getData(), current.getNext().getData()))
                return false;
            current = current.getNext();
        }
        return true;
    }

    public void printPermutation() {
        LinkedListNode current = permutation.getHead();
        while(current != null) {
            writer.print(current.getData() + " ");
            current = current.getNext();
        }
        writer.println();
    }

    public void printMinHC() {
        LinkedListNode current = minHC.getHead();
        while(current != null) {
            writer.print(current.getData() + " ");
            current = current.getNext();
        }
        writer.println();
    }
}
