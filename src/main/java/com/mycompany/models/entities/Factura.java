package com.mycompany.models.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name="facturas")
public class Factura implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String obseevacion;
    
    @Column(name="crate_at")
    @Temporal(TemporalType.DATE)
    private Date crateAt;
    public Long calcularTotal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;
    
    @PrePersist
    public void prePersist(){
        crateAt = new Date();
    }
    
}
