package com.abhiesa.hellowithcopilot.library.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Defensive programming: always requires a non-null, non-empty message.
 */
public class NotFoundException extends RuntimeException {
  /**
   * Constructs a NotFoundException with a detail message.
   * @param msg the detail message (must not be null or empty)
   * @throws IllegalArgumentException if msg is null or empty
   */
  public NotFoundException(String msg) {
    super(validateMessage(msg));
  }

  private static String validateMessage(String msg) {
    if (msg == null || msg.trim().isEmpty()) {
      throw new IllegalArgumentException("NotFoundException message cannot be null or empty");
    }
    return msg;
  }
}