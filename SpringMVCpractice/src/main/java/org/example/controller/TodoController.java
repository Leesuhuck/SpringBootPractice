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
import java.util.stream.Collectors;

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

    @GetMapping("{id}") // 맵핑 id 값 경로를 사용하기위해 @PathVariable을 사용
    public ResponseEntity<TodoResponse> readOne(@PathVariable Long id) {
        System.out.println("READ ONE");

        TodoEntity result = this.service.searchById(id);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        System.out.println("READ ALL");

        List<TodoEntity> List           = this.service.searchAll();
        List<TodoResponse> responses    = List.stream().map(TodoResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id, @RequestBody TodoRequest request) {
        System.out.println("UPDATE");

        TodoEntity result = this.service.updateById(id, request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        System.out.println("DELETE");

        this.service.deleteBuId(id);
        return ResponseEntity.ok().build(); // 딱히 return값이 없기에 현 response엔티티 빌드함.
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        System.out.println("DELETE ALL");

        this.service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
