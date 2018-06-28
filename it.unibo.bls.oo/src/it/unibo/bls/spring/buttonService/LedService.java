package it.unibo.bls.spring.buttonService;

import it.unibo.bls.interfaces.ILed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service public class LedService implements ILed {
    @Value("${LED_SERVICE_URI}") private String LED_SERVICE_URI;

    private final RestTemplate rt;

    public LedService(RestTemplateBuilder rb) {
        this.rt = rb.build();
    }

    @Override
    public void turnOn() {
        this.rt.getForEntity(LED_SERVICE_URI + "/turnOn", Void.class);
    }

    @Override
    public void turnOff() {
        this.rt.getForEntity(LED_SERVICE_URI + "/turnOff", Void.class);
    }

    @Override
    public boolean getState() {
        return this.rt.getForEntity(LED_SERVICE_URI + "/state", Boolean.class).getBody();
    }
}
