package com.shiddiq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import com.shiddiq.DelayManager.DelayTime;

public class TemplateDemo extends BaseTemplate {
    public static void fillInForm(){
        WebUtil.setText("//*[@class='firstColumn' and text()='Name']/following-sibling::*//input", "random name");
		WebUtil.setText("//*[@class='firstColumn' and text()='Company']/following-sibling::*//input", "random company");
		WebUtil.setText("//*[@class='firstColumn' and text()='Title']/following-sibling::*//input", "random Title");
		WebUtil.setText("//*[@class='firstColumn' and text()='Phone number']/following-sibling::*//input", "081215464444");
		WebUtil.setText("//*[@class='firstColumn' and text()='Email']/following-sibling::*//input", "test@gmail.com");
		WebUtil.setText("//*[@class='firstColumn' and text()='Address']/following-sibling::*//input", "random Address");
		WebUtil.setText("//*[@class='firstColumn' and text()='Address 2']/following-sibling::*//input", "random Address 2");
		WebUtil.setText("//*[@class='firstColumn' and text()='Website']/following-sibling::*//input", "www.google.com");
		WebUtil.setText("//*[@class='firstColumn' and text()='Memo']/following-sibling::*//input", "random Memo");

		GlobalVariable.inputName += WebUtil.getText("//*[@class='firstColumn' and text()='Name']/following-sibling::*//input");
		GlobalVariable.inputCompany += WebUtil.getText("//*[@class='firstColumn' and text()='Company']/following-sibling::*//input");
		GlobalVariable.inputPhoneNumber += WebUtil.getText("//*[@class='firstColumn' and text()='Phone number']/following-sibling::*//input");
		GlobalVariable.inputEmail += WebUtil.getText("//*[@class='firstColumn' and text()='Email']/following-sibling::*//input");
		GlobalVariable.inputAddress += WebUtil.getText("//*[@class='firstColumn' and text()='Address']/following-sibling::*//input");
		GlobalVariable.inputWebsite += WebUtil.getText("//*[@class='firstColumn' and text()='Website']/following-sibling::*//input");
		GlobalVariable.inputMemo += WebUtil.getText("//*[@class='firstColumn' and text()='Memo']/following-sibling::*//input");
		
		//change the encoding type
		WebUtil.click("//*[@class='firstColumn' and text()='Encoding']/following-sibling::*//*[@value='MECARD']");
		WebUtil.click("//*[@class='firstColumn' and text()='Encoding']/following-sibling::*//*[@value='vCard']");
		WebUtil.verifyElementVisible("//*[@class='firstColumn' and text()='Encoding']/following-sibling::*//*[@value='vCard']");
		WebUtil.click("//*[@class='firstColumn' and text()='Encoding']/following-sibling::*//*[@value='vCard']");
		WebUtil.click("//*[@class='firstColumn' and text()='Encoding']/following-sibling::*//*[@value='MECARD']");

		//change the barcode size
		WebUtil.click("//*[@class='firstColumn' and text()='Barcode size']/following-sibling::*//*[text()='Large']");
		WebUtil.click("//*[@class='firstColumn' and text()='Barcode size']/following-sibling::*//*[text()='Medium']");
		WebUtil.verifyElementVisible("//*[@class='firstColumn' and text()='Barcode size']/following-sibling::*//*[text()='Medium']");

		//change the Error correction
		WebUtil.click("//*[@class='firstColumn' and text()='Error correction']/following-sibling::*//*[@value='L']");
		WebUtil.click("//*[@class='firstColumn' and text()='Error correction']/following-sibling::*//*[@value='M']");
		WebUtil.verifyElementVisible("//*[@class='firstColumn' and text()='Error correction']/following-sibling::*//*[@value='M']");

		//change the Character encoding
		WebUtil.click("//*[@class='firstColumn' and text()='Character encoding']/following-sibling::*//*[@value='UTF-8']");
		WebUtil.click("//*[@class='firstColumn' and text()='Character encoding']/following-sibling::*//*[@value='ISO-8859-1']");
		WebUtil.verifyElementVisible("//*[@class='firstColumn' and text()='Character encoding']/following-sibling::*//*[@value='ISO-8859-1']");

		// make sure errmsg empty
		String errMsg = WebUtil.getText("//*[@id='errorMessageID']");
		Assert.assertTrue(errMsg.trim().isEmpty(), "CheckErrorMsg");
    }

	public static void generateQrCode(){
		WebUtil.click("//*[@type='button' and text()='Generate \u2192']");
		WebUtil.verifyElementVisible(String.format("//*[@id='innerresult']//img[contains(@src, '%s')]", GlobalConstants.BASE_QR_URL));
		WebUtil.verifyElementVisible("//textarea[@id='rawtextresult']");
		WebUtil.verifyElementVisible(String.format("//*[@id='downloadText']//a[text()='Download' and contains(@href, '%s')]", GlobalConstants.BASE_QR_URL));
		Assert.assertTrue(WebUtil.getText("//*[@id='urlresult']").contains(GlobalConstants.BASE_QR_URL));
	}

	public static void compareDecodedInfoToInputInfo(){
		String actualDecodedText = WebUtil.getText("//textarea[@id='decoded']");
		List<String> actualTexts = convertDecodedTextIntoList(actualDecodedText);

		List<String> expectedTexts = Arrays.asList(
            GlobalVariable.inputName,
            GlobalVariable.inputCompany,
            GlobalVariable.inputPhoneNumber,
            GlobalVariable.inputWebsite,
            GlobalVariable.inputEmail,
            GlobalVariable.inputAddress,
            GlobalVariable.inputMemo
    	);

		System.out.println("actualTexts " + actualTexts);
		System.out.println("expectedTexts " + expectedTexts);		
		Assert.assertTrue(compareTwoListOfInfo(actualTexts, expectedTexts));
	}

	public static void verifyFileUploaded(){
		int count = 0;
		boolean isUploaded = false;
		while (!isUploaded && count < 5){
			try {
				WebUtil.verifyElementNotVisible("//*[@id='decoded' and text()='Now Loading...']");
				isUploaded = true;
			} catch (Exception e) {
				isUploaded = false;
			} finally {
				DelayManager.delay(DelayTime.MEDIUM);
				count++;
			}	
		}
		Assert.assertTrue(isUploaded);
	}

	private static List<String> convertDecodedTextIntoList(String text){
        String[] fields = text.split(";");

        List<String> formattedList = new ArrayList<>();
        for (String field : fields) {
            String[] keyValue = field.split(":");
			System.out.println(field);

            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                String formattedLine = key + ":" + value;
                formattedList.add(formattedLine);
            }else if (keyValue.length > 2){
				formattedList.add(keyValue[1] + ":" +  keyValue[2]);
			}
        }
		return formattedList;
	}

	private static boolean compareTwoListOfInfo(List<String> actual, List<String> expected){
		 if (actual.size() != expected.size()) {
            return false;
        }

        for (int i = 0; i < actual.size(); i++) {
            if (!actual.get(i).contains(expected.get(i))) {
				System.out.println("actual text is " + actual + " not match with expected " + expected);
                return false;
            }
        }

        return true;
	}
}
