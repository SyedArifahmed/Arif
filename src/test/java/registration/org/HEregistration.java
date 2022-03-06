package registration.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.org.Base;

public class HEregistration extends Base {

	/*
	 * @DataProvider(name = "emailid") public Object[][] datafoemail() { Object[][]
	 * daa = {
	 * 
	 * { "divya@engage2serve204.com", "Dhi2850@.com" },
	 * 
	 * { "ahmed01@engage2serve", "syedarif565ck00ll3k.com" },
	 * 
	 * { "hari04@engage2ser300.com", "hari50xl8vx1.com" },
	 * 
	 * { "jyothi0@engage2s", "jyoyhi02506mlxxn851.com" },
	 * 
	 * { "kumar11001@engage2serve201001200.com", "jrajx50200xdg12ll.com" }
	 * 
	 * }; return daa;
	 * 
	 * }
	 */

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
	}

	@BeforeMethod
	public void timeStart() {

		Date dt = new Date();
		// long time = dt.getTime();
		System.out.println(dt);

	}

	private static String name;

	@Test(priority = 1, dataProvider = "LoginCredentials", enabled = true)
	public void login(String emailid, String firstname, String Lastname, String phone, String orgnizationname,
			String Title, String Websitrurl, String producturl) throws InterruptedException {
		this.name = Lastname;

		clearTextBox("id", "email");
		toFilltxtboxbywait("id", "email", emailid);

		toclickwait("xpath", "//input[@title='Get Started']");

		try {
			Assert.assertFalse(driver.findElement(By.xpath("//label[text()='Please enter a valid email address.']"))
					.isDisplayed());
		} catch (Exception e) {
			System.out.println("Please enter the valid email id");
		}

		// Thread.sleep(5000);
		toFilltxtboxbywait("id", "firstname", firstname);

		WebElement fstname = driver.findElement(By.id("firstname"));
		/*
		 * String fst = fstname.getAttribute("value"); int namelen = fst.length(); if
		 * (namelen <=3) { fstname.clear();
		 * 
		 * } if (fst.equals("0")) { fstname.clear(); }
		 */

		toFilltxtboxbywait("id", "lastname", Lastname);
		toFilltxtboxbywait("id", "phone", phone);
		toclickwait("xpath", "(//input[@type='submit'])[2]");

		List<WebElement> character = driver.findElements(By.xpath("Please enter at least 3 characters."));
		for (WebElement allcharacter : character) {

			if (allcharacter.getText().trim().equals("Please enter at least 3 characters.")) {

				Assert.fail();
			}

			List<WebElement> alphebet = driver.findElements(By.xpath("Alphabets only please"));
			for (WebElement allalphabet : alphebet) {

				if (allalphabet.getText().trim().equals("Alphabets only please")) {

					Assert.fail();
				}
			}

			try {
				if (driver.findElement(By.xpath("(//label[text()='This field is required.'])[1]")).isDisplayed()
						|| driver.findElement(By.xpath("(//label[text()='This field is required.'])[2]"))
								.isDisplayed()) {

					Assert.fail();

				}
			} catch (Exception e1) {
				System.out.println("PLESE THE DETAILS");
			}

			toFilltxtboxbywait("id", "organization", orgnizationname);
			WebElement org = driver.findElement(By.id("organization"));
			String orgs = org.getAttribute("value");
			if (orgs.equals("0")) {
				org.clear();
			}

			toclickwait("id", "employee");
			selectvisibilevalue(driver.findElement(By.id("employee")), "51 - 100");
			toFilltxtboxbywait("id", "title", Title);
			toFilltxtboxbywait("id", "domain", Websitrurl);
			toFilltxtboxbywait("id", "producturl", producturl);
			WebElement prods = driver.findElement(By.id("producturl"));
			String prod = prods.getAttribute("value");
			if (prod.equals("0")) {
				prods.clear();
			}

			Thread.sleep(4000);
			toclickwait("xpath", "//input[@id='submit-form']");

			Thread.sleep(4000);

			List<WebElement> characters = driver.findElements(By.xpath("Please enter at least 3 characters."));
			for (WebElement allcharacters : characters) {

				if (allcharacters.getText().trim().equals("Please enter at least 3 characters.")) {

					Assert.fail();
				}

				try {
					List<WebElement> allfields = driver
							.findElements(By.xpath("//label[text()='This field is required.']"));
					for (WebElement eachfield : allfields) {

						if (eachfield.getText().trim().equals("This field is required.")) {
							Assert.fail();

						}

					}
				} catch (Exception e) {
					e.getStackTrace();
				}

				try {
					Assert.assertFalse(
							driver.findElement(By.xpath("//div[text()='Enter proper Domain name']")).isDisplayed());
				} catch (Exception e) {
					System.out.println("Invalid Domain name");
				}

				// Thread.sleep(5000);
				try {
					if (driver.findElement(By.xpath("//h2[text()='Great! You are now registered!']")).isDisplayed()) {
						System.out.println("Successfully Worked");
					}
				} catch (Exception e) {
					e.getStackTrace();
				}
			}
		}
	}

	@AfterMethod
	public void timeEnd(ITestResult r) throws Exception {
		int status = r.getStatus();
		if (status == 2) {
			takeSnap(name);
			Thread.sleep(4000);
		}
		driver.navigate().refresh();
		Thread.sleep(4000);
	}

	@AfterClass
	public void quit() {

		driver.quit();
	}

}
