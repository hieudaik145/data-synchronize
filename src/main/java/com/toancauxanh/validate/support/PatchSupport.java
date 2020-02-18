package com.toancauxanh.validate.support;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PatchSupport {

    public Path getOutPutPatch() {
        return Paths.get(new File(".").getAbsolutePath() + "/src/main/java");
    }
}
