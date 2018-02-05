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

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.qatools.rp.codec.ReportPortalErrorDecoder;
import org.qatools.rp.exceptions.ReportPortalClientException;

import com.epam.ta.reportportal.ws.model.EntryCreatedRS;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.OperationCompletionRS;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.TestItemResource;
import com.epam.ta.reportportal.ws.model.launch.LaunchResource;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import com.epam.ta.reportportal.ws.model.log.SaveLogRQ;
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

public class ReportPortalClient {

    private String projectName;
    private String accessToken;
    private ReportPortal reportPortal;
    private final String baseApiUrl;
    private static final MediaType OCTET_STREAM = MediaType.parse("application/octet-stream");
    private static final MediaType JSON = MediaType.parse("application/json");

    public ReportPortalClient(String baseUrl, String projectName, String accessToken) {
        this(baseUrl, projectName, accessToken, new RetryInfo(1000, 5000, 5));
    }

    public ReportPortalClient(String baseUrl, String projectName, String accessToken, RetryInfo retry) {
        this.projectName = projectName;
        this.accessToken = accessToken;
        this.baseApiUrl = (baseUrl.endsWith("/")) ? baseUrl + "api/v1" : baseUrl + "/api/v1";
        Retryer retryer = new Retryer.Default(retry.getPeriod(), retry.getMaxPeriod(), retry.getMaxAttempts());
        this.reportPortal = Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder())
                .errorDecoder(new ReportPortalErrorDecoder()).retryer(retryer).target(ReportPortal.class, baseApiUrl);
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
     * If you need the log to be published under the test item, use {@code startTestItem(parentItemId, rq, true)}
     */
    public EntryCreatedRS startTestItem(String parentItemId, StartTestItemRQ rq) throws ReportPortalClientException {
        return startTestItem(parentItemId, rq, false);
    }

    public EntryCreatedRS startTestItem(String parentItemId, StartTestItemRQ rq, boolean allowLogging)
            throws ReportPortalClientException {
        String finalParentItemId = (parentItemId != null) ? "/" + parentItemId : "";
        EntryCreatedRS result = reportPortal.startTestItem(accessToken, projectName, finalParentItemId, rq);
        if (allowLogging) {
            LoggingContext.init(result.getId(), this);
        }
        return result;
    }

    public OperationCompletionRS finishTestItem(String itemId, FinishTestItemRQ rq) throws ReportPortalClientException {
        LoggingContext.complete();
        return reportPortal.finishTestItem(accessToken, projectName, itemId, rq);
    }

    public void log(SaveLogRQ rq) throws ReportPortalClientException {
        if (rq.getFile() == null) {
            reportPortal.log(accessToken, projectName, rq);
        } else {
            try {
                String jsonPart = new ObjectMapper().writeValueAsString(Collections.singletonList(rq));
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("json_request_part", "", RequestBody.create(JSON, jsonPart))
                        .addFormDataPart("binary_part", rq.getFile().getName(),
                                RequestBody.create(getMediaType(rq), rq.getFile().getContent()))
                        .build();

                Request request = new Request.Builder().header("Authorization", "bearer " + accessToken)
                        .url(baseApiUrl + "/" + projectName + "/log").post(requestBody).build();

                Response response = new OkHttpClient().newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new ReportPortalClientException(response.body().string());
                }
            } catch (IOException e) {
                throw new ReportPortalClientException(e);
            }
        }

    }

    /**
     * Emits log message if there is any active context attached to the current thread
     *
     * @param logSupplier Log supplier. Converts current Item ID to the {@link SaveLogRQ} object
     */
    public static boolean emitLog(Function<String, SaveLogRQ> logSupplier) {
        final LoggingContext loggingContext = LoggingContext.THREAD_LOCAL_CONTEXT.get();
        if (null != loggingContext) {
            loggingContext.emit(logSupplier);
            return true;
        }
        return false;
    }

    private MediaType getMediaType(SaveLogRQ rq) {
        if (rq.getFile().getContentType() == null || MediaType.parse(rq.getFile().getContentType()) == null) {
            return OCTET_STREAM;
        }
        return MediaType.parse(rq.getFile().getContentType());
    }
}
