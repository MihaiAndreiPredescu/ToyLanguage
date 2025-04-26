package model.adt;
import java.util.List;
import exceptions.EmptyStackException;


public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T t);
    boolean isEmpty();
    int size();
    List<T> getReversed();
}