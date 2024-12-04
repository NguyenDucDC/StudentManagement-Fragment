package vn.edu.hust.studentman

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import vn.edu.hust.studentman.databinding.FragmentAddStudentBinding


class AddStudentFragment : Fragment() {
    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!
    private val studentViewModel: StudentViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val studentId = binding.etStudentId.text.toString()

            if (name.isBlank() || studentId.isBlank()) {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            } else {
                // Thêm sinh viên mới vào ViewModel
                studentViewModel.addStudent(StudentModel(name, studentId))
                findNavController().navigateUp()
                Toast.makeText(requireContext(), "Đã thêm sinh viên $name", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    fun saveStudent(student: StudentModel) {
        studentViewModel.addStudent(student)
        Snackbar.make(binding.root, "Student added!", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

