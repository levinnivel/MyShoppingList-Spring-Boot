package shoppinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class DemoShoppingListSpringBootApplication implements CommandLineRunner
{
    @Autowired
    private DaftarBelanjaRepo repo;

    public static void main(String[] args)
    {
        SpringApplication.run(DemoShoppingListSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        Scanner scan = new Scanner(System.in);
        int pilih;

        while(true){
            System.out.print("\nProgram CRUD MyShoppingList dengan Spring Repository\n" +
                    "1. Membaca Semua Objek DaftarBelanja\n" +
                    "2. Membaca Sebuah Objek DaftarBelanja Berdasarkan ID\n" +
                    "3. Mencari Daftar DaftarBelanja Berdasarkan Kemiripan String Judul yang Diberikan\n" +
                    "4. Menyimpan Sebuah Objek DaftarBelanja ke Tabel Database\n" +
                    "5. Mengupdate Sebuah Objek DaftarBelanja ke Tabel Database\n" +
                    "6. Menghapus Objek DaftarBelanja Berdasarkan ID yang Diberikan\n\n"+
                    "Masukan Pilihan : ");
            pilih = scan.nextInt();

            switch (pilih){
                case 1:
                    getAllDaftarBelanja();
                    break;
                case 2:
                    getDaftarBelanjaById();
                    break;
                case 3:
                    getDaftarBelanjaByJudul();
                    break;
                case 4:
                    createDaftarBelanja();
                    break;
                case 5:
                    updateDaftarBelanja();
                    break;
                case 6:
                    deleteDaftarBelanja();
                    break;
            }
        }

    }

    public void getAllDaftarBelanja(){
        System.out.println("Membaca Semua Record DaftarBelanja : ");
        List<DaftarBelanja> all = repo.findAll();
        for (DaftarBelanja db: all) {
            System.out.println("Data : ");
            System.out.println("Judul : " + db.getJudul() +
                    "\n\t Tanggal : " + db.getTanggal());

            List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
            System.out.println("Detail Daftar Belanja :");
            for (DaftarBelanjaDetil barang : listBarang) {
                System.out.println("\t Nama Barang : " + barang.getNamaBarang() +
                        "\n\t Jumlah : " + barang.getByk() +
                        "\n\t Satuan : " + barang.getSatuan());
            }
        }
    }

    public void getDaftarBelanjaById(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Membaca Record Daftar Belanja Berdasarkan ID : ");
        String tempId = scan.nextLine();
        long id = Long.parseLong(tempId);

        Optional<DaftarBelanja> optDB = repo.findById(id);
        if (optDB.isPresent()) {
            DaftarBelanja db = optDB.get();
            System.out.println("Data : ");
            System.out.println("Judul : " + db.getJudul() +
                    "\n\t Tanggal : " + db.getTanggal());

            List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
            System.out.println("Detail Daftar Belanja :");
            for (DaftarBelanjaDetil barang : listBarang) {
                System.out.println("\t Nama Barang : " + barang.getNamaBarang() +
                        "\n\t Jumlah : " + barang.getByk() +
                        "\n\t Satuan : " + barang.getSatuan());
            }
        }
        else {
            System.out.println("\tData tidak ada");
        }
    }

    public void getDaftarBelanjaByJudul() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Membaca Daftar Belanja Berdasarkan Judul : ");
        String Judul = scan.nextLine();

        DaftarBelanja tempDb = null;
        List<DaftarBelanja> all = repo.findAll();
        for (DaftarBelanja db : all) {
            if(db.getJudul().equals(Judul)){
                tempDb = db;
            }
        }

        if(tempDb != null){
            System.out.println("Data : ");
            System.out.println("Judul : " + tempDb.getJudul() +
                    "\n\t Tanggal : " + tempDb.getTanggal());

            List<DaftarBelanjaDetil> listBarang = tempDb.getDaftarBarang();
            System.out.println("Detail Daftar Belanja :");
            for (DaftarBelanjaDetil barang : listBarang) {
                System.out.println("\t Nama Barang : " + barang.getNamaBarang() +
                        "\n\t Jumlah : " + barang.getByk() +
                        "\n\t Satuan : " + barang.getSatuan());
            }
        }else{
            System.out.println("Data tidak ada");
        }
    }

    public void createDaftarBelanja(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Simpan Record Daftar Belanja : ");
        System.out.print("Masukkan Judul : ");
        String judul = scan.nextLine();
        DaftarBelanja db = new DaftarBelanja();
        LocalDateTime tglPembuatan = LocalDateTime.now().withNano(0);

        db.setJudul(judul);
        db.setTanggal(tglPembuatan);

        repo.save(db);
        System.out.println("Data berhasil dibuat");
    }

    public void updateDaftarBelanja(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Update Daftar Belanja Berdasarkan ID:");
        String tempId = scan.nextLine();
        long id = Long.parseLong(tempId);


        Optional<DaftarBelanja> optDB = repo.findById(id);
        if (optDB.isPresent()) {
            DaftarBelanja db = optDB.get();
            System.out.println("Data : ");
            System.out.println("Judul : " + db.getJudul() +
                    "\n\t Tanggal : " + db.getTanggal());

            List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
            System.out.println("Detail Daftar Belanja :");
            for (DaftarBelanjaDetil barang : listBarang) {
                System.out.println("\t Nama Barang : " + barang.getNamaBarang() +
                        "\n\t Jumlah : " + barang.getByk() +
                        "\n\t Satuan : " + barang.getSatuan());
            }

            System.out.println("Masukkan Data Update");
            System.out.print("Masukkan Judul : ");
            String judul = scan.nextLine();
            LocalDateTime tglPembuatan = LocalDateTime.now().withNano(0);
            db.setJudul(judul);
            db.setTanggal(tglPembuatan);

            repo.save(db);
            System.out.println("Data berhasil diperbaharui");
        }
        else {
            System.out.println("\tData tidak ada");
        }
    }

    public void deleteDaftarBelanja(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Hapus Daftar Belanja berdasarkan ID :");
        String tempId = scan.nextLine();
        long id = Long.parseLong(tempId);

        Optional<DaftarBelanja> optDB = repo.findById(id);
        System.out.println("Data : ");
        if (optDB.isPresent()) {
            DaftarBelanja db = optDB.get();
            System.out.println("Judul : " + db.getJudul() +
                    "\n\t Tanggal : " + db.getTanggal());

            List<DaftarBelanjaDetil> listBarang = db.getDaftarBarang();
            System.out.println("Detail Daftar Belanja :");
            for (DaftarBelanjaDetil barang : listBarang) {
                System.out.println("\t Nama Barang : " + barang.getNamaBarang() +
                        "\n\t Jumlah : " + barang.getByk() +
                        "\n\t Satuan : " + barang.getSatuan());
            }

            repo.deleteById(id);
            System.out.println("Data berhasil dihapus");
        }
        else {
            System.out.println("\tData tidak ada");
        }
    }
}
