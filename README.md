# SpringBootApacheCamel
<pre>
&#8594; It is a open source and Integration F/w used to transfer data between systems
&#8594; Supports different protocol, HTTP, FTP, FILE, JMS..etc
&#8594; Supports different languages like Hadoop, PHP, Python..etc
&#8594; We can trasfer data like XML, JSON, Text, ..etc
</pre>

<pre>
a) Routing  : Transfer data from source to distination
b) Filering : Check condition before trasfer
c) Processing : Modify/conversion,calcuations..etc(XML->JSON)
</pre>

###### Camel coding is done using: EIP => Enterprise Integration Pattern.
######  Spring boot supports auto-configuration with Apache Camel using a starter:camel-spring-boot-starter
<pre>
#1. Create one Spring Starter Project
Name : SpringBoot2ApacheCamelEx
Dep  : Apache Camel
#2. application.properties
camel.springboot.main-run-controller=true
#3. Router class
</pre>

<pre>
@Component
public class MyDataRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:F:\\source").to("file:F:\\desti");
	}
}
</pre>
###### Run main class
<pre>
Execution
1. Create a Folder 'source' in F drive
2. Create a Folder 'desti' in F drive
3. Place files in source folder, those are sent to desti folder
4. In source folder files are exist as backup folder .camel
EIP Patternns-
#1. from("file:G:\\source").to("file:G:\\desti");
 => It indicates sending files from F:/source folder to F:/desti folder
 => All files are taken as backup in source as .camel folder
 => We can send same file again with new data, that overrides at destination.
#2. from("file:G:\\source?noop=true").to("file:G:\\desti");
=> NOOP = No Operation to Override Program
=> No backup into .camel in source folder
=> Avoids sending duplicates 
#3. from("{{my.app.source}}").to("{{my.app.desti}}");
=> here {{my.app.source}} indicates dynamic location is taken from propreties file
</pre>
<pre>
application.properties
camel.springboot.main-run-controller=true
my.app.source=file:G://source
my.app.desti=file:G://desti
</pre>
##### Router
<pre>
@Component
public class MyDataRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("{{my.app.source}}").to("{{my.app.desti}}");
	}
}
</pre>
