package com.bestbuy.curdTest;

import com.bestbuy.bestbuyinfo.StoreSteps;
import com.bestbuy.testBase.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class StoreCURDTest extends TestBase {

  static String name = "Minnetonka12";
  static String type = "BangBox";
  static String address = "135313 Ridgedale Dr";

  static String address2 = " ";
  static String city = "Hopkins";
  static String state = "MN";
  static String zip = "55355";
  static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9;  Sun: 10-8";
  //  static String services;
  static double lat = 44.969658;
  static double lng = -93.449539;
  static int storeId;
  @Steps
  StoreSteps storeSteps;

  @BeforeClass
  public static void inIt() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 3030;
    RestAssured.basePath = "/stores";


  }
  @Title("This will create a new store")
  @Test
  public void test001() {

    ValidatableResponse response = storeSteps.createProducts(name, type, address, address2, city, state, zip, hours, lat, lng)
            .log().all().statusCode(201);
    storeId = (int) response.extract().path("id");

  }

  @Title("Verify if the Store was created to the store")
  @Test
  public void test002() {
    storeSteps.getStoreInfoByStoresId(storeId);
    // HashMap<String, Object> storeMap = storeSteps.getStoreInfoByStoresId(storeId);
    //Assert.assertThat(storeMap, hasValue(storeId));
    // storeId = (int) storeMap.get("id");

  }

  @Title("Update the store information and verify the updated store")
  @Test
  public void test003() {
    city = city + "_updated";
    storeSteps.updateStore(storeId).log().all()
            .statusCode(200);

//        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByStoresId(storeId);
//        Assert.assertThat(storeMap, hasValue(storeId));
//        storeId = (int)storeMap.get("id");
  }


  @Title("Delete the store and verify if the store is deleted")
  @Test
  public void test004() {
    storeSteps.deleteStore(storeId).log().all()
            .statusCode(200);

    storeSteps.getStoreById(storeId)
            .statusCode(404);

  }


}


