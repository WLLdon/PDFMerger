import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class PDFMerger {

    private static String SOURCE;
    private static String PATHOUT;
    private static final PDFMergerUtility ut = new PDFMergerUtility();

    public static void main(String[] args) {

        if (args.length == 0) stop("You didn't specify source directiory");
        if (args.length == 1) stop("You didn't specify target file name");

        SOURCE = args[0];
        PATHOUT = args[1];

        File[] inOrder = FindOrder();
        AddResources(inOrder);
        MergeFiles();

    }

    private static File[] FindOrder() {
        String fileExt, filename;
        File dir = new File(SOURCE);
        File[] result = new File[Objects.requireNonNull(dir.listFiles()).length];

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            fileExt = file.toString().substring(file.toString().length() - 7);
            filename = file.toString();
            System.out.println(fileExt);

            //TODO auto-finding math series in files

            for (int i = 1; i < result.length; i++) {
                if (filename.contains("W" + i + ".pdf")) result[i] = file;
            }

        }

        return result;
    }

    private static void AddResources(File[] files) {

        for (int i = 1; i < files.length; i++) {
            System.out.println("adding source: " + files[i].toString());
            try {
                ut.addSource(files[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void MergeFiles() {

        ut.setDestinationFileName(PATHOUT);
        try {
            ut.mergeDocuments(null);
            System.out.println("Files merged successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void stop(String s) {
        System.out.println(s);
        System.exit(1);
    }
}
