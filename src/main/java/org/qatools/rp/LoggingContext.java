/*
 * Copyright 2019 Maksym Barvinskyi <maksym@mbarvinskyi.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qatools.rp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.qatools.rp.dto.generated.SaveLogRQ;

class LoggingContext {
    static final ThreadLocal<LoggingContext> THREAD_LOCAL_CONTEXT = new ThreadLocal<>();

    /**
     * Initializes new logging context and attaches it to current thread
     *
     * @param itemId        Test Item ID
     * @param bufferSize    How many records keep in memory before sending to RP
     * @param client        Client of ReportPortal
     */
    static void init(String itemId, int bufferSize, final ReportPortalClient client) {
        LoggingContext context = new LoggingContext(itemId, bufferSize, client);
        THREAD_LOCAL_CONTEXT.set(context);
    }

    static void complete() {
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
    void emit(Function<String, SaveLogRQ> idToRqFunction) {
        bufferedLog.add(idToRqFunction.apply(itemId));
        if (bufferedLog.size() >= bufferSize) {
            sendLogAndEmptyBuffer();
        }
    }

    private void sendLogAndEmptyBuffer() {
        try {
            this.client.log(new ArrayList<>(bufferedLog));
        } finally {
            bufferedLog.clear();
        }
    }

}
