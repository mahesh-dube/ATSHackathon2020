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
public class TC_CH8_Part1 {

	@Test(priority = 2)
	public void getRequest() throws IOException {
		API_GenericFunctions.setProxy();

		RestAssured.baseURI = API_ExcelUtility.getURI("BaseURI");
		RestAssured.basePath = API_ExcelUtility.getURI("BasePath");

		try {

			Response response = RestAssured.given().log().all()
					.header(API_ExcelUtility.getHeader("ContentTypeKey"),
							API_ExcelUtility.getHeader("ContentTypeValue"))
					.when().get(API_ExcelUtility.getURI("Endpoint1")).then().extract().response().prettyPeek();
			int statusCode = response.getStatusCode();
			if (statusCode == 200) {
				APIExtentTestManager.getTest().log(Status.PASS, "Status code is 200 OK");
			} else {
				APIExtentTestManager.getTest().log(Status.FAIL, "Status code is" + statusCode);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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

	// deleted created records
	@Test(priority = 3)
	public void deleteRequest() throws IOException {
		API_GenericFunctions.setProxy();

		RestAssured.baseURI = API_ExcelUtility.getURI("BaseURI");
		RestAssured.basePath = API_ExcelUtility.getURI("BasePath");

		try {
			for (int i = 1; i <= 5; i++) {
				String companyName = API_ExcelUtility.getAPITestData("company" + i);
				Response response = RestAssured.given().log().all()
						.header(API_ExcelUtility.getHeader("ContentTypeKey"),
								API_ExcelUtility.getHeader("ContentTypeValue"))
						.when().delete(API_ExcelUtility.getURI("/" + companyName)).then().extract().response()
						.prettyPeek();
				int statusCode = response.getStatusCode();
				System.out.println("Status code for deletion is " + statusCode);
				if (statusCode == 204) {
					APIExtentTestManager.getTest().log(Status.PASS, "Status code is 204 resource deleted successfully");
				} else {
					APIExtentTestManager.getTest().log(Status.FAIL, "Status code is" + statusCode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 4)
	public void deleteInvalidCompanyRequest() throws IOException {
		API_GenericFunctions.setProxy();

		RestAssured.baseURI = API_ExcelUtility.getURI("BaseURI");
		RestAssured.basePath = API_ExcelUtility.getURI("BasePath");

		try {

			Response response = RestAssured.given().log().all()
					.header(API_ExcelUtility.getHeader("ContentTypeKey"),
							API_ExcelUtility.getHeader("ContentTypeValue"))
					.when().delete("/invalidcompanyName").then().extract().response().prettyPeek();
			int statusCode = response.getStatusCode();
			System.out.println("Status code for deletion is " + statusCode);
			if (statusCode == 404) {
				APIExtentTestManager.getTest().log(Status.PASS, "Status code is 404 Bad request");
			} else {
				APIExtentTestManager.getTest().log(Status.FAIL, "Status code is" + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
