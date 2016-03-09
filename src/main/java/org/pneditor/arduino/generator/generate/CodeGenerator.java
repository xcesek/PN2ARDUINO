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

    private final static String[] extensions = { "h", "cpp" };

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
                List<File> headerAndClassfiles = (List<File>) FileUtils.listFiles(new File(arduinoManager.ARDUINO_RES_DIR_NAME),
                        extensions, false);
                for(File f : headerAndClassfiles) {
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
        buffer.append(
                "#include \"Place.h\"\n" +
                        "#include \"Transition.h\"\n" +
                        "#include \"Arc.h\""
        );

        return buffer.toString();
    }

    private String getGlobalVariablesStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(
                "Place place1 = Place(13);\n" +
                        "Transition transition1 = Transition(13);\n" +
                        "Arc arc11 = Arc(&place1, &transition1);"
        );

        return buffer.toString();
    }

    private String getSetupStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(
                "// nothing \n"
        );

        return buffer.toString();
    }

    private String getLoopStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(
                "place1.activate(); \n" +
                        "  delay(1000);\n" +
                        "  place1.deactivate(); \n" +
                        "  delay(4000);\n" +
                        "  transition1.fire();\n" +
                        "  delay(4000);");

        return buffer.toString();
    }
}
