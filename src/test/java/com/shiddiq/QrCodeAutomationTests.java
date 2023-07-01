package com.shiddiq;

import org.testng.annotations.Test;

class QrCodeAutomationTests extends BaseTest {

	@Test(priority = 1)
	public void userCanOpenQrCodeGeneratorWebsite(){
		if (this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
		}
		WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
		WebUtil.verifyElementVisible("//*[@type='button' and text()='Generate \u2192']");
		WebUtil.verifyElementNotVisible("//textarea[@id='rawtextresult']");
		
	}

	@Test(priority = 2)
	public void userVerifyAllFieldEmptyWhenFirstTimeOpenHomePage(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
		}
		WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
	}
	
	
	@Test(priority = 3)
	public void userVerifyAllDropDownInDefaultState(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
			WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
		}

		WebUtil.verifyElementVisible("//*[text()='Contents']/following-sibling::*//*[@value='Contact information']");
		WebUtil.verifyElementVisible("//*[text()='Encoding']/following-sibling::*//*//*[@value='MECARD']");
		WebUtil.verifyElementVisible("//*[text()='Barcode size']/following-sibling::*//*//*[text()='Large']");
		WebUtil.verifyElementVisible("//*[text()='Error correction']/following-sibling::*//*//*[text()='L']");
		WebUtil.verifyElementVisible("//*[text()='Character encoding']/following-sibling::*//*//*[text()='UTF-8']");
	}

	@Test(priority = 4)
	public void userCanSeeDifferentTypeOfFormBasedOnContentsSeletedDropdown(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
			WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
		}
		
		WebUtil.verifyElementVisible("//*[@class='firstColumn' and text()='Name']");
		WebUtil.click("//*[text()='Contents']/following-sibling::*//*[@value='Contact information']");
		WebUtil.click("//*[text()='Contents']/following-sibling::*//*[@value='Wifi network']");
		WebUtil.verifyElementNotVisible("//*[@class='firstColumn' and text()='Name']");
		WebUtil.verifyElementVisible("//*[@class='firstColumn' and text()='SSID']");
		WebUtil.click("//*[text()='Contents']/following-sibling::*//*[@value='Wifi network']");
		WebUtil.click("//*[text()='Contents']/following-sibling::*//*[@value='Contact information']");
		WebUtil.verifyElementNotVisible("//*[@class='firstColumn' and text()='SSID']");
		WebUtil.verifyElementVisible("//*[@class='firstColumn' and text()='Name']");
	}

	@Test(priority = 5)
	public void userVerifyErrorTextWhenClickGenerateButtonUsingEmptyNameField(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
			WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
		}

		WebUtil.click("//*[@type='button' and text()='Generate \u2192']");
		WebUtil.verifyElementVisible("//*[@id='errorMessageID' and text()='Name must be at least 1 character.']");
	}

	@Test(priority = 6)
	public void userVerifyErrorTextWhenInputInvalidPhoneNumber(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
			WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
		}

		WebUtil.setText("//*[@class='firstColumn' and text()='Phone number']/following-sibling::*//input", "invalid number");
		WebUtil.click("//*[@class='firstColumn' and text()='Phone number']");
		WebUtil.verifyElementVisible("//*[@id='errorMessageID' and text()='Phone number must be digits only.']");
	}

	@Test(priority = 7)
	public void userVerifyErrorTextWhenInputInvalidEmailAddress(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
			WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
		}

		WebUtil.setText("//*[@class='firstColumn' and text()='Email']/following-sibling::*//input", "invalid email");
		WebUtil.click("//*[@class='firstColumn' and text()='Email']");
		WebUtil.verifyElementVisible("//*[@id='errorMessageID' and text()='Email is not valid.']");
	}

	@Test(priority = 8)
	public void userVerifyErrorTextWhenInputInvalidWebsite(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
			WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
		}

		WebUtil.setText("//*[@class='firstColumn' and text()='Website']/following-sibling::*//input", "invalid url");
		WebUtil.click("//*[@class='firstColumn' and text()='Website']");
		WebUtil.verifyElementVisible("//*[@id='errorMessageID' and text()='URL is not valid.']");
	}

	@Test(priority = 9)
	public void userCanFillTheForm(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
			WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
		}

		TemplateDemo.fillInForm();
	}

	@Test(priority = 10)
	public void userCanGenerateQrCode(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
			WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
			TemplateDemo.fillInForm();
		}

		TemplateDemo.generateQrCode();
	}

	@Test(priority = 11)
	public void userCanDownloadTheQrCode(){
		if(this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_GENERATOR_URL);
			WebUtil.verifyElementVisible("//*[@id='header']//*[text()='QR Code Generator ']//*[text()='from the ZXing Project']");
			WebUtil.verifyTextFieldEmpty("//*[text()='Name']/following-sibling::*[@class='secondColumn']//*[@type='text']");
			TemplateDemo.fillInForm();
			TemplateDemo.generateQrCode();
		}

		WebUtil.click(String.format("//*[@id='downloadText']//a[text()='Download' and contains(@href, '%s')]", GlobalConstants.BASE_QR_URL));
		WebUtil.verifyElementVisible(String.format("//img[contains(@src, '%s')]", GlobalConstants.BASE_QR_URL));
		WebUtil.verifyElementNotVisible(String.format("//*[@id='downloadText']//a[text()='Download' and contains(@href, '%s')]", GlobalConstants.BASE_QR_URL));
		WebUtil.saveImage(String.format("//img[contains(@src, '%s')]", GlobalConstants.BASE_QR_URL));
	}

	@Test(priority = 12)
	public void userCanOpenQrCodeDecoderWebsite(){
		WebUtil.navigateToUrl(GlobalConstants.QR_DECODER_URL);
		WebUtil.verifyElementVisible("//*[text()='QR code image: ']");
        WebUtil.verifyElementVisible("//input[@name='qrcode' and @accept='.png']");
        WebUtil.verifyElementVisible("//*[text()='Decoded:']");
		WebUtil.verifyElementVisible("//textarea[@id='decoded']");
	}

    @Test(priority = 13)
	public void userCanUploadQrCodeImage(){
		if (this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_DECODER_URL);
			WebUtil.verifyElementVisible("//*[text()='QR code image: ']");
			WebUtil.verifyElementVisible("//textarea[@id='decoded']");
		}
     
        WebUtil.uploadFile("//input[@name='qrcode' and @accept='.png']", "qrCode.png");
        WebUtil.verifyFileInserted("//input[@name='qrcode' and @accept='.png']");
        WebUtil.click("//button[text()='Submit']");
		TemplateDemo.verifyFileUploaded();
		WebUtil.verifyElementVisible("//textarea[@id='decoded' and contains(text(), 'TEL')]");
	}

	@Test(priority = 14)
	public void userVerifyDataDecodedWithDataWasInputBefore(){
		if (this.individualRun){
			WebUtil.navigateToUrl(GlobalConstants.QR_DECODER_URL);
            WebUtil.verifyElementVisible("//*[text()='QR code image: ']");
		    WebUtil.verifyElementVisible("//textarea[@id='decoded']");
			WebUtil.uploadFile("//input[@name='qrcode' and @accept='.png']", "qrCode.png");
        	WebUtil.verifyFileInserted("//input[@name='qrcode' and @accept='.png']");
        	WebUtil.click("//button[text()='Submit']");
			TemplateDemo.verifyFileUploaded();
		}

		TemplateDemo.compareDecodedInfoToInputInfo();
	}
}
