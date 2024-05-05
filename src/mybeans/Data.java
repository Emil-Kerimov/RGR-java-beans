package mybeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Data", propOrder = {
        "date",
        "x",
        "y"
})
public class Data {
    private String date;
    private double x;
    private double y;

    public Data(String date, double x, double y) {
        this.date = date;
        this.x = x;
        this.y = y;
    }

    public Data() {
        x = 0;
        y = 0;
        date = LocalDate.now().toString();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}