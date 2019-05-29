package com.api.Models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "players")
@NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String country;

    private String club;

    @Column(nullable = false)
    private String city;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "id")
    private Gender gender;

    @Column(nullable = false)
    private int age;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private List<Time> times;

    public Player() {
    }

    public Player(String country, String club, String city, Gender gender, int age) {
        this.country = country;
        this.club = club;
        this.city = city;
        this.gender = gender;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }
}
