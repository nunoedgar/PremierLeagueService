package org.nunostudios.premierleagueservice.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.nunostudios.premierleagueservice.DTO.PlayerDTO;
import org.nunostudios.premierleagueservice.Model.Player;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    PlayerDTO toDTO(Player player);

    @Mapping(source = "teamId", target = "team.id")
    Player toEntity(PlayerDTO playerDTO);

    List<PlayerDTO> toDTOList(List<Player> players);
}
