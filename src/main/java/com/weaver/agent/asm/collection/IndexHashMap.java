package com.weaver.agent.asm.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author w
 * 支持索引的HashMap
 * @date 2020-02-22 17:18
 */

public class IndexHashMap<K,V> extends LinkedHashMap<K,V> {
    public LinkedList<K> index = new LinkedList<>();

    @Override
    public V put(K key, V value) {
        index.add(key);
        return super.put(key, value);
    }

    @Override
    public V remove(Object key) {
        index.remove(getIndex((K) key).intValue());
        return super.remove(key);
    }

    public V getByIndex(int i){
        return super.get(index.get(i));
    }

    public Integer getIndex(K key){
        return index.indexOf(key);
    }

    public V put(Integer in,K key, V value){
        index.add(in,key);
        return super.put(key,value);
    }

}
