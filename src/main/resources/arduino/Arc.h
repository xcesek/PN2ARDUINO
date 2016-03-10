#ifndef Arc_h
#define Arc_h

#include "Arduino.h"
#include "Place.h"
#include "Transition.h"

class Arc
{
  private:
    int type;
    Place *place;
    Transition *transition;
    
  public:
    Arc(Place *_place, Transition *_transition);
};

#endif
