public interface HashTable {
    void build(Pair[] pairs);
    void insert(Pair pair);
    Object lookup(int key);
    boolean containsKey(int key);
    boolean contains(Object obj);
    void clear();
    void lookGroup(Pair[] pairs);
    void print();
    int getProblemCounter();
}
