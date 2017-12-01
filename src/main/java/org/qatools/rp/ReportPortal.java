package org.qatools.rp;

import java.util.List;
import java.util.Map;

import com.epam.ta.reportportal.ws.model.EntryCreatedRS;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.launch.LaunchResource;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ReportPortal {

    @RequestLine("GET /{projectName}/launch")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    List<LaunchResource> getLaunches(@Param("token") String token, @Param("projectName") String projectName,
            Map<String, String> parameters);

    @RequestLine("GET /{projectName}/launch/{launchId}")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    LaunchResource getLaunch(@Param("token") String token, @Param("projectName") String projectName,
            @Param("launchId") String launchId);

    @RequestLine("POST /{projectName}/launch")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    EntryCreatedRS startLaunch(@Param("token") String token, @Param("projectName") String projectName,
            StartLaunchRQ rq);

    @RequestLine("PUT /{projectName}/launch/{launchId}/finish")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    void finishLaunch(@Param("token") String token, @Param("projectName") String projectName,
            @Param("launchId") String launchId, FinishExecutionRQ rq);
}
