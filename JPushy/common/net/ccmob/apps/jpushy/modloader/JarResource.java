package net.ccmob.apps.jpushy.modloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * JarResources: JarResources maps all resources included in a Zip or Jar file.
 * Additionaly, it provides a method to extract one as a blob.
 */
public final class JarResource {

    public static void unzipJar(File destinationDir, File file) throws IOException {
        JarFile jar = new JarFile(file);

        // fist get all directories,
        // then make those directory on the destination Path
        for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();) {
            JarEntry entry = (JarEntry) enums.nextElement();

            String fileName = destinationDir + File.separator + entry.getName();
            File f = new File(fileName);

            if (fileName.endsWith("/")) {
                f.mkdirs();
            }

        }

        // now create all files
        for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements();) {
            JarEntry entry = (JarEntry) enums.nextElement();

            String fileName = destinationDir + File.separator + entry.getName();

            String folderName = destinationDir.getAbsolutePath();
            String[] parts = entry.getName().split("/");
            if (parts.length > 0) {
                for (int i = 0; i < parts.length; i++) {
                    if (!folderName.endsWith(File.separator)) {
                        folderName += File.separator;
                    }
                    File sub = new File(folderName + parts[i]);
                    if (i != (parts.length - 1)) {
                        sub.mkdir();
                        folderName += parts[i] + "/";
                    }
                }
            }

            File f = new File(fileName);
            if (!fileName.endsWith("/")) {
                InputStream is = jar.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(f);
                while (is.available() > 0) {
                    fos.write(is.read());
                }
                fos.close();
                is.close();
            }
        }
        jar.close();
    }

} // End of JarResources class.