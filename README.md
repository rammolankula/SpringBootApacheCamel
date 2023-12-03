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

##### Processor: 
<pre>
  &#8594; Data Conversions ( Text --> JSON, XML -> JSON, log -> HTML ..etc)
  &#8594; Data Calculations 
</pre>
<pre>
OpenType:  ctrl+shift+T
-API Details--
1. Processor(I) : This interface indicates data modificiation/operations are going to be
               applied on source data. It is having one abstract method process(Exchange).
=> Anonymous Inner class
new InterfaceName() {
  //override abstract method
}
 &#8594; Lambda Expression:  
 Interface ob = (methodParam) -> { methodBody };

 &#8594; Exchange(I) : It supports reading source Message using method getIn() and
             supports writing destination message using method getOut()/getMessage().


 &#8594; Message(I) : It indicates data source/destination {file}.
 Message contains mainly two parts.
 Header-> File Information (fileName, file Extension..etc)
 Body  -> Actual data inside File
</pre>
@Component
public class MyRouterNew extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:F:/source")
		.process(new Processor() {
			public void process(Exchange exchange)
					throws Exception 
			{
				// read input message given by source
				Message input = exchange.getIn();
				
				//read body as String from Input message
				String data = input.getBody(String.class);
				
				//operation
				StringTokenizer str = new StringTokenizer(data, ",");
				String eid = str.nextToken();
				String ename = str.nextToken();
				String esal = str.nextToken();
				
				//output (data)
				String dataModified = "{eid:"+eid+",ename:"+ename+",esal:"+esal+"}";
				
				// read output message Reference
				//Message output = exchange.getOut();
				Message output = exchange.getMessage();
				
				//set data to output
				output.setBody(dataModified);
			}
		})
		.to("file:F:/desti?fileName=emp.json");
	}
}
-----TASK--------------
https://www.facebook.com/groups/thejavatemple
Task#1 
student.txt
85,RAM,MS,200.00

student.xml
<student>
 <sid>85</sid>
 <sname>RAM</sname>
 <course>MS</course>
 <fee>200.00</fee>
</student>

=====Ex#2 Processor using Lambda Expression ===========
package in.nareshit.raghu.router;

import java.util.StringTokenizer;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRouterNew extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("file:F:/source")
		.process(
				(exch)-> {
					// read input message given by source
					Message input = exch.getIn();

					//read body as String from Input message
					String data = input.getBody(String.class);

					//operation
					StringTokenizer str = new StringTokenizer(data, ",");
					String eid = str.nextToken();
					String ename = str.nextToken();
					String esal = str.nextToken();

					//output (data)
					String dataModified = "{eid:"+eid+",ename:"+ename+",esal:"+esal+"}";

					// read output message Reference
					//Message output = exchange.getOut();
					Message output = exch.getMessage();

					//set data to output
					output.setBody(dataModified);
				}
				)
		.to("file:F:/desti?fileName=emp.json");
	}
}

