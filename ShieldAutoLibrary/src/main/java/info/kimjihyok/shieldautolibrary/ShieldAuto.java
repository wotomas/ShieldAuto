package info.kimjihyok.shieldautolibrary;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.Consumer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * ClassLoader classLoader = this.getClass().getClassLoader();
 * InputStream inputStream = classLoader.getResourceAsStream("stages/stage_1/map.tmx");
 */
public class ShieldAuto implements Plugin<Project> {
    private static final String DEFAULT_XML_PATH = "default-shield.xml";
    private static String DEFAULT_PROGUARD_PATH;
    private DependencyVersionMap versionMap;
    private String proguardFileContent = "";

    @Override
    public void apply(Project project) {
        final ShieldAutoExtension extension = project.getExtensions().create("shieldAuto", ShieldAutoExtension.class, project);
        DEFAULT_PROGUARD_PATH = extension.getDefaultPath();
        versionMap = new DependencyVersionMap();

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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    proguardFileContent += fileContents + "\n";
                }
            }));

            try {
                FileWriter f2 = new FileWriter(inputFile, false);
                f2.write(proguardFileContent);
                f2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}