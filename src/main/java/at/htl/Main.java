package at.htl;

import at.htl.webuntis.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws MqttException, IOException {
        new WebUntisService("tcp://localhost:1883").start();
    }
}
