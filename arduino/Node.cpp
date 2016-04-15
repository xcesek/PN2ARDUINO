#include "Arduino.h"
#include "Node.h"
#include "Helper.h"

Node::Node(char* _id, NodeType _nodeType)
{
  id = _id;
  nodeType = _nodeType;
  extended = 0;
  
  thresholdRangeLow = -1;
  thresholdRangeHigh = -1;
  inverseLogic = 0;
}

Node::Node(char* _id, NodeType _nodeType, int _pin, FunctionType _functionType)
{
  id = _id;
  nodeType = _nodeType;
  pin = _pin;
  functionType = _functionType;
  extended = 1;
  
  thresholdRangeLow = -1;
  thresholdRangeHigh = -1;
  inverseLogic = 0;
};

NodeType Node::getNodeType() {
  return nodeType;
}

void Node::setThresholdRange(int _thresholdRangeLow, int _thresholdRangeHigh) {
  if (_thresholdRangeLow < _thresholdRangeHigh) {
    thresholdRangeLow = _thresholdRangeLow;
    thresholdRangeHigh = _thresholdRangeHigh;
  } else { // try to be proactive, user changed values by mistake
    thresholdRangeLow = _thresholdRangeHigh;
    thresholdRangeHigh = _thresholdRangeLow;
  }

  Serial.print(F("      (node) setting thresholdRange: ")); Serial.print(thresholdRangeLow);Serial.print(F(" - "));Serial.println(thresholdRangeHigh);
}

void Node::setInverseLogic(int _inverseLogic) {
  inverseLogic = _inverseLogic;
}

char* Node::getId() {
  return id;
}
