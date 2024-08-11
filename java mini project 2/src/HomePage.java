/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    public HomePage() {
        setTitle("SPRING");
        setSize(800, 600); // Adjust the size as needed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color to green
        getContentPane().setBackground(Color.GREEN);

        // Create labels for the application name, logo, and subtext
        JLabel nameLabel = new JLabel("SPRING");
        JLabel logoLabel = new JLabel(new ImageIcon("path/to/your/logo.png")); // Replace with the actual path to your logo
        JLabel subtextLabel = new JLabel("Where You Can Find Anything");

        // Set font and foreground color for labels
        Font font = new Font("Arial", Font.BOLD, 24);
        nameLabel.setFont(font);
        nameLabel.setForeground(Color.WHITE);

        subtextLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtextLabel.setForeground(Color.WHITE);

        // Create a panel to hold the labels
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.GREEN);

        // Add labels to the header panel
        headerPanel.add(nameLabel, BorderLayout.WEST);
        headerPanel.add(logoLabel, BorderLayout.CENTER);
        headerPanel.add(subtextLabel, BorderLayout.SOUTH);

        // Add the header panel to the frame
        add(headerPanel, BorderLayout.NORTH);

        // Create a panel for category buttons and labels
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(2, 4, 20, 20)); // Adjusted spacing
        categoryPanel.setBackground(Color.GREEN);

        // Add buttons with images and labels to the category panel
        addCategoryButton(categoryPanel, "D:\\education\\java mini project 2\\java mini project 2\\clothes.jpeg", "CLOTHING");
        addCategoryButton(categoryPanel, "D:\\education\\java mini project 2\\java mini project 2\\electronic.jpeg", "ELECTRONIC");
        addCategoryButton(categoryPanel, "D:\\education\\java mini project 2\\java mini project 2\\FOOTWEAR.jpeg", "FOOTWEAR");
        addCategoryButton(categoryPanel, "D:\\education\\java mini project 2\\java mini project 2\\furniture.jpeg", "FURNITURE");

        // Add the category panel to the frame
        add(categoryPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addCategoryButton(JPanel panel, String imagePath, String categoryName) {
        JButton button = new JButton(new ImageIcon(imagePath));
        JLabel label = new JLabel(categoryName, JLabel.CENTER);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProductFrame(categoryName);
            }
        });
        panel.add(button);
        panel.add(label);
    }

    private void openProductFrame(String categoryName) {
       ProductToggle productToggle = new ProductToggle();
       productToggle.getFrame().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage());
    }
}
*/
/* 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    public HomePage() {
        setTitle("SPRING");
        setSize(800, 600); // Adjust the size as needed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color to green
        getContentPane().setBackground(Color.GREEN);

        // Create labels for the application name, logo, and subtext
        JLabel nameLabel = new JLabel("SPRING");
        JLabel logoLabel = new JLabel(new ImageIcon("path/to/your/logo.png")); // Replace with the actual path to your logo
        JLabel subtextLabel = new JLabel("Where You Can Find Anything");

        // Set font and foreground color for labels
        Font font = new Font("Arial", Font.BOLD, 24);
        nameLabel.setFont(font);
        nameLabel.setForeground(Color.WHITE);

        subtextLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtextLabel.setForeground(Color.WHITE);

        // Create a panel to hold the labels
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.GREEN);

        // Add labels to the header panel
        headerPanel.add(nameLabel, BorderLayout.WEST);
        headerPanel.add(logoLabel, BorderLayout.CENTER);
        headerPanel.add(subtextLabel, BorderLayout.SOUTH);

        // Add the header panel to the frame
        add(headerPanel, BorderLayout.NORTH);

        // Create a panel for category buttons and labels
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(2, 4, 20, 20)); // Adjusted spacing
        categoryPanel.setBackground(Color.GREEN);

        // Add buttons with images and labels to the category panel
        addCategoryButton(categoryPanel, "D:\\education\\java mini project 2\\java mini project 2\\clothes.jpeg", "CLOTHING", 1);
        addCategoryButton(categoryPanel, "D:\\education\\java mini project 2\\java mini project 2\\electronic.jpeg", "ELECTRONIC", 2);
        addCategoryButton(categoryPanel, "D:\\education\\java mini project 2\\java mini project 2\\FOOTWEAR.jpeg", "FOOTWEAR", 3);
        addCategoryButton(categoryPanel, "D:\\education\\java mini project 2\\java mini project 2\\furniture.jpeg", "FURNITURE", 4);

        // Add the category panel to the frame
        add(categoryPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addCategoryButton(JPanel panel, String imagePath, String categoryName, int productToggleNumber) {
        JButton button = new JButton(new ImageIcon(imagePath));
        JLabel label = new JLabel(categoryName, JLabel.CENTER);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProductFrame(categoryName, productToggleNumber);
            }
        });
        panel.add(button);
        panel.add(label);
    }

    private void openProductFrame(String categoryName, int productToggleNumber) {
        if (productToggleNumber == 1) {
            ProductToggle1 productToggle = new ProductToggle1();
            productToggle.getFrame().setVisible(true);
        } else if (productToggleNumber == 2) {
            ProductToggle2 productToggle = new ProductToggle2();
            productToggle.getFrame().setVisible(true);
        } else if (productToggleNumber == 3) {
            ProductToggle3 productToggle = new ProductToggle3();
            productToggle.getFrame().setVisible(true);
        } else if (productToggleNumber == 4) {
            ProductDetailsViewer productToggle = new ProductDetailsViewer();
            productToggle.getFrame().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage());
    }
}*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    public HomePage() {
        setTitle("Home Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons with images for each category
        JButton clothingButton = createCategoryButton("Clothing", "c_details.txt", "clothes.jpeg");
        JButton electronicsButton = createCategoryButton("Electronics", "e_details.txt", "electronic.jpeg");
        JButton furnitureButton = createCategoryButton("Furniture", "f_details.txt", "furniture.jpeg");
        JButton footwearButton = createCategoryButton("Footwear", "ft_details.txt", "FOOTWEAR.jpeg");

        // Create panel for buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 2));
        contentPanel.add(clothingButton);
        contentPanel.add(electronicsButton);
        contentPanel.add(furnitureButton);
        contentPanel.add(footwearButton);

        add(contentPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createCategoryButton(String categoryName, String filePath, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton button = new JButton(categoryName, scaledIcon);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProductDetailsViewer(categoryName, filePath);
            }
        });
        return button;
    }

    private void openProductDetailsViewer(String category, String filePath) {
        // Create instances of ProductDetailsViewer based on the selected category
        if (category.equalsIgnoreCase("Clothing")) {
            SwingUtilities.invokeLater(() -> new ProductDetailsViewerc(filePath).show());
        } else if (category.equalsIgnoreCase("Electronics")) {
            SwingUtilities.invokeLater(() -> new ProductDetailsViewere(filePath).show());
        } else if (category.equalsIgnoreCase("Furniture")) {
            SwingUtilities.invokeLater(() -> new ProductDetailsViewerf(filePath).show());
        } else if (category.equalsIgnoreCase("Home Appliances")) {
            SwingUtilities.invokeLater(() -> new ProductDetailsViewerh(filePath).show());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage());
    }
}
