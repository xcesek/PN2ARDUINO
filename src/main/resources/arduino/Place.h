#ifndef Place_h
#define Place_h

#include <Servo.h> 
#include "Arduino.h"
#include "Helper.h"

class Place
{
  private:
    char *id;
    int pin;
    FunctionType functionType;
    int extended;
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
