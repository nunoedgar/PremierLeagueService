package org.nunostudios.premierleagueservice.DTO;

import java.util.ArrayList;
import java.util.List;

public class TeamDTO {
    private Long id;
    private String name;
    private String color1;
    private String color2;
    private int points;
    private List<PlayerDTO> players = new ArrayList<>();
    private List<Long> playersId = new ArrayList<>();

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public List<Long> getPlayersId() {
        return playersId;
    }

    public void setPlayersId(List<Long> playersId) {
        this.playersId = playersId;
    }
}
