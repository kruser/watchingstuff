/**
 * 
 */
package com.watchingstuff.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kruser
 */
public class ZipUtilsTest
{
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
	}

	/**
	 * Test turning a zip file into a map of filename/contents
	 * @throws IOException 
	 */
	@Test
	public void testFullFiles() throws IOException
	{
		InputStream zipFile = getClass().getClassLoader().getResourceAsStream("archive.zip");
		Assert.assertNotNull(zipFile);
		ZipInputStream zipInputStream = new ZipInputStream(zipFile);
		
		Map<String, String> files = ZipUtils.getTextFilesFromZip(zipInputStream);
		Assert.assertEquals(3, files.size());
		
		Assert.assertTrue(files.get("1.txt").startsWith("1"));
		Assert.assertTrue(files.get("2.txt").startsWith("2"));
		Assert.assertTrue(files.get("3.txt").startsWith("3"));
	}
	
	@Test
	public void testStreamedFiles() throws IOException
	{
		InputStream zipFile = getClass().getClassLoader().getResourceAsStream("archive.zip");
		Assert.assertNotNull(zipFile);
		ZipInputStream zipInputStream = new ZipInputStream(zipFile);
		
		InputStream file1 = ZipUtils.getFileFromZip(zipInputStream, "1.txt");
		Assert.assertNotNull(file1);

		String file1Contents = IOUtils.toString(file1);
		Assert.assertTrue(file1Contents.startsWith("1"));

		InputStream file2 = ZipUtils.getFileFromZip(zipInputStream, "2.txt");
		Assert.assertNotNull(file2);

		String file2Contents = IOUtils.toString(file2);
		Assert.assertTrue(file2Contents.startsWith("2"));

	}

}
