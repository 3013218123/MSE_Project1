package listvieweditview;

/**
 * Created by wenhao on 2017/10/26.
 */

public class Bean {
    private String Name;
    private String Input;
    public Bean(String Name,String Input){
        this.Name=Name;
        this.Input=Input;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setInput(String input) {
        Input = input;
    }

    public String getName() {

        return Name;
    }

    public String getInput() {
        return Input;
    }
}
