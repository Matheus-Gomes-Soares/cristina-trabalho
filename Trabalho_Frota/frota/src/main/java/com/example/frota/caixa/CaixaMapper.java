package com.example.frota.caixa;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.marca.Marca;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Mapper(componentModel = "spring")
public interface CaixaMapper {
    
    // Converte Entity para DTO (para preencher formulário de edição)
    @Mapping(target = "caixaId", source = "caixa.id")
    AtualizacaoCaixa toAtualizacaoDto(Caixa caixa);
    
    // Converte DTO para Entity (para criação NOVA - ignora ID)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "caixa", source = "caixaId", qualifiedByName = "idToCaixa")
    Caixa toEntityFromAtualizacao(AtualizacaoCaixa dto);
    
    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "id", ignore = true) // Não atualiza ID
    void updateEntityFromDto(AtualizacaoCaixa dto, @MappingTarget Caixa caixa);
    

 
}
