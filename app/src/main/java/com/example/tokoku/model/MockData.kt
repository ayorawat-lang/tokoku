package com.example.tokoku.model

object MockData {
    val categories = listOf(
        "Semua",
        "Elektronik",
        "Fashion Pria",
        "Fashion Wanita",
        "Handphone & Gadget",
        "Hobi & Olahraga",
        "Kecantikan",
        "Rumah Tangga",
        "Otomotif",
        "Mainan"
    )

    val products: List<Product> by lazy {
        generateProducts()
    }

    private fun generateProducts(): List<Product> {
        val list = mutableListOf<Product>()

        val sampleImages = mapOf(
            "Elektronik" to listOf(
                "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400&auto=format&fit=crop&q=80",
                "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&auto=format&fit=crop&q=80"
            ),
            "Fashion Pria" to listOf(
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&auto=format&fit=crop&q=80",
                "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=400&auto=format&fit=crop&q=80"
            ),
            "Fashion Wanita" to listOf(
                "https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=400&auto=format&fit=crop&q=80",
                "https://images.unsplash.com/photo-1434389677669-e08b4cac3105?w=400&auto=format&fit=crop&q=80"
            ),
            "Handphone & Gadget" to listOf(
                "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400&auto=format&fit=crop&q=80",
                "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400&auto=format&fit=crop&q=80"
            ),
            "Hobi & Olahraga" to listOf(
                "https://images.unsplash.com/photo-1601925260368-ae2f83cf8b7f?w=400&auto=format&fit=crop&q=80",
                "https://images.unsplash.com/photo-1517649763962-0c623066013b?w=400&auto=format&fit=crop&q=80"
            ),
            "Kecantikan" to listOf(
                "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=400&auto=format&fit=crop&q=80",
                "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=400&auto=format&fit=crop&q=80"
            ),
            "Rumah Tangga" to listOf(
                "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?w=400&auto=format&fit=crop&q=80",
                "https://images.unsplash.com/photo-1583847268964-b28dc8f51f92?w=400&auto=format&fit=crop&q=80"
            ),
            "Otomotif" to listOf(
                "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=400&auto=format&fit=crop&q=80",
                "https://images.unsplash.com/photo-1558449028-b53a39d100fc?w=400&auto=format&fit=crop&q=80"
            ),
            "Mainan" to listOf(
                "https://images.unsplash.com/photo-1594787318286-3d835c1d207f?w=400&auto=format&fit=crop&q=80",
                "https://images.unsplash.com/photo-1587654780291-39c9404d746b?w=400&auto=format&fit=crop&q=80"
            )
        )

        val storeNames = listOf("Official Store", "Mitra TokoKu", "Power Merchant Pro", "Global Shop ID")
        val locations = listOf("Jakarta Pusat", "Jakarta Selatan", "Bandung", "Surabaya")

        for (category in categories.filter { it != "Semua" }) {
            val images = sampleImages[category] ?: sampleImages.values.first()
            for (i in 1..15) {
                val price = (50000 + (i * 25000) % 2500000).toDouble()
                val originalPrice = if (i % 2 == 0) price * 1.25 else null
                val rating = 4.6f + ((i % 4) * 0.1f)
                val soldCount = 50 + (i * 23) % 1500
                
                list.add(
                    Product(
                        id = "p_${category.take(3)}_$i",
                        name = "$category Unggulan #${i}",
                        price = price,
                        originalPrice = originalPrice,
                        rating = if (rating > 5.0f) 5.0f else rating,
                        soldCount = soldCount,
                        category = category,
                        description = "Produk pilihan berkualitas tinggi untuk kategori $category nomor #$i. Bergaransi resmi dan siap dikirim ke seluruh Indonesia.",
                        imageUrl = images[(i - 1) % images.size],
                        storeName = storeNames[(i - 1) % storeNames.size],
                        storeLocation = locations[(i - 1) % locations.size]
                    )
                )
            }
        }
        return list
    }
}
