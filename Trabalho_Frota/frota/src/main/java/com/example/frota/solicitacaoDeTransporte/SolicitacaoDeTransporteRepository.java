package com.example.frota.solicitacaoDeTransporte;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.caixa.Caixa;

@Repository
@Transactional
public interface SolicitacaoDeTransporteRepository extends JpaRepository<SolicitacaoDeTransporte, Long>{

  
}
