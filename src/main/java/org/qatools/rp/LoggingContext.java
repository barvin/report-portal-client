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

import java.util.function.Function;

import org.qatools.rp.exceptions.ReportPortalClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.ta.reportportal.ws.model.log.SaveLogRQ;

public class LoggingContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingContext.class);
    static final ThreadLocal<LoggingContext> THREAD_LOCAL_CONTEXT = new ThreadLocal<>();

    /**
     * Initializes new logging context and attaches it to current thread
     *
     * @param itemId        Test Item ID
     * @param client        Client of ReportPortal
     * @return New Logging Context
     */
    public static LoggingContext init(String itemId, final ReportPortalClient client) {
        LoggingContext context = new LoggingContext(itemId, client);
        THREAD_LOCAL_CONTEXT.set(context);
        return context;
    }

    public static void complete() {
        if (THREAD_LOCAL_CONTEXT.get() != null) {
            THREAD_LOCAL_CONTEXT.set(null);
        }
    }

    private final String itemId;
    private final ReportPortalClient client;

    private LoggingContext(String itemId, final ReportPortalClient client) {
        this.itemId = itemId;
        this.client = client;
    }

    /**
     * Emits the log to RP
     *
     * @param idToRqFunction    Function that takes in the Test Item ID and produces the log request entity
     */
    public void emit(Function<String, SaveLogRQ> idToRqFunction) {
        try {
            this.client.log(idToRqFunction.apply(this.itemId));
        } catch (ReportPortalClientException e) {
            LOGGER.error("Emit log failed.", e);
        }
    }

}
