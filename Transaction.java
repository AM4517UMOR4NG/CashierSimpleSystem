public class Transaction {
    private String id;
    private String tanggal;
    private double totalHarga;
    private String metodePembayaran;
    private String idKasir;

    public Transaction(String id, String tanggal, double totalHarga, String metodePembayaran, String idKasir) {
        this.id = id;
        this.tanggal = tanggal;
        this.totalHarga = totalHarga;
        this.metodePembayaran = metodePembayaran;
        this.idKasir = idKasir;
    }

    public String getId() {
        return id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public String getIdKasir() {
        return idKasir;
    }
}
