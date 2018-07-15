package MqttClient.MqttClient;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Subscriber implements MqttCallback {

	private String topic;
	private int qos;
	private String broker;
	private String clientId;
	private MemoryPersistence persistence;
	private MqttClient client;

	public Subscriber() throws Exception {
		setDefaultValues();
	}

	public Subscriber(String broker, String topic, String clientId, int qos) throws Exception {
		super();
		this.broker = broker;
		this.topic = topic;
		this.clientId = clientId;
		this.qos = qos;
		setDefaultValues();
	}

	public Subscriber(String topic, String broker, String clientId) throws Exception {
		super();
		this.topic = topic;
		this.broker = broker;
		this.clientId = clientId;
		setDefaultValues();
	}

	private void setDefaultValues() throws Exception {
		if (topic == null || topic.length() <= 0)
			topic = "/";
		if (qos < 0 || qos > 2)
			qos = 1;
		if (broker == null || broker.length() <= 0)
			broker = "tcp://localhost:1883";
		if (clientId == null || clientId.length() <= 0)
			clientId = "guest";
		persistence = new MemoryPersistence();
		client = createClient();
	}

	private MqttClient createClient() throws Exception {
		MqttClient client;
		try {
			client = new MqttClient(broker, clientId, persistence);
			client.connect();
			client.subscribe(topic, qos);
			client.setCallback(this);

		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Error creating client");
		}
		return client;
	}

	public boolean publishMessageInCurrentTopic(String message) {
		try {
			MqttMessage mqttMessage = new MqttMessage(message.getBytes());
			if (topic == null || topic.length() <= 0)
				throw new IllegalArgumentException("Topic null");
			client.publish(topic, mqttMessage);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean publishMessageInTopic(String message, String topic) {
		try {
			MqttMessage mqttMessage = new MqttMessage(message.getBytes());
			if (topic == null || topic.length() <= 0)
				throw new IllegalArgumentException("Topic null");
			client.publish(topic, mqttMessage);
		} catch (MqttPersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void disconnect() {
		try {
			client.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getQos() {
		return qos;
	}

	public void setQos(int qos) {
		this.qos = qos;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	// Callback methods
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		System.out.println(cause.getMessage() + "\n" + cause.getCause() + "\n");
		cause.printStackTrace();

	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		String msg = new String(message.getPayload());
		System.out.println(msg);
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}

}
