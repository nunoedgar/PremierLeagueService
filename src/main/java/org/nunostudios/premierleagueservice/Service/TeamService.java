package org.nunostudios.premierleagueservice.Service;

import org.nunostudios.premierleagueservice.DTO.TeamDTO;
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
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamMapper teamMapper;

    public Team createTeam(TeamDTO teamDTO){
        Team team = teamMapper.toEntity(teamDTO);
        Team teamSaved = this.teamRepository.save(team);
        List<Player> playerList = new ArrayList<>();
        for(Long playerId : teamDTO.getPlayersId()){
            Optional<Player> byId = this.playerRepository.findById(playerId);
            if(byId.isPresent()){
                Player player = byId.get();
                player.setTeam(teamSaved);
                playerList.add(player);
            }
        }
        teamSaved.setPlayers(playerList);
        return this.teamRepository.save(teamSaved);
    }

    public List<Team> getTeams(){
        return this.teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id){
        return this.teamRepository.findById(id);
    }

    public Team getTableLeader(){
        return this.teamRepository.findTableLeader();
    }

    public Team updateTeam(Long teamId, TeamDTO teamDTO){
        Optional<Team> byId = this.teamRepository.findById(teamId);
        if(byId.isEmpty()) return null;
        Team team = byId.get();
        team.setName(teamDTO.getName());
        team.setColor1(teamDTO.getColor1());
        team.setColor2(teamDTO.getColor2());
        team.setPoints(teamDTO.getPoints());
        return this.teamRepository.save(team);
    }

    public boolean deleteTeam(Long id){
        if(!teamRepository.existsById(id)) return false;
        this.playerRepository.dissociatePlayersFromTeam(id);
        this.teamRepository.deleteById(id);
        return true;
    }
}
