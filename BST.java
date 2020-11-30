class BSTNode<T> {
	public String key;
	public T data;
	public BSTNode<T> left, right;

	public BSTNode(String key, T data) {
		this.key = key;
		this.data = data;
		left = right = null;
	}
	//add to BSTNode

    @Override
    public String toString() {
	return toString("");
    }

    private String toString(String ident) {
	return ident + key + " = " + data.toString() +
		((left == null) ? "" : "\n" + left.toString(ident + '\t')) +
		((right == null) ? "" : "\n" + right.toString(ident + '\t'));
    }
}

public class BST<T> {

	private BSTNode<T> root, current;

	public BST() {
		current = root = null;
	}

	public void clear() {
		current = root = null;
	}

	public boolean empty() {
		return root == null;
	}

	public boolean full() {
		return false;
	}

	public T retrieve() {
		return current.data;
	}

	public void update(T val) {
		current.data = val;
	}

	public boolean findKey(String k) {

		BSTNode<T> p = root;
		while (p != null) {
			current = p;
			if (k.equals(p.key)) {
				return true;
			} else if (k.compareTo(p.key) < 0) {
				p = p.left;
			} else {
				p = p.right;
			}
		}
		return false;
	}

	public boolean insert(String k, T val) {
		if (root == null) {
			current = root = new BSTNode<T>(k, val);
			return true;
		}

		BSTNode<T> p = current;
		if (findKey(k)) {
			current = p;
			return false;
		}

		BSTNode<T> tmp = new BSTNode<T>(k, val);
		if (k.compareTo(p.key) < 0) {
			current.left = tmp;
		} else {
			current.right = tmp;
		}
		current = tmp;
		return true;
	}

	public boolean removeKey(String k) {
		// Search for k
		String k1 = k;
		BSTNode<T> p = root;
		BSTNode<T> q = null; // Parent of p
		while (p != null) {

			if (k1.compareTo(p.key) < 0) {
				q = p;
				p = p.left;
			} else if (k1.compareTo(p.key) > 0) {
				q = p;
				p = p.right;
			} else { // Found the key

				// Check the three cases
				if ((p.left != null) && (p.right != null)) { // Case 3: two
					// children

					// Search for the min in the right subtree
					BSTNode<T> min = p.right;
					q = p;
					while (min.left != null) {
						q = min;
						min = min.left;
					}
					p.key = min.key;
					p.data = min.data;
					k1 = min.key;
					p = min;
					// Now fall back to either case 1 or 2
				}

				// The subtree rooted at p will change here
				if (p.left != null) { // One child
					p = p.left;
				} else { // One or no children
					p = p.right;
				}

				if (q == null) { // No parent for p, root must change
					root = p;
				} else {
					if (k1.compareTo(q.key) < 0) {
						q.left = p;
					} else {
						q.right = p;
					}
				}
				current = root;
				return true;

			}
		}

		return false; // Not found
	}

	/*
	 * traverse the tree and store each tag in a linked list
	 */

	public LinkedList<String> getAllTags() {
		LinkedList<String> tags = new LinkedList<String>();
		getAllTags(root, tags);
		return tags;
	}

	// recursive method
	private void getAllTags(BSTNode<T> p, LinkedList<String> tags) {
		tags.insert(p.key);
		if (p.left != null)
			getAllTags(p.left, tags);
		if (p.right != null)
			getAllTags(p.right, tags);
	}

	/*
	 * traverse the tree and compare each key with the given tag and count how many
	 * times it took to compare it
	 */

	public int countComparisons(String tag) {
		int numOfComp = 0;
		BSTNode<T> p = root;
		return countComparisons(tag, p, numOfComp);
	}

	// recursive method
	private int countComparisons(String tag, BSTNode<T> p, int count) {
		if (p == null)
			return count;
		if (tag.equals(p.key))
			return ++count;
		if (tag.compareTo(p.key) < 0)
			return countComparisons(tag, p.left, ++count);
		return countComparisons(tag, p.right, ++count);
	}

	/*
	 * delete anything below here :)
	 */

	@SuppressWarnings("unchecked")
	public void inorder() {
		if (empty())
			System.out.println("Empty Tree.");
		else
			inorder((BSTNode<LinkedList<Photo>>) root);
	}

	private void inorder(BSTNode<LinkedList<Photo>> root) {
		if (root != null) {
			inorder(root.left);
			System.out.println("KEY:" + root.key + " ");
			System.out.println();
			display(root.data);
			inorder(root.right);
		}
	}

	public void display(LinkedList<Photo> l) {
		if (l.empty())
			System.out.println("No List");
		else {
			l.findFirst();
			while (!l.last()) {
				System.out.println(l.retrieve().getPath() + " ");
				l.findNext();
			}
			System.out.println(l.retrieve().getPath() + " ");
			System.out.println("______________________________");
		}
	}
	//add to BST
    @Override
    public String toString() {
	if (root == null)
	    return "NULL";
	return root.toString();
    }
}
