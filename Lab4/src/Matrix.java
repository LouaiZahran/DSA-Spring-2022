public class Matrix {
    int height;
    int width;
    int[][] data;

    public Matrix(int height, int width){
        this.height = height;
        this.width = width;
        data = new int[height][width];
    }

    public Matrix multiply(Matrix other){
        assert(this.width == other.height);

        Matrix ret = new Matrix(this.height, other.width);
        for(int i=0; i<this.height; i++)
            for(int j=0; j<other.width; j++)
                for(int k=0; k<this.width; k++)
                    ret.data[i][j] += this.data[i][k] * other.data[k][j];

        for(int i=0; i<this.height; i++)
            for(int j=0; j<other.width; j++)
                ret.data[i][j] &= 1; //Same as ret.data[i][j]%=2, better performance

        return ret;
    }

    public static Matrix convertToMatrix(int decimal){
        //convert to 32-bits and store in a matrix
        Matrix ret = new Matrix(32, 1);
        for(int i=0; i<32; i++){
            ret.data[i][0] = decimal & 1;       //Same as decimal%2, gets current bit
            decimal >>= 1;                      //Same as decimal/=2, better performance
        }
        return ret;
    }

    public static int convertToIndex(Matrix binary){
        int ret = 0;
        for(int i=binary.height - 1; i>=0; i--){
            ret <<= 1;
            ret += binary.data[i][0];
        }
        return ret;
    }

    public void print(){
        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                System.out.printf("%d ", this.data[i][j]);
            }
            System.out.println();
        }
    }
}
