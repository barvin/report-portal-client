package org.qatools.rp;

import java.util.Map;

import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
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
                .target(ReportPortal.class, baseApiUrl);
    }

    public String startLaunch(StartLaunchRQ rq) {
        Map<String, String> startedLaunch = reportPortal.startLaunch(accessToken, projectName, rq);
        return startedLaunch.get("id");
    }

    public void finishLaunch(String launchId, FinishExecutionRQ rq) {
        reportPortal.finishLaunch(accessToken, projectName, launchId, rq);
    }
}
