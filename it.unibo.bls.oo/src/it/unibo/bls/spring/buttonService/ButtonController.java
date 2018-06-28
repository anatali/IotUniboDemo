package it.unibo.bls.spring.buttonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController @RequestMapping(value = "/button")
public class ButtonController {
    @Autowired ButtonComponent button;

    @RequestMapping(value = "/press")
    public void press(){
        button.press();
    }
}
