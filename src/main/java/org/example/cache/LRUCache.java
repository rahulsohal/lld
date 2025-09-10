package org.example.cache;


import java.util.HashMap;
import java.util.Map;

public class LRUCache implements Cache{

    static class Node {
        int key;
        int value;
        Node next;
        Node prev;

        public Node(int k, int v) {
            this.key = k;
            this.value=v;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", key=" + key +
                    '}';
        }
    }

    Map<Integer, Node> cache = new HashMap<>();

    Node head = new Node(-1, -1);
    Node tail = new Node(-1, -1);
    private int cap;

    public LRUCache(int capacity) {
        this.cap = capacity;
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public String toString() {
        return "LRUCache{" +
                "cache=" + cache +
                ", head=" + head +
                ", tail=" + tail +
                ", cap=" + cap +
                '}';
    }

    public void addNode(Node newNode) {
        Node temp = head.next;

        head.next = newNode;
        newNode.prev = head;

        newNode.next = temp;
        temp.prev = newNode;
    }

    public void removeNode(Node targetNode) {
        Node tNext = targetNode.next;
        Node tPrev = targetNode.prev;

        tNext.prev = tPrev;
        tPrev.next = tNext;

        targetNode.next= null;
        targetNode.prev = null;
    }

    public void put(int key, int value) {
        Node newNode = new Node(key, value);

        if(cache.containsKey(key)) {
            Node delete = cache.get(key);
            cache.remove(delete.key);
            removeNode(delete);
        }

        if(cache.size() == cap) {
            Node tNode = tail.prev;
            cache.remove(tNode.key);
            removeNode(tNode);
        }

        cache.put(key, newNode);
        addNode(newNode);
    }

    public int get(int key) {
        Node node;

        if(cache.containsKey(key)) {
            node = cache.get(key);
            removeNode(node);
            addNode(node);
            System.out.println("Key: " + node.value);
            return node.value;
        } else {
            System.out.println("No key = "+key+" found in cache");
            return -1;
        }
    }
}
