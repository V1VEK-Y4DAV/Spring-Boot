package com.mohit.demo.DependencyInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

//    @Autowired
    PaymentService paymentService;

//    @Autowired
    OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

//    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        paymentService.payment();
        System.out.println("Order placed");
    }
}
