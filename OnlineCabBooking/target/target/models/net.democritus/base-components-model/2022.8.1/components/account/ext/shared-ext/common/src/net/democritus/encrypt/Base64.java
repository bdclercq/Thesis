package net.democritus.encrypt;

/**
 * Baso. 64 encode, decode, based on Wikipedia: https://en.wikipedia.org/wiki/Base64
 * <p>
 * At the moment we don't handle linebreaks (\n or \r) when decoding.
 * We don't insert linebreaks when encoding.
 */

// @feature:password-encryption
public class Base64 {

  private static final String CODES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
  private static final char[] CHARS = CODES.toCharArray();

  private static byte lookup(char c) {
    if (c >= 'A' && c <= 'Z') {
      return (byte) (c - 'A');
    }

    if (c >= 'a' && c <= 'z') {
      return (byte) (26 + c - 'a');
    }

    if (c >= '0' && c <= '9') {
      return (byte) (52 + c - '0');
    }

    if (c == '+') {
      return 62;
    }

    if (c == '/') {
      return 63;
    }

    if (c == '=') {
      return 64;
    }

    throw new IllegalArgumentException("Not a valid character '" + c + "'");
  }

  public static String encode(byte[] in) {
    StringBuilder out = new StringBuilder((in.length * 4) / 3);
    int b;
    for (int i = 0; i < in.length; i += 3) {
      b = (in[i] & 0xFC) >> 2;
      out.append(CHARS[b]);
      b = (in[i] & 0x03) << 4;
      if (i + 1 < in.length) {
        b |= (in[i + 1] & 0xF0) >> 4;
        out.append(CHARS[b]);
        b = (in[i + 1] & 0x0F) << 2;
        if (i + 2 < in.length) {
          b |= (in[i + 2] & 0xC0) >> 6;
          out.append(CHARS[b]);
          b = in[i + 2] & 0x3F;
          out.append(CHARS[b]);
        } else {
          out.append(CHARS[b]);
          out.append('=');
        }
      } else {
        out.append(CHARS[b]);
        out.append("==");
      }
    }

    return out.toString();
  }

  @SuppressWarnings("java:S2692")
  public static byte[] decode(String input) {
    if (input.length() % 4 != 0) {
      throw new IllegalArgumentException("Invalid base64 input");
    }
    byte[] decoded = new byte[((input.length() * 3) / 4) - (input.indexOf('=') > 0 ? (input.length() - input.indexOf('=')) : 0)];
    char[] inChars = input.toCharArray();
    int j = 0;
    int[] b = new int[4];
    for (int i = 0; i < inChars.length; i += 4) {
      // This could be made faster (but more complicated) by precomputing these index locations
      b[0] = lookup(inChars[i]);
      b[1] = lookup(inChars[i + 1]);
      b[2] = lookup(inChars[i + 2]);
      b[3] = lookup(inChars[i + 3]);
      decoded[j++] = (byte) ((b[0] << 2) | (b[1] >> 4));
      if (b[2] < 64) {
        decoded[j++] = (byte) ((b[1] << 4) | (b[2] >> 2));
        if (b[3] < 64) {
          decoded[j++] = (byte) ((b[2] << 6) | b[3]);
        }
      }
    }

    return decoded;
  }

}
