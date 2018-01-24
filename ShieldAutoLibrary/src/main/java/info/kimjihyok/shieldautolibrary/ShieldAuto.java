package info.kimjihyok.shieldautolibrary;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.internal.impldep.org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ShieldAuto implements Plugin<Project> {
    private static final String SHIELD_PATH = "app/shield/";
    private static final String SHIELD_PROGUARD_FILE_NAME = "shield-proguard-rules.pro";
    private static final String SHIELD_XML_FILE_NAME = "shield.xml";

    private HashMap<String, String> proguardHashMap;
    private String proguardFileContent;

    @Override
    public void apply(Project project) {
        proguardHashMap = new HashMap<>();
        proguardFileContent = "";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(SHIELD_PATH + SHIELD_XML_FILE_NAME);

            Element shield = doc.getDocumentElement();
            NodeList list = shield.getElementsByTagName("path");

            for (int i = 0; i < list.getLength(); i++) {
                Element path = (Element) list.item(i);
                proguardHashMap.put(path.getAttribute("name"), path.getTextContent());
            }
        } catch(Exception exceptions) {
            exceptions.printStackTrace();
        }

        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project projec) {
                File inputFile = new File(SHIELD_PATH + SHIELD_PROGUARD_FILE_NAME);
                projec.getConfigurations().getByName("compile").getDependencies().forEach(new Consumer<Dependency>() {
                    @Override
                    public void accept(Dependency dependency) {
                        if (proguardHashMap.containsKey(dependency.getName())) {
                            String fileContents = null;

                            try {
                                fileContents = getFileContent(new FileInputStream(new File(proguardHashMap.get(dependency.getName()))));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            proguardFileContent += fileContents + "\n";
                        }
                    }
                });

                try {
                    FileWriter f2 = new FileWriter(inputFile, false);
                    f2.write(proguardFileContent);
                    f2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getFileContent(FileInputStream fileStream) {
        String everything = "";
        try {
            everything = IOUtils.toString(fileStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return everything;
    }

    static File apply() {
        return new File(SHIELD_PATH);
    }
}