import java.io.*;
import java.util.Scanner;

/**
 * Walter Blair
 * Assignment: assignment1
 * 
 * I worked alone and consulted no outside sources for Main.java
 * except for food web image from my Biol 211 lecture.
 * 		
 * This Main class reads a tab-delimited text file in the format of
 * an adjacency matrix based off of the food web diagram. This is a 
 * directed graph where edges are directions of energy flow. For
 * example, robin has an edge to fox because robin can be consumed
 * by fox. Fox does not have an edge to robin. All wild animal vertices 
 * (but not domesticated pets) have an edge to dead animal, as I am 
 * apparently more morbid than the authors of this image, and this
 * is a better representation of a natural food web assuming things
 * that live in the forest eventually return to the earth.
 * 
 * Main initializes a small adjacency list, reads input.txt, and then
 * reinitializes a much bigger list to avoid collisions. After adding
 * original vertices and edges, Main adds dogs that eat foxes and cats 
 * that eat robins and kills off hawks. Main then demos methods and
 * adds a group of helpful fungi to complete the picture.
 */
public class Main {
	public static void main(String[] args) {
		
		List list = new List(123);
		
		// Read file and populate adjacency list
		File file = new File("input.txt");
		try {
			Scanner input = new Scanner(file);
			list.populate(input);
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
		
		list.addVertex("Dog");
		list.addVertex("Cat");
		list.removeVertex("Hawk");
		list.removeEdge("Hawk", "Dead animals");
		list.removeVertex("Hawk");
		
		list.addEdge("Fox", "Dog");
		list.addEdge("Robin", "Cat");
		
		System.out.println("Dog is degree (should be 0): " + list.degree("Dog"));
		System.out.println("Bacteria is degree (should be 2): " + list.degree("Bacteria"));
		System.out.println("Is Hawk an existing vertex? (no)" + list.isVertex("Hawk"));
		System.out.println("Is Cat an existing vertex? (yes)" + list.isVertex("Cat"));
		
		System.out.println("Robin has an edge to Cat (yes): " + list.isAdjacent("Robin", "Cat"));
		System.out.println("Cat has an edge to Robin (no): " + list.isAdjacent("Cat", "Robin"));
		
		System.out.println("Is there a path from Robin to Cat (yes): " + list.isPath("Robin", "Cat"));
		System.out.println("Is there a path from Robin to Hawk (no, we killed the hawks): " + list.isPath("Robin", "Hawk"));
		System.out.println("Is there a path from Robin to Cricket (no, our fungus is dumb): " + list.isPath("Robin", "Cricket"));
		
		System.out.println("Path from Robin to Cat: " + list.getPath("Robin", "Cat"));
		System.out.println("Path from Robin to Hawk: " + list.getPath("Robin", "Hawk"));
		System.out.println("Path from Robin to Cricket: " + list.getPath("Robin", "Cricket"));
		System.out.println("\nUnrealistic! Should be a connection somewhere in here...\n");
		list.addVertex("Mycorrhizal fungi");
		list.addEdge("Live leaves", "Mycorrhizal fungi");
		list.addEdge("Mycorrhizal fungi", "Live leaves");
		list.addEdge("Dead animals", "Mycorrhizal fungi");
		list.addEdge("Mycorrhizal fungi", "Dead animals");
		System.out.println("Path from Robin to Cricket: " + list.getPath("Robin", "Cricket"));
		System.out.println("Path from Robin to Bacteria: " + list.getPath("Robin", "Bacteria"));
		System.out.println("Path from Robin to Robin: " + list.getPath("Robin", "Robin"));
		System.out.println("\nIt's the ciiiiircle of life!");
	}
}