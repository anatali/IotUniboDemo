 /*
=======================================
 bls.h
 project it.unibo.buttonLedSystem.arduino
 ButtonLed system on Arduino
 ARDUINO UNO
   pin 3 maps to interrupt 1,
   pin 2 is interrupt 0,
 =======================================
 */
#include "Arduino.h"
/*
  * =================================================
  *  ChannelSerial: SPECIFICATION
  * =================================================
  */

class ChannelSerial{
 public:
	ChannelSerial();
	~ChannelSerial();
    void addOutput( String msg );
};

/*
  * =================================================
  *  Observer: SPECIFICATION
  * =================================================
  */
class Observer{
 public:  
    Observer();
    ~Observer();
    void updateObserver();     
    virtual void eval() = 0;
    //{ Serial.println(   "Observer eval should be never called "  );} // =0 ; //Abstract class
};
 /*
  * =================================================
  *  Observable: SPECIFICATION
  * =================================================
  */
class Observable{
 protected:
     Observer* _obs;
 public:
     Observable();
    ~Observable();
     void registerObs( Observer* obs  );
     void notify();
};
 /*
  * =================================================
  *  LED: SPECIFICATION
  * =================================================
  */
 class LED13 {
 private:
        boolean ledState;
        String  ledName;
        int  _PIN;
 public:
        LED13( String name, int PIN );
        ~LED13();
        void on();
        void off();
        void switchLed();
        boolean isOn();
        String getName();
        void blink(int time);
};
 /*
  * =================================================
  *  BUTTON: SPECIFICATION
  * =================================================
  */
 class BUTTON3 : public Observable{
 private:
        static int nInterrupt;
 public:
        BUTTON3(int PIN);
        ~BUTTON3();
        void press();   //to control the button by the program
        void isPressed();
 };
  /*
  * =================================================
  *  CONTROL: SPECIFICATION
  * =================================================
  */
 class CONTROL: public Observer{
 private:
        LED13 _led;
        ChannelSerial _channel;
 public:
        CONTROL(LED13& led );
        ~CONTROL();
        void eval();
  };
