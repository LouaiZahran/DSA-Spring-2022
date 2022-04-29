public class HashTable {
    int rebuilds;
    int maxSize;
    int maxSizeBits;
    Object[] data;
    Matrix hashFunction;

    public HashTable(int maxSize){
        maxSize *= maxSize;         //O(N^2) space complexity
        int closestPowerOf2 = 1;
        int maxSizeBits = 0;
        while(closestPowerOf2 < maxSize) {
            closestPowerOf2 <<= 1;
            maxSizeBits++;
        }

        this.rebuilds = 0;
        this.maxSize = closestPowerOf2;
        this.maxSizeBits = maxSizeBits;
        this.data = new Object[this.maxSize];
    }

    public void build(Pair[] pairs){
        this.hashFunction = MatrixGenerator.generate(this.maxSizeBits, 32, 1); //(b, u) = (maxSizeBits, keyBits)
        //this.hashFunction.print();
        for (Pair pair : pairs) {
            if (lookup(pair.key) != null) {         //If found a collision
                this.rebuilds++;
                this.clear();                       //Clear the hashtable
                this.build(pairs);                  //and retry to build with a different hash function
                return;
            }
            insert(pair);
        }
    }

    public void insert(Pair pair){
        int key = pair.key;
        Object value = pair.value;

        Matrix keyMatrix = Matrix.convertToMatrix(key);
        Matrix indexMatrix = hashFunction.multiply(keyMatrix);
        int index = Matrix.convertToIndex(indexMatrix);

        this.data[index] = value;
    }

    public Object lookup(int key){
        Matrix keyMatrix = Matrix.convertToMatrix(key);
        Matrix indexMatrix = hashFunction.multiply(keyMatrix);
        int index = Matrix.convertToIndex(indexMatrix);
        return this.data[index];
    }

    public void clear(){
        this.data = new Object[this.maxSize];
    }

    public void print(){
        for(int i=0; i<maxSize; i++){
            if(data[i] == null)
                continue;
            System.out.printf("%d -> %d\n", i, (Integer)data[i]);
        }
    }
}
