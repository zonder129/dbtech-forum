package park.server.model;

import java.util.Objects;
import javax.validation.constraints.*;

/**
 * Ветка обсуждения на форуме. 
 */
public class Thread   {
    private String author;

    private String created;

    private String forum;

    private Integer id;

    private String message;

    private String slug;

    private String title;

    private Integer votes;

    public Thread() {

    }

    public Thread(String author, String format, String forum, Integer id, String message, String slug, String title, Integer votes) {
      this.author = author;
      this.created = format;
      this.forum = forum;
      this.id = id;
      this.message = message;
      this.slug = slug;
      this.title = title;
      this.votes = votes;
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

    public String getMessage() {
    return message;
    }

    public void setMessage(String message) {
    this.message = message;
    }

   /**
   * Человекопонятный URL. В данной структуре slug опционален и не может быть числом.
   * @return slug
  **/

    @Pattern(regexp="^(\\d|\\w|-|_)*(\\w|-|_)(\\d|\\w|-|_)*$")
    public String getSlug() {
    return slug;
    }

    public void setSlug(String slug) {
    this.slug = slug;
    }

   /**
   * Заголовок ветки обсуждения.
   * @return title
  **/

    public String getTitle() {
    return title;
    }

    public void setTitle(String title) {
    this.title = title;
    }

   /**
   * Кол-во голосов непосредственно за данное сообщение форума.
   * @return votes
  **/

    public Integer getVotes() {
    return votes;
    }

    public void setVotes(Integer votes) {
    this.votes = votes;
    }


    @Override
    public boolean equals(java.lang.Object o) {
    if (this == o) {
        return true;
    }
    if (o == null || getClass() != o.getClass()) {
        return false;
    }
    Thread thread = (Thread) o;
    return Objects.equals(this.author, thread.author) &&
        Objects.equals(this.created, thread.created) &&
        Objects.equals(this.forum, thread.forum) &&
        Objects.equals(this.id, thread.id) &&
        Objects.equals(this.message, thread.message) &&
        Objects.equals(this.slug, thread.slug) &&
        Objects.equals(this.title, thread.title) &&
        Objects.equals(this.votes, thread.votes);
    }

    @Override
    public int hashCode() {
    return Objects.hash(author, created, forum, id, message, slug, title, votes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Thread {\n");

        sb.append("    author: ").append(toIndentedString(author)).append("\n");
        sb.append("    created: ").append(toIndentedString(created)).append("\n");
        sb.append("    forum: ").append(toIndentedString(forum)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    slug: ").append(toIndentedString(slug)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    votes: ").append(toIndentedString(votes)).append("\n");
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

