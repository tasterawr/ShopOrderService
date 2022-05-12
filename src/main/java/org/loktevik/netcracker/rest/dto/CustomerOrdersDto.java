package org.loktevik.netcracker.rest.dto;

import lombok.Data;

@Data
public class CustomerOrdersDto {
    private OrderDto[] orders;
}
