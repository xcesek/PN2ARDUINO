#ifndef Enums_h
#define Enums_h

#include "Arduino.h"


enum FunctionType {
  DIGITAL_IN, DIGITAL_OUT, ANALOG_IN, ANALOG_OUT, SERVO, PWM_MOTOR, LED_DISPLAY
};

enum ArcType {
  regular, inhibitor, reset
};

enum NodeType {
  placeType, transitionType
};

enum logLevel {
  info, debug, warn, error 
};

enum FiringPolicyType {
  AS_CREATED, ROUND_ROBIN, BY_PRIORITY, RANDOM
};

#endif
