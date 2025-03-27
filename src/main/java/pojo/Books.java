package pojo;

public class Books {
	
	private int id;
    private String name;
    private String author;
    private String publication;
    private String category;
    private String pages;
    private String price;

    public Books() {}

    public Books(String name, String author, String publication, String category, String pages, String price) {
        this.name = name;
        this.author = author;
        this.publication = publication;
        this.category = category;
        this.pages = pages;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublication() { return publication; }
    public void setPublication(String publication) { this.publication = publication; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPages() { return pages; }
    public void setPages(String pages) { this.pages = pages; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

}
