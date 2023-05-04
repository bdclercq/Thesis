package net.democritus.assets;

import net.palver.logging.Logger;
import net.palver.logging.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger("net.democritus.assets.ByteArrayConverter");

  public static byte[] toByteArray(InputStream is) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      int nRead;
      byte[] data = new byte[16384];

      while ((nRead = is.read(data, 0, data.length)) != -1) {
        outputStream.write(data, 0, nRead);
      }

      outputStream.flush();
      return outputStream.toByteArray();
    } catch (IOException e) {
      LOGGER.error("Could not convert inputStream to byte array");
      return null;
    }
  }

}
