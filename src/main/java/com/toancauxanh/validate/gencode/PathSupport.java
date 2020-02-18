package com.toancauxanh.validate.gencode;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathSupport {

    public static final String PACKAGE_NAME = "com.filegen.validators";

    public static Path getPathFileGenCode() {
        return Paths.get(new File(".").getAbsolutePath() + "/src/main/java/com/filegen/validators");
    }

    public static final String PACKAGE_NAME_CONFIG = "com.filegen.config";

    public static Path getOutputPath() {
        return Paths.get(new File(".").getAbsolutePath() + "/src/main/java");
    }

}
