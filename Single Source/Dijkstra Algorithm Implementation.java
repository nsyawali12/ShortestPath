// Implementation Dijkstra Algorithm
package dijkstra.algorithm.Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dijkstra.algorithm.Edge;
import dijkstra.algorithm.Graph;
import dijkstra.algorithm.Vertex;

public class dijkstraAlgorithm {
	private final List<Vertex> nodes;
	private final List<Edge> edges;
	private Set<Vertex> settledNodes;
	private Set<Vertex> unSettledNodes;
	private Map<Vertex, Vertex> predecessors; //anggap seperti orang yang bekerjanya atau berpergian
	private Map<Vertex, Integer> distance;

	public dijkstraAlgorithm(Graph graph){
		// copy array supaya dapat berjalan
		this.node = new ArrayList<Vertex>(graph.getVertexes());
		this.edges = new ArrayList<Edge>(graph.getEdges());
	}

	public void execute(Vertex source){
		settledNodes = new HashSet<Vertex>();
		unSettledNodes = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Integer>();
		predecessors = new HashMap<Vertex, Vertex>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while(unSettledNodes.size() > 0){
			Vertex node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}

	}

	private void findMinimalDistances(Vertex node){
		List<Vertex> adjacentNodes = getNeighbors(node);
		for(Vertex target : adjacentNodes) {
			if(getShortestDistances(target) > getShortestDistances(node)
				+ getDistance(node, target)) {
				distance.put(target, getShortestDistances(node)
					+ getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}
	}

	private int getDistance(Vertex node, Vertex target){
		for (Edge edge : edges){
			if(edge.getSource().equals(node)
					&& edge.getDestinantion().equals(target)){
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Tidak bisa terjadi");
	}

	private List<Vertex> getNeighbors(Vertex node){
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for( Edge edge : edges){
			if(edge.getSource().equals(node)
				&& !isSettled(edge.getDestinantion())){
				neighbors.add(edge.getDestinantion));
			}
		}
		return neighbors;
	}

	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for(Vertex vertex : vertexes){
			if(minimum == null){
				minimum = vertex;
			} else {
				if(getShortestDistance(vertex) < getShortestDistance(minimum)){
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Vertex vertex){
		return settledNodes.contains(vertex);
	}

	private int getShortestDistance(Vertex destination){
		Integer d = distance.get(destination);
		if (d == null){
			return integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	Method ini mengembalikan jalan(path) dari sumber ke target
	yang dipilih dan mengembalikan nilai null jika
	tidak ada jalan yang tersedia
	
	*/

	public LinkedList<Vertex> getPath(Vertex target){
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex step = target;
		// check if a path exists
		if(predecessors.get(step) == null){
			return null;
		}
		path.add(step);
		while(predecessors.get(step) != null){
			step = predecessors.get(step);
			path.add(step);
		}
		//put it into the correct order
		Collections.reverse(path);
		return path;
	}


}