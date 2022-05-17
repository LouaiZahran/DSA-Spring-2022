public class Main {

    public static void memoryStats() {
        int mb = 1024 * 1024;
        // get Runtime instance
        Runtime instance = Runtime.getRuntime();
        System.out.println("***** Heap utilization statistics [MB] *****\n");
        // available memory
        System.out.println("Total Memory: " + instance.totalMemory() / mb);
        // free memory
        System.out.println("Free Memory: " + instance.freeMemory() / mb);
        // used memory
        System.out.println("Used Memory: "
                + (instance.totalMemory() - instance.freeMemory()) / mb);
        // Maximum available memory
        System.out.println("Max Memory: " + instance.maxMemory() / mb);
    }
    public static void main(String[] args){

        int MAX_SIZE = 10000;
        int INPUTRANGE = MAX_SIZE*100;
        Pair[] testData = new Pair[MAX_SIZE];
        boolean[] seen = new boolean[INPUTRANGE];

        System.out.println("Inserted Pairs");
        System.out.println("=============");

        for(int i=0; i<MAX_SIZE; i++){
            testData[i] = new Pair();
            //No two pairs should have the same KEY during insertion, this is different from collisions after hashing
            do {
                testData[i].key = (int)(INPUTRANGE * Math.random());
            }while(seen[testData[i].key]);
            seen[testData[i].key] = true;

            testData[i].value = (int)((1L<<31) * Math.random());                        //random number between [0, 2^31 - 1]
            System.out.printf("%d -> %d\n", testData[i].key, (int)testData[i].value);   //Note the difference between key and hashed index
        }

        HashTable hashTable = new TwoLevelSchemeHashTable(MAX_SIZE);
        hashTable.build(testData);

        for(Pair pair: testData)
            assert(hashTable.lookup(pair.key) == pair.value);

        System.out.println("\nHashed Index -> Value");
        System.out.println("=======================");
        hashTable.print();

        System.out.println("\nOutPut Pairs");
        System.out.println("=======================");
        hashTable.lookGroup(testData);

        System.out.printf("\nNumber of Problems ( collision or rebuilds): %d \n", hashTable.getProblemCounter());
        memoryStats();
    }
}
