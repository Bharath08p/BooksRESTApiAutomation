package pojo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Books {
	private String testCaseName;
    private int id;
    private String name;
    private String author;
    private String publication;
    private String category;
    private String pages;
    private String price;

}