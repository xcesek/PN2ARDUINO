#ifndef FiringScheduler_h
#define FiringScheduler_h

#include "Arduino.h"
#include "Helper.h"
#include "Enums.h"
#include "Transition.h"

class FiringScheduler
{
  private:
  FiringPolicyType firingPolicyType;
  Transition **allTransitions;
  int allTransitionsCount;
  Place **allPlaces;
  int allPlacesCount;
  int lastFiredTransitionIndex;
  int *priorityTableInd;
  int *priorityTable;
  Helper *helper;

  void sortTable(int *priority_arr, int* indexes_arr, int array_size);

  public:
    FiringScheduler(FiringPolicyType _firingPolicyType, Transition **_allTransitions, int _allTransitionsCount, Place **_allPlaces, int _allPlacesCount);
    Transition* nextToFire();
    void printActualMarking();
    void setHelper(Helper *_helper);
};

#endif

