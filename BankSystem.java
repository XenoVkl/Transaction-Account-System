import java.io.*;
import java.util.Scanner; 

public class BankSystem {
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter absolute path of the dataSet");
		String firstDataset = reader.next();
		if (firstDataset.equals("default")) //or enter the default
			firstDataset = "C:\\Users\\Xenofon\\Desktop\\datasets-prj1\\dataset.4000.txt";  //change it to the absolute path that your file is stored
		String line = null;
		String [] tokens;
		int firstPosition, secondPosition, currentVertices;
		float amount;
		Vertex v1 = null, v2 = null;
		VertexInfo[] VerticesArray;
		DirectedGraph G = new DirectedGraph(5000); //Create an empty Graph(takes the maximum number of vertices as an argument - change this for extremely large datasets(e.g >100.000)
		HashBucket[] hashtable = new HashBucket[99]; //Create a hashtable
		for (int i=0; i<99; i++)
			hashtable[i] = new HashBucket(); //Initialize each bucket
		
		//Read from Dataset
		try
		{
			FileReader fd = new FileReader(firstDataset);
			BufferedReader br = new BufferedReader(fd);
			while ((line = br.readLine()) != null)
			{
				//System.out.println(line);
				tokens = line.split("\\s");
				switch(tokens[0])
				{
					case "createnodes":
						for (int i=1; i<tokens.length; i++) //Create Bank Accounts with unique IDs
						{
							v1 = G.addBankAccount(Integer.parseInt(tokens[i]));
							firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[i]));
							hashtable[firstPosition].addChainNode(v1);
							System.out.println("Successfully created Bank Account " + v1.get_ID());
						}
						break;
					case "delnodes":
						for (int i=1; i<tokens.length; i++)
						{
							firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[i]));
							hashtable[firstPosition].deleteChainNode(Integer.parseInt(tokens[i]));
							G.deleteVertex(Integer.parseInt(tokens[i]));
						}
						
						break;
					case "addtran":
						firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[1]));
						v1 = hashtable[firstPosition].getAccount(Integer.parseInt(tokens[1]));
						if (v1 == null)
						{
							System.out.println("aaaaaFailure : Bank Account " + Integer.parseInt(tokens[1]) + " does not exist");
							break;
						}
						secondPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[2]));
						v2 = hashtable[secondPosition].getAccount(Integer.parseInt(tokens[2]));
						if (v2 == null)
						{
							System.out.println("Failure : Bank Account " + Integer.parseInt(tokens[2]) + " does not exist");
							break;
						}
						amount = Float.parseFloat(tokens[3]);
						hashtable[firstPosition].addTransaction(v1, v2, amount);
						break;
					case "deltran":
						firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[1]));
						v1 = hashtable[firstPosition].getAccount(Integer.parseInt(tokens[1]));
						if (v1 == null)
						{
							System.out.println("Failure : Bank Account " + Integer.parseInt(tokens[1]) + " does not exist");
							break;
						}
						secondPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[2]));
						v2 = hashtable[secondPosition].getAccount(Integer.parseInt(tokens[2]));
						if (v2 == null)
						{
							System.out.println("Failure : Bank Account " + Integer.parseInt(tokens[2]) + " does not exist");
							break;
						}
						hashtable[firstPosition].deleteTransaction(v1, v2);
						break;
						
					case "lookup":
						firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[2]));
						v1 = hashtable[firstPosition].getAccount(Integer.parseInt(tokens[2]));
						if (v1 == null)
						{
							System.out.println("Failure : Bank Account " + Integer.parseInt(tokens[2]) + " does not exist");
							break;
						}
						if (tokens[1].equals("in"))
						{
							amount = lookUpIn(hashtable, Integer.parseInt(tokens[2]));
							System.out.println("Success : Amount of incoming transactions for Bank Account " + Integer.parseInt(tokens[2]) + " = " + amount);
						}
						else if (tokens[1].equals("out"))
						{
							firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[2]));
							amount = hashtable[firstPosition].lookupOut(Integer.parseInt(tokens[2]));
							System.out.println("Success : Amount of outgoing transactions for Bank Account " + Integer.parseInt(tokens[2]) + " = " + amount);
						}
						else if (tokens[1].equals("sum"))
						{
							amount = lookupSum(hashtable, Integer.parseInt(tokens[2]));
							System.out.println("Success : Sum of incoming & outgoing transactions for Bank Account " + Integer.parseInt(tokens[2]) + " = " + amount);
						}
							
						break;
					case "triangle":
						firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[1]));
						v1 = hashtable[firstPosition].getAccount(Integer.parseInt(tokens[1]));
						if (v1 == null)
						{
							System.out.println("Bank Account " + Integer.parseInt(tokens[1]) + " does not exist");
							break;
						}
						if (G.triangle(v1, Integer.parseInt(tokens[1]), Float.parseFloat(tokens[2]), 3) == 0)
							System.out.println("Success : Bank account with ID " + Integer.parseInt(tokens[1]) + " is involved in a triangle(circular transactions)");
						else
							System.out.println("Success : Bank account with ID " + Integer.parseInt(tokens[1]) + " is NOT involved in a triangle(circular transactions)");
						
						break;
					case "conn":
						currentVertices = G.GetNumberOfVertices();
						VerticesArray = new VertexInfo[currentVertices];
						for (int i=0; i<currentVertices; i++)
						{
							VerticesArray[i] = new VertexInfo();
						}
						G.copyVertices(VerticesArray);
						firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[1]));
						v1 = hashtable[firstPosition].getAccount(Integer.parseInt(tokens[1]));
						if (v1 == null)
						{
							System.out.println("Failure : Bank Account " + Integer.parseInt(tokens[1]) + " does not exist");
							break;
						}
						secondPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[2]));
						v2 = hashtable[secondPosition].getAccount(Integer.parseInt(tokens[2]));
						if (v2 == null)
						{
							System.out.println("Failure : Bank Account " + Integer.parseInt(tokens[2]) + " does not exist");
							break;
						}
						G.findConnectionBetweenVertices(v1,v2, VerticesArray, 1);
						break;
					case "allcycles":
						currentVertices = G.GetNumberOfVertices();
						VerticesArray = new VertexInfo[currentVertices];
						for (int i=0; i<currentVertices; i++)
						{
							VerticesArray[i] = new VertexInfo();
						}
						firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[1]));
						v1 = hashtable[firstPosition].getAccount(Integer.parseInt(tokens[1]));
						if (v1 == null)
						{
							System.out.println("Failure : Bank Account " + Integer.parseInt(tokens[1]) + " does not exist");
							break;
						}
						G.allCycles(v1, VerticesArray, Integer.parseInt(tokens[1]));
						break;
					case "traceflow":
						currentVertices = G.GetNumberOfVertices();
						VerticesArray = new VertexInfo[currentVertices];
						for (int i=0; i<currentVertices; i++)
						{
							VerticesArray[i] = new VertexInfo();
						}
						firstPosition = HashFunction(hashtable.length, Integer.parseInt(tokens[1]));
						v1 = hashtable[firstPosition].getAccount(Integer.parseInt(tokens[1]));
						if (v1 == null)
						{
							System.out.println("Failure : Bank Account " + Integer.parseInt(tokens[1]) + " does not exist");
							break;
						}
						G.traceflow(v1, Integer.parseInt(tokens[2]), VerticesArray);
						break; 
					case "print":
						G.printGraph();
						break;
					 default :
						break;
					
				}
			}
			br.close();
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("BAD FILE");
		}
		catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + firstDataset + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		reader.close();
	}
	
	public static float lookUpIn(HashBucket[] hashtable, int ID)
	{
		float totalAmount = 0.0f;
		for (int i=0; i<hashtable.length; i++)
		{
			totalAmount += hashtable[i].lookupIn(ID);
		}
		return totalAmount;
	}
	
	public static float lookupSum(HashBucket[] hashtable, int ID)
	{
		float totalAmount = 0.0f;
		int position = HashFunction(hashtable.length, ID);
		for (int i=0; i<hashtable.length; i++)
		{
			totalAmount += hashtable[i].lookupIn(ID);
		}
		totalAmount += hashtable[position].lookupOut(ID);
		return totalAmount;
	}
	
	public static int HashFunction(int size, int ID)
	{
		return ID%size;
	}
}
