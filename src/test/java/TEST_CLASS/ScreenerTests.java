
package TEST_CLASS;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import BASE_CLASS.BaseClass;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;

	public class ScreenerTests extends BaseClass {

	    @Test(priority = 1)
	    public void testQuarterlyGrowers() throws InterruptedException {
	    	
	    	
	    	driver.findElement(By.xpath("//a[text()=\"Screens\"]")).click();
	        
	        Thread.sleep(2000);

	        // Scroll to Quarterly Results
	        WebElement quarterlyResultsSection = driver.findElement(By.xpath("//h3[text()='Quarterly results']"));
	        
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", quarterlyResultsSection);
	        Thread.sleep(2000);

	        // Click on Quarterly Growers
	        driver.findElement(By.linkText("Quarterly growers")).click();
	        Thread.sleep(3000);

	        // Validate page
	        AssertJUnit.assertTrue(driver.getTitle().contains("Quarterly Growers"));

	        // Fetch first 10 rows
	        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
	        List<List<String>> tableData = new ArrayList<>();

	        // Add header row
	        List<String> headerRow = new ArrayList<>();
	        List<WebElement> headers = driver.findElements(By.cssSelector("table thead tr th"));
	        for (WebElement header : headers) {
	            headerRow.add(header.getText());
	        }
	        tableData.add(headerRow);

	        // Add data rows
	        for (int i = 0; i < 10 && i < rows.size(); i++) {
	            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
	            List<String> rowData = new ArrayList<>();
	            for (WebElement cell : cells) {
	                rowData.add(cell.getText());
	            }
	            tableData.add(rowData);
	        }

	        // Write to Excel
	        writeToExcel("QG", tableData);
	    }
	}

