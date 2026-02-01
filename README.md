# CashierSimpleSystem

**CashierSimpleSystem** Merupakan aplikasi kasir berbasis Java dengan grafis (GUI) moern memakai Swing, kerjaan ini fokus dirancang untuk membantu pengelolaan transaksi sales, manajemen produk, dan pelaporan transaksi secara efisien di mart atau minimarskala kecil hingga menengah.

---

## Fitur 

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
  - Data produtersimpan selama aplikasi berjalan.

- **Laporan Transaksi**
  - Tabel riwayat transaksi: ID, Tanggal, Total, Metode Pembayaran, ID Kasir.
  - Data dapat digunakan untuk rekap harian atau bulanan.

- **Antarmuka Zaman now**
  - Menggunakan Look & Feel Nimbus (jika tersedia).
  - Responsive, mudah digunakan, dan ramah kasir.

---

## Struktur 

```
CashierSimpleSystem/
│
├── CashierSystem.java   # Main GUI & logic utama aplikasi
├── Product.java         # Model data produk
├── CartItem.java        # Model item keranjang belanja
├── Transaction.java     # Model data transaksi
├── README.md            # Dokumentasi proyek
└── ...




