package org.nunostudios.premierleagueservice.Service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nunostudios.premierleagueservice.DTO.TeamDTO;
import org.nunostudios.premierleagueservice.Mapper.TeamMapper;
import org.nunostudios.premierleagueservice.Model.Player;
import org.nunostudios.premierleagueservice.Model.Team;
import org.nunostudios.premierleagueservice.Repository.PlayerRepository;
import org.nunostudios.premierleagueservice.Repository.TeamRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock private TeamRepository teamRepository;
    @Mock private PlayerRepository playerRepository;
    @Mock private TeamMapper teamMapper;
    @InjectMocks private TeamServiceImpl service;

    @Test
    void createTeamAssociatesFoundPlayersAndSavesTeam() {
        TeamDTO dto = teamDto(List.of(1L, 2L));
        Team mapped = new Team();
        Team saved = new Team();
        Player player = new Player();
        when(teamMapper.toEntity(dto)).thenReturn(mapped);
        when(teamRepository.save(mapped)).thenReturn(saved);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.findById(2L)).thenReturn(Optional.empty());
        when(teamRepository.save(saved)).thenReturn(saved);

        assertSame(saved, service.createTeam(dto));
        assertSame(saved, player.getTeam());
        assertEquals(List.of(player), saved.getPlayers());
        verify(teamRepository, times(2)).save(any(Team.class));
    }

    @Test
    void delegatesTeamQueriesToRepository() {
        List<Team> teams = List.of(new Team());
        Team leader = teams.get(0);
        when(teamRepository.findAll()).thenReturn(teams);
        when(teamRepository.findById(4L)).thenReturn(Optional.of(leader));
        when(teamRepository.findTableLeader()).thenReturn(leader);
        when(teamRepository.findTop5ByOrderByPointsDesc()).thenReturn(teams);

        assertSame(teams, service.getTeams());
        assertEquals(Optional.of(leader), service.getTeamById(4L));
        assertSame(leader, service.getTableLeader());
        assertSame(teams, service.getTopFiveTeams());
    }

    @Test
    void updateTeamChangesFieldsAndSaves() {
        Team existing = new Team();
        TeamDTO dto = teamDto(List.of());
        dto.setName("Updated");
        dto.setColor1("Red");
        dto.setColor2("White");
        dto.setPoints(30);
        when(teamRepository.findById(4L)).thenReturn(Optional.of(existing));
        when(teamRepository.save(existing)).thenReturn(existing);

        assertSame(existing, service.updateTeam(4L, dto));
        assertEquals("Updated", existing.getName());
        assertEquals("Red", existing.getColor1());
        assertEquals("White", existing.getColor2());
        assertEquals(30, existing.getPoints());
    }

    @Test
    void updateTeamThrowsWhenTeamDoesNotExist() {
        when(teamRepository.findById(4L)).thenReturn(Optional.empty());

        assertEquals("Team not found", assertThrows(EntityNotFoundException.class,
                () -> service.updateTeam(4L, teamDto(List.of()))).getMessage());
        verify(teamRepository, never()).save(any());
    }

    @Test
    void deleteTeamDissociatesPlayersBeforeDeletingWhenPresent() {
        when(teamRepository.existsById(4L)).thenReturn(true, false);

        assertTrue(service.deleteTeam(4L));
        assertFalse(service.deleteTeam(4L));
        var order = inOrder(playerRepository, teamRepository);
        order.verify(playerRepository).dissociatePlayersFromTeam(4L);
        order.verify(teamRepository).deleteById(4L);
    }

    private TeamDTO teamDto(List<Long> playerIds) {
        TeamDTO dto = new TeamDTO();
        dto.setPlayersId(playerIds);
        return dto;
    }
}
