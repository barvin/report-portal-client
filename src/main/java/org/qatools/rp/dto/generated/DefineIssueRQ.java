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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DefineIssueRQ
 */

public class DefineIssueRQ {
  @JsonProperty("issues")
  private List<IssueDefinition> issues = new ArrayList<>();

  public DefineIssueRQ issues(List<IssueDefinition> issues) {
    this.issues = issues;
    return this;
  }

  public DefineIssueRQ addIssuesItem(IssueDefinition issuesItem) {
    this.issues.add(issuesItem);
    return this;
  }

   /**
   * Get issues
   * @return issues
  **/
  @ApiModelProperty(required = true, value = "")
  public List<IssueDefinition> getIssues() {
    return issues;
  }

  public void setIssues(List<IssueDefinition> issues) {
    this.issues = issues;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefineIssueRQ defineIssueRQ = (DefineIssueRQ) o;
    return Objects.equals(this.issues, defineIssueRQ.issues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(issues);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DefineIssueRQ {\n");

    sb.append("    issues: ").append(toIndentedString(issues)).append("\n");
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
