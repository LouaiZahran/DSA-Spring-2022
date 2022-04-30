public interface HashTable {
    void build(Pair[] pairs);
    void insert(Pair pair);
    Object lookup(int key);
    void clear();
    void lookGroup(Pair[] pairs);
    void print();
    int getProblemCounter();
}
