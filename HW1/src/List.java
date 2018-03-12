import java.util.Scanner;

/**
 * Walter Blair
 * Assignment: assignment1
 * 
 * I worked alone and consulted the following references:
 * 		https://en.m.wikipedia.org/wiki/Adjacency_list
 * 		my work from CSCI 230 (July 2016)
 * 
 * List is an adjacency list abstract data type that contains 
 * an array of size n where each index contains a LinkedList. 
 */
public class List {
	private LinkedList[] array;
	
	/**
	 * Constructor for adjacency list of size n called in Main
	 * @param n of type int specified in Main
	 */
	public List(int n) {
		array = new LinkedList[n];
	}
	
	/**
	 * populate reads structured adjacency matrix file and adds vertices
	 * into adjacency list using addVertex method and then adds
	 * edges using addEdge method for each subsequent line in input
	 * file.
	 * @param Scanner object created in Main
	 */
	public void populate(Scanner file) {
		//Apply addVertex to vertices from first line of file
		String nextLine = file.nextLine();
		String delims = "[\t]+";
		String[] vertices = nextLine.split(delims);
		// Store each string as a vertex
		for(int i = 1; i < vertices.length; i++) {
			addVertex(vertices[i]);
		}
		//Apply addEdge to edges in from file's adjacency matrix.
		//for(j) loop runs through each vertex's edges.
		for(int j = 1; j < vertices.length; j++) {
			nextLine = file.nextLine();
			//Read the vertex and all of its edges into an array of strings
			String[] edges = nextLine.split(delims);
			//for(k) loop checks each vertex for 0 or 1 to find edges 
			for(int k = 1; k < edges.length; k++) {
				if(edges[k].equals("1")) {
					//If vertex has a "1", add edge from this vertex to that vertex
					addEdge(edges[0], vertices[k]);
				}
			}
		}
		System.out.println("\nFile read successfully!\n");
	}
	
	/**
	 * degree will return the number of edges that a vertex has in
	 * its adjacent LinkedList. An edge from one vertex to another signifies
	 * that energy can be directionally transferred from one vertex to the 
	 * other.
	 * @param vertexValue of type Object
	 * @return int number of edges from this vertex to other vertices
	 */
	public int degree(Object vertexValue) {
		int n = 0;
		int index = hashFunction(vertexValue);
		try {
			LinkedListNode head = array[index].getHead();
			LinkedListNode current = head.getNext();
			while(current != null) {
				current = current.getNext();
				n++;
			}
		}
		catch(Exception e) {
			//System.out.println("You deleted this poor " + vertexValue);
		}
		return n;
	}
	
	/**
	 * isVertex returns true if vertex currently exists in list, false 
	 * if it doesn't currently exist. 
	 * @param vertexValue of type Object
	 * @return boolean true if vertex exists, false if it does not.
	 */
	public boolean isVertex(Object vertexValue) {
		int index = hashFunction(vertexValue);
		if(array[index] == null)
			return false;
		else
			return true;
	}
	
	/**
	 * addVertex(Object) function adds to List's array a new LinkedList 
	 * with the new vertex as the head of the LinkedList.
	 * Edges are added separately by addEdge method.
	 * If there is a collision, then addVertex builds new bigger array and copies 
	 * everything over
	 * @param data of type Object.
	 */
	public void addVertex(Object data) {
		//call hashFunction
		int index = hashFunction(data);
		
		//insert new LinkedList at index
		if(array[index] == null) {
			array[index] = new LinkedList(data);
			System.out.println("Vertex " + data.toString() + " added successfully");
		}
		
		//if there is a collision, make a new array that is bigger
		else {
			System.out.println("Collision! Increasing size of your array...");
			LinkedList[] newArray = new LinkedList[array.length*100-1];
			for(int i = 0; i < array.length; i++) {
				if(array[i] != null) {
					LinkedListNode head = array[i].getHead();
					//temporary version of hashFunction accounting for new array length
					int newIndex = Math.abs(head.getData().hashCode() % newArray.length);
					//copy new LinkedList at new index
					newArray[newIndex] = new LinkedList(head.getData());
					//copy any existing edges to new LinkedList
					LinkedListNode current = head.getNext();
					while(current != null) {
						newArray[newIndex].insert(current);
						current = current.getNext();
					}
				}
			}
			System.out.println("We should be all good, resuming...");
			array = newArray;
			index = hashFunction(data);
			array[index] = new LinkedList(data);
			System.out.println("Vertex " + data.toString() + " added successfully");
		}
	}
	
