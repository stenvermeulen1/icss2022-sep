package nl.han.ica.datastructures;

import java.util.LinkedList;

public class HANStack<T> implements IHANStack<T>{

    private LinkedList<T> stack  = new LinkedList<T>();

    @Override
    public void push(T value) {
        stack.addFirst(value);
    }

    @Override
    public T pop() {
        T temp = stack.get(0);
        stack.removeFirst();
        return temp;
    }

    @Override
    public T peek() {
        return stack.get(0);
    }
}
