public class MatrixGenerator {
    public static Matrix generate(int height, int width, int maxValue){
        maxValue++;
        Matrix ret = new Matrix(height, width);
        for(int i=0; i<height; i++)
            for(int j=0; j<width; j++)
                ret.data[i][j] = (int)(maxValue * Math.random());
        return ret;
    }
}
