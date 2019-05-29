package com.api.Models;

import javax.persistence.*;

@Entity
@Table(name = "age_categories")
@NamedQuery(name = "AgeCategory.findAll", query = "SELECT a FROM AgeCategory a")
public class AgeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age_from;

    @Column(nullable = false)
    private Integer age_to;

    public AgeCategory() {

    }

    public AgeCategory(String name, Integer age_from, Integer age_to) {
        this.name = name;
        this.age_from = age_from;
        this.age_to = age_to;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge_from() {
        return age_from;
    }

    public void setAge_from(Integer age_from) {
        this.age_from = age_from;
    }

    public Integer getAge_to() {
        return age_to;
    }

    public void setAge_to(Integer age_to) {
        this.age_to = age_to;
    }
}