	/**
	 * addEdge takes two Object parameters that get passed to hashFunction 
	 * and inserts a new Node within the LinkedList of the first vertex 
	 * @param vertex1 is the starting vertex for the edge and receives the edge Node
	 * @param vertex2 is the ending vertex for the edge and does not receive an edge Node
	 */
	public void addEdge(Object vertex1, Object vertex2) {
		//call hashFunction
		int index1 = hashFunction(vertex1);
		int index2 = hashFunction(vertex2);
		
		//insert new Node in vertex1's LinkedList and update total number of edges
		LinkedListNode v1 = array[index1].getHead();
		LinkedListNode v2 = array[index2].getHead();
		array[index1].insert(vertex2);
		v1.setNumberOfEdges(v1.getNumberOfEdges()+1);
		v2.setNumberOfEdges(v2.getNumberOfEdges()+1);
		System.out.println("Edge from " + vertex1.toString() + " to " + vertex2.toString() + " added successfully");
	}
	
	/**
	 * removeVertex checks to see that vertex exists and is degree 0.
	 * If vertex is degree 0, then its array index is set to null. Does
	 * not remove any cases where another vertex has an edge to this one, 
	 * because I don't want to lose edge info in case we reintroduce
	 * vertices in the future. For example, if we remove hawks and then
	 * reintroduce them, they would resume all previous edge relationship.
	 * @param vertexValue of type Object.
	 */
	public void removeVertex(Object vertexValue) {
		int index = hashFunction(vertexValue);
		if(array[index] == null)
			System.out.println("Vertex does not exist");
		else {
			LinkedListNode head = array[index].getHead();
			if(head.getNext() != null)
				System.out.println("Vertex " + vertexValue.toString() + " is of degree " + degree(vertexValue) + "! Cannot remove");
			else {
				array[index] = null;
				System.out.println("Vertex " + vertexValue.toString() + " removed successfully");
			}
		}
	}
	
	/**
	 * removeEdge uses isAdjacent to check edge exists and then
 	 * uses LinkedList's remove method to remove edges from vertexvalue1's LinkedList.
	 * @param vertexValue1 of type Object represented as an edge Node
	 * @param vertexValue2 ""
	 */
	public void removeEdge(Object vertexValue1, Object vertexValue2) {
		if(isAdjacent(vertexValue1, vertexValue2)) {
			int index1 = hashFunction(vertexValue1);
			array[index1].remove(vertexValue2);
			System.out.println("Edge from " + vertexValue1 + " to " + vertexValue2 + " removed successfully");
		}
	}
	
	/**
	 * isAdjacent checks vertexValue1's LinkedList for an edge matching the second
	 * vertex.
	 * @param vertexValue1 Object with edge to Value2
	 * @param vertexValue2 Object does not have edge to Value1
	 * @return boolean returns true if edge does exist, false otherwise.
	 */
	public boolean isAdjacent(Object vertexValue1, Object vertexValue2) {
		//call hashFunction
		int index1 = hashFunction(vertexValue1);
		
		LinkedListNode current = array[index1].getHead();
		while(current != null) {
			if(current.getData().equals(vertexValue2)) 
					return true;
			else
				current = current.getNext();
		}
		return false;
	}
	
