package com.codeheadsystems.rules.model;

/**
 * The enum Window.
 */
public enum Window {

  /**
   * Event window.
   */
  EVENT(0),
  /**
   * Hour window.
   */
  HOUR(1),
  /**
   * Day window.
   */
  DAY(24),
  /**
   * Week window.
   */
  WEEK(168),
  /**
   * Month window.
   */
  MONTH(730),
  /**
   * Year window.
   */
  YEAR(8760),
  /**
   * Forever window.
   */
  FOREVER(Integer.MAX_VALUE);

  private final int hours;

  Window(final int hours) {
    this.hours = hours;
  }

  /**
   * Hours int.
   *
   * @return the int
   */
  public int hours() {
    return hours;
  }

}
