import java.util.Random;
import java.util.Scanner;

/** Aturan Game:
 - Terdapat sejumlah batang kayu (10-30 batang di awal).
 - Pemain dan komputer bergiliran mengambil 1, 2, atau 3 batang.
 - Pemain yang terpaksa mengambil batang terakhir (batang ke-1) dinyatakan kalah.
 - Komputer menggunakan strategi matematis untuk memaksimalkan peluang menang.
 */
public class gamenimtes {
    public static void main(String[] args) {
        // INISIALISASI OBJEK UTAMA
        // Scanner untuk membaca input dari pengguna
        Scanner scanner = new Scanner(System.in);
        // Random untuk menghasilkan angka acak (jumlah batang awal)
        Random random = new Random();

        // KONSTANTA WARNA (UNTUK MEMBUAT OUTPUT DI TERMINAL BERWARNA)
        final String RESET = "\u001B[0m";    // Reset semua formatting
        final String RED = "\u001B[31m";     // Teks merah (untuk pesan kekalahan)
        final String GREEN = "\u001B[32m";   // Teks hijau (untuk batang kayu dan kemenangan)
        final String YELLOW = "\u001B[33m";  // Teks kuning (untuk pesan netral)
        final String BLUE = "\u001B[34m";    // Teks biru (untuk header informasi)

        // TAMPILAN AWAL DALAM OPENING GAME
        System.out.println(YELLOW + "==================================");
        System.out.println("   SELAMAT DATANG DI GAME NIM!");
        System.out.println("==================================" + RESET);
        System.out.println("Aturan Main :");
        System.out.println("- Anda dan komputer bergiliran mengambil 1-3 batang.");
        System.out.println("- Pemain yang mengambil batang terakhir KALAH.");
        System.out.println("----------------------------------");

        // PEMILIHAN GILIRAN AWAL
        System.out.print("Pilih giliran (1 untuk main pertama, 2 untuk main kedua): ");
        int playerOrder = scanner.nextInt();

        int sticks;          // Variabel menyimpan jumlah batang kayu saat ini
        boolean userFirst = (playerOrder == 1);  // True jika user main pertama

        // GENERATE JUMLAH BATANG AWAL (10-30)
        if (userFirst) {
            // Jika user main pertama, pastikan komputer bisa menang dengan strategi
            // Jumlah batang awal diatur sehingga (sticks % 4 == 1) -> Supaya kemungkinan player menang lebih sedikit
            do {
                sticks = 10 + random.nextInt(21); // Angka acak antara 10-30
            } while ((sticks % 4) != 1); // Ulangi hingga kondisi terpenuhi
        } else {
            // Jika komputer main pertama, jumlah batang bisa random (10-30)
            sticks = 10 + random.nextInt(21);
        }

        boolean userTurn = userFirst;  // Menentukan giliran saat ini

        // LOOP UTAMA GAME (BERJALAN SELAMA BATANG > 1)
        while (sticks > 1) {
            // TAMPILAN VISUAL BATANG KAYU
            System.out.println("\n\n" + BLUE + "==== BATANG KAYU ====" + RESET);
            for (int i = 0; i < sticks; i++) {
                if (i % 10 == 0) System.out.println();  // Baris baru setiap 10 batang
                System.out.print(GREEN + "| " + RESET);   // Tampilkan batang
            }
            System.out.println("\n\nSisa batang: " + RED + sticks + RESET);

            // LOGIKA GILIRAN USER
            if (userTurn) {
                System.out.print("\nAmbil batang (1-3): ");
                int take = scanner.nextInt();

                // INPUT USER
                // Pastikan input antara 1-3 dan tidak melebihi batang tersisa
                while (take < 1 || take > 3 || take >= sticks) {
                    System.out.print("Input tidak valid. Ambil 1-3 (maks " + (sticks - 1) + "): ");
                    take = scanner.nextInt();
                }

                sticks -= take;  // Kurangi jumlah batang
                System.out.println(YELLOW + "Anda mengambil " + take + RESET);
            } 
            // LOGIKA GILIRAN KOMPUTER
            else {
                // STRATEGI KOMPUTER:
                // Ambil jumlah batang sehingga sisa batang memenuhi (sticks % 4 == 1)
                int take = (sticks - 1) % 4;
                if (take == 0) take = 1;  // Minimal ambil 1 batang

                sticks -= take;  // Kurangi jumlah batang
                System.out.println(RED + "Komputer mengambil " + take + RESET);
            }

            userTurn = !userTurn;  // Ganti giliran (user ↔ komputer)
        }

        // TAMPILAN AKHIR GAME (BATANG TERAKHIR)
        System.out.println("\n\n" + BLUE + "==== BATANG TERAKHIR ====" + RESET);
        System.out.println(GREEN + "|" + RESET);  // Tampilkan batang terakhir
        
        // PENENTUAN PEMENANG
        if (userTurn) {
            // Jika giliran user saat batang terakhir, user kalah
            System.out.println(RED + "\nANDA MENGAMBIL BATANG TERAKHIR!\nANDA KALAH!" + RESET);
        } 
        else {
            // Jika giliran komputer saat batang terakhir, user menang
            System.out.println(GREEN + "\nKOMPUTER HARUS MENGAMBIL BATANG TERAKHIR!\nANDA MENANG!" + RESET);
        }

        scanner.close();  // Tutup Scanner untuk menghindari resource leak
    }
}