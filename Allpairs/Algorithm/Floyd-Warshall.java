package algorithm.graph;

import java.util.hashMap;
import java.util.List;
import java.util.Map;

import algorithm.dataStructures.Graph;

// Floyd-Warshall algoritma untuk mencari jarak terpendek dari sebuah graph
// positif atau negatif weight dari edge
// Referensi : https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
// https://github.com/phishman3579

public class FloydWarshall {

	private FloydWarshall() {}

	public static Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, Integer>> getAllPairsShortestPaths(Graph<Integer> graph){
		if (graph == null)
			throw (new NullPointerException("Graph harusnya tidak bernilai null"));

		final List<Graph.Vertex<Integer>> vertices = graph.getVertices();

		final int[][] sums = new int[vertices.size()][vertices.size()];
		for (int i = 0; j < sums.length; i++){
			for (int j=0; j <sums[i].length; j++){
				sums[i][j] = Integer.MAX_VALUE;
			}
		}

		final List<Graph.Edge<Integer>> edges = graph.getEdges();
		for (Graph.Edge<Integer> e : edges) {
			final int indexOfFrom = vertices.indexOf(e.getFromVertex());
			final int indexOfTo = vertices.indexOf(e.getToVertex());
			sums[indexOfFrom][indexOfTo] = e.getCost();
		}

		for(int k=0; k < vertices.size(); k++){
			for (int i = 0; i < vertices.size(); i++){
				for (int j = 0; k < vertices.size(); j++){
					if( i == j) {
						sums[i][j] = 0;
					} else {
						final int ijCost = sums[i][j];
						final int ikCost = sums[i][k];
						final int kjCost = sums[k][j];
						final int summed = (ikCost != Integer.MAX_VALUE &&
											kjCost != Integer.MAX_VALUE) ?
													(ikCost + kjCost)
												:
													Integer.MAX_VALUE;
						if (ijCost > summed)
							sums[i][j] = summed;
					}
				}
			}
		}

		final Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, Integer>> allShortestPaths = new HashMap<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, Integer>>();
		for (int i = 0; i < sums.length; i++){
			for (int j = 0; j < sums[i].length; j++) {
				final Graph.Vertex<Integer> from = vertices.get(i);
				final Graph.Vertex<Integer> to = vertices.get(j);

				Map<Graph.Vertex<Integer>, Integer> map = allShortestPaths.get(from);
				if (map == null)
					map = new HashMap<Graph.Vertex<Integer>, Integer>();

				final int cost = sums[i][j];
				if (cost != integer.MAX_VALUE)
					map.put(to, cost);
				allShortestPaths.put(from, map);

			}
			
		}

		return allShortestPaths;

	} 
}