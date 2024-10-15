package evgen.sh.model;

import java.util.List;

public class Library {
    private String libraryName;
    private String address;
    private List<LibraryBook> books; // Используем LibraryBook

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<LibraryBook> getBooks() {
        return books;
    }

    public void setBooks(List<LibraryBook> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Library{" +
                "libraryName='" + libraryName + '\'' +
                ", address='" + address + '\'' +
                ", books=" + books +
                '}';
    }
}
