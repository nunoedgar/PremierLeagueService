package org.nunostudios.premierleagueservice.Service;

import org.nunostudios.premierleagueservice.DTO.PlayerDTO;
import org.nunostudios.premierleagueservice.DTO.TeamDTO;
import org.nunostudios.premierleagueservice.Mapper.TeamMapper;
import org.nunostudios.premierleagueservice.Model.Player;
import org.nunostudios.premierleagueservice.Model.Team;
import org.nunostudios.premierleagueservice.Repository.PlayerRepository;
import org.nunostudios.premierleagueservice.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                this.playerRepository.save(player);
                playerList.add(player);
            }
        }
        teamSaved.setPlayers(playerList);
        return teamSaved;
    }

    public List<Team> getTeams(){
        return this.teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id){
        return this.teamRepository.findById(id);
    }

    public Team updateTeam(Team team){
        //TODO VALIDATIONS
//        if(this.teamRepository.findById(team.getId()).get() == null){
//            return null;
//        }
        return null;// this.teamRepository.save(team);
    }

    public void deleteTeam(Long id){
        //TODO DELETE CASCADE
        this.teamRepository.deleteById(id);
    }
}
