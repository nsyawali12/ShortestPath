// Graph terdiri dari vertex dan egde, di presentasikan dalam model ini

package dijkstra.algorithm;

public class Vertex {
		final private String id;
		final private String name;

		public Vertex (String id, String name){
			this.id = id;
			this.name = name;
		}

		public String getId(){
			return id;
		}

		public String getName(){
			return name; 
		}

		//hasCode digunakan untuk menghasilkan nilai kuncu untuk struktur data
		@Override
		public int hashCode(){
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hasCode());
			return result;
		}

		@Override
		public boolean equals(object obj){
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			if (id == null) {
				if(other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}

		@Override
		public String toString(){
			return name;
		}
}

// Setiap atau sebuah edge mempunyai sumber dan tujuan

package dijkstra.algorithm;

public class Edge{
	private final String id;
	private final Vertex source;
	private final Vertex destination;
	private final int weight;

	public Edge(String id, Vertex source, Vertex destination, int weight){
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public String getId(){
		return id;
	}

	public Vertex getDestination(){
		return destination;
	}

	public Vertex source(){
		return source;
	}

	public int getWeight(){
		return weight;
	}

	//jika ingin merepresentasikan object menjadi string, toString() method gunakan itu.
	@Override
	public String toString(){
		return source + "" + destination;
	}
	
}