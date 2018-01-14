package park.server.controllers;

import java.math.BigDecimal;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import park.server.model.Post;
import park.server.model.PostFull;
import park.server.model.PostUpdate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import park.server.services.PostService;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity postGetOne(@PathVariable("id") Integer id,
                                               @RequestParam(value = "related", required = false) List<String> related) {
        try {
            PostFull result = postService.getFullPostById(id, related);
            return ResponseEntity.status(200).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(404).body(new Error(ex.getMessage()));
        }
    }

    @PostMapping("/{id}/details")
    public ResponseEntity postUpdate(@PathVariable("id") Integer id,
                                           @Valid @RequestBody PostUpdate postUpdate) {
        try {
            Post updatedPost = postService.updatePost(id, postUpdate);
            return ResponseEntity.status(200).body(updatedPost);
        } catch (DataAccessException exception) {
            return ResponseEntity.status(404).body(new Error(exception.getMessage()));
        }
    }

}
