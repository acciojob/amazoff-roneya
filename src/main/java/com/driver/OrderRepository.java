package com.driver;

import io.swagger.models.auth.In;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderHashMap;
    HashMap<String,DeliveryPartner> orderIdAndDelivery; //orderId and its partner
    HashMap<String, DeliveryPartner> deliveryPartnerHashMap;
    HashMap<String, List<String>> orderAndDeliveryPartnerPair; //ids of partner and list of order ids

    public OrderRepository()
    {
        orderHashMap =  new HashMap<>();
        deliveryPartnerHashMap = new HashMap<>();
        orderAndDeliveryPartnerPair = new HashMap<>();
        orderIdAndDelivery =  new HashMap<>();
    }

    public void addOrder(Order order)
    {
        orderHashMap.put(order.getId(),order);
    }

    public void addPartner(DeliveryPartner deliveryPartner)
    {

            deliveryPartnerHashMap.put(deliveryPartner.getId(),deliveryPartner);
    }


    public void addOrderPartnerPair(String orderId, String partnerId){

        if(orderHashMap.containsKey(orderId) && deliveryPartnerHashMap.containsKey(partnerId)){
            if(!orderAndDeliveryPartnerPair.containsKey(partnerId))
                orderAndDeliveryPartnerPair.put(partnerId, new ArrayList<>()); //0th order assigned to deliveryPartner

            orderAndDeliveryPartnerPair.get(partnerId).add(orderId);

            DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(partnerId);
            deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
            orderIdAndDelivery.put(orderId, deliveryPartnerHashMap.get(partnerId));  //id and partnerId linkage
        }



    }

    public Order getOrderById(String orderId){

        return orderHashMap.get(orderId);
    }





    public DeliveryPartner getPartnerById(String partnerId){

        return deliveryPartnerHashMap.get(partnerId);
    }


    public Integer getOrderCountByPartnerId(String partnerId){

        if(orderAndDeliveryPartnerPair.containsKey(partnerId))
            return orderAndDeliveryPartnerPair.get(partnerId).size() ;
        return 0;
    }



    public List<String> getOrdersByPartnerId (String partnerId)
    {

        if(orderAndDeliveryPartnerPair.containsKey(partnerId))
            return orderAndDeliveryPartnerPair.get(partnerId);
        return new ArrayList<>(); //empty
    }

    public List<String> getAllOrders(){
        List<String> l = new ArrayList<>();
    for(String x : orderHashMap.keySet())
    {
        l.add(orderHashMap.get(x).getId());
    }
    return l;
    }


    public Integer getCountOfUnassignedOrders(){
    return orderHashMap.size() - orderIdAndDelivery.size();
    }



    public Integer getOrdersLeftAfterGivenTimeByPartnerId ( String time, String partnerId){
        String s [] = time.split(":");
        int t =  Integer.valueOf(s[0])*60 + Integer.valueOf(s[1]);
        List<String> l = orderAndDeliveryPartnerPair.get(partnerId);
        int c =0;
        for(String x : l)
        {
            if(orderHashMap.get(x).getDeliveryTime() > t) c++;
        }
        return c;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
    if(orderAndDeliveryPartnerPair.containsKey(partnerId)) {
        Integer max = 0;
        List<String> l = orderAndDeliveryPartnerPair.get(partnerId);
        for (String s : l) {
            if (orderHashMap.containsKey(s)) {
                if (orderHashMap.get(s).getDeliveryTime() > max)
                    max = orderHashMap.get(s).getDeliveryTime();
            }
        }


        int a = max % 60;
        int b = max / 60;
       return b+":"+a;
    }
    else return "";

    }


    public void deletePartnerById(String partnerId){
        if(deliveryPartnerHashMap.containsKey(partnerId))
            deliveryPartnerHashMap.remove(partnerId); //delivery map removed
        if (orderAndDeliveryPartnerPair.containsKey(partnerId))
            orderAndDeliveryPartnerPair.remove(partnerId); //his order list is removed

    }

    public void deleteOrderById (String orderId)
    {
        if(orderHashMap.containsKey(orderId))
            orderHashMap.remove(orderId);
        if(orderIdAndDelivery.containsKey(orderId))
            orderIdAndDelivery.remove(orderId);
    }



}
