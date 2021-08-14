package com.insidecoderz.databasedemo

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.widget.ListPopupWindow
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage

class UserDetails : AppCompatActivity() {

    var listDataColg = ArrayList<String>()
    var listDataCourse = ArrayList<String>()
    var listDataSem = ArrayList<String>()
    var listDataBranch = ArrayList<String>()

    lateinit var name_userdeatils:TextView
    lateinit var college:TextView
    lateinit var course:TextView
    lateinit var branch:TextView
    lateinit var sem:TextView
    lateinit var radioGroup_role: RadioGroup
    lateinit var btn_proceed:Button
    private lateinit var firebaseUser: FirebaseUser
    private var storageProfileRef : StorageReference?= null
    var context = this@UserDetails
    lateinit var listPopupWindow: ListPopupWindow


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        college=findViewById(R.id.college)
        course=findViewById(R.id.course)
        name_userdeatils=findViewById(R.id.name_userdeatils)
        sem=findViewById(R.id.sem)

        branch=findViewById(R.id.branch)
        radioGroup_role=findViewById(R.id.radioGroup_role)
        btn_proceed=findViewById(R.id.btn_proceed)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storageProfileRef = FirebaseStorage.getInstance().reference.child("Profile Dp")


        collegepopup()
        listcourse()
        listsem()
        listbranch()


