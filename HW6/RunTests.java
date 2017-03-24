import java.awt.*;
import java.io.*;

/**
 * Created by walter on 11/14/16.
 * Graph code is lifted from my Assignment 1 submission.
 * PrintWriter reference was http://stackoverflow.com/questions/2885173/how-to-create-a-file-and-write-to-a-file-in-java
 */
public class RunTests {
    public static void main(String[] args) {
        try {
            PrintWriter writer = new PrintWriter("blair-supplementary.txt", "UTF-8");
            PrintWriter report = new PrintWriter("blair-report.txt", "UTF-8");
            report.println("Towers of Hanoi \t \t Traveling Salesperson \t \t");
            report.println("n \t time \t n \t time");
            // Hanoi
            writer.println("\nTowers of Hanoi");
            Towers myTowers = new Towers(2, writer);
            writer.println(myTowers.print());
            myTowers.solve();

            // HC
            writer.println("\nHamiltonian Circuits");
            Circuits myCircuits = new Circuits(3, 3, writer);
            myCircuits.solve();
            myCircuits.printMinHC();

            writer.close();
            report.close();

        } catch (Exception e) {
            System.out.println("Trying to write report to file!");
        }
    }
}

