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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.Consumer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ShieldAuto implements Plugin<Project> {
    private static final String DEFAULT_SHIELD_PATH = "./shield/";
    private static final String DEFAULT_SHIELD_PROGUARD_FILE_NAME = "shield-proguard-rules.pro";
    private static final String DEFAULT_SHIELD_XML_FILE_NAME = "shield.xml";

  private static String PROGUARD_FULL_PATH = "";
  private HashMap<String, String> proguardHashMap;
  private String proguardFileContent;

  @Override
  public void apply(Project project) {
    proguardHashMap = new HashMap<>();
    proguardFileContent = "";

    final ShieldAutoExtension extension = project.getExtensions().create("shieldAuto", ShieldAutoExtension.class, project);
    PROGUARD_FULL_PATH = extension.getDefaultPath() + extension.getProguardFilePath();

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new File(extension.getXmlMapperFilePath()));

      Element shield = doc.getDocumentElement();
      NodeList list = shield.getElementsByTagName("path");

      for (int i = 0; i < list.getLength(); i++) {
        Element path = (Element) list.item(i);
        proguardHashMap.put(path.getAttribute("name"), path.getTextContent());
      }
    } catch (Exception exceptions) {
      exceptions.printStackTrace();
    }

    project.afterEvaluate(new Action<Project>() {
      @Override
      public void execute(Project projec) {
        File inputFile = new File(extension.getProguardFilePath());
        projec.getConfigurations().getByName("compile", new Action<Configuration>() {
          @Override
          public void execute(Configuration files) {
            files.getDependencies().forEach(new Consumer<Dependency>() {
              @Override
              public void accept(Dependency dependency) {
                if (proguardHashMap.containsKey(dependency.getName())) {
                  String fileContents = null;

                  try {
                    fileContents = readFile(proguardHashMap.get(dependency.getName()), StandardCharsets.UTF_8);
                  } catch (IOException e) {
                    e.printStackTrace();
                  }

                  proguardFileContent += fileContents + "\n";
                }
              }
            });
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

  private String readFile(String path, Charset encoding) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

  public static class ShieldAutoExtension {
    String defaultPath = DEFAULT_SHIELD_PATH + DEFAULT_SHIELD_PROGUARD_FILE_NAME;

    public String getDefaultPath() {
      return defaultPath;
    }
  }

}