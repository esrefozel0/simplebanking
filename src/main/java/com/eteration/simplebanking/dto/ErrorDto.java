package com.eteration.simplebanking.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Setter
@Getter
public class ErrorDto {
  private String title;
  private List<String> detail;
  private int status;
  private String error;
  private Integer errorCode;
  private String timestamp;

  public ErrorDto(String title, List<String> detail, String error, int status, Integer errorCode) {
    this.title = title;
    this.detail = detail;
    this.error = error;
    this.status = status;
    this.errorCode = errorCode;
    Calendar calendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    this.timestamp = dateFormat.format(calendar.getTime());
  }

  public static ErrorDtoBuilder builder() {
    return new ErrorDtoBuilder();
  }

  public static class ErrorDtoBuilder {
    private String title;
    private List<String> detail;
    private int status;
    private String error;
    private Integer errorCode;

    public ErrorDtoBuilder withTitle(final String title) {
      this.title = title;
      return this;
    }

    public ErrorDtoBuilder withDetail(final List<String> detail) {
      this.detail = detail;
      return this;
    }

    public ErrorDtoBuilder withStatus(final int status) {
      this.status = status;
      return this;
    }

    public ErrorDtoBuilder withError(final String error) {
      this.error = error;
      return this;
    }

    public ErrorDtoBuilder withErrorCode(final Integer errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public ErrorDto build() {
      return new ErrorDto(title, detail, error, status, errorCode);
    }
  }
}
