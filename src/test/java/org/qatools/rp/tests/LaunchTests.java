package org.qatools.rp.tests;

import java.util.Calendar;

import org.qatools.rp.ReportPortalClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;

public class LaunchTests {
    private ReportPortalClient rpClient;

    @BeforeClass
    public void beforeClass() {
        rpClient = new ReportPortalClient("", "","");
    }

    @Test
    public void startAndStopLaunch() {
        StartLaunchRQ startRq = new StartLaunchRQ();
        startRq.setMode(Mode.DEFAULT);
        startRq.setName("Test_Launch");
        startRq.setStartTime(Calendar.getInstance().getTime());
        String launchId = rpClient.startLaunch(startRq);

        FinishExecutionRQ finishRq = new FinishExecutionRQ();
        finishRq.setEndTime(Calendar.getInstance().getTime());
        rpClient.finishLaunch(launchId, finishRq);
    }
}
