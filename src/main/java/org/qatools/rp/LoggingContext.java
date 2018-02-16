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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.qatools.rp.exceptions.ReportPortalClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.ta.reportportal.ws.model.log.SaveLogRQ;

public class LoggingContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingContext.class);
    private static final int DEFAULT_BUFFER_SIZE = 10;
    static final ThreadLocal<LoggingContext> THREAD_LOCAL_CONTEXT = new ThreadLocal<>();

    public static LoggingContext init(String itemId, final ReportPortalClient client) {
        return init(itemId, DEFAULT_BUFFER_SIZE, client);
    }

    /**
     * Initializes new logging context and attaches it to current thread
     *
     * @param itemId        Test Item ID
     * @param bufferSize    How many records keep in memory before sending to RP
     * @param client        Client of ReportPortal
     * @return New Logging Context
     */
    public static LoggingContext init(String itemId, int bufferSize, final ReportPortalClient client) {
        LoggingContext context = new LoggingContext(itemId, bufferSize, client);
        THREAD_LOCAL_CONTEXT.set(context);
        return context;
    }

    public static void complete() {
        if (THREAD_LOCAL_CONTEXT.get() != null) {
            if (!THREAD_LOCAL_CONTEXT.get().bufferedLog.isEmpty()) {
                THREAD_LOCAL_CONTEXT.get().sendLogAndEmptyBuffer();
            }
            THREAD_LOCAL_CONTEXT.set(null);
        }
    }

    private final String itemId;
    private final ReportPortalClient client;
    private final int bufferSize;
    private List<SaveLogRQ> bufferedLog;

    private LoggingContext(String itemId, int bufferSize, final ReportPortalClient client) {
        this.itemId = itemId;
        this.client = client;
        this.bufferedLog = new ArrayList<>();
        this.bufferSize = bufferSize;
    }

    /**
     * Emits the log to RP
     *
     * @param idToRqFunction    Function that takes in the Test Item ID and produces the log request entity
     */
    public void emit(Function<String, SaveLogRQ> idToRqFunction) {
        bufferedLog.add(idToRqFunction.apply(itemId));
        if (bufferedLog.size() >= bufferSize) {
            sendLogAndEmptyBuffer();
        }
    }

    private void sendLogAndEmptyBuffer() {
        try {
            this.client.log(bufferedLog);
        } catch (ReportPortalClientException e) {
            LOGGER.error("Emit log failed.", e);
        } finally {
            bufferedLog.clear();
        }
    }

}
