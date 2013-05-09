package com.example.testing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;

public class ChooseOperatingWeight extends DialogFragment // implements
{// android.content.DialogInterface.OnClickListener {

	Button chosen;
	private EditText weight;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getDialog().setTitle("Выберите рабочий вес!");
		// View v = inflater.inflate(R.layout.operating_weight_layout, null);
		// v.findViewById(R.id.chosen).setOnClickListener(this);

		weight = new EditText(getActivity());
		weight.setInputType(InputType.TYPE_CLASS_NUMBER);

		/*
		 * return new AlertDialog.Builder(getActivity())
		 * .setTitle(R.string.app_name) .setMessage("Please Enter Quantity")
		 * .setPositiveButton("OK", this).setView(weight)
		 * .setCanceledOnTouchOutside(false).create();
		 */

		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(getActivity()).create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.setCancelable(false);
		alertDialog.setTitle("Введите рабочий вес!");
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String value = weight.getText().toString();
						if (weight.getText().toString().length() == 0) {
							value = "0";
						}
						MyTimer callingActivity = (MyTimer) getActivity();
						callingActivity.onUserSelectValue(value);
						dismiss();
					}
				});
		alertDialog.setView(weight);
		alertDialog.show();
		return alertDialog;
	}

	/*
	 * public void onClick(DialogInterface dialog, int position) {
	 * 
	 * String value = weight.getText().toString(); if
	 * (weight.getText().toString().length() == 0) { weight.requestFocus();
	 * weight.setError(getString(R.string.complete_the_form)); return; } else {
	 * MyTimer callingActivity = (MyTimer) getActivity();
	 * callingActivity.onUserSelectValue(value); dismiss(); }
	 */

}
