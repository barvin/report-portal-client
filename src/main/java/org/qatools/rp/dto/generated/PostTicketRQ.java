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

import java.util.*;

/**
 * PostTicketRQ
 */

public class PostTicketRQ {
  @JsonProperty("backLinks")
  private Map<String, String> backLinks = new HashMap<>();

  @JsonProperty("domain")
  private String domain = null;

  @JsonProperty("fields")
  private List<PostFormField> fields = new ArrayList<>();

  @JsonProperty("include_comments")
  private Boolean includeComments = null;

  @JsonProperty("include_data")
  private Boolean includeData = null;

  @JsonProperty("include_logs")
  private Boolean includeLogs = null;

  @JsonProperty("item")
  private String item = null;

  @JsonProperty("log_quantity")
  private Integer logQuantity = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("token")
  private String token = null;

  @JsonProperty("username")
  private String username = null;

  public PostTicketRQ backLinks(Map<String, String> backLinks) {
    this.backLinks = backLinks;
    return this;
  }

  public PostTicketRQ putBackLinksItem(String key, String backLinksItem) {
    this.backLinks.put(key, backLinksItem);
    return this;
  }

   /**
   * Get backLinks
   * @return backLinks
  **/
  @ApiModelProperty(required = true, value = "")
  public Map<String, String> getBackLinks() {
    return backLinks;
  }

  public void setBackLinks(Map<String, String> backLinks) {
    this.backLinks = backLinks;
  }

  public PostTicketRQ domain(String domain) {
    this.domain = domain;
    return this;
  }

   /**
   * Get domain
   * @return domain
  **/
  @ApiModelProperty(value = "")
  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public PostTicketRQ fields(List<PostFormField> fields) {
    this.fields = fields;
    return this;
  }

  public PostTicketRQ addFieldsItem(PostFormField fieldsItem) {
    this.fields.add(fieldsItem);
    return this;
  }

   /**
   * Get fields
   * @return fields
  **/
  @ApiModelProperty(required = true, value = "")
  public List<PostFormField> getFields() {
    return fields;
  }

  public void setFields(List<PostFormField> fields) {
    this.fields = fields;
  }

  public PostTicketRQ includeComments(Boolean includeComments) {
    this.includeComments = includeComments;
    return this;
  }

   /**
   * Get includeComments
   * @return includeComments
  **/
  @ApiModelProperty(value = "")
  public Boolean isIncludeComments() {
    return includeComments;
  }

  public void setIncludeComments(Boolean includeComments) {
    this.includeComments = includeComments;
  }

  public PostTicketRQ includeData(Boolean includeData) {
    this.includeData = includeData;
    return this;
  }

   /**
   * Get includeData
   * @return includeData
  **/
  @ApiModelProperty(value = "")
  public Boolean isIncludeData() {
    return includeData;
  }

  public void setIncludeData(Boolean includeData) {
    this.includeData = includeData;
  }

  public PostTicketRQ includeLogs(Boolean includeLogs) {
    this.includeLogs = includeLogs;
    return this;
  }

   /**
   * Get includeLogs
   * @return includeLogs
  **/
  @ApiModelProperty(value = "")
  public Boolean isIncludeLogs() {
    return includeLogs;
  }

  public void setIncludeLogs(Boolean includeLogs) {
    this.includeLogs = includeLogs;
  }

  public PostTicketRQ item(String item) {
    this.item = item;
    return this;
  }

   /**
   * Get item
   * @return item
  **/
  @ApiModelProperty(required = true, value = "")
  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public PostTicketRQ logQuantity(Integer logQuantity) {
    this.logQuantity = logQuantity;
    return this;
  }

   /**
   * Get logQuantity
   * @return logQuantity
  **/
  @ApiModelProperty(value = "")
  public Integer getLogQuantity() {
    return logQuantity;
  }

  public void setLogQuantity(Integer logQuantity) {
    this.logQuantity = logQuantity;
  }

  public PostTicketRQ password(String password) {
    this.password = password;
    return this;
  }

   /**
   * Get password
   * @return password
  **/
  @ApiModelProperty(value = "")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public PostTicketRQ token(String token) {
    this.token = token;
    return this;
  }

   /**
   * Get token
   * @return token
  **/
  @ApiModelProperty(value = "")
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public PostTicketRQ username(String username) {
    this.username = username;
    return this;
  }

   /**
   * Get username
   * @return username
  **/
  @ApiModelProperty(value = "")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostTicketRQ postTicketRQ = (PostTicketRQ) o;
    return Objects.equals(this.backLinks, postTicketRQ.backLinks) &&
        Objects.equals(this.domain, postTicketRQ.domain) &&
        Objects.equals(this.fields, postTicketRQ.fields) &&
        Objects.equals(this.includeComments, postTicketRQ.includeComments) &&
        Objects.equals(this.includeData, postTicketRQ.includeData) &&
        Objects.equals(this.includeLogs, postTicketRQ.includeLogs) &&
        Objects.equals(this.item, postTicketRQ.item) &&
        Objects.equals(this.logQuantity, postTicketRQ.logQuantity) &&
        Objects.equals(this.password, postTicketRQ.password) &&
        Objects.equals(this.token, postTicketRQ.token) &&
        Objects.equals(this.username, postTicketRQ.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(backLinks, domain, fields, includeComments, includeData, includeLogs, item, logQuantity, password, token, username);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PostTicketRQ {\n");

    sb.append("    backLinks: ").append(toIndentedString(backLinks)).append("\n");
    sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
    sb.append("    includeComments: ").append(toIndentedString(includeComments)).append("\n");
    sb.append("    includeData: ").append(toIndentedString(includeData)).append("\n");
    sb.append("    includeLogs: ").append(toIndentedString(includeLogs)).append("\n");
    sb.append("    item: ").append(toIndentedString(item)).append("\n");
    sb.append("    logQuantity: ").append(toIndentedString(logQuantity)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
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
