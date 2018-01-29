package info.kimjihyok.shieldautolibrary;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * ClassLoader classLoader = this.getClass().getClassLoader();
 * InputStream inputStream = classLoader.getResourceAsStream("stages/stage_1/map.tmx");
 */
public class ShieldAuto implements Plugin<Project> {
    private static final String DEFAULT_XML_PATH = "default-shield.xml";
    private DependencyMap versionMap;
    private String proguardFileContent = "";
    private String proguardIncludedText = "";
    private String proguardExcludedText = "";

    @Override
    public void apply(Project project) {
        final ShieldAutoExtension extension = project.getExtensions().create("shieldAuto", ShieldAutoExtension.class, project);
        versionMap = new DependencyMap();

        // local flow
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(DEFAULT_XML_PATH);
        versionMap.initializeWithXML(inputStream);

        project.afterEvaluate(projec -> {
            File inputFile = new File(extension.getDefaultPath());

            projec.getConfigurations().getByName("compile", files -> files.getDependencies().forEach(dependency -> {

                if (versionMap.containsKey(dependency.getName())) {
                    String fileContents = "";

                    try {
                        InputStream proguardStream = this.getClass().getClassLoader().getResourceAsStream(dependency.getName() + "/proguard.txt");

                        String text = "#### AUTO-GENERATED PROGUARD RULE FOR " + dependency.getName() + " START ####\n";
                        text += convertStreamToString(proguardStream);
                        text += "\n#### AUTO-GENERATED PROGUARD RULE FOR " + dependency.getName() + " END   ####\n\n";

                        fileContents = text;
                        versionMap.put(dependency.getName(), text);
                        proguardIncludedText += dependency.getName() + ", ";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    proguardFileContent += fileContents + "\n";
                } else {
                    proguardExcludedText += dependency.getName() + ", ";
                }
            }));

            try {
                FileWriter f2 = new FileWriter(inputFile, false);
                f2.write(proguardFileContent);
                f2.close();
                System.out.println("ShieldAuto Proguard Included libraries: " + proguardIncludedText);
                System.out.println("ShieldAuto Proguard Excluded libraries: " + proguardExcludedText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}