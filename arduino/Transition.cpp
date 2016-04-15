#include "Arduino.h"
#include "Transition.h"
#include "Arc.h"
#include "Helper.h"
#include "Enums.h"

Transition::Transition(char* _id)
: Node(_id, transitionType)
{  
  earliestFiringTime = 0;
  latestFiringTime = 0;
  applyDelay = 0;
  priority = 0;

  Serial.print(F("(transition) initializing NOT ARDUINO transition with ID: ")); Serial.println(id);
  Serial.print(F("      (transition) will be using function: ")); Serial.println(functionType);
}


Transition::Transition(char* _id, int _pin, FunctionType _functionType)
: Node(_id, transitionType, _pin, _functionType)
{ 
  earliestFiringTime = 0;
  latestFiringTime = 0;
  applyDelay = 0;
  priority = 0;

  Serial.print(F("(transition) initializing transition with ID: ")); Serial.print(id);  Serial.print(" -> ");
  
  switch (functionType) {
    case DIGITAL_IN:
      pinMode(pin, INPUT);
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
    default:
      Serial.println(F("!!! not supported operation on transition !!!"));
  }

  Serial.print(F("      (transition) will be using function: ")); Serial.println(functionType);
}

void Transition::fire()
{
  Serial.print(F("(transition) firing transition with ID: ")); Serial.println(id);

  // take token(s) from all in places
  for (int i = 0; i < connectedArcsCount; i++) {
    Node *source = connectedArcs[i]->getSource();
    if (source->getNodeType() == placeType) {
      Place *place = (Place*) source;
      place->removeTokens(connectedArcs[i]->getMultiplicity());
    }
  }

  // pre delay action
  if (extended) {
    takePreDelayAction();
  } else {
    Serial.println(F("      (transition) no pre delay action as no extended transition"));
  }

  // delay
  if (applyDelay || extended == 0) {  // if not extended, ignore applyDelay flag
    if (earliestFiringTime != latestFiringTime) {
      int randomTime = random(earliestFiringTime, latestFiringTime);
      Serial.print(F("      (transition) waiting for ")); Serial.print(randomTime); Serial.println(F(" millis"));
      delay(randomTime);
    } else {
      Serial.print(F("      (transition) waiting for ")); Serial.print(earliestFiringTime); Serial.println(F(" millis"));
      delay(earliestFiringTime);
    }
  }

  // post delay action
  if (extended) {
      takePostDelayAction();
    } else {
      Serial.println(F("      (transition) no post delay action as no extended transition"));
  }

  // put token(s) to all out places
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
  Serial.print(F("(transition) checking firability on transition with ID: ")); Serial.println(id);

  // internal condition
  int internalTriggerActive = 1;
  for (int i = 0; i < connectedArcsCount; i++) {
    Node *source = connectedArcs[i]->getSource();
    if (source->getNodeType() == placeType) {
      Place *place = static_cast<Place*>(source);
      if (place->getTokens() < connectedArcs[i]->getMultiplicity()) internalTriggerActive = 0;
    }
    Node *destination = connectedArcs[i]->getDestination();
    if (destination->getNodeType() == placeType) {
      Place *place = static_cast<Place*>(destination);
      if ( connectedArcs[i]->getMultiplicity() > ( place->getCapacity() - place->getTokens() ) ) {
        internalTriggerActive = 0;
      }
    }
  }
  Serial.print(F("      (transition) internal condition: ")); Serial.println(internalTriggerActive);

  // if not extended finish here, as no arduino pin is associated with transition instance
  if (!extended) {
    Serial.print(F("      (transition) not assoc with arduino, returning ")); Serial.println(internalTriggerActive);
    return internalTriggerActive; 
  }
  
  // external condition - guard
  int readValue;
  int externalTriggerActive = 0;
  switch (functionType) {
    case DIGITAL_IN:
      readValue = digitalRead(pin);
      Serial.print(F("      (transition) DIGITAL read value: ")); Serial.println(readValue);
      if(readValue == 1) externalTriggerActive = 1;
      break;
      
    case DIGITAL_OUT: 
      // not applicable in isEnabled() method, bud in fire() method
      break;
      
    case ANALOG_IN:
      readValue = analogRead(pin);
      Serial.print(F("      (transition) ANALOG read value: ")); Serial.println(readValue);
      switch(inverseLogic){
        case 0:
          if((thresholdRangeLow <= readValue) && (readValue <= thresholdRangeHigh))
            externalTriggerActive = 1;
          break;

        case 1:
          if(((thresholdRangeLow > readValue) || (readValue > thresholdRangeHigh)))
            externalTriggerActive = 1;
          break;
      }     
      break;
      
    case ANALOG_OUT: 
      // not applicable in isEnabled() method, bud in fire() method
      break;
     default:
      Serial.println("      (transition) Unsupported operation processed in isActive method");
  }

  Serial.print(F("      (transition) isActive: ")); Serial.println(internalTriggerActive && externalTriggerActive);
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
  Serial.print(F("      (transition) trying to set delay: "));
  Serial.print(_earliestFiringTime);Serial.print(" - ");Serial.println(_latestFiringTime);
  _earliestFiringTime = max(_earliestFiringTime, 0);
  _latestFiringTime = max(_latestFiringTime, 0);

  if (_earliestFiringTime < _latestFiringTime) {
    earliestFiringTime =  _earliestFiringTime;
    latestFiringTime = _latestFiringTime;
  } else { // try to be proactive, user changed values by mistake
    earliestFiringTime =  _latestFiringTime;
    latestFiringTime = _earliestFiringTime;
  }

  Serial.print(F("      (transition) delay set to: "));
  Serial.print(earliestFiringTime);Serial.print(" - ");Serial.println(latestFiringTime);

}

