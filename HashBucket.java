
public class HashBucket {
	//Fields
	private OverflowList ChainList;
	
	//Methods
	HashBucket()
	{
		//System.out.println("Created a hash bucket");
		ChainList = new OverflowList();
	}
	
	public void addChainNode(Vertex v) //extends the overflow bucket-list and returns a reference to a specific bank account(vertex of a graph)
	{
		ChainList.addChainNode(v);
	}
	
	public void deleteChainNode(int ID)
	{
		ChainList.deleteChainNode(ID);
	}
	
	public Vertex getAccount(int ID)
	{
		return ChainList.getAccount(ID);
	}
	
	public void addTransaction(Vertex v1, Vertex v2, float amount)
	{
		ChainList.addTransaction(v1, v2, amount);
	}
	
	public void deleteTransaction(Vertex v1, Vertex v2)
	{
		ChainList.deleteTransaction(v1, v2);
	}
	
	public float lookupOut(int ID) //returns the total amount that this bank account(vertex) sends to other bank accounts(vertices) - basically the total weight of all the outgoing edges
	{
		return ChainList.lookupOut(ID);
	}
	
	public float lookupIn(int ID)
	{
		return ChainList.lookupIn(ID);
	}
	
}
