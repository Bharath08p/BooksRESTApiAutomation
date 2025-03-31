package helpers;

import pojo.Books;
import java.util.Map;
import java.util.Optional;

public class BookFieldUpdater {

    private static final Map<String, BookUpdateStrategy> fieldUpdates = Map.of(
        "name", book -> book.setName(book.getName() + " - Updated"),
        "author", book -> book.setAuthor(book.getAuthor() + " - Revised"),
        "publication", book -> book.setPublication(book.getPublication() + " (New Edition)"),
        "category", book -> book.setCategory(book.getCategory() + " & Updated"),
        "pages", book -> book.setPages(Integer.toString(Integer.parseInt(book.getPages()) + 50)),
        "price", book -> {
            try {
                double updatedPrice = Double.parseDouble(book.getPrice()) + 500.0;
                book.setPrice(String.valueOf(updatedPrice));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid price format: " + book.getPrice(), e);
            }
        },
        "multiplefields", book -> {
            book.setName(book.getName() + " - Updated");
            book.setAuthor(book.getAuthor() + " - Revised");
            book.setPublication(book.getPublication() + " (New Edition)");
            book.setCategory(book.getCategory() + " & Updated");
            book.setPages(Integer.toString(Integer.parseInt(book.getPages()) + 50));
            try {
                double updatedPrice = Double.parseDouble(book.getPrice()) + 500.0;
                book.setPrice(String.valueOf(updatedPrice));
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid price format in multiple fields update: " + book.getPrice(), e);
            }
        }
    );

    public static void updateField(Books book, String fieldName) {
        Optional.ofNullable(fieldUpdates.get(fieldName.replace("\"", "").toLowerCase()))
            .orElseThrow(() -> new IllegalArgumentException("Invalid field name: " + fieldName))
            .update(book);
    }

    @FunctionalInterface
    private interface BookUpdateStrategy {
        void update(Books book);
    }
}
