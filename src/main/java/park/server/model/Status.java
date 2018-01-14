package park.server.model;

import java.util.Objects;

/**
 * Status
 */

public class Status {
  private Integer forum;

  private Integer post;

  private Integer thread;

  private Integer user;

  public Status() {

  }

  public Status(Integer forum, Integer post, Integer thread, Integer user) {
    this.forum = forum;
    this.post = post;
    this.thread = thread;
    this.user = user;
  }

  public Integer getForum() {
    return forum;
  }

  public void setForum(Integer forum) {
    this.forum = forum;
  }

  public Integer getPost() {
    return post;
  }

  public void setPost(Integer post) {
    this.post = post;
  }

  public Integer getThread() {
    return thread;
  }

  public void setThread(Integer thread) {
    this.thread = thread;
  }

  public Integer getUser() {
    return user;
  }

  public void setUser(Integer user) {
    this.user = user;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Status status = (Status) o;
    return Objects.equals(this.forum, status.forum) &&
        Objects.equals(this.post, status.post) &&
        Objects.equals(this.thread, status.thread) &&
        Objects.equals(this.user, status.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(forum, post, thread, user);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Status {\n");
    
    sb.append("    forum: ").append(toIndentedString(forum)).append("\n");
    sb.append("    post: ").append(toIndentedString(post)).append("\n");
    sb.append("    thread: ").append(toIndentedString(thread)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
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

