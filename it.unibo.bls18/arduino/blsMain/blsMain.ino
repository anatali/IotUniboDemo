 /*
=======================================
 blsMain.ino
 project it.unibo.buttonLedSystem.arduino
 ButtonLed system on Arduino
 ARDUINO UNO
   pin 3 maps to interrupt 1, 
   pin 2 is interrupt 0, 
 =======================================
 */
#include "bls.h"

 /*
  * ==============================================================
  * SYSTEM: CONFIGURATION
  * 
  * This sketch handles interrupts raising the pyhisical button 
  * and sends (buttonInterruptHandler->CONTROL) to the PC the message
  * 
  *  msg( command, dispatch, arduinoLed13, connectedpc, ld1(V),0) with v=0/1
  * 
  * Moreover the sketch works like a LedServer that reads chars 
  * where 1 means button pressed
   * ==============================================================
 */
const int BUTTON_PIN = 3; 
const int  LED_PIN   = 13; 

LED13 *ledRef;
BUTTON3* buttonRef;
CONTROL* controlRef;

void configure(){
 Serial.println("------------------- configure");
static  LED13 led("ld13",LED_PIN);       //initialize an instance  
static  BUTTON3 button(BUTTON_PIN);     //initialize an instance  
static  CONTROL controller( led  );

  button.registerObs( &controller );

  ledRef     = &led;
  buttonRef  = &button;
  controlRef = &controller;
    
  attachInterrupt(1, buttonInterruptHandler, RISING); // RISING CHANGE FOLLING LOW  
  Serial.println("------------------- configure end");
}

void buttonInterruptHandler(){
  boolean ok = debouncing(); 
  if( ok ){
    int v  = digitalRead(BUTTON_PIN);
    Serial.println(   "after interrupt button=" + String(v));
    buttonRef->press();
   }
}
 
boolean debouncing(){
static unsigned long lastInterruptTime = 0; //persist beyond the function call
unsigned long interruptTime = millis(); 
//Serial.println(   "interruptTime=" + String(interruptTime) + " lastInterruptTime=" + String(lastInterruptTime) );
  if( (interruptTime - lastInterruptTime) > 100 ){
     lastInterruptTime = interruptTime;
     return true;
  }
  lastInterruptTime = interruptTime;
  return false;
}

/*
-----------------------------
LED SERVER
-----------------------------
*/
void ledServer(){
   int v = Serial.read();   //NO BLOCKING
   if( v != - 1 && v != 13 && v != 10 ){
     boolean isPressed = ( v == 49 ) ; //48 is '0' 49 is '1'
     turnTheLed( isPressed );
   }
}
void turnTheLed(boolean isPressed){
    if( isPressed ) ledRef->on();
    else  ledRef->off();
}
/*
-----------------------------
SETUP
-----------------------------
*/ 
void setup(){
  Serial.begin(9600);
  Serial.println( "--------------------------------------"  );
  Serial.println( "project it.unibo.buttonLedSystem.arduino"  );
  Serial.println( "blsMain/blsMain.ino"  );
  Serial.println( "--------------------------------------"  );
  configure();
 }

void loop(){
  ledServer();  //polling on input
  //delay(1000);   //better in debug mode
  delay(50);    // wait for 0.05 second
}
