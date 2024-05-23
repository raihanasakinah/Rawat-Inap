package com.example.raihanasakinah_2057.wiget

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.raihanasakinah_2057.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RawatActivity : AppCompatActivity() {
    private lateinit var datePicker: DatePickerDialog.OnDateSetListener
    private lateinit var timePicker: TimePickerDialog.OnTimeSetListener
    private lateinit var tvTglMasuk: TextView
    private lateinit var tvJamMasuk: TextView

    private lateinit var etRegister: EditText
    private lateinit var etNama: EditText
    private lateinit var etNIK: EditText
    private lateinit var etHP: EditText
    private lateinit var etJumlahHari: EditText
    private lateinit var chkBedSofa: CheckBox
    private lateinit var etBedSofa: EditText
    private lateinit var chkSofa: CheckBox
    private lateinit var etSofa: EditText
    private lateinit var chkDispenser: CheckBox
    private lateinit var etDispenser: EditText
    private lateinit var chkMakanan: CheckBox
    private lateinit var etMakanan: EditText
    private lateinit var rgKategoriPasien: RadioGroup
    private lateinit var btnSimpan: Button
    private lateinit var tvBayar: TextView
    private lateinit var spKelasKamar: Spinner
    private lateinit var pilStatus: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rawat2)

        //Inisialisasi
        etRegister = findViewById(R.id.etRegister)
        etNama = findViewById(R.id.etNama)
        etBedSofa = findViewById(R.id.etBedSofa)
        etSofa = findViewById(R.id.etSofa)
        etDispenser = findViewById(R.id.etDispenser)
        etMakanan = findViewById(R.id.etMakanan)
        etNIK = findViewById(R.id.etNIK)
        etHP = findViewById(R.id.etHP)
        etJumlahHari = findViewById(R.id.etJumlahHari)
        spKelasKamar = findViewById(R.id.spKelasKamar)

        tvTglMasuk = findViewById(R.id.tvTglMasuk)
        tvJamMasuk = findViewById(R.id.tvJamMasuk)
        spKelasKamar = findViewById(R.id.spKelasKamar)

        //Panggil Fungsi
        setTanggal()
        setJam()
        setKelasKamar()

        //variable untuk checkbox
        chkBedSofa = findViewById(R.id.chkBedSofa)
        chkSofa = findViewById(R.id.chkSofa)
        chkDispenser = findViewById(R.id.chkDispenser)
        chkMakanan = findViewById(R.id.chkMakanan)

        //Proses
        btnSimpan = findViewById(R.id.btnSimpan)

        //Var Hasil
        tvBayar = findViewById(R.id.tvBayar)

        //Variable untuk menampung radioGroup
        rgKategoriPasien = findViewById(R.id.rgKategoriPasien)
        pilStatus = findViewById(rgKategoriPasien.checkedRadioButtonId)

    }

    override fun onStart() {
        super.onStart()
        SIMPAN()
    }

    private fun SIMPAN(){
        //Proses akan dijalankan ketika klik btnSimpan
        btnSimpan.setOnClickListener {
            var BiayaTambah = ""
            var pilStatus = findViewById<RadioButton>(rgKategoriPasien.checkedRadioButtonId)
            var bayar = 0.0
            var totalBayar = 0.0




            if (chkBedSofa.isChecked){

                if (etBedSofa.text.isEmpty()){
                    etBedSofa.setText("1")
                }
                bayar += 75_000 * etBedSofa.text.toString().toDouble()
                BiayaTambah += "${chkBedSofa.text} jumlah: ${etBedSofa.text}Hari\n"
            }
            if (chkSofa.isChecked){

                if (etSofa.text.isEmpty()){
                    etSofa.setText("1")
                }
                bayar += 60_000 * etSofa.text.toString().toDouble()
                BiayaTambah += "${chkSofa.text} jumlah: ${etSofa.text}Hari\n"
            }
            if (chkDispenser.isChecked){

                if (etDispenser.text.isEmpty()){
                    etDispenser.setText("1")
                }
                bayar += 25_000 * etDispenser.text.toString().toDouble()
                BiayaTambah += "${chkDispenser.text} jumlah: ${etDispenser.text}Hari\n"
            }
            if (chkMakanan.isChecked){

                if (etMakanan.text.isEmpty()){
                    etMakanan.setText("1")
                }
                bayar += 45_000 * etMakanan.text.toString().toDouble()
                BiayaTambah += "${chkMakanan.text} jumlah: ${etMakanan.text}Hari\n"
            }


            var diskon = 0.0
            val status = findViewById<RadioButton>(rgKategoriPasien.checkedRadioButtonId)

            diskon = if (status.text.equals("BPJS")){
                0.1
            }else if (status.text.equals("NonBPJS")){
                0.05
            } else{
                0.03
            }

            var hargakamar = 0.0
            hargakamar = if (spKelasKamar.selectedItem.equals("Kelas Super VIP")){
                etJumlahHari.text.toString().toDouble()*750000.0
            }else if (spKelasKamar.selectedItem.equals("Kelas VIP")){
                etJumlahHari.text.toString().toDouble()*600000.0
            } else if (spKelasKamar.selectedItem.equals("Kelas Utama")){
                etJumlahHari.text.toString().toDouble()*400000.0
            } else if (spKelasKamar.selectedItem.equals("Kelas I")){
                etJumlahHari.text.toString().toDouble()*255000.0
            } else if (spKelasKamar.selectedItem.equals("Kelas II")){
                etJumlahHari.text.toString().toDouble()*170000.0
            } else {
                etJumlahHari.text.toString().toDouble()*160000.0
            }

            if (etJumlahHari.text.toString().toDouble() >3) {
                totalBayar = (bayar + hargakamar) - (hargakamar*diskon) - 50000.0
            }
            else {
                totalBayar = (bayar + hargakamar) - (hargakamar*diskon)
            }


            var BAYAR = """
                |No Register: ${etRegister.text} 
                |Nama Lengkap: ${etNama.text}
                |NIK : ${etNIK.text}
                |HP : ${etHP.text}
                |Tanggal Masuk : ${tvTglMasuk.text}
                |Jam Masuk : ${tvJamMasuk.text}
                |Kelas Kamar : ${spKelasKamar.selectedItem}
                |Kategori Pasien : ${pilStatus.text}
                |Jumlah Hari : ${etJumlahHari.text}
                |Biaya Tambahan : 
                |$BiayaTambah
                |Total Bayar = $totalBayar
            """.trimMargin()

            tvBayar.text = BAYAR

            //Tampilkan hasil input
            tvBayar.text = BAYAR
            Log.d("Hasil Input", BAYAR)
            Toast.makeText(applicationContext,BAYAR, Toast.LENGTH_LONG).show()

        }
    }

    private fun setTanggal() {
        val myCalendar = Calendar.getInstance()
        datePicker = DatePickerDialog.OnDateSetListener { _, tahun, bulan, hari ->
            myCalendar[Calendar.YEAR] = tahun
            myCalendar[Calendar.MONTH] = bulan
            myCalendar[Calendar.DAY_OF_MONTH] = hari
            val formatIndonesia = "dd-MMMM-yyyy"
            val sdf = SimpleDateFormat(formatIndonesia, Locale.US)
            //isikan ke dalam tvtgl
            tvTglMasuk.text = sdf.format(myCalendar.time)
        }

        //untuk menampilkan dialog date tambahan evant
        tvTglMasuk.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

    }

    private fun setJam() {
        val myCurrentTime = Calendar.getInstance()
        timePicker = TimePickerDialog.OnTimeSetListener { _, jam, menit ->
            myCurrentTime.set(Calendar.HOUR_OF_DAY, jam)
            myCurrentTime.set(Calendar.MINUTE, menit)

            //isikan time ke dalam
            tvJamMasuk.text = SimpleDateFormat("HH:mm").format(myCurrentTime.time)
        }
        //tambhakna evant onclik pada tvJam
        tvJamMasuk.setOnClickListener {
            TimePickerDialog(
                this,
                timePicker,
                myCurrentTime.get(Calendar.HOUR_OF_DAY),
                myCurrentTime.get(Calendar.MINUTE),
                true
            ).show()
        }

    }

    private fun setKelasKamar(){
        val listKelasKamar = arrayOf("Kelas Super VIP","Kelas VIP", "Kelas Utama", "Kelas I", "Kelas II", "Kelas III")
        val adapterSpKelasKamar: ArrayAdapter<*>

        adapterSpKelasKamar = ArrayAdapter(
            this,
            R.layout.spin_style,listKelasKamar)

        //tampilkan ke spKondisi menggunakan adapter
        spKelasKamar.adapter = adapterSpKelasKamar
    }

}