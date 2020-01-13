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

package org.qatools.rp.tests;

import org.qatools.rp.ReportPortalClient;
import org.qatools.rp.dto.generated.*;
import org.qatools.rp.exceptions.ReportPortalClientException;
import org.qatools.rp.message.HashMarkSeparatedMessageParser;
import org.qatools.rp.message.MessageParser;
import org.qatools.rp.message.ReportPortalMessage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

public class LaunchTests {
    private ReportPortalClient rpClient;

    @BeforeClass
    public void beforeClass() {
        rpClient = new ReportPortalClient("https://rp.epam.com", "maksym_barvinskyi_personal", "");
    }

    @Test
    public void fullFlow() throws IOException {
        String launchId = startLaunch();
        String suiteItemId = startSuite(launchId);
        String testItemId = startTestItem(launchId, suiteItemId);
        logMessage();
        logMessageFile(testItemId);
        finishTestItem(testItemId);
        finishTestItem(suiteItemId);
        finishLaunch(launchId);
    }

    private void logMessage() {
        ReportPortalClient.emitLog(itemId -> {
            SaveLogRQ rq = new SaveLogRQ();
            rq.setMessage("Test log message");
            rq.setTime(Calendar.getInstance().getTime());
            rq.setItemId(itemId);
            rq.setLevel(SaveLogRQ.LevelEnum.INFO);
            return rq;
        });
    }

    private void logMessageFile(String testItemId) {
        ReportPortalClient.emitLog(itemId -> {
            SaveLogRQ rq = new SaveLogRQ();
            rq.setMessage("Test log message with screen shot");
            rq.setTime(Calendar.getInstance().getTime());
            rq.setItemId(testItemId);
            rq.setLevel(SaveLogRQ.LevelEnum.INFO);
            SaveLogRQ.File file = new SaveLogRQ.File();
            file.setName("screen_shot_1.png");
            try {
                InputStream fileStream = getClass().getClassLoader().getResourceAsStream("screen_shot_1.png");
                byte[] fileContent = IOUtils.readFully(fileStream, -1, false);
                file.setContent(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            rq.setFile(file);
            return rq;
        });
    }

    private void finishTestItem(String itemId) throws ReportPortalClientException {
        FinishTestItemRQ rq = new FinishTestItemRQ();
        rq.setEndTime(Calendar.getInstance().getTime());
        rq.setStatus(FinishTestItemRQ.StatusEnum.PASSED);
        rpClient.finishTestItem(itemId, rq);
    }

    private String startTestItem(String launchId, String suiteItemId) throws ReportPortalClientException {
        StartTestItemRQ rq = new StartTestItemRQ();
        rq.setLaunchId(launchId);
        rq.setName("Some test");
        rq.setDescription("Great description");
        rq.setStartTime(Calendar.getInstance().getTime());
        rq.setType(StartTestItemRQ.TypeEnum.STEP);
        rq.setTags(Collections.singletonList("qa_tools"));
        return rpClient.startTestStepItem(suiteItemId, rq).getId();
    }

    private String startSuite(String launchId) throws ReportPortalClientException {
        StartTestItemRQ rq = new StartTestItemRQ();
        rq.setLaunchId(launchId);
        rq.setName("Suite");
        rq.setStartTime(Calendar.getInstance().getTime());
        rq.setType(StartTestItemRQ.TypeEnum.SUITE);
        return rpClient.startRootTestItem(rq).getId();
    }

    private void finishLaunch(String launchId) throws ReportPortalClientException {
        FinishExecutionRQ finishRq = new FinishExecutionRQ();
        finishRq.setEndTime(Calendar.getInstance().getTime());
        rpClient.finishLaunch(launchId, finishRq);
    }

    private String startLaunch() throws ReportPortalClientException {
        StartLaunchRQ startRq = new StartLaunchRQ();
        startRq.setMode(StartLaunchRQ.ModeEnum.DEFAULT);
        startRq.setName("Test_Launch");
        startRq.setStartTime(Calendar.getInstance().getTime());
        return rpClient.startLaunch(startRq);
    }

    @Test
    public void getLaunches() throws ReportPortalClientException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("filter.eq.name", "Test_Launch");
        parameters.put("page.page", "1");
        parameters.put("page.size", "2");
        List<LaunchResource> launches = rpClient.getLaunches(parameters);
        assert launches.size() == 2;

        LaunchResource launch = rpClient.getLaunch(launches.get(0).getId());
        assert launch.getId().equals(launches.get(0).getId());
    }

    @Test
    public void testMessageParser() throws IOException, URISyntaxException {
        MessageParser parser = new HashMarkSeparatedMessageParser();

        String absPath = Paths.get(getClass().getClassLoader().getResource("screen_shot_1.png").toURI())
                .toAbsolutePath().toString();
        ReportPortalMessage message = parser.parse("RP_MESSAGE#FILE#" + absPath + "#demo test message######33");
        Assert.assertEquals(message.getMessage(), "demo test message######33");
        Assert.assertNotNull(message, "Message should not be null");
        Assert.assertNotNull(message.getData(), "Binary data should not be null");
    }
}
