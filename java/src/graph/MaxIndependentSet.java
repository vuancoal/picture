package graph;

import javafx.scene.Parent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import static java.lang.System.out;

public class MaxIndependentSet {
	private static MaxIndependentSet ins;

	public MaxIndependentSet() {
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

		out.println(Arrays.toString(mis(tree, 0)));
	}

	/**
	 * 動的計画法を用いて，木の最大独立集合を計算する関数
	 *
	 * @param tree 木
	 * @param root 木の根，即ち入力の木は根付きき
	 * @return 最大独立集合
	 */
	public int[] mis(List<Integer>[] tree, int root) {
		int n = tree.length;
		boolean visited[] = new boolean[n];
		int max[] = new int[n];
		int parent[] = new int[n];
		List<Integer>[] sol = new ArrayList[n];
		for (int i = 0; i < n; ++i) sol[i] = new ArrayList<>();
		dfsForMIS(tree, root, visited, max, parent, sol);
		int res[] = new int[sol[root].size()];
		for (int i = 0; i < res.length; ++i)
			res[i] = sol[root].get(i);
		return res;
	}

	private void dfsForMIS(List<Integer>[] tree, int node, boolean[] visited, int[] max, int[] parent, List<Integer>[] sol) {
		visited[node] = true;
		for (int child : tree[node]) {
			if (visited[child]) continue;
			parent[child] = node;
			dfsForMIS(tree, child, visited, max, parent, sol);
		}

		int opt1 = 0;
		for (int child : tree[node]) {
			if (child == parent[node]) continue;
			opt1 += max[child];
		}

		int opt2 = 1;
		for (int child : tree[node]) {
			if (child == parent[node]) continue;
			for (int grandchild : tree[child]) {
				if (grandchild == node) continue;
				opt2 += max[grandchild];
			}
		}

		if (opt1 < opt2) {
			sol[node].add(node);
			for (int child : tree[node]) {
				if (child == parent[node]) continue;
				for (int grandchild : tree[child]) {
					if (grandchild == node) continue;
					for (int elem : sol[grandchild]) {
						sol[node].add(elem);
					}
				}
			}
		} else {
			for (int child : tree[node]) {
				if (child == parent[node]) continue;
				for (int elem : sol[child]) {
					sol[node].add(elem);
				}
			}
		}
		max[node] = Integer.max(opt1, opt2);
	}

	public static void main(String... args) {
		getIns();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	public static MaxIndependentSet getIns() {
		return (ins == null) ? (ins = new MaxIndependentSet()) : ins;
	}
}
