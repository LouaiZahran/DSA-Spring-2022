import java.util.ArrayList;

public class HashTable2 {
    //arrayList of the bins
    private ArrayList<ArrayList<Pair>>  collisionPairs;
    private ArrayList<Integer> collisionIndices;
    private  HashTable[] hashTables;
    private Object data[];
    private Matrix hashFunction;
      private int maxSize=0;
      private int maxSizeBits=0;
      private int lastPairIndexAdded=0;
    int collisions=0;

    HashTable2(int maxSize){
        int closestPowerOf2 = 1;
        int maxSizeBits = 0;
        while(closestPowerOf2 < maxSize) {
            closestPowerOf2 <<= 1;
            maxSizeBits++;
        }
        this.collisions = 0;
        this.maxSize = closestPowerOf2;
        this.maxSizeBits = maxSizeBits;
        this.data = new Object[this.maxSize];
        this.collisionPairs=new ArrayList<ArrayList<Pair>>();
        for(int i=0;i<this.maxSize;i++) {
            this.collisionPairs.add(new ArrayList<Pair>());
        }
        this.hashTables=new HashTable[this.maxSize];
        this.collisionIndices=new ArrayList<>();
    }
    public void build(Pair[] pairs){
        firstLevel(pairs);
        secondLevel();
    }
    private void firstLevel(Pair[] pairs){
        this.hashFunction = MatrixGenerator.generate(this.maxSizeBits, 32, 1); //(b, u) = (maxSizeBits, keyBits)
        //this.hashFunction.print();
        for (Pair pair : pairs) {
            if (isCollided(pair.key)) { // collision happened
                int collisionIndex=getHashedIndex(pair.key);
                this.collisionIndices.add(collisionIndex);
                this.collisionPairs.get(collisionIndex).add(pair);
                this.collisions++;
            }else {
                insert(pair);
                this.collisionPairs.get(this.lastPairIndexAdded).add(pair);
            }
        }
    }
    private void secondLevel(){
        //create hashmaps
        for(Integer collisionIndex:this.collisionIndices){
            this.hashTables[collisionIndex] = new HashTable(this.collisionIndices.size());
            this.hashTables[collisionIndex].build(collisionPairs.get(collisionIndex).toArray(new Pair[collisionPairs.get(collisionIndex).size()]));
        }
    }
    public void insert(Pair pair){
        int key = pair.key;
        Object value = pair.value;

        Matrix keyMatrix = Matrix.convertToMatrix(key);
        Matrix indexMatrix = hashFunction.multiply(keyMatrix);
        int index = Matrix.convertToIndex(indexMatrix);
        this.lastPairIndexAdded=index;
        this.data[index] = value;
    }
    private int getHashedIndex(int key){
        Matrix keyMatrix = Matrix.convertToMatrix(key);
        Matrix indexMatrix = hashFunction.multiply(keyMatrix);
        return Matrix.convertToIndex(indexMatrix);
    }
    private boolean isCollided(int key){
        Matrix keyMatrix = Matrix.convertToMatrix(key);
        Matrix indexMatrix = hashFunction.multiply(keyMatrix);
        int index = Matrix.convertToIndex(indexMatrix);
        return this.data[index]!=null;
    }
    private int lookupFirstLevel(int key){
        return getHashedIndex(key);
    }
    private Object lookupSecondLevel(int key,int collisionIndex){
        return this.hashTables[collisionIndex].lookup(key);
    }
    public Object lookup(int key){
        int index =lookupFirstLevel(key);
        if(this.collisionPairs.get(index).size()<=1) //found in first level
            return this.data[index];
        return lookupSecondLevel(key,index);
    }

    public void clear(){
        this.data = new Object[this.maxSize];
    }
    public void lookGroup(Pair[] pairs){
        for(Pair pair:pairs)
            System.out.printf("%d -> %d\n",pair.key,(Integer)lookup(pair.key));

    }

    public void print(){
        for(int i=0; i<maxSize; i++){
            if(data[i] == null)
                continue;
            System.out.printf("%d -> %d\n", i, (Integer)data[i]);
        }
    }

}
