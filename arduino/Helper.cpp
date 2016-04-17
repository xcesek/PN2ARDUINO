#include "Arduino.h"
#include "Helper.h"

Helper::Helper(int _loggingEnabled) {
  loggingEnabled = _loggingEnabled;
}

void Helper::log(const __FlashStringHelper *ifsh) {
  if (loggingEnabled) {
    Serial.print(ifsh);
  }
}

void Helper::logLn(const __FlashStringHelper *ifsh) {
  if (loggingEnabled) {
    Serial.println(ifsh);
  }
}

void Helper::log(char *str1) {
  if (loggingEnabled) {
    Serial.print(str1);
  }
}

void Helper::logLn(char *str1) {
  if (loggingEnabled) {
    Serial.println(str1);
  }
}

void Helper::log(int value) {
  if (loggingEnabled) {
    Serial.print(value);
  }
}

void Helper::logLn(int value) {
  if (loggingEnabled) {
    Serial.println(value);
  }
}
