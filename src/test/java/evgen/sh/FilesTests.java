package evgen.sh;

import com.opencsv.CSVReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FilesTests {

    private final ClassLoader cl = FilesTests.class.getClassLoader();

    @Test
    void zipTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/somefiles.zip"));
        try (ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("somefiles.zip"))) {

            ZipEntry entry;

            while ((entry = is.getNextEntry()) != null) {
                System.out.println("Found file: " + entry.getName());

                if (entry.getName().equals("username.csv")) {

                    try (InputStream inputStreamCsv = zf.getInputStream(entry);
                         CSVReader csvReader = new CSVReader(new InputStreamReader(inputStreamCsv))) {

                        String[] firstLine = csvReader.readNext();
                        Assertions.assertThat(firstLine).containsExactly("Username; Identifier;First name;Last name");
                        Assertions.assertThat(csvReader.readNext()).containsExactly("booker12;9012;Rachel;Booker");
                        Assertions.assertThat(csvReader.readNext()).containsExactly("grey07;2070;Laura;Grey");
                    }
                } else if (entry.getName().equals("example.xlsx")) {

                    try (InputStream inputStreamXlsx = zf.getInputStream(entry);
                         Workbook workbook = WorkbookFactory.create(inputStreamXlsx)) {

                        Sheet sheet = workbook.getSheetAt(0);

                        Cell cellE2 = sheet.getRow(1).getCell(4);
                        Assertions.assertThat(cellE2.toString().trim()).isEqualTo("United States");

                        Cell cellH7 = sheet.getRow(6).getCell(2);
                        Assertions.assertThat(cellH7.toString().trim()).isEqualTo("Brumm");
                    }

                } else if (entry.getName().equals("sample.pdf")) {

                    try (InputStream inputStreamPdf = zf.getInputStream(entry);
                         PDDocument pdfDocument = PDDocument.load(inputStreamPdf)) {

                        PDFTextStripper pdfStripper = new PDFTextStripper();
                        String pdfText = pdfStripper.getText(pdfDocument);
                        Assertions.assertThat(pdfText).contains("This is a simple PDF file. Fun fun fun.");
                    }
                }
            }
        }
    }
}
