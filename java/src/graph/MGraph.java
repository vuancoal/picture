package graph;

import javax.print.attribute.standard.RequestingUserName;
import java.util.*;

public class MGraph<T, W> {
	private static MGraph ins;
	private Map<T, Set<T>> graph = new HashMap<T, Set<T>>();
	private Map<T, MVertex> vertexMap = new HashMap<T, MVertex>();
	private Map<String, MEdge> edgeMap = new HashMap<>();

	private MGraph() {
	}

	public static MGraph getIns() {
		return (ins == null) ? (ins = new MGraph()) : ins;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public void initGraph(List<Integer>[] lists, int maxWeight) {
		int n = lists.length;
		Random random = new Random();
		for (Integer u = 0; u < n; ++u)
			addVertex((T) u);
		for (Integer u = 0; u < n; ++u) {
			for (Integer v : lists[u]) {
				Integer w = random.nextInt(maxWeight);
				T tu = (T) u, tv = (T) v;
				W ww = (W) w;
				if (!hasEdge(tv, tu))
					addEdge(tu, tv, ww);
				else addEdge(tu, tv, getWeight(tv, tu));
			}
		}
	}

	public void initDirectedGraph(List<Integer>[] lists, int maxWeight) {
		int n = lists.length;
		Random random = new Random();
		for (Integer u = 0; u < n; ++u)
			addVertex((T) u);
		for (Integer u = 0; u < n; ++u) {
			for (Integer v : lists[u]) {
				Integer w = random.nextInt(maxWeight);
				T tu = (T) u, tv = (T) v;
				W ww = (W) w;
				addEdge(tu, tv, ww);
			}
		}
	}

	public void initGraph(Map<T, Set<T>> g, int maxWeight) {
		Random random = new Random();
		for (T u : g.keySet())
			addVertex(u);
		for (T u : g.keySet()) {
			for (T v : g.get(u)) {
				Integer w = random.nextInt(maxWeight);
				if (!hasEdge(v, u))
					addEdge(u, v, (W) w);
				else addEdge(u, v, getWeight(v, u));
			}
		}
	}

	public void initDirectedGraph(Map<T, Set<T>> g, int maxWeight) {
		Random random = new Random();
		for (T u : g.keySet())
			addVertex(u);
		for (T u : g.keySet()) {
			for (T v : g.get(u)) {
				Integer w = random.nextInt(maxWeight);
				addEdge(u, v, (W) w);
			}
		}
	}

	public Map sortByValue(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				return ((Comparable) (((Map.Entry) o1).getValue())).compareTo(((Map.Entry) o2).getValue());
			}
		});

		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext(); ) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public String hashKey(T u, T v) {
		return "(" + u.toString() + ", " + v.toString() + ")";
	}

	public int n() {
		return vertexMap.size();
	}

	public int m() {
		return edgeMap.size();
	}

	public boolean addVertex(T u) {
		if (hasVertex(u)) return false;
		graph.put(u, new HashSet<T>());
		vertexMap.put(u, new MVertex(u));
		return true;
	}

	public boolean addEdge(T u, T v, W w) {
		if (hasEdge(u, v)) return false;
		graph.get(u).add(v);
		edgeMap.put(hashKey(u, v), new MEdge(u, v, w));
		return true;
	}

	public boolean removeVertex(T u) {
		if (!hasVertex(u)) throw new NoSuchElementException();
		vertexMap.remove(u);
		for (T v : graph.get(u))
			edgeMap.remove(hashKey(u, v));
		for (T v : graph.keySet())
			edgeMap.remove(hashKey(v, u));
		for (T v : graph.get(u))
			graph.get(v).remove(u);
		graph.remove(u);
		return true;
	}

	public boolean removeEdge(T u, T v) {
		if (!hasEdge(u, v)) throw new NoSuchElementException();
		graph.get(u).remove(v);
		edgeMap.remove(hashKey(u, v));
		return true;
	}

	public boolean hasVertex(T u) {
		return graph.containsKey(u);
	}

	public boolean hasEdge(T u, T v) {
		if (!graph.containsKey(u) || !graph.containsKey(v))
			throw new NoSuchElementException();
		return graph.get(u).contains(v);
	}

	public W getWeight(T u, T v) {
		if (!hasEdge(u, v)) throw new NoSuchElementException();
		return edgeMap.get(hashKey(u, v)).w;
	}

	public boolean setWeight(T u, T v, W w) {
		if (!hasEdge(u, v)) throw new NoSuchElementException();
		edgeMap.get(hashKey(u, v)).setWeight(w);
		return true;
	}

	public Map<T, MVertex> getVertexMap() {
		return vertexMap;
	}

	public Map<String, MEdge> getEdgeMap() {
		return edgeMap;
	}

	class MVertex implements Comparable<MVertex> {
		private T u;

		public MVertex(T u) {
			this.u = u;
		}

		@Override
		public int compareTo(MVertex o) {
			return ((Comparable) u).compareTo(o.u);
		}

		@Override
		public String toString() {
			return String.format("(%s)", u.toString());
		}
	}

	class MEdge implements Comparable<MEdge> {
		private T u, v;
		private W w;

		public MEdge(T u, T v, W w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}

		public String hashKey() {
			return MGraph.this.hashKey(u, v);
		}

		@Override
		public int compareTo(MEdge o) {
			return ((Comparable) w).compareTo(o.w);
		}

		@Override
		public String toString() {
			return String.format("(%s, %s, %s)", u.toString(), v.toString(), w.toString());
		}

		public W getWeight() {
			return w;
		}

		public void setWeight(W w) {
			this.w = w;
		}
	}
}
