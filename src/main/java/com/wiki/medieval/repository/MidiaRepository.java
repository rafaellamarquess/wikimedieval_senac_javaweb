package com.wiki.medieval.repository;

import com.wiki.medieval.model.MidiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MidiaRepository extends JpaRepository<MidiaModel, Long> {
    List<MidiaModel> findByTipo(MidiaModel.TipoMidia tipo);
}
