import java.util.ArrayList;

public class Sudoku {
	int[][] grid;

	/**
	 * @param grid
	 */
	public Sudoku(int[][] grid) { // new grid
		try {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (!verifyRange(grid[i][j] - 1)) {
						if (grid[i][j] != 0) {
							System.out.println("invalid input entry "
									+ grid[i][j] + " at " + "(" + i + "," + j
									+ ")");
						}
					}
				}
			}
			this.grid = grid;
		} catch (Exception e) {
			this.grid = new int[9][9]; // empty grid
			System.out.println("incorrect input grid size");
		}
	}

	public Sudoku() {
		this.grid = new int[9][9]; // empty grid
	}

	// strict verification will occur if verifyBlanks is true
	public boolean verifyGrid(boolean verifyBlanks) {
		// verify row and column
		for (int i = 0; i < 9; i++) {
			if (!verifyRow(i) || !verifyColumn(i)) {
				return false;
			}
		}
		// verify only the 9 squares
		for (int i = 0; i < 9; i = i + 3) {
			for (int j = 0; j < 9; j = j + 3) {
				if (!verifySquare(i, j)) {
					return false;
				}
			}
		}
		if (verifyBlanks) {
			// make sure there are no blank entries
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (this.grid[i][j] == 0) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// does not allow solve to be called unless input grid is valid
	public boolean solveHelper() {
		if (verifyGrid(false)) {
			return this.solve();
		} else {
			System.out.println("input sudoku has an error");
			return false;
		}

	}

	public boolean solve() {
		// iterate over every grid entry
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// check if grid is empty
				if (this.grid[i][j] == 0) {
					// iterate over every possible value for an entry
					for (int k = 1; k <= 9; k++) {
						this.grid[i][j] = k; // set entry value
						// check if entry value is valid
						if (verifyRow(i) && verifyColumn(j)
								&& verifySquare(i, j)) {
							// recursively call solve to see if new value leads
							// to solution
							if (solve()) {
								return true;
							}
						}
						// reset value if it does not lead to solution
						this.grid[i][j] = 0;
					}
					// condition for backtracking
					return false;
				}
			}
		}
		return true;
	}

	public boolean verifyRow(int rowNumber) {
		// make sure row number input is in valid range
		if (!verifyRange(rowNumber)) {
			return false;
		}
		for (int i = 0; i < 9; i++) {
			int value = this.grid[rowNumber][i]; // get grid value for row
			// check if entry value is valid
			if (!verifyRange(value - 1)) {
				// only if invalid value is not 0
				if (value != 0) {
					return false;
				}
			}
			if (value != 0) {
				// check to see if value is not repeated in row
				for (int j = 0; j < 9; j++) {
					if ((i != j) && value == this.grid[rowNumber][j]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean verifyColumn(int columnNumber) {
		// make sure row number input is in valid range
		if (!verifyRange(columnNumber)) {
			return false;
		}
		for (int i = 0; i < 9; i++) {
			int value = this.grid[i][columnNumber]; // get grid value for column
			// check if entry value is valid
			if (!verifyRange(value - 1)) {
				// only if invalid value is not 0
				if (value != 0) {
					return false;
				}
			}
			if (value != 0) {
				// check to see if value is not repeated in column
				for (int j = 0; j < 9; j++) {
					if ((i != j) && value == this.grid[j][columnNumber]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// performs verification on only one 3x3 square given by input boundaries
	public boolean verifySquareHelper(int outerL, int outerH, int innerL,
			int innerH) { // takes boundaries on inner and outer loop so
							// verification can be performed on a particular
							// square
		// holds unique values in square
		ArrayList<Integer> visited = new ArrayList<Integer>();
		for (int i = outerL; i < outerH; i++) {
			for (int j = innerL; j < innerH; j++) {
				int value = this.grid[i][j];
				// check if entry value is valid
				if (!verifyRange(value - 1)) {
					// only if invalid value is not 0
					if (value != 0) {
						return false;
					}
				}
				// checks current value against those already visited
				if (visited.contains(value) && value != 0) {
					return false;
				} else {
					visited.add(value);
				}
			}
		}
		return true;
	}

	public boolean verifySquare(int rowNumber, int columnNumber) {
		// check if rowNumber and columnNumber are in valid range
		if (!verifyRange(columnNumber) || !verifyRange(rowNumber)) {
			return false;
		}
		if ((rowNumber < 3) && (columnNumber < 3)) { // square 1
			if (!verifySquareHelper(0, 3, 0, 3)) {
				return false;
			}
		} else if ((rowNumber < 3)
				&& ((columnNumber < 6) && (columnNumber >= 3))) { // square 2
			if (!verifySquareHelper(0, 3, 3, 6)) {
				return false;
			}
		} else if ((rowNumber < 3)
				&& ((columnNumber < 9) && (columnNumber >= 6))) { // square 3
			if (!verifySquareHelper(0, 3, 6, 9)) {
				return false;
			}
		} else if (((rowNumber < 6) && (rowNumber >= 3)) && (columnNumber < 3)) { // square
																					// 4
			if (!verifySquareHelper(3, 6, 0, 3)) {
				return false;
			}
		} else if (((rowNumber < 6) && (rowNumber >= 3))
				&& ((columnNumber < 6) && (columnNumber >= 3))) { // square 5
			if (!verifySquareHelper(3, 6, 3, 6)) {
				return false;
			}
		} else if (((rowNumber < 6) && (rowNumber >= 3))
				&& ((columnNumber < 9) && (columnNumber >= 6))) { // square 6
			if (!verifySquareHelper(3, 6, 6, 9)) {
				return false;
			}
		} else if (((rowNumber < 9) && (rowNumber >= 6)) && (columnNumber < 3)) { // square
																					// 7
			if (!verifySquareHelper(6, 9, 0, 3)) {
				return false;
			}
		} else if (((rowNumber < 9) && (rowNumber >= 6))
				&& ((columnNumber < 6) && (columnNumber >= 3))) { // square 8
			if (!verifySquareHelper(6, 9, 3, 6)) {
				return false;
			}
		} else if (((rowNumber < 9) && (rowNumber >= 6))
				&& ((columnNumber < 9) && (columnNumber >= 6))) { // square 9
			if (!verifySquareHelper(6, 9, 6, 9)) {// lol
				return false;
			}
		} else {
			System.out.println("something terrible has happened...");
			return false;
		}
		return true;
	}

	public int[][] getGrid() {
		return this.grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	public void printSolution() {
		if (this.solveHelper()) {
			System.out.println("Solved sudoku:");
		} else {
			System.out.println("Sudoku does not have a solution...");
			System.out.println("Unsolved sudoku:");
		}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if ((j == 3) || (j == 6)) {
					System.out.print("|");
				}
				System.out.print(this.grid[i][j] + " ");
			}
			if ((i == 2) || (i == 5)) {
				System.out.println();
				System.out.print("-------------------");
				System.out.println();
			} else {
				System.out.println();
			}
		}
	}

	public boolean setValue(int rowNumber, int columnNumber, int value) {
		if (verifyRange(rowNumber, columnNumber, value - 1)) {
			this.grid[rowNumber][columnNumber] = value;
			return true;
		} else {
			return false;
		}
	}

	public boolean verifyRange(int... number) { // verifies that the numbers are
												// between 0 and 8 inclusive
		for (int i = 0; i < number.length; i++) {
			if ((number[i] >= 0) && (number[i] <= 8)) {
				// pass
			} else {
				return false;
			}
		}
		return true;
	}

	public static void main(String args[]) {
		int[][] inputGrid = new int[9][9];
		int[] r1 = { 2, 5, 7, 3, 1, 0, 0, 0, 0 };
		int[] r2 = { 4, 1, 0, 9, 0, 0, 8, 0, 7 };
		int[] r3 = { 6, 8, 0, 0, 0, 0, 0, 0, 0 };
		int[] r4 = { 0, 0, 0, 0, 3, 7, 0, 0, 6 };
		int[] r5 = { 8, 0, 1, 5, 0, 0, 0, 3, 0 };
		int[] r6 = { 0, 0, 0, 0, 0, 1, 5, 0, 4 };
		int[] r7 = { 0, 0, 4, 0, 0, 2, 0, 0, 1 };
		int[] r8 = { 0, 0, 8, 4, 0, 3, 0, 0, 5 };
		int[] r9 = { 5, 0, 0, 0, 0, 0, 0, 4, 0 };		
		inputGrid[0] = r1;
		inputGrid[1] = r2;
		inputGrid[2] = r3;
		inputGrid[3] = r4;
		inputGrid[4] = r5;
		inputGrid[5] = r6;
		inputGrid[6] = r7;
		inputGrid[7] = r8;
		inputGrid[8] = r9;
		Sudoku s = new Sudoku(inputGrid);
		s.printSolution();
	}
}