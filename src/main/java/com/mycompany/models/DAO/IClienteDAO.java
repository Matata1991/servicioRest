package com.mycompany.models.DAO;

import com.mycompany.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteDAO extends JpaRepository<Cliente, Long>{


    
}
