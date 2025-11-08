package org.nunostudios.premierleagueservice.Service;

import org.nunostudios.premierleagueservice.DTO.TeamDTO;
import org.nunostudios.premierleagueservice.Model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    Team createTeam(TeamDTO teamDTO);
    List<Team> getTeams();
    Optional<Team> getTeamById(Long id);
    Team getTableLeader();
    Team updateTeam(Long teamId, TeamDTO teamDTO);
    boolean deleteTeam(Long id);
}