void Transition::setApplyDelay(int _applyDelay) {
  Serial.print(F("      (transition) setting apply delay flag to: ")); Serial.println(_applyDelay);
  applyDelay = _applyDelay;
}

void Transition::setPriority(int _priority) {
  Serial.print(F("      (transition) setting priority flag to: ")); Serial.println(_priority);
  priority = _priority;
}

int Transition::getPriority() {
  return priority;
}

int Transition::getRandomValueFromInterval(int thresholdRangeLow, int thresholdRangeHigh, int max) {
  // validation
  thresholdRangeLow = min(thresholdRangeLow, max);
  thresholdRangeHigh = min(thresholdRangeHigh, max);

  int toWrite;
  if (thresholdRangeLow != thresholdRangeHigh) {
    toWrite = random(thresholdRangeLow, thresholdRangeHigh);
  } else {
    toWrite = thresholdRangeLow;
  }

  Serial.print(F("      (transition) getRandomValueFromInterval "));
  Serial.print(thresholdRangeLow);Serial.print(" - ");Serial.println(thresholdRangeHigh);
  Serial.print(F("      (transition) generated random value: ")); Serial.println(toWrite);
  return toWrite;
}

int Transition::getRandomValueFromIntervalComplement(int thresholdRangeLow, int thresholdRangeHigh, int max) {
  // validation
  thresholdRangeLow = min(thresholdRangeLow, max);
  thresholdRangeHigh = min(thresholdRangeHigh, max);

  int toWrite;
  if (thresholdRangeLow != thresholdRangeHigh) {
    int nrFromLowerInterval = random(0, thresholdRangeLow);
    int nrFromUpperInterval = random(thresholdRangeHigh, max);
    if (nrFromLowerInterval % 2 == 0) { // kinda random selection :)
      toWrite = nrFromLowerInterval;
    } else {
      toWrite = nrFromUpperInterval;
    }

  } else {
    toWrite = random(0, max); // lets hope we wont randomly get thresholdRangeLow :)
  }

  Serial.print(F("      (transition) getRandomValueFromInterval "));
  Serial.print(thresholdRangeLow);Serial.print(" - ");Serial.println(thresholdRangeHigh);
  Serial.print(F("      (transition) generated random value: ")); Serial.println(toWrite);
  return toWrite;
}

void Transition::takePreDelayAction() {
  switch (functionType) {
        case DIGITAL_IN:
          // not applicable in fire() method, bud in isEnabled() method
          break;

        case DIGITAL_OUT:
          // thresholdRange not applicable here
          if (inverseLogic == 0) {
            digitalWrite(pin, HIGH);
            Serial.print(F("      (transition) DIGITAL out: ")); Serial.println(1);
          } else {
            digitalWrite(pin, LOW);
            Serial.print(F("      (transition) DIGITAL out: ")); Serial.println(0);
          }
          break;

        case ANALOG_IN:
          // not applicable in fire() method, bud in isEnabled() method
          break;

        case ANALOG_OUT:
            int toWrite;
            if (inverseLogic == 0) {
              // write random number from thresholdRange, or take thresholdRangeLow if they are the same
              toWrite = getRandomValueFromInterval(thresholdRangeLow, thresholdRangeHigh, 255);
            } else {
              // inverse logic here: write random number from negation of thresholdRange,
              // or if they are the same, take any value from interval (0 - 255) except for the thresholdRangeLow or thresholdRangeHigh
              toWrite = getRandomValueFromIntervalComplement(thresholdRangeLow, thresholdRangeHigh, 255);
            }
            Serial.print(F("      (transition) ANALOG out: ")); Serial.println(toWrite);
            analogWrite(pin, toWrite);
          break;
          default:
            Serial.println(F("      (transition) not supported operation in fire pre delay method"));
    }
}

void Transition::takePostDelayAction() {
  switch (functionType) {
      case DIGITAL_IN:
        // not applicable in fire() method, bud in isEnabled() method
        break;

      case DIGITAL_OUT:
        // thresholdRange not applicable here
        if (inverseLogic == 0) {
          digitalWrite(pin, LOW);
          Serial.print(F("      (transition) (post delay) DIGITAL out: ")); Serial.println(0);
        } else {
          digitalWrite(pin, HIGH);
          Serial.print(F("      (transition) (post delay) DIGITAL out: ")); Serial.println(1);
        }
        break;

      case ANALOG_IN:
        // not applicable in fire() method, bud in isEnabled() method
        break;

      case ANALOG_OUT:
          // inverse logic not applicable here, only turn off analog out
          Serial.print(F("      (transition) (post delay) ANALOG out: ")); Serial.println(0);
          analogWrite(pin, 0);
        break;
      default:
        Serial.println(F("      (transition) not supported operation in fire post delay method: "));
      }
}