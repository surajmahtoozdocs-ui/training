package com.training.storefront.forms;

import de.hybris.platform.acceleratorstorefrontcommons.forms.ConsentForm;

/**
 * Custom form object for training registration (without titleCode).
 */
public class TrainingRegisterForm
{
	private String firstName;
	private String lastName;
	private String email;
	private String pwd;
	private String checkPwd;
	private ConsentForm consentForm;
	private boolean termsCheck;
private String mobileNumber;

public String getMobileNumber()
{
	return mobileNumber;
}

public void setMobileNumber(final String mobileNumber)
{
	this.mobileNumber = mobileNumber;
}
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(final String pwd)
	{
		this.pwd = pwd;
	}

	public String getCheckPwd()
	{
		return checkPwd;
	}

	public void setCheckPwd(final String checkPwd)
	{
		this.checkPwd = checkPwd;
	}

	public ConsentForm getConsentForm()
	{
		return consentForm;
	}

	public void setConsentForm(final ConsentForm consentForm)
	{
		this.consentForm = consentForm;
	}

	public boolean isTermsCheck()
	{
		return termsCheck;
	}

	public void setTermsCheck(final boolean termsCheck)
	{
		this.termsCheck = termsCheck;
	}
}
