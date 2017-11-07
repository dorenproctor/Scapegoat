//Author: Doren Proctor
//Created for CS450 - Design and Analysis of Algorithms
//Due Nov 6, 2017

class Scapegoat {
	private Node root;
	private int n;
	private double alpha;

	public Scapegoat(double a, int val) { // constructor
		alpha = a;
		root = new Node(val);
		n = 1;
	}


	public boolean insert(int val) {
		Node newNode = new Node(val);
		int depth = addWithDepth(newNode);
 		// if too deep then find highest scapegoat in tree
		if (depth > Math.floor(Math.log(n) / Math.log(1/alpha))) {
			Node scapegoat = newNode;
			Node node = newNode;
			while (node.parent != null) {
				node = node.parent;
				if (size(scapegoat.left) > alpha*size(scapegoat.right) ||
					size(scapegoat.right) > alpha*size(scapegoat.left)) {
					scapegoat = node;
				}
			}
			rebuild(scapegoat);
		}
		return (depth != -1);
	}


	public boolean isEmpty() {
		return (root == null);
	}


	public void clearTree() {
		root = null;
		n = 0;
	}


	public boolean search(int val) {
		Node node = root;

		while (node != null) {
			if (val == node.value)
				return true;
			else if (val < node.value)
				node = node.left; 
			else
				node = node.right;
		}
		return false;
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
