package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class MaxMatching {
	private static MaxMatching ins;

	private MaxMatching() {
		test2();
	}

	void test2() {
		MGraph tree = MGraph.getIns();
		try {
			String fileName = new File("").getAbsolutePath() + "/src/data/tree.txt";
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int n = Integer.parseInt(br.readLine());
			for (int i = 0; i < n; ++i)
				tree.addVertex(i);
			for (int i = 0; i < n; ++i) {
				for (String vertex : br.readLine().split(" ")) {
					tree.addEdge(i, Integer.parseInt(vertex), 1);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void test1() {
		List<Integer> tree[] = null;
		try {
			String fileName = new File("").getAbsolutePath() + "/src/data/graph.txt";
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int n = Integer.parseInt(br.readLine());
			tree = new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				String elems[] = br.readLine().split(" ");
				tree[i] = new ArrayList<>();
				for (String node : elems) {
					tree[i].add(Integer.parseInt(node));
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println("tree: " + Arrays.deepToString(tree));
		out.println(System.getProperty("line.separator") + "max matching cardinity: " + maxMatching1(tree));
	}

	/**
	 * 木の最大マッチングを計算する関数
	 *
	 * @param tree 木
	 * @return 最大マッチングの値
	 */
	public int maxMatching1(List<Integer>[] tree) {
		int n = tree.length, res[] = new int[1];
		boolean[] visited = new boolean[n];
		final List<Integer>[] copy = new ArrayList[n];
		for (int i = 0; i < n; ++i) {
			copy[i] = new ArrayList<>();
			for (int node : tree[i])
				copy[i].add(node);
		}
		out.print("max matching edge-set: ");
		dfs(tree, copy, 0, visited, res);
		return res[0];
	}

	private void dfs(List<Integer>[] tree, List<Integer>[] copy, int node, boolean[] visited, int[] res) {
		visited[node] = true;
		for (int i = 0; i < copy[node].size(); ++i) {
			int child = copy[node].get(i);
			if (visited[child]) continue;
			dfs(tree, copy, child, visited, res);

			// remove leaf and its parent
			if (tree[child].size() == 1) {
				res[0]++;
				int parent = tree[child].get(0);
				for (int nei : tree[parent]) {
					for (int j = 0; j < tree[nei].size(); ++j) {
						if (tree[nei].get(j) == parent) {
							tree[nei].remove(j);
							break;
						}
					}
				}
				tree[parent].clear();
				out.print(String.format("[%d, %d] ", parent, child));
			}
		}
	}

	/**
	 * 木の最大マッチングを計算する関数
	 *
	 * @param tree 各要素がリストとなる配列で保存される木
	 * @return 木の最大マッチング
	 */
	public int maxMatching2(List<Integer>[] tree) {
		int n = tree.length, res = 0;
		boolean hasLeaf = true;
		out.print("max matching edge-set: ");
		while (hasLeaf) {
			hasLeaf = false;
			for (int i = 0; i < n; ++i) {
				if (tree[i].size() != 1) continue;
				hasLeaf = true;

				// remove leaf and its parent
				int parent = tree[i].get(0);
				for (int j = 0; j < tree[parent].size(); ++j) {
					int neighbor = tree[parent].get(j);
					for (int k = 0; k < tree[neighbor].size(); ++k) {
						if (tree[neighbor].get(k) == parent) {
							tree[neighbor].remove(k);
							break;
						}
					}
				}
				tree[parent].clear();
				res++;
				out.print(String.format("[%d, %d] ", parent, i));
			}
		}
		return res;
	}

	public static void main(String... args) {
		double start = System.currentTimeMillis();
		getIns();
		out.println("consumed " + (System.currentTimeMillis() - start) / 1000 + " (s)");
	}

	synchronized static MaxMatching getIns() {
		return (ins == null) ? (ins = new MaxMatching()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}
}
