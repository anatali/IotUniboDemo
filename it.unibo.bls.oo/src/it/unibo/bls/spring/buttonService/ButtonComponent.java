package it.unibo.bls.spring.buttonService;

import it.unibo.bls.applLogic.BlsApplicationLogic;
import it.unibo.bls.devices.ButtonAsGui;
import it.unibo.bls.interfaces.ILed;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class ButtonComponent {
    private ButtonAsGui button;

    public ButtonComponent(ILed ledService){
        JFrame frame = new JFrame("Button");
        frame.setSize(400,400);
        frame.setVisible(true);
        button = ButtonAsGui.createButton(frame, "press", new BlsApplicationLogic(ledService));
    }

    public void press(){
        this.button.actionPerformed(new ActionEvent(this, this.hashCode(), "pressed"));
    }
}
