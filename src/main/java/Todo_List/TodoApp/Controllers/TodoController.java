package Todo_List.TodoApp.Controllers;


import Todo_List.TodoApp.GlobalConstants;
import Todo_List.TodoApp.Models.*;
import Todo_List.TodoApp.Repositories.DayRespository;
import Todo_List.TodoApp.Repositories.TodoRepository;
import Todo_List.TodoApp.Repositories.WeekRepository;
import Todo_List.TodoApp.Tables.DaysTable;
import Todo_List.TodoApp.Tables.TodosTable;
import Todo_List.TodoApp.Tables.WeekTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    WeekRepository weekRepository;
    @Autowired
    DayRespository dayRespository;

    @PostMapping("/todo/get")
    public ResponseEntity<ApiResponseDTO> getTodos(@RequestBody getTodoInputModel fetchTodoDTO) {
        List<TodosTable> todoList = new ArrayList<>();
       if(fetchTodoDTO.getWeekEnd() != null && fetchTodoDTO.getWeekStart() != null) {
           if(weekRepository.existsByWeekStart(fetchTodoDTO.getWeekStart())){

               WeekTable currentWeek = weekRepository.findByWeekStart(fetchTodoDTO.getWeekStart()).get();
               todoList.addAll(currentWeek.getTodos());

           }


       }else if(fetchTodoDTO.getDate() !=null) {
           if(dayRespository.existsByDate(fetchTodoDTO.getDate())){

               DaysTable currentDay = dayRespository.findByDate(fetchTodoDTO.getDate()).get();
               todoList.addAll(currentDay.getTodos());

           }

       }

       todoList = todoList.stream().filter(t->t.getTodoType()!=TodoType.PART_OF_GROUP).toList();


       return new ResponseEntity<>(new ApiResponseDTO("Todo fetched successfully",GlobalConstants.SUCCESS,todoList), HttpStatus.OK);

    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<ApiResponseDTO> updateTodoTask (@PathVariable Long id,@RequestBody updateTodoInputModel  task) {
        if(!todoRepository.existsById(id)) {
            ApiResponseDTO  apiResponseDTO = new ApiResponseDTO(String.format("Todo not found with id %d",id), GlobalConstants.FAILED,null);


            return new ResponseEntity<>(apiResponseDTO,HttpStatus.NOT_FOUND);
        }
        TodosTable todo = todoRepository.findById(id).get();
        if(task.getTask() !=null)
            todo.setTask(task.getTask());
        if(task.getCompleted() !=null)
            todo.setCompleted(task.getCompleted());
        todo = todoRepository.save(todo);
        ApiResponseDTO  apiResponseDTO = new ApiResponseDTO("Todo updated", GlobalConstants.SUCCESS,todo);

        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);

    }

    @PostMapping("/todo")
    public ResponseEntity<ApiResponseDTO> createTodo(@RequestBody createTodoInputModel todos) throws Exception {
        TodosTable finalTodo;
        if(todos.getWeekStart() != null && todos.getWeekEnd() != null){
            //PROCESSING FOR WEEKLY GOALS
            System.out.println(todos.getWeekStart());
            System.out.println(todos.getWeekEnd());
            System.out.println(todos.getTask());

            WeekTable currentWeek = weekRepository.findByWeekStart(todos.getWeekStart()).orElseGet(()->{

                WeekTable newWeek = new WeekTable();
                newWeek.setWeekStart(todos.getWeekStart());
                newWeek.setWeekEnd(todos.getWeekEnd());
                weekRepository.save(newWeek);
                return newWeek;
            }) ;

             finalTodo = createTodos(todos,currentWeek,null,false);
            finalTodo = todoRepository.save(finalTodo);
        }
        else if(todos.getDay() != null && todos.getDate() != null){
            //PROCESSING FOR DAILY GOALS
            System.out.println(todos.getDate());
            System.out.println(todos.getDay());
            DaysTable currentDay = dayRespository.findByDate(todos.getDate()).orElseGet(()->{
                DaysTable newDay = new DaysTable();
                newDay.setDay(todos.getDay());
                newDay.setDate(todos.getDate());
                dayRespository.save(newDay);
                return newDay;
            });

            finalTodo = createTodos(todos,null,currentDay,false);
            finalTodo = todoRepository.save(finalTodo);



        }else{
            ApiResponseDTO  apiResponseDTO = new ApiResponseDTO("Invalid Todo Details", GlobalConstants.FAILED,null);

            return new ResponseEntity<>(apiResponseDTO,HttpStatus.BAD_REQUEST);
        }


        ApiResponseDTO  apiResponseDTO = new ApiResponseDTO("Todo created", GlobalConstants.SUCCESS,finalTodo);


        return new ResponseEntity<>(apiResponseDTO,HttpStatus.CREATED);
    }


    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ApiResponseDTO> deleteTodoTask (@PathVariable Long id){
        if(!todoRepository.existsById(id)) {
            return new ResponseEntity<>(new ApiResponseDTO("Todo not found",GlobalConstants.FAILED,null),HttpStatus.NOT_FOUND);
        }
        todoRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponseDTO("Todo deleted",GlobalConstants.SUCCESS,null),HttpStatus.OK);
    }

    private TodosTable createTodos(createTodoInputModel todos, WeekTable currentWeek, DaysTable currentDay, Boolean completed) throws Exception {

        TodosTable newTodos = new TodosTable();
        newTodos.setTask(todos.getTask());
        newTodos.setWeek(currentWeek);
        newTodos.setCompleted(completed);
        newTodos.setDay(currentDay);
        newTodos.setTodoType(todos.getTodoType());
        if(todos.isParentCheck() && todos.getTodoType().equals(TodoType.PART_OF_GROUP)){
            throw new RuntimeException("Parent todo can't be of type part of group");
        }
        if(todos.getTodoType().equals(TodoType.PART_OF_GROUP) && todos.getParentId()==null){
            throw new RuntimeException("Parent id missing");

        }
        if(todos.isParentCheck()){
            newTodos.setParentCheck(true);
            newTodos.setChildren(new ArrayList<TodosTable>());


        }else{
            newTodos.setParentCheck(false);;
            newTodos.setChildren(null);
        }

        if(todos.getTodoType().equals(TodoType.PART_OF_GROUP)){
            TodosTable parent = todoRepository.findById(todos.getParentId()).orElse(null);
            if(parent==null){
                throw new RuntimeException("Parent not found");
            }
            if(!parent.isParentCheck()){
                throw new RuntimeException("Invalid parent");

            }
            newTodos.setParent(parent);
        }
        return newTodos;
    }

//    @PostMapping("/todo/group")
//    private ResponseEntity<ApiResponseDTO> createTodoGroup(@RequestBody createTodoGroupDTO group){
//        if(group.getGroupName() != null){
//            return new ResponseEntity<>(new ApiResponseDTO("Group Name Needed",GlobalConstants.FAILED,null),HttpStatus.BAD_REQUEST);
//        }
//        TodoGroupTable todoGroup = new TodoGroupTable();
//        todoGroup.setGroupName(group.getGroupName());
//        todoGroup.setTodos(group.getTodos());
//        todoGroup.setDay(group.getDay());
//
//
//    }

}
