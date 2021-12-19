package a_2_consumersPattern;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare("hello", true, false, true, null);
            for (int i = 1; i <= 50; i++) {
                int number = (int) (Math.random() * 10) + 1;
                String message = String.format("%d %s", number, new String(new char[number]).replace("\0", "."));
                channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                Thread.sleep(500);
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}
