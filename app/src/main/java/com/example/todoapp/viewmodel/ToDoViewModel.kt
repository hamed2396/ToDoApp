package com.example.todoapp.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoTask
import com.example.todoapp.data.repository.DataStoreRepository
import com.example.todoapp.data.repository.ToDoRepository
import com.example.todoapp.utils.Action
import com.example.todoapp.utils.RequestState
import com.example.todoapp.utils.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val id = mutableIntStateOf(0)
    val title = mutableStateOf("")
    val priority = mutableStateOf(Priority.LOW)
    val description = mutableStateOf("")

    val searchAppBarState = mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState = mutableStateOf("")

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTask get() = _allTasks as StateFlow<RequestState<List<ToDoTask>>>
    val action = mutableStateOf(Action.NO_ACTION)


    fun getAllTask() = viewModelScope.launch {
        _allTasks.value = RequestState.Loading
        try {
            repository.getAllTask.collect {
                _allTasks.value = RequestState.Success(it)
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }

    }

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask get() = _selectedTask as StateFlow<ToDoTask?>
    fun getSelectedTask(taskId: Int) = viewModelScope.launch {
        repository.getSelectedTask(taskId).collect { task ->
            _selectedTask.value = task
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.intValue = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.intValue = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }

    }

    fun validateFields() = title.value.isNotEmpty() and description.value.isNotEmpty()

    private fun addTask() = viewModelScope.launch(Dispatchers.IO) {
        val todoTask = ToDoTask(
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        repository.addTask(todoTask)
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    private fun updateTask() = viewModelScope.launch(Dispatchers.IO) {
        val todoTask = ToDoTask(
            id = id.intValue,
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        repository.updateTask(todoTask)
    }

    private fun deleteTask() = viewModelScope.launch(Dispatchers.IO) {
        val todoTask = ToDoTask(
            id = id.intValue,
            title = title.value,
            description = description.value,
            priority = priority.value
        )
        repository.deleteTask(todoTask)
    }

    private val _searchTask = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchTask get() = _searchTask as StateFlow<RequestState<List<ToDoTask>>>
    fun searchTask(search: String) {

        _searchTask.value = RequestState.Loading
        viewModelScope.launch {
            try {
                repository.searchDataBase(query = "%$search%").collect {
                    _searchTask.value = RequestState.Success(it)
                }
            } catch (e: Exception) {
                _searchTask.value = RequestState.Error(e)
            }

        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    private fun deleteAllTask() = viewModelScope.launch {
        repository.deleteAllTask()
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch {
            dataStoreRepository.persistSortState(priority)
        }
    }

    val lowPriorityTask: StateFlow<List<ToDoTask>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(),
            emptyList()
        )
    val highPriorityTask: StateFlow<List<ToDoTask>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(),
            emptyList()
        )
    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState get() = _sortState as StateFlow<RequestState<Priority>>
    fun readSortState() = viewModelScope.launch {
        _sortState.value = RequestState.Loading
        try {
            dataStoreRepository.readSortSate.map {
                Priority.valueOf(it.toString())
            }.collect {
                _sortState.value = RequestState.Success(it)
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }

    }

    fun handleDataBaseActions(action: Action) {
        when (action) {
            Action.ADD -> addTask()
            Action.UPDATE -> updateTask()
            Action.DELETE -> deleteTask()
            Action.DELETE_ALL -> deleteAllTask()
            Action.UNDO -> addTask()
            Action.NO_ACTION -> {}
        }
        this.action.value = Action.NO_ACTION
    }
}