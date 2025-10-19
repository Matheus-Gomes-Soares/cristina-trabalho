package com.example.frota.solicitacaoDeTransporte;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.example.frota.caixa.Caixa;

@Mapper(componentModel = "spring")
public interface SolicitacaoDeTransporteMapper {

    // Converte Entity para DTO (para preencher formulário de edição)
    @Mapping(target = "caixaId", source = "caixa.id")
    @Mapping(target = "nomeProduto", source = "produto.nomeProduto")
    @Mapping(target = "comprimento", source = "produto.comprimento")
    @Mapping(target = "largura", source = "produto.largura")
    @Mapping(target = "altura", source = "produto.altura")
    @Mapping(target = "peso", source = "produto.peso")
    AtualizacaoSolicitacaoDeTransporte toAtualizacaoDto(SolicitacaoDeTransporte solicitacao);

    // Converte DTO para Entity (para criação NOVA - ignora ID)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto.nomeProduto", source = "nomeProduto")
    @Mapping(target = "produto.comprimento", source = "comprimento")
    @Mapping(target = "produto.largura", source = "largura")
    @Mapping(target = "produto.altura", source = "altura")
    @Mapping(target = "produto.peso", source = "peso")
    @Mapping(target = "caixa", source = "caixaId", qualifiedByName = "idToCaixa")
    SolicitacaoDeTransporte toEntityFromAtualizacao(AtualizacaoSolicitacaoDeTransporte dto);

    // Atualiza Entity existente com dados do DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto.nomeProduto", source = "nomeProduto")
    @Mapping(target = "produto.comprimento", source = "comprimento")
    @Mapping(target = "produto.largura", source = "largura")
    @Mapping(target = "produto.altura", source = "altura")
    @Mapping(target = "produto.peso", source = "peso")
    @Mapping(target = "caixa", source = "caixaId", qualifiedByName = "idToCaixa")
    void updateEntityFromDto(AtualizacaoSolicitacaoDeTransporte dto, @MappingTarget SolicitacaoDeTransporte solicitacao);

    // Método auxiliar para converter caixaId em objeto Caixa
    @Named("idToCaixa")
    default Caixa idToCaixa(Long caixaId) {
        if (caixaId == null) return null;
        Caixa caixa = new Caixa();
        caixa.setId(caixaId);
        return caixa;
    }
}
