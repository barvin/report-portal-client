/*
 *     Report Portal Client
 *     Copyright (C) 2017  Maksym Barvinskyi <maksym@mbarvinskyi.com>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
