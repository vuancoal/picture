package graph;

import static java.lang.System.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VuanCoal on 2016/07/14.
 */

public class MaxSTC {
	int n;
	final static int K = 13;
	boolean g[][], stc[][];

	void start() {
		init();
		if (!g[0][4]) return;
		stc[0][4] = true;
		out.println("stc[0][4] <-- true;");
		find(stc);
	}

	void find(boolean[][] stc) {
		List<int[]> next = nextRecommendStrongEdges(g, stc);
		if (next == null) {
			if (getNumberOfStrongEdge(stc) >= K)
				print(getHumanReadableGraph(stc));
			return;
		}
		for (int[] edge : next) {
			boolean[][] newStc = clone(stc);
			newStc[edge[0]][edge[1]] = true;
			find(newStc);
		}
	}

	void print(List<int[]> list) {
		for (int[] edge : list) {
			out.print(String.format("[%d, %d] ", edge[0], edge[1]));
		}
		out.println("");
	}

	List<int[]> getHumanReadableGraph(boolean[][] g) {
		int len = g.length;
		List<int[]> list = new ArrayList<>();
		for (int i = 0; i < len; ++i) {
			for (int j = 0; j < len; ++j) {
				if (g[i][j]) list.add(new int[]{i, j});
			}
		}
		return list;
	}

	List<int[]> nextRecommendStrongEdges(boolean g[][], boolean[][] stc) {
		int count = 0;
		List<int[]> list = new ArrayList<>();
		for (int i = 0; i < n; ++i) {
			for (int j = i + 1; j < n; ++j) {
				if (!g[i][j]) continue;
				if (sastifyAfterAdd(i, j, stc)) {
					++count;
					list.add(new int[]{i, j});
				}
			}
		}

		return (count == 0) ? null : list;
	}

	int getNumberOfStrongEdge(boolean[][] stc) {
		int len = stc.length, cnt = 0;
		for (int i = 0; i < len; ++i) {
			for (int j = i + 1; j < len; ++j) {
				if (stc[i][j]) ++cnt;
			}
		}
		return cnt;
	}

	void init() {
		int lineNo = 0;
		String fileName = new File("").getAbsolutePath() + "/src/library/practice/graph.txt", line;
//		String fileName = new File("").getAbsolutePath() + "/graph.txt", line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			n = Integer.parseInt(reader.readLine());
			out.println("n = " + n);
			g = new boolean[n][n];
			stc = new boolean[n][n];
			while ((line = reader.readLine()) != null && lineNo < n) {
				String[] elems = line.split(" ");
				for (int i = 0; i < elems.length; ++i) {
					g[lineNo][Integer.parseInt(elems[i])] = true;
				}
				++lineNo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean sastifyAfterAdd(int u, int v, boolean[][] stc) {
		if (!exist(g, u, v) || exist(stc, u, v)) return false;
		for (int i = 0; i < n; ++i) {
			if (i == v || !exist(g, u, i) || !exist(stc, u, i)) continue;
			if (!exist(g, v, i)) return false;
		}

		for (int i = 0; i < n; ++i) {
			if (i == u || !exist(g, v, i) || !exist(stc, v, i)) continue;
			if (!exist(g, u, i)) return false;
		}

		return true;
	}

	boolean exist(boolean[][] g, int u, int v) {
		return (g[u][v] || g[v][u]);
	}

	boolean[][] clone(boolean[][] arr) {
		int n = arr.length;
		boolean[][] res = new boolean[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				res[i][j] = arr[i][j];
			}
		}
		return res;
	}

	public static void main(String... args) {
		new MaxSTC().start();
	}
}
