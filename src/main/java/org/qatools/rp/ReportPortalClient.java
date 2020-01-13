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

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.qatools.rp.codec.ReportPortalErrorDecoder;
import org.qatools.rp.exceptions.ReportPortalClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.qatools.rp.dto.generated.EntryCreatedRS;
import org.qatools.rp.dto.generated.FinishExecutionRQ;
import org.qatools.rp.dto.generated.FinishTestItemRQ;
import org.qatools.rp.dto.generated.OperationCompletionRS;
import org.qatools.rp.dto.generated.StartTestItemRQ;
import org.qatools.rp.dto.generated.TestItemResource;
import org.qatools.rp.dto.generated.LaunchResource;
import org.qatools.rp.dto.generated.StartLaunchRQ;
import org.qatools.rp.dto.generated.SaveLogRQ;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Feign;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.String.valueOf;

public class ReportPortalClient {

    private String projectName;
    private String accessToken;
    private ReportPortal reportPortal;
    private final String baseApiUrl;
    private final int bufferSize;
    private final int logThreads;
    private static final MediaType OCTET_STREAM = MediaType.parse("application/octet-stream");
    private static final MediaType JSON = MediaType.parse("application/json");
    private static final int DEFAULT_BUFFER_SIZE = 10;
    private static final int DEFAULT_LOG_THREADS_COUNT = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportPortalClient.class);
    private ExecutorService executor;

    public ReportPortalClient(String baseUrl, String projectName, String accessToken) {
        this(baseUrl, projectName, accessToken, DEFAULT_BUFFER_SIZE, DEFAULT_LOG_THREADS_COUNT,
                new RetryInfo(1000, 5000, 5));
    }

    public ReportPortalClient(String baseUrl, String projectName, String accessToken, int bufferSize) {
        this(baseUrl, projectName, accessToken, bufferSize, DEFAULT_LOG_THREADS_COUNT, new RetryInfo(1000, 5000, 5));
    }

    public ReportPortalClient(String baseUrl, String projectName, String accessToken, int bufferSize, int logThreads) {
        this(baseUrl, projectName, accessToken, bufferSize, logThreads, new RetryInfo(1000, 5000, 5));
    }

    public ReportPortalClient(String baseUrl, String projectName, String accessToken, int bufferSize, int logThreads,
            RetryInfo retry) {
        this.projectName = projectName;
        this.accessToken = accessToken;
        this.baseApiUrl = (baseUrl.endsWith("/")) ? baseUrl + "api/v1" : baseUrl + "/api/v1";
        Retryer retryer = new Retryer.Default(retry.getPeriod(), retry.getMaxPeriod(), retry.getMaxAttempts());
        this.reportPortal = Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder())
                .errorDecoder(new ReportPortalErrorDecoder()).retryer(retryer).target(ReportPortal.class, baseApiUrl);
        this.bufferSize = bufferSize;
        this.logThreads = logThreads;
    }

    public String startLaunch(StartLaunchRQ rq) throws ReportPortalClientException {
        EntryCreatedRS startedLaunch = reportPortal.startLaunch(accessToken, projectName, rq);
        return startedLaunch.getId();
    }

    public void finishLaunch(String launchId, FinishExecutionRQ rq) throws ReportPortalClientException {
        reportPortal.finishLaunch(accessToken, projectName, launchId, rq);
    }

    public List<LaunchResource> getLaunches(Map<String, Object> parameters) throws ReportPortalClientException {
        return reportPortal.getLaunches(accessToken, projectName, parameters).getContent();
    }

    public LaunchResource getLaunch(String launchId) throws ReportPortalClientException {
        return reportPortal.getLaunch(accessToken, projectName, launchId);
    }

    public List<TestItemResource> getAllTestItems(Map<String, String> filter) throws ReportPortalClientException {
        return reportPortal.getAllTestItems(accessToken, projectName, filter).getContent();
    }

    public EntryCreatedRS startRootTestItem(StartTestItemRQ rq) throws ReportPortalClientException {
        return startTestItem(null, rq);
    }

    /**
     * Starts test item on the Report Portal and do not send log messages to it.
     * Use it for suites or test items that will have children.
     * If you need the log to be published under the test item, use {@code startTestStepItem(parentItemId, rq)}
     */
    public EntryCreatedRS startTestItem(String parentItemId, StartTestItemRQ rq) throws ReportPortalClientException {
        return startTestItem(parentItemId, rq, false);
    }

    /**
     * Starts test item on the Report Portal with logging enabled.
     */
    public EntryCreatedRS startTestStepItem(String parentItemId, StartTestItemRQ rq)
            throws ReportPortalClientException {
        return startTestItem(parentItemId, rq, true);
    }

    private EntryCreatedRS startTestItem(String parentItemId, StartTestItemRQ rq, boolean allowLogging)
            throws ReportPortalClientException {
        String finalParentItemId = (parentItemId != null) ? "/" + parentItemId : "";
        EntryCreatedRS result = reportPortal.startTestItem(accessToken, projectName, finalParentItemId, rq);
        if (allowLogging) {
            LoggingContext.init(result.getId(), bufferSize, this);
            executor = Executors.newFixedThreadPool(logThreads);
        }
        return result;
    }

    public OperationCompletionRS finishTestItem(String itemId, FinishTestItemRQ rq) throws ReportPortalClientException {
        if (executor != null) {
            LoggingContext.complete();
            try {
                executor.shutdown();
                executor.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                LOGGER.error("tasks interrupted");
            } finally {
                if (!executor.isTerminated()) {
                    executor.shutdownNow();
                }
            }
        }
        return reportPortal.finishTestItem(accessToken, projectName, itemId, rq);
    }

    void log(List<SaveLogRQ> rqs) {
        if (executor != null) {
            Runnable task = () -> {
                try {
                    String jsonPart = new ObjectMapper().writeValueAsString(rqs);
                    MultipartBody.Builder reqBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("json_request_part", "", RequestBody.create(JSON, jsonPart));
                    rqs.stream().filter(rq -> rq.getFile() != null)
                            .forEach(rq -> reqBuilder.addFormDataPart("binary_part", rq.getFile().getName(),
                                    RequestBody.create(getMediaType(rq), rq.getFile().getContent())));

                    Request request = new Request.Builder().header("Authorization", "bearer " + accessToken)
                            .url(baseApiUrl + "/" + projectName + "/log").post(reqBuilder.build()).build();

                    Response response = new OkHttpClient().newCall(request).execute();
                    if (!response.isSuccessful()) {
                        LOGGER.error(response.body().string());
                    }
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            };
            executor.submit(task);
        }
    }

    /**
     * Emits log message if there is any active context attached to the current thread
     *
     * @param logSupplier Log supplier. Converts current Item ID to the {@link SaveLogRQ} object
     */
    public static void emitLog(Function<String, SaveLogRQ> logSupplier) {
        final LoggingContext loggingContext = LoggingContext.THREAD_LOCAL_CONTEXT.get();
        if (null != loggingContext) {
            loggingContext.emit(logSupplier);
        }
    }

    private MediaType getMediaType(SaveLogRQ rq) {
        if (rq.getFile().getContentType() == null || MediaType.parse(rq.getFile().getContentType()) == null) {
            return OCTET_STREAM;
        }
        return MediaType.parse(rq.getFile().getContentType());
    }
}
