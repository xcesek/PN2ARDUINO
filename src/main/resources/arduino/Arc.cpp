#include "Arduino.h"
#include "Arc.h"
#include "Node.h"
#include "Enums.h"

Arc::Arc(Node *_source, Node *_destination) {
  source = _source; 
  destination = _destination;
  type = standard;
  multiplicity = 1;
}

Arc::Arc(Node *_source, Node *_destination, ArcType _type, int _multiplicity) {
  source = _source; 
  destination = _destination;
  type = _type;
  multiplicity = _multiplicity;
}

int Arc::getMultiplicity() {
  return multiplicity;
}

ArcType Arc::getType() {
  return type;
}

Node* Arc::getSource() {
  return source;
}

Node* Arc::getDestination() {
  return destination;
}

Node* Arc::getPlaceNode() {
  if (source->getNodeType() == placeType) return source;
  else return destination;
}

Node* Arc::getTransitionNode() {
if (source->getNodeType() == transitionType) return source;
  else return destination;
}
