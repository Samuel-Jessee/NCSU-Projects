package edu.ncsu.csc316.bridgemanager.util;

/**
 * A Heap that stores Nodes in a partially ordered binary tree.
 * 
 * @author Samuel Jessee (sijessee)
 * @author Theodore Reger (tlreger)
 * @author Devin Janus (dwjanus)
 *
 * @param <K>
 *            Type to use for keys.
 * @param <V>
 *            Type to use for values.
 */
public class Heap<K extends Comparable<K>, V> {

	/** Array that stores the heap. */
	private ArrayBasedList<Entry<K, V>> heap;

	/**
	 * Constructs a Heap.
	 */
	public Heap() {
		this.heap = new ArrayBasedList<Entry<K, V>>();
	}

	/**
	 * Constructs a Heap with a root Node.
	 * 
	 * @param key
	 *            The key of the root node.
	 * @param value
	 *            The value at the root node.
	 */
	public Heap(K key, V value) {
		Entry<K, V> input = new Entry<K, V>(key, value);
		this.heap = new ArrayBasedList<Entry<K, V>>();
		heap.add(0, input);
	}

	/**
	 * Returns the index of the parent to the parameter index.
	 * 
	 * @param i
	 *            Initial index.
	 * @return Index of parent.
	 */
	public int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Returns the index of the left child to the parameter index.
	 * 
	 * @param i
	 *            Initial index.
	 * @return Index of left child.
	 */
	public int left(int i) {
		return 2 * i + 1;
	}

	/**
	 * Returns the index of the right child to the parameter index.
	 * 
	 * @param i
	 *            Initial index.
	 * @return Index of right child.
	 */
	public int right(int i) {
		return 2 * i + 2;
	}

	/**
	 * Determines if the given index has a left child.
	 * 
	 * @param i
	 *            Index to check.
	 * @return True if the node has a left child.
	 */
	public boolean hasLeft(int i) {
		return left(i) < heap.size();
	}

	/**
	 * Determines if the given index has a right child.
	 * 
	 * @param i
	 *            Index to check.
	 * @return True if the node has a right child.
	 */
	public boolean hasRight(int i) {
		return right(i) < heap.size();
	}

	/**
	 * Swaps the positions of two nodes in the Heap.
	 * 
	 * @param i
	 *            First node to swap.
	 * @param j
	 *            Second node to swap.
	 */
	public void swap(int i, int j) {
		Entry<K, V> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}

	/**
	 * Moves upward in the Heap, performing swap methods as needed to keep the
	 * Heap partially ordered.
	 * 
	 * @param i
	 *            Next index to check.
	 */
	public void upheap(int i) {
		while (i >= 0) {
			int p = parent(i);
			if (heap.get(i).compareTo(heap.get(p)) >= 0)
				break;
			swap(i, p);
			i = p;
		}
	}

	/**
	 * Moves downward in the Heap, performing swap methods as needed to keep the
	 * Heap partially ordered.
	 * 
	 * @param i
	 *            Next index to check.
	 */
	public void downheap(int i) {
		while (hasLeft(i)) {
			int leftIndex = left(i);
			int smallChild = leftIndex;
			if (hasRight(i)) {
				int rightIndex = right(i);
				if (heap.get(leftIndex).compareTo(heap.get(rightIndex)) > 0) {
					smallChild = rightIndex;
				}
			}
			if (heap.get(smallChild).compareTo(heap.get(i)) >= 0)
				break;
			swap(i, smallChild);
			i = smallChild;
		}
	}

	/**
	 * Returns the size of the Heap.
	 * 
	 * @return The size.
	 */
	public int size() {
		return heap.size();
	}

	/**
	 * Determines if the Heap is empty.
	 * 
	 * @return True if empty.
	 */
	public boolean isEmpty() {
		return heap.isEmpty();
	}

	/**
	 * Returns the minimum node in the Heap, which is the root node.
	 * 
	 * @return The minimum node.
	 */
	public Entry<K, V> min() {
		if (heap.isEmpty()) {
			return null;
		} else
			return heap.get(0);
	}

	/**
	 * Enters a new element in the Heap, and performs upheap operations to
	 * reorder the Heap.
	 * 
	 * @param key
	 *            The new node's key.
	 * @param value
	 *            The value at the new node.
	 * @return The new node.
	 * @throws IllegalArgumentException
	 */
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		Entry<K, V> in = new Entry<K, V>(key, value);
		heap.add(in);
		upheap(heap.size() - 1);
		return in;
	}

	/**
	 * Deletes the root node, and performs downheap operations to order the
	 * Heap.
	 * 
	 * @return The deleted node.
	 */
	public Entry<K, V> deleteMin() {
		if (heap.isEmpty())
			return null;
		Entry<K, V> min = heap.get(0);
		swap(0, heap.size() - 1);
		heap.remove(heap.size() - 1);
		downheap(0);
		return min;
	}

	/**
	 * Returns a String representation of the Heap.
	 * 
	 * @return A String describing the Heap.
	 */
	public String toString() {
		return "Heap" + heap.toString();
	}

	/**
	 * An node in the Heap that stores a key and a value.
	 * 
	 * @author Theodore Reger (tlreger)
	 * @author Devin Janus (dwjanus)
	 * @author Samuel Jessee (sijessee)
	 *
	 * @param <K>
	 *            Type to use for keys.
	 * @param <V>
	 *            Type to use for values.
	 */
	@SuppressWarnings("hiding")
	public class Entry<K extends Comparable<K>, V> {

		/** The Entry's key. */
		private K key;

		/** The value of this Entry. */
		private V value;

		/**
		 * Constructs an Entry.
		 * 
		 * @param key
		 *            The key of the Entry.
		 * @param v
		 *            The value of the Entry.
		 */
		public Entry(K key, V v) {
			this.key = key;
			this.value = v;
		}

		/**
		 * Returns the key.
		 * 
		 * @return The key.
		 */
		public K getKey() {
			return this.key;
		}

		/**
		 * Returns the value.
		 * 
		 * @return The value.
		 */
		public V getVal() {
			return this.value;
		}

		/**
		 * Compares this Entry to another one, and determines which comes first
		 * in sorted order. A -1 means that this Entry comes first, a 1 means
		 * this comes second, and if both Entries are exactly the same, it
		 * returns 0.
		 * 
		 * @param obj
		 *            Object to compare to this.
		 * @return An int representing which object comes first.
		 */
		public int compareTo(Object obj) {
			if (obj == null)
				throw new IllegalArgumentException();
			if (!(obj instanceof Entry))
				throw new IllegalArgumentException();

			@SuppressWarnings("unchecked")
			Entry<K, V> d = (Entry<K, V>) obj;
			if (key.equals(d.key))
				return 0;
			else if (key.compareTo(d.key) > 0)
				return 1;
			else
				return -1;
		}

		/**
		 * Returns a String representation of the Entry.
		 * 
		 * @return A String describing the Entry.
		 */
		public String toString() {
			return this.value.toString();
		}
	}

	/**
	 * Used to return a specific entry from the heap
	 * 
	 * @param i
	 *            the index of the heap we want to access
	 * @return Entry<K,V>
	 */
	public Heap<K, V>.Entry<K, V> get(int i) {
		return heap.get(i);
	}
}