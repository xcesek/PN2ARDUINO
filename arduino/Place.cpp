#include "Arduino.h"
#include "Node.h"
#include "Place.h"
#include "Helper.h"
#include "Enums.h"



Place::Place(char* _id)
: Node(_id, placeType)
{
  tokens = 0; 
  capacity = 100000;

  helper->log(F("(place) initializing NOT ARDUINO place with ID: ")); helper->logLn(id);
}

Place::Place(char* _id, int _pin, FunctionType _functionType)
: Node(_id, placeType, _pin, _functionType)
{
  tokens = 0; 
  capacity = 100000;
  
  helper->log(F("(place) initializing place with ID: ")); helper->log(id);  helper->log(F(" -> "));
  switch (functionType) {
    case DIGITAL_IN:
      pinMode(pin, INPUT);   // not applicable -> do not initialize?
      helper->logLn(F("digitalIn"));
      break;
    case DIGITAL_OUT: 
      pinMode(pin, OUTPUT);
      helper->logLn(F("digitalOut"));
      break;
    case ANALOG_IN:
      // nothing to do - works out of the box
      helper->logLn(F("analogIn"));
      break;
    case ANALOG_OUT: 
      pinMode(pin, OUTPUT);  //yes, analog out is done through PWM
      helper->logLn(F("analogOut"));
      break;
    case SERVO:
     myservo.attach(pin);
     helper->logLn(F("servo"));
     break;
    case LED_DISPLAY: 
      //8 SEGMENT LED display need to use 8 artudino pins, so reserverd pin 2 - 9
      for(int i=2;i<10;i++)
      {
         pinMode(i,OUTPUT);
         digitalWrite(i,HIGH);
      }
      helper->logLn(F("ledDisplay"));
      break;
    default:
            helper->logLn(F("!!! not supported operation on place !!!"));
  }
}


void Place::apply()
{
  helper->log(F("(place) applying ")); helper->logLn(id);
  helper->log(F("      (place) tokens: ")); helper->logLn(tokens);
  if (!extended) return;
  
  switch (functionType) {
    case DIGITAL_IN:
      // not applicable
      break;
      
    case DIGITAL_OUT: 
      helper->log(F("      (place) thresholdRange: ")); helper->log(thresholdRangeLow);
      helper->log(F(" - ")); helper->logLn(thresholdRangeHigh);
      switch (inverseLogic) {
        case 0:
          if ((thresholdRangeLow == -1) && tokens > 0) {
            digitalWrite(pin, HIGH);
            helper->log(F("      (place) digital out ~1: ")); helper->logLn(1);
          } else if((thresholdRangeLow != -1) && (thresholdRangeLow <= tokens) && (tokens <= thresholdRangeHigh)) {
            digitalWrite(pin, HIGH);  
            helper->log(F("      (place) digital out ~2: ")); helper->logLn(1);
          } else {
            digitalWrite(pin, LOW);
            helper->log(F("      (place) digital out ~3: ")); helper->logLn(0);
          }
          break;
        case 1:
          if ((thresholdRangeLow == -1) && tokens == 0) {
            digitalWrite(pin, HIGH);
            helper->log(F("      (place) digital out ^1: ")); helper->logLn(1);
          } else if((thresholdRangeLow != -1) && ((tokens < thresholdRangeLow) || (tokens > thresholdRangeHigh))) {
            digitalWrite(pin, HIGH);  
            helper->log(F("      (place) digital out ^2: ")); helper->logLn(1);
          } else {
            digitalWrite(pin, LOW);
            helper->log(F("      (place) digital out ^3: ")); helper->logLn(0);
          }
          break;          
      }
      
      break;
      
    case ANALOG_IN:
      // not applicable
      break;
      
    case ANALOG_OUT: 
      helper->log(F("      (place) analog out: ")); helper->logLn((int)(tokens/(capacity*1.0)*255));
      analogWrite(pin, (int)(tokens/(capacity*1.0)*255));
      break;
      
    case SERVO:    
      helper->log(F("      (place) servo: ")); helper->logLn((int)(tokens/(capacity*1.0)*180));
      myservo.write((int)(tokens/(capacity*1.0)*170));
      break;

    case LED_DISPLAY: 
      helper->log(F("      (place) LED Display number: ")); helper->logLn((int)(tokens));
      /*
        1 Digital 8-Segment LED Display
        Display the number from 1-9
      */
      byte Digital[10]={0xfc,0x60,0xda,0xf2,0x66,0xb6,0xbe,0xe0,0xfe,0xf6};//the character code for number 1-9
      int i=0;
      int j; 
      for(j=0;j<8;j++)
      {  
        if(Digital[tokens]&1<<j)//COOL MAGIC with binary AND and binary LEFT SHIFT operands, but shortest way how to show right number :)(!!working!!)
          digitalWrite(9-j,LOW);
        else
          digitalWrite(9-j,HIGH);
      }
      break;      
  }
}

int Place::getTokens() {
    return tokens;
}
  
void Place::setTokens(int _tokens) {
  if (_tokens <= capacity)  tokens = _tokens;
}

void Place::addTokens(int howMany) {
  if ((tokens + howMany) <= capacity)  tokens = tokens + howMany;
}

void Place::removeTokens(int howMany) {
  if ((tokens - howMany) >= 0)  tokens = tokens - howMany;
}

void Place::setCapacity(int _capacity) {
  helper->log(F("      (place) setting capacity: ")); helper->logLn(_capacity);
  capacity = _capacity;
}

int Place::getCapacity() {
  return capacity;
}
