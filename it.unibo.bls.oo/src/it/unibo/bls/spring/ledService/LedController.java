package it.unibo.bls.spring.ledService;

import it.unibo.bls.interfaces.ILed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value = "/led")
public class LedController {
    @Autowired private ILed led;

    @RequestMapping(value = "/state")
    public Boolean state(){
        return led.getState();
    }

    @RequestMapping(value = "/turnOn")
    public void turnOn(){
        led.turnOn();
    }

    @RequestMapping(value = "/turnOff")
    public void turnOff(){
        led.turnOff();
    }
}
