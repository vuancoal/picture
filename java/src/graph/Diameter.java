package graph;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Diameter {
	private static Diameter ins;

	public Diameter() {
		List<Integer> tree[] = null;
		String fileName = new File("").getAbsolutePath() + "/src/data/tree.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int n = Integer.parseInt(br.readLine());
			tree = new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				tree[i] = new ArrayList<>();
				String[] elems = br.readLine().split(" ");
				for (String node : elems) {
					tree[i].add(Integer.parseInt(node));
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		out.println(Arrays.toString(diameterOfTree(tree)));
	}

	/**
	 * 木の直径を計算する関数
	 *
	 * @param tree
	 *            木
	 * @return 木の直径を形成する二頂点
	 */
	public int[] diameterOfTree(List<Integer>[] tree) {
		int u = longestPath(tree, 0);
		int v = longestPath(tree, u);
		return new int[] { u, v };
	}

	private int longestPath(List<Integer>[] tree, int src) {
		int n = tree.length, res = 0;
		Queue<Integer> queue = new LinkedList<>();
		boolean[] visited = new boolean[n];
		int[] l = new int[n];
		queue.add(src);
		while (queue.size() > 0) {
			src = queue.remove();
			// if (visited[parent]) continue;
			visited[src] = true;
			for (int child : tree[src]) {
				if (visited[child])
					continue;
				queue.add(child);
				l[child] = l[src] + 1;
			}
		}

		int maxL = Integer.MIN_VALUE, indexMaxL = -1;
		for (int i = 0; i < n; ++i) {
			if (maxL < l[i]) {
				maxL = l[i];
				indexMaxL = i;
			}
		}

		return indexMaxL;
	}

	public static void main(String... args) {
		getIns();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	public static Diameter getIns() {
		return (ins == null) ? (ins = new Diameter()) : ins;
	}
}
