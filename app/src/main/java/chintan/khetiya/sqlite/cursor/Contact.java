package chintan.khetiya.sqlite.cursor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Contact {

    // private variables
    public int _id;
    public String _name;
    public String _phone_number;
    public String _email;
    private String mImagePath;
    public Contact() {
    }

    // constructor
    public Contact(int id, String name, String _phone_number, String _email,  String mImagePath) {
	this._id = id;
	this._name = name;
	this._phone_number = _phone_number;
	this._email = _email;
        this.mImagePath=mImagePath;

    }

    // constructor
    public Contact(String name, String _phone_number, String _email,String mImagePath) {
	this._name = name;
	this._phone_number = _phone_number;
	this._email = _email;
        this.mImagePath=mImagePath;
    }

    // getting ID
    public int getID() {
	return this._id;
    }

    // setting id
    public void setID(int id) {
	this._id = id;
    }

    // getting name
    public String getName() {
	return this._name;
    }

    // setting name
    public void setName(String name) {
	this._name = name;
    }

    // getting phone number
    public String getPhoneNumber() {
	return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
	this._phone_number = phone_number;
    }

    // getting email
    public String getEmail() {
	return this._email;
    }

    // setting email
    public void setEmail(String email) {
	this._email = email;
    }
    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }


    public boolean hasImage() {

        return getImagePath() != null && !getImagePath().isEmpty();
    }


    /**
     * Get a thumbnail of this profile's picture, or a default image if the profile doesn't have a
     * Image.
     *
     * @return Thumbnail of the profile.
     */
    public Drawable getThumbnail(Context context) {

        return getScaledImage(context, 128, 128);
    }

    /**
     * Get this profile's picture, or a default image if the profile doesn't have a Image.
     *
     * @return Image of the profile.
     */
    public Drawable getImage(Context context) {

        return getScaledImage(context, 512, 512);
    }

    /**
     * Get a scaled version of this profile's Image, or a default image if the profile doesn't have
     * a Image.
     *
     * @return Image of the profile.
     */
    private Drawable getScaledImage(Context context, int reqWidth, int reqHeight) {

        // If profile has a Image.
        if (hasImage()) {

            // Decode the input stream into a bitmap.
            Bitmap bitmap = FileUtils.getResizedBitmap(getImagePath(), reqWidth, reqHeight);

            // If was successfully created.
            if (bitmap != null) {

                // Return a drawable representation of the bitmap.
                return new BitmapDrawable(context.getResources(), bitmap);
            }
        }

        // Return the default image drawable.
        return context.getResources().getDrawable(Constants.DEFAULT_IMAGE_RESOURCE);
    }



}