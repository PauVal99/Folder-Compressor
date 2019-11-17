package tests.JUnit;

import org.junit.Assert;
import org.junit.Test;

import src.dominio.ByteArrayHelper;

public class ByteArrayHelperTest {
  @Test
  public void testIntToByteArrayOneByte() {
    byte[] b = {68};
    Assert.assertArrayEquals(b, ByteArrayHelper.intToByteArray(68, 1));
  }

  @Test
  public void testIntToByteArrayTwoBytes() {
    byte[] b = {68,0};
    Assert.assertArrayEquals(b, ByteArrayHelper.intToByteArray(68, 2));
  }

  @Test
  public void testIntToByteArrayThreeBytes() {
    byte[] b = {68,0,0};
    Assert.assertArrayEquals(b, ByteArrayHelper.intToByteArray(68, 3));
  }

  @Test
  public void testIntToByteArrayBigInt() {
    byte[] b = {2,1};
    Assert.assertArrayEquals(b, ByteArrayHelper.intToByteArray(258, 2));
  }

  @Test
  public void testIntToByteArrayBiggerInt() {
    byte[] b = {80,114,13,15};
    Assert.assertArrayEquals(b, ByteArrayHelper.intToByteArray(252539472, 4));
  }

  @Test
  public void testByteArrayToIntOneByte() {
    byte[] b = {68};
    Assert.assertEquals(68, ByteArrayHelper.byteArrayToInt(b));
  }

  @Test
  public void testByteArrayToIntTwoBytes() {
    byte[] b = {68,0};
    Assert.assertEquals(68, ByteArrayHelper.byteArrayToInt(b));
  }

  @Test
  public void testByteArrayToIntThreeBytes() {
    byte[] b = {68,0,0};
    Assert.assertEquals(68, ByteArrayHelper.byteArrayToInt(b));
  }

  @Test
  public void testByteArrayToIntBigInt() {
    byte[] b = {2,1};
    Assert.assertEquals(258, ByteArrayHelper.byteArrayToInt(b));
  }

  @Test
  public void testByteArrayToIntBiggerInt() {
    byte[] b = {80,114,13,15};
    Assert.assertEquals(252539472, ByteArrayHelper.byteArrayToInt(b));
  }
}