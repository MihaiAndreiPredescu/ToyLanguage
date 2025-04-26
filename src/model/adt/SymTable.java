/*
package model.adt;

import exceptions.EmptyDictionaryException;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SymTable<K, V> implements MyIDictionary<K, V> {
    private Map<K, V> map;

    public SymTable() { this.map = new HashMap<K, V>(); }

    @Override
    public void insert(K k, V v) {
        this.map.put(k, v);
    }

    @Override
    public void remove(K k) throws EmptyDictionaryException {
        if (!this.map.containsKey(k)) throw new EmptyDictionaryException("Key doesn't exist!\n");
        this.map.remove(k);
    }

    @Override
    public boolean contains(K k) {
        return this.map.containsKey(k);
    }

    @Override
    public V get(K k) throws EmptyDictionaryException {
        if (!this.map.containsKey(k)) throw new EmptyDictionaryException("Key doesn't exist!\n");
        return this.map.get(k);
    }

    @Override
    public void update(K k, V v) throws EmptyDictionaryException {
        if (!this.map.containsKey(k)) throw new EmptyDictionaryException("Key doesn't exist!\n");
        this.map.put(k, v);
    }

    @Override
    public Set<K> keys() {
        return this.map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        this.map.forEach((k, v) -> {
            str.append(k).append("->").append(v).append("\n");
        });
        return "SymTable:\n" + str;
    }

    @Override
    public Map<K, V> getContent(){
        return this.map;
    }
}
*/
