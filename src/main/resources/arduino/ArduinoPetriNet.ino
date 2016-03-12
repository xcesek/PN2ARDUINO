#include <Servo.h>

#include "Helper.h"
#include "Place.h"
#include "Transition.h"

// ====== places ======
Place *place0;
Place *place1;
Place **allPlaces;
int allPlacesCount;

// ====== transitions ======
Transition *transition01;
Transition *transition10;
Transition **allTransitions;
int allTransitionsCount;


void setup()
{
  Serial.begin(9600);
  
  // ====== general ======
  allPlacesCount = 2;
  allPlaces = (Place**) malloc(allPlacesCount*sizeof(Place*));
  
  allTransitionsCount = 2;
  allTransitions = (Transition**) malloc(allTransitionsCount*sizeof(Transition*));


  // ====== places ======
  //0.
  place0 = new Place("place0", 13, digitalOut);
  place0->setCapacity(10);
  place0->setTokens(1);
  allPlaces[0] = place0;
  
  //1.
  place1 = new Place("place1", 7, digitalOut);
  place1->setCapacity(10);
  place1->setTokens(0);
  allPlaces[1] = place1;
  
  
  // ====== transitions ======  
  // 0.
  Place **inPlacesT01 = (Place**) malloc(10*sizeof(Place*));
  inPlacesT01[0] = place0;  
  
  Place **outPlacesT01 = (Place**) malloc(10*sizeof(Place*));
  outPlacesT01[0] = place1;
  
  transition01 = new Transition("t01", 0, analogIn, inPlacesT01, 1, outPlacesT01, 1);
  transition01->setAnalogTreshold(80);
  //transition01->setAnalogTresholdRange(80, 150);
  allTransitions[0] = transition01;
  
  // 1.
  Place **inPlacesT10 = (Place**) malloc(3*sizeof(Place*));
  inPlacesT10[0] = place1;  
  
  Place **outPlacesT10 = (Place**) malloc(3*sizeof(Place*));
  outPlacesT10[0] = place0;
  
  transition10 = new Transition("t10", 4, digitalIn, inPlacesT10, 1, outPlacesT10, 1);
  allTransitions[1] = transition10;
}

void loop()
{  
  for (int i = 0; i < allTransitionsCount; i++) {
    if (allTransitions[i]->isActive()) {     // todo: more facny logic
      allTransitions[i]->fire();
    }
  }
  
  for (int i = 0; i < allPlacesCount; i++) {
    allPlaces[i]->apply();
  }
    
  Serial.println("=================================================");
  delay(300);
}

