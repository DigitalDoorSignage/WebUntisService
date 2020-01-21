package at.htl.webuntis.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemCacheTest {

    @Test
    void set() {
        MemCache<Integer, String> cache = new MemCache<>();
        cache.set(0, "test");
        assertEquals("test", cache.get(0).get().getValue());
    }

    @Test
    void settingExistingKey() throws InterruptedException {
        MemCache<Integer, String> cache = new MemCache<>();
        cache.set(0, "test");
        assertEquals("test", cache.get(0).get().getValue());
        cache.set(0, "test2");
        assertEquals("test2", cache.get(0).get().getValue());
    }
}