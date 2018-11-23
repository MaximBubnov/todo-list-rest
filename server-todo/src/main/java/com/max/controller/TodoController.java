package com.max.controller;

import com.max.domain.Todo;
import com.max.repository.TodoRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/main")
public class TodoController {

    private final TodoRepo todoRepo;

    @Autowired
    public TodoController(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    @GetMapping("/list")
    public List<Todo> getAll() {
        return todoRepo.findAll();
    }

    @GetMapping("/todo/{id}")
    public Todo getTodo(@PathVariable("id") Todo todo) {
        return todo;
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        todo.setActive(false);
        return todoRepo.save(todo);
    }

    @PutMapping("/update/{id}")
    public Todo updateTodo(@PathVariable("id") Todo todoFromDb,
                           @RequestBody Todo todo) {

        BeanUtils.copyProperties(todo, todoFromDb, "id");

        return todoRepo.save(todoFromDb);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTodo(@PathVariable("id") Todo todo) {
        todoRepo.delete(todo);
    }


}
