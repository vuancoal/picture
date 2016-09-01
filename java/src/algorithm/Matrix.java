package algorithm;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
	public Matrix() {
		Integer[][] m1, m2, m3, m4;
		m1 = new Integer[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		m2 = new Integer[][] { { 2 }, { 3 }, { 4 } };
		m3 = new Integer[][] { { 3, 4 } };
		m4 = new Integer[][] { { 6, 5, 4 }, { 3, 2, 1 } };

		List<Integer[][]> list = new ArrayList<>();
		list.add(m1);
		list.add(m2);
		list.add(m3);
		list.add(m4);

		out.println(toString(multiply(list)));
	}

	public String toString(int[][] matrix) {
		String res = "", lf = System.getProperty("line.separator");
		for (int i = 0; i < matrix.length; ++i) {
			res += Arrays.toString(matrix[i]);
			res += lf;
		}
		return res;
	}

	/**
	 * 与えられた行列のリストに対し，行列の積を最適に計算するために， どのように組み合わせて計算するかを求める
	 *
	 * @param list
	 *            行列のリスト
	 * @return 区間を分けるインデックスの配列
	 */
	public <T> int[][] multiply(List<T[][]> list) {
		int n = list.size(), optimalIndex[][] = new int[n][n];
		for (int i = 0; i < n; ++i)
			Arrays.fill(optimalIndex[i], -1);
		for (int d = 1; d < n; ++d) {
			for (int i = 0; i < n; ++i) {
				if (i + d >= n)
					break;
				optimize(list, i, i + d, optimalIndex);
			}
		}
		return optimalIndex;
	}

	private <T> int optimize(List<T[][]> list, int i1, int i2, int[][] optimalIndex) {
		if (i2 - i1 < 0)
			throw new RuntimeException(i1 + ", " + i2);
		else if (i2 - i1 == 0)
			return 0;
		else if (i2 - i1 == 1) {
			if (optimalIndex[i1][i2] != 0)
				return optimalIndex[i1][i2];
			T[][] m1 = list.get(i1), m2 = list.get(i2);
			int val = m1.length * m2.length * m2[0].length;
			out.println(String.format("min[%d, %d]: %d", i1, i2, val));
			return m1.length * m2.length * m2[0].length;
		}

		int minVal = Integer.MAX_VALUE;
		for (int d = 1; d < i2 - i1; ++d) {
			int x = list.get(i1).length;
			int y = list.get(i1 + d)[0].length;
			int z = list.get(i2)[0].length;
			int tmp = optimize(list, i1, i1 + d, optimalIndex) + optimize(list, i1 + d + 1, i2, optimalIndex)
					+ x * y * z;
			if (tmp < minVal) {
				minVal = tmp;
				optimalIndex[i1][i2] = i1 + d;
			}
		}
		return minVal;
	}

	public static void main(String... args) {
		new Matrix();
	}
}
