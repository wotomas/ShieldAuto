package info.kimjihyok.shieldautolibrary;

import org.gradle.api.Project;

/**
 * Created by jihyokkim on 2018. 1. 29..
 */

public class ShieldAutoExtension {
    private String defaultPath = "default-proguard.pro";
    private Project project;

    public ShieldAutoExtension(Project project) {
        this.project = project;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public String auto() {
        String defaultPathTest = project.getRootDir().getPath() + "/" + defaultPath;
        System.out.println("ShieldAuto proguard directory: " + defaultPathTest);
        return defaultPathTest;
    }
}
