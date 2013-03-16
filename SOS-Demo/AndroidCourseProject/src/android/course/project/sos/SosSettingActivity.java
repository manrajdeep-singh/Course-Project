package android.course.project.sos;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.course.project.sos.domain.Contact;
import android.course.project.sos.utility.DatabaseHelper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public final class SosSettingActivity extends Activity {

	private TextView sosPhoneCallLabel;
	private HashMap<String,Contact> selectedContact;
	static final int PICK_CONTACT_REQUEST = 1;  // The request code
	EditText Name;
	EditText Phone;
	private DatabaseHelper dbHelper;

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sos_setting);
        selectedContact = new HashMap<String,Contact>();
        sosPhoneCallLabel = (TextView) findViewById(R.id.textTimer);
        
        dbHelper=new DatabaseHelper(this);
		
        
        Button pickPhoneCallButton = (Button) findViewById(R.id.btnPickPhoneCallContact);       
        pickPhoneCallButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        		// calling OnActivityResult with intenet And Some conatct for Identifie
        		startActivityForResult(intent, PICK_CONTACT_REQUEST);

        	}
        });
        
        Button pickSmsButton = (Button) findViewById(R.id.btnPickSMSContact);       
        pickSmsButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){

        	}
        });
        
    }
	
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);
		switch (reqCode) {
		case (PICK_CONTACT_REQUEST):
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
		        String[] projection = new String[] {
		                ContactsContract.Contacts._ID,
		                ContactsContract.Contacts.DISPLAY_NAME,
		        };
		        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
				Cursor c = getContentResolver().query(contactData, projection, null, null, sortOrder);
				if (c.moveToFirst()) {
					Contact selected = new Contact();
					selected.setID(c.getString(c.getColumnIndexOrThrow(People._ID)));
					selected.setName(c.getString(c.getColumnIndexOrThrow(People.DISPLAY_NAME)));
//					selected.setPhoneNumber(c.getString(c.getColumnIndexOrThrow(People.PRIMARY_PHONE_ID)));
					selected.setPhone(true);
					selectedContact.put(selected.getID(), selected);
				}
			}
			
			break;
		}
	}
	

}
