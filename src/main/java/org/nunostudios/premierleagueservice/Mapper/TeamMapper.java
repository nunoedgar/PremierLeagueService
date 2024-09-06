package org.nunostudios.premierleagueservice.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.nunostudios.premierleagueservice.DTO.TeamDTO;
import org.nunostudios.premierleagueservice.Model.Team;

import java.util.List;

@Mapper(componentModel = "spring", uses = PlayerMapper.class)
public interface TeamMapper {

    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    @Mapping(source = "players", target = "players")
    TeamDTO toDTO(Team team);

    @Mapping(source = "players", target = "players")
    Team toEntity(TeamDTO teamDTO);

    List<TeamDTO> toDTOList(List<Team> teams);
}
