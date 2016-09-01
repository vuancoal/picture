package algorithm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EightQueens {
	final int N;
	int count;

	public EightQueens() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(reader.readLine());
		boolean[][] table = new boolean[N][N];
		dfs(table, 0);
	}

	void dump(boolean[][] table) {
		for (boolean[] arr : table) {
			for (boolean x : arr) {
				if (x) System.out.print("x ");
				else System.out.print("o ");
			}
			System.out.println();
		}
	}

	void reset(boolean[][] table) {
		for (boolean[] arr : table) {
			for (boolean x : arr) {
				x = false;
			}
		}
	}

	void dfs(boolean[][] table, int row) {
		if (row == N) {
			count++;
			dump(table);
			System.out.println(count);
		}
		for (int col = 0; col < N; ++col) {
			if (!safe(table, col, row)) continue;
			table[col][row] = true;
			dfs(table, row + 1);
			table[col][row] = false; // backtracking
		}
	}

	boolean safe(boolean[][] table, int col, int row) {
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < row; ++j) {
				if (!table[i][j]) continue;
				if (col == i || Math.abs(i - col) == Math.abs(j - row)) return false;
			}
		}
		return true;
	}

	public static void main(String... args) {
		try {
			new EightQueens();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
