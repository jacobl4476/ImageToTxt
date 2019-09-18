public class PixelChar {
    int num = 0; //ASCII value
    String text = ""; //ASCII char
    double darkness = 0.0;//Darkness value from 0 to 255
    public PixelChar(int num, String text, double darkness){
        this.num = num;
        this.text = text;
        this.darkness = darkness;
    }

    public String toString() {
        return (num + ", " + text + ", " + darkness);
    }

    public double getDarkness() {
        return darkness;
    }

    public int getNum() {
        return num;
    }

    public String getText() {
        return text;
    }

}
