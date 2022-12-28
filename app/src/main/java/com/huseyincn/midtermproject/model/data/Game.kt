package com.huseyincn.midtermproject.model.data

data class Game(
    val id: Int,
    val name: String,
    val metacritic: Int?,
    val genres: List<Genres>,
    val background_image: String?,
    var isChecked: Boolean = false,
    var isFav: Boolean = false
)


/*

package com.huseyincn.midtermproject.model.data

/*
name = oyunun ismini tutuyor
score = meta critict değerini tutuyor
genres = array olarak oyunun türünü tutuyor
isChecked = kullanıcının detail sayfasını kontrol edip etmediğini tutuyor
 */

data class Game(
    val name: String,
    val score: Int,
    val genres: Array<String>,
    var isChecked: Boolean = false,
    var isFav: Boolean = false
)

// tutulacak daha fazla VERİ
// oyun hakkında detay
// oyun görseli
// oyun reddit page
// oyun website

 */
