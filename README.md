# HITS-Pagerank-Algo


// Neel Patel 

HANDOUT 2 ADHERED; NO BUGS TO REPORT

****HITS and PageRank Algorithm****

	It is a Java Implementation for Kleinberg’s HITS and Google’s PageRank Algorithm.
	
	It has two java files:
		1. hits7302.java --> It computes the authority and hub values of nodes (web pages) in a network represented here as a directed graph.
		
		2. pgrk7302.java --> It computes the pagerank values of nodes (web pages) in a network represented here as a directed graph.

****Guidelines****
 
	~Execution steps:
		unzip prp_7302.zip --> unzip the file
		
		compile the two java file
		
		java hits7302 iterations initialvalue filename

		java pgrk7302 iterations initialvalue filename

	~Iterations:
		For iteration = 0, the algorithms run until the iteration values converge with an error rate of 0.00001. That is, the iterations are computed until the difference between the current and last iteration values is less than 0.00001.
		
		For negative iteration values i.e -1, -2, -6 , etc the algorithms run until value converges with an error rate of 10 to the power of error rate.

		For non zero iteration values, all the iterations are computed and displayed.

		For graphs with nodes greater than 10, only values for the last iteration are displayed (iterations is assumed to be 0, and initialvalue = -1).

	~Initialvalue:
		This command line argument can take values in the range from -2, -1, 0, 1.

		For -2, the hub/authority or pagerank values are initialized as 1/(sqrt(n)), for all the nodes, where n is the number of nodes.

		For -1, the hub/authority or pagerank values are initialized as 1/(n), for all the nodes, where n is the number of nodes.

		For 0, the hub/authority or pagerank values are initialized as 0 for all the nodes.

		For 1, the hub/authority or pagerank values are initialized as 1 for all the nodes.

	~filename:
		This text file contains the representation of a graph.

		The first number of the first line represents the number of nodes (n) of the graph.

		The second number of the first line represents the number of edges (m) of the graph.

		Remaining line (i j) represents the edge from node i to node j in the graph.
