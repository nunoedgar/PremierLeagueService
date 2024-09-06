package org.nunostudios.premierleagueservice.Service;

import org.nunostudios.premierleagueservice.Model.Team;
import org.nunostudios.premierleagueservice.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Team createTeam(Team team){
        return this.teamRepository.save(team);
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
        return this.teamRepository.save(team);
    }

    public void deleteTeam(Long id){
        //TODO DELETE CASCADE
        this.teamRepository.deleteById(id);
    }
}
