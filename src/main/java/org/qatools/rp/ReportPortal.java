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

import java.util.List;
import java.util.Map;

import org.qatools.rp.dto.PagedList;

import org.qatools.rp.dto.generated.EntryCreatedRS;
import org.qatools.rp.dto.generated.FinishExecutionRQ;
import org.qatools.rp.dto.generated.FinishTestItemRQ;
import org.qatools.rp.dto.generated.OperationCompletionRS;
import org.qatools.rp.dto.generated.StartTestItemRQ;
import org.qatools.rp.dto.generated.TestItemResource;
import org.qatools.rp.dto.generated.LaunchResource;
import org.qatools.rp.dto.generated.StartLaunchRQ;
import org.qatools.rp.dto.generated.SaveLogRQ;

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
    EntryCreatedRS startTestItem(@Param("token") String token, @Param("projectName") String projectName,
            @Param("parentItemId") String parentItemId, StartTestItemRQ rq);

    @RequestLine("PUT /{projectName}/item/{itemId}")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    OperationCompletionRS finishTestItem(@Param("token") String token, @Param("projectName") String projectName,
            @Param("itemId") String itemId, FinishTestItemRQ rq);

    @RequestLine("POST /{projectName}/log")
    @Headers({ "Content-Type: application/json", "Authorization: bearer {token}" })
    EntryCreatedRS log(@Param("token") String token, @Param("projectName") String projectName, SaveLogRQ rq);

    @RequestLine("POST /{projectName}/log")
    @Headers({ "Content-Type: multipart/form-data", "Authorization: bearer {token}" })
    void log(@Param("token") String token, @Param("projectName") String projectName,
            @Param("json_request_part") List<SaveLogRQ> rqs, @Param("binary_part") byte[] file);

}
