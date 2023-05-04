package net.democritus.assets;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteArrayConverterTest {

  @Test
  public void testToByteArray() throws Exception {
    File sourceFile = File.createTempFile("input", "bin");
    FileOutputStream fileOutputStream = new FileOutputStream(sourceFile);
    // generates 2000 * 10 bytes large file, which is larger than the buffer size 16384
    for (int i = 0; i < 2000; i++) {
      fileOutputStream.write("0123456789".getBytes());
    }
    fileOutputStream.close();

    FileInputStream sourceInputStream = new FileInputStream(sourceFile);
    byte[] bytes = ByteArrayConverter.toByteArray(sourceInputStream);

    File outputFile = writeToFile(bytes);

    Assert.assertNotNull(bytes);
    Assert.assertEquals(20000, bytes.length);
    Assert.assertEquals(20000, outputFile.length());
  }

  private File writeToFile(byte[] bytes) throws IOException {
    File outputFile = File.createTempFile("output", "bin");

    FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
    fileOutputStream.write(bytes);
    fileOutputStream.close();

    return outputFile;
  }

}