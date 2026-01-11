package com.hotelmanagement.service;

import com.hotelmanagement.dao.BillDAO;
import com.hotelmanagement.dao.impl.BillDAOImpl;
import com.hotelmanagement.entity.Bill;

import java.util.List;

public class BillingService {

    private BillDAO billDAO = new BillDAOImpl();

    public void createBill(Bill bill) {
        billDAO.save(bill);
    }

    public double getTotalRevenue() {
        return billDAO.findAll().stream()
                .mapToDouble(Bill::getAmount)
                .sum();
    }
}
