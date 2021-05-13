package Map;
import java.util.*;

public class MyNode<K,V> {
	K key;
	V value;
	int hashCode;
	MyNode next;
	
	MyNode (K key, V value, int hashCode) {
		this.key = key;
		this.value = value;
		this.hashCode = hashCode;
	}
}

