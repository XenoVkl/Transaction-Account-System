
public class ListOfEdges {
	//Fields
	private Edge head;
	private Edge tail;
	
	//Methods
	
	ListOfEdges()
	{
		head = null;
		tail = null;
	}
	
	public Edge getHead()
	{
		return head;
	}
	
	public Edge getTail()
	{
		return tail;
	}
	
	public void addAmount(float amount, Vertex v)
	{
		boolean hasEdge = false;
		Edge current = head;
		while (current != null)
		{
			if (current.target == v) //There is already a transaction(edge) so just add the amount
			{
				current.addAmount(amount);
				hasEdge = true;
			}
			current = current.NextEdge;
		}
		if (hasEdge == false) //There hasn't been a transaction before so we'll create a new edge to the vertex v
		{
			Edge node = new Edge(amount, v);
			if (head == null) //empty overflow list
			{
				head = node;
				tail = node;
			}
			else
			{
				tail.NextEdge = node;
				tail = node;
			}
		}
	}
	
	public void deleteEdge(Vertex v)
	{
		Edge current = head;
		if (head == null)
			return;
		if (head.target == v)
		{
			head = current.NextEdge;
			current = null;
			return;
		}
		else
		{
			current = head.NextEdge;
			Edge previous = head;
			while (current != null)
			{
				if (current.target == v )
				{
					previous.NextEdge = current.NextEdge;
					current = null;
					return;
				}
				current = current.NextEdge;
				previous = previous.NextEdge;
			}
		}
	}
	
	public float getTotalAmount()
	{
		float totalAmount = 0.0f;
		Edge current = head;
		while (current != null)
		{
			totalAmount += current.getAmount();
			current = current.NextEdge;
		}
		return totalAmount;
	}
	
	public float getSpecificTransaction(int ID)
	{
		float totalAmount = 0.0f;
		Edge current = head;
		while (current != null)
		{
			if (current.target.get_ID() == ID)
			{
				totalAmount += current.getAmount();
			}
			current = current.NextEdge;
		}
		return totalAmount;
	}
}
