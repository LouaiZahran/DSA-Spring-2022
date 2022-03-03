package Trees;

import java.util.NoSuchElementException;

public interface Tree {
    boolean search(Object obj);
    void insert(Object obj) throws IllegalArgumentException;
    void delete(Object obj) throws NoSuchElementException;
    void clear();
    void traverse();
    int getHeight();
    int getSize();
    Object getMax();
}
