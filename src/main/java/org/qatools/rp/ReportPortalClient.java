package org.qatools.rp;

import java.util.List;
import java.util.Map;

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

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class ReportPortalClient {

    private String projectName;
    private String accessToken;
    private ReportPortal reportPortal;

    public ReportPortalClient(String baseUrl, String projectName, String accessToken) {
        this.projectName = projectName;
        this.accessToken = accessToken;

        String baseApiUrl = (baseUrl.endsWith("/")) ? baseUrl + "api/v1" : baseUrl + "/api/v1";
        this.reportPortal = Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder())
                .errorDecoder(new ReportPortalErrorDecoder()).logger(new Logger.JavaLogger().appendToFile("pr.log"))
                .logLevel(Logger.Level.FULL).target(ReportPortal.class, baseApiUrl);
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

    public EntryCreatedRS startTestItem(String parentItemId, StartTestItemRQ rq) throws ReportPortalClientException {
        String finalParentItemId = (parentItemId != null) ? "/" + parentItemId : "";
        return reportPortal.startTestItems(accessToken, projectName, finalParentItemId, rq);
    }

    public OperationCompletionRS finishTestItem(String itemId, FinishTestItemRQ rq) throws ReportPortalClientException {
        return reportPortal.finishTestItem(accessToken, projectName, itemId, rq);
    }

    public void log(SaveLogRQ rq) throws ReportPortalClientException {
        reportPortal.log(accessToken, projectName, rq);
    }
}
