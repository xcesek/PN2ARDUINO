package org.pneditor.arduino.generator.generate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.pneditor.arduino.manager.ArduinoManager;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final static String[] extensions = {"h", "cpp"};

    public CodeGenerator(ArduinoManager arduinoManager) {
        this.arduinoManager = arduinoManager;

        projectDir = new File(arduinoManager.getProjectDirName());
        mainSketchFile = new File(projectDir + File.separator + arduinoManager.MAIN_SKETCH_FILE_NAME);
        sketchTemplateFile = new File(arduinoManager.ARDUINO_RES_DIR_NAME + File.separator + arduinoManager.SKETCH_TEMPLATE_NAME);
    }

    public String generate() {
        String generatedCodeStr = "";

        // prepare

        if (projectDir.exists()) {
            try {
                FileUtils.deleteDirectory(projectDir);
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
                generatedCodeStr = "";  // todo
            }

        } else {
            generatedCodeStr = "";  // todo
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
        buffer.append("\n");

        return buffer.toString();
    }

    private String getGlobalVariablesStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("// ====== places ======\n");
        //todo Place *place0;
        buffer.append("Place **allPlaces;\n");
        buffer.append("int allPlacesCount;\n");
        buffer.append("\n");

        buffer.append("// ====== transitions ======\n");
        //todo Transition *transition0;
        buffer.append("Transition **allTransitions;\n");
        buffer.append("int allTransitionsCount;\n");
        buffer.append("\n");

        buffer.append("// ====== arcs ======\n");
        //todo Arc *arc0;
        buffer.append("Arc **allArcs;\n");
        buffer.append("int allArcsCount;\n");
        buffer.append("\n");

        return buffer.toString();
    }

    private String getSetupStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Serial.begin(9600);\n\n");
        buffer.append("// ====== general ======\n");
        buffer.append("allPlacesCount = 2;\n");
        buffer.append("allPlaces = (Place**) malloc(allPlacesCount*sizeof(Place*));\n");
        buffer.append("allTransitionsCount = 2;\n");
        buffer.append("allTransitions = (Transition**) malloc(allTransitionsCount*sizeof(Transition*));\n");
        buffer.append("allArcsCount = 4;\n");
        buffer.append("allArcs = (Arc**) malloc(allArcsCount*sizeof(Arc*));\n");
        buffer.append("\n");


        return buffer.toString();
    }

    private String getLoopStr() {
        StringBuffer buffer = new StringBuffer();


        return buffer.toString();
    }
}
