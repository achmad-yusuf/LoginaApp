package com.example.achmadyusuf.loginapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.HashMap;
import java.net.URL;
import java.net.URLEncoder;


public class LoginActivity extends AppCompatActivity {
    EditText ET_Userid,ET_Password;
    Button btn_Login;
    String UseridStr,PasswordStr,doLoginResult;
    ArrayList ReturnResult;
    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "pid";
    /** Web Service */
    public static String URL = "http://192.168.43.173/wsmobile/WebService.asmx";
   // public static String URL = "http://tempuri.org/WebService.asmx";
  //  public static String URL = "http://localhost/wsmobile/WebService.asmx?WSDL";
  //  public static String URL = "http://10.0.2.2:80/wsmobile/WebService.asmx?WSDL";
   // public static String URL = "http://10.191.4.174:62608/WebService.asmx";
   // public static String NAMESPACE = "http://192.168.43.173";
    // public static String NAMESPACE = "http://192.168.43.173/wsmobile/WebService.asmx?op=GetXml";
   public static String NAMESPACE="http://tempuri.org/";
    /** Login */
  //  public static String SOAP_ACTION_Login = "http://192.168.43.173/wsmobile/GetXml";
   // public static String SOAP_ACTION_Login = "http://192.168.43.173/GetXml";
 //   public static String SOAP_ACTION_Login = "http://192.168.43.173/wsmobile/WebService.asmx/GetXml";
    //public static String SOAP_ACTION_Login = "http://192.168.43.173/wsmobile/WebService.asmx";
  ////  public static String SOAP_ACTION_Login = "http://192.168.43.173/wsmobile/WebService.asmx?op=GetXml";
   // public static String SOAP_ACTION_Login = "http://192.168.43.173/wsmobile";
    public static String SOAP_ACTION_Login = "http://tempuri.org/GetDataXml"; //jalan tapi erro cari drive d
  //  public static String SOAP_ACTION_Login = "http://tempuri.org/GetXml"; //jalan tapi erro cari drive d
   // public static String SOAP_ACTION_Login = "http://localhost/GetXml"; //jalan tapi erro cari drive d
   //public static String SOAP_ACTION_Login = "http://localhost/wsmobile/GetXml";
  //  public static String SOAP_ACTION_Login = "http://10.0.2.2:80/wsmobile/WebService.asmx?op=GetXml";
   // public static String SOAP_ACTION_Login = "http://192.168.43.173/wsmobile/GetXml";
    //public static String SOAP_ACTION_Login = "http://192.168.43.173/wsmobile/WebService.asmx?op=GetXml";
   // public static String SOAP_ACTION_Login = "http://192.168.43.173/wsmobile/WebService/GetXml";
   // public static String SOAP_ACTION_Login = "http://192.168.43.173:80/GetXml";
    //public static String METHOD_NAME_Login = "GetDataXml";
    public static String METHOD_NAME_Login = "GetDataXml";
    //private static final String url_product_detials = "http://192.168.43.173/wsmobile/GetXml";
    //private static final String url_product_detials = "http://192.168.43.173/wsmobile/WebService.asmx/GetDataXml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ET_Userid=(EditText)findViewById(R.id.ETUserid);
        ET_Password=(EditText)findViewById(R.id.ETPassword);

