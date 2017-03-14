
public class Edge {
	//Fields
	Edge NextEdge;
	Vertex target;
	float amount;
	
	//Methods
	Edge(float w, Vertex v)
	{
		NextEdge = null;
		target = v;
		amount = w;
	}
	
	public void addAmount(float w)
	{
		amount += w;
	}
	
	public float getAmount()
	{
		return amount;
	}
	
}
