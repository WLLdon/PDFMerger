import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        File dir = new File("prez");
        PDFMergerUtility ut = new PDFMergerUtility();
        String fileExt;
        File[] ordered = new File[Objects.requireNonNull(dir.listFiles()).length];

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            fileExt = file.toString().substring(file.toString().length() - 7);
            System.out.println(fileExt);

            for (int i = 1; i < ordered.length; i++) {
                if (fileExt.contains(" " + i + ".pdf")) ordered[i] = file;
            }

        }

        for (int i = 1; i < ordered.length; i++) {
            System.out.println("adding source: "+ordered[i].toString());
            try {
                ut.addSource(ordered[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ut.setDestinationFileName("PPBFile.pdf");
        try {
            ut.mergeDocuments(null);
            System.out.println("Files merged successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
