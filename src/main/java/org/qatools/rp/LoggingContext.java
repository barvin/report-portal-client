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
