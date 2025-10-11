package com.example.frota.caminhao;

import com.example.frota.marca.Marca;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T10:06:15-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CaminhaoMapperImpl implements CaminhaoMapper {

    @Override
    public AtualizacaoCaminhao toAtualizacaoDto(Caminhao caminhao) {
        if ( caminhao == null ) {
            return null;
        }

        Long marcaId = null;
        Long id = null;
        String modelo = null;
        String placa = null;
        Integer ano = null;
        Double cargaMaxima = null;
        Double largura = null;
        Double comprimento = null;
        Double altura = null;

        marcaId = caminhaoMarcaId( caminhao );
        id = caminhao.getId();
        modelo = caminhao.getModelo();
        placa = caminhao.getPlaca();
        ano = caminhao.getAno();
        cargaMaxima = caminhao.getCargaMaxima();
        largura = caminhao.getLargura();
        comprimento = caminhao.getComprimento();
        altura = caminhao.getAltura();

        AtualizacaoCaminhao atualizacaoCaminhao = new AtualizacaoCaminhao( id, modelo, placa, ano, cargaMaxima, marcaId, largura, comprimento, altura );

        return atualizacaoCaminhao;
    }

    @Override
    public Caminhao toEntityFromAtualizacao(AtualizacaoCaminhao dto) {
        if ( dto == null ) {
            return null;
        }

        Caminhao caminhao = new Caminhao();

        caminhao.setMarca( idToMarca( dto.marcaId() ) );
        if ( dto.altura() != null ) {
            caminhao.setAltura( dto.altura() );
        }
        if ( dto.ano() != null ) {
            caminhao.setAno( dto.ano() );
        }
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }
        if ( dto.comprimento() != null ) {
            caminhao.setComprimento( dto.comprimento() );
        }
        if ( dto.largura() != null ) {
            caminhao.setLargura( dto.largura() );
        }
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );

        return caminhao;
    }

    @Override
    public void updateEntityFromDto(AtualizacaoCaminhao dto, Caminhao caminhao) {
        if ( dto == null ) {
            return;
        }

        caminhao.setMarca( idToMarca( dto.marcaId() ) );
        if ( dto.altura() != null ) {
            caminhao.setAltura( dto.altura() );
        }
        if ( dto.ano() != null ) {
            caminhao.setAno( dto.ano() );
        }
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }
        if ( dto.comprimento() != null ) {
            caminhao.setComprimento( dto.comprimento() );
        }
        if ( dto.largura() != null ) {
            caminhao.setLargura( dto.largura() );
        }
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );
    }

    private Long caminhaoMarcaId(Caminhao caminhao) {
        if ( caminhao == null ) {
            return null;
        }
        Marca marca = caminhao.getMarca();
        if ( marca == null ) {
            return null;
        }
        long id = marca.getId();
        return id;
    }
}
