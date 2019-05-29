package com.api.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genders")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "gender", fetch = FetchType.EAGER)
    private List<Player> player;

    public Gender() {

    }

    public Gender(String name) {
        this.name = name;
    }

    public List<Player> getPlayer() {
        return player;
    }

    @JsonIgnore
    public void setPlayer(List<Player> player) {
        this.player = player;
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
}
