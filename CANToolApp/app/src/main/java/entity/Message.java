package entity;

/**
 * Created by wenhao on 2017/10/17.
 */

public class Message {
    private String BO_;
    private String id;
    private String MessageName;
    private String seporator;
    private String DLC;
    private String NodeName;

     Message(String id,String MessageName,String  DLC,String NodeName){
         BO_="BO_";
         this.id=id;
         this.MessageName=MessageName;
         seporator=":";
         this.DLC=DLC;
         this.NodeName=NodeName;

    }

    public String getBO_() {
        return BO_;
    }

    public String getId() {
        return id;
    }

    public String getMessageName() {
        return MessageName;
    }

    public String getSeporator() {
        return seporator;
    }

    public String getDLC() {
        return DLC;
    }

    public String getNodeName() {
        return NodeName;
    }
}
