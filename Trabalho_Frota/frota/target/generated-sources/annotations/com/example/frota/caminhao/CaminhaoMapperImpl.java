package com.example.frota.caminhao;

import com.example.frota.marca.Marca;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-17T09:23:09-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (BellSoft)"
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
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }
        if ( dto.ano() != null ) {
            caminhao.setAno( dto.ano() );
        }
        if ( dto.comprimento() != null ) {
            caminhao.setComprimento( dto.comprimento() );
        }
        if ( dto.largura() != null ) {
            caminhao.setLargura( dto.largura() );
        }
        if ( dto.altura() != null ) {
            caminhao.setAltura( dto.altura() );
        }

        return caminhao;
    }

    @Override
    public void updateEntityFromDto(AtualizacaoCaminhao dto, Caminhao caminhao) {
        if ( dto == null ) {
            return;
        }

        caminhao.setMarca( idToMarca( dto.marcaId() ) );
        caminhao.setModelo( dto.modelo() );
        caminhao.setPlaca( dto.placa() );
        if ( dto.cargaMaxima() != null ) {
            caminhao.setCargaMaxima( dto.cargaMaxima() );
        }
        if ( dto.ano() != null ) {
            caminhao.setAno( dto.ano() );
        }
        if ( dto.comprimento() != null ) {
            caminhao.setComprimento( dto.comprimento() );
        }
        if ( dto.largura() != null ) {
            caminhao.setLargura( dto.largura() );
        }
        if ( dto.altura() != null ) {
            caminhao.setAltura( dto.altura() );
        }
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
