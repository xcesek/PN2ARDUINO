#include "Arduino.h"
#include "Transition.h"
#include "Arc.h"
#include "Helper.h"
#include "Enums.h"

Transition::Transition(char* _id)
: Node(_id, transitionType)
{  
  analogThreshold = -1;
  analogThresholdRangeLow = -1;
  analogThresholdRangeHigh = -1;
}


Transition::Transition(char* _id, int _pin, FunctionType _functionType)
: Node(_id, transitionType, _pin, _functionType)
{ 
  analogThreshold = -1;
  analogThresholdRangeLow = -1;
  analogThresholdRangeHigh = -1;
  
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
  // delay ?
  
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
      if((analogThreshold != -1) && (readValue > analogThreshold)) externalTriggerActive = 1;
      if((analogThresholdRangeLow != -1) && (analogThresholdRangeLow < readValue) && (readValue < analogThresholdRangeHigh)) externalTriggerActive = 1;
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

void Transition::setAnalogThreshold(int _analogThreshold) {
  analogThreshold = _analogThreshold;
  Serial.print("(transition) setting analogThreshold: "); Serial.println(analogThreshold);
}

void Transition::setAnalogThresholdRange(int _analogThresholdRangeLow, int _analogThresholdRangeHigh) {
  analogThresholdRangeLow = _analogThresholdRangeLow;
  analogThresholdRangeHigh = _analogThresholdRangeHigh;
  Serial.print("(transition) setting analogThresholdRange: "); Serial.print(analogThresholdRangeLow);Serial.print(" - ");Serial.println(analogThresholdRangeHigh);
}


