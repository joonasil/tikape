
package tikape.tikapeforum;

import java.util.HashMap;

public class HtmlEncoder {
    
    private HashMap<Character, String> muunnokset;
    
    public HtmlEncoder () {
        this.muunnokset = new HashMap();
        asetaMuunnokset();
    }
    
    private void asetaMuunnokset() {
        muunnokset.put('&', "&amp");
        muunnokset.put('<', "&lt");
        muunnokset.put('>', "&gt");
        muunnokset.put('\"', "&quot");
        muunnokset.put('\'', "&#x27");
        muunnokset.put('/', "&#x2F");
    }
    
    public String escapeHtml(String syote) {
        String escaped = "";
        HashMap<Character, String> muunnokset = new HashMap();
        
        muunnokset.put('&', "&amp");
        muunnokset.put('<', "&lt");
        muunnokset.put('>', "&gt");
        muunnokset.put('\"', "&quot");
        muunnokset.put('\'', "&#x27");
        muunnokset.put('/', "&#x2F");
        
        for (int i = 0; i < syote.length(); i++) {
            if (muunnokset.containsKey(syote.charAt(i))) {
                escaped += muunnokset.get(syote.charAt(i));
            } else {
                escaped += syote.charAt(i);
            }
        }
        
        return escaped;
    }
    
}
