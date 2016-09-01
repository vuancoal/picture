package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 連結度を計算するプログラム
 */
public class VertexConnected {
	private static VertexConnected ins;
	List<Integer> graph[];

	private VertexConnected() {
		String fileName = new File("").getAbsolutePath() + "/src/data/graph.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			int n = Integer.parseInt(br.readLine());
			graph = new ArrayList[n];
			for (int i = 0; i < n; ++i) {
				graph[i] = new ArrayList<>();
				for (String node : br.readLine().split(" ")) {
					graph[i].add(Integer.parseInt(node));
				}
			}
			br.close();
		} catch (Exception e) {
		}
	}

	public static void main(String... args) {
		getIns();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("not support clone");
	}

	public static VertexConnected getIns() {
		return (ins == null) ? (ins = new VertexConnected()) : ins;
	}
}