        btn_proceed.setOnClickListener {
            saveData()
        }


    }


    private fun saveData() {
        val fullname: String = name_userdeatils.text.toString()
        val collegename: String = college.text.toString()
        val coursename: String = course.text.toString()
        val semname: String = sem.text.toString()
        val branchname: String = branch.text.toString()
        val rolename: String = radioGroup_role.toString()


        when {


            TextUtils.equals(collegename, "College") -> {
                college.error = "Required college name"
                college.requestFocus()
                return
            }

            TextUtils.equals(coursename, "Course") -> {
                course.error = "Required college name"
                course.requestFocus()
                return
            }
            TextUtils.equals(semname, "Semester") -> {
                sem.error = "Required college name"
                sem.requestFocus()
                return
            }
            TextUtils.equals(branchname, "Branch") -> {
                branch.error = "Required branch name"
                branch.requestFocus()
                return
            }


            else -> {

                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Getting things ready")
                progressDialog.setMessage("Please wait,this may take a while...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()


                userInfo(
                    fullname,
                    collegename,
                    coursename,
                    semname,
                    branchname,
                    rolename,
                    progressDialog
                )

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun userInfo(
        fullname: String,
        collegename: String,
        coursename: String,
        semname: String,
        branchname: String,
        rolename: String,
        progressDialog: ProgressDialog
    ) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val dataRef = FirebaseDatabase.getInstance().reference.child("Users")




      //  ref.child(firebaseUser.uid).updateChildren(userMap)




        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserId
        userMap["fullname"] = fullname
        userMap["collegename"] = collegename
        userMap["rolename"] = rolename
        userMap["coursename"] = coursename
        userMap["semname"] = semname
        userMap["branchname"] = branchname
         dataRef.child(firebaseUser.uid).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //   progressDialog.dismiss()
                    Toast.makeText(this,
                        "Account has been created successfully",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }









    private fun collegepopup() {
        listPopupWindow = ListPopupWindow(context)
        listDataColg.add("Graphic Era (Deemed)")
        listDataColg.add("Graphic Era (Hill)")
        listDataColg.add("Graphic Era (Bhimtal)")
    }

    private fun listcourse() {
        listPopupWindow = ListPopupWindow(context)
        listDataCourse.add("B.tech")
        listDataCourse.add("BBA")
        listDataCourse.add("BCA")
        listDataCourse.add("B.Sc")
        listDataCourse.add("BA")
        listDataCourse.add("B.Com")
        listDataCourse.add("BHM")
        listDataCourse.add("MCA")
        listDataCourse.add("M.tech")
        listDataCourse.add("M.Sc")
        listDataCourse.add("MHM")
        listDataCourse.add("MBA")

    }

    private fun listbranch() {
        listPopupWindow = ListPopupWindow(context)
        listDataBranch.add("COMPUTER SCIENCE AND ENGINEERING (CSE)")
        listDataBranch.add("MECHANICAL ENGINEERING")
        listDataBranch.add("ELECTRICAL ENGINEERING")
        listDataBranch.add("CIVIL ENGINEERING")
        listDataBranch.add("ELECTRONICS AND COMMUNICATION ENGINEERING")
        listDataBranch.add("COMPUTER SCIENCE & TECHNOLOGY (HONS.) WITH SPECIALIZATION IN CLOUD COMPUTING")
        listDataBranch.add("COMPUTER SCIENCE & TECHNOLOGY (HONS.) WITH SPECIALIZATION IN MACHINE LEARNING & ARTIFICIAL INTELLIGENCE")
        listDataBranch.add("COMPUTER ENGINEERING (HONS.) WITH SPECIALIZATION IN INFORMATION SECURITY AND ARTIFICIAL INTELLIGENCE")
        listDataBranch.add("COMPUTER ENGINEERING (HONS.) WITH SPECIALIZATION IN ARTIFICIAL INTELLIGENCE AND ROBOT BUILDING")
        listDataBranch.add("COMPUTER ENGINEERING (HONS.) WITH SPECIALIZATION IN DEEP LEARNING AND ARTIFICIAL INTELLIGENCE")
        listDataBranch.add("COMPUTER ENGINEERING (HONS.) WITH SPECIALIZATION IN BLOCKCHAIN")
        listDataBranch.add("COMPUTER ENGINEERING (HONS.) WITH SPECIALIZATION IN CYBER SECURITY")
        listDataBranch.add("COMPUTER SCIENCE & TECHNOLOGY")
        listDataBranch.add("COMPUTER ENGINEERING")
        listDataBranch.add("ARTIFICIAL INTELLIGENCE & DATA SCIENCE")
        listDataBranch.add("BIOTECHNOLOGY ENGINEERING")
        listDataBranch.add("PETROLEUM ENGINEERING")
        listDataBranch.add("BACHELOR OF COMPUTER APPLICATION")
        listDataBranch.add("BHM(BACHELOR OF HOSPITALITY MANAGEMENT)")
        listDataBranch.add("B.COM (HONS.)")
        listDataBranch.add("B.COM. (HONS.) INTERNATIONAL FINANCE AND ACCOUNTING WITH ACCA, UK")
        listDataBranch.add("B.COM. (HONS.) CORPORATE ACCOUNTING AND FINANCIAL ANALYSIS WITH CMA, US")
        listDataBranch.add("BACHELOR OF ARTS")
        listDataBranch.add("BA ECONOMICS (HONS.)")
        listDataBranch.add("BA PSYCHOLOGY (HONS.)")
        listDataBranch.add("BA POLITICAL SCIENCE (HONS.)")
        listDataBranch.add("BA SOCIOLOGY (HONS)")
        listDataBranch.add("BA (HONS.) ENGLISH")
        listDataBranch.add("COMPUTER SCIENCE")
        listDataBranch.add("FOOD AND NUTRITION")
        listDataBranch.add("MICROBIOLOGY")
        listDataBranch.add("BIOTECHNOLOGY")
        listDataBranch.add("BACHELOR OF BUSINESS ADMINISTRATION")
        listDataBranch.add("BBA INTERNATIONAL FINANCE AND ACCOUNTING WITH ACCA")
        listDataBranch.add("BBA CORPORATE ACCOUNTING AND FINANCIAL ANALYSIS WITH CMA")
        listDataBranch.add("MASTERS OF COMPUTER APPLICATION")
        listDataBranch.add("MHM(MASTERS IN HOSPITALITY MANAGEMENT)")
        listDataBranch.add("M.SC. BIOTECHNOLOGY")
        listDataBranch.add("M.SC. MICROBIOLOGY")
        listDataBranch.add("M.SC. FOOD TECHNOLOGY")
        listDataBranch.add("M.SC ENVIRONMENTAL SCIENCE")
        listDataBranch.add("MASTER OF BUSINESS ADMINISTRATION")
        listDataBranch.add("MASTER OF BUSINESS ADMINISTRATION IN ARTIFICIAL INTELLIGENCE")
        listDataBranch.add("MASTER OF BUSINESS ADMINISTRATION IEV")
        listDataBranch.add("M.TECH (COMPUTER SCIENCE & ENGG.)")
    }


    private fun listsem() {
        listPopupWindow = ListPopupWindow(context)
        listDataSem.add("I")
        listDataSem.add("II")
        listDataSem.add("III")
        listDataSem.add("IV")
        listDataSem.add("V")
        listDataSem.add("VI")
        listDataSem.add("VII")
        listDataSem.add("VIII")
    }


    fun listCollege(view: View) {
        with(listPopupWindow) {
            setAdapter(
                android.widget.ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    listDataColg
                )
            )
            anchorView = view
            setOnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> {
                        college.text = "Graphic Era (Deemed)"
                    }
                    1 -> {
                        college.text = "Graphic Era (Hill)"
                    }
                    else -> {
                        college.text = "Graphic Era (Bhimtal)"
                    }
                }
                this.dismiss()
            }
            show()

        }
    }

    fun listCourse(view: View) {
        with(listPopupWindow) {
            setAdapter(
                android.widget.ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    listDataCourse
                )
            )
            anchorView = view
            setOnItemClickListener { parent, view, position, id ->
                course.text = listDataCourse[position]
                this.dismiss()
            }
            show()

        }
    }

    fun listSemester(view: View) {
        with(listPopupWindow) {
            setAdapter(
                android.widget.ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    listDataSem
                )
            )
            anchorView = view
            setOnItemClickListener { parent, view, position, id ->
                sem.text = listDataSem[position]
                this.dismiss()
            }
            show()

        }
    }


    fun listBranch(view: View) {
        with(listPopupWindow) {
            setAdapter(
                android.widget.ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    listDataBranch
                )
            )
            anchorView = view
            setOnItemClickListener { parent, view, position, id ->
                branch.text = listDataBranch[position]
                this.dismiss()
            }
            show()
        }
    }


}
