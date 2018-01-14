package park.server.controllers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.*;
import park.server.model.Post;
import park.server.model.Thread;
import park.server.model.ThreadUpdate;
import park.server.model.Vote;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import park.server.services.PostService;
import park.server.services.ThreadService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/thread")
public class ThreadController {

    private ThreadService threadService;

    private PostService postService;

    public ThreadController(ThreadService threadService, PostService postService) {
        this.threadService = threadService;
        this.postService = postService;
    }


    @PostMapping("/{slug_or_id}/create")
    public ResponseEntity postsCreate(@PathVariable("slug_or_id") String slugOrId,
                                             @Valid @RequestBody List<Post> posts) {
        try {
            final List<Post> newPosts = postService.createPosts(slugOrId, posts);
            return ResponseEntity.status(201).body(newPosts);
        } catch (IncorrectResultSizeDataAccessException exception) {
            return ResponseEntity.status(404).body(new Error(exception.getMessage()));
        } catch (SQLException | NoSuchElementException exception) {
            return ResponseEntity.status(409).body(new Error(exception.getMessage()));
        }
    }

    @GetMapping("/{slug_or_id}/details")
    public ResponseEntity threadGetOne(@PathVariable("slug_or_id") String slugOrId) {
        try {
            Thread result = threadService.getThreadBySlugOrId(slugOrId);
            return ResponseEntity.status(200).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(404).body(new Error(ex.getMessage()));
        }
    }

    @GetMapping("/{slug_or_id}/posts")
    public ResponseEntity threadGetPosts(@PathVariable("slug_or_id") String slugOrId,
                                                @RequestParam(value = "limit", required = false, defaultValue="100") Integer limit,
                                                @RequestParam(value = "since", required = false) Integer since,
                                                @RequestParam(value = "sort", required = false, defaultValue="flat") String sort,
                                                @RequestParam(value = "desc", required = false) Boolean desc) {
        try {
            List<Post> result = threadService.getThreadPosts(slugOrId, limit, since, sort, desc);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(ex.getMessage()));
        }
    }

    @PostMapping("/{slug_or_id}/details")
    public ResponseEntity threadUpdate(@PathVariable("slug_or_id") String slugOrId,
                                               @Valid @RequestBody ThreadUpdate updatedThread) {
        try {
            Thread result = threadService.updateThread(slugOrId, updatedThread);
            return ResponseEntity.status(200).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(404).body(new Error(ex.getMessage()));
        }
    }

    @PostMapping("/{slug_or_id}/vote")
    public ResponseEntity threadVote(@PathVariable("slug_or_id") String slugOrId,
                                             @Valid @RequestBody Vote vote) {
        try {
            Thread result = threadService.setVote(slugOrId, vote);
            return ResponseEntity.status(200).body(result);
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.status(409).body(new Error(ex.getMessage()));
        } catch (DataAccessException ex) {
            return ResponseEntity.status(404).body(new Error(ex.getMessage()));
        }
    }

}
