#include "Arduino.h"
#include "Arc.h"
#include "Place.h"
#include "Transition.h"

Arc::Arc(Place *_place, Transition *_transition)
{
  place = _place;
  transition = _transition;
}
