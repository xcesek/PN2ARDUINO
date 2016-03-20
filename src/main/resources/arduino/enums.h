#ifndef Enums_h
#define Enums_h

#include "Arduino.h"


enum FunctionType {
  DIGITAL_IN, DIGITAL_OUT, ANALOG_IN, ANALOG_OUT, 
  SERVO, PWM_MOTOR,
  DIODE, 
  POTENTIOMETER, THERMOMETER, MICROPHONE, ULTRASONIC_SENSOR,
  BUTTON
};

enum ArcType {
  standard, inhibitor, test
};

enum NodeType {
  placeType, transitionType
};

enum logLevel {
  info, debug, warn, error 
};

#endif
