package com.simian.project.simianproject.util;

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
			}

			else if (i < N && pattern[j] != txt[i]) {

				if (j != 0) {
					j = lps[j - 1];

				} else {
					i = i + 1;
				}
			}
		}

		return 0;
	}
	
	// It's similar to KMP algorithm but some changes were made because we don't
	// need to search in whole matrix
	// since it is not fully populated.
	private static boolean isPatternPresentInDiagonalRow(char[][] charArrayMatrix, char pattern[]) {

		int N = charArrayMatrix.length;
		int charInRow = 4;

		for (int i = 3; i < N - 3; i++) {

			// search in each row
			if (KMPSearch(pattern, charArrayMatrix[i], charInRow) == 1)
				return true;
			charInRow++;
		}
		return false;
	}

	// if we didn't limit it to 'char',
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

	public static boolean isPatternPresentInStringArray(String[] strigArray, char[] pattern) {

		char[][] charMatrix = getCharMatrix(strigArray);

		if (!isPatternPresentInRowColumn(charMatrix, pattern)) {
			char[][] diagonalCharMatrix = getdiagonalOrderMatrix(charMatrix);
			if (!isPatternPresentInDiagonalRow(diagonalCharMatrix, pattern)) {
				diagonalCharMatrix = getdiagonalOrderMatrix(invertMatrix(charMatrix));
				if (!isPatternPresentInDiagonalRow(diagonalCharMatrix, pattern)) {
					return false;
				}
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

	private static char[][] getdiagonalOrderMatrix(char[][] charMatrix) {

		int matrixSize = charMatrix.length;

		char[][] diagonalMatrix = new char[2 * matrixSize - 1][2 * matrixSize - 1];

		for (int line = 1; line <= (2 * matrixSize - 1); line++) {

			int start_col = Integer.max(0, line - matrixSize);

			int count = Integer.min(Integer.min(line, (matrixSize - start_col)), matrixSize);

			for (int j = 0; j < count; j++) {
				System.out.print(charMatrix[Integer.min(matrixSize, line) - j - 1][start_col + j] + " ");
				diagonalMatrix[line - 1][j] = charMatrix[Integer.min(matrixSize, line) - j - 1][start_col + j];

			}
			System.out.println();
		}
		return diagonalMatrix;

	}

	private static char[][] invertMatrix(char[][] matrix) {

		char[][] invertedMatrix = new char[matrix.length][matrix.length];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				invertedMatrix[i][j] = matrix[i][matrix.length - j - 1];
			}
		}
		return invertedMatrix;
	}

	// Utility function to print a String Matrix
	static void printMatrix(String matrix[]) {

		int matrixSize = matrix.length;

		char[][] diagonalMatrixArray = new char[matrixSize][matrixSize];

		for (int i = 0; i < matrix.length; i++) {
			diagonalMatrixArray[i] = matrix[i].toCharArray();
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				System.out.print(diagonalMatrixArray[i][j] + " ");
			System.out.print("\n");
		}
	}

	static void printCharMatrix(char[][] matrix) {

		System.out.println("PRINTANDO MATRIX");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

}