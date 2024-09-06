package org.nunostudios.premierleagueservice.Controller;

import org.nunostudios.premierleagueservice.Model.Team;
import org.nunostudios.premierleagueservice.Service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService personService) {
        this.teamService = personService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Team> getTeams() {
        return this.teamService.getTeams();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Team getPersonById(@PathVariable Long id) {
        Optional<Team> person = this.teamService.getTeamById(id);
        if(person.isPresent()){
            return person.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Team updatePerson(@PathVariable Long id, @RequestBody Team team) {
        return this.teamService.updateTeam(team);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@PathVariable Long id) {
        this.teamService.deleteTeam(id);
    }
}
