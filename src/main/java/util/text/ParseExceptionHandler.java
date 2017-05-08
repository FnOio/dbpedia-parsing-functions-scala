package util.text;

/**
 */
public interface ParseExceptionHandler
{
  /**
   * @param error never {@code null}
   * @throws ParseException maybe...
   */
  void error(ParseException error);
}
