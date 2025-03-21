package BASE_CLASS;

	import org.apache.poi.ss.usermodel.*;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.*;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.io.FileHandler;
	import org.testng.ITestResult;
	import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.*;
	import java.time.Duration;
	import java.util.*;

	public class BaseClass {

	    protected WebDriver driver;
	    protected String baseUrl = "https://www.screener.in/";
	    protected String excelFilePath = "./Test_Data.xlsx";

	    @BeforeClass
	    public void setup() {
	        WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        driver.manage().window().maximize();
	        driver.get(baseUrl);
	    }

	    @AfterMethod
	    public void takeScreenshotOnFailure(ITestResult result) {
	        if (ITestResult.FAILURE == result.getStatus()) {
	            try {
	                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	                FileHandler.copy(screenshot, new File("C:\\Users\\shrad\\eclipse-workspace\\INFOSYS\\src\\test\\resources\\screenshots" + result.getName() + ".png"));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    @AfterClass
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }

	    protected void writeToExcel(String sheetName, List<List<String>> data) {
	        try (Workbook workbook = new XSSFWorkbook();
	             FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {

	            Sheet sheet = workbook.createSheet(sheetName);
	            for (int i = 0; i < data.size(); i++) {
	                Row row = sheet.createRow(i);
	                List<String> rowData = data.get(i);
	                for (int j = 0; j < rowData.size(); j++) {
	                    Cell cell = row.createCell(j);
	                    cell.setCellValue(rowData.get(j));
	                }
	            }
	            workbook.write(fileOut);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

