public class Main {
    public static void main(String[] args){
        Pair[] testData = new Pair[100];
        for(int i=0; i<100; i++){
            testData[i] = new Pair();
            testData[i].key = (int)((1L<<31) * Math.random());      //random number between [0, 2^31 - 1]
            testData[i].value = (int)((1L<<31) * Math.random());    //1L isn't a typo, 1L = 1 of type Long
        }
        HashTable hashTable = new HashTable(100);
        hashTable.build(testData);
        for(Pair pair: testData)
            assert(hashTable.lookup(pair.key) == pair.value);
        hashTable.print();
    }
}
