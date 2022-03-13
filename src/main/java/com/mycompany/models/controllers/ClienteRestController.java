package com.mycompany.models.controllers;

import com.mycompany.models.entities.Cliente;
import com.mycompany.models.service.IClienteService;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
class ClienteRestController {
    
    @Autowired
    private IClienteService clienteService;
    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }
    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 3);
        return clienteService.findAll(pageable);
    }
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        
        try{
            cliente = clienteService.findById(id);
        } catch(DataAccessException e){
            response.put("Mensaje", "Error al realizar la consulta en la base de datos.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        if (cliente == null){
            response.put("Mensaje", "El cliente ID: " .concat(id.toString().concat(", no se encuentra en la base.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }
    @PostMapping("/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result){
        
        Cliente newCliente = null;
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
                    
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        try{
            newCliente = clienteService.save(cliente);
        } catch(DataAccessException e){
            response.put("Mensaje", "Error al insertar un cliente nuevo en la base.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("Mensaje", "El cliente ha sido creado con exito.");
        response.put("Cliente", newCliente);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
    }
    @PutMapping("/clientes/{id}")	
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id){
        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteActualizado = null;
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
                    
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        if (clienteActual == null){ 
            response.put("Mensaje", "No se puede editar: el cliente ID: " .concat(id.toString().concat(", no se encuentra en la base.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
	try{
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCrateAt(cliente.getCrateAt());
            
            clienteActualizado = clienteService.save(clienteActual);   
            
        } catch(DataAccessException e){
            response.put("Mensaje", "Error al actualizar el cliente seleccionado en la base.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response.put("Mensaje", "El cliente ha actualizado con exito.");
            response.put("Cliente", clienteActualizado);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        try {
            clienteService.delete(id);

        }   catch(DataAccessException e){
            response.put("Mensaje", "Error al eliminar el cliente seleccionado de la base.");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        response.put("Mensaje", "El cliente ha sido eliminado con exito.");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
