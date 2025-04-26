/*
package model.adt;

import exceptions.EmptyDictionaryException;
import exceptions.EmptyStackException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileTable<K, V> implements MyIDictionary<K, V> {
    private Map<K, V> map;

    public FileTable() {
        this.map = new HashMap<>();
    }

    @Override
    public void insert(K k, V v) {
        this.map.put(k, v);
    }

    @Override
    public void remove(K k) throws EmptyDictionaryException {
        if (!this.map.containsKey(k)) throw new EmptyDictionaryException("Key doesn't exist");
        this.map.remove(k);
    }

    @Override
    public boolean contains(K k) {
        return this.map.containsKey(k);
    }

    @Override
    public V get(K k) throws EmptyDictionaryException {
        if (!this.map.containsKey(k)) throw new EmptyDictionaryException("Key doesn't exist");
        return this.map.get(k);
    }

    @Override
    public Set<K> keys() {
        return this.map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (K k : this.map.keySet()) {
            str.append(k).append("\n");
        }
        return "FileTable:\n" + str;
    }

    @Override
    public void update(K k, V v) throws EmptyDictionaryException {
        if (!this.map.containsKey(k)) throw new EmptyDictionaryException("Key doesn't exist!\n");
        this.map.put(k, v);
    }

    @Override
    public Map<K, V> getContent(){
        return this.map;
    }
}
*/
