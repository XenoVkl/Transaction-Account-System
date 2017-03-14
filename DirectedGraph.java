
public class DirectedGraph {
	private int maxNumberOfBankAccounts;
	private Vertex[] BankAccounts;
	private boolean HasTraceflow;
	private boolean InvolvedInCycle;
	
	//Methods
	DirectedGraph(int m)
	{
		maxNumberOfBankAccounts = m; 
		BankAccounts = new Vertex[maxNumberOfBankAccounts]; //We'll have a maximum number of vertices(bank accounts) in the graph 
	}
	
	public int GetNumberOfVertices()
	{
		int num=0;
		for (int i=0; i<maxNumberOfBankAccounts; i++)
		{
			if (BankAccounts[i] != null)
				num++;
		}
		return num;
	}
	
	public void copyVertices(VertexInfo[] array)
	{
		int k=0;
		for (int i=0; i<maxNumberOfBankAccounts; i++)
		{
			if (BankAccounts[i] != null) //Check if the BankAccount already exists
			{
				array[k].setID(BankAccounts[i].get_ID());
				k++;
			}
		}
	}
	
	public void printGraph()
	{
		System.out.println("Printing Graph");
		for (int i=0; i<maxNumberOfBankAccounts; i++)
		{
			if (BankAccounts[i] != null)
			{
				ListOfEdges ConnectedVertices = BankAccounts[i].Edges;
				Edge currentNeighbor = ConnectedVertices.getHead();
				if (currentNeighbor != null)
					System.out.print("|" + BankAccounts[i].get_ID() + "| points to ");
				else
					System.out.println("|" + BankAccounts[i].get_ID() + "|");
				while (currentNeighbor != null)
				{
					if (currentNeighbor.NextEdge != null)
						System.out.print("|" + currentNeighbor.target.get_ID() + "| and ");
					else
						System.out.println("|" + currentNeighbor.target.get_ID() + "|");
					currentNeighbor = currentNeighbor.NextEdge;
				}
			}
		}
	}
	
	public Vertex addBankAccount(int ID) //Inserts a bank account(vertex) to the graph that holds a unique ID and return that Vertex
	{
		for (int i=0; i<maxNumberOfBankAccounts; i++)
		{
			if (BankAccounts[i] != null && BankAccounts[i].get_ID() == ID) //Check if the BankAccount already exists
			{
				System.out.println("Failed to create Bank Account with ID " + ID + "(Already exists)");
				return null;
			}
		}
		for (int i=0; i<maxNumberOfBankAccounts; i++)
		{
			if (BankAccounts[i] == null) 
			{
				BankAccounts[i] = new Vertex(); //Create new BankAccount
				BankAccounts[i].set_ID(ID);
				return BankAccounts[i];//.getAccount(); //Return the new BankAccount
			}
		}
		System.out.println("Failed to create Bank Account with ID " + ID);
		return null;
		
	}
	
	public void deleteVertex(int ID)
	{
		for (int i=0; i<maxNumberOfBankAccounts; i++)
		{
			if (BankAccounts[i] != null && BankAccounts[i].get_ID() == ID)
				BankAccounts[i] = null;
		}
	}
	
	public int triangle(Vertex v, int ID, float amount, int times)
	{
		if (times < 1)
			return -1;
		ListOfEdges ConnectedVertices = v.Edges;
		Edge current = ConnectedVertices.getHead();
		while (current != null)
		{
			if (current.getAmount() >= amount && times == 1 && current.target.get_ID() == ID)
			{
				//System.out.println("BankAccount with ID " + ID + " is involved in a triangle(circular transactions)");
				return 0;
			}
			if (current.getAmount() < amount)
			{
				current = current.NextEdge;
				continue;
			}
			if (current.getAmount() >= amount && times > 1)
			{
				if (triangle(current.target, ID, amount, times-1) == 0)
					return 0;
			}
			current = current.NextEdge;
		}
		return -1;	
	}
	
	public boolean IsVisited(VertexInfo[] array, int ID)
	{
		for (int i=0; i<array.length; i++)
		{
			if ((array[i].getID() == ID) && array[i].IsMarked() == false)
			{
				return false;
			}
		}
		return true;
	}
	
	public void MarkVertex(VertexInfo[] array, int ID, int queueNum)
	{
		for (int i=0; i<array.length; i++)
		{
			if (array[i].getID() == ID)
			{
				array[i].markVertex(true);
				array[i].IncludeVertex(queueNum);
			}
		}
	}
	
	public void connection(Vertex v1, Vertex v2, VertexInfo[] array, int queueNum)
	{
		MarkVertex(array, v1.get_ID(), queueNum);
		ListOfEdges ConnectedVertices = v1.Edges;
		Edge current = ConnectedVertices.getHead();
		while (current != null)
		{
			if (IsVisited(array, current.target.get_ID()) == false)
				connection(current.target , v2, array, queueNum + 1);
			current = current.NextEdge;
		}
	}
	
