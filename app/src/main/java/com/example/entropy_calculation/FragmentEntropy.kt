package com.example.entropy_calculation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.entropy_calculation.databinding.FragmentEntropyBinding
import java.math.RoundingMode


class FragmentEntropy : Fragment() {

    private lateinit var bilding:FragmentEntropyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bilding = FragmentEntropyBinding.inflate(inflater,container,false)
        var db: MainDb = MainDb.getDb(requireContext())

        //вставка при переходе
        if(db.getDao().existenceCheck()>0) {
            var data = db.getDao().getData()[0]
            bilding.tvInput.setText(data.message)
            InsertEntropy(data)
        }

        //очистка текста
        bilding.btClear.setOnClickListener{
            bilding.tvInput.text.clear()
            bilding.tvUncondEntropy.text = ""
            bilding.tvMaxEntropy.text = ""
            bilding.tvAlphabetOverload.text = ""
            bilding.tvFirstEntropy.text = ""
            db.getDao().deleteCharList()
            db.getDao().deleteData()
        }

        //просчёт
        bilding.btCount.setOnClickListener{
            db.getDao().deleteCharList()
            db.getDao().deleteData()

            val message = bilding.tvInput.text.toString()
            val size:Double = message.length.toDouble()
            var charList:MutableList<Probability> = mutableListOf()
            var bigramList:MutableList<Probability> = mutableListOf()

            //подсчет вероятностей символов алфавита
            for(i in message.indices){
                var countChar = 1
                if(i == 0 || ContCharInPribabil(message[i],charList)) {
                    for (j in i + 1 until message.length)
                        if (message[j] == message[i])
                            countChar++
                    val prob = (countChar / size).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
                    charList.add(Probability(null,message[i].toString(), prob,null,null))
                }
            }
            //подсчет вероятностей биграмм
            for(i in 1 until message.length){
                var countBigram = 1
                val bigram:String = message[i-1].toString() + message[i]
                if(i == 1 || ContBigramInPribabil(bigram,bigramList)) {
                    for (j in i + 1 until message.length)
                        if(bigram == (message[j-1].toString()+message[j]))
                            countBigram++
                    val prob = (countBigram / (size-1)).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
                    bigramList.add(Probability(null,null,null,bigram,prob))
                }
            }
            // вывод энтропий
            var uncondEntropy = 0.0
            for(i in charList)
                uncondEntropy -= i.probabilChar!!*Math.log10(i.probabilChar!!)/Math.log10(2.0)
            var maxEntropy = Math.log10(charList.size.toDouble())/Math.log10(2.0)
            var alphOver = maxEntropy - uncondEntropy
            var firstEntropy = 0.0
            for(i in charList){
                for(j in bigramList){
                    if(j.bigram!![0] == i.chars!![0])
                    firstEntropy -= i.probabilChar!! * j.probabilBigram!! * Math.log10(j.probabilBigram!!)/Math.log10(2.0)
                }
            }
            //оклугление до 2-ого знака
            uncondEntropy = uncondEntropy.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
            maxEntropy = maxEntropy.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
            alphOver = alphOver.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
            firstEntropy = firstEntropy.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()

            var data = Data(null,message,uncondEntropy, maxEntropy,alphOver, firstEntropy)

            Thread {
                db.getDao().insertCharList(charList)
                db.getDao().insertCharList(bigramList)
                db.getDao().insertData(data)
            }.start()

            InsertEntropy(data)
        }

        return bilding.root
    }
    fun InsertEntropy (data: Data){
        bilding.tvUncondEntropy.text = "Безусловная энтропия: ${data.uncondEntropy}"
        bilding.tvMaxEntropy.text = "Макс. энтропия источника: ${data.maxEntropy}"
        bilding.tvAlphabetOverload.text = "Перегрузка алфавита источника: ${data.alphOver}"
        bilding.tvFirstEntropy.text = "Энтропия первого порядка: ${data.firstEntropy}"
    }

    //проверка на содержание символа
    fun ContCharInPribabil(char:Char, list:List<Probability>):Boolean{
        for(i in list)
            if(char.toString() == i.chars)
                return false
        return true
    }

    //проверка на содержание биграммы
    fun ContBigramInPribabil(bigram:String, list:List<Probability>):Boolean{
        for(i in list)
            if(bigram == i.bigram)
                return false
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentEntropy().apply {
                arguments = Bundle().apply {
                }
            }
    }
}