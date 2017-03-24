import java.io.*;

/**
 * Created by walter on 11/14/16.
 */
public class Towers {

    private int size, n, turn;
    String s;
    private Stack A, B, C;
    PrintWriter writer;

    public Towers(int size, PrintWriter writer) {
        this.size = size;
        n = size;
        A = new Stack(size);
        B = new Stack(size);
        C = new Stack(size);
        for(int i = size; i > 0; i--)
            A.push(i);
        this.writer = writer;
    }

    public String print() {
        String s = "\nTurn " + turn++ + "\n";
        s += "A: ";
        for(int i = A.getTop(); i > -1; i--) {
            s += A.getArray()[i] + " ";
        }
        s+= "\nB: ";
        for(int i = B.getTop(); i > -1; i--) {
            s += B.getArray()[i]  + " ";
        }
        s += "\nC: ";
        for(int i = C.getTop(); i > -1; i--) {
            s += C.getArray()[i] + " ";
        }
        s += "\n";
        return s;
    }

    public void solve() {
        HANOI (A, B, C, n);
    }

    public void HANOI (Stack A, Stack B, Stack C, int n){
        if (n == 1) {
            C.push(A.pop());
            writer.println(this.print());
        } else {
            HANOI(A, C, B, n - 1);
            C.push(A.pop());
            writer.println(this.print());
            HANOI(B, A, C, n - 1);
        }
    }

    public int getSize() {
        return size;
    }

}
