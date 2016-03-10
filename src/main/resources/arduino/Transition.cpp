#include "Arduino.h"
#include "Transition.h"

Transition::Transition(int _pin)
{
  pinMode(_pin, OUTPUT);
  pin = _pin;
}

void Transition::fire()
{
  digitalWrite(pin, HIGH); 
}

