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

public class Hashtable<K,V> implements DictionaryADT<K,V> {
	private int currentSize, maxSize, tableSize, modCounter;
	private UnorderedList<DictionaryNode<K,V>> [] list;

	@SuppressWarnings("unchecked")
	public Hashtable(int n) {
		currentSize = modCounter = 0;
		maxSize = n;
		tableSize = (int)(maxSize * 1.3f);
		list = new UnorderedList[tableSize]; //cannot put the k's and v's directly into here at this point because the array is not initialized
		for(int i = 0; i < tableSize; i++) //make the lists in this for loop
		list[i] = new UnorderedList<DictionaryNode<K,V>>();
		}

	@SuppressWarnings("hiding")
	class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>{
		K key;
		V value;
		@SuppressWarnings("unchecked")
		public int compareTo(DictionaryNode<K,V> b){
			return ((Comparable<K>)key).compareTo(b.key);
		}

		public DictionaryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }


	}
	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key) {
		if(getValue(key) == null)
			return false;
		return true;
	}

	// Adds the given key/value pair to the dictionary.  Returns
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean add(K key, V value){
		if(isFull()) return false;
		if(contains(key)) return false;
		list[getIndex(key)].addFirst(new DictionaryNode<K,V> (key,value));
		currentSize++;
		modCounter++;
		if(isFull())
			return false;

		return true;
	}
	private int getIndex(K key) {return (key.hashCode()&0x7FFFFFFF) % tableSize;}
	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean delete(K key) {
		if(contains(key)) {
			list[getIndex(key)].remove(new DictionaryNode<K,V>(key,null));
			currentSize--;
			return true;
		}
		return false;
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key) {
		DictionaryNode<K,V> tmp = list[getIndex(key)].find(new DictionaryNode<K,V>(key,null));
		if(tmp == null)
			return null;
		return tmp.value;
	}


	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more
	// than one key exists that matches the given value, returns the
	// first one found.
	@SuppressWarnings("unchecked")
	public K getKey(V value) {
		Iterator<K> iterK = keys();
		Iterator<V> iterV = values();
		while(iterK.hasNext()) {
			K tmpK = iterK.next();
			V tmpV = iterV.next();
			if(((Comparable<V>)tmpV).compareTo(value)== 0)
				return tmpK;
		}
		return null;
	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	public int size() {return currentSize;}

	// Returns true if the dictionary is at max capacity
	public boolean isFull() {
		if(currentSize > maxSize) return true;
		return false;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty() {
		if(currentSize == 0) return true;
		return false;
	}

	// Returns the Dictionary object to an empty state.
	public void clear() {
		currentSize = 0;
		for(int i = 0; i < tableSize; i++)
			list[i].makeEmpty();
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order.  The iterator must be fail-fast.
	public Iterator<K> keys() {return new KeyIteratorHelper<K>();}

	@SuppressWarnings("hiding")
	public class KeyIteratorHelper<K> implements Iterator<K> {
		private DictionaryNode<K,V> [] nodes;
		private int index;
		long stateCheck;
		@SuppressWarnings("unchecked")
		public KeyIteratorHelper() {
			nodes = new DictionaryNode[currentSize];
			stateCheck = modCounter;
			index = 0;
			int j = 0;
			for(int i = 0; i < tableSize; i++)
				for(@SuppressWarnings("rawtypes") DictionaryNode n : list[i])
					nodes[j++] = n;
			nodes = (DictionaryNode[]) shellSort(nodes);
		}
		public void remove() {throw new UnsupportedOperationException();}
		public boolean hasNext() {
			if(stateCheck != modCounter)
				throw new ConcurrentModificationException();
			else
				return index < currentSize;
			//return index < currentSize;
//			if(index >= currentSize)
//				throw new ConcurrentModificationException();
//			return true;
			}
		public K next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (K)nodes[index++].key;
		}

	}

	// Returns an Iterator of the values in the dictionary.  The
	// order of the values must match the order of the keys.
	// The iterator must be fail-fast.
	public Iterator<V> values() {return new ValueIteratorHelper();}

	public class ValueIteratorHelper implements Iterator<V> {
		private Iterator<K> k;
		public ValueIteratorHelper() {k = keys();}
		public boolean hasNext() {return k.hasNext();}
		public V next() {return getValue(k.next());}
		public void remove() {throw new UnsupportedOperationException();}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DictionaryNode[] shellSort(DictionaryNode[] array) {
		DictionaryNode[] n = array;
		int in, out, h=1;
		DictionaryNode temp;
		int size = n.length;

		while(h <= size/3) //calculate gaps
			h = h*3+1;

		while(h > 0) {
			for(out = h; out < size; out++) {
				temp = n[out];
				in = out;
				while(in > h-1 && n[in-h].compareTo((DictionaryNode<K,V>) temp) >= 0) {
					n[in] = n[in-h];
					in -= h;
				}
			n[in] = temp;
			}//end for
			h = (h-1)/3;
		}//end while

		return n;
		}

}
