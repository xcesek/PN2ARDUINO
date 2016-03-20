#ifndef Place_h
#define Place_h

#include <Servo.h> 
#include "Arduino.h"
#include "Node.h"
#include "Helper.h"
#include "Enums.h"



class Place : public Node
{
  private:
    int tokens;
    int capacity;
    
    Servo myservo;
    
  public:
    Place(char* id);
    Place(char* id, int _pin, FunctionType _functionType);
    void apply();
    int getTokens();
    void setTokens(int _tokens);
    void addTokens(int howMany);
    void removeTokens(int howMany);
    void setCapacity(int _capacity);
};

#endif
