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

    public CodeGenerator(ArduinoManager arduinoManager) {
        this.arduinoManager = arduinoManager;

        projectDir = new File(arduinoManager.getProjectDirName());
        mainSketchFile = new File(projectDir + File.separator + arduinoManager.MAIN_SKETCH_FILE_NAME);
        sketchTemplateFile = new File("src\\main\\resources\\arduino" + File.separator + arduinoManager.SKETCH_TEMPLATE_NAME);
    }

    public String generate() {
        String generatedCodeStr = "";

        // prepare

        if (projectDir.exists()) {
            try {
                FileUtils.deleteDirectory(projectDir);
                FileUtils.forceMkdir(projectDir);

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
        return "// no imports ";
    }

    private String getGlobalVariablesStr() {
        return "// no variables ";
    }

    private String getSetupStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("// initialize digital pin 13 as an output.").append("\n")
                .append("pinMode(13, OUTPUT);");

        return buffer.toString();
    }

    private String getLoopStr() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(
                "      digitalWrite(13, HIGH);   // turn the LED on (HIGH is the voltage level)\\n +\n" +
                "      delay(3000);              // wait for a second\\n +\n" +
                "      digitalWrite(13, LOW);    // turn the LED off by making the voltage LOW\\n +\n" +
                "      delay(1000);              // wait for a second");

        return buffer.toString();
    }
}
