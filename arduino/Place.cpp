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
}

Place::Place(char* _id, int _pin, FunctionType _functionType)
: Node(_id, placeType, _pin, _functionType)
{
  tokens = 0; 
  capacity = 100000;
  
  Serial.print(F("(place) initializing -> ")); Serial.print(id);  Serial.print(F(" -> "));
  
  
  switch (functionType) {
    case DIGITAL_IN:
      pinMode(pin, INPUT);   // not applicable -> do not initialize?
      Serial.println(F("digitalIn"));
      break;
    case DIGITAL_OUT: 
      pinMode(pin, OUTPUT);
      Serial.println(F("digitalOut"));
      break;
    case ANALOG_IN:
      // nothing to do - works out of the box
      Serial.println(F("analogIn"));
      break;
    case ANALOG_OUT: 
      pinMode(pin, OUTPUT);  //yes, analog out is done through PWM
      Serial.println(F("analogOut"));
      break;
    case SERVO:
     myservo.attach(pin);
     Serial.println(F("servo"));
     break;
    case LED_DISPLAY: 
      //8 SEGMENT LED display need to use 8 artudino pins, so reserverd pin 2 - 9
      int i=2;
      for(i=2;i<10;i++)
      {
         pinMode(i,OUTPUT);
         digitalWrite(i,HIGH);
      }
      Serial.println(F("ledDisplay"));
      break;
  }
}


void Place::apply()
{
  Serial.print(F("(place) applying ")); Serial.println(id);
  Serial.print(F("   (place) capacity: ")); Serial.println(capacity);
  Serial.print(F("   (place) tokens: ")); Serial.println(tokens);
  Serial.print(F("   (place) functions: ")); Serial.println(functionType);
  if (!extended) return;
  
  switch (functionType) {
    case DIGITAL_IN:
      // not applicable
      break;
      
    case DIGITAL_OUT: 
      Serial.print(F("   (place) thresholdRangeLow: ")); Serial.println(thresholdRangeLow);
      Serial.print(F("   (place) thresholdRangeHigh: ")); Serial.println(thresholdRangeHigh);
      switch (inverseLogic) {
        case 0:
          if((thresholdRangeLow != -1) && (thresholdRangeLow <= tokens) && (tokens <= thresholdRangeHigh)) {
            digitalWrite(pin, HIGH);  
            Serial.print(F("   (place) digital out: ")); Serial.println(1);
          } else {
            digitalWrite(pin, LOW);
            Serial.print(F("   (place) digital out: ")); Serial.println(0);
          }
          break;
        case 1:
          if((thresholdRangeLow != -1) && ((tokens < thresholdRangeLow) || (tokens > thresholdRangeHigh))) {
            digitalWrite(pin, HIGH);  
            Serial.print(F("   (place) digital out: ")); Serial.println(1);
          } else {
            digitalWrite(pin, LOW);
            Serial.print(F("   (place) digital out: ")); Serial.println(0);
          }
          break;          
      }
      
      break;
      
    case ANALOG_IN:
      // not applicable
      break;
      
    case ANALOG_OUT: 
      Serial.print(F("   (place) analog out: ")); Serial.println((int)(tokens/(capacity*1.0)*255));
      analogWrite(pin, (int)(tokens/(capacity*1.0)*255));
      break;
      
    case SERVO:    
      Serial.print(F("   (place) servo: ")); Serial.println((int)(tokens/(capacity*1.0)*180));
      myservo.write((int)(tokens/(capacity*1.0)*180)); 
      break;

    case LED_DISPLAY: 
      Serial.print(F("   (place) LED Display number: ")); Serial.println((int)(tokens));
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
  capacity = _capacity;
}

int Place::getCapacity() {
  return capacity;
}
