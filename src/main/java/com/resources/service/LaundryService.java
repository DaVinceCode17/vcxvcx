package com.resources.service;

import com.resources.dao.CustomerDAO;
import com.resources.dao.OrderDAO;
import com.resources.dao.PricingDAO;
import com.resources.model.Customer;
import com.resources.model.Order;
import com.resources.model.Pricing;
import java.sql.SQLException;
import java.util.List;

public class LaundryService {
    
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private PricingDAO pricingDAO;
    
    public LaundryService() {
        customerDAO = new CustomerDAO();
        orderDAO = new OrderDAO();
        pricingDAO = new PricingDAO();
    }
    
    // ===== CUSTOMER METHODS =====
    public Customer login(String contact, String password) throws SQLException {
        return customerDAO.findByContactAndPassword(contact, password);
    }
    
    public boolean register(Customer customer) throws SQLException {
        if (customerDAO.findByContact(customer.getContact()) != null) {
            throw new IllegalArgumentException("Contact already registered");
        }
        return customerDAO.save(customer);
    }
    
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.findAll();
    }
    
    public Customer getCustomerById(int id) throws SQLException {
        return customerDAO.findById(id);
    }
    
    public Customer getCustomerByContact(String contact) throws SQLException {
        return customerDAO.findByContact(contact);
    }
    
    // ===== ORDER METHODS =====
    public Order createOrder(Order order) throws SQLException {
        order.setQueueNumber(orderDAO.getNextQueueNumber());
        order.setStatus("set_pricing");
        return orderDAO.saveToSetPricing(order);
    }
    
    // ============================================================
    // FIXED: UPDATE WEIGHT & PRICE USING CUSTOMER_ID + SERVICES + CREATED_AT
    // ============================================================
    public boolean updateSetPricingWeightAndPrice(int customerId, String services, String timestamp, double weight, double price) throws SQLException {
        return orderDAO.updateSetPricingWeightAndPrice(customerId, services, timestamp, weight, price);
    }
    
    // ============================================================
    // FIXED: MOVE METHODS USING CUSTOMER_ID + SERVICES + CREATED_AT
    // ============================================================
    public Order moveToPending(int customerId, String services, String timestamp) throws SQLException {
        return orderDAO.moveToPending(customerId, services, timestamp);
    }
    
    public Order moveToWash(int customerId, String services, String timestamp) throws SQLException {
        return orderDAO.moveToWash(customerId, services, timestamp);
    }
    
    public Order moveToDry(int customerId, String services, String timestamp) throws SQLException {
        return orderDAO.moveToDry(customerId, services, timestamp);
    }
    
    public Order moveToIron(int customerId, String services, String timestamp) throws SQLException {
        return orderDAO.moveToIron(customerId, services, timestamp);
    }
    
    public Order moveToFold(int customerId, String services, String timestamp) throws SQLException {
        return orderDAO.moveToFold(customerId, services, timestamp);
    }
    
    public Order moveToForPickup(int customerId, String services, String timestamp) throws SQLException {
        return orderDAO.moveToForPickup(customerId, services, timestamp);
    }
    
    public Order moveToDeliver(int customerId, String services, String timestamp) throws SQLException {
        return orderDAO.moveToDeliver(customerId, services, timestamp);
    }
    
    public Order moveToClaimed(int customerId, String services, String timestamp) throws SQLException {
        return orderDAO.moveToClaimed(customerId, services, timestamp);
    }
    
    public Order moveToClaimedFromPickup(int customerId, String services, String timestamp) throws SQLException {
        return orderDAO.moveToClaimedFromPickup(customerId, services, timestamp);
    }
    
    public List<Order> getSetPricingOrders() throws SQLException {
        return orderDAO.getSetPricingOrders();
    }
    
    public List<Order> getOrdersByTable(String tableName) throws SQLException {
        return orderDAO.getOrdersByTable(tableName);
    }
    
    public List<Order> getLaundryLogs() throws SQLException {
        return orderDAO.getLaundryLogs();
    }
    
    public List<Order> getOrdersByCustomer(int customerId) throws SQLException {
        return orderDAO.getOrdersByCustomer(customerId);
    }
    
    // ===== PRICING METHODS =====
    public Pricing getPricing() throws SQLException {
        Pricing p = pricingDAO.getPricing();
        if (p == null) {
            pricingDAO.insertDefaultPricing();
            p = pricingDAO.getPricing();
        }
        return p;
    }
    
    public boolean updatePricing(Pricing pricing) throws SQLException {
        return pricingDAO.updatePricing(pricing);
    }
}