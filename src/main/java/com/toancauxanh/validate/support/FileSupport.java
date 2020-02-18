package com.toancauxanh.validate.support;

import java.io.File;
import java.nio.file.Path;

import com.toancauxanh.validate.exception.SystemException;


public class FileSupport {

    /**
     * If there is no directory, create it.
     *
     * @param location
     */
    public void createDirectory(Path location) {
        File file = new File(location.toUri());

        if (file.exists()) {
            return;
        }

        if (!file.mkdirs()) {
            throw new SystemException(String.format("Couldn't create directory [path:%s]", file.getAbsolutePath()));
        }
    }

    /**
     *
     *
     * @param location
     * @param filename
     * @return
     */
    public boolean existFile(Path location, String fileName) {
        if (location == null) {
            return false;
        }

        Path file = location.resolve(fileName);

        return file.toFile().exists();
    }

    /**
     * Check file existion.
     *
     * @param pathFile
     * @return true if file is existed else false
     */
    public boolean isFileExist(String pathFile) {
        return new File(pathFile).exists();
    }

}
