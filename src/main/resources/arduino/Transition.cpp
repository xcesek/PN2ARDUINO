#include "Arduino.h"
#include "Transition.h"
#include "Arc.h"
#include "Helper.h"
#include "Enums.h"

Transition::Transition(char* _id)
: Node(_id, transitionType)
{  
  earliestFiringTime = -1;
  latestFiringTime = -1;
  applyDelay = 0;
}


Transition::Transition(char* _id, int _pin, FunctionType _functionType)
: Node(_id, transitionType, _pin, _functionType)
{ 
  earliestFiringTime = 0;
  latestFiringTime = 0;
  applyDelay = 0;
  Serial.print("(transition) initializing -> "); Serial.print(id);  Serial.print(" -> ");
  
  switch (functionType) {
    case DIGITAL_IN:
      pinMode(pin, INPUT);
      Serial.println("digitalIn");
      break;
    case DIGITAL_OUT: 
      // what?
      pinMode(pin, OUTPUT);
      Serial.println("digitalOut");
      break;
    case ANALOG_IN:
      // nothing to do - works out of the box
      Serial.println("analogIn");
      break;
    case ANALOG_OUT: 
      // what?
      pinMode(pin, OUTPUT);  //yes, analog out is done through PWM
      Serial.println("analogOut");
      break;
  }
}

void Transition::fire()
{
  Serial.print("(transition) firing "); Serial.println(id);
  Serial.print("   (transition) funtions: "); Serial.println(functionType);
  Serial.print("   (transition) connectedArcsCount: "); Serial.println(connectedArcsCount);
  
  for (int i = 0; i < connectedArcsCount; i++) {
    Node *source = connectedArcs[i]->getSource();
    if (source->getNodeType() == placeType) {
      Place *place = (Place*) source;
      place->removeTokens(connectedArcs[i]->getMultiplicity());
    }
  }

  // delay
  if (earliestFiringTime != latestFiringTime) {
    delay(random(earliestFiringTime, latestFiringTime));
  } else {
    delay(earliestFiringTime);
  }
  
  for (int i = 0; i < connectedArcsCount; i++) {
    Node *destination = connectedArcs[i]->getDestination();
    if (destination->getNodeType() == placeType) {
      Place *place = (Place*) destination;
      place->addTokens(connectedArcs[i]->getMultiplicity());
    }
  }
}

int Transition::isEnabled()
{
  Serial.print("(transition) isEnabled? "); Serial.println(id);

  // internal condition
  int internalTriggerActive = 1;
  for (int i = 0; i < connectedArcsCount; i++) {
    Node *source = connectedArcs[i]->getSource();
    if (source->getNodeType() == placeType) {
      Place *place = static_cast<Place*>(source);
      if (place->getTokens() < connectedArcs[i]->getMultiplicity()) internalTriggerActive = 0;
    }
  }
  Serial.print("(transition) internal : "); Serial.println(internalTriggerActive);
  if (!extended) {
    return internalTriggerActive; 
  }
  
  // external guard
  int readValue;
  int externalTriggerActive = 0;
  switch (functionType) {
    case DIGITAL_IN:
      readValue = digitalRead(pin);
      Serial.print("   (transition) digital read value: "); Serial.println(readValue);
      if(readValue == 1) externalTriggerActive = 1;
      break;
      
    case DIGITAL_OUT: 
      // what?
      break;
      
    case ANALOG_IN:
      readValue = analogRead(pin);
      Serial.print("   (transition) analog read value: "); Serial.println(readValue);
      if((thresholdRangeLow != -1) && (thresholdRangeLow <= readValue) && (readValue <= thresholdRangeHigh)) externalTriggerActive = 1;
      break;
      
    case ANALOG_OUT: 
      // what?
      break;
  }
  Serial.print("   (transition) isActive: "); Serial.println(internalTriggerActive && externalTriggerActive);
  return internalTriggerActive && externalTriggerActive;
}

void Transition::setConnectedArcs(Arc **arcs) {
  connectedArcs = arcs;
}

void Transition::setConnectedArcsCount(int count) {
  connectedArcsCount = count;
}

Arc **Transition::getConnectedArcs() {
  return connectedArcs;
}

int Transition::getConnectedArcsCount() {
  return connectedArcsCount;
}

void Transition::setDelay(int _earliestFiringTime, int _latestFiringTime) {
  earliestFiringTime =  _earliestFiringTime;
  latestFiringTime = _latestFiringTime;
}

void Transition::setApplyDelay(int _applyDelay) {
  applyDelay = _applyDelay;
}
