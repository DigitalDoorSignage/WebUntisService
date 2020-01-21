package at.htl.webuntis.cache;

import java.util.*;

public class MemCache<TKey, TValue> {
    private Map<TKey, MemCacheItem<TValue>> items;

    public MemCache() {
        items = new HashMap<>();
    }

    public void set(TKey key, TValue value){
        if(items.containsKey(key)){
            items.get(key).setValue(value);
        }
        else {
            items.put(key, new MemCacheItem(value));
        }
    }

    public Optional<MemCacheItem<TValue>> get(TKey key){
        return items.containsKey(key)
                ? Optional.ofNullable(items.get(key))
                : Optional.empty();
    }

    public List<MemCacheItem<TValue>> getItems(){
        return new ArrayList(this.items.values());
    }

}
