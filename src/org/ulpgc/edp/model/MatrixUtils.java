package org.ulpgc.edp.model;

import java.util.Arrays;
import java.util.Random;

class MatrixUtils {
    static final Random generator = new Random();
    private int[][] matrix;
    private int[][]otherMatrix;

    MatrixUtils(int tableBitsLength, int pseudokeyBitsLength) {
        this.matrix = newMatrix(tableBitsLength, pseudokeyBitsLength);
        this.otherMatrix = newMatrix(tableBitsLength, pseudokeyBitsLength / 2);
    }

    void matrixString() {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    int[][] matrix() {
        return Arrays.copyOf(matrix, matrix.length);
    }

    int[][] otherMatrix() {
        return Arrays.copyOf(otherMatrix, otherMatrix.length);
    }

    private int nextInt() {
        return generator.nextInt(0, 2);
    }

    int[][] newMatrix(int tableBitsLength, int pseudokeyBitsLength) {

        if (
                matrix == null ||
                        tableBitsLength != matrix.length ||
                        pseudokeyBitsLength != matrix[0].length
        ) {
            matrix = new int[tableBitsLength][pseudokeyBitsLength];
        }

        for (int i = 0; i < tableBitsLength; i++) {
            for (int j = 0; j < pseudokeyBitsLength; j++) {
                matrix[i][j] = nextInt();
            }
        }

        return matrix;
    }

    int hash(int vector, int length, int[][] m) {
        int[] newVector = new int[length];
        int[] resultVector = new int[m.length];
        int result = 0;

        for (int i = 0; i < length; i++) {
            int shift = 1 << (length - 1 - i);
            newVector[i] = (vector & shift) >> (length - 1 - i);
        }

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                resultVector[i] += m[i][j] * newVector[j];
            }
            resultVector[i] %= 2;
        }

        for (int i = 0; i < m.length; i++) {
            result += (int) Math.pow(2, m.length - 1 - i) * resultVector[i];
        }

        System.out.println(result);

        return result;
    }
}
