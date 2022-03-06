package registration.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.org.Base;


public class Registrationpage extends Base {
	
	 /* @DataProvider(name = "emailid") public Object[][] datafoemail() { Object[][]
	  daa = {
	  
	  { "divyaba@engage2serve204.com", "Dhi2850ba@.com" },
	  
	 { "ahmed01test@engage2serve", "syedarif565csk00ll3k.com" },
	  
	  { "hari04ba@engage2ser305.com", "hari50xl8xvx1.com" },
	  
	  { "jyothi0manual@engage2s", "jyoyhi02506mlxxn851.com" },
	 
	  { "kumar11001hr@engage2serve2010011200.com", "jrajx50200xdjg12ll.com" }
	  
	 }; return daa;
	  
	  }*/
	

	public static String[][] datas() throws IOException {
		File f = new File("C:\\Users\\syed Arif\\eclipse-workspace\\Highengage\\data\\registrationdetails.xlsx");
		FileInputStream fin = new FileInputStream(f);
		Workbook book = new XSSFWorkbook(fin);
		Sheet sh = book.getSheet("Sheet1");
		Row row = sh.getRow(0);
		Cell cell = row.getCell(0);

		int RowCount = sh.getPhysicalNumberOfRows();
		int CellCount = row.getPhysicalNumberOfCells();

		String[][] obj = new String[RowCount - 1][CellCount];

		for (int i = 0; i < sh.getPhysicalNumberOfRows() - 1; i++) {
			Row r = sh.getRow(i + 1);
			for (int j = 0; j < r.getPhysicalNumberOfCells(); j++) {
				Cell c = r.getCell(j);

				int cellType = c.getCellType();

				String value;
				if (cellType == 1) {

					value = c.getStringCellValue();
				} else {
					if (DateUtil.isCellDateFormatted(c)) {
						Date date = c.getDateCellValue();
						SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
						value = s.format(date);
					} else {
						double dob = c.getNumericCellValue();
						long l = (long) dob;
						value = String.valueOf(l);
					}
				}
				obj[i][j] = value;
			}
		}
		return obj;
	}

	@DataProvider(name = "LoginCredentials")
	private String[][] loginData() throws IOException {
		String[][] datas = datas();
		return datas;
		
	}

	@BeforeClass
	public void highenglogin() {

		 openChrome();
		launchUr("https://www.highengage.com/register2/signup.html");
		 maximizewindow();
		 waiting(100);
	}

	@BeforeMethod
	public void timeStart() {

		Date dt = new Date();
		// long time = dt.getTime();
		System.out.println(dt);

	}

	
 public static String name;
 
	@Test( dataProvider = "LoginCredentials", enabled = false)
	public void login(String emailid, String firstname, String Lastname, String phone, String orgnizationname,
			String Title, String Websitrurl, String producturl) throws InterruptedException {
	//public void login(String emailid, String firstname, String Lastname, String phone, String orgnizationname,
			//String Title, String Websitrurl, String producturl) throws InterruptedException {

		Registrationpage.name = firstname;
		
		clearTextBox("id", "email");
		toFilltxtboxbywait("id", "email", emailid);

		toclickwait("xpath", "//input[@title='Get Started']");

	
		try {
			Assert.assertFalse(driver.findElement(By.xpath("//label[text()='Please enter a valid email address.']"))
					.isDisplayed());
		} catch (Exception e) {
			System.out.println("Please enter the valid email id");
		}
		//Thread.sleep(5000);
		toFilltxtboxbywait("id", "firstname", "Arif");
		toFilltxtboxbywait("id", "lastname", "Ahmed");
		toFilltxtboxbywait("id", "phone", "1234567896");
		toclickwait("xpath", "(//input[@type='submit'])[2]");
		Thread.sleep(4000);
		toFilltxtboxbywait("id", "organization", "Qucient");
		toclickwait("id", "employee");
		selectvisibilevalue(driver.findElement(By.id("employee")), "51 - 100");
		toFilltxtboxbywait("id", "title", "Java");
		toFilltxtboxbywait("id", "domain", "https//test1.com");
		toFilltxtboxbywait("id", "producturl", producturl);
		Thread.sleep(4000);
		toclickwait("id", "submit-form");
		try {
			if (driver.findElement(By.xpath("//h2[text()='Great! You are now registered!']")).isDisplayed()) {
				System.out.println("Successfully Worked");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		driver.navigate().refresh();

	}

	@AfterMethod
	public void timeEnd(ITestResult r) throws Exception {
		int snap = r.getStatus();
		
		if (snap == 2) {
		takeSnap(name);	
		}
		
			}

}
