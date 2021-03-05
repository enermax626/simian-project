package com.simian.project.simianproject.utilImpl;

import com.simian.project.simianproject.exception.WrongStringFormatException;
import com.simian.project.simianproject.util.StringPatternFinder;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class StringPatternFinderImpl implements StringPatternFinder {


    @Override
    public boolean isValidInput(String[] matrix, char[][] patterns) {
        long arraySize = matrix.length;
        long stringSize = matrix[0].length();
        boolean validCharacter = true;

        if (stringSize != arraySize)
            return false;

        if (Arrays.stream(matrix).parallel()
                .anyMatch(row -> row.length() != stringSize))
            return false;

        String patternChars = Arrays.stream(patterns).parallel().map(s -> s[0]).collect(Collectors.toList()).toString();
        String regex = ".*[^" + patternChars + "].*";


        if(Arrays.stream(matrix).parallel().anyMatch(m ->
                m.matches(regex)))
            return false;


        return true;
    }

    @Override
    public char[][] getCharMatrix(String[] stringArray) {

        char[][] charMatrix = new char[stringArray.length][stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            charMatrix[i] = stringArray[i].toCharArray();
        }

        return charMatrix;
    }

    // used to compute the preprocessing array that KPM will use to improve its
    // performance.
    private void computeLPSArray(char[] pat, int M, int[] lps) {
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

    @Override
    public int KMPSearch(char[] pattern, char[] txt, int N) {

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

    @Override
    public boolean isPatternPresentInDiagonal(char[][] matrix, char[] pattern) {

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
    @Override
    public boolean isPatternPresentInRowColumn(char[][] charArrayMatrix, char[] pattern) {

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

    @Override
    public boolean isAnyPatternPresentInStringArray(String[] stringArray, char[][] patterns) {

        char[][] charMatrix = getCharMatrix(stringArray);

        if (!isValidInput(stringArray, patterns))
            throw new WrongStringFormatException("Wrong array size or character not expected, verify your data");

        if (Arrays.stream(patterns)
                .anyMatch(entry -> isPatternPresentInStringArray(charMatrix, entry)))
            return true;

        return false;
    }

    @Override
    public boolean isPatternPresentInStringArray(char[][] charMatrix, char[] pattern) {

        if (!isPatternPresentInRowColumn(charMatrix, pattern)) {
            return isPatternPresentInDiagonal(charMatrix, pattern);
        }

        return true;
    }


}