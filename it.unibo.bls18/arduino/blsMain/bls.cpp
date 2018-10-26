/*
=======================================
 bls.cpp
 project it.unibo.buttonLedSystem.arduino
 ButtonLed system on Arduino
 ARDUINO UNO
   pin 3 maps to interrupt 1,
   pin 2 is interrupt 0,
 =======================================
 */
#include "bls.h"

 /*
 * =================================================
 *  ChannelSerial: IMPLEMENTATION
 * =================================================
 */
	ChannelSerial::ChannelSerial(){
		Serial.println(   "ChannelSerial construction done "  );
	}
	ChannelSerial::~ChannelSerial(){}
	void ChannelSerial::addOutput(String msg){
		Serial.println( msg  );
	}
 /*
  * =================================================
  *  Observer: IMPLEMENTATION
  * =================================================
  */
  Observer::Observer(){
    Serial.println(   "Observer construction done "  );
  }
   Observer::~Observer(){
    Serial.println(   "Observer destruction done "  );
  }
  void Observer::updateObserver(){
    Serial.println(   "Observer updateObserver  "  );
    this->eval();
  }
 /*
  * =================================================
  *  Observable: IMPLEMENTATION
  * =================================================
  */
  Observable::Observable(){
    Serial.println(   "Observable construction done "  );
  }
  Observable::~Observable(){
    Serial.println(   "Observable destruction done "  );
  }
  void Observable::registerObs( Observer* obs  ){
     _obs = obs;
  }
  void Observable::notify(){
    Serial.println(   "Observable notify "  );
    _obs->updateObserver();
  }
 /*
  * =================================================
  *  LED: IMPLEMENTATION
  * =================================================
  */
//<<constructor>> setup the LED
LED13::LED13( String name, int PIN ){
    ledName  = name;
    ledState = false;
    _PIN = PIN;
    pinMode( _PIN, OUTPUT);
    Serial.println(   "LED13 " + ledName + " construction done "  );
}
//<<destructor>>
LED13::~LED13(){
  Serial.println(   "LED13 " + ledName + " destructor done "  );
  /*nothing to destruct*/
}
//turn the LED on
void LED13::on(){
  digitalWrite(_PIN, HIGH); //set the pin HIGH and thus turn LED on
}
//turn the LED off
void LED13::off(){
  digitalWrite(_PIN, LOW); //set the pin LOW and thus turn LED off
}
//
void LED13::switchLed(){
  ledState = ! ledState;
  /*
   * Write on the led pin
   */
  digitalWrite(_PIN, ledState);
}
//
boolean LED13::isOn(){
  return ledState;
}
//
String LED13::getName(){
  return ledName;
}
//blink the LED in a period equal to paramterer -time.
void LED13::blink(int time){
        on();                   //turn LED on
        delay(time/2);  //wait half of the wanted period
        off();                  //turn LED off
        delay(time/2);  //wait the last half of the wanted period
}

 /*
  * =================================================
  *  BUTTON: IMPLEMENTATION
  * =================================================
  */
int BUTTON3::nInterrupt = 0;

BUTTON3::BUTTON3(int PIN){
    pinMode(PIN, INPUT);
    digitalWrite(PIN, HIGH); //set PULLUP : PUSH=>0
}
BUTTON3::~BUTTON3(){
    //Nothing to do
}
void BUTTON3::press(){
  Serial.println(   "BUTTON pressed "  );
  notify();
}
 /*
  * =================================================
  *  CONTROL: IMPLEMENTATION
  * =================================================
  */
   CONTROL::CONTROL(LED13& led ) : _led(led)  { //led constructor
 	 ChannelSerial _channel;
     Serial.println(   "CONTROL constructor done "  );
   }
  CONTROL::~CONTROL(){
    Serial.println(   "CONTROL destructor done "  );
    //Nothing to do
  }
  void CONTROL::eval(){
	  Serial.println(   "CONTROL eval "  );
     _led.switchLed();
     /*
      * Send a message on the output device
      */
     String msgOut = "msg( command, dispatch, arduinoLed13, connectedpc, ";
     msgOut = msgOut + _led.getName() +"("+String( _led.isOn() ) + "),0)" ;
     _channel.addOutput(   msgOut );
     //Serial.println(msgOut);
  }
