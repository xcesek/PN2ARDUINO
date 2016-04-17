#ifndef Transition_h
#define Transition_h

#include "Arduino.h"
#include "Helper.h"
#include "Enums.h"
#include "Arc.h"
#include "Place.h"

class Transition : public Node
{
  private:
    Arc **connectedArcs;
    int connectedArcsCount;
    int earliestFiringTime;
    int latestFiringTime;
    DelayOccurrenceType delayOccurrenceType;
    int priority;

    int getRandomValueFromInterval(int thresholdRangeLow, int thresholdRangeHigh, int max);
    int getRandomValueFromIntervalComplement(int thresholdRangeLow, int thresholdRangeHigh, int max);
    void takePreDelayAction();
    void takePostDelayAction();


  public:
    Transition(char* id);
    Transition(char* id, int _pin, FunctionType _functionType);
    void fire();
    int isEnabled();
    void setConnectedArcs(Arc **arcs);
    void setConnectedArcsCount(int count);
    Arc **getConnectedArcs();
    int getConnectedArcsCount();
    void setDelay(int _earliestFiringTime, int _latestFiringTime);
    void setDelayOccurrenceType(DelayOccurrenceType _delayOccurrenceType);
    void setPriority(int _priority);
    int getPriority();
    void doDelay();
};

#endif

