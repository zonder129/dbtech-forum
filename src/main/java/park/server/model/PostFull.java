package park.server.model;

import java.util.Objects;

/**
 * Полная информация о сообщении, включая связанные объекты. 
 */
public class PostFull   {
    private User author;

    private Forum forum;

    private Post post;

    private Thread thread;

    public PostFull() {

    }

    public PostFull(User author, Forum forum, Post post, Thread thread) {
        this.author = author;
        this.forum = forum;
        this.post = post;
        this.thread = thread;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
    this.author = author;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
    this.forum = forum;
    }
    public Post getPost() {
    return post;
    }

    public void setPost(Post post) {
    this.post = post;
    }

    public Thread getThread() {
    return thread;
    }

    public void setThread(Thread thread) {
    this.thread = thread;
    }


    @Override
    public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostFull postFull = (PostFull) o;
    return Objects.equals(this.author, postFull.author) &&
        Objects.equals(this.forum, postFull.forum) &&
        Objects.equals(this.post, postFull.post) &&
        Objects.equals(this.thread, postFull.thread);
    }

    @Override
    public int hashCode() {
    return Objects.hash(author, forum, post, thread);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PostFull {\n");

        sb.append("    author: ").append(toIndentedString(author)).append("\n");
        sb.append("    forum: ").append(toIndentedString(forum)).append("\n");
        sb.append("    post: ").append(toIndentedString(post)).append("\n");
        sb.append("    thread: ").append(toIndentedString(thread)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
    * Convert the given object to string with each line indented by 4 spaces
    * (except the first line).
    */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

