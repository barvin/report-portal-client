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

import java.util.Objects;

/**
 * IssueDefinition
 */

public class IssueDefinition {
  @JsonProperty("issue")
  private Issue issue = null;

  @JsonProperty("test_item_id")
  private String testItemId = null;

  public IssueDefinition issue(Issue issue) {
    this.issue = issue;
    return this;
  }

   /**
   * Get issue
   * @return issue
  **/
  @ApiModelProperty(required = true, value = "")
  public Issue getIssue() {
    return issue;
  }

  public void setIssue(Issue issue) {
    this.issue = issue;
  }

  public IssueDefinition testItemId(String testItemId) {
    this.testItemId = testItemId;
    return this;
  }

   /**
   * Get testItemId
   * @return testItemId
  **/
  @ApiModelProperty(required = true, value = "")
  public String getTestItemId() {
    return testItemId;
  }

  public void setTestItemId(String testItemId) {
    this.testItemId = testItemId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IssueDefinition issueDefinition = (IssueDefinition) o;
    return Objects.equals(this.issue, issueDefinition.issue) &&
        Objects.equals(this.testItemId, issueDefinition.testItemId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(issue, testItemId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IssueDefinition {\n");

    sb.append("    issue: ").append(toIndentedString(issue)).append("\n");
    sb.append("    testItemId: ").append(toIndentedString(testItemId)).append("\n");
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
