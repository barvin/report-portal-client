package org.qatools.rp.tests;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.qatools.rp.ReportPortalClient;
import org.qatools.rp.exceptions.ReportPortalClientException;
import org.qatools.rp.message.HashMarkSeparatedMessageParser;
import org.qatools.rp.message.MessageParser;
import org.qatools.rp.message.ReportPortalMessage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.launch.LaunchResource;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import com.epam.ta.reportportal.ws.model.log.SaveLogRQ;

import sun.misc.IOUtils;

public class LaunchTests {
    private ReportPortalClient rpClient;

    @BeforeClass
    public void beforeClass() {
        rpClient = new ReportPortalClient("", "", "");
    }

    @Test
    public void fullFlow() throws IOException {
        String launchId = startLaunch();
        String suiteItemId = startSuite(launchId);
        String testItemId = startTestItem(launchId, suiteItemId);
        logMessage();
        logMessageFileDirectly(testItemId);
        finishTestItem(testItemId);
        finishTestItem(suiteItemId);
        finishLaunch(launchId);
    }

    private void logMessage() {
        ReportPortalClient.emitLog(itemId -> {
            SaveLogRQ rq = new SaveLogRQ();
            rq.setMessage("Test log message");
            rq.setLogTime(Calendar.getInstance().getTime());
            rq.setTestItemId(itemId);
            rq.setLevel("INFO");
            return rq;
        });
    }

    private void logMessageFileDirectly(String testItemId) throws ReportPortalClientException {
        SaveLogRQ rq = new SaveLogRQ();
        rq.setMessage("Test log message with screen shot");
        rq.setLogTime(Calendar.getInstance().getTime());
        rq.setTestItemId(testItemId);
        rq.setLevel("INFO");
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
        rpClient.log(rq);
    }

    private void finishTestItem(String itemId) throws ReportPortalClientException {
        FinishTestItemRQ rq = new FinishTestItemRQ();
        rq.setEndTime(Calendar.getInstance().getTime());
        rq.setStatus("PASSED");
        rpClient.finishTestItem(itemId, rq);
    }

    private String startTestItem(String launchId, String suiteItemId) throws ReportPortalClientException {
        StartTestItemRQ rq = new StartTestItemRQ();
        rq.setLaunchId(launchId);
        rq.setName("Some test");
        rq.setDescription("Great description");
        rq.setStartTime(Calendar.getInstance().getTime());
        rq.setType("STEP");
        rq.setTags(Collections.singleton("qa_tools"));
        return rpClient.startTestItem(suiteItemId, rq).getId();
    }

    private String startSuite(String launchId) throws ReportPortalClientException {
        StartTestItemRQ rq = new StartTestItemRQ();
        rq.setLaunchId(launchId);
        rq.setName("Suite");
        rq.setStartTime(Calendar.getInstance().getTime());
        rq.setType("SUITE");
        return rpClient.startRootTestItem(rq).getId();
    }

    private void finishLaunch(String launchId) throws ReportPortalClientException {
        FinishExecutionRQ finishRq = new FinishExecutionRQ();
        finishRq.setEndTime(Calendar.getInstance().getTime());
        rpClient.finishLaunch(launchId, finishRq);
    }

    private String startLaunch() throws ReportPortalClientException {
        StartLaunchRQ startRq = new StartLaunchRQ();
        startRq.setMode(Mode.DEFAULT);
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

        LaunchResource launch = rpClient.getLaunch(launches.get(0).getLaunchId());
        assert launch.getLaunchId().equals(launches.get(0).getLaunchId());
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