	/**
	 * Recursive depth-first isPath determines whether a path exists between currentVertex 
	 * and stopVertex by checking each edge of the currentVertex and tracking which vertices 
	 * have been visited. Prints messages if an edge would be available, but the associated 
	 * vertex has been deleted.
	 * @param currentVertex - Object renamed from startVertex to clarify recursive approach
	 * @param stopVertex - Object - always the same end goal as original call
	 * @return boolean true if path exists, false if path does not exist
	 */
	public boolean isPath(Object currentVertex, Object stopVertex) {
		if(!isVertex(currentVertex) || !isVertex(stopVertex))
			return false;
		int index = hashFunction(currentVertex);
		LinkedListNode head = array[index].getHead();
		LinkedListNode current = head.getNext();
		while(current != null) {
			//First, see if any of the edges is a match
			if(current.getData().equals(stopVertex)) {
				index = hashFunction(current.getData());
				//Check to see if this vertex still exists or if it was deleted...
				if(array[index] == null) {
					current = current.getNext();
				}
				//If I found the goal, reset all visited attributes for next time and return true
				else {
					array[index].getHead().setPrevious(head);
					clearVisited();
					return true;
				}
			}
			else
				current = current.getNext();
		}
		//If no immediate matches found, need to explore each edge starting with first one...
		//If candidate has no edges, was deleted, or all of its edges have been visited, try next one.
		current = head.getNext();
		int indexCurrent = hashFunction(current.getData());
		while(degree(current.getData()) == 0 || 
				!isVertex(current.getData()) || 
				current.getVisited() == array[indexCurrent].getHead().getNumberOfEdges()) {
			current = current.getNext();
			if(current == null) {
				clearVisited();
				return false;
			}
			indexCurrent = hashFunction(current.getData());
		}
		//If candidate has edges, exists, and has not yet been visited, check it
		//add one visit to Visited before checking
		current.setVisited(current.getVisited()+1);
		LinkedListNode currentNode = array[hashFunction(current.getData())].getHead();
		if(currentNode.getPrevious() == null)
			currentNode.setPrevious(head);
		return isPath(current.getData(), stopVertex);
	}

	/**
	 * getPath uses 'previous' LinkedListNode attribute recorded by 
	 * isPath to create a path from startVertex to stopVertex
	 * @param startVertex of type Object
	 * @param stopVertex of type Object
	 * @return String of vertices along path
	 */
	public String getPath(Object startVertex, Object stopVertex) {
		String retval = "(" + stopVertex.toString() + ")";
		if(isPath(startVertex, stopVertex)) {
			int index = hashFunction(stopVertex);
			LinkedListNode current = array[index].getHead();
			stopVertex = current.getPrevious().getData();
			index = hashFunction(stopVertex);
			while(!startVertex.equals(stopVertex)) {
				retval = "(" + stopVertex.toString() + ") --> " + retval;
				if(array[index] == null) {
					System.out.println("You killed the " + stopVertex.toString());
					clearPath();
					return retval;
				}
				else {
					current = array[index].getHead();
					stopVertex = current.getPrevious().getData();
					index = hashFunction(stopVertex);
				}
			}
			clearPath();
			return "(" + startVertex.toString() + ") --> " + retval;
		}
		else
			return null;
	}
	
	/**
	 * clearPath() is used at the end of getPath to reset isPath's assignments of 'previous'
	 * This must be done for subsequent isPath() and getPath() calls.
	 */
	public void clearPath() {
		//Start by resetting any prior isPath's assignments of previous
		for(int i = 1; i < array.length; i++) {
			if(array[i] != null) {
				LinkedListNode resetNode = array[i].getHead();
				while(resetNode != null) {
					resetNode.setPrevious(null);
					resetNode = resetNode.getNext();
				}
			}
		}
	}
	
	/**
	 * Like clearPath(), clearVisited() resets 'visited' assignments
	 */
	public void clearVisited() {
		for(int i = 1; i < array.length; i++) {
			if(array[i] != null) {
				LinkedListNode resetNode = array[i].getHead();
				while(resetNode != null) {
					resetNode.setVisited(0);
					resetNode = resetNode.getNext();
				}
			}
		}
	}
	
	/**
	 * hashFunction suggested by Eclipse with additional abs() to 
	 * fix negative index values e.g. for Lizard
	 * @param data of type Object
	 * @return int value of index
	 */
	public int hashFunction(Object data) {
		return Math.abs(data.hashCode() % array.length);
	}
}