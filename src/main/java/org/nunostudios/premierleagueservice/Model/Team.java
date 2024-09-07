package org.nunostudios.premierleagueservice.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_gen")
    @SequenceGenerator(name = "team_id_gen", sequenceName = "team_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "color1", length = Integer.MAX_VALUE)
    private String color1;

    @Column(name = "color2", length = Integer.MAX_VALUE)
    private String color2;

    @Column(name = "points")
    private Integer points;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();

    public Team() {
    }

    public Team(Long id, String name, String color1, String color2, Integer points, List<Player> players) {
        this.id = id;
        this.name = name;
        this.color1 = color1;
        this.color2 = color2;
        this.points = points;
        this.players = players;
    }

    public Team(String name, String color1, String color2, Integer points, List<Player> players) {
        this.name = name;
        this.color1 = color1;
        this.color2 = color2;
        this.points = points;
        this.players = players;
    }

    public Team(String name, String color1, String color2, Integer points) {
        this.name = name;
        this.color1 = color1;
        this.color2 = color2;
        this.points = points;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

}