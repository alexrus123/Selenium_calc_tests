package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NewTest {
	
	public static WebDriver myDriver;
	public static String pathToResources = "/Resources/";
	public static String CSVfilename = "test_data.csv";
	
	//Adding data from CSV to DataProvider TestNG
	@DataProvider(name = "Data")
    public Iterator<Object []> dataReader() throws InterruptedException {  
        List<Object []> dataLines = new ArrayList<>();  
        boolean notDataHeader = false;  
        String[] data;  
        BufferedReader br;  
        String line;  
        String workingDirectory = System.getProperty("user.dir");
        String filePath = workingDirectory + pathToResources + CSVfilename;
        
        try {  
            br = new BufferedReader(new FileReader(filePath));  
            while ((line = br.readLine()) != null) {  
                data = line.split(",");  
                if (notDataHeader) dataLines.add(data);  
                notDataHeader = true;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return dataLines.iterator();  
    } 
    
  @Test(dataProvider = "Data")
  // Actual test
  public void Calc_tests(String n1, String n2, String Operation, String Result){
	  
	  myDriver.get("http://web2.0calc.com/");
	  
	  myDriver.findElement(By.id("input")).sendKeys(n1);
	  
	  switch(Operation)
	  {
	  	case "Multiplication":
	  		myDriver.findElement(By.id("BtnMult")).click(); 
        break;
        
	  	case "Division":
	  		myDriver.findElement(By.id("BtnDiv")).click(); 
        break;
        
	  	case "Addition":
	  		myDriver.findElement(By.id("BtnPlus")).click(); 
        break;
        
	  	case "Subtraction":
	  		myDriver.findElement(By.id("BtnMinus")).click(); 
        break;
	  }
	  
	  myDriver.findElement(By.id("input")).sendKeys(n2);
	  
	  myDriver.findElement(By.id("BtnCalc")).click();
	  
	  myDriver.findElement(By.id("input")).click();
	  
	  //Waiting for History to become visible
	  WebDriverWait wait = new WebDriverWait(myDriver, 5);
	  wait.until(ExpectedConditions.presenceOfElementLocated(By.className("r")));
	
	  String ActualResult = myDriver.findElement(By.className("r")).getAttribute("title");
	  
	  System.out.println("---Results: " + ActualResult);
	  
	  Assert.assertEquals(ActualResult, Result);
  }
  
  @BeforeMethod
  public void beforeTest() {
	  //myDriver = new FirefoxDriver();
	  System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + pathToResources +"chromedriver");
	  myDriver = new ChromeDriver();
  }

  @AfterMethod
  public void afterTest() {
	  myDriver.quit();
  }
}
