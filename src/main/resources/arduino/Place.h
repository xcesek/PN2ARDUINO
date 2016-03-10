#ifndef Place_h
#define Place_h

#include "Arduino.h"

class Place
{
  private:
    int pin;
    char pin_type;
    
  public:
    Place(int _pin);
    void activate();
    void deactivate();
};

#endif
