package dto;

import java.math.BigDecimal;

public class MovieDTO {

    private String id;

    private String name;

    private BigDecimal rating;

    private Long year;

    public MovieDTO() {
    }

    public MovieDTO(String id, String name, BigDecimal rating, Long year) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
}
