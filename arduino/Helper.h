#ifndef Helper_h
#define Helper_h

#include "Arduino.h"

class Helper
{
public:
    int loggingEnabled;

    Helper(int _loggingEnabled);
    void logMessage(char *str1, char *str2);
    void log(char *str1);
    void logLn(char *str1);
    void log(int value);
    void logLn(int value);
    void log(const __FlashStringHelper *ifsh);
    void logLn(const __FlashStringHelper *ifsh);
};

#endif
