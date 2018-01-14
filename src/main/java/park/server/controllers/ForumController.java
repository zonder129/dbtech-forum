package park.server.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import park.server.model.Forum;
import park.server.model.Thread;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import park.server.model.User;
import park.server.services.ForumService;
import park.server.services.ThreadService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/forum")
public class ForumController{

    private ForumService forumService;
    private ThreadService threadService;

    public ForumController(ForumService forumService, ThreadService threadService) {
        this.forumService = forumService;
        this.threadService = threadService;
    }


    @PostMapping("/create")
    public ResponseEntity forumCreate(@Valid @RequestBody Forum forum) {
        try {
            final Forum newForum = forumService.createForum(forum);
            return ResponseEntity.status(201).body(newForum);
        } catch (DuplicateKeyException exception) {
            return ResponseEntity.status(409).body(forumService.getForumBySlug(forum.getSlug()));
        } catch (DataAccessException exception) {
            return ResponseEntity.status(404).body(new Error(exception.getMessage()));
        }
    }

    @GetMapping("/{slug}/details")
    public ResponseEntity forumGetOne(@PathVariable("slug") String slug) {
        try{
            final Forum requestedForum = forumService.getForumBySlug(slug);
            return ResponseEntity.status(HttpStatus.OK).body(requestedForum);
        } catch (DataAccessException exception) {
            return ResponseEntity.status(404).body(new Error(exception.getMessage()));
        }
    }

    @GetMapping("/{slug}/threads")
    public ResponseEntity forumGetThreads(@PathVariable("slug") String slug,
                                                        @RequestParam(value = "limit", required = false, defaultValue="100") Integer limit,
                                                        @RequestParam(value = "since", required = false) String since,
                                                        @RequestParam(value = "desc", required = false) Boolean desc) {
        try{
            Forum forum = forumService.getForumBySlug(slug);
            final List<Thread> threads = forumService.getForumThreads(slug, limit, since, desc);
            return ResponseEntity.status(200).body(threads);
        } catch (DataAccessException exception) {
            return ResponseEntity.status(404).body(new Error(exception.getMessage()));
        }
    }

    @GetMapping("/{slug}/users")
    public ResponseEntity forumGetUsers(@PathVariable("slug") String slug,
                                                    @RequestParam(value = "limit", required = false, defaultValue="100") Integer limit,
                                                    @RequestParam(value = "since", required = false) String since,
                                                    @RequestParam(value = "desc", required = false) Boolean desc) {
        try {
            final List<User> forumUsers = forumService.getForumUsers(slug, limit, since, desc);
            return ResponseEntity.status(200).body(forumUsers);
        } catch (DataAccessException e) {
            return ResponseEntity.status(404).body(new Error(e.getMessage()));
        }
    }
    @PostMapping("/{slug}/create")
    public ResponseEntity threadCreate(@PathVariable("slug") String slug,
                                               @Valid @RequestBody Thread thread) {
        try {
            final Thread newThread = threadService.createThread(slug, thread);
            return ResponseEntity.status(201).body(newThread);
        } catch (DuplicateKeyException exception) {
            return ResponseEntity.status(409).body(threadService.getThreadBySlugOrId(thread.getSlug()));
        } catch (DataAccessException exception) {
            return ResponseEntity.status(404).body(new Error(exception.getMessage()));
        }
    }

}
