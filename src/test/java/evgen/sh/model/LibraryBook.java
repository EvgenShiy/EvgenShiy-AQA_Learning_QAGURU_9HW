package evgen.sh.model;

import java.util.List;

public class LibraryBook {
    private String title;
    private String author;
    private int publishedYear;
    private boolean available;
    private List<String> genres;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "LibraryBook{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear=" + publishedYear +
                ", available=" + available +
                ", genres=" + genres +
                '}';
    }
}
