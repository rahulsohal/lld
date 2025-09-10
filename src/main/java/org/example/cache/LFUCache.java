package org.example.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache implements Cache {

    //Idea here is to maintain a frequency map for keys which we refer while evicting Least frequently used
    // also maintain a global counter for minfreq and reset once the element is evicted.

    static class Node {
        int key;
        int value;
        int freq;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    private final int cap;
    Map<Integer, Node> cache;
    Map<Integer, LinkedHashSet<Node>> freqMap;
    private int minFreq;

    public LFUCache(int capacity) {
        cache = new HashMap<>();
        freqMap = new HashMap<>();
        this.cap = capacity;
        this.minFreq = 0;
    }

    @Override
    public void put(int key, int value) {
        if(cap <=0) return;

        if(cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            increaseFreq(node);
            return;
        }

        if(cache.size() == cap) {
            evict();
        }

        Node newNode  = new Node(key, value);
        cache.put(key, newNode);
        freqMap.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(newNode);
        minFreq=1;
    }

    @Override
    public int get(int key) {
        if(cache.containsKey(key)) {
            Node node = cache.get(key);
            increaseFreq(node);
            System.out.println("Key: "+ key + " value:" + node.value);
            return node.value;
        }else {
            System.out.println("No key = "+key+" found");
            return -1;
        }
    }

    private void increaseFreq(Node node) {
        int oldFreq = node.freq;
        freqMap.get(oldFreq).remove(node);

        int newFreq = oldFreq + 1;
        node.freq = newFreq;

        freqMap.computeIfAbsent(newFreq, k-> new LinkedHashSet<>()).add(node);

        if(freqMap.get(oldFreq).isEmpty() && oldFreq == minFreq)
            minFreq++;
    }

    private void evict() {
        if(freqMap.containsKey(minFreq)) {
            LinkedHashSet<Node> set = freqMap.get(minFreq);
            Node nodeToEvict = set.getFirst();
            set.remove(nodeToEvict);
            cache.remove(nodeToEvict.key);
        }
    }

    @Override
    public String toString() {
        return "LFUCache{" +
                "cap=" + cap +
                ", cache=" + cache +
                ", freqMap=" + freqMap +
                ", minFreq=" + minFreq +
                '}';
    }
}
