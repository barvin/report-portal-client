package org.qatools.rp.tests;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.qatools.rp.ReportPortalClient;
import org.qatools.rp.exceptions.ReportPortalClientException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.launch.LaunchResource;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;

public class LaunchTests {
    private ReportPortalClient rpClient;

    @BeforeClass
    public void beforeClass() {
        rpClient = new ReportPortalClient("", "","");
    }

    @Test
    public void startAndStopLaunch() throws ReportPortalClientException {
        StartLaunchRQ startRq = new StartLaunchRQ();
        startRq.setMode(Mode.DEFAULT);
        startRq.setName("Test_Launch");
        startRq.setStartTime(Calendar.getInstance().getTime());
        String launchId = rpClient.startLaunch(startRq);

        FinishExecutionRQ finishRq = new FinishExecutionRQ();
        finishRq.setEndTime(Calendar.getInstance().getTime());
        rpClient.finishLaunch(launchId, finishRq);
    }

    @Test
    public void getLaunches() throws ReportPortalClientException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("filter.eq.name", "Test_Launch");
        parameters.put("page.page", "1");
        parameters.put("page.size", "2");
        List<LaunchResource> launches = rpClient.getLaunches(parameters);
        assert launches.size() == 2;

        LaunchResource launch = rpClient.getLaunch(launches.get(0).getLaunchId());
        assert launch.getLaunchId().equals(launches.get(0).getLaunchId());
    }
}
