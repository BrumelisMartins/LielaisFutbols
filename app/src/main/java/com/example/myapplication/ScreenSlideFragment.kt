package com.example.myapplication


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.myapplication.databinding.SliderFragmentLayoutBinding

class ScreenSlideFragment(dbKomandas: KomandasDb, dbSpeletaji: SpeletajsDb, counter: Int) : Fragment() {

    val userDati =  MutableLiveData<List<SpeletajsEntity>>()
    val komandasDati = MutableLiveData<List<KomandasEntity>>()
    val rupjakieDati = MutableLiveData<List<SpeletajsEntity>>()
    val vartsarguDati = MutableLiveData<List<Vartusargs>>()



    val dbKomandas = dbKomandas
    val dbSpeletaji = dbSpeletaji
    var counter = counter

    lateinit var binding: SliderFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):  View {

        binding = DataBindingUtil.inflate(inflater, R.layout.slider_fragment_layout, container, false)
        val komandasAdapter = KomandasAdapter()
        val speletajuAdapter = VisuSpeletajuAdapter()
        val rupjakoAdapter = RupjakoSpeletajuAdapter()
        val vartsarguAdapter = VartsarguAdapter()


        if (counter == 0){
            CoroutineScope(Dispatchers.IO).launch {
                val allKomandas = dbKomandas.komandasDao().getAllGames()
                val sortedKomandas = allKomandas.sortedByDescending{it.punkti}
                komandasDati.postValue(sortedKomandas)
            }
            binding.komandasRecycler.adapter = komandasAdapter
            binding.listesNosaukums.text = "Komandas"
        }
        if (counter == 1){
            CoroutineScope(Dispatchers.IO).launch {
                val allUsers = dbSpeletaji.speletajsDao().getAllPlayers()
                val sortedUsers = allUsers.sortedWith(compareBy({it.varti}, {it.piespeles})).asReversed()
                val topTen = sortedUsers.take(10)
                userDati.postValue(topTen)
            }
            binding.komandasRecycler.adapter = speletajuAdapter
            binding.listesNosaukums.text = "Labākie Spēlētāji"
        }
        if (counter == 2){
            CoroutineScope(Dispatchers.IO).launch {
                val allUsers = dbSpeletaji.speletajsDao().getAllPlayers()
                val sortedUsers = allUsers.sortedByDescending{it.sodi}
                val topTen = sortedUsers.take(10)
                rupjakieDati.postValue(topTen)
            }
            binding.komandasRecycler.adapter = rupjakoAdapter
            binding.listesNosaukums.text = "Rupjākie Spēlētāji"
        }
        if (counter == 3){
            CoroutineScope(Dispatchers.IO).launch {
                val allUsers = dbSpeletaji.speletajsDao().getAllPlayers()
                val vartsargu = ArrayList<Vartusargs>()
                for (t in allUsers){
                    if (t.loma == "V") {
                        var attieciba = 0.0
                        if (t.gamesPlayed > 0){
                            if (t.ielaistieVarti > 0){
                                attieciba = (t.ielaistieVarti.toDouble() / t.gamesPlayed)
                            }
                            val vartusargs = Vartusargs(t.vards,t.uzvards,t.gamesPlayed,t.ielaistieVarti, attieciba, t.komanda)
                            vartsargu.add(vartusargs)
                        }

                    }
                }
                vartsargu.sortWith(compareBy {it.attieciba})
                vartsarguDati.postValue(vartsargu)
            }
            binding.komandasRecycler.adapter = vartsarguAdapter
            binding.listesNosaukums.text = "Labākie vārtusargi"
        }



        val komandasObserver = Observer<List<KomandasEntity>> { newKomandas ->
            komandasAdapter.komanda = ArrayList(newKomandas)
        }
        komandasDati.observe(this, komandasObserver)

        val userObserver = Observer<List<SpeletajsEntity>> { newUserList ->
            speletajuAdapter.speletaji = ArrayList(newUserList)
            }
        userDati.observe(this, userObserver)

        val rupjakoObserver = Observer<List<SpeletajsEntity>> { newUserList ->
            rupjakoAdapter.speletaji = ArrayList(newUserList)
        }
        rupjakieDati.observe(this, rupjakoObserver)

        val vartsarguObserver = Observer<List<Vartusargs>> { newUserList ->
            vartsarguAdapter.speletaji = ArrayList(newUserList)
        }
        vartsarguDati.observe(this, vartsarguObserver)

        binding.setLifecycleOwner { this.lifecycle }

        return binding.root
    }
}