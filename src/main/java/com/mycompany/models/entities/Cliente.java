package com.mycompany.models.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Entity 
@Table(name="clientes")
public class Cliente implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "No puede estar vacio")
    @Size(min=4, max=12, message = "El tamaño es entre 4 y 12 caracteres.")
    @Column(nullable=false)
    private String nombre;
    
    @NotEmpty(message = "No puede estar vacio")
    private String apellido;
    
    @NotEmpty(message = "No puede estar vacio")
    @Email(message = "Direccion con formato incorrecto.")
    @Column(nullable=false, unique=true)
    private String email;
    
    @Column(name="crate_at")
    @Temporal(TemporalType.DATE)
    private Date crateAt;
    
    @PrePersist
    public void prePersist(){
        crateAt = new Date();
    }
    private static final long serialVersionUID = 1L;
}
