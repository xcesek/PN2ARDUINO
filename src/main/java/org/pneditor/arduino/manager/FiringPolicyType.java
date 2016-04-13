package org.pneditor.arduino.manager;

/**
 * Created by palo on 4/9/2016.
 */
public enum FiringPolicyType {
    AS_CREATED{
        public String toString() {
            return "As created";
        }
    },
    ROUND_ROBIN{
        public String toString() {
            return "Round robin";
        }
    },
    BY_PRIORITY{
        public String toString() {
            return "By priority";
        }
    },
    RANDOM{
        public String toString() {
            return "Random";
        }
    },

}