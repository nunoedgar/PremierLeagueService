package org.nunostudios.premierleagueservice.Repository;

import org.nunostudios.premierleagueservice.Model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t ORDER BY t.points DESC")
    List<Team> findLeader();
}
