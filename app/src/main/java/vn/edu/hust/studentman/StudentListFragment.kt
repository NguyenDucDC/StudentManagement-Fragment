package vn.edu.hust.studentman

import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import vn.edu.hust.studentman.databinding.FragmentStudentListBinding
import vn.edu.hust.studentman.StudentListFragmentDirections

@Suppress("DEPRECATION")
class StudentListFragment : Fragment() {
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!
    private val studentViewModel: StudentViewModel by activityViewModels()

    private val students = mutableListOf<StudentModel>()
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentAdapter = StudentAdapter(students, requireContext())
        binding.listView.adapter = studentAdapter

        setHasOptionsMenu(true)
        registerForContextMenu(binding.listView)
        studentViewModel.students.observe(viewLifecycleOwner) { newStudents: List<StudentModel> ->
            students.clear()
            students.addAll(newStudents)
            studentAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu, menu) // File context_menu.xml
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedStudent = students[info.position]

        return when (item.itemId) {
            R.id.menuEdit -> {
                val action = StudentListFragmentDirections.actionStudentListFragmentToEditStudentFragment(selectedStudent)
                findNavController().navigate(action)
                true
            }
            R.id.menuRemove -> {
                val studentToRemove = students[info.position]
                students.removeAt(info.position)
                studentAdapter.notifyDataSetChanged()

                val snackbar = Snackbar.make(
                    binding.root,
                    "Sinh viên đã bị xóa",
                    Snackbar.LENGTH_INDEFINITE
                )

                // Biến để cập nhật nội dung Snackbar
                val snackbarText = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

                // Sử dụng CountDownTimer để đếm ngược 10 giây
                val timer = object : CountDownTimer(10000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val secondsLeft = millisUntilFinished / 1000
                        snackbarText.text = "Sinh viên đã bị xóa. Khôi phục trong $secondsLeft giây"
                    }

                    override fun onFinish() {
                        if (students.contains(studentToRemove).not()) {


                        }
                        snackbar.dismiss()

                    }
                }

                // Thêm nút Undo vào Snackbar
                snackbar.setAction("Undo") {
                    // Nếu người dùng nhấn Undo, phục hồi sinh viên đã xóa
                    students.add(info.position, studentToRemove)
                    studentAdapter.notifyDataSetChanged()
                    timer.cancel() // Hủy bỏ đếm ngược nếu người dùng nhấn Undo
                }

                // Hiển thị Snackbar và bắt đầu đếm ngược
                snackbar.show()
                timer.start() // Bắt đầu đếm ngược

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuAddNew -> {
                findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addStudent(student: StudentModel) {
        students.add(student) // Thêm vào danh sách dữ liệu
        studentAdapter.notifyDataSetChanged() // Thông báo adapter cập nhật
    }
}