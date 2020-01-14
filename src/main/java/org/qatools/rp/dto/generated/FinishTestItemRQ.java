/*
 * Report Portal
 * Report Portal API documentation
 *
 * OpenAPI spec version: 4.3.11
 * Contact: Support EPMC-TST Report Portal <SupportEPMC-TSTReportPortal@epam.com>
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.qatools.rp.dto.generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * FinishTestItemRQ
 */

public class FinishTestItemRQ {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("end_time")
  private Date endTime = null;

  @JsonProperty("issue")
  private Issue issue = null;

  @JsonProperty("retry")
  private Boolean retry = null;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    PASSED("PASSED"),
    
    FAILED("FAILED"),
    
    STOPPED("STOPPED"),
    
    SKIPPED("SKIPPED"),
    
    RESETED("RESETED"),
    
    CANCELLED("CANCELLED");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("status")
  private StatusEnum status = null;

  @JsonProperty("tags")
  private List<String> tags = null;

  public FinishTestItemRQ description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public FinishTestItemRQ endTime(Date endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Get endTime
   * @return endTime
  **/
  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public FinishTestItemRQ issue(Issue issue) {
    this.issue = issue;
    return this;
  }

   /**
   * Get issue
   * @return issue
  **/
  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }

  public FinishTestItemRQ retry(Boolean retry) {
    this.retry = retry;
    return this;
  }

   /**
   * Get retry
   * @return retry
  **/
  public Boolean isRetry() {
    return retry;
  }

  public void setRetry(Boolean retry) {
    this.retry = retry;
  }

  public FinishTestItemRQ status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public FinishTestItemRQ tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public FinishTestItemRQ addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Get tags
   * @return tags
  **/
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FinishTestItemRQ finishTestItemRQ = (FinishTestItemRQ) o;
    return Objects.equals(this.description, finishTestItemRQ.description) &&
        Objects.equals(this.endTime, finishTestItemRQ.endTime) &&
        Objects.equals(this.issue, finishTestItemRQ.issue) &&
        Objects.equals(this.retry, finishTestItemRQ.retry) &&
        Objects.equals(this.status, finishTestItemRQ.status) &&
        Objects.equals(this.tags, finishTestItemRQ.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, endTime, issue, retry, status, tags);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FinishTestItemRQ {\n");

    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    issue: ").append(toIndentedString(issue)).append("\n");
    sb.append("    retry: ").append(toIndentedString(retry)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
