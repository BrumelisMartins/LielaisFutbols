package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.ActivityMainBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
private const val NUM_PAGES = 4

class MainActivity : AppCompatActivity() {

    var client: FutbolsApiService = retrofit

    val spelesJson =  MutableLiveData<JsonDTO>()
    private lateinit var viewPager: ViewPager2



    lateinit var dbSpeles: SpelesDb
    lateinit var dbSpeletaji : SpeletajsDb

    lateinit var dbKomandas : KomandasDb

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        dbSpeles = Room.databaseBuilder(applicationContext, SpelesDb::class.java, "SpelesDB")
            .build()
        dbSpeletaji = Room.databaseBuilder(applicationContext, SpeletajsDb::class.java, "SpeletajuDB")
            .build()
        dbKomandas = Room.databaseBuilder(applicationContext, KomandasDb::class.java, "KomandasDB")
            .build()

        val spelesObserver = Observer<JsonDTO> { newJson ->
            insertSpele(newJson.spele)
        }
        spelesJson.observe(this, spelesObserver)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.pager.adapter = pagerAdapter

        binding.editButton.setOnClickListener {
            val url = binding.editUrl.text
            if (url != null){
                if (url.toString().contains("api.myjson.com/")){
                    var urlString = url.toString()
                    var delimiter = "myjson.com/"
                    val parts = urlString.split(delimiter)
                    val query = parts.last()
                    try{
                        CoroutineScope(Dispatchers.IO).launch {
                            val jsonDto = client.getProperties(query)
                            withContext(Dispatchers.Main) {
                                setKomanda(jsonDto.spele)
                                setSpeletaji(jsonDto.spele)
                                spelesJson.postValue(jsonDto)
                            }
                        }
                    }catch (e: Throwable){
                        Log.d("Error123", e.message)
                        Toast.makeText(this, "Neizdevās pievienot spēli", Toast.LENGTH_SHORT).show()

                    }

                }
                else {
                    Toast.makeText(this, "Lūdzu pievienojiet pareizu adresi", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Lūdzu pievienojiet pareizu adresi", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun insertSpele(spele: Spele){
        CoroutineScope(Dispatchers.IO).launch {
            val speleRetreived : SpelesEntity? = dbSpeles.spelesDao().loudGame(spele.laiks, spele.skatitaji, spele.vieta)
            if (speleRetreived == null) {
                Log.d("debug123", "NO SUCH GAME FOUND, INSERTING")
                val speleNew = SpelesEntity(spele.laiks, spele.skatitaji, spele.vieta)
                dbSpeles.spelesDao().insert(speleNew)
                for (komanda in spele.komanda)
                    insertKomanda(komanda)
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity, "Spēle pievienota", Toast.LENGTH_SHORT).show()
                }

            }
            else withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity, "Spēle jau ir pievienota!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun insertKomanda(k: Komanda) {
        val komandaRetreived : KomandasEntity? = dbKomandas.komandasDao().loudTeam(k.nosaukums)
        val komandaNew = KomandasEntity(k.nosaukums, k.punkti, k.uzvarasPamatl, k.zaudesPamatl,
            k.uzvarasPapild, k.zaudesPapild, k.iegutieVarti, k.zaudetieVarti)
        if (komandaRetreived != null){
            komandaRetreived.punkti += komandaNew.punkti
            komandaRetreived.uzvarasPamatl += komandaNew.uzvarasPamatl
            komandaRetreived.zaudesPamatl += komandaNew.zaudesPamatl
            komandaRetreived.uzvarasPapild += komandaNew.zaudesPapild
            komandaRetreived.zaudesPapild += komandaNew.zaudesPapild
            komandaRetreived.zaudetieVarti += komandaNew.zaudetieVarti
            komandaRetreived.iegutieVarti += komandaNew.iegutieVarti
            dbKomandas.komandasDao().insert(komandaRetreived)
        }
        else {
            dbKomandas.komandasDao().insert(komandaNew)
        }
        insertSpeletajs(k)
    }

    suspend fun insertSpeletajs(komanda: Komanda){
        for (s in komanda.speletaji.speletajs) {
            val speletajsRetreived: SpeletajsEntity? = dbSpeletaji.speletajsDao().loadUser(s.nr, s.uzvards, s.vards)
            val speletajsNew = SpeletajsEntity(s.nr, s.uzvards, s.vards, s.loma, s.komanda, s.varti, s.piespeles,
                s.sodi, s.tiesnesis, s.sodietoSkaits, s.gamesPlayed, s.ielaistieVarti)
            if (speletajsRetreived != null) {
                speletajsRetreived.varti += s.varti
                speletajsRetreived.piespeles += s.varti
                speletajsRetreived.sodi += s.sodi
                speletajsRetreived.sodietoSkaits += s.sodietoSkaits
                speletajsRetreived.gamesPlayed += s.gamesPlayed
                speletajsRetreived.ielaistieVarti += s.ielaistieVarti
                dbSpeletaji.speletajsDao().insert(speletajsRetreived)
            }
            else {
                dbSpeletaji.speletajsDao().insert(speletajsNew)
            }
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: AppCompatActivity) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment = ScreenSlideFragment(dbKomandas, dbSpeletaji, position)
    }


}









