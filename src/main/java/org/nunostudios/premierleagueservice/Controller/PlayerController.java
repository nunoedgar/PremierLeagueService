package org.nunostudios.premierleagueservice.Controller;

import org.nunostudios.premierleagueservice.DTO.PlayerDTO;
import org.nunostudios.premierleagueservice.Mapper.PlayerMapper;
import org.nunostudios.premierleagueservice.Model.Player;
import org.nunostudios.premierleagueservice.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerMapper playerMapper;


    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerDTO> getPlayers() {
        return playerMapper.toDTOList(this.playerService.getPlayers());
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO getPlayerById(@PathVariable Long id) {
        Optional<Player> player = this.playerService.getPlayerById(id);
        if(player.isPresent()){
            return playerMapper.toDTO(player.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
    }

    @CrossOrigin
    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO createPlayer(@RequestBody PlayerDTO playerDTO) {
        PlayerDTO result = playerMapper.toDTO(this.playerService.createPlayer(playerDTO));
        if(result == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
        return result;
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {
        Player player = this.playerService.updatePlayer(id, playerDTO);
        if(player == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        return playerMapper.toDTO(player);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlayer(@PathVariable Long id) {
        if(!this.playerService.deletePlayer(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
    }
}
