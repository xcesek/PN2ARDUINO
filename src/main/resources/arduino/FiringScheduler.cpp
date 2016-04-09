#include "Arduino.h"
#include "FiringScheduler.h"
#include "FiringScheduler.h"
#include "Helper.h"
#include "Enums.h"

FiringScheduler::FiringScheduler(FiringPolicyType _firingPolicyType, Transition **_allTransitions, int _allTransitionsCount, Place **_allPlaces, int _allPlacesCount)
{  
  firingPolicyType = _firingPolicyType;
    allTransitions = _allTransitions;
    allTransitionsCount = _allTransitionsCount;
    allPlaces = _allPlaces;
    allPlacesCount = _allPlacesCount;
}


Transition* FiringScheduler::nextToFire()
{
  switch(firingPolicyType) {
   case AS_CREATED:
       for (int i = 0; i < allTransitionsCount; i++) {
             if (allTransitions[i]->isEnabled()) {
                 allTransitions[i]->fire();
                 break; // break for loop
             }
         }
         break;
   case BY_PRIORITY:
         break;
   case RANDOM:
         break;
  }

  return allTransitions[0];
}
