
public class VertexInfo {
	int ID;
	boolean marked;
	int queueNum;
	
	VertexInfo()
	{
		marked = false;
		queueNum = 0;
	}
	
	public void setID(int i)
	{
		ID = i;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void markVertex(boolean x)
	{
		marked = x;
	}
	
	public void unmarkVertex()
	{
		marked = false;
	}
	
	public void IncludeVertex(int k)
	{
		queueNum = k;
	}
	
	
	public boolean IsMarked()
	{
		return marked;
	}
	
	public int GetQueueNum()
	{
		return queueNum;
	}

}
