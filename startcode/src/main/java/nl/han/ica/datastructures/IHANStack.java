package nl.han.ica.datastructures;

import java.util.LinkedList;
import java.util.List;

public interface IHANStack<T> {
    /**
     * pushes value T to the top of the stack
     * @param value value to push
     */
    void push(T value);

    /**
     * Pops (and removes) value at top of stack
     * @return popped value
     */
    T pop();

    /**
     * Peeks at the top of the stack. Does not remove anything
     * @return value at the top of the stack
     */
    T peek();
}
