import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CashierSystem extends JFrame {
    // Main layout
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Simulated databases
    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Transaction> transactionList = new ArrayList<>();

    // For search filtering in product management
    private DefaultTableModel productTableModel;
    
    public CashierSystem() {
        super("Sistem Kasir");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set Nimbus look and feel if available
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to default.
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize sample data
        initializeProducts();
        initializeTransactions();

        // Add all panels to mainPanel
        mainPanel.add(getLoginPanel(), "LOGIN");
        mainPanel.add(getDashboardPanel(), "DASHBOARD");
        mainPanel.add(getTransactionPanel(), "TRANSACTION");
        mainPanel.add(getProductPanel(), "PRODUCT");
        mainPanel.add(getReportPanel(), "REPORT");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
        setVisible(true);
    }

    // ----------------- Login Panel -----------------
    private JPanel getLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Title
        JLabel titleLabel = new JLabel("Login Kasir", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Center form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        JTextField txtUsername = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        JButton btnLogin = new JButton("Login");

        // Center alignment
        txtUsername.setMaximumSize(txtUsername.getPreferredSize());
        txtPassword.setMaximumSize(txtPassword.getPreferredSize());
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(new JLabel("Username:"));
        formPanel.add(txtUsername);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(new JLabel("Password:"));
        formPanel.add(txtPassword);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(btnLogin);

        panel.add(formPanel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            // Default credentials: username "kasir" and password "123"
            if (username.equals("kasir") && password.equals("123")) {
                JOptionPane.showMessageDialog(panel, "Login successful!");
                cardLayout.show(mainPanel, "DASHBOARD");
            } else {
                JOptionPane.showMessageDialog(panel, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    // ----------------- Dashboard Panel -----------------
    private JPanel getDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton btnTransaction = new JButton("Proses Transaksi");
        JButton btnProduct = new JButton("Manajemen Produk");
        JButton btnReport = new JButton("Laporan Transaksi");
        JButton btnLogout = new JButton("Logout");

        btnTransaction.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnProduct.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnReport.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnTransaction, gbc);
        gbc.gridy = 1;
        panel.add(btnProduct, gbc);
        gbc.gridy = 2;
        panel.add(btnReport, gbc);
        gbc.gridy = 3;
        panel.add(btnLogout, gbc);

        btnTransaction.addActionListener(e -> cardLayout.show(mainPanel, "TRANSACTION"));
        btnProduct.addActionListener(e -> cardLayout.show(mainPanel, "PRODUCT"));
        btnReport.addActionListener(e -> cardLayout.show(mainPanel, "REPORT"));
        btnLogout.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));

        return panel;
    }

    // ----------------- Transaction Panel -----------------
    private JPanel getTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        JButton btnHome = new JButton("Kembali ke Home");
        btnHome.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));
        JLabel lblTitle = new JLabel("Transaksi Penjualan", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(btnHome, BorderLayout.WEST);
        header.add(lblTitle, BorderLayout.CENTER);
        panel.add(header, BorderLayout.NORTH);

        // Left panel: product code input and quantity; also remove from cart option
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Kode Produk:"));
        JTextField txtProductCode = new JTextField(8);
        inputPanel.add(txtProductCode);
        inputPanel.add(new JLabel("Jumlah:"));
        JTextField txtQuantity = new JTextField(5);
        inputPanel.add(txtQuantity);
        JButton btnAdd = new JButton("Tambah");
        inputPanel.add(btnAdd);
        leftPanel.add(inputPanel);

        // Button to remove selected cart item
        JButton btnRemove = new JButton("Hapus Item Terpilih");
        btnRemove.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(btnRemove);

        panel.add(leftPanel, BorderLayout.WEST);

        // Center panel: Cart table
        DefaultTableModel cartTableModel = new DefaultTableModel(
                new Object[]{"Kode", "Nama", "Harga", "Jumlah", "Subtotal"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableCart = new JTable(cartTableModel);
        JScrollPane scrollCart = new JScrollPane(tableCart);
        scrollCart.setPreferredSize(new Dimension(500, 200));
        panel.add(scrollCart, BorderLayout.CENTER);

        // Bottom panel: Receipt area and checkout button
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        JButton btnCheckout = new JButton("Proses Pembayaran");
        bottomPanel.add(btnCheckout, BorderLayout.NORTH);
        JTextArea txtReceipt = new JTextArea(10, 40);
        txtReceipt.setEditable(false);
        txtReceipt.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollReceipt = new JScrollPane(txtReceipt);
        bottomPanel.add(scrollReceipt, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Temporary cart storage
        ArrayList<CartItem> cart = new ArrayList<>();

        // Add product to cart
        btnAdd.addActionListener(e -> {
            String code = txtProductCode.getText().trim();
            int qty;
            try {
                qty = Integer.parseInt(txtQuantity.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Jumlah harus berupa angka!");
                return;
            }
            // Find product by code
            Product prod = null;
            for (Product p : productList) {
                if (p.getId().equalsIgnoreCase(code)) {
                    prod = p;
                    break;
                }
            }
            if (prod == null) {
                JOptionPane.showMessageDialog(panel, "Produk tidak ditemukan!");
                return;
            }
            if (qty > prod.getStok()) {
                JOptionPane.showMessageDialog(panel, "Stok tidak mencukupi!");
                return;
            }
            CartItem item = new CartItem(prod, qty);
            cart.add(item);
            refreshCartTable(cartTableModel, cart);
            txtProductCode.setText("");
            txtQuantity.setText("");
        });

        // Remove selected cart item
        btnRemove.addActionListener(e -> {
            int selectedRow = tableCart.getSelectedRow();
            if (selectedRow != -1) {
                cart.remove(selectedRow);
                refreshCartTable(cartTableModel, cart);
            } else {
                JOptionPane.showMessageDialog(panel, "Pilih item yang akan dihapus!");
            }
        });

        // Checkout button action
        btnCheckout.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Keranjang kosong!");
                return;
            }
            double total = 0;
            StringBuilder receipt = new StringBuilder("===== STRUK TRANSAKSI =====\n");
            for (CartItem ci : cart) {
                receipt.append(ci.getProduct().getNama())
                       .append(" x").append(ci.getQuantity())
                       .append(" = ").append(ci.getSubtotal()).append("\n");
                total += ci.getSubtotal();
            }
            double tax = total * 0.1;
            double grandTotal = total + tax;
            receipt.append("-------------------------\n");
            receipt.append("Subtotal: ").append(total).append("\n");
            receipt.append("Pajak (10%): ").append(tax).append("\n");
            receipt.append("Total: ").append(grandTotal).append("\n");
            receipt.append("=========================\n");
            txtReceipt.setText(receipt.toString());
            // Record the transaction
            String transactionId = "T" + (transactionList.size() + 1);
            transactionList.add(new Transaction(transactionId,
                    java.time.LocalDate.now().toString(), grandTotal, "Tunai", "kasir"));
            JOptionPane.showMessageDialog(panel, "Pembayaran berhasil!");
            cart.clear();
            refreshCartTable(cartTableModel, cart);
        });

        return panel;
    }

    // ----------------- Product Management Panel -----------------
    private JPanel getProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        JButton btnHome = new JButton("Kembali ke Home");
        btnHome.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));
        JLabel lblTitle = new JLabel("Manajemen Produk", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(btnHome, BorderLayout.WEST);
        header.add(lblTitle, BorderLayout.CENTER);
        panel.add(header, BorderLayout.NORTH);

        // Center: Product table with search functionality
        productTableModel = new DefaultTableModel(
                new Object[]{"ID Produk", "Nama", "Harga", "Stok", "Kategori"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableProducts = new JTable(productTableModel);
        JScrollPane scrollPane = new JScrollPane(tableProducts);
        panel.add(scrollPane, BorderLayout.CENTER);
        refreshProductTable();

        // Search field above table
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari Produk:"));
        JTextField txtSearch = new JTextField(15);
        searchPanel.add(txtSearch);
        panel.add(searchPanel, BorderLayout.SOUTH);

        // Live search filtering
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterProducts(txtSearch.getText()); }
            public void removeUpdate(DocumentEvent e) { filterProducts(txtSearch.getText()); }
            public void changedUpdate(DocumentEvent e) { filterProducts(txtSearch.getText()); }
        });

        // Form to add new product
        JPanel formPanel = new JPanel(new FlowLayout());
        JTextField txtId = new JTextField(5);
        JTextField txtNama = new JTextField(10);
        JTextField txtHarga = new JTextField(7);
        JTextField txtStok = new JTextField(5);
        JTextField txtKategori = new JTextField(10);
        JButton btnAdd = new JButton("Tambah Produk");

        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Nama:"));
        formPanel.add(txtNama);
        formPanel.add(new JLabel("Harga:"));
        formPanel.add(txtHarga);
        formPanel.add(new JLabel("Stok:"));
        formPanel.add(txtStok);
        formPanel.add(new JLabel("Kategori:"));
        formPanel.add(txtKategori);
        formPanel.add(btnAdd);
        panel.add(formPanel, BorderLayout.PAGE_END);

        btnAdd.addActionListener(e -> {
            String id = txtId.getText().trim();
            String nama = txtNama.getText().trim();
            double harga;
            int stok;
            String kategori = txtKategori.getText().trim();
            try {
                harga = Double.parseDouble(txtHarga.getText().trim());
                stok = Integer.parseInt(txtStok.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Harga dan Stok harus berupa angka!");
                return;
            }
            productList.add(new Product(id, nama, harga, stok, kategori));
            refreshProductTable();
            txtId.setText("");
            txtNama.setText("");
            txtHarga.setText("");
            txtStok.setText("");
            txtKategori.setText("");
        });

        return panel;
    }

    // ----------------- Transaction Report Panel -----------------
    private JPanel getReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        JButton btnHome = new JButton("Kembali ke Home");
        btnHome.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));
        JLabel lblTitle = new JLabel("Laporan Transaksi", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(btnHome, BorderLayout.WEST);
        header.add(lblTitle, BorderLayout.CENTER);
        panel.add(header, BorderLayout.NORTH);

        // Transaction report table
        DefaultTableModel reportTableModel = new DefaultTableModel(
                new Object[]{"ID Transaksi", "Tanggal", "Total Harga", "Metode Pembayaran", "ID Kasir"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tableReport = new JTable(reportTableModel);
        JScrollPane scrollPane = new JScrollPane(tableReport);
        panel.add(scrollPane, BorderLayout.CENTER);
        refreshTransactionTable(reportTableModel);

        return panel;
    }

    // ----------------- Helper Methods -----------------
    private void refreshCartTable(DefaultTableModel model, ArrayList<CartItem> cart) {
        model.setRowCount(0);
        for (CartItem item : cart) {
            model.addRow(new Object[]{
                item.getProduct().getId(),
                item.getProduct().getNama(),
                item.getProduct().getHarga(),
                item.getQuantity(),
                item.getSubtotal()
            });
        }
    }

    private void refreshProductTable() {
        productTableModel.setRowCount(0);
        for (Product p : productList) {
            productTableModel.addRow(new Object[]{
                p.getId(), p.getNama(), p.getHarga(), p.getStok(), p.getKategori()
            });
        }
    }

    private void filterProducts(String keyword) {
        DefaultTableModel filteredModel = new DefaultTableModel(
                new Object[]{"ID Produk", "Nama", "Harga", "Stok", "Kategori"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        for (Product p : productList) {
            if (p.getNama().toLowerCase().contains(keyword.toLowerCase()) ||
                p.getKategori().toLowerCase().contains(keyword.toLowerCase())) {
                filteredModel.addRow(new Object[]{
                    p.getId(), p.getNama(), p.getHarga(), p.getStok(), p.getKategori()
                });
            }
        }
        // For simplicity, update the productTableModel reference.
        productTableModel = filteredModel;
    }

    private void refreshTransactionTable(DefaultTableModel model) {
        model.setRowCount(0);
        for (Transaction t : transactionList) {
            model.addRow(new Object[]{
                t.getId(), t.getTanggal(), t.getTotalHarga(), t.getMetodePembayaran(), t.getIdKasir()
            });
        }
    }

    private void initializeProducts() {
        productList.add(new Product("P001", "Produk A", 10000, 50, "Kategori 1"));
        productList.add(new Product("P002", "Produk B", 20000, 30, "Kategori 2"));
        productList.add(new Product("P003", "Produk C", 15000, 20, "Kategori 1"));
    }

    private void initializeTransactions() {
        transactionList.add(new Transaction("T001", "2025-02-15", 50000, "Tunai", "kasir"));
        transactionList.add(new Transaction("T002", "2025-02-15", 75000, "Kartu", "kasir"));
        transactionList.add(new Transaction("T003", "2025-02-16", 100000, "E-Wallet", "kasir"));
    }

    // ----------------- Main Method -----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CashierSystem::new);
    }
}
