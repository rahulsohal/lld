package org.example.cache;

import java.util.*;

public class TimeMap {
    static class Node {
        String key;
        String value;
        int timestamp;

        public Node(String v, int t) {
            this.value = v;
            this.timestamp = t;
        }

        @Override
        public String toString() {
            return "Node{" +
                    ", value='" + value + '\'' +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }

    private Map<String, List<Node>> store;
    private static final String EMPTY_STRING = "";

    public TimeMap() {
        store = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {

        Node node  = new Node(value, timestamp);
        store.computeIfAbsent(key, k -> new ArrayList<>()).add(node);
    }

    public String get(String key, int timestamp) {
        if(store.containsKey(key))
            return binarySearch(store.get(key), timestamp);
        return EMPTY_STRING;
    }

    private String binarySearch(List<Node> nodes, int timestamp) {
        int start =0, end=nodes.size()-1;
        String value = EMPTY_STRING;
        while (start <= end) {
            int mid = start + (end - start) / 2;

            if(nodes.get(mid).timestamp <= timestamp ) {
                value = nodes.get(mid).value;
                start = mid+1;
            } else {
                end = mid-1;
            }
        }
        return value;
    }
}
