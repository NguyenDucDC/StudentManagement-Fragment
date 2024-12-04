package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val etName = findViewById<EditText>(R.id.etName)
        val etMSSV = findViewById<EditText>(R.id.etMSSV)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val mssv = etMSSV.text.toString()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                val resultIntent = Intent()
                val newStudent = StudentModel(name, mssv)

                resultIntent.putExtra("newName", name)
                resultIntent.putExtra("newId", mssv)
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Kết thúc Activity
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show()
                etName.error = "Please enter name"
                etMSSV.error = "Please enter MSSV"
            }
        }
    }
}
