package org.example.cache;

public class CacheRunner {
    public static void main(String[] args) {
        Cache cache = new LFUCache(2);

        cache.put(1,467);
        cache.put(2,198);
        cache.get(1);
        cache.put(3,255);
        cache.get(4);
        cache.get(2);
        System.out.println(cache);
    }
}
