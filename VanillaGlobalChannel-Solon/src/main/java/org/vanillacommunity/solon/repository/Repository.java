package org.vanillacommunity.solon.repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 通用的仓库类
 * 提供基础的增删改查和主键映射
 *
 * @param <K> 主键类型
 * @param <V> 值类型
 */
public abstract class Repository<K, V> {
    private final Map<K, V> map = new ConcurrentHashMap<>();
    /**
     * 将值映射为主键的函数
     */
    private final Function<V, K> getPrimaryKey;

    public Repository(Function<V, K> getPrimaryKey) {
        this.getPrimaryKey = getPrimaryKey;
    }

    public void update(V value) {
        map.put(getPrimaryKey.apply(value), value);
    }

    public V find(K key) {
        if (key == null) return null;
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
