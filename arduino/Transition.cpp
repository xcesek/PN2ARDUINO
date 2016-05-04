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
  delayOccurrenceType = NO;
  priority = 0;

  helper->log(F("(transition) initializing NOT ARDUINO transition with ID: ")); helper->logLn(id);
  helper->log(F("      (transition) will be using function: ")); helper->logLn(functionType);
}


Transition::Transition(char* _id, int _pin, FunctionType _functionType)
: Node(_id, transitionType, _pin, _functionType)
{ 
  earliestFiringTime = 0;
  latestFiringTime = 0;
  delayOccurrenceType = NO;
  priority = 0;

  helper->log(F("(transition) initializing transition with ID: ")); helper->log(getId());  helper->log(" -> ");
  
  switch (functionType) {
    case DIGITAL_IN:
      pinMode(pin, INPUT);
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
    default:
      helper->logLn(F("!!! not supported operation on transition !!!"));
  }

  helper->log(F("      (transition) will be using function: ")); helper->logLn(functionType);
}

void Transition::fire()
{
  helper->log(F("(transition) firing transition with ID: ")); helper->logLn(id);

  if (delayOccurrenceType == BEFORE) doDelay();

  // take token(s) from all in places
  for (int i = 0; i < connectedArcsCount; i++) {
    Node *source = connectedArcs[i]->getSource();
    if (source->getNodeType() == placeType) {
      Place *place = (Place*) source;
      place->removeTokens(connectedArcs[i]->getMultiplicity());
      place->apply();
    }
  }

  if (delayOccurrenceType == DURING) doDelay();

  // put token(s) to all out places
  for (int i = 0; i < connectedArcsCount; i++) {
    Node *destination = connectedArcs[i]->getDestination();
    if (destination->getNodeType() == placeType) {
      Place *place = (Place*) destination;
      place->addTokens(connectedArcs[i]->getMultiplicity());
      place->apply();
    }
  }

  if (delayOccurrenceType == AFTER) doDelay();
}

int Transition::isEnabled()
{
  helper->log(F("(transition) checking firability on transition with ID: ")); helper->logLn(id);

  // internal condition
  int internalTriggerActive = 1;
  for (int i = 0; i < connectedArcsCount; i++) {
    Node *source = connectedArcs[i]->getSource();
    if (source->getNodeType() == placeType) {
      Place *place = static_cast<Place*>(source);
      switch(connectedArcs[i]->getType()){
        //regular type
        case regular:
          //Serial.print("  (transition) source place arc type : "); Serial.println("regular");
          if (place->getTokens() < connectedArcs[i]->getMultiplicity())
            internalTriggerActive = 0;
          break;
        //inhibitor type
        case inhibitor:
          //Serial.print("  (transition) source place arc type : "); Serial.println("inhibitor");
          if (place->getTokens() != 0)
            internalTriggerActive = 0;
          break;
        //reset type
        case reset:
          //Serial.print("  (transition) source place arc type : "); Serial.println("reset");
          internalTriggerActive = 1;
          break;
      }
    }
    Node *destination = connectedArcs[i]->getDestination();
    if (destination->getNodeType() == placeType) {
      Place *place = static_cast<Place*>(destination);
      if ( connectedArcs[i]->getMultiplicity() > ( place->getCapacity() - place->getTokens() ) ) {
        internalTriggerActive = 0;
      }
    }
  }
  helper->log(F("      (transition) internal condition: ")); helper->logLn(internalTriggerActive);

  // if not extended finish here, as no arduino pin is associated with transition instance
  if (!extended) {
    helper->log(F("      (transition) not assoc with arduino, returning ")); helper->logLn(internalTriggerActive);
    return internalTriggerActive; 
  }
  
  // external condition - guard
  int readValue;
  int externalTriggerActive = 0;
  switch (functionType) {
    case DIGITAL_IN:
      readValue = digitalRead(pin);
      helper->log(F("      (transition) DIGITAL read value: ")); helper->logLn(readValue);
      if(readValue == 1) externalTriggerActive = 1;
      break;
      
    case DIGITAL_OUT: 
      // not applicable in isEnabled() method, bud in fire() method
      break;
      
    case ANALOG_IN:
      readValue = analogRead(pin);
      helper->log(F("      (transition) ANALOG read value: ")); helper->logLn(readValue);
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
      helper->logLn("      (transition) Unsupported operation processed in isActive method");
  }

  helper->log(F("      (transition) isActive: ")); helper->logLn(internalTriggerActive && externalTriggerActive);
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
  helper->log(F("      (transition) trying to set delay: "));
  helper->log(_earliestFiringTime);helper->log(" - ");helper->logLn(_latestFiringTime);
  _earliestFiringTime = max(_earliestFiringTime, 0);
  _latestFiringTime = max(_latestFiringTime, 0);

  if (_earliestFiringTime < _latestFiringTime) {
    earliestFiringTime =  _earliestFiringTime;
    latestFiringTime = _latestFiringTime;
  } else { // try to be proactive, user changed values by mistake
    earliestFiringTime =  _latestFiringTime;
    latestFiringTime = _earliestFiringTime;
  }

  helper->log(F("      (transition) delay set to: "));
  helper->log(earliestFiringTime);helper->log(" - ");helper->logLn(latestFiringTime);

}

