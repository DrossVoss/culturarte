
package culturarte.casosuso;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ImageService {

    private static final String BASE_DIR = "server_images";

    public static String saveImage(File source, String preferredName) throws IOException {
        File base = new File(BASE_DIR);
        if (!base.exists()) base.mkdirs();
        String ext = "";
        String nm = source.getName();
        int dot = nm.lastIndexOf('.');
        if (dot >= 0) ext = nm.substring(dot);
        String unique = (preferredName==null || preferredName.trim().isEmpty()) ? UUID.randomUUID().toString() : preferredName + "_" + UUID.randomUUID().toString();
        String destName = unique + ext;
        File dest = new File(base, destName);
        Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        // return relative path
        return BASE_DIR + File.separator + destName;
    }

    public static File resolve(String relativePath) {
        if (relativePath == null) return null;
        try {
            String lower = relativePath.toLowerCase();
            if (lower.startsWith("http://") || lower.startsWith("https://")) {
                // download the remote image into BASE_DIR and return the file
                File base = new File(BASE_DIR);
                if (!base.exists()) base.mkdirs();
                String name = "remote_" + UUID.randomUUID().toString();
                String ext = "";
                int dot = relativePath.lastIndexOf('.');
                if (dot > 0 && dot < relativePath.length() - 1) {
                    ext = relativePath.substring(dot);
                    if (ext.length() > 8) ext = "";
                }
                File dest = new File(base, name + ext);
                try (java.io.InputStream in = new java.net.URL(relativePath).openStream()) {
                    java.nio.file.Files.copy(in, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    return dest;
                } catch (Exception ex) {
                    // if download fails, fall back to returning null
                    return null;
                }
            } else {
                // local path
                return new File(relativePath);
            }
        } catch (Throwable t) {
            return null;
        }
    }
}
