package Map;
import java.util.*;

public class MyMap<K,V> {
	private final float LOAD = 0.75f;
	private int size;
	private int capacity = 10;
	ArrayList<MyNode<K,V>> bucket;
	
	public MyMap () {
		bucket = new ArrayList<MyNode<K,V>>();
		size = 0;
		for (int i = 0 ; i < capacity ; i++)
			bucket.add(null);
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty () {
		return size == 0;
	}
	
	private final int hashCode (K key) {
		return Objects.hashCode(key);
	}
	
	public V get (K key) {
		int hashCode = hashCode(key);
		int index = hashCode % capacity;
		
		MyNode<K,V> node = bucket.get(index);
		while (node != null) {
			if (node.hashCode == hashCode && node.key == key) {
				return node.value;
			}
			else
				node = node.next;
		}
		return null;
	}
	
	public void remove (K key) {
		int hashCode = hashCode(key);
		int index = hashCode % capacity;
		
		MyNode<K,V> node = bucket.get(index);
		while (node != null) {
			if (node.hashCode == hashCode && node.key == key) {
				delete (index, node);
				return;
			}
			else
				node = node.next;
		}
	}
	
	
	public void put (K key, V value) {
		int hashCode = hashCode(key);
		int index = hashCode % capacity;
		
		MyNode<K,V> node = bucket.get(index);
		MyNode<K,V> parent = null;
		
		while (node != null) {
			if (hashCode == node.hashCode && key == node.key) {
				node.value = value;
				return;
			}
			
			parent = node;
			node = node.next;
			
			if (node == null && parent != null) {
				size++;
				parent.next = new MyNode<K, V>(key, value, hashCode);
				return;
			}
		}
		
		size++;
		bucket.set(index, new MyNode<K, V>(key, value, hashCode));
		
		if ((double)size/capacity > LOAD) {
			resizeBucket();
		}
	}
	
	private void resizeBucket () {
		ArrayList<MyNode<K,V>> temp = bucket;
		capacity = capacity * 2;
		
		bucket = new ArrayList<MyNode<K,V>>();
		for (int i = 0 ; i < capacity ; i++)
			bucket.add(null);
		
		for (MyNode<K,V> obj : temp) {
			while (obj != null) {
				put(obj.key, obj.value);
				obj = obj.next;
			}
		}
	}
	
	private void delete (int index, MyNode<K,V> node) {
		MyNode<K,V> root = bucket.get(index);
		
		MyNode<K,V> parent = null;
		while (root != null) {
			
			if (root == node) {
				if (parent == null) {
					bucket.add(index, root.next);
				}
				else {
					parent.next = root.next;
				}
				size--;
			}
			parent = root;
			root = root.next;
		}
	}
	
}
