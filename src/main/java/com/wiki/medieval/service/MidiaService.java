package com.wiki.medieval.service;

import com.wiki.medieval.model.MidiaModel;
import com.wiki.medieval.repository.MidiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidiaService {

    @Autowired
    private MidiaRepository midiaRepository;

    public MidiaModel cadastrarMidia(MidiaModel midiaModel) {
        return midiaRepository.save(midiaModel);
    }
}