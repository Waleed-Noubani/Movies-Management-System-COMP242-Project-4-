package application;


public class TNode<T extends Comparable<T>> {
	private T value;
	private TNode<T> left, right;

	TNode(T val) {
		value = val;
	}

	@Override
	public String toString() {
		return "TNode [value=" + value + "]";
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public TNode<T> getLeft() {
		return left;
	}

	public void setLeft(TNode<T> left) {
		this.left = left;
	}

	public TNode<T> getRight() {
		return right;
	}

	public void setRight(TNode<T> right) {
		this.right = right;
	}

}
