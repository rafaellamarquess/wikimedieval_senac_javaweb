package com.wiki.medieval.repository;

import com.wiki.medieval.model.MidiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MidiaRepository extends JpaRepository<MidiaModel, Long> {
    List<MidiaModel> findAllByTipo(MidiaModel.TipoMidia tipo);
    MidiaModel findByTitulo(String oSenhorDosAnéis);
}
