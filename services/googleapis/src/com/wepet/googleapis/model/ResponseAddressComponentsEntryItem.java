/*
 * 
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.wepet.googleapis.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;
/**
 * ResponseAddressComponentsEntryItem
 */

public class ResponseAddressComponentsEntryItem {
  @JsonProperty("types")
  private List<String> types = new ArrayList<>();

  @JsonProperty("short_name")
  private String short_name = null;

  @JsonProperty("long_name")
  private String long_name = null;

  public ResponseAddressComponentsEntryItem types(List<String> types) {
    this.types = types;
    return this;
  }

  public ResponseAddressComponentsEntryItem addTypesItem(String typesItem) {
    this.types.add(typesItem);
    return this;
  }

   /**
   * Get types
   * @return types
  **/
  public List<String> getTypes() {
    return types;
  }

  public void setTypes(List<String> types) {
    this.types = types;
  }

  public ResponseAddressComponentsEntryItem short_name(String short_name) {
    this.short_name = short_name;
    return this;
  }

   /**
   * Get short_name
   * @return short_name
  **/
  public String getShortName() {
    return short_name;
  }

  public void setShortName(String short_name) {
    this.short_name = short_name;
  }

  public ResponseAddressComponentsEntryItem long_name(String long_name) {
    this.long_name = long_name;
    return this;
  }

   /**
   * Get long_name
   * @return long_name
  **/
  public String getLongName() {
    return long_name;
  }

  public void setLongName(String long_name) {
    this.long_name = long_name;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseAddressComponentsEntryItem Response_address_componentsEntryItem = (ResponseAddressComponentsEntryItem) o;
    return Objects.equals(this.types, Response_address_componentsEntryItem.types) &&
        Objects.equals(this.short_name, Response_address_componentsEntryItem.short_name) &&
        Objects.equals(this.long_name, Response_address_componentsEntryItem.long_name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(types, short_name, long_name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseAddressComponentsEntryItem {\n");
    
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
    sb.append("    short_name: ").append(toIndentedString(short_name)).append("\n");
    sb.append("    long_name: ").append(toIndentedString(long_name)).append("\n");
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
