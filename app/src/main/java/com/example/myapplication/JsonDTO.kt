package com.example.myapplication

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.squareup.moshi.FromJson
import org.json.JSONObject


data class JsonDTO(
    @SerializedName("Spele")
    val spele: Spele
)

data class Spele(
    @SerializedName("Laiks")
    val laiks: String,
    @SerializedName("Skatitaji")
    val skatitaji: Int,
    @SerializedName("Vieta")
    val vieta: String,
    @SerializedName("T")
    val t: List<T>,
    @SerializedName("Komanda")
    val komanda: List<Komanda>,
    @SerializedName("VT")
    val vT: VT
)

data class T(
    @SerializedName("Uzvards")
    val uzvards: String,
    @SerializedName("Vards")
    val vards: String
)

data class Komanda(
    @SerializedName("Nosaukums")
    val nosaukums: String,
    @SerializedName("Speletaji")
    val speletaji: Speletaji,
    @SerializedName("Pamatsastavs")
    val pamatsastavs: Pamatsastavs,
    @SerializedName("Varti")
    val jsonVarti: JsonElement,
    @SerializedName("Mainas")
    val jsonMainas: JsonElement,
    @SerializedName("Sodi")
    val jsonSodi: JsonElement,
    var varti: Varti,
    var mainas: Mainas,
    var sodi: Sodi,
    var punkti: Int,
    var uzvarasPamatl: Int,
    var zaudesPamatl: Int,
    var uzvarasPapild: Int,
    var zaudesPapild: Int,
    var iegutieVarti: Int,
    var zaudetieVarti: Int
) {
    fun setData() {
        if (!jsonVarti.isJsonPrimitive){
            varti = Gson().fromJson(jsonVarti, Varti::class.java)
        }
        if (!jsonMainas.isJsonPrimitive){
            mainas = Gson().fromJson(jsonMainas, Mainas::class.java)
        }
        if (!jsonSodi.isJsonPrimitive){
            sodi = Gson().fromJson(jsonSodi, Sodi::class.java)
        }
    }
}

data class Speletaji(
    @SerializedName("Speletajs")
    val speletajs: List<Speletaj>
)

data class Speletaj(
    @SerializedName("Loma")
    val loma: String,
    @SerializedName("Nr")
    val nr: Int,
    @SerializedName("Uzvards")
    val uzvards: String,
    @SerializedName("Vards")
    val vards: String,
    var komanda: String,
    var varti: Int,
    var piespeles: Int,
    var sodi: Int,
    var tiesnesis: Boolean,
    var sodietoSkaits: Int,
    var gamesPlayed: Int,
    var ielaistieVarti: Int
)

data class Pamatsastavs(
    @SerializedName("Speletajs")
    val speletajs: List<SpeletajX>
)

data class SpeletajX(
    @SerializedName("Nr")
    val nr: Int
)

data class Varti(
    @SerializedName("VG")
    val vG: JsonElement
) {
    fun getVg(): ArrayList<VG>? {
        return if (vG != null){
            var vGuveji: ArrayList<VG> = ArrayList()
            var vgTemp = VG("1", vG, 1, "1")
            getJsonData(vG, vgTemp, vGuveji as ArrayList<Any>)
            vGuveji
        } else null
    }
}

data class VG(
    @SerializedName("Laiks")
    val laiks: String,
    @SerializedName("P")
    val p: JsonElement,
    @SerializedName("Nr")
    val nr: Int,
    @SerializedName("Sitiens")
    val sitiens: String
) {
    fun getPiespeles(): ArrayList<P>? {
        return if (p != null){
            var piespeles: ArrayList<P> = ArrayList()
            val piespele = P(1)
            getJsonData(p, piespele, piespeles as ArrayList<Any>)
            piespeles
        }
        else null
    }
}

data class P(
    @SerializedName("Nr")
    val nr: Int
)

data class Mainas(
    @SerializedName("Maina")
    val mainasJson: JsonElement
) {
    fun getMainas(): ArrayList<Maina>? {
        return if (mainasJson != null){
            var mainas: ArrayList<Maina> = ArrayList()
            var maina = Maina("1", 1, 1)
            getJsonData(mainasJson, maina, mainas as ArrayList<Any>)
            mainas
        } else null
    }
}

data class Maina(
    @SerializedName("Laiks")
    val laiks: String,
    @SerializedName("Nr1")
    val nr1: Int,
    @SerializedName("Nr2")
    val nr2: Int
)

data class Sodi(
    @SerializedName("Sods")
    val sodsJson: JsonElement
) {
    fun getSodi(): ArrayList<Sods>? {
        return if (sodsJson != null){
            var sodi: ArrayList<Sods> = ArrayList()
            val sods = Sods("1", 1)
            getJsonData(sodsJson, sods, sodi as ArrayList<Any>)
            sodi
        } else null
    }
}

data class Sods(
    @SerializedName("Laiks")
    val laiks: String,
    @SerializedName("Nr")
    val nr: Int
)

data class VT(
    @SerializedName("Uzvards")
    val uzvards: String,
    @SerializedName("Vards")
    val vards: String
)


fun isJsonArray(json: JsonElement): Boolean {
    return json.isJsonArray
}

fun getJsonData(json: JsonElement, type: Any, list: ArrayList<Any>) {
    if (isJsonArray(json)) {
        for (obj in json as JsonArray) {
            if (type is Sods) {
                list.add(Gson().fromJson(obj, Sods::class.java))
            }
            if (type is P) {
                list.add(Gson().fromJson(obj, P::class.java))
            }
            if (type is Maina) {
                list.add(Gson().fromJson(obj, Maina::class.java))
            }
            if (type is VG) {
                list.add(Gson().fromJson(obj, VG::class.java))
            }
        }
    } else {
        if (type is Sods) {
            list.add(Gson().fromJson(json, Sods::class.java))
        }
        if (type is P) {
            list.add(Gson().fromJson(json, P::class.java))
        }
        if (type is Maina) {
            list.add(Gson().fromJson(json, Maina::class.java))
        }
        if (type is VG) {
            list.add(Gson().fromJson(json, VG::class.java))
        }
    }
}