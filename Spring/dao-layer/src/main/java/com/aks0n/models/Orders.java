package com.aks0n.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private Date date;
    @ManyToOne
    private Customers customer;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private int amount;
}
