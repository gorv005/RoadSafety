 package com.app.roadsafety.frameworks.retrofit;

 import com.google.gson.annotations.Expose;
 import com.google.gson.annotations.SerializedName;

 public class RestError {

     @SerializedName("status")
     @Expose
     private String status;

     @SerializedName("http_code")
     @Expose
     private Integer httpCode;

     @SerializedName("resource")
     @Expose
     private String resource;

     @SerializedName("message")
     @Expose
     private String message;

     @SerializedName("error")
     @Expose
     private String error;
     @SerializedName("error_description")
     @Expose
     private String errorDescription;
     public String getStatus() {
         return status;
     }

     public void setStatus(String status) {
         this.status = status;
     }

     public Integer getHttpCode() {
         return httpCode;
     }

     public void setHttpCode(Integer httpCode) {
         this.httpCode = httpCode;
     }

     public String getResource() {
         return resource;
     }

     public void setResource(String resource) {
         this.resource = resource;
     }

     public String getMessage() {
         return message;
     }

     public void setMessage(String message) {
         this.message = message;
     }

     public String getError() {
         return error;
     }

     public void setError(String error) {
         this.error = error;
     }

     public String getErrorDescription() {
         return errorDescription;
     }

     public void setErrorDescription(String errorDescription) {
         this.errorDescription = errorDescription;
     }

 }