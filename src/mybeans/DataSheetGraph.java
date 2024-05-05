package mybeans;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class DataSheetGraph extends JPanel {
    private JPanel dataSheetGraph;

    private void createUIComponents() {
        initialize();
    }

    private static final long serialVersionUID = 1L;

    private DataSheet dataSheet = null;
    private boolean isConnected;
    private int deltaX;
    private int deltaY;
    transient private Color color;


    public DataSheetGraph() {
        super();
        initialize();
    }

    private void initialize() {
        isConnected = false;
        deltaX = 3;
        deltaY = 3;
        color = Color.red;
        this.setSize(600, 600);
    }

    public DataSheet getDataSheet() {
        return dataSheet;
    }

    public void setDataSheet(DataSheet dataSheet) {
        this.dataSheet = dataSheet;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
        repaint();
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
        repaint();
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
        repaint();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    private double minX() {
// Вісь повинна відображатись
        double result = 0;
// Визначаємо мінімальне значення Х
        if (dataSheet != null) {
            int size = dataSheet.size();
            for (int i = 0; i < size; i++)
                if (dataSheet.getDataItem(i).getX() < result)
                    result = dataSheet.getDataItem(i).getX();
        }
        return result;
    }

    private double maxX() {
        double result = 0;
        if (dataSheet != null) {
            int size = dataSheet.size();
            for (int i = 0; i < size; i++)
                if (dataSheet.getDataItem(i).getX() > result)
                    result = dataSheet.getDataItem(i).getX();
        }
        return result;
    }

    private double minY() {
        double result = 0;
        if (dataSheet != null) {
            int size = dataSheet.size();
            for (int i = 0; i < size; i++)
                if (dataSheet.getDataItem(i).getY() < result)
                    result = dataSheet.getDataItem(i).getY();
        }
        return result;
    }

    private double maxY() {
        double result = 0;
        if (dataSheet != null) {
            int size = dataSheet.size();
            for (int i = 0; i < size; i++)
                if (dataSheet.getDataItem(i).getY() > result)
                    result = dataSheet.getDataItem(i).getY();
        }
        return result;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        showGraph(g2);
    }
    public void showGraph(Graphics2D gr) {
        double xMin, xMax, yMin, yMax;
        double width = getWidth();
        double height = getHeight();
        xMin = minX() - deltaX;
        xMax = maxX() + deltaX;
        yMin = minY() - deltaY;
        yMax = maxY() + deltaY;

        double xScale = width / (xMax - xMin);
        double yScale = height / (yMax - yMin);
        double x0 = -xMin * xScale;
        double y0 = yMax * yScale;

        Paint oldColor = gr.getPaint();
        gr.setPaint(Color.WHITE);
        gr.fill(new Rectangle2D.Double(0.0, 0.0, width, height));
        Stroke oldStroke = gr.getStroke();
        Font oldFont = gr.getFont();

        float[] dashPattern = {10, 10};
        gr.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0));
        gr.setFont(new Font("Serif", Font.BOLD, 10));

        double xStep = 1;
        double yStep = 1;
        for (double dx = xStep; dx < xMax; dx += xStep) {
            calculateStepX(gr, height, xScale, x0, xStep, dx);
        }
        for (double dx = -xStep; dx >= xMin; dx -= xStep) {
            calculateStepX(gr, height, xScale, x0, xStep, dx);
        }

        for (double dy = yStep; dy < yMax; dy += yStep) {
            calculateStepY(gr, width, yScale, y0, yStep, dy);
        }

        for (double dy = -yStep; dy >= yMin; dy -= yStep) {
            calculateStepY(gr, width, yScale, y0, yStep, dy);
        }

        gr.setPaint(Color.BLACK);
        gr.setStroke(new BasicStroke(3.0f));
        gr.draw(new Line2D.Double(x0, 0, x0, height));
        gr.draw(new Line2D.Double(0, y0, width, y0));
        gr.drawString("X", (int) width - 10, (int) y0 - 2);
        gr.drawString("Y", (int) x0 + 2, 10);

        if (dataSheet != null) {
            if (!isConnected) {
                for (int i = 0; i < dataSheet.size(); i++) {
                    double x = x0 + (dataSheet.getDataItem(i).getX() * xScale);
                    double y = y0 - (dataSheet.getDataItem(i).getY() * yScale);
                    gr.setColor(Color.white);
                    gr.fillOval((int) (x - 5 / 2), (int) (y - 5 / 2), 5, 5);
                    gr.setColor(color);
                    gr.drawOval((int) (x - 5 / 2), (int) (y - 5 / 2), 5, 5);
                }
            } else {
                gr.setPaint(color);
                gr.setStroke(new BasicStroke(2.0f));
                double xOld = x0 + dataSheet.getDataItem(0).getX() * xScale;
                double yOld = y0 - dataSheet.getDataItem(0).getY() * yScale;
                for (int i = 1; i < dataSheet.size(); i++) {
                    double x = x0 + dataSheet.getDataItem(i).getX() * xScale;
                    double y = y0 - dataSheet.getDataItem(i).getY() * yScale;
                    gr.draw(new Line2D.Double(xOld, yOld, (double) x, y));
                    xOld = x;
                    yOld = y;
                }
            }
            gr.setPaint(oldColor);
            gr.setStroke(oldStroke);
            gr.setFont(oldFont);
        }
    }

    private void calculateStepY(Graphics2D gr, double width, double yScale, double y0, double yStep, double dy) {
        double y = y0 - dy * yScale;
        gr.setPaint(Color.LIGHT_GRAY);
        gr.draw(new Line2D.Double(0, y, width, y));
        gr.setPaint(Color.BLACK);
        gr.drawString(Math.round(dy / yStep) * yStep + "", 2, (int) y - 2);
    }

    private void calculateStepX(Graphics2D gr, double height, double xScale, double x0, double xStep, double dx) {
        double x = x0 + dx * xScale;
        gr.setPaint(Color.LIGHT_GRAY);
        gr.draw(new Line2D.Double(x, 0, x, height));
        gr.setPaint(Color.BLACK);
        gr.drawString(Math.round(dx / xStep) * xStep + "", (int) x + 2, 10);
    }
}