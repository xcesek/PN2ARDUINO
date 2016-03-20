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
  
  Serial.print("(place) initializing -> "); Serial.print(id);  Serial.print(" -> ");
  
  
  switch (functionType) {
    case DIGITAL_IN:
      pinMode(pin, INPUT);   // not applicable -> do not initialize?
      Serial.println("digitalIn");
      break;
    case DIGITAL_OUT: 
      pinMode(pin, OUTPUT);
      Serial.println("digitalOut");
      break;
    case ANALOG_IN:
      // nothing to do - works out of the box
      Serial.println("analogIn");
      break;
    case ANALOG_OUT: 
      pinMode(pin, OUTPUT);  //yes, analog out is done through PWM
      Serial.println("analogOut");
      break;
    case SERVO:
     myservo.attach(pin);
     Serial.println("servo");
     break;
  }
}


void Place::apply()
{
  Serial.print("(place) applying "); Serial.println(id);
  Serial.print("   (place) capacity: "); Serial.println(capacity);
  Serial.print("   (place) tokens: "); Serial.println(tokens);
  Serial.print("   (place) funtions: "); Serial.println(functionType);
  if (!extended) return;
  
  switch (functionType) {
    case DIGITAL_IN:
      // not applicable
      break;
      
    case DIGITAL_OUT: 
      Serial.print("   (place) digital out: "); Serial.println(tokens > 0);
      if (tokens > 0)  digitalWrite(pin, HIGH);  
      else digitalWrite(pin, LOW);
      break;
      
    case ANALOG_IN:
      // not applicable
      break;
      
    case ANALOG_OUT: 
      Serial.print("   (place) analog out: "); Serial.println((int)(tokens/(capacity*1.0)*255));
      analogWrite(pin, (int)(tokens/(capacity*1.0)*255));
      break;
      
    case SERVO:    
      Serial.print("   (place) servo: "); Serial.println((int)(tokens/(capacity*1.0)*180));  
      myservo.write((int)(tokens/(capacity*1.0)*180)); 
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
