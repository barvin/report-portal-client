package org.qatools.rp;

import java.util.List;
import java.util.Map;

import org.qatools.rp.codec.ReportPortalErrorDecoder;
import org.qatools.rp.exceptions.ReportPortalClientException;

import com.epam.ta.reportportal.ws.model.EntryCreatedRS;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.launch.LaunchResource;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;

import feign.Feign;
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
                .errorDecoder(new ReportPortalErrorDecoder()).target(ReportPortal.class, baseApiUrl);
    }

    public String startLaunch(StartLaunchRQ rq) throws ReportPortalClientException {
        EntryCreatedRS startedLaunch = reportPortal.startLaunch(accessToken, projectName, rq);
        return startedLaunch.getId();
    }

    public void finishLaunch(String launchId, FinishExecutionRQ rq) throws ReportPortalClientException {
        reportPortal.finishLaunch(accessToken, projectName, launchId, rq);
    }

    public List<LaunchResource> getLaunches(Map<String, String> parameters) throws ReportPortalClientException {
        return reportPortal.getLaunches(accessToken, projectName, parameters);
    }

    public LaunchResource getLaunch(String launchId) throws ReportPortalClientException {
        return reportPortal.getLaunch(accessToken, projectName, launchId);
    }
}
