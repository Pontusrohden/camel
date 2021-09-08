import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class Camel {




    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[]args) throws Exception {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://127.0.0.1:3306/customers");
        dataSource.setUser("root");
        dataSource.setPassword("admin");

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("dataSource", dataSource);

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

        CamelContext context = new DefaultCamelContext(registry);
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        restConfiguration


        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:queue:address")
                        .setBody(simple("insert into customer_info values(" +
                                "'${body[0]}'," +
                                "'${body[1]}'," +
                                "'${body[2]}'," +
                                "'${body[3]}'," +
                                "'${body[4]}'," +
                                "'${body[5]}',")
                        )
                        .to("jdbc:dataSource");
            }
        });
        while(true) {
            context.start();

            //ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
            //consumerTemplate.receiveBody("jdbc:dataSource", String.class);


        }
    }
}

