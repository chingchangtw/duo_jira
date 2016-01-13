# Overview

**duo_jira** - Duo two-factor authentication for JIRA.

# Usage

Standard offical Documentation: <https://www.duosecurity.com/docs/jira> from Duo Security.

# Integration with JIRA Connect mobile APP
If you want to integrate with JIRA Connect mobile APP, PLS follow these additional steps. JIRA will send Duo Security authentication request in background and default authentication factor is Duo Push.

###	Install 3rd party JAR
Copy the 3rd party commons-net-3.3.jar from http://archive.apache.org/dist/commons/net/binaries/ into the JIRA lib directory.

```sh
cp etc/commons-net-3.3.jar $JIRA_DIR/atlassian-jira/WEB-INF/lib</b>
```

###	Re-build customized Seraph Filter 
Re-build your own customized Seraph Filter 
```sh
git clone git://github.com/duosecurity/duo_java.git
cd duo_java/duo_seraph_filter
atlas-mvn package
```
After this step, the built JAR can be copied to the JIRA lib directory as described in Install the duo web JAR.

### Bypass Duo to some APIs
You need to add additional XML to bypass Duo in section of "unprotected.dirs". PLS refer to a sample web.xml in <b>duo_java/duo_seraph_filter/doc</b> in detail.
```sh
	<init-param>
		<param-name>unprotected.dirs</param-name>
		<param-value>/plugins/servlet/streams /sr/jira.issueviews:searchrequest 
		/secure/RunPortlet /rpc/soap /plugins/servlet/streams /plugins/servlet/applinks/whoami 
		/download/resources/com.duosecurity.jira.plugins.duo-twofactor:resources/ 
		/download/resources/com.duosecurity.confluence.plugins.duo-twofactor:resources/ 
		/rest/gadget/1.0/login /rpc/xmlrpc
		</param-value>
	</init-param>
```

### IP Whitelist and Blacklist
You can bypass Duo by a range of IPs or force to use Duo Security. PLS refer to a sample web.xml in <b>duo_java/duo_seraph_filter/doc</b> in detail.
```sh
    <init-param>	    
        <param-name>unprotected.ips</param-name>
        <param-value>10.0.0.0/8 172.16.0.0/16
        </param-value>
    </init-param>
    <init-param>
        <param-name>protected.ips</param-name>
        <param-value>10.31.31.105
        </param-value>
    </init-param>
```
###Priority Rule
The priority of rule is <b>1. unprotected.dirs</b> > <b>2. protected.ips</b> > <b>3. unprotected.ips</b>. For instance, a connection from 10.31.31.105 will force to have Duo Security but 10.31.31.104 will bypass.
 

# Support
Some JARs are customized. PLS don't reach out Duo Security.


Have fun!
