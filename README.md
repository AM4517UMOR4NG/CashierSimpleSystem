# 💸 CashierSimpleSystem

**CashierSimpleSystem** Merupakan aplikasi kasir berbasis Java dengan antarmuka grafis (GUI) moern memakai Swing, kerjaan ini fokus dirancang untuk membantu pengelolaan transaksi sales, manajemen produk, dan pelaporan transaksi secara efisien di mart atau minimarskala kecil hingga menengah.

---

## 🚀 Fitur Utama

- **Login Aman**
  - Username: `kasir`
  - Password: `123`
  - Sistem login sederhana untuk memberikan batas akses aplikasi.

- **Dashboard Interaktif**
  - Navigasi mudah ke fitur utama: Transaksi, Manajemen Produk, Laporan, dan Logout.

- **Transaksi Penjualan**
  - Input kode produk & jumlah, tambah ke keranjang.
  - Validasi stok otomatis.
  - Delete item dari keranjang.
  - Proses pembayaran dengan struk otomatis (termasuk pajak 10%).
  - Riwayat transaksi otomatis tercatat.

- **Manajemen Produk**
  - Tabel produk dengan fitur pencarian real-time.
  - Tambah produk baru (ID, Nama, Harga, Stok, Kategori).
  - Data produk tersimpan selama aplikasi berjalan.

- **Laporan Transaksi**
  - Tabel riwayat transaksi: ID, Tanggal, Total, Metode Pembayaran, ID Kasir.
  - Data dapat digunakan untuk rekap harian atau bulanan.

- **Antarmuka Zaman now**
  - Menggunakan Look & Feel Nimbus (jika tersedia).
  - Responsive, mudah digunakan, dan ramah kasir.

---

## 🏗️ Struktur Pekerjaan

```
CashierSimpleSystem/
│
├── CashierSystem.java   # Main GUI & logic utama aplikasi
├── Product.java         # Model data produk
├── CartItem.java        # Model item keranjang belanja
├── Transaction.java     # Model data transaksi
├── README.md            # Dokumentasi proyek
└── ...
```

---

## 📦 Instalasi & Menjalankan Aplikasi

### **Prasyarat**
- Java JDK 8 atau lebih edition
- IDE (NetBeans, IntelliJ, Eclipse, atau bisa pakai terminal)

### **Langkah Menjalankan**
1. **Clone atau Download** repository ini.
2. **Pastikan semua file (`CashierSystem.java`, `Product.java`, `CartItem.java`, `Transaction.java`) berada dalam satu folder/proyek.**
3. **Compile semua file Java:**
   ```sh
   javac *.java
   ```
4. **Jalankan aplikasi:**
   ```sh
   java CashierSystem
   ```
5. **Login** dengan username `kasir` dan password `123`.

---

## 🖥️ Penjelasan Kelas Utama

- **CashierSystem.java**
  - Kelas utama, mengatur seluruh tampilan dan logika aplikasi (login, dashboard, transaksi, produk, laporan).
  - Memakai `CardLayout` untuk navigasi antar panel.
  - Simulasi database menggunakan `ArrayList`.

- **Product.java**
  - Model data produk (ID, Nama, Harga, Stok, Kategori).

- **CartItem.java**
  - Model item dalam keranjang belanja (produk + jumlah + subtotal).

- **Transaction.java**
  - Model data transaksi (ID, Tanggal, Total, Metode Pembayaran, ID Kasir).

---

## 📝 Contoh Penggunaan

1. **Login** ke App.
2. **Transaksi Penjualan:**
   - Input kode produk & jumlah, klik "Tambah".
   - Setelah selesai, klik "Proses Pembayaran" untuk mencetak struk.
3. **Manajemen Produk:**
   - Tambah produk baru melalui form di bawah tabel.
   - Cari produk menggunakan fitur pencarian.
4. **Laporan Transaksi:**
   - Lihat riwayat transaksi yang sudah dilakukan.

---

## 📄 **License**

This project is licensed under the MIT License. 

## 💡 Catatan

- **Data tidak tersimpan permanen (temporary) ** (belum/tidak terhubung ke database, hanya di memori selama aplikasi dipakai).
- Untuk pengembangan lebih lanjut. Agar bisa diintegrasikan dengan database. 


## 🤝 Kontribusi

Kontribusi/Bantuan sangat terbuka! Silakan fork, buat branch, dan pull request untuk fitur/bugfix baru. 



