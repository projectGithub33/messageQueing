package jmsensak;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		System.out.print("Code:");
//		String code=scanner.nextLine();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
			Connection connection=connectionFactory.createConnection();
			connection.start();
			Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//consumer evoie
			Destination destination=session.createQueue("ensak.queue");
			MessageConsumer consumer=session.createConsumer(destination);
//			 Destination destination=session.createTopic("ensak.topic");
//			MessageConsumer consumer=session.createConsumer(destination,"code='"+code+"'");
			
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					if(message instanceof TextMessage){
						TextMessage textMessage=(TextMessage) message;
						try {
							System.out.println(textMessage.getText());
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			       
				}
			});
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
