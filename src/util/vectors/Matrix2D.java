package util.vectors;

public class Matrix2D {

    public float[][] matrix = new float[2][2];

    public Matrix2D(float x0y0, float x1y0, float x0y1, float x1y1) {
        matrix[0][0] = x0y0;
        matrix[0][1] = x0y1;
        matrix[1][0] = x1y0;
        matrix[1][1] = x1y1;
    }

    public Matrix2D(Vector2D row0, Vector2D row1) {
        matrix[0][0] = row0.x;
        matrix[1][0] = row0.y;
        matrix[0][1] = row1.x;
        matrix[1][1] = row1.y;
    }

    public void multiply(Matrix2D matrix2) {
        matrix[0][0] = Vector2D.dotProduct(matrix[0][0], matrix[1][0], matrix2.matrix[0][0], matrix2.matrix[1][0]);
        matrix[1][0] = Vector2D.dotProduct(matrix[0][0], matrix[1][0], matrix2.matrix[1][0], matrix2.matrix[1][1]);
        matrix[0][1] = Vector2D.dotProduct(matrix[0][1], matrix[1][1], matrix2.matrix[0][0], matrix2.matrix[1][0]);
        matrix[1][1] = Vector2D.dotProduct(matrix[0][1], matrix[1][1], matrix2.matrix[1][0], matrix2.matrix[1][1]);
    }
    public static Matrix2D multiply(Matrix2D matrix1, Matrix2D matrix2) {
        Matrix2D output = new Matrix2D(0, 0 , 0,0);
        output.matrix[0][0] = Vector2D.dotProduct(matrix1.matrix[0][0], matrix1.matrix[1][0],
                                matrix2.matrix[0][0], matrix2.matrix[1][0]);
        output.matrix[1][0] = Vector2D.dotProduct(matrix1.matrix[0][0], matrix1.matrix[1][0],
                                matrix2.matrix[1][0], matrix2.matrix[1][1]);
        output.matrix[0][1] = Vector2D.dotProduct(matrix1.matrix[0][1], matrix1.matrix[1][1],
                                matrix2.matrix[0][0], matrix2.matrix[1][0]);
        output.matrix[1][1] = Vector2D.dotProduct(matrix1.matrix[0][1], matrix1.matrix[1][1],
                                matrix2.matrix[1][0], matrix2.matrix[1][1]);
        return output;
    }
    public void multiply(float coefficient) {
        matrix[0][0] *= coefficient;
        matrix[1][0] *= coefficient;
        matrix[0][1] *= coefficient;
        matrix[1][1] *= coefficient;
    }
    public static Matrix2D multiply(Matrix2D matrix1, float coefficient) {
        return new Matrix2D(matrix1.matrix[0][0] * coefficient,
                            matrix1.matrix[1][0] * coefficient,
                            matrix1.matrix[0][1] * coefficient,
                            matrix1.matrix[1][1] * coefficient);
    }

    public float getDeterminant() {
        return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
    }

    public Matrix2D getInverse() {
        float determinant = getDeterminant();
        if (determinant == 0) return null;

        Matrix2D scaledMatrix = multiply(this, 1/determinant);
        return new Matrix2D(scaledMatrix.matrix[1][1], -scaledMatrix.matrix[1][0],
                            -scaledMatrix.matrix[0][1], scaledMatrix.matrix[0][0]);
    }

}