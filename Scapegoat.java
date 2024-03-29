//Author: Doren Proctor
//Created for CS450 - Design and Analysis of Algorithms
//Due Nov 6, 2017

class Scapegoat {
	public int debug = 0;

	private Node root;
	private int n, maxN;
	private double alpha, baseAlpha;

	public Scapegoat(double a, int val) { // constructor
		alpha = a;
		baseAlpha = Math.log(1/alpha); // no need to calculate on every insert/delete
		root = new Node(val);
		n = 1;
		maxN = 1;
		if (debug>0) System.out.println("Creating tree with alpha: "+a+" and root: "+val+"\n");
	}


	public boolean insert(int val) {
		Node newNode = new Node(val);
		int depth = addWithDepth(newNode);
 		double maxDepth = Math.floor(Math.log(n) / baseAlpha); // log base alpha of n
 		if (debug>0) System.out.println("Inserting "+val);
 		if (debug>1) System.out.println("depth: "+depth+"  MaxDepth: "+maxDepth);

		if (depth > maxDepth) {
			Node scapegoat = getScapegoat(newNode);
			rebuild(scapegoat);
			
		}
		maxN = Math.max(n, maxN); // for delete
		if (debug>0) System.out.println("");
		return (depth != -1);
	}


	public boolean delete(int val) {
		if (debug>0) System.out.println("Deleting "+val);
		Node node = search(val);
		if (node == null) {
			if (debug>0) System.out.println("Did not find "+val+". Can't delete\n");
			return false;
		}

		if (node.left == null)
			transplant(node, node.right);
		else if (node.right == null)
			transplant(node, node.left);
		else {
			Node successorNode = node;
			while (successorNode.left != null)
				successorNode = successorNode.left;
			if (successorNode.parent != node) {
				transplant(successorNode, successorNode.right);
				successorNode.right = node.right;
				successorNode.right.parent = successorNode;
			}
			transplant(node, successorNode);
			successorNode.left = node.left;
			successorNode.left.parent = successorNode;
		}

		n--;
		if (n <= alpha*maxN) {
			Node scapegoat = getScapegoat(node);
			rebuild(scapegoat);
			maxN = n;
		}

		if (debug>0) System.out.println("");
		return true;
	}


	public boolean isEmpty() {
		return (root == null);
	}


	public void clearTree() {
		root = null;
		n = 0;
	}


	public Node search(int val) {
		Node node = root;

		while (node != null) {
			if (val == node.value)
				return node;
			else if (val < node.value)
				node = node.left; 
			else
				node = node.right;
		}
		return null;
	}


	public int getSize() {
		return n;
	}


	public Node getRoot() {
		return root;
	}


	private int size(Node node) {
		if (node == null)
			return 0;

		return 1 + size(node.left) + size(node.right);
	}


	private Node getScapegoat(Node node) {
		if (debug>0) System.out.println("Finding scapegoat");
		Node scapegoat = node;
		Node tmp = node;
		while (tmp.parent != null) {
			tmp = tmp.parent;
			if (size(tmp.left) > alpha*size(tmp.right) ||
				size(tmp.right) > alpha*size(tmp.left)) {
				scapegoat = tmp;
				if (debug>2) System.out.println(scapegoat.value+" is a viable scapegoat");
			}
		}
		if (debug>0) System.out.println("Scapegoat is "+scapegoat.value);
		return scapegoat;
	}


	private void transplant(Node oldNode, Node newNode) {
		if (oldNode.parent == null)
			root = newNode;
		else if (oldNode == oldNode.parent.left)
			oldNode.parent.left = newNode;
		else
			oldNode.parent.right = newNode;
		if (newNode != null)
			newNode.parent = oldNode.parent;
	}


	private int addWithDepth(Node newNode) {
		Node node = root;
		if (node == null) {
			root = node;
			n++;
			return 0;
		}
		int depth = 0;

		while (true) {
			if (newNode.value < node.value) {
				if (node.left == null) {
					node.left = newNode;
					newNode.parent = node;
					break;
				}
				else
					node = node.left;
			}
			else if (newNode.value > node.value) {
				if (node.right == null) {
					node.right = newNode;
					newNode.parent = node;
					break;
				}
				else
					node = node.right;
			}
			else {
				System.out.println("A node with value "+newNode.value+"already exists. Not inserting.\n");
				return -1;
			}
			depth++;
		}

		n++;
		return depth;
	}


	private void rebuild(Node node) {
		int nodeSize = size(node);
		Node parent = node.parent;
		Node[] nodeArray = new Node[nodeSize];
		fillArray(node, nodeArray, 0);
		if (parent == null) {
			root = rebalance(nodeArray, 0, nodeSize);
			root.parent = null;
		}
		else if (parent.left == node) {
			parent.left = rebalance(nodeArray, 0, nodeSize);
			parent.left.parent = parent;
		}
		else {
			parent.right = rebalance(nodeArray, 0, nodeSize);
			parent.right.parent = parent;
		}
		if (node == root) { // if entire tree is rebalanced, maxN become N. For delete
			maxN = n;
		}
	}

	private int fillArray(Node node, Node[] nodeArray, int i) {
		if (node == null)
			return i;

		i = fillArray(node.left, nodeArray, i);
		nodeArray[i++] = node;
		return fillArray(node.right, nodeArray, i);
	}


	private Node rebalance(Node[] nodeArray, int i, int nodeSize) {
		if (nodeSize == 0)
			return null;

		int j = nodeSize / 2;
		int ij = i+j;

		nodeArray[ij].left = rebalance(nodeArray, i, j);
		if (nodeArray[ij].left != null)
			nodeArray[ij].left.parent = nodeArray[ij];

		nodeArray[ij].right = rebalance(nodeArray, ij+1, nodeSize-j-1);
		if (nodeArray[ij].right != null)
			nodeArray[ij].right.parent = nodeArray[ij];

		return nodeArray[ij];
	}
}
