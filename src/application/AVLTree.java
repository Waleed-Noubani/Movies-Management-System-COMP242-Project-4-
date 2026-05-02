package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AVLTree<T extends Comparable<T>> implements Iterable<T> {
	TNode<T> root;

	public int height() {
		return height(root);
	}

	private int height(TNode<T> node) {
		if (node == null)
			return 0;
		return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
	}

	int balanceFactor(TNode<T> node) { // height diffrants
		if (node == null)
			return 0;
		return height(node.getLeft()) - height(node.getRight());
	}

	private TNode<T> leftRotation(TNode<T> node) {
		TNode<T> child = node.getRight();
		TNode<T> grandChild = child.getLeft();
		child.setLeft(node);
		node.setRight(grandChild);
		return child;
	}

	private TNode<T> rightRotation(TNode<T> node) {
		TNode<T> child = node.getLeft();
		TNode<T> grandChild = child.getRight();
		child.setRight(node);
		node.setLeft(grandChild);
		return child;
	}

	private TNode<T> rotateLeftRight(TNode<T> node) {
		TNode<T> c = node.getLeft();
		node.setLeft(leftRotation(c));
		return rightRotation(node);
	}

	private TNode<T> rotateRightLeft(TNode<T> node) {
		TNode<T> c = node.getRight();
		node.setRight(rightRotation(c));
		return leftRotation(node);
	}

	TNode<T> search(T key) {
		return search(root, key);
	}

	TNode<T> search(TNode<T> root, T key) {
		if (root == null || key.compareTo(root.getValue()) == 0)
			return root;
		if (key.compareTo(root.getValue()) < 0)
			return search(root.getLeft(), key);
		return search(root.getRight(), key);
	}

	private TNode<T> insert(TNode<T> curr, T val) {
		if (curr == null)
			return new TNode(val);

		if (val.compareTo(curr.getValue()) < 0)
			curr.setLeft(insert(curr.getLeft(), val));
		else if (val.compareTo(curr.getValue()) > 0)
			curr.setRight(insert(curr.getRight(), val));
		else
			return curr;

		int balance = balanceFactor(curr);

		if (balance > 1) {
			if (balanceFactor(curr.getLeft()) >= 0) {
				return rightRotation(curr); // LL
			} else {
				return rotateLeftRight(curr); // LR
			}

		} else if (balance < -1) {
			if (balanceFactor(curr.getRight()) <= 0) {
				return leftRotation(curr); // RR
			} else {
				return rotateRightLeft(curr); // RL
			}
		}

		return curr;
	}

	public void insert(T val) {
		root = insert(root, val);
	}

	void inOrder(TNode<T> node) {
		if (node != null) {
			inOrder(node.getLeft());
			System.out.print(node.getValue() + " ");
			inOrder(node.getRight());
		}
	}

	public void printInOrder() {
		inOrder(root);
		System.out.println();
	}

	private TNode<T> minValueNode(TNode<T> node) {
		TNode<T> current = node;

		while (current.getLeft() != null)
			current = current.getLeft();

		return current;
	}

	public void delete(T data) {
		root = delete(root, data);
	}

	private TNode<T> delete(TNode<T> node, T target) {
		if (node == null)
			return null;

		if (target.compareTo(node.getValue()) < 0) {
			node.setLeft(delete(node.getLeft(), target));
		} else if (target.compareTo(node.getValue()) > 0) {
			node.setRight(delete(node.getRight(), target));
		} else {
			if (node.getLeft() == null || node.getRight() == null) {
				TNode<T> temp = (node.getLeft() != null) ? node.getLeft() : node.getRight();

				if (temp == null) {
					node = null;
				} else {
					node = temp;
				}
			} else {
				TNode<T> temp = minValueNode(node.getRight());
				node.setValue(temp.getValue());
				node.setRight(delete(node.getRight(), temp.getValue()));
			}
		}

		if (node == null)
			return null;

		int balance = balanceFactor(node);

		if (balance > 1 && balanceFactor(node.getLeft()) >= 0)
			return rightRotation(node);
		if (balance > 1 && balanceFactor(node.getLeft()) < 0) {
			node.setLeft(leftRotation(node.getLeft()));
			return rightRotation(node);
		}

		if (balance < -1 && balanceFactor(node.getRight()) <= 0)
			return leftRotation(node);

		if (balance < -1 && balanceFactor(node.getRight()) > 0) {
			node.setRight(rightRotation(node.getRight()));
			return leftRotation(node);
		}

		return node;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public ObservableList<T> toObservableList() {
		ObservableList<T> list = FXCollections.observableArrayList();
		for (T item : this) {
			list.add(item);
		}
		return list;
	}

	@Override
	public Iterator<T> iterator() {
		List<T> list = new ArrayList<>();
		inOrderTraversal(root, list);
		return list.iterator();
	}

	private void inOrderTraversal(TNode<T> node, List<T> list) {
		if (node != null) {
			inOrderTraversal(node.getLeft(), list);
			list.add(node.getValue());
			inOrderTraversal(node.getRight(), list);
		}
	}

	public void clear() {

		root = null;
	}
}
