package parse;

/**
 * Created by wenhao on 2017/10/23.
 */

public class JudgeStr {
    public static boolean isQualifiedStr(String str){
        String id="",DLC="",totalDD="";
        int numOfDD=0;
        if(str.charAt(0)=='T'){
            if(str.length()<10) return false;
            id=str.substring(1,9);
            DLC=str.substring(9,10);
            try{
                numOfDD=Integer.parseInt(DLC);
            }catch (Exception e){
                return false;
            }
            if(numOfDD<0||numOfDD>8) return false;
            if(str.length()!=10+numOfDD*2) return false;
            totalDD=str.substring(10,10+numOfDD*2);
            if(isHex(id)&&isHex(totalDD)){
                return true;
            }else{
                return false;
            }
        }else if(str.charAt(0)=='t'){
            if(str.length()<5) return false;
            id=str.substring(1,4);
            DLC=str.substring(4,5);
            try{
                numOfDD=Integer.parseInt(DLC);
            }catch (Exception e){
                return false;
            }
            if(numOfDD<0||numOfDD>8) return false;
            if(str.length()!=5+numOfDD*2) return false;
            totalDD=str.substring(5,5+numOfDD*2);
            if(isHex(id)&&isHex(totalDD)){
                return true;
            }else{
                return false;
            }

        }else{
            return false;
        }
    }
    public static boolean isHex(String str) {
        boolean isHexFlg = true;

        int i = 0;
        char c;

        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);

            if ( !( ( (  c >= '0' ) && ( c <= '9' ) ) ||
                    ( (  c >= 'A' ) && ( c <= 'F' ) ) ||
                    (  c >= 'a' ) && ( c <= 'f' ) ) ) {
                isHexFlg = false;
                break;
            }
        }

        return isHexFlg;
    }
}
