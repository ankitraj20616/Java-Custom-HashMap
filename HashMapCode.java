import java.util.*;

public class HashMapCode {
    static class HashMap<K, V> {
        private class Node {
            K key;
            V value;

            public Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private int n;
        private int N;
        private LinkedList<Node> buckets[];

        @SuppressWarnings("unchecked")
        public HashMap() {
            this.N = 4;
            this.buckets = new LinkedList[N];
            for (int i = 0; i < buckets.length; i++) {
                buckets[i] = new LinkedList<>();
            }
        }

        public int hashFunction(K key) {
            int bi = key.hashCode();
            return Math.abs(bi) % N;
        }

        public int searchInLL(K key, int bi) {
            LinkedList<Node> ll = buckets[bi];
            for (int i = 0; i < ll.size(); i++) {
                Node data = ll.get(i);
                if (data.key == key)
                    return i;
            }
            return -1;
        }

        @SuppressWarnings("unchecked")
        public void rehash() {
            LinkedList<Node> oldbuckets[] = buckets;
            buckets = new LinkedList[N * 2];

            for (int i = 0; i < oldbuckets.length; i++) {
                LinkedList<Node> ll = oldbuckets[i];
                for (int j = 0; j < ll.size(); j++) {
                    Node data = ll.get(j);
                    put(data.key, data.value);
                }
            }
        }

        public void put(K key, V value) {
            int bi = hashFunction(key);
            int di = searchInLL(key, bi);

            if (di < 0) {
                buckets[bi].add(new Node(key, value));
                n++;
            } else {
                Node data = buckets[bi].get(di);
                data.value = value;
            }

            double lamda = (double) n / N;
            if (lamda > 2.0) {
                rehash();
            }
        }

        public V get(K key) {
            int bi = hashFunction(key);
            int di = searchInLL(key, bi);

            if (di < 0) {
                return null;
            } else {
                Node data = buckets[bi].get(di);
                return data.value;
            }
        }

        public boolean containKey(K key) {
            int bi = hashFunction(key);
            int di = searchInLL(key, bi);

            if (di < 0)
                return false;
            else
                return true;
        }

        public V remove(K key) {
            int bi = hashFunction(key);
            int di = searchInLL(key, bi);

            if (di < 0)
                return null;
            else {
                Node data = buckets[bi].remove(di);
                n--;
                return data.value;
            }
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public ArrayList<K> keySet() {
            ArrayList<K> keys = new ArrayList<>();

            for (int i = 0; i < buckets.length; i++) {
                LinkedList<Node> ll = buckets[i];
                for (int j = 0; j < ll.size(); j++) {
                    Node data = ll.get(j);
                    keys.add(data.key);
                }
            }
            return keys;
        }
    }

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("India", 190);
        map.put("China", 200);
        map.put("US", 150);
        map.put("Sri Lanka", 100);

        ArrayList<String> keys = map.keySet();
        for (int i = 0; i < keys.size(); i++)
            System.out.println(keys.get(i) + " -> " + map.get(keys.get(i)));

        System.out.println("After removing China from HashMap:-");
        map.remove("China");
        keys = map.keySet();
        for (int i = 0; i < keys.size(); i++)
            System.out.println(keys.get(i) + " -> " + map.get(keys.get(i)));

    }
}
