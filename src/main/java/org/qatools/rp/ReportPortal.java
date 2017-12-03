package org.qatools.rp;

import java.util.Map;

import org.qatools.rp.dto.PagedList;

import com.epam.ta.reportportal.ws.model.EntryCreatedRS;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.OperationCompletionRS;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.TestItemResource;
import com.epam.ta.reportportal.ws.model.launch.LaunchResource;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import com.epam.ta.reportportal.ws.model.log.SaveLogRQ;

import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

public interface ReportPortal {

    @RequestLine("GET /{projectName}/launch")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    PagedList<LaunchResource> getLaunches(@Param("token") String token, @Param("projectName") String projectName,
            @QueryMap Map<String, Object> parameters);

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

    @RequestLine("GET /{projectName}/item")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    PagedList<TestItemResource> getAllTestItems(@Param("token") String token, @Param("projectName") String projectName,
            @QueryMap Map<String, String> filter);

    @RequestLine("POST /{projectName}/item{parentItemId}")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    EntryCreatedRS startTestItems(@Param("token") String token, @Param("projectName") String projectName,
            @Param("parentItemId") String parentItemId, StartTestItemRQ rq);

    @RequestLine("PUT /{projectName}/item/{itemId}")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    OperationCompletionRS finishTestItem(@Param("token") String token, @Param("projectName") String projectName,
            @Param("itemId") String itemId, FinishTestItemRQ rq);

    @RequestLine("POST /{projectName}/log")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    EntryCreatedRS log(@Param("token") String token, @Param("projectName") String projectName, SaveLogRQ rq);

}
