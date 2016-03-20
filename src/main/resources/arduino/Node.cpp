#include "Arduino.h"
#include "Node.h"
#include "Helper.h"

Node::Node(char* _id, NodeType _nodeType)
{
  id = _id;
  nodeType = _nodeType;
  extended = 0;
}

Node::Node(char* _id, NodeType _nodeType, int _pin, FunctionType _functionType)
{
  id = _id;
  nodeType = _nodeType;
  pin = _pin;
  functionType = _functionType;
  extended = 1;
  Helper::log(1,"(place) initializing -> ", id);
};

NodeType Node::getNodeType() {
  return nodeType;
}
