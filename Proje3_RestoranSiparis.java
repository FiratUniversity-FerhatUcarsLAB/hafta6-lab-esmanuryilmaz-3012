/**Adı-Soyadı: Esmanur YILMAZ
/Öğrenci Numarası: 250541033
*/
import java.util.Scanner;
    public class RestoranSiparis {
    
        // 1. Menü Fiyat Metotları

        public static double getMainDishPrice(int secim) {
            switch (secim) {
                case 1: return 85.0;  // Izgara Tavuk
                case 2: return 120.0; // Adana Kebap
                case 3: return 110.0; // Levrek
                case 4: return 65.0;  // Mantı
                default: return 0.0;
            }
        }

        public static double getAppetizerPrice(int secim) {
            switch (secim) {
                case 1: return 25.0;  // Çorba
                case 2: return 45.0;  // Humus
                case 3: return 55.0;  // Sigara Böreği
                default: return 0.0;
            }
        }

        public static double getDrinkPrice(int secim) {
            switch (secim) {
                case 1: return 15.0;  // Kola
                case 2: return 12.0;  // Ayran
                case 3: return 35.0;  // Taze Meyve Suyu
                case 4: return 25.0;  // Limonata
                default: return 0.0;
            }
        }

        public static double getDessertPrice(int secim) {
            switch (secim) {
                case 1: return 65.0;  // Künefe
                case 2: return 55.0;  // Baklava
                case 3: return 35.0;  // Sütlaç
                default: return 0.0;
            }
        }

        //2. Özel Durum Kontrol Metotları
    
        public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
            return anaVar && icecekVar && tatliVar;
        }
        public static boolean isHappyHour(int saat) {
            return saat >= 14 && saat < 17;
        }

        //3. Hesaplama Metotları
        public static double calculateDiscount(double brütTutar, double icecekTutari, boolean combo, boolean ogrenci, int gun, int saat) {
            double totalDiscountAmount = 0.0;
            double discountedTotal = brütTutar; // Kademeli indirimler için güncel tutar

            // 1. Combo İndirimi (%15)
            if (combo) {
                double comboDiscount = brütTutar * 0.15;
                totalDiscountAmount += comboDiscount;
                discountedTotal -= comboDiscount; // Tutarı günceller
            }

            // 2. Happy Hour İndirimi (Sadece İçeceklerde %20)
            if (isHappyHour(saat)) {
                double happyHourDiscount = icecekTutari * 0.20;
                totalDiscountAmount += happyHourDiscount;
                // Not: Bu indirim tutara etki etmez, sadece içecek fiyatını düşürür.
                // Ancak toplam indirim miktarına eklenir.
            }

            // 3. Öğrenci İndirimi (Hafta İçi %10 ekstra)
            // gun >= 1 (Pazartesi) && gun <= 5 (Cuma)
            if (ogrenci) {
                if (gun >= 1 && gun <= 5) {
                    // Öğrenci indirimi, Combo uygulanmış tutar üzerinden
                    double ogrenciDiscount = discountedTotal * 0.10;
                    totalDiscountAmount += ogrenciDiscount;
                    discountedTotal -= ogrenciDiscount; // Tutarı günceller
                }
            }

            // 4. 200 TL Üzeri İndirim (%10)
            // Tüm önceki indirimler uygulandıktan sonraki tutar üzerinden
            if (discountedTotal > 200.0) {
                double buyukIndirim = discountedTotal * 0.10;
                totalDiscountAmount += buyukIndirim;
            }

            return totalDiscountAmount;
        }
        public static double calculateServiceTip(double finalTutar) {
            return finalTutar * 0.10;
        }

        // --- Ana Program Akışı ---

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            // Kullanıcıdan Girdi Alma (Örnek Basitleştirilmiş Girdiler)
            System.out.println("--- Akıllı Restoran Sipariş Sistemi ---\n");

            System.out.print("Sipariş Saati (8-23): ");
            int saat = scanner.nextInt();

            System.out.print("Bugün Gün Kodu (1=Pzt, ..., 7=Paz): ");
            int gun = scanner.nextInt();

            System.out.print("Öğrenci misiniz? (true/false): ");
            boolean ogrenci = scanner.nextBoolean();

            // Basitleştirilmiş menü seçimi ve miktar alma
            System.out.print("\nAna Yemek Seçimi (1-4, Yoksa 0): ");
            int anaYemekSecim = scanner.nextInt();

            System.out.print("İçecek Seçimi (1-4, Yoksa 0): ");
            int icecekSecim = scanner.nextInt();

            System.out.print("Tatlı Seçimi (1-3, Yoksa 0): ");
            int tatliSecim = scanner.nextInt();

            System.out.print("Başlangıç Seçimi (1-3, Yoksa 0): ");
            int baslangicSecim = scanner.nextInt();

            // 1. Brüt Fiyat Hesaplama
            double anaYemekTutar = getMainDishPrice(anaYemekSecim);
            double icecekTutar = getDrinkPrice(icecekSecim);
            double tatliTutar = getDessertPrice(tatliSecim);
            double baslangicTutar = getAppetizerPrice(baslangicSecim);

            double brutTutar = anaYemekTutar + icecekTutar + tatliTutar + baslangicTutar;

            // Sipariş Var mı Kontrolleri
            boolean anaVar = anaYemekSecim != 0;
            boolean icecekVar = icecekSecim != 0;
            boolean tatliVar = tatliSecim != 0;

            // 2. Özel Durum Kontrolleri
            boolean combo = isComboOrder(anaVar, icecekVar, tatliVar);

            // 3. İndirimi Hesaplama
            double toplamIndirim = calculateDiscount(brutTutar, icecekTutar, combo, ogrenci, gun, saat);

            // 4. Nihai Tutar
            double nihaiTutar = brutTutar - toplamIndirim;

            // 5. Bahşiş Hesaplama
            double bahsisOnerisi = calculateServiceTip(nihaiTutar);
            double odenecekToplam = nihaiTutar + bahsisOnerisi;

            // 6. Çıktı Oluşturma
            System.out.println("\n--- Fatura Özeti ---");
            System.out.printf("Brüt Sipariş Tutarı: %.2f TL\n", brutTutar);
            System.out.println("-------------------------");

            // Detaylı İndirim Bilgisi
            if (combo) {
                System.out.println("Combo Menü İndirimi: %15 Uygulandı.");
            }
            if (isHappyHour(saat) && icecekTutar > 0) {
                System.out.printf("Happy Hour İndirimi: İçeceklerde %.2f TL Düşüldü.\n", icecekTutar * 0.20);
            }
            if (ogrenci && (gun >= 1 && gun <= 5)) {
                System.out.println("Öğrenci Ekstra İndirimi: %10 Uygulandı.");
            }
            if (brutTutar - toplamIndirim > 200.0) {
                System.out.println("200 TL Üzeri İndirimi: %10 Uygulandı.");
            }

            System.out.printf("Toplam İndirim Miktarı: -%.2f TL\n", toplamIndirim);
            System.out.println("-------------------------");
            System.out.printf("İndirimler Sonrası Tutar: **%.2f TL**\n", nihaiTutar);
            System.out.printf("Garson Bahşişi Önerisi (10%%): +%.2f TL\n", bahsisOnerisi);
            System.out.println("-------------------------");
            System.out.printf("Toplam Ödenecek Tutar: **%.2f TL**\n", odenecekToplam);

            scanner.close();
        }
    }
