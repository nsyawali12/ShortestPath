package algorithm.graph;

import java.util.ArrayList;
import java.util.Hashmap;
import java.util.List;
import java.util.Map;

import algorithm.dataStructures.Graph;

//Bellman-ford shortest path. bekerja pada negative dan positif weight
//edges. juga mendeteksi negative weight cycles, mengembalikan total cost shortest path dan jalannya
//references : https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm
// https://github.com/phishman3579

public class BellmanFord {

	private BellmanFord(){}

	//mendapat shortest path dari semua vertices

	public static Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> getShortestPaths(Graph<Integer> graph, Graph.Vertex<Integer> start) {
        final Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths = new HashMap<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>();
        final Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs = new HashMap<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>>();

        getShortestPath(graph, start, paths, costs);

        final Map<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>> map = new HashMap<Graph.Vertex<Integer>, Graph.CostPathPair<Integer>>();
        for (Graph.CostVertexPair<Integer> pair : costs.values()) {
            final int cost = pair.getCost();
            final Graph.Vertex<Integer> vertex = pair.getVertex();
            final List<Graph.Edge<Integer>> path = paths.get(vertex);
            map.put(vertex, new Graph.CostPathPair<Integer>(cost, path));
        }
        return map;
    }

    // Mendapat shortest path dari mulai hingga berakhir di vertices

    public static Graph.CostPathPair<Integer> getShortestPath(Graph<Integer> graph, Graph.Vertex<Integer> start, Graph.Vertex<Integer> end) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));

        final Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths = new HashMap<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>>();
        final Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs = new HashMap<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>>();
        return getShortestPath(graph, start, end, paths, costs);
    }

    private static Graph.CostPathPair<Integer> getShortestPath(Graph<Integer> graph, 
                                                               Graph.Vertex<Integer> start, Graph.Vertex<Integer> end,
                                                               Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths,
                                                               Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs) {
        if (end == null)
            throw (new NullPointerException("end must be non-NULL."));

        getShortestPath(graph, start, paths, costs);

        final Graph.CostVertexPair<Integer> pair = costs.get(end);
        final List<Graph.Edge<Integer>> list = paths.get(end);
        return (new Graph.CostPathPair<Integer>(pair.getCost(), list));
    }

    private static void getShortestPath(Graph<Integer> graph,
    									Graph.Vertex<Integer> start,
    									Map<Graph.Vertex<Integer>, List<Graph.Edge<Integer>>> paths, 
    									Map<Graph.Vertex<Integer>, Graph.CostVertexPair<Integer>> costs) {
    	if (graph == null)
    		throw (new NullPointerException("Graph jangan sampai kosong"));
    	if (start == null)
    		throw (new NullPointerException("Start jangan sampai kosong"));

    	for (Graph.Vertex<Integer> v : graph.getVertices())
    		paths.put(v, new ArrayList<Graph.Edge<Integer>>());

    	// semua vertices infinit kecuali vertices awal/mulai

    	for (Graph.Vertex<Integer> v : graph.getVertices())
            if (v.equals(start))
                costs.put(v, new Graph.CostVertexPair<Integer>(0, v));
            else
                costs.put(v, new Graph.CostVertexPair<Integer>(Integer.MAX_VALUE, v));

        boolean negativeCycleCheck = false;
        for (int i = 0; i < graph.getVertices().size(); i++){
        	//jika ini adalah vertices terakhir, perform negative weight cycles cek/
        	//grapnya seharusnya finish dengan size ()-1 waktu tempuh this loop.
        	if (i == (graph.getVertices().size()-1))
        		negativeCycleCheck = true;

        	// mengkalkulasi costs ke semua vertices
        	for (Graph.Edge<Integer> e : graph.getEdges()){
        		final Graph.CostVertexPair<Integer> pair = costs.get(e.getTpVertex());
        		final Graph.CostVertexPair<Integer> lowestCostToThisVertex = costs.get(e.getFromVertex());

        		// jika cost dari vertex adalah MAX maka perlakukan sebagain INFINIT.
        		if(lowestCostToThisVertex.getCost() == Integer.MAX_VALUE)
        			continue;

        		final int cost = lowestCostToThisVertex.getCost() + e.getCost();
        		if (cost < pair.getCost()) {
        			// menemukan sebuah shorter path untuk di jangkau vertex
        			pair.setCost(cost);

        			if (negativeCycleCheck){
        				//negative weight cycle
        				throw new IllegalArgumentException("Graph ini mengandung konten negative weight cycle.");

        			}

        			final List<Graph.Edge<Integer>> list = paths.get(e.getToVertex());
        			list.clear();
        			list.addAll(paths.get(e.getFromVertex()));
        			list.add(e);
        		}

        	}	

        }

    }

}