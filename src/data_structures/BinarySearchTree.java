/*
 * Robbie Pecjo masc1518
 * Professor Riggins
 * Programming Assignment #3
 * 12/05/2012
 */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class BinarySearchTree<K,V> implements DictionaryADT<K,V> {
	@SuppressWarnings("hiding")
	private class DictionaryNode<K,V> {
		private K key;	//data item(key)
		private V value;	//data item
		private DictionaryNode<K,V> leftChild;	//node's left child
		private DictionaryNode<K,V> rightChild;	//node's right child

		public DictionaryNode(K k, V v) {
			key = k;
			value = v;
			leftChild = rightChild = null;
		}

		@SuppressWarnings({ "unchecked", "unused" })
		public int compareTo(DictionaryNode<K,V> node) {
			return (((Comparable<K>)key).compareTo((K)node.key));
		}
	}

	private DictionaryNode<K,V> root;
	private int currentSize, modCounter;

	public BinarySearchTree() {
		root = null;
		currentSize = modCounter = 0;
	}
	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean contains(K key) {
		DictionaryNode<K,V> tmp = new DictionaryNode<K,V>(key,null);
		if(root == null)
			return false;
		else {
			DictionaryNode<K,V> current = root;
			for(;;) {
				if(((Comparable)tmp).compareTo(current) < 0)
					current = current.leftChild;
				else if(((Comparable)tmp).compareTo(current) > 0)
					current = current.rightChild;
				else if(((Comparable)tmp).compareTo(current) == 0)
					return true;
				else
					return false;
			}
		}
	}

	// Adds the given key/value pair to the dictionary.  Returns
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean add(K key, V value) {
		if(root == null)
			root = new DictionaryNode<K,V>(key,value);
		else
			insert(key, value, root, null, false);
		currentSize++;
		modCounter++;
		return true;
	}

	@SuppressWarnings("unchecked")
	private void insert(K k, V v, DictionaryNode<K,V> n, DictionaryNode<K,V> parent, boolean wasLeft) {
		if(n == null) { //at a leaf node so do the insert
			if(wasLeft) parent.leftChild = new DictionaryNode<K,V>(k,v);
			else parent.rightChild = new DictionaryNode<K,V>(k,v);
		}
		else if(((Comparable<K>)k).compareTo((K)n.key) <0)
			insert(k, v, n.leftChild, n, true); //go left
		else
			insert(k, v, n.rightChild, n, false); //go right
		}


	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean delete(K key) {
		if(isEmpty()) return false;

		DictionaryNode<K,V> current = root;
		DictionaryNode<K,V> parent = root;
		boolean wasLeft = true;

		if(current == null)
			return false;
		if(currentSize == 0)
			root = null;
		//case for 0 children
		if(current.leftChild == null && current.rightChild == null)
		{
			if(current == root) {
				root = null;
				currentSize--;
			}
			else if(wasLeft) {//check if leftChild
				parent.leftChild = null;
				currentSize--;
			}
			else {//if not leftChild, then rightChild
				parent.rightChild = null;
				currentSize--;
			}
		}
		//case for 1 child, right null
		else if(current.rightChild == null)
		{
			if(current == root) {
				root = current.leftChild;
				currentSize--;
			}
			else if(wasLeft) {//check if leftChild
				parent.leftChild = current.leftChild;
				currentSize--;
			}
			else {//if not leftChild, then rightChild
				parent.rightChild = current.leftChild;
				currentSize--;
			}
		}
		//case for 1 child, left null
		else if(current.leftChild == null) {
			if(current == root) {
				root = current.rightChild;
				currentSize--;
			}
			else if(wasLeft) {//check if leftChild
				parent.leftChild = current.rightChild;
				currentSize--;
			}
			else {//if not leftChild, then rightChild
				parent.rightChild = current.rightChild;
				currentSize--;
			}
		}
		//case for 2 children
		else {
			DictionaryNode<K,V> successor = findSuccessor(current);
			if(current == root) {
				root = successor;
				currentSize--;
			}
			else if(wasLeft) {//check if leftChild
				parent.leftChild = successor;
				currentSize--;
			}
			else {//if not leftChild, then rightChild
				parent.rightChild = successor;
				currentSize--;
			}
			successor.leftChild = current.leftChild;
		}
		return true;
	}
	/*find the inorder successor and use the key and value from one of those nodes to replace the key and value in the node to be deleted*/
	private DictionaryNode<K,V> findSuccessor(DictionaryNode<K,V> node) {
		DictionaryNode<K,V> successorParent = node;
		DictionaryNode<K,V> successor = node;
		DictionaryNode<K,V> current = node.rightChild;
		while(current != null) { // if null, returns successor
			successorParent = successor;
			successor = current;
			current = current.leftChild;
		}
		if(successor != node.rightChild) {
			successorParent.leftChild = successor.rightChild;
			successor.rightChild = node.rightChild;
		}
		return successor;
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key) {
		return find(key, root);
	}

	@SuppressWarnings("unchecked")
	private V find(K key, DictionaryNode<K,V> n) {
		if(n == null) return null;
		if(((Comparable<K>)key).compareTo(n.key) < 0)
			return find(key, n.leftChild);
		else if(((Comparable<K>)key).compareTo(n.key) > 0)
			return find(key, n.rightChild);
		else return (V) n.value;

	}
	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more
	// than one key exists that matches the given value, returns the
	// first one found.
	@SuppressWarnings("unchecked")
	public K getKey(V value) {
		Iterator<K> k = keys();
		Iterator<V> v = values();
		while(k.hasNext()) {
			K tmpK = k.next();
			V tmpV = v.next();
			if(((Comparable<V>)tmpV).compareTo(value) == 0)
				return tmpK;
		}
		return null;
	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	public int size() {return currentSize;}

	// Returns true if the dictionary is at max capacity
	public boolean isFull(){
		return false;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty() {
		return currentSize == 0;
	}

	// Returns the Dictionary object to an empty state.
	public void clear() {
		root = null;
		currentSize = 0;
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order.  The iterator must be fail-fast.
	public Iterator<K> keys() {return new IteratorHelperK();}

	public class IteratorHelperK implements Iterator<K> {
		long stateCheck;
		int IteratorPointer, index;
		@SuppressWarnings("unchecked")
		K [] tmpArray = (K[]) new Object[currentSize];

		private void inOrderFill(DictionaryNode<K,V> n) {
			if(n == null) return;
			inOrderFill(n.leftChild);
			tmpArray[index++] = (K) n.key;
			inOrderFill(n.rightChild);
		}

		public IteratorHelperK() {
			IteratorPointer = index = 0;
			stateCheck = modCounter;
			inOrderFill(root);
		}

		@Override
		public boolean hasNext() {
			if(stateCheck != modCounter)
				throw new ConcurrentModificationException();
			else
				return IteratorPointer < currentSize;
		}
		@Override
		public K next() {
			if(!hasNext()) throw new NoSuchElementException();
			return tmpArray[IteratorPointer++];
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	// Returns an Iterator of the values in the dictionary.  The
	// order of the values must match the order of the keys.
	// The iterator must be fail-fast.
	public Iterator<V> values() {return new IteratorHelperV();}

	public class IteratorHelperV implements Iterator<V> {
		long stateCheck;
		int IteratorPointer, index;
		@SuppressWarnings("unchecked")
		V [] tmpArray = (V[]) new Object[currentSize];

		private void inOrderFill(DictionaryNode<K,V> n) {
			if(n == null) return;
			inOrderFill(n.leftChild);
			tmpArray[index++] = (V)n.value;
			inOrderFill(n.rightChild);
		}

		public IteratorHelperV() {
			IteratorPointer = index = 0;
			stateCheck = modCounter;
			inOrderFill(root);
		}

		@Override
		public boolean hasNext() {
			if(stateCheck != modCounter)
				throw new ConcurrentModificationException();
			else
				return IteratorPointer < currentSize;
		}

		@Override
		public V next() {
			if(!hasNext()) throw new NoSuchElementException();
			return (V)tmpArray[IteratorPointer++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
