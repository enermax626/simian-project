package com.simian.project.simianproject.util;

public interface StringPatternFinder {

    boolean isValidInput(String[] matrix, char[][] patterns);

    char[][] getCharMatrix(String[] stringArray);

    int KMPSearch(char pattern[], char txt[], int N);

    boolean isPatternPresentInDiagonal(char[][] matrix, char pattern[]);

    boolean isPatternPresentInRowColumn(char[][] charArrayMatrix, char pattern[]);

    boolean isAnyPatternPresentInStringArray(String[] stringArray, char[][] patterns);

    boolean isPatternPresentInStringArray(char[][] charMatrix, char[] pattern);
}
