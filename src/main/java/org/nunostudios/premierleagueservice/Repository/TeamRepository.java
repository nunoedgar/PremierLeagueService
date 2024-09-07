package org.nunostudios.premierleagueservice.Repository;

import org.nunostudios.premierleagueservice.Model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t ORDER BY t.points DESC LIMIT 1")
    Team findTableLeader();
}
