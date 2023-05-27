package Exceptions;

import com.sun.org.apache.xpath.internal.objects.XString;

public class XmlFileDoesntExist extends RuntimeException{
    private final String EXCEPTION_MESSAGE="Your file doesn't exist!!";

    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
