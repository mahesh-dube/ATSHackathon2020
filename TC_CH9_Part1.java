package pocFramework.pocFramework;

import java.io.IOException;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import APIReport.APIExtentManager;
import APIReport.APIExtentTestManager;
import Function_Library.API_GenericFunctions;
import Utility.API_ExcelUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@Test
public class TC_CH9_Part1 {

	// Post request to create 5 companies record using Test data file
	@Test(priority = 1)
	public void postRequest() throws IOException {
		API_GenericFunctions.setProxy();

		RestAssured.baseURI = API_ExcelUtility.getURI("BaseURI");
		RestAssured.basePath = API_ExcelUtility.getURI("BasePath");

		try {
			for (int i = 1; i <= 5; i++) {
				String jsonBody = API_ExcelUtility.getJson("JsonBody" + i);
				Response response = RestAssured.given().log().all()
						.header(API_ExcelUtility.getHeader("ContentTypeKey"),
								API_ExcelUtility.getHeader("ContentTypeValue"))
						.body(jsonBody).when().post(API_ExcelUtility.getURI("Endpoint1")).then().extract().response()
						.prettyPeek();
				int statusCode = response.getStatusCode();
				long responseTime = response.getTime();

				APIExtentTestManager.getTest().log(Status.INFO,
						"Response time for concurrent requests" + i + " is :" + responseTime);

				if (statusCode == 201) {
					APIExtentTestManager.getTest().log(Status.PASS, "Status code is 201 Created");
				} else {
					APIExtentTestManager.getTest().log(Status.FAIL, "Status code is" + statusCode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
