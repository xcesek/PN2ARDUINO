#ifndef Node_h
#define Node_h

#include "Arduino.h"
#include "Helper.h"
#include "Enums.h"

class Node
{
  protected:
    char *id;
    NodeType nodeType;
    int pin;
    FunctionType functionType;
    int extended;  
    int thresholdRangeLow;
    int thresholdRangeHigh;
    int inverseLogic;
    
  public:
    Node(char* id, NodeType _nodeType);
    Node(char* id, NodeType _nodeType, int _pin, FunctionType _functionType);
    NodeType getNodeType();
    void setThresholdRange(int _tresholdRangeLow, int _tresholdRangeHigh);
    void setInverseLogic(int _inverseLogic);
};

#endif
