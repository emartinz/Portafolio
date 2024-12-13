package com.test.t1.application.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.t1.adapter.out.repository.VersionRepository;
import com.test.t1.domain.model.entity.Version;

@Service
public class VersionService{
    @Autowired
    private VersionRepository repository;

    public List<Version> getAll() {
        return repository.findAll();
    } 

    public Optional<Version> getById(Long id){
        return repository.findById(id);
    }

    public Version add (Version object) {
        return repository.save(object);
    }

    public Version update(Long id, Version updatedObject) {
        Version currentObject = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró una version con id: " + id));
        
            currentObject.setVersion(updatedObject.getVersion());
            currentObject.setDescription(updatedObject.getDescription());
            
            return repository.save(currentObject);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
