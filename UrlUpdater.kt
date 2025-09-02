import shutil
import datetime
import os

# Güncellenecek dosya
dosya_adi = "urls.txt"

# Eski URL -> Yeni URL eşleştirmeleri
url_degisiklikleri = {
    "eski-site1.com": "yeni-site1.com",
    "eski-site2.com": "yeni-site2.com",
    "eski-site3.com": "yeni-site3.com"
}

# Dosyanın bulunduğu dizin
dosya_yolu = os.path.join(os.path.dirname(__file__), dosya_adi)

# Yedek dosya adı (tarih ekleyerek)
tarih = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
yedek_dosya = f"{dosya_yolu}_backup_{tarih}.txt"

# Dosyayı yedekle
shutil.copyfile(dosya_yolu, yedek_dosya)
print(f"Yedek alındı: {yedek_dosya}")

# Dosyayı oku
with open(dosya_yolu, "r", encoding="utf-8") as file:
satirlar = file.readlines()

# Tüm eski URL'leri yeni URL'lerle değiştir
guncellenmis_satirlar = []
for satir in satirlar:
for eski, yeni in url_degisiklikleri.items():
satir = satir.replace(eski, yeni)
guncellenmis_satirlar.append(satir)

# Güncellenmiş URL’leri dosyaya yaz
with open(dosya_yolu, "w", encoding="utf-8") as file:
file.writelines(guncellenmis_satirlar)

print("Tüm URL’ler başarıyla güncellendi!")
