package io.github.cr3ahal0.eiydciwsyml;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by E130110Z on 04/12/15.
 */
public class Client {


    public static void main(String[] args) {
        try {

            Properties env = new Properties();
            env.put("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
            env.put("java.naming.provider.url", "remote://localhost:4447");

            /*env.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
            env.put(Context.PROVIDER_URL, "remote://localhost:4447");*/

            Context initial = new InitialContext(env);
            ConnectionFactory cf = (ConnectionFactory) initial.lookup("jms/RemoteConnectionFactory");
            Destination destination = (Destination) initial.lookup("jms/queue.mine");

            //see http://docs.jboss.org/hornetq/2.2.2.Final/user-manual/en/html/configuring-transports.html#configuring-transports.acceptors
            Connection connection = cf.createConnection();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer producer = session.createProducer(destination);


            connection.start();

            //TEST : should not be successful according to the server rules
            MyMessage infos = new MyMessage();
            infos.put("machineId", "100");
            infos.put("action", "login");
            infos.put("success", "true");

            ObjectMessage om = session.createObjectMessage(infos);

            producer.send(om);

            System.out.println("Sent !");
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
};