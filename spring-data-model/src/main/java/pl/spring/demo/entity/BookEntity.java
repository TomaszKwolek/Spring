package pl.spring.demo.entity;




import pl.spring.demo.to.IdAware;


public class BookEntity implements IdAware {
    private Long id;
    private String title;
    private String authors;

    public BookEntity(){
    }

    public BookEntity(Long id, String title, String authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }
}