void Transition::setDelayOccurrenceType(DelayOccurrenceType _delayOccurrenceType) {
  helper->log(F("      (transition) setting DelayOccurrenceType flag to: ")); helper->logLn(_delayOccurrenceType);
  delayOccurrenceType = _delayOccurrenceType;
}

void Transition::setPriority(int _priority) {
  helper->log(F("      (transition) setting priority flag to: ")); helper->logLn(_priority);
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

  helper->log(F("      (transition) getRandomValueFromInterval "));
  helper->log(thresholdRangeLow);helper->log(" - ");helper->logLn(thresholdRangeHigh);
  helper->log(F("      (transition) generated random value: ")); helper->logLn(toWrite);
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

  helper->log(F("      (transition) getRandomValueFromInterval "));
  helper->log(thresholdRangeLow);helper->log(" - ");helper->logLn(thresholdRangeHigh);
  helper->log(F("      (transition) generated random value: ")); helper->logLn(toWrite);
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
            helper->log(F("      (transition) DIGITAL out: ")); helper->logLn(1);
          } else {
            digitalWrite(pin, LOW);
            helper->log(F("      (transition) DIGITAL out: ")); helper->logLn(0);
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
            helper->log(F("      (transition) ANALOG out: ")); helper->logLn(toWrite);
            analogWrite(pin, toWrite);
          break;
          default:
            helper->logLn(F("      (transition) not supported operation in fire pre delay method"));
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
          helper->log(F("      (transition) (post delay) DIGITAL out: ")); helper->logLn(0);
        } else {
          digitalWrite(pin, HIGH);
          helper->log(F("      (transition) (post delay) DIGITAL out: ")); helper->logLn(1);
        }
        break;

      case ANALOG_IN:
        // not applicable in fire() method, bud in isEnabled() method
        break;

      case ANALOG_OUT:
          // inverse logic not applicable here, only turn off analog out
          helper->log(F("      (transition) (post delay) ANALOG out: ")); helper->logLn(0);
          analogWrite(pin, 0);
        break;
      default:
        helper->logLn(F("      (transition) not supported operation in fire post delay method: "));
      }
}

void Transition::doDelay() {
  // pre delay action
  if (extended) {
    takePreDelayAction();
  } else {
    helper->logLn(F("      (transition) no pre delay action as no extended transition"));
   }

  if (earliestFiringTime != latestFiringTime) {
    int randomTime = random(earliestFiringTime, latestFiringTime);
    helper->log(F("      (transition) waiting for ")); helper->log(randomTime); helper->logLn(F(" millis"));
    delay(randomTime);
  } else {
    helper->log(F("      (transition) waiting for ")); helper->log(earliestFiringTime); helper->logLn(F(" millis"));
    delay(earliestFiringTime);
  }

  // post delay action
  if (extended) {
      takePostDelayAction();
    } else {
      helper->logLn(F("      (transition) no post delay action as no extended transition"));
  }
}