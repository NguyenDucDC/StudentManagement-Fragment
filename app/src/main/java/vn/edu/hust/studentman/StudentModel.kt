package vn.edu.hust.studentman

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentModel(
    var studentName: String,
    var studentId: String
) : Parcelable