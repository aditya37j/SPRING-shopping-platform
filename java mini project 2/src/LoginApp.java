import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.TreeMap;

public class LoginApp extends JFrame {
    private JTextField loginUsernameField, loginPasswordField;
    private JRadioButton loginCustomerRadioButton, loginRetailerRadioButton;
    private JPanel loginPanel, registerPanel;

    private JTextField registerUsernameField, registerEmailField, registerAgeField, registerAddressField,
            registerMobileField,
            registerCompanyNameField, registerProductCategoryField, registerNumProductsField;
    private JPasswordField registerPasswordField;
    private JRadioButton registerCustomerRadioButton, registerRetailerRadioButton;

    private static final String CUSTOMER_FILE_PATH = "customer_file.txt";
    private static final String RETAILER_FILE_PATH = "retailer_file.txt";

    private TreeMap<String, User> userDatabase = new TreeMap<>();

    public LoginApp() {
        setTitle("Login/Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));

        loadUsersFromFile(CUSTOMER_FILE_PATH, "customer");
        loadUsersFromFile(RETAILER_FILE_PATH, "retailer");

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.setBackground(new Color(46, 139, 87)); // Green color
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        registerButton.setBackground(new Color(0, 102, 204)); // Blue color
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginPanel();
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterPanel();
                register();
            }
        });

        loginPanel = createLoginPanel();
        registerPanel = createRegisterPanel();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBackground(new Color(240, 240, 240));

        panel.add(new JLabel("Username:"));
        loginUsernameField = new JTextField();
        panel.add(loginUsernameField);

        panel.add(new JLabel("Password:"));
        loginPasswordField = new JPasswordField();
        panel.add(loginPasswordField);

        panel.add(new JLabel("User Type:"));
        loginCustomerRadioButton = new JRadioButton("Customer");
        loginRetailerRadioButton = new JRadioButton("Retailer");

        ButtonGroup loginTypeGroup = new ButtonGroup();
        loginTypeGroup.add(loginCustomerRadioButton);
        loginTypeGroup.add(loginRetailerRadioButton);

        panel.add(loginCustomerRadioButton);
        panel.add(loginRetailerRadioButton);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 2));
        panel.setBackground(new Color(240, 240, 240));

        panel.add(new JLabel("Username:"));
        registerUsernameField = new JTextField();
        panel.add(registerUsernameField);

        panel.add(new JLabel("Password:"));
        registerPasswordField = new JPasswordField();
        panel.add(registerPasswordField);

        panel.add(new JLabel("Email:"));
        registerEmailField = new JTextField();
        panel.add(registerEmailField);

        panel.add(new JLabel("Age:"));
        registerAgeField = new JTextField();
        panel.add(registerAgeField);

        panel.add(new JLabel("Address:"));
        registerAddressField = new JTextField();
        panel.add(registerAddressField);

        panel.add(new JLabel("Mobile:"));
        registerMobileField = new JTextField();
        panel.add(registerMobileField);

        panel.add(new JLabel("Company Name:"));
        registerCompanyNameField = new JTextField();
        panel.add(registerCompanyNameField);

        panel.add(new JLabel("Product Category:"));
        registerProductCategoryField = new JTextField();
        panel.add(registerProductCategoryField);

        panel.add(new JLabel("Number of Products:"));
        registerNumProductsField = new JTextField();
        panel.add(registerNumProductsField);

        panel.add(new JLabel("User Type:"));
        registerCustomerRadioButton = new JRadioButton("Customer");
        registerRetailerRadioButton = new JRadioButton("Retailer");

        ButtonGroup registerTypeGroup = new ButtonGroup();
        registerTypeGroup.add(registerCustomerRadioButton);
        registerTypeGroup.add(registerRetailerRadioButton);

        panel.add(registerCustomerRadioButton);
        panel.add(registerRetailerRadioButton);

        return panel;
    }

    private void showLoginPanel() {
        remove(registerPanel);
        add(loginPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showRegisterPanel() {
        remove(loginPanel);
        add(registerPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void login() {
        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getText());

        if (userDatabase.containsKey(username)) {
            User user = userDatabase.get(username);

            if (user.getPassword().equals(password) && user instanceof Customer) {
                SwingUtilities.invokeLater(() -> {
                    showMessage("Login successful as Customer");
                    openHomePage();
                });
            } else if (user.getPassword().equals(password) && user instanceof Retailer) {
                SwingUtilities.invokeLater(() -> {
                    showMessage("Login successful as Retailer");
                    openRetailerFrame((Retailer) user);
                });
            } else {
                SwingUtilities.invokeLater(() -> showMessage("Invalid password"));
            }
        } else {
            SwingUtilities.invokeLater(() -> showMessage("User not found. Please register."));
        }
    }

    private void openHomePage() {
        SwingUtilities.invokeLater(() -> new HomePage());
        this.dispose();
    }

    private void register() {
        String username = registerUsernameField.getText();
        String password = new String(registerPasswordField.getPassword());
        String email = registerEmailField.getText();
        int age = Integer.parseInt(registerAgeField.getText());
        String address = registerAddressField.getText();
        String mobile = registerMobileField.getText();

        if (registerRetailerRadioButton.isSelected()) {
            String companyName = registerCompanyNameField.getText();
            String productCategory = registerProductCategoryField.getText();
            int numProducts = Integer.parseInt(registerNumProductsField.getText());

            registerRetailer(username, password, email, age, address, mobile, companyName, productCategory, numProducts);
            showMessage("Registration successful as Retailer");
            showLoginPanel();
        } else if (registerCustomerRadioButton.isSelected()) {
            registerCustomer(username, password, email, age, address, mobile);
            showMessage("Registration successful as Customer");
            showLoginPanel();
        } else {
            showMessage("Please select user type for registration.");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void registerRetailer(String username, String password, String email, int age, String address, String mobile,
                                  String companyName, String productCategory, int numProducts) {
        Retailer retailer = new Retailer(username, password, email, age, address, mobile, companyName, productCategory,
                numProducts);
        userDatabase.put(username, retailer);
        saveRetailerToFile(RETAILER_FILE_PATH, username, age, email, password, mobile, address,
                companyName, productCategory, String.valueOf(numProducts));
    }

    private void registerCustomer(String username, String password, String email, int age, String address,
                                  String mobile) {
        Customer customer = new Customer(username, password, email, age, address, mobile);
        userDatabase.put(username, customer);
        saveCustomerToFile(CUSTOMER_FILE_PATH, username, age, email, password, mobile, address);
    }

    private void openRetailerFrame(Retailer retailer) {
        JFrame retailerFrame = new JFrame("Retailer Dashboard");
        retailerFrame.setSize(400, 300);

        JButton addProductButton = new JButton("Add Product");
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct(retailer);
            }
        });

        JPanel retailerPanel = new JPanel();
        retailerPanel.setLayout(new GridLayout(1, 1));
        retailerPanel.add(addProductButton);

        retailerFrame.add(retailerPanel);
        retailerFrame.setLocationRelativeTo(null);
        retailerFrame.setVisible(true);
    }

    private void addProduct(Retailer retailer) {
      String category = JOptionPane.showInputDialog("Enter category (Furniture, Clothing, Electronics, Footwear):");
      String prodId = retailer.generateProductID(category);
  
      String productName = JOptionPane.showInputDialog("Enter product name:");
      String brand = JOptionPane.showInputDialog("Enter brand:");
      double price = Double.parseDouble(JOptionPane.showInputDialog("Enter price:"));
      int stock = Integer.parseInt(JOptionPane.showInputDialog("Enter stock:"));
      String imagePath = JOptionPane.showInputDialog("Enter image path:");
  
      Product product = new Product(prodId, productName, brand, price, stock, imagePath);
      retailer.addProductToCategory(product);
  
      showMessage("Product added successfully!");
  }
  
  

    private void loadUsersFromFile(String filePath, String userType) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(" ");
                    String username = userData[0];
                    int age = Integer.parseInt(userData[1]);
                    String email = userData[2];
                    String password = userData[3];
                    String mobile = userData[4];
                    String address = userData[5];
                    if ("customer".equals(userType)) {
                        Customer customer = new Customer(username, password, email, age, address, mobile);
                        userDatabase.put(username, customer);
                    } else if ("retailer".equals(userType)) {
                        String companyName = userData[6];
                        String productCategory = userData[7];
                        int numProducts = Integer.parseInt(userData[8]);
                        Retailer retailer = new Retailer(username, password, email, age, address, mobile, companyName,
                                productCategory, numProducts);
                        userDatabase.put(username, retailer);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCustomerToFile(String filePath, String username, int age, String email, String password, String mobile, String address) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            StringBuilder userDataLine = new StringBuilder();

            userDataLine.append(username).append(" ");
            userDataLine.append(age).append(" ");
            userDataLine.append(email).append(" ");
            userDataLine.append(password).append(" ");
            userDataLine.append(mobile).append(" ");
            userDataLine.append(address).append(" ");

            fileWriter.write(userDataLine.toString().trim() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRetailerToFile(String filePath, String username, int age, String email, String password, String mobile, String address, String companyName, String productCategory, String numProducts) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            StringBuilder userDataLine = new StringBuilder();

            userDataLine.append(username).append(" ");
            userDataLine.append(age).append(" ");
            userDataLine.append(email).append(" ");
            userDataLine.append(password).append(" ");
            userDataLine.append(mobile).append(" ");
            userDataLine.append(address).append(" ");
            userDataLine.append(companyName).append(" ");
            userDataLine.append(productCategory).append(" ");
            userDataLine.append(numProducts).append(" ");

            fileWriter.write(userDataLine.toString().trim() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginApp();
            }
        });
    }

    static class User {
        private String username;
        private String password;
        private String email;
        private int age;
        private String address;
        private String mobile;

        public User(String username, String password, String email, int age, String address, String mobile) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.age = age;
            this.address = address;
            this.mobile = mobile;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    static class Customer extends User {
        public Customer(String username, String password, String email, int age, String address, String mobile) {
            super(username, password, email, age, address, mobile);
        }

        public String getPassword() {
            return super.getPassword();
        }
    }

    static class Retailer extends User {
      private String companyName;
      private String productCategory;
      private int numProducts;
      private int productCounter = 1;

      public Retailer(String username, String password, String email, int age, String address, String mobile,
                      String companyName, String productCategory, int numProducts) {
          super(username, password, email, age, address, mobile);
          this.companyName = companyName;
          this.productCategory = productCategory;
          this.numProducts = numProducts;
      }

      public String generateProductID(String category) {
          String candidateID;
          do {
              candidateID = category.substring(0, 1) + generateRandomNumbers();
          } while (isProductIDExists(candidateID));

          return candidateID;
      }

      private int generateRandomNumbers() {
          // Generate four random numbers
          return 1000 + (int) (Math.random() * 9000);
      }

      private boolean isProductIDExists(String candidateID) {
          // Check if the product ID already exists in the file
          String fileName = getProductDetailsFileName(candidateID);
          try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
              String line;
              while ((line = reader.readLine()) != null) {
                  String[] parts = line.split(" ");
                  if (parts.length > 0 && parts[0].equals(candidateID)) {
                      return true; // ID exists
                  }
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
          return false; // ID doesn't exist
      }

      private String getProductDetailsFileName( String productCategory) {
        return productCategory.toLowerCase() + "_details.txt";
    }
    

    public void addProductToCategory(Product product) {
      String category = product.getCategory();
      String fileName = getProductDetailsFileName(category);
      try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
          out.println(product.toString());
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  

      public int getNumProducts() {
          return numProducts;
      }

      public void setNumProducts(int numProducts) {
          this.numProducts = numProducts;
      }

      public String getCompanyName() {
          return companyName;
      }

      public String getProductCategory() {
          return productCategory;
      }
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
  
      public String getCategory() {
          return prodId.substring(0, 1);
      }
  
      @Override
      public String toString() {
          return prodId + " " + productName + " " + brand + " " + price + " " + stock + " " + imagePath;
      }
  }
  
}