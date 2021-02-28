package com.simian.project.simianproject.util;

import com.simian.project.simianproject.exception.WrongStringFormatException;

public class StringPatternFinder {

    // used to compute the preprocessing array that KPM will use to improve its
    // performance.
    private static void computeLPSArray(char pat[], int M, int lps[]) {
        int len = 0;
        int i = 1;
        lps[0] = 0;

        while (i < M) {
            if (pat[i] == pat[len]) {
                len++;
                lps[i++] = len;
            } else {
                if (len != 0) {

                    len = lps[len - 1];
                } else {
                    lps[i++] = 0;
                }
            }
        }

    }

    private static int KMPSearch(char pattern[], char txt[], int N) {

        int M = pattern.length;

        int[] lps = new int[M];
        int j = 0;

        // Preprocess the pattern to improve performance so we don't need to
        // compare positions that we already know will match.
        computeLPSArray(pattern, M, lps);

        int i = 0; // index for txt[]

        while (i < N) {
            if (pattern[j] == txt[i]) {
                j++;
                i++;
            }
            if (j == M) {
                return 1;
            } else if (i < N && pattern[j] != txt[i]) {

                if (j != 0) {
                    j = lps[j - 1];

                } else {
                    i = i + 1;
                }
            }
        }

        return 0;
    }

    private static boolean isPatternPresentInDiagonal(char[][] matrix, char pattern[]) {

        int matrixSize = matrix.length;
        int patternSize = pattern.length;
        int x = 0;


        //Validates the left to right diagonal
        for (int i = 0; i < matrixSize - (patternSize - 1); i++) {
            for (int j = 0; j < matrixSize - (patternSize - 1); j++) {
                x = 0;
                while (matrix[i + x][j + x] == pattern[x] && x < patternSize) {
                    x += 1;
                    if (x == patternSize)
                        return true;
                }
            }
        }
        //Validates the right to left diagonal
        for (int i = 0; i < matrixSize - (patternSize - 1); i++) {
            for (int j = (patternSize - 1); j < matrixSize; j++) {
                x = 0;
                while (matrix[i + x][j - x] == pattern[x] && x < patternSize) {
                    x += 1;
                    if (x == patternSize)
                        return true;
                }
            }
        }


        return false;

    }

    // if I didn't limit it to 'char',
    // this could be expanded or modified to object so it would be able to compare
    // objects
    // and find a pattern in a matrix of any type.
    // It uses KMP pattern searching algorithm
    private static boolean isPatternPresentInRowColumn(char[][] charArrayMatrix, char pattern[]) {

        int N = charArrayMatrix.length;
        char[] col = new char[N];

        for (int i = 0; i < N; i++) {

            // search in row i
            if (KMPSearch(pattern, charArrayMatrix[i], N) == 1) {
                return true;
            }

            // Construct an array to store columns
            for (int j = 0; j < N; j++) {
                col[j] = charArrayMatrix[j][i];
            }

            // Search in column i
            if (KMPSearch(pattern, col, N) == 1) {
                return true;
            }
        }
        return false;
    }


    public static boolean isAnyPatternPresentInStringArray(String[] stringArray, char[][] patterns) {

        char[][] charMatrix = getCharMatrix(stringArray);

        if (!isValidInput(charMatrix, patterns))
            throw new WrongStringFormatException("Wrong array size or character not expected, verify your data");

        for (char[] pattern : patterns) {
            if (isPatternPresentInStringArray(charMatrix, pattern))
                return true;
        }
        return false;
    }


    private static boolean isValidInput(char[][] input, char[][] patterns) {
        long arraySize = input.length;
        long stringSize = input[0].length;
        boolean validCharacter = true;

        if (arraySize != stringSize)
            return false;

        for (char[] string : input) {
            if (string.length != stringSize)
                return false;
        }

        //populating pattern characters so we can compare all of them in the matrix.
        char[] patternCharacters = new char[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            patternCharacters[i] = patterns[i][0];
        }


        for (int row = 0; row < arraySize; row++) {
            for (int col = 0; col < arraySize; col++) {
                if (!validCharacter)
                    return false;
                validCharacter = false;
                for (int pat = 0; pat < patternCharacters.length; pat++) {
                    if (input[row][col] == patternCharacters[pat])
                        validCharacter = true;
                }
            }
        }

        return true;

    }


    private static boolean isPatternPresentInStringArray(char[][] charMatrix, char[] pattern) {


        if (!isPatternPresentInRowColumn(charMatrix, pattern)) {
            if (!isPatternPresentInDiagonal(charMatrix, pattern)) {
                return false;
            }
        }

        return true;
    }


    private static char[][] getCharMatrix(String[] stringArray) {

        char[][] charMatrix = new char[stringArray.length][stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            charMatrix[i] = stringArray[i].toCharArray();
        }

        return charMatrix;
    }

}