package org.pneditor.arduino.generator.generate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.pneditor.arduino.manager.ArduinoManager;
import org.pneditor.petrinet.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Pavol Cesek on 2/27/2016.
 *
 * @email xcesek@stuba.sk
 * Faculty of Electrical Engineering and Information Technology STU
 * Under GNU GPL v3 licence
 */
public class CodeGenerator {
    private File projectDir;
    private File mainSketchFile;
    private File sketchTemplateFile;

    private ArduinoManager arduinoManager;
    private Subnet subnet;
    private Marking marking;

    private final static String[] extensions = {"h", "cpp"};

    public CodeGenerator(ArduinoManager arduinoManager, Marking marking) {
        this.arduinoManager = arduinoManager;
        this.marking = marking;
        this.subnet = marking.getPetriNet().getCurrentSubnet();

        projectDir = new File(arduinoManager.getProjectDirName());
        mainSketchFile = new File(projectDir + File.separator + arduinoManager.MAIN_SKETCH_FILE_NAME);
        sketchTemplateFile = new File(arduinoManager.ARDUINO_RES_DIR_NAME + File.separator + arduinoManager.SKETCH_TEMPLATE_NAME);
    }

    public String generate() {
        String generatedCodeStr;

        // prepare

        try {
            if (projectDir.exists()) {
                FileUtils.deleteDirectory(projectDir);
            }
            FileUtils.forceMkdir(projectDir);

            // copy .h and .cpp files
            List<File> headerAndClassFiles = (List<File>) FileUtils.listFiles(new File(arduinoManager.ARDUINO_RES_DIR_NAME),
                    extensions, false);
            for (File f : headerAndClassFiles) {
                FileUtils.copyFileToDirectory(f, projectDir);
            }

            // generate main sketch file with all logic inside
            generatedCodeStr = getGeneratedCodeStr();
            FileUtils.write(mainSketchFile, generatedCodeStr);

        } catch (IOException e) {
            System.out.println(e);
            generatedCodeStr = "Error manipulating with source files.";  // todo
        }

        return generatedCodeStr;
    }

    private String getGeneratedCodeStr() throws IOException {
        Map valuesMap = new HashMap();
        valuesMap.put("comments", getCommentsStr());
        valuesMap.put("imports", getImportsStr());
        valuesMap.put("globalVariables", getGlobalVariablesStr());
        valuesMap.put("setupBody", getSetupStr());
        valuesMap.put("loopBody", getLoopStr());

        String templateStr = FileUtils.readFileToString(sketchTemplateFile);
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String resolvedStr = sub.replace(templateStr);

        return resolvedStr;
    }

    private String getCommentsStr() {
        String dateTimeFormatPattern = "yyyy/MM/dd HH:mm:ss z";
        final DateFormat format = new SimpleDateFormat(dateTimeFormatPattern);
        final String nowString = format.format(new Date());

        StringBuffer buffer = new StringBuffer();
        buffer.append("Generated at ").append(nowString)
                .append(" for board ").append(arduinoManager.getBoardSettings().getBoardType().getBoardName());

        return buffer.toString();
    }

    private String getImportsStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("#include <Servo.h>\n");
        buffer.append("#include \"Helper.h\"\n");
        buffer.append("#include \"Enums.h\"\n");
        buffer.append("#include \"Place.h\"\n");
        buffer.append("#include \"Transition.h\"\n");
        buffer.append("#include \"Arc.h\"\n");
        buffer.append("#include \"FiringScheduler.h\"\n");
        buffer.append("\n");

