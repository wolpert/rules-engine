package com.codeheadsystems.rules.accessor;

import com.codeheadsystems.rules.model.Rule;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * The interface File accessor.
 */
public interface FileAccessor {

  /**
   * List files list.
   *
   * @param path the path
   * @return the list
   */
  List<String> listFiles(String path);

  /**
   * Gets file.
   *
   * @param path the path
   * @return the file
   */
  Optional<InputStream> getFile(String path);

}
