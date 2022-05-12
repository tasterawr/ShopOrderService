package org.loktevik.netcracker.rest.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long orderId;
    private Long offerId;
    private String name;
    private String offerName;
    private String deliveryTime;
    private String status;
    private Long customerId;
    private Integer amount;
    private Boolean isPaid;
    private Double fullPrice;
}
