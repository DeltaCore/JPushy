package net.ccmob.apps.jpushy.modloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;

public class ModResourceLoader {

    private static File cacheFolder = new File("cache");
    
    public static void loadPlugins(String path) {
        File folder = new File(path);
        if(!folder.exists()){
        	folder.mkdir();
        }
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".jar")) {
                    return true;
                }
                return false;
            }
        };
        for (File f : folder.listFiles(filter)) {
        	System.out.println("[ModResourceLoader] Found mod jar at " + f.getPath().toString());
          loadInstance(f);
        }
    }

    private static String getMainClass(File f) {
        try {
            String className = "";

            if (!cacheFolder.exists())
                cacheFolder.mkdir();

            JarResource.unzipJar(cacheFolder, f);

            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if (name.endsWith(".info")) {
                        return true;
                    }
                    return false;
                }
            };
            for (File fSub : cacheFolder.listFiles(filter)) {
                if (fSub.getName().equals("class.info")) {
                    BufferedReader reader = new BufferedReader(new FileReader(fSub));
                    String line = reader.readLine();
                    if (line.startsWith("class=")) {
                        className = line.replace("class=", "");
                    }
                    reader.close();
                }
            }

            deleteRecursive(cacheFolder);

            return className;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void loadInstance(File file) {
        String lcStr = getMainClass(file);
        if (lcStr == null)
            return;
        URL jarfile;
        try {
            jarfile = new URL("jar", "", "file:" + file.getAbsolutePath() + "!/");
            URLClassLoader cl = URLClassLoader.newInstance(new URL[] { jarfile });
            Class<?> loadedClass = cl.loadClass(lcStr);
            loadedClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteRecursive(File path) {
        File[] c = path.listFiles();
        for (File file : c) {
            if (file.isDirectory()) {
                deleteRecursive(file);
                file.delete();
            } else {
                file.delete();
            }
        }
        path.delete();
    }

}
