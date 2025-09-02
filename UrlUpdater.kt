import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    val klasorAdi = "src" // Tarayacağın klasör. testdenemerett


    // Eski URL -> Yeni URL eşleştirmeleri
    val urlDegisiklikleri = mapOf(
        "eski-site1.com" to "yeni-site1.com",
        "eski-site2.com" to "yeni-site2.com",
        "eski-site3.com" to "yeni-site3.com"
    )

    val tarih = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))

    // Regex ile URL’leri tespit eden basit pattern
    val urlRegex = Regex("""https?://[^\s"']+""")

    // Klasördeki tüm Kotlin dosyaları
    val ktDosyalari = File(klasorAdi).walkTopDown().filter { it.isFile && it.extension == "kt" }

    for (dosya in ktDosyalari) {
        // Yedek oluştur
        val yedekDosya = File(dosya.parentFile, "${dosya.nameWithoutExtension}_backup_$tarih.kt")
        dosya.copyTo(yedekDosya)
        println("Yedek alındı: ${yedekDosya.path}")

        val satirlar = dosya.readLines()
        val guncellenmisSatirlar = satirlar.map { satir ->
            var yeniSatir = satir
            // Satırdaki tüm URL’leri tespit et
            urlRegex.findAll(satir).forEach { match ->
                val url = match.value
                // Eğer URL eşleşiyorsa değiştir
                urlDegisiklikleri[url]?.let { yeniUrl ->
                    yeniSatir = yeniSatir.replace(url, yeniUrl)
                }
            }
            yeniSatir
        }

        dosya.writeText(guncellenmisSatirlar.joinToString("\n"))
        println("Güncellendi: ${dosya.path}")
    }

    println("Tüm Kotlin dosyalarındaki URL’ler başarıyla otomatik güncellendi!")
}
