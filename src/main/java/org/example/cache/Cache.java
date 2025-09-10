package org.example.cache;

public interface Cache {

    void put(int key, int value);
    int get(int key);
}
