/**Adı-Soyadı: Esmanur YILMAZ
/Öğrenci Numarası: 250541033
*/
import java.util.Scanner;
  public class SinemaBileti {
      public static boolean isWeekend(int gun) {
            // 6 = Cumartesi, 7 = Pazar
            return gun == 6 || gun == 7;
        }
        public static boolean isMatinee(int saat) {
            return saat < 12;
        }
        public static double calculateBasePrice(int gun, int saat) {
            boolean isWknd = isWeekend(gun);
            boolean isMat = isMatinee(saat);
            double basePrice;

            if (isWknd) {
                // Hafta Sonu
                basePrice = isMat ? 55.0 : 85.0; // Matine: 55 TL, Normal: 85 TL
            } else {
                // Hafta İçi
                basePrice = isMat ? 45.0 : 65.0; // Matine: 45 TL, Normal: 65 TL
            }
            return basePrice;
        }
        public static double calculateDiscount(int yas, int meslek, int gun) {
            // A. YAŞ İNDİRİMLERİ (Öncelikli)
            if (yas >= 65) {
                return 0.30; // 65+ yaş: %30 (her gün)
            } else if (yas < 12) {
                return 0.25; // 12 yaş altı: %25 (her gün)
            }

            // B. MESLEK İNDİRİMLERİ
            switch (meslek) {
                case 1: // Öğrenci
                    // Pazartesi(1)-Perşembe(4) %20, Cuma(5)-Pazar(7) %15
                    return (gun >= 1 && gun <= 4) ? 0.20 : 0.15;
                case 2: // Öğretmen
                    // Sadece Çarşamba(3) %35
                    return (gun == 3) ? 0.35 : 0.0;
                case 3: // Diğer
                default:
                    return 0.0;
            }
        }
        public static double getFormatExtra(int filmTuru) {
            switch (filmTuru) {
                case 2: // 3D
                    return 25.0;
                case 3: // IMAX
                    return 35.0;
                case 4: // 4DX
                    return 50.0;
                case 1: // 2D
                default:
                    return 0.0;
            }
        }
        public static double calculateFinalPrice(int gun, int saat, int yas, int meslek, int filmTuru) {
            // 1. Temel Fiyat
            double basePrice = calculateBasePrice(gun, saat);

            // 2. İndirim Miktarı
            double discountRate = calculateDiscount(yas, meslek, gun);
            double discountAmount = basePrice * discountRate;

            // 3. İndirimli Fiyat
            double discountedPrice = basePrice - discountAmount;

            // 4. Format Ekstra Ücreti
            double formatExtra = getFormatExtra(filmTuru);

            // 5. Toplam Fiyat
            return discountedPrice + formatExtra;
        }
        public static String generateTicketInfo(int gun, int saat, int yas, int meslek, int filmTuru, double finalPrice) {
            double basePrice = calculateBasePrice(gun, saat);
            double discountRate = calculateDiscount(yas, meslek, gun);
            double discountAmount = basePrice * discountRate;
            double formatExtra = getFormatExtra(filmTuru);

            // Gün/Saat durumu
            String durum = (isWeekend(gun) ? "Hafta Sonu " : "Hafta İçi ") + (isMatinee(saat) ? "Matine" : "Normal");

            // Film Türü adı
            String filmTuruAdi;
            switch (filmTuru) {
                case 1: filmTuruAdi = "2D"; break;
                case 2: filmTuruAdi = "3D"; break;
                case 3: filmTuruAdi = "IMAX"; break;
                case 4: filmTuruAdi = "4DX"; break;
                default: filmTuruAdi = "Bilinmiyor"; break;
            }

            // Metin oluşturma
            return String.format(
                    "\n--- Hesaplama Özeti ---\n" +
                            "Girdi: Gün=%d (%s), Saat=%d, Yaş=%d, Meslek=%d, Film Türü=%s\n" +
                            "Temel Fiyat: %.2f TL (%s)\n" +
                            "İndirim Oranı: %.0f%% (%.2f TL)\n" +
                            "İndirimli Fiyat: %.2f TL\n" +
                            "%s Ekstra: +%.2f TL\n" +
                            "---------------------------\n" +
                            "Toplam: **%.2f TL**",
                    gun, durum.split(" ")[0], saat, yas, meslek, filmTuruAdi, // Girdi
                    basePrice, durum, // Temel Fiyat
                    discountRate * 100, discountAmount, // İndirim
                    basePrice - discountAmount, // İndirimli
                    filmTuruAdi, formatExtra, // Ekstra
                    finalPrice // Toplam
            );
        }

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            // 1. Girdileri Al
            System.out.println("Sinema Bileti Fiyatlandırma Sistemi\n");

            System.out.print("Gün (1=Pzt, ..., 7=Paz): ");
            int gun = scanner.nextInt();

            System.out.print("Saat (8-23): ");
            int saat = scanner.nextInt();

            System.out.print("Yaş: ");
            int yas = scanner.nextInt();

            System.out.print("Meslek (1=Öğrenci, 2=Öğretmen, 3=Diğer): ");
            int meslek = scanner.nextInt();

            System.out.print("Film Türü (1=2D, 2=3D, 3=IMAX, 4=4DX): ");
            int filmTuru = scanner.nextInt();

            // 2. Fiyatı Hesapla
            double finalPrice = calculateFinalPrice(gun, saat, yas, meslek, filmTuru);

            // 3. Bilgiyi Yazdır
            String ticketInfo = generateTicketInfo(gun, saat, yas, meslek, filmTuru, finalPrice);
            System.out.println(ticketInfo);

            scanner.close();
        }
}
