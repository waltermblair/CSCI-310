/**
 * Created by walter on 11/14/16.
 * Used https://www.tutorialspoint.com/javaexamples/data_stack.htm for help with stack implementation
 */
public class Stack {
    private int size;
    private int top;
    private int[] array;

    public Stack(int size) {
        this.size = size;
        array = new int[size];
        top = -1;
    }

    public void push(int n) {
        array[++top] = n;
    }

    public int pop() {
        return array[top--];
    }

    public int peak() {
        return array[top];
    }

    public int getSize() {
        return size;
    }

    public int getTop() {
        return top;
    }

    public int[] getArray() {
        return array;
    }
}
