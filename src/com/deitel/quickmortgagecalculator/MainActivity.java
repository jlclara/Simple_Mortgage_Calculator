/*
 * File name: MainActivity.java
 * Project name: QuickMortgageCalculator
 * Written by: Jenny Nguyen
 * Date: Mar 4 2015
 */

package com.deitel.quickmortgagecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.NumberFormat;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class MainActivity extends Activity 
{
	
	private static final NumberFormat cf = NumberFormat.getCurrencyInstance();
	private static final NumberFormat nf = NumberFormat.getNumberInstance();
	
   
	private static final String LOAN_AMOUNT = "LOAN_AMOUNT";
    private static final String CUSTOM_INTEREST_RATE = "CUSTOM_INTEREST_RATE";
   
    private double currentLoanAmount; 
    private EditText loanEditText; 
   
    private double currentCustomRate; 
    private TextView customRateTextView; 
   
    private EditText tenYearEditText; 
    private EditText twentyYearEditText; 
    private EditText thirtyYearEditText;  

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if ( savedInstanceState == null ) 
	      {
	         currentLoanAmount = 0.0; 
	         currentCustomRate = 500; 
	      } 
	    else 
	      {
	    	 currentLoanAmount = savedInstanceState.getDouble(LOAN_AMOUNT); 
	         currentCustomRate = 
	            savedInstanceState.getDouble(CUSTOM_INTEREST_RATE); 
	      }
	      
		customRateTextView = (TextView) findViewById(R.id.customRateTextView);
	    loanEditText = (EditText) findViewById(R.id.loanEditText);
	    loanEditText.addTextChangedListener(loanEditTextWatcher);
	    SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
	    customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
	    tenYearEditText = (EditText) findViewById(R.id.tenYearEditText);
	    twentyYearEditText = (EditText) findViewById(R.id.twentyYearEditText);
	    thirtyYearEditText = (EditText) findViewById(R.id.thirtyYearEditText);	
	}
	
	private double formula(double loan, double rate, int term)   
	{ 
	  if (rate==0)
		  return (loan/(12*term));
	  double c = rate/100/12.0;
	  double n = term *12;
	  return loan * (c * Math.pow(1 + c, n )) / ( Math.pow(1 + c,n)-1);
	}
	
	private void updateMonthlyPayment()
	{
		double year10 = formula(currentLoanAmount, currentCustomRate/100, 10);
		double year20 = formula(currentLoanAmount, currentCustomRate/100, 20);
		double year30 = formula(currentLoanAmount, currentCustomRate/100, 30);
		tenYearEditText.setText(cf.format(year10));
		twentyYearEditText.setText(cf.format(year20));
		thirtyYearEditText.setText(cf.format(year30));
	}
	
	private void updateCustomRate()
	{
		customRateTextView.setText(String.format("%.02f", currentCustomRate/100) + "%");
		updateMonthlyPayment();
	}
	
	private OnSeekBarChangeListener customSeekBarListener = 
		new OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser)
				
		{
			currentCustomRate = seekBar.getProgress();
			updateCustomRate();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {}
		
	};
	
	private TextWatcher loanEditTextWatcher = new TextWatcher()
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			try
			{
				currentLoanAmount = Double.parseDouble(s.toString());
			}
			catch (NumberFormatException e)
			{
				currentLoanAmount = 0.0;
			}
			updateMonthlyPayment();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}

		@Override
		public void afterTextChanged(Editable s) {}
		
	};
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action  bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}//end of Activity
