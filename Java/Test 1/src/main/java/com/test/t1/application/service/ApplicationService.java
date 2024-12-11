package com.test.t1.application.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.t1.adapter.out.repository.ApplicationRepository;
import com.test.t1.domain.model.entity.Application;

@Service
public class ApplicationService{
    @Autowired
    private ApplicationRepository repository;
 
    public List<Application> getAll() {
        return repository.findAll();
    }

    public Optional<Application> getById(Long id){
        return repository.findById(id);
    }

    public Application add (Application object) {
        return repository.save(object);
    }

    public Application update(Long id, Application updatedObject) {
        Application currentObject = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró una aplicacion con id: " + id));
        
            currentObject.setCode(updatedObject.getCode());
            currentObject.setName(updatedObject.getName());
            currentObject.setDescription(updatedObject.getDescription());
            
            return repository.save(currentObject);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
