package com.example.myapplication

import android.icu.text.SimpleDateFormat
import android.util.Log

fun setKomanda(spele: Spele) {
    for (komanda in spele.komanda) {
        komanda.setData()
    }
    val df = SimpleDateFormat("mm:ss")
    val komanda1 = spele.komanda[0]
    val komanda2 = spele.komanda[1]
    val komanda1varti = if (komanda1.jsonVarti.isJsonPrimitive) 0
    else komanda1.varti.getVg()!!.size

    val komanda2varti = if (komanda2.jsonVarti.isJsonPrimitive) 0
    else komanda2.varti.getVg()!!.size


    val komanda1pedejie = if (komanda1.jsonVarti.isJsonPrimitive) 0
    else df.parse(komanda1.varti.getVg()!![komanda1varti - 1].laiks).minutes

    val komanda2pedejie = if (komanda2.jsonVarti.isJsonPrimitive) 0
    else df.parse(komanda2.varti.getVg()!![komanda2varti - 1].laiks).minutes
    komanda1.iegutieVarti = komanda1varti
    komanda1.zaudetieVarti = komanda2varti
    komanda2.iegutieVarti = komanda2varti
    komanda2.zaudetieVarti = komanda1varti
    if (komanda1varti > komanda2varti) {
        if (komanda1pedejie < 60) {
            komanda1.uzvarasPamatl = 1
            komanda2.zaudesPamatl = 1
            komanda1.punkti = 5
            komanda2.punkti = 1
        } else {
            komanda1.uzvarasPapild = 1
            komanda2.zaudesPapild = 1
            komanda1.punkti = 3
            komanda2.punkti = 2
        }

    } else {
        if (komanda2pedejie < 60) {
            komanda2.uzvarasPamatl = 1
            komanda1.zaudesPamatl = 1
            komanda2.punkti = 5
            komanda1.punkti = 1
        } else {
            komanda2.uzvarasPapild = 1
            komanda1.zaudesPapild = 1
            komanda2.punkti = 3
            komanda1.punkti = 2
        }
    }
}

fun setSpeletaji(spele: Spele) {
    for (komanda in spele.komanda) {
        for (speletajs in komanda.speletaji.speletajs) {
            speletajs.komanda = komanda.nosaukums
            speletajs.tiesnesis = false
            speletajs.sodi = 0
            speletajs.ielaistieVarti = 0

            if (!komanda.jsonVarti.isJsonPrimitive) {
                for (vartuGuvejs in komanda.varti.getVg()!!) {
                    if (vartuGuvejs.nr == speletajs.nr) {
                        speletajs.varti++
                    }
                    if (vartuGuvejs.getPiespeles() != null) {
                        for (piespeletajs in vartuGuvejs.getPiespeles()!!) {
                            if (piespeletajs.nr == speletajs.nr) {
                                speletajs.piespeles++
                            }
                        }
                    }
                }
            }
            if (komanda.sodi != null) {
                for (sodamais in komanda.sodi.getSodi()!!) {
                    if (sodamais.nr == speletajs.nr) {
                        speletajs.sodi++
                    }
                }
            }
            for (pamatSpeletajs in komanda.pamatsastavs.speletajs) {
                if (pamatSpeletajs.nr == speletajs.nr) {
                    speletajs.gamesPlayed = 1
                    if (speletajs.loma == "V"){
                        speletajs.ielaistieVarti = komanda.zaudetieVarti
                    }
                }
            }
            if (komanda.mainas != null) {
                if (speletajs.gamesPlayed == 0) {
                    for (maina in komanda.mainas.getMainas()!!) {
                        if (maina.nr2 == speletajs.nr) {
                            speletajs.gamesPlayed = 1
                        }
                    }
                }
            }

        }
    }
}