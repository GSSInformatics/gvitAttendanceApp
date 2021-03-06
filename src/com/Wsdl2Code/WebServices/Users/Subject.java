package com.Wsdl2Code.WebServices.Users;

//------------------------------------------------------------------------------
// <wsdl2code-generated>
//    This code was generated by http://www.wsdl2code.com version  2.5
//
// Date Of Creation: 1/6/2015 6:55:51 AM
//    Please dont change this code, regeneration will override your changes
//</wsdl2code-generated>
//
//------------------------------------------------------------------------------
//
//This source code was auto-generated by Wsdl2Code  Version
//
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import java.util.Hashtable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

public class Subject implements KvmSerializable {
    
    public String subjectCode;
    public String branch;
    public String year;
    public String semester;
    public String section;
    
    public Subject(){}
    
    public Subject(SoapObject soapObject)
    {
        if (soapObject == null)
            return;
        if (soapObject.hasProperty("SubjectCode"))
        {
            Object obj = soapObject.getProperty("SubjectCode");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                subjectCode = j.toString();
            }else if (obj!= null && obj instanceof String){
                subjectCode = (String) obj;
            }
        }
        if (soapObject.hasProperty("Branch"))
        {
            Object obj = soapObject.getProperty("Branch");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                branch = j.toString();
            }else if (obj!= null && obj instanceof String){
                branch = (String) obj;
            }
        }
        if (soapObject.hasProperty("Year"))
        {
            Object obj = soapObject.getProperty("Year");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                year = j.toString();
            }else if (obj!= null && obj instanceof String){
                year = (String) obj;
            }
        }
        if (soapObject.hasProperty("Semester"))
        {
            Object obj = soapObject.getProperty("Semester");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                semester = j.toString();
            }else if (obj!= null && obj instanceof String){
                semester = (String) obj;
            }
        }
        if (soapObject.hasProperty("Section"))
        {
            Object obj = soapObject.getProperty("Section");
            if (obj != null && obj.getClass().equals(SoapPrimitive.class)){
                SoapPrimitive j =(SoapPrimitive) obj;
                section = j.toString();
            }else if (obj!= null && obj instanceof String){
                section = (String) obj;
            }
        }
    }
    @Override
    public Object getProperty(int arg0) {
        switch(arg0){
            case 0:
                return subjectCode;
            case 1:
                return branch;
            case 2:
                return year;
            case 3:
                return semester;
            case 4:
                return section;
        }
        return null;
    }
    
    @Override
    public int getPropertyCount() {
        return 5;
    }
    
    @Override
    public void getPropertyInfo(int index, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo info) {
        switch(index){
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SubjectCode";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Branch";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Year";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Semester";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Section";
                break;
        }
    }
    
    @Override
    public void setProperty(int arg0, Object arg1) {
    }
    
}
