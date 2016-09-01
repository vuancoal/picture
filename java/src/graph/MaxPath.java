package graph;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxPath {
	public final int N;

	public MaxPath() throws Exception {
		String fileName = new File("").getAbsolutePath() + "/src/data/graph.txt";
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		N = Integer.parseInt(reader.readLine());
		List<Integer>[] graph = new ArrayList[N];
		for (int i = 0; i < N; ++i) {
			graph[i] = new ArrayList<>();
			String[] nodes = reader.readLine().split(" ");
			for (String node : nodes) {
				graph[i].add(Integer.parseInt(node));
			}
		}
		out.println(Arrays.toString(maxPath(graph)));
		reader.close();
	}

	public int[] maxPath(List<Integer>[] graph) {
		int n = graph.length;
		int[] res = new int[n];
		return res;
	}

	private void dfs(List<Integer>[] graph, boolean[] visited, int parent, int deep, final int n) {
		for (int child : graph[parent]) {
			if (!visited[child]) {
			}
		}
	}

	public static void main(String... args) {
		try {
			new MaxPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
