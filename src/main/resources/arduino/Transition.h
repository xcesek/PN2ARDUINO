#ifndef Transition_h
#define Transition_h

#include "Arduino.h"

class Transition
{
  private:
    int pin;
    char pin_type;
    
  public:
    Transition(int _pin);
    void fire();
};

#endif
