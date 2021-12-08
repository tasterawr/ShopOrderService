package org.loktevik.netcracker.rest.dto;

import lombok.Data;

@Data
public class OrderInfo {
    private Long orderId;
    private Long offerId;
    private String name;
    private String deliveryTime;
    private Integer status;
    private Long customerId;
    private Integer amount;
    private Boolean isPaid;
}
