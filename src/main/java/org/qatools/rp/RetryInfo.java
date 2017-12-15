package org.qatools.rp;

public final class RetryInfo {
    private long period;
    private long maxPeriod;
    private int maxAttempts;

    public RetryInfo(long period, long maxPeriod, int maxAttempts) {
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxAttempts = maxAttempts;
    }

    public long getPeriod() {
        return period;
    }

    public long getMaxPeriod() {
        return maxPeriod;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}
