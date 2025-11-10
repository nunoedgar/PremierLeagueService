package org.nunostudios.premierleagueservice.Service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.nunostudios.premierleagueservice.DTO.PlayerDTO;
import org.nunostudios.premierleagueservice.Mapper.PlayerMapper;
import org.nunostudios.premierleagueservice.Model.Player;
import org.nunostudios.premierleagueservice.Model.Team;
import org.nunostudios.premierleagueservice.Repository.PlayerRepository;
import org.nunostudios.premierleagueservice.Repository.TeamRepository;
import org.nunostudios.premierleagueservice.Service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    public PlayerServiceImpl(TeamRepository teamRepository, PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public Player createPlayer(PlayerDTO playerDTO){
        Player player = playerMapper.toEntity(playerDTO);
        if(playerDTO.getTeamId() != null){
            Optional<Team> byId = teamRepository.findById(playerDTO.getTeamId());
            if(byId.isPresent()){
                Team team = byId.get();
                player.setTeam(team);
            }else{
                throw new EntityNotFoundException("Team not found");
            }
        }
        return this.playerRepository.save(player);
    }

    @Override
    public List<Player> getPlayers(){
        return this.playerRepository.findAll();
    }

    @Override
    public Optional<Player> getPlayerById(Long id){
        return this.playerRepository.findById(id);
    }

    @Override
    public Player updatePlayer(Long playerId, PlayerDTO playerDTO){
        Optional<Player> byId = this.playerRepository.findById(playerId);
        if(byId.isEmpty()) throw new EntityNotFoundException("Player not found");
        Player player = byId.get();
        player.setName(playerDTO.getName());
        player.setAge(playerDTO.getAge());
        Optional<Team> teamById = this.teamRepository.findById(playerDTO.getTeamId());
        if(teamById.isEmpty()) throw new EntityNotFoundException("Team not found");
        Team team = teamById.get();
        player.setTeam(team);
        return this.playerRepository.save(player);
    }

    @Override
    public boolean deletePlayer(Long id){
        if(!playerRepository.existsById(id)) return false;
        this.playerRepository.deleteById(id);
        return true;
    }
}
