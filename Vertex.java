
public class Vertex {
	//Fields
	private int ID;
	private int incomingTransactions;
	ListOfEdges Edges;
	
	//Methods
	public void set_ID(int given_ID) //set BankAccount ID
	{
		ID = given_ID;
	}
	public int get_ID() //get BankAccount ID
	{
		return ID;
	}
	Vertex()
	{
		incomingTransactions=0;
		Edges = new ListOfEdges();
	}
	public Vertex getAccount() //return the BankAccount(graph vertex)
	{
		return this;
	}
	public void raiseIncomingTransactions()
	{
		incomingTransactions++;
	}
	public void reduceIncomingTransactions()
	{
		incomingTransactions--;
	}
	public int getIncomingTransactions()
	{
		return incomingTransactions;
	}

}
