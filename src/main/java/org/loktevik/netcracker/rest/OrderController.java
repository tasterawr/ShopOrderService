package org.loktevik.netcracker.rest;

import lombok.RequiredArgsConstructor;
import org.loktevik.netcracker.domain.Order;
import org.loktevik.netcracker.domain.Status;
import org.loktevik.netcracker.rest.dto.CustomerOrdersDto;
import org.loktevik.netcracker.rest.dto.OrderDto;
import org.loktevik.netcracker.rest.utils.URLProvider;
import org.loktevik.netcracker.service.DateProvider;
import org.loktevik.netcracker.service.OrderService;
import org.loktevik.netcracker.service.StatusService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final DateProvider dateProvider;
    private final StatusService statusService;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> getOrder(HttpServletRequest request, @PathVariable Long id) {
        Order order = orderService.getById(id);
        OrderDto orderDto = getOrderDto(request, order);

        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping(value = "customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerOrdersDto> getCustomerOrders(HttpServletRequest request, @PathVariable Long id) {
        orderService.updateOrdersStatuses();
        CustomerOrdersDto dto = getOrders(request, id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAll();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> saveOrder(@RequestBody Order order){
        orderService.saveOrder(order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping(value="/customer/{id}/new-order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createCustomerOrder(@RequestBody OrderDto info, @PathVariable Long id){
        Order order = createNewOrder(info, id);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        orderService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> updateOrder(@RequestBody Order order){
        orderService.updateOrder(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping(value = "{orderId}/status/{statusId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setStatusToOrder(@PathVariable Long orderId, @PathVariable Long statusId){
        orderService.setStatus(orderId, statusId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("{id}/pay")
    public ResponseEntity<Order> payOrder(@PathVariable Long id){
        Order order = orderService.getById(id);
        Status status = statusService.getByName("В процессе доставки");
        orderService.setStatus(order.getId(), status.getId());
        order.setPaid(true);
        orderService.updateOrder(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> sendOrder(){
        Order order1 = new Order();
        order1.setName("Заказ 1");
        order1.setCustomerId(1L);
        order1.setPaid(false);

        orderService.saveOrder(order1);

        return new ResponseEntity<>(order1, HttpStatus.OK);
    }

    public Order createNewOrder(OrderDto info, Long customerId){
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOfferId(info.getOfferId());
        order.setPaid(false);
        order.setAmount(info.getAmount());
        order.setDeliveryTime(dateProvider.getDeliveryDate());
        order.setStatus(statusService.getById(1L));
        orderService.saveOrder(order);
        order.setName("ЗАКАЗ №" + order.getId());
        orderService.updateOrder(order);

        return order;
    }

    public CustomerOrdersDto getOrders(HttpServletRequest request, Long customerId){
        List<Order> orders = orderService.getByCustomerId(customerId);
        List<OrderDto> orderDtos = new ArrayList<>();

        orders.stream().forEach(order -> {
            orderDtos.add(getOrderDto(request, order));
        });

        CustomerOrdersDto ordersDto = new CustomerOrdersDto();
        ordersDto.setOrders(orderDtos.toArray(new OrderDto[0]));

        return ordersDto;
    }

    public HttpHeaders formHeadersWithAuth(HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(request.getHeader("Authorization").substring("Bearer ".length()));
        return headers;
    }

    public OrderDto getOrderDto(HttpServletRequest request, Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getId());
        orderDto.setOfferId(order.getOfferId());
        orderDto.setIsPaid(order.getPaid());
        orderDto.setAmount(order.getAmount());
        orderDto.setCustomerId(order.getCustomerId());
        orderDto.setDeliveryTime(order.getDeliveryTime().toString());
        orderDto.setName(order.getName());
        orderDto.setStatus(order.getStatus().getName());
        String url = URLProvider.getOfferServiceUrl() + String.format("/offers/%d/price", order.getOfferId());
        ResponseEntity<String> price = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(String.class, formHeadersWithAuth(request)), String.class);
        orderDto.setFullPrice(Double.parseDouble(price.getBody()) * order.getAmount());
        url = URLProvider.getOfferServiceUrl() + String.format("/offers/%d/name", order.getOfferId());
        ResponseEntity<String> offerName = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(String.class, formHeadersWithAuth(request)), String.class);
        orderDto.setOfferName(offerName.getBody());

        return orderDto;
    }
}
