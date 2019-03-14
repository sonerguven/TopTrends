package com.sonerguven.toptrends.toptrends.Helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vb53023 on 21.12.2016.
 */

public class SGSession {
    private static Map<String,Object> itemCollection = new HashMap<String, Object>();
    public static Map<String,Object> getItemCollection() {
        synchronized (itemCollection) {
            if (itemCollection == null) {
                itemCollection = new HashMap<String, Object>();
            }
        }
        return itemCollection;
    }

    public static synchronized <T> void Add(String key, T item){
        itemCollection.put(key,item);
    }

    public static Object Get(String key)
    {
        try{
            return itemCollection.get(key);
        }
        catch (Exception i)
        {
            return null;
        }
    }

    public static void Remove(String key){
        try{
            itemCollection.remove(key);
        }
        catch (Exception i)
        {
            //silosi
        }
    }

    public static class Ref<T> {

        private T value;

        public Ref(T value) {
            this.value = value;
        }
        public Ref() {
        }

        public T get() {
            return value;
        }

        public synchronized void set(T anotherValue) {
            value = anotherValue;
        }

        @Override
        public String toString() {
            return value.toString();
        }

        @Override
        public boolean equals(Object obj) {
            return value.equals(obj);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }
}
