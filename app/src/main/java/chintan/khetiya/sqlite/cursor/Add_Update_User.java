package chintan.khetiya.sqlite.cursor;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Add_Update_User extends Activity {
    EditText add_name, add_mobile, add_email;
    Button add_save_btn, add_view_all, update_btn, update_view_all;
    LinearLayout add_view, update_view;
	private Customer mCustomer;

	private String mCurrentImagePath = null;
	private Uri mCapturedImageURI = null;
	private ImageButton mProfileImageButton;



	String valid_mob_number = null, valid_email = null, valid_name = null,
	    Toast_msg = null, valid_user_id = "";
    int USER_ID;
    DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.add_update_screen);

	// set screen
	Set_Add_Update_Screen();

	// set visibility of view as per calling activity
	String called_from = getIntent().getStringExtra("called");

	if (called_from.equalsIgnoreCase("add")) {
	    add_view.setVisibility(View.VISIBLE);
	    update_view.setVisibility(View.GONE);
	} else {

	    update_view.setVisibility(View.VISIBLE);
	    add_view.setVisibility(View.GONE);
	    USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

	    Contact c = dbHandler.Get_Contact(USER_ID);

	    add_name.setText(c.getName());
	    add_mobile.setText(c.getPhoneNumber());
	    add_email.setText(c.getEmail());
		if (mCurrentImagePath != null && !mCurrentImagePath.isEmpty()) {
			mProfileImageButton.setImageDrawable(new BitmapDrawable(getResources(),
					FileUtils.getResizedBitmap(mCurrentImagePath, 512, 512)));
		} else {
			mProfileImageButton.setImageDrawable(c.getImage(this));
		}


		// dbHandler.close();
	}
	add_mobile.addTextChangedListener(new TextWatcher() {

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		// min lenth 10 and max lenth 12 (2 extra for - as per phone
		// matcher format)
		Is_Valid_Sign_Number_Validation(12, 12, add_mobile);
	    }
	});
	add_mobile
		.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

	add_email.addTextChangedListener(new TextWatcher() {

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		Is_Valid_Email(add_email);
	    }
	});

	add_name.addTextChangedListener(new TextWatcher() {

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before,
		    int count) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
		    int after) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		Is_Valid_Person_Name(add_name);
	    }
	});

	add_save_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		// check the value state is null or not
		if (valid_name != null && valid_mob_number != null
			&& valid_email != null && valid_name.length() != 0
			&& valid_mob_number.length() != 0
			&& valid_email.length() != 0) {

		    dbHandler.Add_Contact(new Contact(valid_name,
			    valid_mob_number, valid_email,mCurrentImagePath));
		    Toast_msg = "Data inserted successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();

		}

	    }
	});

	update_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub

		valid_name = add_name.getText().toString();
		valid_mob_number = add_mobile.getText().toString();
		valid_email = add_email.getText().toString();

		// check the value state is null or not
		if (valid_name != null && valid_mob_number != null
			&& valid_email != null && valid_name.length() != 0
			&& valid_mob_number.length() != 0
			&& valid_email.length() != 0) {

		    dbHandler.Update_Contact(new Contact(USER_ID, valid_name,
			    valid_mob_number, valid_email,mCurrentImagePath));
		    dbHandler.close();
		    Toast_msg = "Data Update successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();
		} else {
		    Toast_msg = "Sorry Some Fields are missing.\nPlease Fill up all.";
		    Show_Toast(Toast_msg);
		}

	    }
	});
	update_view_all.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent view_user = new Intent(Add_Update_User.this,
			Main_Screen.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});

	add_view_all.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent view_user = new Intent(Add_Update_User.this,
			Main_Screen.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});

    }

    public void Set_Add_Update_Screen() {

	add_name = (EditText) findViewById(R.id.add_name);
	add_mobile = (EditText) findViewById(R.id.add_mobile);
	add_email = (EditText) findViewById(R.id.add_email);

	add_save_btn = (Button) findViewById(R.id.add_save_btn);
	update_btn = (Button) findViewById(R.id.update_btn);
	add_view_all = (Button) findViewById(R.id.add_view_all);
	update_view_all = (Button) findViewById(R.id.update_view_all);


		mProfileImageButton = (ImageButton) findViewById(R.id.customer_image_button);
		mProfileImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chooseImage();
			}
		});



	add_view = (LinearLayout) findViewById(R.id.add_view);
	update_view = (LinearLayout) findViewById(R.id.update_view);

	add_view.setVisibility(View.GONE);
	update_view.setVisibility(View.GONE);

    }

    public void Is_Valid_Sign_Number_Validation(int MinLen, int MaxLen,
	    EditText edt) throws NumberFormatException {
	if (edt.getText().toString().length() <= 0) {
	    edt.setError("Number Only");
	    valid_mob_number = null;
	} else if (edt.getText().toString().length() < MinLen) {
	    edt.setError("Minimum length " + MinLen);
	    valid_mob_number = null;

	} else if (edt.getText().toString().length() > MaxLen) {
	    edt.setError("Maximum length " + MaxLen);
	    valid_mob_number = null;

	} else {
	    valid_mob_number = edt.getText().toString();

	}

    } // END OF Edittext validation

    public void Is_Valid_Email(EditText edt) {
	if (edt.getText().toString() == null) {
	    edt.setError("Invalid Email Address");
	    valid_email = null;
	} else if (isEmailValid(edt.getText().toString()) == false) {
	    edt.setError("Invalid Email Address");
	    valid_email = null;
	} else {
	    valid_email = edt.getText().toString();
	}
    }

    boolean isEmailValid(CharSequence email) {
	return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    } // end of email matcher

    public void Is_Valid_Person_Name(EditText edt) throws NumberFormatException {
	if (edt.getText().toString().length() <= 0) {
	    edt.setError("Accept Alphabets Only.");
	    valid_name = null;
	} else if (!edt.getText().toString().matches("[a-zA-Z ]+")) {
	    edt.setError("Accept Alphabets Only.");
	    valid_name = null;
	} else {
	    valid_name = edt.getText().toString();
	}

    }

    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void Reset_Text() {

	add_name.getText().clear();
	add_mobile.getText().clear();
	add_email.getText().clear();

    }

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mCapturedImageURI != null) {
			outState.putString(Constants.KEY_IMAGE_URI, mCapturedImageURI.toString());
		}
		outState.putString(Constants.KEY_IMAGE_PATH, mCurrentImagePath);
	}



	private void chooseImage() {

		//We need the customer's name to to save the image file
		if (add_name.getText() != null && !add_name.getText().toString().isEmpty()) {
			// Determine Uri of camera image to save.
			final File rootDir = new File(Constants.PICTURE_DIRECTORY);

			//noinspection ResultOfMethodCallIgnored
			rootDir.mkdirs();

			// Create the temporary file and get it's URI.

			//Get the customer name
			String customerName = add_name.getText().toString();

			//Remove all white space in the customer name
			customerName.replaceAll("\\s+", "");

			//Use the customer name to create the file name of the image that will be captured
			File file = new File(rootDir, FileUtils.generateImageName(customerName));
			mCapturedImageURI = Uri.fromFile(file);

			// Initialize a list to hold any camera application intents.
			final List<Intent> cameraIntents = new ArrayList<Intent>();

			// Get the default camera capture intent.
			final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			// Get the package manager.
			final PackageManager packageManager = this.getPackageManager();

			// Ensure the package manager exists.
			if (packageManager != null) {

				// Get all available image capture app activities.
				final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);

				// Create camera intents for all image capture app activities.
				for (ResolveInfo res : listCam) {

					// Ensure the activity info exists.
					if (res.activityInfo != null) {

						// Get the activity's package name.
						final String packageName = res.activityInfo.packageName;

						// Create a new camera intent based on android's default capture intent.
						final Intent intent = new Intent(captureIntent);

						// Set the intent data for the current image capture app.
						intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
						intent.setPackage(packageName);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

						// Add the intent to available camera intents.
						cameraIntents.add(intent);
					}
				}
			}

			// Create an intent to get pictures from the filesystem.
			final Intent galleryIntent = new Intent();
			galleryIntent.setType("image/*");
			galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

			// Chooser of filesystem options.
			final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

			// Add the camera options.
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
					cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

			// Start activity to choose or take a picture.
			startActivityForResult(chooserIntent, Constants.ACTION_REQUEST_IMAGE);
		} else {
			add_name.setError("Please enter customer name");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			// Get the resultant image's URI.
			final Uri selectedImageUri = (data == null) ? mCapturedImageURI : data.getData();

			// Ensure the image exists.
			if (selectedImageUri != null) {

				// Add image to gallery if this is an image captured with the camera
				//Otherwise no need to re-add to the gallery if the image already exists
				if (requestCode == Constants.ACTION_REQUEST_IMAGE) {
					final Intent mediaScanIntent =
							new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					mediaScanIntent.setData(selectedImageUri);
					this.sendBroadcast(mediaScanIntent);
				}

				mCurrentImagePath = FileUtils.getPath(this, selectedImageUri);

				// Update client's picture
				if (mCurrentImagePath != null && !mCurrentImagePath.isEmpty()) {
					mProfileImageButton.setImageDrawable(new BitmapDrawable(getResources(),
							FileUtils.getResizedBitmap(mCurrentImagePath, 512, 512)));
				}
			}
		}

	}



}