        return buffer.toString();
    }

    private String getGlobalVariablesStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("// ====== places ======\n");

        Set<Place> places = subnet.getPlaces();
        places.forEach(p -> buffer.append("Place *p_" + p.getId() + ";\n"));
        buffer.append("Place **allPlaces;\n");
        buffer.append("int allPlacesCount;\n");
        buffer.append("\n");

        buffer.append("// ====== transitions ======\n");
        Set<Transition> transitions = subnet.getTransitions();
        transitions.forEach(t -> buffer.append("Transition *t_" + t.getId() + ";\n"));
        buffer.append("Transition **allTransitions;\n");
        buffer.append("int allTransitionsCount;\n");
        buffer.append("\n");

        buffer.append("// ====== arcs ======\n");
        Set<Arc> arcs = subnet.getArcs();
        arcs.forEach(a -> buffer.append("Arc *a_" + a.hashCode() + ";\n"));
        buffer.append("Arc **allArcs;\n");
        buffer.append("int allArcsCount;\n");
        buffer.append("\n");

        buffer.append("// ====== firing policy ======\n");
        buffer.append("FiringScheduler *firingScheduler;\n");
        buffer.append("\n");

        return buffer.toString();
    }

    private String getSetupStr() {
        Set<Place> places = subnet.getPlaces();
        Set<Transition> transitions = subnet.getTransitions();
        Set<Arc> arcs = subnet.getArcs();

        StringBuffer buffer = new StringBuffer();
        buffer.append("Serial.begin(9600);\n\n");
        buffer.append("// ====== general ======\n");
        buffer.append("allPlacesCount = " + places.size() + ";\n");
        buffer.append("allPlaces = (Place**) malloc(allPlacesCount*sizeof(Place*));\n");
        buffer.append("allTransitionsCount = " + transitions.size() + ";\n");
        buffer.append("allTransitions = (Transition**) malloc(allTransitionsCount*sizeof(Transition*));\n");
        buffer.append("allArcsCount = " + arcs.size() + ";\n");
        buffer.append("allArcs = (Arc**) malloc(allArcsCount*sizeof(Arc*));\n");
        buffer.append("\n");

        buffer.append("// ====== places ======\n");
        CustomCounter placeOrd = new CustomCounter();
        places.forEach(p -> {
            if (p.getArduinoNodeExtension().isEnabled()) {
                buffer.append("p_" + p.getId()
                        + " = new Place(\"p_" + p.getId() + "\", "
                        + p.getArduinoNodeExtension().getPin().getNumber() + ", "
                        + p.getArduinoNodeExtension().getFunction().name() + ");\n");
            } else {
                buffer.append("p_" + p.getId()
                        + " = new Place(\"p_" + p.getId() + "\");\n");
            }

            buffer.append("p_" + p.getId()
                    + "->setCapacity(" + p.getCapacity() + ");\n");
            buffer.append("p_" + p.getId()
                    + "->setTokens(" + marking.getTokens(p) + ");\n");
            buffer.append("allPlaces[" + placeOrd.getNumber() + "] = p_" + p.getId() + ";\n");
            buffer.append("p_" + p.getId()
                    + "->setThresholdRange("
                    + p.getArduinoNodeExtension().getThresholdRangeLow() + ", "
                    + p.getArduinoNodeExtension().getThresholdRangeHigh() + ");\n");
            buffer.append("p_" + p.getId()
                    + "->setInverseLogic(" + p.getArduinoNodeExtension().getInverseLogic() + ");\n");

        });

        buffer.append("\n// ====== transitions ======\n");
        CustomCounter transOrd = new CustomCounter();
        transitions.forEach(t -> {
            if (t.getArduinoNodeExtension().isEnabled()) {
                buffer.append("t_" + t.getId()
                        + " = new Transition(\"t_" + t.getId() + "\", "
                        + t.getArduinoNodeExtension().getPin().getNumber() + ", "
                        + t.getArduinoNodeExtension().getFunction().name() + ");\n");
                buffer.append("t_" + t.getId()
                        + "->setThresholdRange("
                        + t.getArduinoNodeExtension().getThresholdRangeLow() + ", "
                        + t.getArduinoNodeExtension().getThresholdRangeHigh() + ");\n");
                buffer.append("t_" + t.getId()
                        + "->setDelay(" + t.getEarliestFiringTime() + ", " + t.getLatestFiringTime() + ");\n");
                buffer.append("t_" + t.getId()
                        + "->setInverseLogic(" + t.getArduinoNodeExtension().getInverseLogic() + ");\n");
                buffer.append("t_" + t.getId()
                        + "->setApplyDelay(" + t.getArduinoNodeExtension().isWithDelay() + ");\n");
                buffer.append("t_" + t.getId()
                        + "->setPriority(" + t.getPriority() + ");\n");
            } else {
                buffer.append("t_" + t.getId()
                        + " = new Transition(\"t_" + t.getId() + "\");\n");
            }

            buffer.append("allTransitions[" + transOrd.getNumber() + "] = t_" + t.getId() + ";\n");
        });

        buffer.append("\n// ====== arcs ======\n");
        CustomCounter arcsOrd = new CustomCounter();
        arcs.forEach(a -> {
//            buffer.append("a_" + a.hashCode()
//                    + " = new Arc("
//                    + (a.getSource() instanceof Place ? "p_" : "t_") + a.getSource().getId() + ", "     // just a simple hack :)
//                    + (a.getDestination() instanceof Place ? "p_" : "t_") + a.getDestination().getId() + ");\n");
            buffer.append("a_" + a.hashCode()
                    + " = new Arc("
                    + (a.getSource() instanceof Place ? "p_" : "t_") + a.getSource().getId() + ", "     // just a simple hack :)
                    + (a.getDestination() instanceof Place ? "p_" : "t_") + a.getDestination().getId() + ", "
                    + a.getType() + ", + " + a.getMultiplicity() + ");\n");
            buffer.append("allArcs[" + arcsOrd.getNumber() + "] = a_" + a.hashCode() + ";\n");
        });

        buffer.append("\n// ====== wiring parts together ======\n");
        transOrd.reset();
        transitions.forEach(t -> {
            buffer.append("Arc** ta_" + t.getId() + " = (Arc**) malloc( " + t.getConnectedArcs().size() + "*sizeof(Arc*));\n");
            CustomCounter conArcsOrd = new CustomCounter();
            t.getConnectedArcs().forEach(a -> {
                buffer.append("ta_" + t.getId() + "[" + conArcsOrd.getNumber() + "] = a_" + a.hashCode() + ";\n");
            });
            buffer.append("t_" + t.getId() + "->setConnectedArcs(ta_" + t.getId() + ");\n");
            buffer.append("t_" + t.getId() + "->setConnectedArcsCount(" + t.getConnectedArcs().size() + ");\n");
        });

        buffer.append("// ====== firing policy ======\n");
        buffer.append("firingScheduler = new FiringScheduler(" + arduinoManager.getFiringPolicyType().name()
                + ", allTransitions, allTransitionsCount"
                + ", allPlaces, allPlacesCount"
                + ");\n");

        buffer.append("randomSeed(analogRead(0));\n");

        return buffer.toString();
    }

    private String getLoopStr() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(" Transition *nextToFire = firingScheduler->nextToFire();\n");
        buffer.append(" if(nextToFire != NULL) nextToFire->fire();\n");
        buffer.append(" for (int i = 0; i < allPlacesCount; i++) {\n" +
                "       allPlaces[i]->apply();\n" +
                "   }\n");

        buffer.append("Serial.println(\"=================================================\");\n");
        buffer.append("delay(7000);\n");
        return buffer.toString();
    }


    private class CustomCounter {
        private int i = 0;

        public int getNumber() {
            return i++;
        }

        public void reset() {
            i = 0;
        }
    }
}
