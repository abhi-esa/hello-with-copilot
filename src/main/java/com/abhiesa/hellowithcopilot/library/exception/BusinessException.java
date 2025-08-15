package com.abhiesa.hellowithcopilot.library.exception;

/**
 * Exception thrown for business rule violations.
 * Defensive programming: always requires a non-null, non-empty message.
 */
public class BusinessException extends RuntimeException {
  /**
   * Constructs a BusinessException with a detail message.
   * @param msg the detail message (must not be null or empty)
   * @throws IllegalArgumentException if msg is null or empty
   */
  public BusinessException(String msg) {
    super(validateMessage(msg));
  }

  private static String validateMessage(String msg) {
    if (msg == null || msg.trim().isEmpty()) {
      throw new IllegalArgumentException("BusinessException message cannot be null or empty");
    }
    return msg;
  }
}
