#include "Arduino.h"
#include "Place.h"

Place::Place(int _pin)
{
  pinMode(pin, OUTPUT);
  pin = _pin;
}

void Place::activate()
{
  digitalWrite(pin, HIGH); 
}

void Place::deactivate()
{
  digitalWrite(pin, LOW);
}
