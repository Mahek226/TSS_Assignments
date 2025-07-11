package com.tss.test;

import com.tss.model.CartEmptyException;
import com.tss.model.CartSerializer;
import com.tss.model.Customer;
import com.tss.model.LineItem;
import com.tss.model.Order;
import com.tss.model.Product;

import java.util.*;

public class ProductTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int lineItemId = 1;

        
        System.out.print("Enter Customer ID: ");
        int custId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String custName = sc.nextLine();

        Customer customer = new Customer(custId, custName);

        System.out.print("Enter Order ID: ");
        int orderId = sc.nextInt();
        Order order = new Order(orderId);

        
        List<Product> products = Arrays.asList(
                new Product(1, "Milk", 100, 10),
                new Product(2, "Laptop", 60000, 12),
                new Product(3, "Mouse", 1000, 5),
                new Product(4, "Book", 300, 15),
                new Product(5, "Phone", 20000, 8)
        );

        boolean adding = true;

        while (adding) {
            System.out.println("\nAvailable Products:");
            for (Product p : products) {
                System.out.println("ID: " + p.getId() + ", Name: " + p.getName()
                        + ", Price: ₹" + p.getPrice() + ", Discount: " + p.getDiscountPercent() + "%");
            }

            System.out.print("Enter Product ID to add: ");
            int pid = sc.nextInt();
            Product selected = products.stream()
                    .filter(p -> p.getId() == pid)
                    .findFirst()
                    .orElse(null);

            if (selected == null) {
                System.out.println("Invalid product ID.");
                continue;
            }

            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            order.addLineItem(new LineItem(lineItemId++, qty, selected));

            System.out.print("Add another item? (y/n): ");
            String more = sc.next().toLowerCase();
            adding = more.equals("y");
        }

        
        customer.addOrder(order);

       
        CartSerializer.saveCart(customer);

        
        System.out.println("\n*********** BILL ***************");
        System.out.println("Customer ID: " + customer.getId());
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Order ID: " + order.getId());
        System.out.println("Order Date: " + order.getDate());

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-10s%-12s%-10s%-10s%-15s%-15s%-15s%n",
                "LineItem", "Prod ID", "Item", "Qty", "Unit Price", "Discounted", "Total");
        System.out.println("---------------------------------------------------------------");

        for (LineItem item : order.getItems()) {
            Product p = item.getProduct();
            double discPrice = p.calculateDiscountedPrice();
            double total = item.calculateLineItemCost();

            System.out.printf("%-10d%-12d%-10s%-10d%-15.2f%-15.2f%-15.2f%n",
                    item.getId(), p.getId(), p.getName(), item.getQuantity(),
                    p.getPrice(), discPrice, total);
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("Total Order Price: ₹%.2f%n", order.calculateOrderPrice());

        System.out.println("\n********** Thank you for shopping with us! **********");
    }
}
