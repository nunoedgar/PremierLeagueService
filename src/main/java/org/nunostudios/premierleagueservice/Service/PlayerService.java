package org.nunostudios.premierleagueservice.Service;

import org.nunostudios.premierleagueservice.DTO.PlayerDTO;
import org.nunostudios.premierleagueservice.Model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    Player createPlayer(PlayerDTO playerDTO);
    List<Player> getPlayers();
    Optional<Player> getPlayerById(Long id);
    Player updatePlayer(Long playerId, PlayerDTO playerDTO);
    boolean deletePlayer(Long id);
}
