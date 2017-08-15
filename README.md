opp-jenkins-plugin
=========================

## Add your web page test url and hipchat token to:
WptService.java
HipchatV1Service.java

-- this will eventually become properties so you won't need to do this.

## Build the plugin
```bash
gradle clean jpi
```

## Locating the plugin
The plugin is located in the build/libs directory of your project.  It is called opp-unplugin.hpi


## Install the plugin
1.  Login to jenkins
2.  Select "Manage Jenkins"
3.  Select "Manage Plugins"
4.  Click on the "Advanced Tab"
5.  Scroll down to "Upload Plugin" and upload your plugin
6.  Click the "restart" checkbox so Jenkins will restart when no jobs are running




