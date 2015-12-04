package io.github.cr3ahal0.eiydciwsyml;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by E130110Z on 04/12/15.
 */
public class Client {


    public static void main(String[] args) {
        try {
            Context initial = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory) initial.lookup("ConnectionFactory");
            Destination destination = (Destination) initial.lookup("jms/queue.mine");

            Connection connection = cf.createConnection("guest", "guest");

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer producer = session.createProducer(destination);

            connection.start();

            //TEST
            MyMessage infos = new MyMessage();
            infos.put("machineId", "100");
            infos.put("action", "login");
            infos.put("success", "true");

            ObjectMessage om = session.createObjectMessage(infos);

            producer.send(om);

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
};