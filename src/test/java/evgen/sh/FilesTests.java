package evgen.sh;
import evgen.sh.model.LibraryBook;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;

import evgen.sh.model.Library;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void jsonFileParsingImprovedTest() throws Exception {

            ObjectMapper objectMapper = new ObjectMapper();
            Library library = objectMapper.readValue(new File("src/test/resources/books.json"), Library.class);

            Assertions.assertThat(library.getLibraryName()).isEqualTo("City Central Library");
            Assertions.assertThat(library.getAddress()).isEqualTo("123 Main St, Anytown, USA");

            List<LibraryBook> books = library.getBooks();

            LibraryBook firstBook = books.get(0);
            assertThat(firstBook.getTitle()).isEqualTo("To Kill a Mockingbird");
            assertThat(firstBook.getAuthor()).isEqualTo("Harper Lee");
            assertThat(firstBook.getPublishedYear()).isEqualTo(1960);
            assertThat(firstBook.isAvailable()).isTrue();
            assertThat(firstBook.getGenres()).containsExactly("Fiction", "Classic", "Drama");

            LibraryBook secondBook = books.get(1);
            assertThat(secondBook.getTitle()).isEqualTo("1984");
            assertThat(secondBook.getAuthor()).isEqualTo("George Orwell");
            assertThat(secondBook.getPublishedYear()).isEqualTo(1949);
            assertThat(secondBook.isAvailable()).isFalse();
            assertThat(secondBook.getGenres()).containsExactly("Dystopian", "Science Fiction", "Political Fiction");

            LibraryBook thirdBook = books.get(2);
            assertThat(thirdBook.getTitle()).isEqualTo("The Great Gatsby");
            assertThat(thirdBook.getAuthor()).isEqualTo("F. Scott Fitzgerald");
            assertThat(thirdBook.getPublishedYear()).isEqualTo(1925);
            assertThat(thirdBook.isAvailable()).isTrue();
            assertThat(thirdBook.getGenres()).containsExactly("Fiction", "Classic", "Tragedy");

    }
}
