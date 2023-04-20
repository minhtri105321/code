package com.example.kt_firebase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kt_firebase.R
import com.example.kt_firebase.adapters.EmpAdapter
import com.example.kt_firebase.modules.Employees
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var empRecycleView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<Employees>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        // khởi taok một layout recycleview
        empRecycleView = findViewById(R.id.recyclerView)
        empRecycleView.layoutManager = LinearLayoutManager(this)
        empRecycleView.setHasFixedSize(true)

        tvLoadingData = findViewById(R.id.tvLoadingData)


        empList = arrayListOf<Employees>()

        getEmployeesData()
    }

//    lấy dữ liệu tên của các người dùng để tạo thành danh sách
    private fun getEmployeesData(){
        empRecycleView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if(snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(Employees::class.java)
                        empList.add(empData!!)
                    }
                    val mAdapter = EmpAdapter(empList)
                    empRecycleView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity, ModifyEmpActivity::class.java)
                            intent.putExtra("empId", empList[position].empId)
                            intent.putExtra("empName", empList[position].empName)
                            intent.putExtra("empAge", empList[position].empAge)
                            intent.putExtra("empJob", empList[position].empJob)
                            intent.putExtra("empBirthday", empList[position].empBirthday)
                            startActivity(intent)
                        }

                    })

                    empRecycleView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}