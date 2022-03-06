package Base.org;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
public class Base {
	public static  WebDriver driver;

	//to find webElement
	public static WebElement  findele(String value , String loc) {
		WebElement ele = null;
		
		if (value.equals("id")) {
			
		ele = driver.findElement(By.id(loc));
			
		}
		else if (value.equals("name")) {
			
			ele= driver.findElement(By.name(loc));
			
		}
		else if (value.equals("xpath")) {
			ele= driver.findElement(By.xpath(loc));
		}	
		else {
			System.out.println("the enter mail id is incorrect");
		}
		
		return ele;
		}
	//to find webElement
	public static WebElement  toFilltxtboxbywait(String locName,String loc, String value) {
		
		WebDriverWait ar = new WebDriverWait(driver, Duration.ofSeconds(100));
		
		if (locName.equals("id")) {
			ar.until(ExpectedConditions.visibilityOfElementLocated(By.id(loc))).sendKeys(value);
		}
		
		else if (locName.equals("name")) {
		
		
		ar.until(ExpectedConditions.visibilityOfElementLocated(By.name(loc))).sendKeys(value);
		}
		
		else if(locName.equals("xpath")) {
		
		ar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loc))).sendKeys(value);

		}
		
		else {
			System.out.println("Invalid Locator");
		}
		return null;
	}
	
	//to click button
	//to find webElement
		public static WebElement  toclickwait(String locName,String loc) {
			
			WebDriverWait ar = new WebDriverWait(driver, Duration.ofSeconds(100));
			
			if (locName.equals("id")) {
				ar.until(ExpectedConditions.visibilityOfElementLocated(By.id(loc))).click();
			}
			
			else if (locName.equals("name")) {
			
			
			ar.until(ExpectedConditions.visibilityOfElementLocated(By.name(loc))).click();
			}
			
			else if(locName.equals("xpath")) {
			
			ar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loc))).click();

			}
			
			else {
				System.out.println("Invalid Locator");
			}
			return null;
		}


	// TO Open Chrome	
		public static void openChrome() {
		
	WebDriverManager.chromedriver().setup();

			driver = new ChromeDriver();
		}
		
		//To open firefox
		public void openfirefox() {
			
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		
		//TO open Edge
		public static void openedge() {
		
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
	}
	//TO Maximize Window
		public static void maximizewindow() {
		
	       driver.manage().window().maximize();
		}
		
		// to Launch url
		public static void launchUr(String ref) {
			
	      driver.get(ref);
		}
		
		// TO click 
		public static void click(WebElement a) {
	a.click();
		}
		
		// sendkeys
		public static void toFilltex(WebElement user, String name) {
			
		user.sendKeys(name);
			
		}
		// Implicity wait
		public static void waiting(int ref) {
			driver.manage().timeouts().implicitlyWait(ref, TimeUnit.SECONDS);

		}
	// TO hold	
		public static void toHold() throws InterruptedException {
			
	     Thread.sleep(10000);
		}
		
		//To read a From Excel sheet
		
	public static String readfromexcel( String sheetname,int row, int cell) throws Throwable {		
			
	File f = new File("C:\\Users\\syed.a\\eclipse-workspace\\Cucumeber1\\Workbook\\datadriven.xlsx");
	FileInputStream fin = new FileInputStream(f);
	Workbook book = new XSSFWorkbook(fin);
	Sheet sh = book.getSheet(sheetname);
	Row r = sh.getRow(row);
	Cell c = r.getCell(cell);

	int cellType = c.getCellType();

	String value =null;
	if(cellType==1) {
		
	 value = c.getStringCellValue();
	}
	else if (DateUtil.isCellDateFormatted(c)) {	
		Date date = c.getDateCellValue();
		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
	     value = s.format(date);
	}
	else {
	double dob = c.getNumericCellValue();
	long l = (long)dob;
	 value= String.valueOf(l);
	}
	return value;


	}

	// to take a screen shot
	public static void takeSnap(String picName) throws Exception {
		
		TakesScreenshot tk =(TakesScreenshot)driver;
		
		File st = tk.getScreenshotAs(OutputType.FILE);
		
		File de = new File("C:\\Users\\syed Arif\\eclipse-workspace\\Highengage\\target\\screenshot\\"+picName+".png");
		
		FileUtils.copyFile(st, de);

	}

	public static void toScrolldown(int value) {
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		js.executeScript("window.scrollBy(0,"+value+")");

	}
	// 14 Switch to Alert

		public static void alertswitch(String handle) {

			WebDriverWait al = new WebDriverWait(driver, Duration.ofSeconds(100));

			if (handle.equalsIgnoreCase("accept")) {

				al.until(ExpectedConditions.alertIsPresent()).accept();

			}

			else {

				al.until(ExpectedConditions.alertIsPresent()).dismiss();

			}
		}
		// 15 TO select by visible text

		public static void selectvisibiletext(WebElement loc, String value) {

			Select select = new Select(loc);

			select.selectByVisibleText(value);
		}

		public static void selectvisibyindex(WebElement loc, int value) {

			Select select = new Select(loc);

			select.selectByIndex(value);
		}

		public static void selectvisibilevalue(WebElement loc, String value) {

			Select select = new Select(loc);

			select.selectByValue(value);
		}

		public static void gettext(WebElement ref, String value) {

			String gettext = ref.getText();
			System.out.println(gettext);

		}

		public static WebElement getvalue(WebElement ref) {

			String attribute = ref.getAttribute("value");
			return ref;

		}

		// Robot class Enter
		public void enter(String value) throws Exception {
			Robot ro = new Robot();

			ro.keyPress(KeyEvent.VK_ENTER);
			ro.keyRelease(KeyEvent.VK_ENTER);

		}
		// clear the textbox
		
		public static void clearTextBox(String locName, String loc) {

			WebDriverWait ar = new WebDriverWait(driver, Duration.ofSeconds(100));
			
			ar.until(ExpectedConditions.visibilityOfElementLocated(By.id(loc))).clear();

			
			
			
			
			
		}


}
