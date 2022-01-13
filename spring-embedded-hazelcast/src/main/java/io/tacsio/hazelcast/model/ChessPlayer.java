package io.tacsio.hazelcast.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class ChessPlayer implements Serializable, Comparable<ChessPlayer> {
    private String id;
    private String name;
    private Integer age;

    private Set<Rating> ratings;

    public ChessPlayer(String name, Integer age) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
    }

    public Integer getClassicRating() {
        return getRating("Classic");
    }

    public Integer getRapidRating() {
        return getRating("Rapid");
    }

    public Integer getBlitzRating() {
        return getRating("Blitz");
    }

    private Integer getRating(String rating) {
        return this.getRatings().stream()
                .filter(r -> r.getTitle().equalsIgnoreCase(rating))
                .findFirst().get()
                .getValue();
    }

    @Override
    public int compareTo(ChessPlayer o) {
        return o.getClassicRating().compareTo(this.getClassicRating());
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }


}
