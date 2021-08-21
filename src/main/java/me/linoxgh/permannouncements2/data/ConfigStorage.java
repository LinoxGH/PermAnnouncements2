package me.linoxgh.permannouncements2.data;

import org.jetbrains.annotations.Nullable;

public class ConfigStorage {
    private int interval;
    private boolean prefixEnabled;
    private String prefix;

    public int getInterval() {
        return interval;
    }
    public @Nullable String getPrefix() {
        return prefix;
    }
    public boolean isPrefixEnabled() {
        return prefixEnabled;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
    public void setPrefix(@Nullable String prefix) {
        this.prefix = prefix;
    }
    public void setPrefixEnabled(boolean prefixEnabled) {
        this.prefixEnabled = prefixEnabled;
    }
}
