package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import vn.edu.hust.studentman.databinding.FragmentEditStudentBinding

class EditStudentFragment : Fragment() {
    private var _binding: FragmentEditStudentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val student = arguments?.getParcelable<StudentModel>("student")

        binding.studentNameEditText.setText(student?.studentName)
        binding.studentIdEditText.setText(student?.studentId)

        binding.saveButton.setOnClickListener {
            student?.studentName = binding.studentNameEditText.text.toString()
            student?.studentId = binding.studentIdEditText.text.toString()

            // Trở lại danh sách sinh viên
            findNavController().navigateUp()
        }
        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
