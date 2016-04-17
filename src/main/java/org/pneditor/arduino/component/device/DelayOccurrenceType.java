package org.pneditor.arduino.component.device;

/**
 * Created by palo on 4/17/2016.
 */
public enum DelayOccurrenceType {
    NO {
        public String toString() {
            return "No delay";
        }
    },
    BEFORE {
        public String toString() {
            return "Before firing";
        }
    },
    DURING {
        public String toString() {
            return "During firing";
        }
    },
    AFTER {
        public String toString() {
            return "After firing";
        }
    }

}
