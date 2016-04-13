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
    lastFiredTransitionIndex = -1;

    // fill priority table
    priorityTable = (int*) malloc(allTransitionsCount*sizeof(int));
    for (int i = 0; i < allTransitionsCount; i++) {
        priorityTable[i] = allTransitions[i]->getPriority();
    }
    sortTable(priorityTable, allTransitionsCount);
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
        for (int i = 0; i < allTransitionsCount; i++) {
            if (allTransitions[priorityTable[i]]->isEnabled()) {
                nextToFire = allTransitions[priorityTable[i]];
                break; // break for loop
            }
        }
     break;

   case ROUND_ROBIN:
        // increment index or reset to zero if has come to an end
        if (lastFiredTransitionIndex >= (allPlacesCount - 1)) {
            lastFiredTransitionIndex = 0;
        } else {
            lastFiredTransitionIndex++;
        }

        // now find next enabled transition
        for (int i = lastFiredTransitionIndex; i < allTransitionsCount; i++) {
            if (allTransitions[i]->isEnabled()) {
                nextToFire = allTransitions[i];
                lastFiredTransitionIndex = i; // update index
                break; // break for loop
            }
        }
   break;

    case RANDOM:
        int nextToFireIndex = random(0, allPlacesCount);
        // now find next enabled transition
        for (int i = nextToFireIndex; i < allTransitionsCount; i++) {
            if (allTransitions[i]->isEnabled()) {
                nextToFire = allTransitions[i];
                break; // break for loop
            }
        }
    break;
  }

   if(nextToFire != NULL) {
        Serial.print(" (FiringScheduler) choosing transition : "); Serial.println(nextToFire->getId());
   }

  return nextToFire;
}


void FiringScheduler::sortTable(int *numbers, int array_size)
{
  int i, j, temp;

  for (i = (array_size - 1); i > 0; i--)
  {
    for (j = 1; j <= i; j++)
    {
      if (numbers[j-1] > numbers[j])
      {
        temp = numbers[j-1];
        numbers[j-1] = numbers[j];
        numbers[j] = temp;
      }
    }
  }
}
