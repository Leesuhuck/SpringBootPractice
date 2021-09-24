package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.TodoEntity;
import org.example.model.TodoRequest;
import org.example.model.TodoResponse;
import org.example.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping("/")
public class TodoController {

    private final TodoService service;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {

        System.out.println("CREATE");

        // 리퀘스트 타이틀이 없을시
        if (ObjectUtils.isEmpty(request.getTitle())) {

            // 잘못된 요청이라고 반환
            return ResponseEntity.badRequest().build();
        }

        // 리퀘스트 order값이 없을시
        if (ObjectUtils.isEmpty(request.getOrder())) {

            // 디폴트값 입력
            request.setOrder(0L);
        }

        // 리퀘스트 Completed값이 없을시
        if (ObjectUtils.isEmpty(request.getCompleted())) {

            //  Completed 논리값 false로 지정
            request.setCompleted(false);
        }

        // ResponseEntity 서비스에 request(리퀘스트바디) 추가 하고 그것을 TodoEntity result 초기화
        TodoEntity result = this.service.add(request);

        // 받은 result를 TodoResponse에 맵핑해서 올려줍니다.
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        System.out.println("READ ONE");
        TodoEntity result = this.service.searchById(id);

        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        System.out.println("READ ALL");
        return null;
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update() {
        System.out.println("UPDATE");

        return null;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne() {
        System.out.println("DELETE");

        return null;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        System.out.println("DELETE ALL");

        return null;
    }
}
