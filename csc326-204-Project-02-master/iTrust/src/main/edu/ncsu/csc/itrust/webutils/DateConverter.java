package edu.ncsu.csc.itrust.webutils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateConverter")
public class DateConverter implements Converter {
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            return formatter.parse(value);
        } catch (IllegalArgumentException | ParseException e) {
        	FacesMessage throwMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid date format", "Date format must be mm/dd/yyyy");
         	throw new ConverterException(throwMsg);
        }
    }
	
	@Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (!(value instanceof Date)) {
            throw new ConverterException("Invalid Date");
        }
        Format formatter = new SimpleDateFormat("MM/dd/yyyy");
        String d = formatter.format(value);
        return d;
    }

}
