package org.qatools.rp;

import feign.Body;
import feign.Feign;
import feign.Headers;
import feign.Logger;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;

import java.util.Calendar;

public class ReportPortalClient {

    interface ReportPortal {
        @RequestLine("POST /{projectName}/launch?access_token={access_token}")
        @Headers({
                "Content-Type: application/json"
        })
        @Body("%7B\"mode\": \"{mode}\"," +
                " \"name\": \"{name}\"," +
                " \"start_time\": \"{start_time}\"" +
                "%7D")
        String startLaunch(@Param("projectName") String projectName, @Param("mode") String mode,
                           @Param("name") String name, @Param("start_time") String startTime,
                           @Param("access_token") String token);
    }

    public static void main(String... args) {
        Logger logger = new Logger.JavaLogger().appendToFile("log.txt");

        ReportPortal reportPortal = Feign.builder()
                .decoder(new GsonDecoder())
                .logger(logger)
                .logLevel(Logger.Level.FULL)
                .target(ReportPortal.class, "https://rp.epam.com");

//        StartLaunchRQ rq = new StartLaunchRQ();
//        rq.setMode(Mode.DEFAULT);
//        rq.setName("It_Works");
//        rq.setStartTime(Calendar.getInstance().getTime());
        System.out.println(Calendar.getInstance().getTime().toString());
        String launchId = reportPortal.startLaunch("maksym_barvinskyi_personal", "DEFAULT",
                "It_Works", "2017-11-30T20:51:52.638Z", "");
        System.out.println(launchId);
    }
}
