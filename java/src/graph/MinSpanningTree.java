package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import static java.lang.System.nanoTime;
import static java.lang.System.out;

public class MinSpanningTree {
	private static MinSpanningTree ins;

	public MinSpanningTree() {
		List<Integer>[] g = null;
		String fileName = new File("").getAbsolutePath() + "/src/data/graph.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int n = Integer.parseInt(br.readLine());
			g = new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				g[i] = new ArrayList<>();
				for (String node : br.readLine().split(" "))
					g[i].add(Integer.parseInt(node));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

//		mstWithKruskal(g);
		out.println("tree: " + Arrays.toString(mstWithPrim(g)));
	}

	/**
	 * プリムのアルゴリズムを用いて，最小全域木を見つける
	 *
	 * @param g 特定のグラフ
	 * @return 全域木の各頂点の親
	 */
	public int[] mstWithPrim(List<Integer>[] g) {
		MGraph graph = MGraph.getIns();
		Random rnd = new Random();
		int n = g.length, parent[] = new int[n];
		graph.initGraph(g, 5);

		Set<Integer> set = new HashSet<>();
		boolean insideSet[] = new boolean[n];
		set.add(0);
		parent[0] = -1;
		insideSet[0] = true;
		int e = 0, min, w, bestU = -1, bestV = -1;
		while (e < n - 1) {
			for (int u : set) {
				min = Integer.MAX_VALUE;
				for (int v : g[u]) {
					if (insideSet[v]) continue;
					w = (Integer) graph.getWeight(u, v);
					if (min > w) {
						min = w;
						bestU = u;
						bestV = v;
					}
				}
			}

			set.add(bestV);
			insideSet[bestV] = true;
			parent[bestV] = bestU;
			e++;
		}
		return parent;
	}

	/**
	 * クラスカルのアルゴリズムを用いて，最小全域木を見つける
	 *
	 * @param g 特定のグラフ
	 * @return 全域木の各頂点の親
	 */
	public int[] mstWithKruskal(List<Integer>[] g) {
		int n = g.length;
		Random rnd = new Random();
		MGraph graph = MGraph.getIns();
		for (int i = 0; i < n; ++i)
			graph.addVertex(i);
		for (int i = 0; i < n; ++i) {
			for (int v : g[i])
				graph.addEdge(i, v, rnd.nextInt(5));
		}

		out.println(graph.getEdgeMap().toString());
		Map sortedMap = graph.sortByValue(graph.getEdgeMap());
		out.println(sortedMap.toString());
		return null;
	}

	public static void main(String... args) {
		getIns();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new RuntimeException("not support clone");
	}

	public static MinSpanningTree getIns() {
		return (ins == null) ? (ins = new MinSpanningTree()) : ins;
	}
}
