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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ActivityResource
 */

public class ActivityResource {
  @JsonProperty("actionType")
  private String actionType = null;

  @JsonProperty("activityId")
  private String activityId = null;

  @JsonProperty("history")
  private List<FieldValues> history = null;

  @JsonProperty("lastModifiedDate")
  private Date lastModifiedDate = null;

  @JsonProperty("loggedObjectRef")
  private String loggedObjectRef = null;

  @JsonProperty("objectName")
  private String objectName = null;

  @JsonProperty("objectType")
  private String objectType = null;

  @JsonProperty("projectRef")
  private String projectRef = null;

  @JsonProperty("userRef")
  private String userRef = null;

  public ActivityResource actionType(String actionType) {
    this.actionType = actionType;
    return this;
  }

   /**
   * Get actionType
   * @return actionType
  **/
  @ApiModelProperty(required = true, value = "")
  public String getActionType() {
    return actionType;
  }

  public void setActionType(String actionType) {
    this.actionType = actionType;
  }

  public ActivityResource activityId(String activityId) {
    this.activityId = activityId;
    return this;
  }

   /**
   * Get activityId
   * @return activityId
  **/
  @ApiModelProperty(required = true, value = "")
  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public ActivityResource history(List<FieldValues> history) {
    this.history = history;
    return this;
  }

  public ActivityResource addHistoryItem(FieldValues historyItem) {
    if (this.history == null) {
      this.history = new ArrayList<>();
    }
    this.history.add(historyItem);
    return this;
  }

   /**
   * Get history
   * @return history
  **/
  @ApiModelProperty(value = "")
  public List<FieldValues> getHistory() {
    return history;
  }

  public void setHistory(List<FieldValues> history) {
    this.history = history;
  }

  public ActivityResource lastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
    return this;
  }

   /**
   * Get lastModifiedDate
   * @return lastModifiedDate
  **/
  @ApiModelProperty(required = true, value = "")
  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public ActivityResource loggedObjectRef(String loggedObjectRef) {
    this.loggedObjectRef = loggedObjectRef;
    return this;
  }

   /**
   * Get loggedObjectRef
   * @return loggedObjectRef
  **/
  @ApiModelProperty(required = true, value = "")
  public String getLoggedObjectRef() {
    return loggedObjectRef;
  }

  public void setLoggedObjectRef(String loggedObjectRef) {
    this.loggedObjectRef = loggedObjectRef;
  }

  public ActivityResource objectName(String objectName) {
    this.objectName = objectName;
    return this;
  }

   /**
   * Get objectName
   * @return objectName
  **/
  @ApiModelProperty(value = "")
  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public ActivityResource objectType(String objectType) {
    this.objectType = objectType;
    return this;
  }

   /**
   * Get objectType
   * @return objectType
  **/
  @ApiModelProperty(required = true, value = "")
  public String getObjectType() {
    return objectType;
  }

  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }

  public ActivityResource projectRef(String projectRef) {
    this.projectRef = projectRef;
    return this;
  }

   /**
   * Get projectRef
   * @return projectRef
  **/
  @ApiModelProperty(required = true, value = "")
  public String getProjectRef() {
    return projectRef;
  }

  public void setProjectRef(String projectRef) {
    this.projectRef = projectRef;
  }

  public ActivityResource userRef(String userRef) {
    this.userRef = userRef;
    return this;
  }

   /**
   * Get userRef
   * @return userRef
  **/
  @ApiModelProperty(required = true, value = "")
  public String getUserRef() {
    return userRef;
  }

  public void setUserRef(String userRef) {
    this.userRef = userRef;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ActivityResource activityResource = (ActivityResource) o;
    return Objects.equals(this.actionType, activityResource.actionType) &&
        Objects.equals(this.activityId, activityResource.activityId) &&
        Objects.equals(this.history, activityResource.history) &&
        Objects.equals(this.lastModifiedDate, activityResource.lastModifiedDate) &&
        Objects.equals(this.loggedObjectRef, activityResource.loggedObjectRef) &&
        Objects.equals(this.objectName, activityResource.objectName) &&
        Objects.equals(this.objectType, activityResource.objectType) &&
        Objects.equals(this.projectRef, activityResource.projectRef) &&
        Objects.equals(this.userRef, activityResource.userRef);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionType, activityId, history, lastModifiedDate, loggedObjectRef, objectName, objectType, projectRef, userRef);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActivityResource {\n");

    sb.append("    actionType: ").append(toIndentedString(actionType)).append("\n");
    sb.append("    activityId: ").append(toIndentedString(activityId)).append("\n");
    sb.append("    history: ").append(toIndentedString(history)).append("\n");
    sb.append("    lastModifiedDate: ").append(toIndentedString(lastModifiedDate)).append("\n");
    sb.append("    loggedObjectRef: ").append(toIndentedString(loggedObjectRef)).append("\n");
    sb.append("    objectName: ").append(toIndentedString(objectName)).append("\n");
    sb.append("    objectType: ").append(toIndentedString(objectType)).append("\n");
    sb.append("    projectRef: ").append(toIndentedString(projectRef)).append("\n");
    sb.append("    userRef: ").append(toIndentedString(userRef)).append("\n");
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

