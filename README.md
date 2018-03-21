# report-portal-client  

[ ![Download](https://api.bintray.com/packages/mbarvinskyi/qatools/report-portal-client/images/download.svg) ](https://bintray.com/mbarvinskyi/qatools/report-portal-client/_latestVersion)  

General Java client to the Report Portal

Usage example:
```java
StartLaunchRQ rq = new StartLaunchRQ();
rq.setName("MyLaunch");
rq.setStartTime(Calendar.getInstance().getTime());
rq.setMode(Mode.DEFAULT);

ReportPortalClient reportPortal = new ReportPortalClient("https://your.rp.com", "your-project",
        "1b22cec4-6f24-4161-be5b-8aa0cac741a7");
try {
    String launchId = reportPortal.startLaunch(rq);
    // do something with launchId
} catch (ReportPortalClientException e) {
    System.out.println("Unable to start launch in ReportPortal: " + e.getMessage());
}
```  

See the list of available actions in the class `src/main/java/org/qatools/rp/ReportPortal.java`
