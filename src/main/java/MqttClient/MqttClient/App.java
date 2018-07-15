package MqttClient.MqttClient;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String input = new String();
		try {
			Subscriber subscriber = new Subscriber();
			Publisher publisher = new Publisher();
			do {
				System.out.println("Enter text: ");
				input = sc.nextLine();
				publisher.publishMessageInCurrentTopic(input);
			} while (!input.toUpperCase().equals("SALIR"));
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
