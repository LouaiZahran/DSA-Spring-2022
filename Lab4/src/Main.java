public class Main {
    public static void main(String[] args){
        int MAX_SIZE = 128;
        Pair[] testData = new Pair[MAX_SIZE];
        boolean[] seen = new boolean[MAX_SIZE];

        System.out.println("Inserted Pairs");
        System.out.println("=============");

        for(int i=0; i<MAX_SIZE; i++){
            testData[i] = new Pair();
            //No two pairs should have the same KEY during insertion, this is different from collisions after hashing
            do {
                testData[i].key = (int)(MAX_SIZE * Math.random());
            }while(seen[testData[i].key]);
            seen[testData[i].key] = true;

            testData[i].value = (int)((1L<<31) * Math.random());                        //random number between [0, 2^31 - 1]
            System.out.printf("%d -> %d\n", testData[i].key, (int)testData[i].value);   //Note the difference between key and hashed index
        }

        HashTable hashTable = new HashTable(MAX_SIZE);
        hashTable.build(testData);

        for(Pair pair: testData)
            assert(hashTable.lookup(pair.key) == pair.value);

        System.out.println("\nHashed Index -> Value");
        System.out.println("=======================");
        hashTable.print();

        System.out.printf("\nNumber of rebuilds: %d", hashTable.rebuilds);
    }
}
