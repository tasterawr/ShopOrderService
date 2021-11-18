package org.loktevik.netcracker.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long offerId;
    private String name;
    private Date deliveryTime;

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;
    private Long customerId;
    private boolean paid;
}
