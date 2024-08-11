import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsViewere {
    private JFrame frame;
    private JLabel productLabel;
    private JTextArea infoTextArea;
    private List<Product> products;
    private int currentProductIndex;
    private String reviewsFilePath; // Added field for reviews file path
    private String wishlistFilePath; // Added field for wishlist file path

    public ProductDetailsViewere(String filePath) {
        frame = new JFrame("Product Details Viewer");
        productLabel = new JLabel();
        productLabel.setHorizontalAlignment(JLabel.CENTER);
        productLabel.setVerticalAlignment(JLabel.CENTER);

        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);

        JButton toggleButton = new JButton("Next Product");
        toggleButton.addActionListener(e -> toggleProduct());

        // Add Review button
        JButton addReviewButton = new JButton("Add Review");
        addReviewButton.addActionListener(e -> addReview());

        // Add Add to Wishlist button
        JButton addToWishlistButton = new JButton("Add to Wishlist");
        addToWishlistButton.addActionListener(e -> addToWishlist());

        // Add Show Wishlist button
        JButton showWishlistButton = new JButton("Show Wishlist");
        showWishlistButton.addActionListener(e -> showWishlist());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(toggleButton);
        buttonPanel.add(addReviewButton);
        buttonPanel.add(addToWishlistButton);
        buttonPanel.add(showWishlistButton);

        frame.setLayout(new BorderLayout());
        frame.add(productLabel, BorderLayout.CENTER);
        frame.add(infoTextArea, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        // Set reviews file path based on the product category
        reviewsFilePath = filePath.replace("_details.txt", "_reviews.txt");

        // Set wishlist file path based on the product category
        wishlistFilePath = "wishlistfile.txt";

        loadProductDetailsFromFile(filePath);
        updateProductInfo();
    }

    private void toggleProduct() {
        currentProductIndex = (currentProductIndex + 1) % products.size();
        updateProductInfo();
    }

    private void updateProductInfo() {
        if (!products.isEmpty()) {
            Product currentProduct = products.get(currentProductIndex);
            infoTextArea.setText(currentProduct.toString());

            // Show reviews
            String reviews = loadReviewsFromFile(currentProduct.getProdId());
            infoTextArea.append("\n\nReviews:\n" + reviews);

            // Assuming the last part of each line is the image path
            ImageIcon productImage = getScaledImageIcon(currentProduct.getImagePath(), 200, 150);
            productLabel.setIcon(productImage);
        } else {
            infoTextArea.setText("No product details available.");
            productLabel.setIcon(null);
        }
    }

    private ImageIcon getScaledImageIcon(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void loadProductDetailsFromFile(String filePath) {
        products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 6) {  // Ensure at least six parts to construct a product
                    String prodId = parts[0];
                    String productName = parts[1];
                    String brand = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    int stock = Integer.parseInt(parts[4]);
                    String imagePath = parts[5];

                    Product product = new Product(prodId, productName, brand, price, stock, imagePath);
                    products.add(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadReviewsFromFile(String prodId) {
        StringBuilder reviews = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(reviewsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+", 2);
                if (parts.length >= 2 && parts[0].equals(prodId)) {
                    reviews.append(parts[1]).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews.toString();
    }

    private void addReview() {
        if (!products.isEmpty()) {
            Product currentProduct = products.get(currentProductIndex);
            String review = JOptionPane.showInputDialog("Enter your review:");

            try (PrintWriter writer = new PrintWriter(new FileWriter(reviewsFilePath, true))) {
                writer.println(currentProduct.getProdId() + " " + review);
                showMessage("Review added successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                showMessage("Error adding review.");
            }

            updateProductInfo();
        }
    }

    private void addToWishlist() {
        if (!products.isEmpty()) {
            Product currentProduct = products.get(currentProductIndex);

            // Prompt user for username
            String username = JOptionPane.showInputDialog("Enter your username:");

            if (username != null && !username.trim().isEmpty()) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(wishlistFilePath, true))) {
                    writer.println(username + " " + currentProduct.getProductName() + " " + currentProduct.getPrice());
                    showMessage("Added to Wishlist!");
                } catch (IOException e) {
                    e.printStackTrace();
                    showMessage("Error adding to Wishlist.");
                }
            } else {
                showMessage("Username cannot be empty. Please try again.");
            }
        }
    }

    private void showWishlist() {
        // Prompt user for username
        String username = JOptionPane.showInputDialog("Enter your username:");
    
        if (username != null && !username.trim().isEmpty()) {
            displayWishlist(username);
        } else {
            showMessage("Username cannot be empty. Please try again.");
        }
    }
    
    private void displayWishlist(String username) {
        JFrame wishlistFrame = new JFrame("Wishlist for " + username);
        JPanel wishlistPanel = new JPanel();
        wishlistPanel.setLayout(new BoxLayout(wishlistPanel, BoxLayout.Y_AXIS));
    
        try (BufferedReader reader = new BufferedReader(new FileReader(wishlistFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 3 && parts[0].equals(username)) {
                    String productName = parts[1];
                    double price = Double.parseDouble(parts[2]);
    
                    // Create a panel for each wishlist item
                    JPanel itemPanel = new JPanel();
                    itemPanel.setLayout(new BorderLayout());
    
                    // Display wishlist item information
                    JLabel itemLabel = new JLabel("Product: " + productName + ", Price: " + price);
                    itemPanel.add(itemLabel, BorderLayout.CENTER);
    
                    // Add Remove from Wishlist button
                    JButton removeFromWishlistButton = new JButton("Remove from Wishlist");
                    removeFromWishlistButton.addActionListener(e -> removeFromWishlist(username, productName, price, wishlistFrame));
                    itemPanel.add(removeFromWishlistButton, BorderLayout.EAST);
    
                    wishlistPanel.add(itemPanel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (wishlistPanel.getComponentCount() > 0) {
            // Add Pay button
            JButton payButton = new JButton("Pay");
            payButton.addActionListener(e -> performPayment(username, wishlistPanel));
            wishlistPanel.add(payButton);
    
            // Display wishlist in the new frame
            JScrollPane scrollPane = new JScrollPane(wishlistPanel);
            scrollPane.setPreferredSize(new Dimension(300, 200));
            wishlistFrame.add(scrollPane);
            wishlistFrame.pack();
            wishlistFrame.setLocationRelativeTo(frame);  // Set location relative to the main frame
            wishlistFrame.setVisible(true);
        } else {
            showMessage("Wishlist is empty for " + username);
        }
    }
    
    private void performPayment(String username, JPanel wishlistPanel) {
        double totalPrice = calculateTotalPrice(wishlistPanel);
    
        // Prompt user to enter credit card details
        String cardNumber = JOptionPane.showInputDialog("Enter your credit card number:");
        String expiryDate = JOptionPane.showInputDialog("Enter your credit card expiry date (MM/YY):");
        String cardholderName = JOptionPane.showInputDialog("Enter your credit cardholder name:");
        String cvv = JOptionPane.showInputDialog("Enter your CVV number:");
    
        if (cardNumber != null && !cardNumber.trim().isEmpty()
                && expiryDate != null && !expiryDate.trim().isEmpty()
                && cardholderName != null && !cardholderName.trim().isEmpty()
                && cvv != null && !cvv.trim().isEmpty()) {
    
           
    
            // Display payment successful message
            showMessage("Payment successful!");
    
            // Display purchased product information
            displayPurchasedProducts(username, wishlistPanel);
    
            // Clear the wishlist after successful payment
            clearWishlist(username, wishlistPanel);
        } else {
            showMessage("Credit card details cannot be empty. Payment failed.");
        }
    }
    private double calculateTotalPrice(JPanel wishlistPanel) {
        double totalPrice = 0.0;
    
        // Iterate over the components in the wishlistPanel to calculate the total price
        for (Component component : wishlistPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel itemPanel = (JPanel) component;
                for (Component subComponent : itemPanel.getComponents()) {
                    if (subComponent instanceof JLabel) {
                        String text = ((JLabel) subComponent).getText();
                        // Extract price from the text (assuming the format "Product: <productName>, Price: <price>")
                        String[] parts = text.split("Price: ");
                        if (parts.length >= 2) {
                            double price = Double.parseDouble(parts[1]);
                            totalPrice += price;
                        }
                    }
                }
            }
        }
    
        return totalPrice;
    }
    
    private void displayPurchasedProducts(String username, JPanel wishlistPanel) {
        // Display purchased product information in a new frame
        JFrame purchaseFrame = new JFrame("Purchased Products for " + username);
        JPanel purchasePanel = new JPanel();
        purchasePanel.setLayout(new BoxLayout(purchasePanel, BoxLayout.Y_AXIS));
    
        // Iterate over the components in the wishlistPanel to extract product information
        for (Component component : wishlistPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel itemPanel = (JPanel) component;
                for (Component subComponent : itemPanel.getComponents()) {
                    if (subComponent instanceof JLabel) {
                        String text = ((JLabel) subComponent).getText();
                        JLabel purchasedLabel = new JLabel("Purchased: " + text);
                        purchasePanel.add(purchasedLabel);
                    }
                }
            }
        }
    
        // Display purchased product information in a scrollable dialog
        JScrollPane scrollPane = new JScrollPane(purchasePanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        purchaseFrame.add(scrollPane);
        purchaseFrame.pack();
        purchaseFrame.setLocationRelativeTo(frame);  // Set location relative to the main frame
        purchaseFrame.setVisible(true);
    }
    
    private void clearWishlist(String username, JPanel wishlistPanel) {
        // Remove purchased items from the wishlistPanel
        List<Component> componentsToRemove = new ArrayList<>();
        for (Component component : wishlistPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel itemPanel = (JPanel) component;
                componentsToRemove.add(itemPanel);
            }
        }
        for (Component component : componentsToRemove) {
            wishlistPanel.remove(component);
        }
    
        // Refresh the wishlistPanel
        wishlistPanel.revalidate();
        wishlistPanel.repaint();
    
        // Remove purchased items from the wishlist file
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(wishlistFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3 && parts[0].equals(username)) {
                        continue;  // Skip the line for purchased item
                    }
                    lines.add(line);
                }
            }
    
            try (PrintWriter writer = new PrintWriter(new FileWriter(wishlistFilePath))) {
                for (String line : lines) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error clearing purchased items from Wishlist.");
        }
    }
    
    private void removeFromWishlist(String username, String productName, double price, JFrame wishlistFrame) {
        try {
            // Read all lines from the wishlist file
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(wishlistFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
    
            // Rewrite the wishlist file excluding the item to be removed
            try (PrintWriter writer = new PrintWriter(new FileWriter(wishlistFilePath))) {
                for (String line : lines) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3 && parts[0].equals(username)
                            && parts[1].equals(productName) && Double.parseDouble(parts[2]) == price) {
                        // Skip the line to remove from the wishlist
                        continue;
                    }
                    writer.println(line);
                }
            }
    
            // Close the previous wishlist frame
            wishlistFrame.dispose();
    
            // Update the displayed wishlist
            displayWishlist(username);
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error removing item from Wishlist.");
        }
    }
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductDetailsViewere("e_details.txt").show());
    }

    static class Product {
        private String prodId;
        private String productName;
        private String brand;
        private double price;
        private int stock;
        private String imagePath;

        public Product(String prodId, String productName, String brand, double price, int stock, String imagePath) {
            this.prodId = prodId;
            this.productName = productName;
            this.brand = brand;
            this.price = price;
            this.stock = stock;
            this.imagePath = imagePath;
        }

        public String getProdId() {
            return prodId;
        }

        public String getProductName() {
            return productName;
        }

        public String getBrand() {
            return brand;
        }

        public double getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        public String getImagePath() {
            return imagePath;
        }

        @Override
        public String toString() {
            return String.format("Product ID: %s\nName: %s\nBrand: %s\nPrice: %.2f\nStock: %d",
                    prodId, productName, brand, price, stock);
        }
    }
}
