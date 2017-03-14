# Transaction-Account-System
Implementation of a Bank System(Accounts, transactions, balance, tracking illegal activity among accounts via graph-cycles etc)

--------------------------------------------------------------------------
Implementing a Directed Graph and a Hashtable to represent the Bank System
--------------------------------------------------------------------------
-> Directed Graph<-

-Each vertex of the directed graph represents a bank account and each edge among two vertices represents a transaction. The graph is  dynamically changing in the main memory as more and more bank accounts are being created. Every edge has a weight and it indicates the total amount of money being sent from one bank account to another.
-Methods are available for creating/deleting accounts, adding/removing transactions, finding triangles/cycles in the graph, finding paths between accounts, finding traceflows, printing the graph etc.

->Hashtable<-

-Array of buckets : each bucket is a list and extends when we have a collision, creating an overflow bucketList. Each node of that list holds a pointer to a bank account(vertex of the directed graph)
-Usage of the hashtable is to offer the ability of inserting/finding elements with a cost of O(1).

-------------------
Running the program
-------------------
The program will ask you for an operation file to read commands in order to proceed in executing. Four input files are available
in the Dataset folder and the commands that are used in those files are the above:
- createnodes N1 N2 N3 N4 ...

this command creates one bank account(N1) or more(N2, N3, N4 ...)
- delnodes N1 N2 N3 N4 ...

this command deletes one bank account(N1) or more(N2, N3, N4 ...)
- addtran N1 N2 amount

this command adds an edge(transaction) from bank account(N1) to bank account(N2) with weight/amount = amount. If the edge already existed, the amount will be added to the existing value/amount of the account
- deltran N1 N2

this command deletes an edge(transaction) from bank account(N1) to bank account(N2)
- lookup in/out/sum N

this command finds the total amount of money that is sent to account N(in)/is being sent by account N(out)/is sent AND is being sent to/by account N(sum)
- triangle N k

this command finds if a bank account N is involved in triangular transactions with other bank accounts(three-node circular relations one of which is N).However the minimum amount in each edge must be at least k euros
- conn N1 N2

this command finds if there is a path from bank account N1 to bank account N2 and prints it
- allcycles N

this command prints all cycles that bank account N is involved into
- traceflow N l

this command prints all the paths of depth l and also the weight(amount of money) of each edge
- bye

Destroy the graph
- print

prints the Directed Graph
