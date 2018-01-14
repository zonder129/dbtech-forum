package park.server.model;

import java.util.Objects;

/**
 * Сообщение внутри ветки обсуждения на форуме. 
 */
public class Post {
  private String author;

  private String created;

  private String forum;

  private Integer id;

  private Boolean isEdited;

  private String message;

  private Integer parentID;

  private Integer threadID;

  public Post() {

  }

  public Post(String author, String created, String forum, int id, boolean is_edited, String message, int parentID, int threadID) {
    this.author = author;
    this.created = created;
    this.forum = forum;
    this.id = id;
    isEdited = is_edited;
    this.message = message;
    this.parentID = parentID;
    this.threadID = threadID;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getForum() {
    return forum;
  }

  public void setForum(String forum) {
    this.forum = forum;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Boolean getIsEdited() {
    return isEdited;
  }

  public void setIsEdited(Boolean isEdited) {
    this.isEdited = isEdited;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getParent() {
    return parentID;
  }

  public void setParent(Integer parentID) {
    this.parentID = parentID;
  }

  public Integer getThread() {
    return threadID;
  }

  public void setThread(Integer threadID) {
    this.threadID = threadID;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Post post = (Post) o;
    return Objects.equals(this.author, post.author) &&
        Objects.equals(this.created, post.created) &&
        Objects.equals(this.forum, post.forum) &&
        Objects.equals(this.id, post.id) &&
        Objects.equals(this.isEdited, post.isEdited) &&
        Objects.equals(this.message, post.message) &&
        Objects.equals(this.parentID, post.parentID) &&
        Objects.equals(this.threadID, post.threadID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, created, forum, id, isEdited, message, parentID, threadID);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Post {\n");
    
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    forum: ").append(toIndentedString(forum)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    isEdited: ").append(toIndentedString(isEdited)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    parent: ").append(toIndentedString(parentID)).append("\n");
    sb.append("    thread: ").append(toIndentedString(threadID)).append("\n");
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

