package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HashTable<T extends Comparable<T>> {

	AVLTree<T>[] table;
	int size;
	int count;

	public HashTable(int size) {
		this.size = size;
		table = new AVLTree[size];
		for (int i = 0; i < table.length; i++) {
			table[i] = new AVLTree<>();
		}
		count = 0;
	}

	public int getSize() {
		return this.size;
	}

	public AVLTree<T>[] getTable() {
		return table;
	}

	public void insert(T data) {
		int key = Math.abs(data.hashCode());
		int index = key % size;
		if (table[index].search(data) == null) {
			table[index].insert(data);
			count++;
			if (hightAvg() > 3) {
				reHashing();
			}
		} else {
		}
	}

	public T search(T target) {
		int key = Math.abs(target.hashCode());
		int index = key % size;
		AVLTree<T> tree = table[index];
		if (tree != null) {
			TNode<T> node = tree.search(target);
			if (node != null)
				return node.getValue();
		}
		return null;
	}

	public AVLTree<T> getAvll(int index) {
		return table[index];
	}

	public void delete(T target) {
		int key = Math.abs(target.hashCode());
		int index = key % size;
		AVLTree<T> tree = table[index];
		if (tree != null && tree.search(target) != null) {
			tree.delete(target);
			count--;
		} else {
		}
	}

	public int hightAvg() {
		int countNonEmpty = 0;
		int total = 0;

		for (AVLTree<T> avlTree : table) {
			total += avlTree.height();
			if (!avlTree.isEmpty())
				countNonEmpty++;
		}

		if (countNonEmpty == 0) // عشان ما يضرب اكسبشن
			return 0;

		return total / countNonEmpty;
	}

	public void reHashing() {
		this.size = 2 * size;
		while (!isPrime(size))
			this.size++;

		AVLTree<T>[] temp = table;
		table = new AVLTree[this.size];
		for (int i = 0; i < table.length; i++) { // return make inishilaize
			table[i] = new AVLTree<>();
		}

		this.count = 0;

		for (AVLTree<T> avlTree : temp) { // 
			for (T value : avlTree) {
				this.insert(value);
			}
		}
	}

	public void reSize() {
		this.size = 2 * size;
		while (!isPrime(size))
			this.size++;

	}

	public boolean isPrime(int sizz) {
		if (sizz <= 3)
			return true;
		if (sizz % 2 == 0 || sizz % 3 == 0 || size <= 1)
			return false;

		for (int i = 5; i * i <= sizz; i += 6) {
			if (sizz % i == 0 || sizz % (i + 2) == 0)
				return false;
		}
		return true;
	}

	public ObservableList<T> toObservableList() {
		ObservableList<T> list = FXCollections.observableArrayList();
		for (AVLTree<T> avlTree : table) {
			for (T item : avlTree) {
				list.add(item);
			}
		}
		return list;
	}

	public void traverse() {
		for (AVLTree<T> avlTree : table) {
			avlTree.printInOrder();
		}
	}

	public void clear() {
		for (AVLTree<T> avlTree : table) {
			avlTree.clear();
		}
	}
}
