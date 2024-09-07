package org.nunostudios.premierleagueservice.Service;

import org.nunostudios.premierleagueservice.DTO.PlayerDTO;
import org.nunostudios.premierleagueservice.DTO.TeamDTO;
import org.nunostudios.premierleagueservice.Mapper.PlayerMapper;
import org.nunostudios.premierleagueservice.Mapper.TeamMapper;
import org.nunostudios.premierleagueservice.Model.Player;
import org.nunostudios.premierleagueservice.Model.Team;
import org.nunostudios.premierleagueservice.Repository.PlayerRepository;
import org.nunostudios.premierleagueservice.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private PlayerMapper playerMapper;

    public Player createPlayer(PlayerDTO playerDTO){
        Player player = playerMapper.toEntity(playerDTO);
        return this.playerRepository.save(player);
    }

    public List<Player> getPlayers(){
        return this.playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(Long id){
        return this.playerRepository.findById(id);
    }

    public Player updatePlayer(Long playerId, PlayerDTO playerDTO){
        //TODO
        return null;
    }

    public boolean deletePlayer(Long id){
        if(!playerRepository.existsById(id)) return false;
        this.playerRepository.deleteById(id);
        return true;
    }
}
