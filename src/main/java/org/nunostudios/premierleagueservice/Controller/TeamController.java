package org.nunostudios.premierleagueservice.Controller;

import org.nunostudios.premierleagueservice.DTO.TeamDTO;
import org.nunostudios.premierleagueservice.Mapper.TeamMapper;
import org.nunostudios.premierleagueservice.Model.Team;
import org.nunostudios.premierleagueservice.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMapper teamMapper;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TeamDTO> getTeams() {
        return teamMapper.toDTOList(this.teamService.getTeams());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TeamDTO getTeamById(@PathVariable Long id) {
        Optional<Team> team = this.teamService.getTeamById(id);
        if(team.isPresent()){
            return teamMapper.toDTO(team.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public TeamDTO createTeam(@RequestBody TeamDTO teamDTO) {
        return teamMapper.toDTO(this.teamService.createTeam(teamDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Team updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return this.teamService.updateTeam(team);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@PathVariable Long id) {
        this.teamService.deleteTeam(id);
    }
}
