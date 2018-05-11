package algorithm.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithm.dataStructures.Graph;

//referensi : https://en.wikipedia.org/wiki/Johnson%27s_algorithm

public class Johson {

	private Johson(){ }

	public static Map<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>> getAllPairsShortestPaths(Graph<Integer> g) {
		if (g == null){
			throw (new NullPointerException("Graph jangan null"));

		}

		// Pertama buat node bari sebagai penghubung atau konektor ke graph
		final Graph<Integer> graph = new Graph<Integer>(g);
		final Graph.Vertex<Integer> connector = new Graph.Vertex<Integer>(Integer.MAX_VALUE);

		// add konektor vektex ke semua egde
		for(Graph.Vertex<Integer> v : graph.getVertices()){
			final int indexOfV = graph.getVertices().indexOfV(v);
			final Graph.Edge<Integer> edge = new Graph.Edge<Integer>(0, connector, graph.getVertices().get(indexOfV));
			connector.addEdge(edge);
			graph.getEdges().add(edge);
		}

		graph.getVertices().add(connector);

		//kedua, menggunakan Bellman-Ford Algorithm, dimulai dari vertex yang baru, untuk mencari vertex 'V' untuk setiap vertex
		//minimum weight h(v) dari path konektor v . jika step ini mendeteksi negative cycle, algorithm akan dihentikan
		final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> costs = BellmanFord.getShortestPaths(graph, connector);

		//Selanjutnya edges dari graph aslinya ditimbang ulang menggunakan nilai dari perhitungan oleh bellman-Ford algorith
		//Edge dari u ke v, mempunya lenght w(u, v), diberikan panjang yang baru lenght w(u,v) + h(u) - h(v).

		for(Graph.Edge<Integer> e : graph.getEdges()){
			final int weight = e.getCost();
			final Graph.Vertex<Integer> u = e.getFromVertex();
			final Graph.Vertex<Integer> v = e.getToVertex();

			// jangan khawati dengan konektornya
			if(u.equals(connector) || v.equals(connector)){
				continue;
			}

			// sesuikan nilainya
			final int uCost = costs.get(u).getCost();
			final int vCost = costs.get(v).getCost();
			final int newWeight = weight + uCost - vCost;
			e.setCost(newWeight);
		}

		//akhirnya, konektor di hapus/remove, dan Dijkstra algorithm bisa digunakan untuk mencari jarak terpendek dari setiap node (s) untuk setiap
		// other vertex di dalam perubahan ulang nilai graph
		final int indexOfConnector = graph.getVertices().indexOf(connector);
		graph.getVertices().remove(indexOfConnector);
		for(Graph.Edge<Integer> e : connector.getEdges()){
			final int indexOfConnectorEdge = graph.getEdges().indexOf(e);
			graph.getEdges().remove(indexOfConnectorEdge);
		}

		final Map<Graph,Vertex<Integer>, Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>> allShortestPaths = new HashMap<Graph.Vertex<Integer>, Map<Graph.Vertex<Integer>, List <Graph.Edge<Integer>>>>();
		for (Graph.Vertex<Integer> v : graph.getVertices()){
			final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> costPath = Dijkstra.getShortestPaths(graph, v);
			final Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths = new HashMap<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>();
			for (Graph.Vertex<Integer> v2 : costPaths.keySet()) {
				final Graph.CostPathPair<Integer> pair = costPaths.get(v2);
				paths.put(v2, pair.getPath());
			}
			allShortestPaths.put(v, paths);

		}
		return allShortestPaths;



	} 


}