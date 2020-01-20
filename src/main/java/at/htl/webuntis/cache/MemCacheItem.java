package at.htl.webuntis.cache;

import java.util.Date;

public class MemCacheItem<TValue> {
    private Long updatedAt;
    private TValue value;

    public MemCacheItem(TValue value) {
        this.value = value;
        this.updatedAt = new Date().getTime();
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TValue getValue() {
        return value;
    }

    public void setValue(TValue value) {
        this.updatedAt = new Date().getTime();
        this.value = value;
    }
}
