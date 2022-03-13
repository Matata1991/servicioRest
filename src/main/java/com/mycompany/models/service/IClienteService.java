package com.mycompany.models.service;

import com.mycompany.models.entities.Cliente;
import java.util.List;
import org.springframework.data.domain.*;

public interface IClienteService {
    
    public List<Cliente> findAll();
    
    public Page<Cliente> findAll(Pageable pageable);
    
    public Cliente findById(Long id);
    
    public Cliente save(Cliente cliente);
    
    public void delete(Long id);
    
}
