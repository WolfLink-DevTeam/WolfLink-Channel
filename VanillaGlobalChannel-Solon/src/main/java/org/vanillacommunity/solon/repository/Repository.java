package org.vanillacommunity.solon.repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class Repository<K,V> {
    private final Map<K,V> map = new ConcurrentHashMap<>();
    private final Function<V,K> getPrimaryKey;
    public Repository(Function<V,K> getPrimaryKey) {
        this.getPrimaryKey = getPrimaryKey;
    }
    public void update(V value) {
        map.put(getPrimaryKey.apply(value),value);
    }
    public V find(K key) {
        return map.get(key);
    }
    public void delete(K key) {
        map.remove(key);
    }
    public Collection<V> findAll() {
        return map.values();
    }
    public void clear() {
        map.clear();
    }
}