	public void findConnectionBetweenVertices(Vertex v1, Vertex v2, VertexInfo[] array, int queueNum)
	{
		connection(v1, v2, array, queueNum);
		BubbleSort(array);
		for (int i=0; i<array.length; i++)
		{
			if (array[i].getID() == v2.get_ID() && array[i].GetQueueNum() == 0)
			{
				System.out.println("Success : There is no connection between BankAccounts(vertices) " + v1.get_ID() + " and " + v2.get_ID());
				return;
			}
				
		}
		System.out.print("Success - Connection found(Path from " + v1.get_ID() + " to " + v2.get_ID() + ") : ");
		for (int k=0; k<array.length; k++) 
		{
			if (array[k].GetQueueNum() != 0)
			{
				if (array[k].getID() == v2.get_ID())
				{
					System.out.println(v2.get_ID());
					return;
				}
				else if (array[k].getID() != v2.get_ID())
				{
					if (k == array.length - 1)
					{
						if (array[k].GetQueueNum() == array[k-1].GetQueueNum())
							System.out.print(array[k].getID() + " ->" );
					}
					else
					{
						if (array[k].GetQueueNum() != array[k+1].GetQueueNum())
							System.out.print(array[k].getID() + " ->" );
					}	
				}
			}
		}
	}
	
	public int IsIncluded(int ID, VertexInfo[] array)
	{
		for (int i=0; i<array.length; i++)
		{
			if (ID == array[i].getID())
				return 1;
		}
		return 0;
	}
	
	public void findTraceflow(Vertex v, int currentDepth, int maxDepth, VertexInfo[] array)
	{
		array[currentDepth].setID(v.get_ID());
		ListOfEdges ConnectedVertices = v.Edges;
		Edge current = ConnectedVertices.getHead();
		if (currentDepth == maxDepth)
			return;
		else if (currentDepth < maxDepth)
		{
			if (currentDepth == maxDepth-1) // print Path
			{
				HasTraceflow = true;
				System.out.print("Success - Traceflow detected : ");
				for (int i=0; i<maxDepth; i++)
				{
					if (i == 0)
						System.out.print("(" + array[i].getID() + ", ");
					else if (i == maxDepth - 1)
						System.out.println(array[i].getID() + ")");
					else
					{
						System.out.print(array[i].getID() + ", ");
					}
				}
				return;
			}
			while (current != null)
			{
				if (IsIncluded(current.target.get_ID(), array) == 0) //Is not visited
				{
					findTraceflow(current.target, currentDepth+1, maxDepth, array);
					current = current.NextEdge;
				}
				else
					current = current.NextEdge;
			}
			
		}
	}
	
	public void traceflow(Vertex v, int depth, VertexInfo[] array)
	{
		HasTraceflow = false;
		for (int i=0; i<array.length; i++)
			array[i].setID(0);
		findTraceflow(v, 0, depth+1, array);
		if (HasTraceflow == false)
			System.out.println("Success : No traceflows detected for Bank Account " + v.get_ID());
	}
	
	public void findAllCycles(Vertex v, int currentDepth, VertexInfo[] array, int ID)
	{
		array[currentDepth].setID(v.get_ID());
		ListOfEdges ConnectedVertices = v.Edges;
		Edge current = ConnectedVertices.getHead();
		if (currentDepth >= 3 && v.get_ID() == ID)
		{
			System.out.print("Success - Cycle Detected : ");
			InvolvedInCycle = true;
			for (int i=0; i<currentDepth; i++)
			{
				if (i == 0)
					System.out.print("(" + array[i].getID() + ", ");
				else if (i == currentDepth - 1)
					System.out.println(array[i].getID() + ")");
				else
				{
					System.out.print(array[i].getID() + ", ");
				}
			}
			return;
		}
		while (current != null)
		{
			if (IsIncluded(current.target.get_ID(), array) == 0 || (current.target.get_ID() == ID && currentDepth >= 2))
			{
				findAllCycles(current.target, currentDepth + 1, array, ID);
				current = current.NextEdge;
			}
			else
				current = current.NextEdge;
		}
		
	}
	
	public void allCycles(Vertex v, VertexInfo[] array, int ID)
	{
		InvolvedInCycle = false;
		for (int i=0; i<array.length; i++)
			array[i].setID(0);
		findAllCycles(v, 0, array, ID);
		if (InvolvedInCycle == false)
			System.out.println("Success : No cycles detected for Bank Account with ID " + ID);
	}
	
	public void BubbleSort(VertexInfo[] array)
	{
		 int size = array.length;
         VertexInfo temp = new VertexInfo();
         for(int i=0; i < size; i++)
         {
	         for(int j=1; j < (size-i); j++)
	         {
	             if(array[j-1].GetQueueNum() > array[j].GetQueueNum())
	             {
	                     //swap the elements!
	                     temp.setID(array[j-1].getID());
	                     temp.markVertex(array[j-1].IsMarked());
	                     temp.IncludeVertex(array[j-1].GetQueueNum());
	                     array[j-1].setID(array[j].getID());
	                     array[j-1].markVertex(array[j].IsMarked());
	                     array[j-1].IncludeVertex(array[j].GetQueueNum());
	                     array[j].setID(temp.getID());
	                     array[j].markVertex(temp.IsMarked());
	                     array[j].IncludeVertex(temp.GetQueueNum());
	             }
	                
	         }
         }
	}
	
	
}
