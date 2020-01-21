package at.htl.webuntis.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemCacheItemTest {

    @Test
    void setValue() throws InterruptedException {
        MemCacheItem<String> item = new MemCacheItem<>("test");
        Long oldTimestamp = item.getUpdatedAt();
        Thread.sleep(1);
        item.setValue("test2");
        assertTrue(oldTimestamp < item.getUpdatedAt());
    }
}