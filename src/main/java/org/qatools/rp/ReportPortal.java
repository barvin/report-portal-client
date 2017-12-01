package org.qatools.rp;

import java.util.Map;

import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ReportPortal {

    @RequestLine("POST /{projectName}/launch")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    Map<String, String> startLaunch(@Param("token") String token, @Param("projectName") String projectName,
            StartLaunchRQ rq);

    @RequestLine("PUT /{projectName}/launch/{launchId}/finish")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    void finishLaunch(@Param("token") String token, @Param("projectName") String projectName,
                      @Param("launchId") String launchId, FinishExecutionRQ rq);
}
