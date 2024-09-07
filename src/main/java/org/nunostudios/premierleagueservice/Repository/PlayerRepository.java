package org.nunostudios.premierleagueservice.Repository;

import org.nunostudios.premierleagueservice.Model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p Where p.team.id = :teamId")
    List<Player> findPlayersByTeam(@Param("teamId") Long teamId);

    @Modifying
    @Transactional
    @Query("UPDATE Player p SET p.team = null WHERE p.team.id = :teamId")
    void dissociatePlayersFromTeam(@Param("teamId") Long teamId);
}