        btn_Login=(Button)findViewById(R.id.btnlogin);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UseridStr=ET_Userid.getText().toString();
                PasswordStr=ET_Password.getText().toString();
                if(UseridStr.isEmpty()||PasswordStr.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please, Enter Your Email and Password.", Toast.LENGTH_SHORT).show();
                }
                else{
                    new MyAsyncTask().execute(UseridStr,PasswordStr);

                }

            }
        });

    }

    private class MyAsyncTask extends AsyncTask<String,Void,ArrayList> {

        @Override
        protected ArrayList doInBackground(String... strings) {
           // SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME_Login);
            SoapObject request = new SoapObject("http://tempuri.org/", "GetDataXml");

          //  SoapObject request = new SoapObject("http://tempuri.org/", "GetXml");
            Object response;
            //PropertyInfo infoEmail=new PropertyInfo();
            //infoEmail.setName("Sql");
            //infoEmail.setType(String.class);
            //infoEmail.setValue(strings[0].toString());
          //  infoEmail.setValue("SELECT top 5 * from dbo.SPCOUSING_VEF");
           // infoEmail.setValue("SELECT top 5 * from dbo.SPCOUSING_VEF where type ='"+ strings[0].toString()+"'");
            request.addProperty("Sql","select top 3 * from [SHI-FW\\Achmad.Yusuf].tbl_user where userid='" + strings[0].toString()+"'" + " and password ='" + strings[1].toString()+"'");

            //PropertyInfo infoPassword=new PropertyInfo();
            //infoPassword.setName("strKon");
            //infoPassword.setType(String.class);
            //infoPassword.setValue( strings[1].toString());

            //Use this to add parameters
            //request.addProperty(infoPassword);
             request.addProperty("strKon", "_aims");

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
                //Thread.sleep(5000);
                //  AndroidHttpTransport
                //this is the actual part that will call the webservice
              //  androidHttpTransport.debug = true;
               // androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                androidHttpTransport.call(SOAP_ACTION_Login, envelope);
               // System.out.println("####################: " +envelope.getResponse());
                //  SoapPrimitive resultee=(SoapPrimitive)envelope.getResponse();
                //StringBuffer result2;
                //result2 = new StringBuffer(resultee.toString());
                // Get the SoapResult from the envelope body.
                String TAG="";
                Log.d(TAG,"requestdump:"+androidHttpTransport.requestDump);
                Log.d(TAG,"responseDump:"+androidHttpTransport.responseDump);
               // returnable = (SoapObject)envelope.getResponse();
                SoapObject result = (SoapObject) envelope.bodyIn;
                //SoapObject result = (SoapObject) envelope.getResponse();
                //---json format
               // int success;
               // String pid;
                //HashMap<String, String> params = new  HashMap<String, String>();
                //Intent j = getIntent();
                //pid = j.getStringExtra(TAG_PID);
                //params.put("Sql", "select top 3 * from dbo.spcousing_vef");
                //params.put("strKon", "_aims");
                //JSONParser jsonParser = new JSONParser();
                //JSONObject json = jsonParser.makeHttpRequest(url_product_detials, "POST", params);

                //Log.d("Single Product Details", json.toString());
                //success = json.getInt(TAG_SUCCESS);
               // SoapObject result = (SoapObject) envelope.getResponse();
                //request=(SoapObject)envelope.getResponse();
                //response=request.toString();
                // SoapObject result = (SoapObject) envelope.getResponse();

                System.out.println(result.toString());
                if (result != null) {
                    ArrayList list = new ArrayList(result.getPropertyCount());

                    XmlPullParserHandler parser = new XmlPullParserHandler();

                    String  is =result.getProperty(0).toString();
                   // String  is = result.toString().replace("<Data>","<?xml version=\"1.0\" encoding=\"utf-8\"?><Data>");

                   // is = is.replace("GetDataXmlResult=","");
                    //  is = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Data><Rows><NAMEOBJ>FLANGE 1 of BRANCH 1 of PIPE /flangeset-PIPE</NAMEOBJ><TYPE>FLAN</TYPE><DTXR>FLANGE WN ANSI B16.5 #150.RF</DTXR><CATRE>AAFWBB0JJ</CATRE><SPEC>A150/WNRF:50</SPEC></Rows><Rows><NAMEOBJ>GASKET 1 of BRANCH 1 of PIPE /flangeset-PIPE</NAMEOBJ><TYPE>GASK</TYPE><DTXR>GASKET ANSI B16.21 #150.RF</DTXR><CATRE>ABGCBBGJJ</CATRE><SPEC>A150/GRF:50</SPEC></Rows><Rows><NAMEOBJ>FLANGE 2 of BRANCH 1 of PIPE /flangeset-PIPE</NAMEOBJ><TYPE>FLAN</TYPE><DTXR>FLANGE WN ANSI B16.5 #150.RF</DTXR><CATRE>AAFWBB0JJ</CATRE><SPEC>A150/WNRF:50</SPEC></Rows></Data>";
                    ArrayList xmlOutput = parser.parse(is);

                    ReturnResult = xmlOutput ;
                 //   lv_arr = new String[result.getPropertyCount()];
                    for (int i = 0; i < result.getPropertyCount(); i++) {
                        Object property = result.getProperty(i);
                        if (property instanceof SoapObject) {
                            SoapObject countryObj = (SoapObject) property;
                            String countryName = countryObj.getProperty(1).toString();
                            list.add(countryName );
                        }
                    }
                    //Get the first property and change the label text
                   // ReturnResult = result.getProperty(0).toString();
//String msg=resultee.toString();
                    //    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //return e.toString();
                return null;
            }

            return ReturnResult;
        }

        protected void onPostExecute(ArrayList result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task


            if(result.isEmpty()== false){
                Intent MyHomeActivity=new Intent(LoginActivity.this,MainActivity.class);
                MyHomeActivity.putExtra("GetUser",UseridStr);
                //No Full Name
                //MyHomeActivity.putExtra("GetDisplayName",user.getDisplayName());
                //  MyHomeActivity.putExtra("GetPhotoUrl",user.getPhotoUrl());
                startActivity(MyHomeActivity);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "User Name or Password inccorret", Toast.LENGTH_LONG).show();
                // Hide the progress bar
                //  progressBar.setVisibility(View.GONE);
            }

        }

        //pharser

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
