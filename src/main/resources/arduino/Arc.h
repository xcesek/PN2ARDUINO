#ifndef Arc_h
#define Arc_h

#include "Arduino.h"
#include "Helper.h"
#include "Enums.h"
#include "Node.h"

class Arc
{
  private:
    Node *source;
    Node *destination;
    ArcType type;
    int multiplicity;
    
  public:
    Arc(Node *source, Node *destination);
    Arc(Node *source, Node *destination, ArcType type, int multiplicity);
    int getMultiplicity();
    ArcType getType();
    Node* getSource();
    Node* getDestination();
    Node* getPlaceNode();
    Node* getTransitionNode();
};

#endif
