package kraulerService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class ArchiveWorker {

    public static String decompressGzipFile(String gzipFile, String pathFile) {

        String nameOutFile = gzipFile + ".unpack";
        gzipFile = pathFile + "/" + gzipFile;

        try {
            String newFile = pathFile + "/" + nameOutFile;
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameOutFile;
    }

    public static File decompressGzipFile(String gzipFile, String pathFile, boolean returnFile) {
        if (returnFile)
        return new File(decompressGzipFile(gzipFile, pathFile));
        return new File(decompressGzipFile(gzipFile, pathFile));
    }


    public static void compressGzipFile(String file, String gzipFile) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


/*
public class ArchiveWorker {
    public static void main(String[] args) {
        String file = "/Users/pankaj/sitemap.xml";
        String gzipFile = "/Users/pankaj/sitemap.xml.gz";
        String newFile = "/Users/pankaj/new_sitemap.xml";

        compressGzipFile(file, gzipFile);
        decompressGzipFile(gzipFile, newFile);
    }
}
*/