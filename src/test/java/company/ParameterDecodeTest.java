package company;

import com.company.responders.ParameterDecode;
import org.junit.Test;

import java.io.IOException;
import java.util.Hashtable;

import static junit.framework.Assert.assertEquals;

public class ParameterDecodeTest {
    ParameterDecode parameterDecode = new ParameterDecode();
    Hashtable<String, Object> req = new Hashtable<String, Object>();
    Hashtable<String, Object> resp = new Hashtable<String, Object>();

    @Test public void testParameterDecode() throws IOException {
        Hashtable<String, String> body = new Hashtable<String, String>();
        Hashtable<String, String> params = new Hashtable<String, String>();
        params.put("hello", "world");
        params.put("cruel", "life");
        req.put("Request-URI", "/form");
        req.put("Parameters", params);
        req.put("Method", "POST");
        req.put("Body", body);
        resp = parameterDecode.respond(req);
        String newBody = new String((byte[]) resp.get("message-body"), "UTF-8");
        assertEquals("<html><head><title></title></head><body>  hello = world</br>" +
                "cruel = life</br>" +
                " </body></html>", newBody);
    }
}
