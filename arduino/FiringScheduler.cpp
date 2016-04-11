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
  Transition* nextToFire = NULL;
  Serial.print("(FiringScheduler) using policy : "); Serial.println(firingPolicyType);
  switch(firingPolicyType) {
   case AS_CREATED:
       for (int i = 0; i < allTransitionsCount; i++) {
             if (allTransitions[i]->isEnabled()) {
                 nextToFire = allTransitions[i];
                 break; // break for loop
             }
         }
         break;
   case BY_PRIORITY:
        nextToFire = allTransitions[0];
         break;
   case RANDOM:
        nextToFire = allTransitions[0];
         break;
  }

   if(nextToFire != NULL) {
        Serial.print(" (FiringScheduler) choosing transition : "); Serial.println(nextToFire->getId());
   }

  return nextToFire;
}
