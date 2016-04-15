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
    Serial.print(F("(FiringScheduler) init priority table (unsorted): "));
    priorityTableInd = (int*) malloc(allTransitionsCount*sizeof(int));
    priorityTable = (int*) malloc(allTransitionsCount*sizeof(int));
    for (int i = 0; i < allTransitionsCount; i++) {
        priorityTable[i] = allTransitions[i]->getPriority();
        priorityTableInd[i] = i;
        Serial.print(F(" ")); Serial.print(priorityTable[i]);
    }
    sortTable(priorityTable, priorityTableInd, allTransitionsCount);

    Serial.print(F("\n(FiringScheduler) priority table (sorted): "));
    for (int i = 0; i < allTransitionsCount; i++) {
        Serial.print(F(" ")); Serial.print(priorityTable[i]);
    }
    Serial.print(F("\n(FiringScheduler) priority table (indexes): "));
    for (int i = 0; i < allTransitionsCount; i++) {
        Serial.print(F(" ")); Serial.print(priorityTableInd[i]);
    }
    Serial.println();
}


Transition* FiringScheduler::nextToFire()
{
  Transition* nextToFire = NULL;
  switch(firingPolicyType) {
    case AS_CREATED:
        for (int i = 0; i < allTransitionsCount; i++) {
            if (allTransitions[i]->isEnabled()) {
                nextToFire = allTransitions[i];
                Serial.print(F("(FiringScheduler) strategy: AS_CREATED; fired ind: ")); Serial.println(i);
                break; // break for loop
            }
        }
    break;

   case BY_PRIORITY:
        for (int i = 0; i < allTransitionsCount; i++) {
            if (allTransitions[priorityTableInd[i]]->isEnabled()) {
                nextToFire = allTransitions[priorityTableInd[i]];
                Serial.print(F("(FiringScheduler) strategy: BY_PRIORITY; fired ind: ")); Serial.println(priorityTableInd[i]);
                break; // break for loop
            }
        }
     break;

   case ROUND_ROBIN:
        // increment index or reset to zero if has come to an end
        if (lastFiredTransitionIndex >= (allPlacesCount - 1)) {
            lastFiredTransitionIndex = 0;
            Serial.print(F("(FiringScheduler) strategy: ROUND_ROBIN; resetting index; value: "));Serial.println(lastFiredTransitionIndex);
        } else {
            lastFiredTransitionIndex++;
            Serial.print(F("(FiringScheduler) strategy: ROUND_ROBIN; incrementing index; value: "));Serial.println(lastFiredTransitionIndex);
        }

        // now find next enabled transition
        for (int i = lastFiredTransitionIndex; i < allTransitionsCount; i++) {
            if (allTransitions[i]->isEnabled()) {
                nextToFire = allTransitions[i];
                lastFiredTransitionIndex = i; // update index
                Serial.print(F("(FiringScheduler) strategy: ROUND_ROBIN; updating index; value: "));Serial.println(lastFiredTransitionIndex);
                Serial.print(F("(FiringScheduler) fired ind: ")); Serial.println(lastFiredTransitionIndex);
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
                Serial.print(F("(FiringScheduler) strategy: RANDOM; fired ind: ")); Serial.println(i);
                break; // break for loop
            }
        }
    break;
  }

   if(nextToFire != NULL) {
        Serial.print("(FiringScheduler) choosing transition with ID: ");
        Serial.println(nextToFire->getId());
   } else {
        Serial.println(F("(FiringScheduler) NO transition to fire!"));
   }

  return nextToFire;
}


void FiringScheduler::sortTable(int *priority_arr, int* indexes_arr, int array_size)
{
  int i, j, temp;

  for (i = (array_size - 1); i > 0; i--)
  {
    for (j = 1; j <= i; j++)
    {
      if (priority_arr[j-1] < priority_arr[j])
      {
        temp = priority_arr[j-1];
        priority_arr[j-1] = priority_arr[j];
        priority_arr[j] = temp;

        temp = indexes_arr[j-1];
        indexes_arr[j-1] = indexes_arr[j];
        indexes_arr[j] = temp;
      }
    }
  }
}
