import java.util.ArrayList;

public class TwoLevelSchemeHashTable implements HashTable{
    //arrayList of the bins
    private ArrayList<ArrayList<Pair>>  bins;
    private ArrayList<Integer> collisionIndices;
    private  HashTable[] matrixMethodHashTables;
    private Object data[];
    private Matrix hashFunction;
      private int maxSize=0;
      private int maxSizeBits=0;
      private int lastPairIndexAdded=0;
    private int collisions=0;

    TwoLevelSchemeHashTable(int maxSize){
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
        this.bins=new ArrayList<ArrayList<Pair>>();
        for(int i=0;i<this.maxSize;i++) {
            this.bins.add(new ArrayList<Pair>());
        }
        this.matrixMethodHashTables =new MatrixMethodHashTable[this.maxSize];
        this.collisionIndices=new ArrayList<>();
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
    private void buildInsertion(Pair pair){
        int key = pair.key;
        Object value = pair.value;

        Matrix keyMatrix = Matrix.convertToMatrix(key);
        Matrix indexMatrix = hashFunction.multiply(keyMatrix);
        int index = Matrix.convertToIndex(indexMatrix);
        this.lastPairIndexAdded=index;
        this.data[index] = value;
    }
    private void firstLevel(Pair[] pairs){
        this.hashFunction = MatrixGenerator.generate(this.maxSizeBits, 32, 1); //(b, u) = (maxSizeBits, keyBits)
        //this.hashFunction.print();
        for (Pair pair : pairs) {
            if (isCollided(pair.key)) { // collision happened
                int collisionIndex=getHashedIndex(pair.key);
                this.collisionIndices.add(collisionIndex);
                this.bins.get(collisionIndex).add(pair);
                this.collisions++;
            }else {
                buildInsertion(pair);
                this.bins.get(this.lastPairIndexAdded).add(pair);
            }
        }
    }
    private void secondLevel(){
        //create hashmaps
        for(Integer collisionIndex:this.collisionIndices){
            this.matrixMethodHashTables[collisionIndex] = new MatrixMethodHashTable(this.bins.get(collisionIndex).size());
            this.matrixMethodHashTables[collisionIndex]
                    .build(     bins.get(collisionIndex).toArray(   new Pair[this.bins.get(collisionIndex).size()]   )  );
        }
    }
    @Override
    public void build(Pair[] pairs){
        firstLevel(pairs);
        secondLevel();
    }


    private void firstLevelInsertion(Pair pair)
    {
        if (isCollided(pair.key)) { // collision happened
            int collisionIndex=getHashedIndex(pair.key);
            this.collisionIndices.add(collisionIndex);
            this.bins.get(collisionIndex).add(pair);
            secondLevelInsertion(pair,collisionIndex);
            this.collisions++;
        }else {
            buildInsertion(pair);
            this.bins.get(this.lastPairIndexAdded).add(pair);
        }
    }
    private void secondLevelInsertion(Pair pair,int collisionIndex){
        if(this.matrixMethodHashTables[collisionIndex]==null)
            this.matrixMethodHashTables[collisionIndex] = new MatrixMethodHashTable(this.bins.get(collisionIndex).size());
        this.matrixMethodHashTables[collisionIndex]
                .build(     bins.get(collisionIndex).toArray(   new Pair[this.bins.get(collisionIndex).size()]   )  );
    }
    @Override
    public void insert(Pair pair){
        firstLevelInsertion(pair);
    }

    private int lookupFirstLevel(int key){
        return getHashedIndex(key);
    }
    private Object lookupSecondLevel(int key,int collisionIndex){
        return this.matrixMethodHashTables[collisionIndex].lookup(key);
    }
    @Override
    public Object lookup(int key){
        int index =lookupFirstLevel(key);
        if(this.bins.get(index).size()<=1) //found in first level
            return this.data[index];
        return lookupSecondLevel(key,index);
    }
    @Override
    public void clear(){
        this.data = new Object[this.maxSize];
        this.bins=new ArrayList<ArrayList<Pair>>();
        for(int i=0;i<this.maxSize;i++) {
            this.bins.add(new ArrayList<Pair>());
        }
        this.matrixMethodHashTables =new MatrixMethodHashTable[this.maxSize];
        this.collisionIndices=new ArrayList<>();
    }
    @Override
    public void lookGroup(Pair[] pairs){
        for(Pair pair:pairs)
            System.out.printf("%d -> %d\n",pair.key,(Integer)lookup(pair.key));

    }
    @Override
    public void print(){
        for (ArrayList<Pair> bin :this.bins) {
            for (Pair pair : bin) {
                System.out.printf("%d -> %d \n" ,pair.key,lookup(pair.key));
            }
        }
    }

    @Override
    public int getProblemCounter() {
        return this.collisions;
    }
}
