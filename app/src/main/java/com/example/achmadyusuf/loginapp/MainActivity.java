package com.example.achmadyusuf.loginapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String ,String> hmLang = new HashMap<String,String>();
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    Button btn_save,Btloadingdate;
    String UseridStr,tglmuatStr,tempatmuatStr,nopolisiStr,nodoStr;
    String oilInt;
    EditText ET_Userid,ET_Nopolisi,ET_Deliveryorder,ET_oil;
    Spinner sp_loadingplace;
    String Loctionid;
    String ReturnResultsave;
    public static String URL = "http://192.168.43.173/wsmobile/WebService.asmx";
    public static String SOAP_ACTION_Login = "http://tempuri.org/ExecSP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Setuploadingplace();
        initDatePicker();
        dateButton = findViewById(R.id.Btloadingdate);
        dateButton.setText(getTodaysDate());
        ET_Userid=(EditText)findViewById(R.id.ETUserid);
        Btloadingdate=(Button)findViewById(R.id.Btloadingdate);
        sp_loadingplace=(Spinner)findViewById(R.id.sp_loadingplace);
        ET_Nopolisi=(EditText)findViewById(R.id.ETnopolice);
        ET_Deliveryorder=(EditText)findViewById(R.id.ETnodo);
        ET_oil=(EditText)findViewById(R.id.EToil);
        btn_save=(Button)findViewById(R.id.btnsave);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UseridStr = ET_Userid.getText().toString();
                tglmuatStr = Btloadingdate.getText().toString();
                tempatmuatStr= Loctionid;
                nopolisiStr=ET_Nopolisi.getText().toString();
                nodoStr=ET_Deliveryorder.getText().toString();
                oilInt=ET_oil.getText().toString();
                if(UseridStr.isEmpty()||nopolisiStr.isEmpty() ||nodoStr.isEmpty() ){
                    Toast.makeText(MainActivity.this,"Please, Enter Your Poliisi No and Delivery Order No", Toast.LENGTH_SHORT).show();
                }else{
                    new inputloadAsynctask().execute(UseridStr,nodoStr,tglmuatStr,tempatmuatStr,nopolisiStr,oilInt);
                }
            }
        });
    }

    private class inputloadAsynctask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject("http://tempuri.org/", "ExecSP");
            request.addProperty("SP","exec [SHI-FW\\Achmad.Yusuf].[M_Crude_User] @userid='" + strings[0].toString()+"' ");

            // request.addProperty("SP","exec [SHI-FW\\Achmad.Yusuf].[M_Crude_Load] @userid='" + strings[0].toString()+"' ,@NODO='" + strings[1].toString()+"' ,@LOADINGDATE='" + strings[2].toString()+"' ,@LOADINGFIELD='"+ strings[3].toString()+"' ,@NOPOLISI='" + strings[4].toString()+"' ,@OILUSE='"+ strings[5].toString()+"' ");
            request.addProperty("db", "_aims");
            //Declare the version of the SOAP request
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            //  AndroidHttpTransport httpTransport = new AndroidHttpTransport(URL);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug =true;
            SoapObject returnable = null;

            try {

                androidHttpTransport.call(SOAP_ACTION_Login, envelope);

                String TAG="";
                Log.d(TAG,"requestdump:"+androidHttpTransport.requestDump);
                Log.d(TAG,"responseDump:"+androidHttpTransport.responseDump);
                // returnable = (SoapObject)envelope.getResponse();
                SoapObject result = (SoapObject) envelope.bodyIn;



                System.out.println(result.toString());
                if (result != null) {
                    ArrayList list = new ArrayList(result.getPropertyCount());

                    MainActivity.XmlPullParserHandler parser = new MainActivity.XmlPullParserHandler();

                    String  is =result.getProperty(0).toString();
                    // String  is = result.toString().replace("<Data>","<?xml version=\"1.0\" encoding=\"utf-8\"?><Data>");

                    // is = is.replace("GetDataXmlResult=","");
                    //  is = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Data><Rows><NAMEOBJ>FLANGE 1 of BRANCH 1 of PIPE /flangeset-PIPE</NAMEOBJ><TYPE>FLAN</TYPE><DTXR>FLANGE WN ANSI B16.5 #150.RF</DTXR><CATRE>AAFWBB0JJ</CATRE><SPEC>A150/WNRF:50</SPEC></Rows><Rows><NAMEOBJ>GASKET 1 of BRANCH 1 of PIPE /flangeset-PIPE</NAMEOBJ><TYPE>GASK</TYPE><DTXR>GASKET ANSI B16.21 #150.RF</DTXR><CATRE>ABGCBBGJJ</CATRE><SPEC>A150/GRF:50</SPEC></Rows><Rows><NAMEOBJ>FLANGE 2 of BRANCH 1 of PIPE /flangeset-PIPE</NAMEOBJ><TYPE>FLAN</TYPE><DTXR>FLANGE WN ANSI B16.5 #150.RF</DTXR><CATRE>AAFWBB0JJ</CATRE><SPEC>A150/WNRF:50</SPEC></Rows></Data>";
                    ArrayList xmlOutput = parser.parse(is);

                   // ReturnResultsave = xmlOutput ;
                    //   lv_arr = new String[result.getPropertyCount()];
                    for (int i = 0; i < result.getPropertyCount(); i++) {
                        Object property = result.getProperty(i);
                        if (property instanceof SoapObject) {
                            SoapObject countryObj = (SoapObject) property;
                            String countryName = countryObj.getProperty(1).toString();
                            list.add(countryName );
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();

            }




            return ReturnResultsave;
        }
        protected void onPostExecute(String result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
           // imageView.setImageBitmap(result);
            // Hide the progress bar
          //  progressBar.setVisibility(ProgressBar.INVISIBLE);
            if(result.isEmpty()== false){

                //No Full Name
                //MyHomeActivity.putExtra("GetDisplayName",user.getDisplayName());
                //  MyHomeActivity.putExtra("GetPhotoUrl",user.getPhotoUrl());
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                //finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "User Name or Password inccorret", Toast.LENGTH_LONG).show();
                // Hide the progress bar
                //  progressBar.setVisibility(View.GONE);
            }
        }

    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


    private void Setuploadingplace(){

        Spinner dropdown = findViewById(R.id.sp_loadingplace);
        ArrayList<SpinnerLoadingPlace>  loadingPlaces = new ArrayList<SpinnerLoadingPlace>();
        loadingPlaces.add(new SpinnerLoadingPlace   ("1","PT.BHS") );
        loadingPlaces.add(new SpinnerLoadingPlace   ("2","PT.BBMM 2") );
        loadingPlaces.add(new SpinnerLoadingPlace   ("3","PT.KAI ANAE") );
        ArrayAdapter<SpinnerLoadingPlace> adapter = new ArrayAdapter<SpinnerLoadingPlace>(this, android.R.layout.simple_spinner_dropdown_item, loadingPlaces);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                SpinnerLoadingPlace tempatmuat = (SpinnerLoadingPlace)adapterView.getItemAtPosition(position);
                Loctionid=tempatmuat.id;
              //  Toast.makeText(MainActivity.this,tempatmuat.id,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.achmad_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Loading Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "checkloading", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Admin", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "checkloading", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public class XmlPullParserHandler {

        String text;
        StringBuilder builder = new StringBuilder();
        ArrayList<User> users = new ArrayList<>();
        User currentUser = null;
        public ArrayList<User> parse(String  is) {

            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(new StringReader( is ));

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = parser.getName();
                    switch (eventType) {

                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;

                        case XmlPullParser.START_TAG:
                            if (tagname.equalsIgnoreCase("Rows")) {
                                currentUser = new User();
                                users.add(currentUser);
                            } else if (currentUser != null ){

                                if (tagname.equalsIgnoreCase("UserId")) {
                                    builder.append("UserId = " + text);
                                    currentUser.userid=text;
                                } else if (tagname.equalsIgnoreCase("Username")) {
                                    builder.append("\nUsername = " + text);
                                    currentUser.username=text;
                                }
                            }

                            break;

                        default:
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return users ;
        }


    }


}
