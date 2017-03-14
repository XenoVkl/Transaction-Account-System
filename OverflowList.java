
public class OverflowList {
	private OverflowNode head;
	private OverflowNode tail;
	//Methods
	OverflowList()
	{
		head = null;
		tail = null;
		//System.out.println("Created an empty overflow list");
	}
	
	public void addChainNode(Vertex v) //Adds a node at the end of the list
	{
		OverflowNode node = new OverflowNode(v);
		if (head == null) //empty overflow list
		{
			head = node;
			tail = node;
		}
		else if (head.next == null)
		{
			head.next = node;
			tail = node;
		}
		else
		{
			tail.next = node;
			tail = node;
		}
	}
	
	public void deleteChainNode(int ID) //Delete a node from the overflow hashBucket list : returns 1 for success , -1 for failure
	{
		OverflowNode current = head;
		if (head == null)
			return;
		if (head.target.get_ID() == ID)
		{
			if (head.target.getIncomingTransactions() != 0) //If there are any transactions(incoming edges) on this account , deletion is prohibited
			{
				System.out.println("Failure : Bank Account cannot be deleted due to incoming transactions");
				return;
			}
			else if (head.target.Edges == null) //outgoing edges
			{
				System.out.println("Failure : Bank Account cannot be deleted due to outgoing transactions");
				return;
			}
			else //Deletion can be applied
			{
				head = current.next;
				current = null;
				System.out.println("Success : Bank Account with ID " + ID + " was successfully deleted");
				return;
			}
		}
		else
		{
			current = head.next;
			OverflowNode previous = head;
			while (current != null)
			{
				if (current.target.get_ID() == ID )
				{
					previous.next = current.next;
					current = null;
					System.out.println("Success : Bank Account with ID " + ID + " was successfully deleted");
					return;
				}
				current = current.next;
				previous = previous.next;
			}
			System.out.println("Failed to delete Bank Account " + ID + " (Does not exist)");
			return;
		}
	}
	
	public Vertex getAccount(int ID)
	{
		OverflowNode current = head;
		Vertex v = null;
		while (current != null)
		{
			if (current.target.get_ID() == ID) //get the bank account that will send an amount
			{
				v = current.target;
				return v;
			}
			current = current.next;
		}
		return v;
	}
	
	public void addTransaction(Vertex v1, Vertex v2, float amount)
	{
		v1.Edges.addAmount(amount, v2);
		System.out.println("Success : " + amount + " were transferred from Bank Account " + v1.get_ID() + " to Bank Account " + v2.get_ID());
		
	}
	
	public void deleteTransaction(Vertex v1, Vertex v2)
	{
		v1.Edges.deleteEdge(v2);
	}
	
	public float lookupOut(int ID)
	{
		float amount = 0.0f;
		OverflowNode current = head;
		while (current != null)
		{
			if (current.target.get_ID() == ID)
			{
				amount = current.target.Edges.getTotalAmount();
				return amount;
			}
			current = current.next;
		}
		return amount;
	}
	
	public float lookupIn(int ID)
	{
		float amount =0.0f;
		OverflowNode current = head;
		while (current != null)
		{
			amount += current.target.Edges.getSpecificTransaction(ID);
			current = current.next;
		}
		return amount;
	}
	
	
}
