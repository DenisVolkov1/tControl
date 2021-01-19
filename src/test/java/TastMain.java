import org.util.LOg;
import org.eclipse.paho.client.mqttv3.*;

import java.util.UUID;

public class TastMain {
    public static void main(String[] args) throws MqttException, InterruptedException {

        try {
            throw new Exception("Message veri ompotant!");
        } catch (Exception e) {
            LOg.println( e );
        }

        //client();
//        for (int i = 0; i < 5; i++) {
//            LOg.println(LOg.SQL,"message");
//            Thread.sleep(200);
//        }
//
//        for (int i = 0; i < 10; i++) {
//            LOg.println(LOg.SQL,"message");
//            Thread.sleep(200);
//        }




    }

    private static void client() throws MqttException {

        String publisherId = UUID.randomUUID().toString();
        IMqttClient client = new MqttClient("tcp://localhost:1883",publisherId);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.out.println("lost connection");

            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println("topic is : "+s);
                System.out.println("message is : "+mqttMessage);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        client.connect(options);

        client.subscribe("home/temp");
    }

}
