package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order)
    {
       orderRepository.addOrder(order);
    }
    public void addPartner(DeliveryPartner deliveryPartner)
    {
        orderRepository.addPartner(deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId){
        orderRepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId){

       return orderRepository.getOrderById(orderId);
    }



    public DeliveryPartner getPartnerById(String partnerId){

        return orderRepository.getPartnerById(partnerId);
    }


    public Integer getOrderCountByPartnerId(String partnerId){

        return orderRepository.getOrderCountByPartnerId(partnerId) ;
    }



    public List<String> getOrdersByPartnerId (String partnerId)   //list of orders for delivery guy
    {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }


    public List<String> getAllOrders(){
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }

    public void deletePartnerById(String partnerId){
        orderRepository.deletePartnerById(partnerId);
    }


    public Integer getOrdersLeftAfterGivenTimeByPartnerId ( String time, String partnerId){
    return getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }


    public String getLastDeliveryTimeByPartnerId( String partnerId){
    return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }



    public void deleteOrderById (String orderId)
    {
        orderRepository.deleteOrderById(orderId);
    }
}
