package org.nunostudios.premierleagueservice.Service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nunostudios.premierleagueservice.DTO.PlayerDTO;
import org.nunostudios.premierleagueservice.Mapper.PlayerMapper;
import org.nunostudios.premierleagueservice.Model.Player;
import org.nunostudios.premierleagueservice.Model.Team;
import org.nunostudios.premierleagueservice.Repository.PlayerRepository;
import org.nunostudios.premierleagueservice.Repository.TeamRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock private TeamRepository teamRepository;
    @Mock private PlayerRepository playerRepository;
    @Mock private PlayerMapper playerMapper;
    @InjectMocks private PlayerServiceImpl service;

    @Test
    void createPlayerWithoutTeamMapsAndSavesPlayer() {
        PlayerDTO dto = playerDto(null);
        Player mapped = new Player();
        Player saved = new Player();
        when(playerMapper.toEntity(dto)).thenReturn(mapped);
        when(playerRepository.save(mapped)).thenReturn(saved);

        assertSame(saved, service.createPlayer(dto));
        verify(playerRepository).save(mapped);
        verifyNoInteractions(teamRepository);
    }

    @Test
    void createPlayerWithTeamAssociatesTeamBeforeSaving() {
        PlayerDTO dto = playerDto(3L);
        Player mapped = new Player();
        Team team = new Team();
        when(playerMapper.toEntity(dto)).thenReturn(mapped);
        when(teamRepository.findById(3L)).thenReturn(Optional.of(team));
        when(playerRepository.save(mapped)).thenReturn(mapped);

        service.createPlayer(dto);

        assertSame(team, mapped.getTeam());
        verify(playerRepository).save(mapped);
    }

    @Test
    void createPlayerWithUnknownTeamThrows() {
        PlayerDTO dto = playerDto(3L);
        when(playerMapper.toEntity(dto)).thenReturn(new Player());
        when(teamRepository.findById(3L)).thenReturn(Optional.empty());

        assertEquals("Team not found", assertThrows(EntityNotFoundException.class,
                () -> service.createPlayer(dto)).getMessage());
        verifyNoInteractions(playerRepository);
    }

    @Test
    void delegatesPlayerQueriesToRepository() {
        List<Player> players = List.of(new Player());
        Player player = players.get(0);
        when(playerRepository.findAll()).thenReturn(players);
        when(playerRepository.findById(8L)).thenReturn(Optional.of(player));

        assertSame(players, service.getPlayers());
        assertEquals(Optional.of(player), service.getPlayerById(8L));
    }

    @Test
    void updatePlayerChangesFieldsAndTeam() {
        Player existing = new Player();
        Team newTeam = new Team();
        PlayerDTO dto = playerDto(5L);
        dto.setName("Updated");
        dto.setAge(25);
        when(playerRepository.findById(8L)).thenReturn(Optional.of(existing));
        when(teamRepository.findById(5L)).thenReturn(Optional.of(newTeam));
        when(playerRepository.save(existing)).thenReturn(existing);

        assertSame(existing, service.updatePlayer(8L, dto));
        assertEquals("Updated", existing.getName());
        assertEquals(25, existing.getAge());
        assertSame(newTeam, existing.getTeam());
    }

    @Test
    void updatePlayerThrowsWhenPlayerDoesNotExist() {
        when(playerRepository.findById(8L)).thenReturn(Optional.empty());

        assertEquals("Player not found", assertThrows(EntityNotFoundException.class,
                () -> service.updatePlayer(8L, playerDto(5L))).getMessage());
        verify(playerRepository, never()).save(any());
    }

    @Test
    void updatePlayerThrowsWhenTeamDoesNotExist() {
        when(playerRepository.findById(8L)).thenReturn(Optional.of(new Player()));
        when(teamRepository.findById(5L)).thenReturn(Optional.empty());

        assertEquals("Team not found", assertThrows(EntityNotFoundException.class,
                () -> service.updatePlayer(8L, playerDto(5L))).getMessage());
        verify(playerRepository, never()).save(any());
    }

    @Test
    void deletePlayerOnlyDeletesWhenPresent() {
        when(playerRepository.existsById(8L)).thenReturn(true, false);

        assertTrue(service.deletePlayer(8L));
        assertFalse(service.deletePlayer(8L));
        verify(playerRepository).deleteById(8L);
    }

    private PlayerDTO playerDto(Long teamId) {
        PlayerDTO dto = new PlayerDTO();
        dto.setTeamId(teamId);
        dto.setName("Player");
        dto.setAge(20);
        return dto;
    }
}
