package org.qatools.rp.tests;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.qatools.rp.ReportPortalClient;
import org.qatools.rp.exceptions.ReportPortalClientException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.launch.LaunchResource;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import com.epam.ta.reportportal.ws.model.log.SaveLogRQ;

public class LaunchTests {
    private ReportPortalClient rpClient;

    @BeforeClass
    public void beforeClass() {
        rpClient = new ReportPortalClient("", "","");
    }

    @Test
    public void fullFlow() throws IOException {
        String launchId = startLaunch();
        String suiteItemId = startSuite(launchId);
        String testItemId = startTestItem(launchId, suiteItemId);
        logMessage(testItemId);
        finishTestItem(testItemId);
        finishTestItem(suiteItemId);
        finishLaunch(launchId);
    }

    private void logMessage(String testItemId) throws ReportPortalClientException {
        SaveLogRQ rq = new SaveLogRQ();
        rq.setMessage("Test log message");
        rq.setLogTime(Calendar.getInstance().getTime());
        rq.setTestItemId(testItemId);
        rq.setLevel("INFO");
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
}
