package ram.learn.router;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class MyRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
      from("{{my.app.source}}").to("{{my.app.destination}}");		
	}

}
