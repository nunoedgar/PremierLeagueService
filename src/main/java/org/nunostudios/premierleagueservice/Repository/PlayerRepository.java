package org.nunostudios.premierleagueservice.Repository;

import org.nunostudios.premierleagueservice.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
